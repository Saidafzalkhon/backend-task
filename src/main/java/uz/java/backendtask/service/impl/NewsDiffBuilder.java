package uz.java.backendtask.service.impl;

import org.springframework.stereotype.Component;
import uz.java.backendtask.dto.NewsSnapshot;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class NewsDiffBuilder {

    public Map<String, Object> diff(NewsSnapshot oldS, NewsSnapshot newS) {

        Map<String, Object> diff = new LinkedHashMap<>();

        compare(diff, "status", oldS.getStatus(), newS.getStatus());
        compare(diff, "isFeatured", oldS.getIsFeatured(), newS.getIsFeatured());
        compare(diff, "isDeleted", oldS.getIsDeleted(), newS.getIsDeleted());
        compare(diff, "publishAt", oldS.getPublishAt(), newS.getPublishAt());
        compare(diff, "unpublishAt", oldS.getUnpublishAt(), newS.getUnpublishAt());
        compare(diff, "categoryId", oldS.getCategoryId(), newS.getCategoryId());
        compare(diff, "coverMediaId", oldS.getCoverMediaId(), newS.getCoverMediaId());
        compare(diff, "tagIds", oldS.getTagIds(), newS.getTagIds());

        return diff;
    }

    private void compare(
            Map<String, Object> diff,
            String field,
            Object oldVal,
            Object newVal
    ) {
        if (!Objects.equals(oldVal, newVal)) {
            diff.put(field, Map.of(
                    "old", oldVal,
                    "new", newVal
            ));
        }
    }
}
