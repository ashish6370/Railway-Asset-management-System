package com.railway.assetvault.controller;
import com.railway.assetvault.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
@RestController @RequestMapping("/api/dashboard") @CrossOrigin
public class DashboardController {
    @Autowired private DashboardService ds;
    @GetMapping("/metrics") public Map<String, Object> metrics() { return ds.getMetrics(); }
}