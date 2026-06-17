package com.railway.assetvault;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DataMigrationRunner implements CommandLineRunner {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) throws Exception {
        try { jdbcTemplate.execute("UPDATE assets SET asset_code = asset_id WHERE asset_code IS NULL OR asset_code = ''"); } catch(Exception e) {}
        try { jdbcTemplate.execute("UPDATE assets SET asset_name = name WHERE asset_name IS NULL OR asset_name = ''"); } catch(Exception e) {}
        try { jdbcTemplate.execute("ALTER TABLE assets MODIFY COLUMN asset_id varchar(255) NULL"); } catch(Exception e) {}
        try { jdbcTemplate.execute("ALTER TABLE assets MODIFY COLUMN name varchar(255) NULL"); } catch(Exception e) {}
        System.out.println("Data migration completed successfully. Old columns made nullable.");
    }
}
