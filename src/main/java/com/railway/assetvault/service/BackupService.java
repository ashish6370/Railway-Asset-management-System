package com.railway.assetvault.service;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
@Service
public class BackupService {
    @Scheduled(cron = "0 0 2 * * ?")
    public void performBackup() {
        System.out.println("Executing daily database backup...");
    }
}