package uz.java.backendtask.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import uz.java.backendtask.dto.AdRow;
import uz.java.backendtask.repository.AdsRepository;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class PublicAdsService {

    private final AdsRepository adsRepository;

    @Cacheable(
            value = "list120s",
            key = "#placementCode + ':' + #lang"
    )
    public AdRow getAd(String placementCode, String lang, Long categoryId) {

        List<AdRow> ads =
                adsRepository.findAds(placementCode, lang, categoryId);

        if (ads.isEmpty()) {
            return null;
        }

        return pickByWeight(ads);
    }

    public AdRow pickByWeight(List<AdRow> ads) {

        int totalWeight = ads.stream()
                .mapToInt(AdRow::weight)
                .sum();

        int random = ThreadLocalRandom.current().nextInt(totalWeight);

        int current = 0;
        for (AdRow ad : ads) {
            current += ad.weight();
            if (random < current) {
                return ad;
            }
        }
        return ads.get(0);
    }

}
