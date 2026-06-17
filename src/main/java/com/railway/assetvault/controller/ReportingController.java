package com.railway.assetvault.controller;
import com.railway.assetvault.service.ReportingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/reports") @CrossOrigin
public class ReportingController {
    @Autowired private ReportingService rs;
    @GetMapping("/pdf") public byte[] pdf() { return rs.generatePdfReport(); }
    @GetMapping("/excel") public byte[] excel() { return rs.generateExcelReport(); }
}