package com.railway.assetvault.dto.response;

import com.railway.assetvault.entity.Asset;
import java.util.List;

public class AssetPassportDTO {
    public Asset asset;
    public double healthScorePercentage;
    public List<TimelineEvent> timeline;

    public AssetPassportDTO(Asset asset, double healthScorePercentage, List<TimelineEvent> timeline) {
        this.asset = asset;
        this.healthScorePercentage = healthScorePercentage;
        this.timeline = timeline;
    }
}
