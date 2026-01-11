package uz.java.backendtask.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ResponseExceptionDTO {

    private Boolean ok;
    private String message;
    private Integer status;
}
