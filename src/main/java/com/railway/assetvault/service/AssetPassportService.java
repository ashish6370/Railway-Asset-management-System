package com.railway.assetvault.service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.railway.assetvault.dto.response.AssetPassportDTO;
import com.railway.assetvault.dto.response.TimelineEvent;
import com.railway.assetvault.entity.*;
import com.railway.assetvault.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class AssetPassportService {

    @Autowired private AssetRepository assetRepository;
    @Autowired private AssetAssignmentRepository assignmentRepository;
    @Autowired private MaintenanceLogRepository maintenanceLogRepository;
    @Autowired private AssetTransferRepository transferRepository;
    @Autowired private AssetDisposalRepository disposalRepository;
    @Autowired private AuditItemRepository auditItemRepository;

    public AssetPassportDTO getPassport(Long assetId) {
        Asset asset = assetRepository.findById(assetId)
                .orElseThrow(() -> new RuntimeException("Asset not found"));

        List<TimelineEvent> timeline = new ArrayList<>();

        // 1. Purchase Event
        if (asset.getPurchaseDate() != null) {
            timeline.add(new TimelineEvent("PROCUREMENT", "Asset purchased from " + asset.getVendorName(), 
                asset.getPurchaseDate().atStartOfDay(), "System", "COMPLETED"));
        }

        // 2. Assignments
        List<AssetAssignment> assignments = assignmentRepository.findByAssetId(assetId);
        for (AssetAssignment a : assignments) {
            timeline.add(new TimelineEvent("ASSIGNMENT", "Assigned to " + a.getUser().getName(), 
                a.getAssignedDate(), "Admin", a.getStatus()));
            if (a.getReturnDate() != null) {
                timeline.add(new TimelineEvent("RETURN", "Returned by " + a.getUser().getName(), 
                    a.getReturnDate(), "Admin", "COMPLETED"));
            }
        }

        // 3. Maintenance
        List<MaintenanceLog> maintenanceLogs = maintenanceLogRepository.findByAssetId(assetId);
        for (MaintenanceLog m : maintenanceLogs) {
            LocalDateTime date = m.getCompletionDate() != null ? m.getCompletionDate().atStartOfDay() : m.getScheduledDate().atStartOfDay();
            timeline.add(new TimelineEvent("MAINTENANCE", m.getDescription(), date, m.getPerformedBy(), m.getStatus()));
        }

        // 4. Transfers
        List<AssetTransfer> transfers = transferRepository.findByAssetId(assetId);
        for (AssetTransfer t : transfers) {
            timeline.add(new TimelineEvent("TRANSFER", "Transferred to " + t.getToDepot().getName(), 
                t.getRequestDate(), t.getRequestedBy().getName(), t.getStatus()));
        }

        // 5. Audits
        List<AuditItem> audits = auditItemRepository.findByAssetId(assetId);
        for (AuditItem ai : audits) {
            timeline.add(new TimelineEvent("AUDIT", "Status: " + ai.getStatus(), 
                ai.getAuditSession().getStartDate(), ai.getAuditSession().getAuditor().getName(), "VERIFIED"));
        }

        // Sort by Date descending
        timeline.sort(Comparator.comparing((TimelineEvent e) -> e.date).reversed());

        // Calculate Health Score (Simple heuristic)
        double healthScore = 100.0;
        int ageInYears = asset.getPurchaseDate() != null ? 
            java.time.Period.between(asset.getPurchaseDate(), java.time.LocalDate.now()).getYears() : 0;
        
        healthScore -= (ageInYears * 2); // -2% per year
        healthScore -= (maintenanceLogs.size() * 5); // -5% per maintenance issue

        if ("POOR".equalsIgnoreCase(asset.getConditionStatus())) healthScore -= 30;
        if ("FAIR".equalsIgnoreCase(asset.getConditionStatus())) healthScore -= 15;

        if (healthScore < 0) healthScore = 0;

        return new AssetPassportDTO(asset, healthScore, timeline);
    }

    public byte[] generatePassportPdf(Long assetId) {
        AssetPassportDTO passport = getPassport(assetId);
        Asset asset = passport.asset;

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, baos);
            document.open();

            // Fonts
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 24, java.awt.Color.BLUE);
            Font subTitleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
            Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 12);

            // Title
            Paragraph title = new Paragraph("Digital Asset Passport", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph(" "));

            // Asset Details Table
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);

            addCell(table, "Asset ID", subTitleFont);
            addCell(table, asset.getAssetId(), normalFont);

            addCell(table, "Name", subTitleFont);
            addCell(table, asset.getName(), normalFont);

            addCell(table, "Serial Number", subTitleFont);
            addCell(table, asset.getSerialNumber(), normalFont);

            addCell(table, "Brand & Model", subTitleFont);
            addCell(table, asset.getBrand() + " " + asset.getModel(), normalFont);

            addCell(table, "Health Score", subTitleFont);
            addCell(table, String.format("%.0f%%", passport.healthScorePercentage), normalFont);

            addCell(table, "Current Status", subTitleFont);
            addCell(table, asset.getStatus() + " (" + asset.getConditionStatus() + ")", normalFont);

            document.add(table);
            document.add(new Paragraph(" "));
            
            // Timeline
            Paragraph timelineTitle = new Paragraph("Lifecycle Timeline", subTitleFont);
            document.add(timelineTitle);
            document.add(new Paragraph(" "));

            PdfPTable timelineTable = new PdfPTable(4);
            timelineTable.setWidthPercentage(100);
            timelineTable.setWidths(new float[]{2f, 2f, 4f, 2f});
            
            addCell(timelineTable, "Date", subTitleFont);
            addCell(timelineTable, "Event", subTitleFont);
            addCell(timelineTable, "Description", subTitleFont);
            addCell(timelineTable, "Status", subTitleFont);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            for (TimelineEvent event : passport.timeline) {
                addCell(timelineTable, event.date.format(formatter), normalFont);
                addCell(timelineTable, event.type, normalFont);
                addCell(timelineTable, event.description, normalFont);
                addCell(timelineTable, event.status, normalFont);
            }

            document.add(timelineTable);
            document.close();

            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate PDF", e);
        }
    }

    private void addCell(PdfPTable table, String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text != null ? text : "N/A", font));
        cell.setPadding(8);
        table.addCell(cell);
    }
}
