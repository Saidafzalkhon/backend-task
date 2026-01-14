package uz.java.backendtask.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import uz.java.backendtask.entity.News;
import uz.java.backendtask.entity.Tag;
import uz.java.backendtask.enumeration.NewsStatus;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class NewsSnapshot  implements Serializable {

    private NewsStatus status;
    private Boolean isFeatured;
    private Boolean isDeleted;
    private LocalDateTime publishAt;
    private LocalDateTime unpublishAt;
    private Long categoryId;
    private Long coverMediaId;
    private Set<Long> tagIds;

    public static NewsSnapshot from(News n) {
        return new NewsSnapshot(
                n.getStatus(),
                n.getIsFeatured(),
                n.getIsDeleted(),
                n.getPublishAt(),
                n.getUnpublishAt(),
                n.getCategory() != null ? n.getCategory().getId() : null,
                n.getCoverMedia() != null ? n.getCoverMedia().getId() : null,
                n.getTags().stream().map(Tag::getId).collect(Collectors.toSet())
        );
    }
}
