package uz.java.backendtask.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import uz.java.backendtask.enumeration.NewsStatus;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
public class NewsHistoryResponseDTO  implements Serializable {

    private Long id;

    private NewsResponseDTO news;

    private UserResponseDTO changedBy;

    private NewsStatus fromStatus;

    private NewsStatus toStatus;

    private Object diffJson;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime changedAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
}
