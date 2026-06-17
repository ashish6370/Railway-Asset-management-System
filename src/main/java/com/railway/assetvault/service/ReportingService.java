package com.railway.assetvault.service;
import org.springframework.stereotype.Service;
@Service
public class ReportingService {
    public byte[] generatePdfReport() { return "Mock PDF Data".getBytes(); }
    public byte[] generateExcelReport() { return "Mock Excel Data".getBytes(); }
}