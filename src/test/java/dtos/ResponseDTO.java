package dtos;

import lombok.Data;

import java.util.List;

@Data
public class ResponseDTO {

    private String next;
    private List<ResultDTO> results;

}
