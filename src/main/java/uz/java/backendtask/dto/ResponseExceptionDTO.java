package uz.java.backendtask.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ResponseExceptionDTO  implements Serializable {

    private Boolean ok;
    private String message;
    private Integer status;
}
