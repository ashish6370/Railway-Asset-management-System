package com.railway.assetvault.service;

import com.railway.assetvault.entity.Asset;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExcelService {
    public ByteArrayInputStream assetsToExcel(List<Asset> assets) {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            Sheet sheet = workbook.createSheet("Assets");
            Row headerRow = sheet.createRow(0);
            String[] headers = { "ID", "Asset ID", "Name", "Serial Number", "Status" };
            for (int col = 0; col < headers.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(headers[col]);
            }

            int rowIdx = 1;
            for (Asset asset : assets) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(asset.getId());
                row.createCell(1).setCellValue(asset.getAssetId());
                row.createCell(2).setCellValue(asset.getName());
                row.createCell(3).setCellValue(asset.getSerialNumber() != null ? asset.getSerialNumber() : "");
                row.createCell(4).setCellValue(asset.getStatus());
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("Fail to import data to Excel file: " + e.getMessage());
        }
    }
}
