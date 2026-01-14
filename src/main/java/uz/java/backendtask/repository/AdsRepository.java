package uz.java.backendtask.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import uz.java.backendtask.dto.AdRow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class AdsRepository {

    private final NamedParameterJdbcTemplate jdbc;

    public List<AdRow> findAds(
            String placementCode,
            String lang,
            Long categoryId
    ) {

        String sql = """
                SELECT
                    ac.id AS creative_id,
                    ac.type AS creative_type,
                    ac.landing_url,
                    ac.image_media_id,
                    ac.html_snippet,
                    act.title,
                    act.alt_text,
                    aa.weight
                FROM ads_assignments aa
                JOIN ads_placements ap ON ap.id = aa.placement_id
                JOIN ads_campaigns c   ON c.id = aa.campaign_id
                JOIN ads_creative ac  ON ac.id = aa.creative_id
                LEFT JOIN ads_creative_translations act
                       ON act.creative_id = ac.id
                      AND act.lang = CAST(:lang AS text)
                WHERE ap.code = CAST(:placementCode AS text)
                  AND ap.is_active = true
                  AND aa.is_active = true
                  AND now() BETWEEN aa.start_at AND COALESCE(aa.end_at, now())
                  AND c.status = 'ACTIVE'
                  AND now() BETWEEN c.start_at AND COALESCE(c.end_at, now())
                  AND ac.is_active = true
                  AND (
                        aa.lang_filter IS NULL
                        OR aa.lang_filter @> jsonb_build_array(CAST(:lang AS text))
                      )
                  AND (
                        CAST(:categoryId AS bigint) IS NULL
                        OR aa.category_filter IS NULL
                        OR aa.category_filter @> jsonb_build_array(CAST(:categoryId AS bigint))
                      );
                """;

        Map<String, Object> params = new HashMap<>();
        params.put("placementCode", placementCode);
        params.put("lang", lang);
        params.put("categoryId", categoryId);


        return jdbc.query(sql, params, (rs, i) ->
                new AdRow(
                        rs.getLong("creative_id"),
                        rs.getString("creative_type"),
                        rs.getString("title"),
                        rs.getString("alt_text"),
                        rs.getString("landing_url"),
                        rs.getObject("image_media_id", Long.class),
                        rs.getString("html_snippet"),
                        rs.getInt("weight")
                )
        );
    }
}
