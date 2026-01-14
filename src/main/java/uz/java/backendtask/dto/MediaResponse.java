package uz.java.backendtask.dto;

import lombok.Getter;
import lombok.Setter;
import uz.java.backendtask.entity.Media;

import java.io.Serializable;

@Getter
@Setter
public class MediaResponse  implements Serializable {

    private Long id;
    private String url;
    private String mime;
    private Long size;
    private Integer width;
    private Integer height;

    public static MediaResponse from(Media media) {
        MediaResponse r = new MediaResponse();
        r.setId(media.getId());
        r.setUrl(media.getUrl());
        r.setMime(media.getMime());
        r.setSize(media.getSize());
        r.setWidth(media.getWidth());
        r.setHeight(media.getHeight());
        return r;
    }
}
