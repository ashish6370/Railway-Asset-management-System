package com.railway.assetvault.dto.response;

import java.time.LocalDateTime;

public class TimelineEvent {
    public String type;
    public String description;
    public LocalDateTime date;
    public String performedBy;
    public String status;

    public TimelineEvent(String type, String description, LocalDateTime date, String performedBy, String status) {
        this.type = type;
        this.description = description;
        this.date = date;
        this.performedBy = performedBy;
        this.status = status;
    }
}
