package com.tpinfo.tpinfofinal.dto;

import lombok.Builder;
import lombok.Data;
import javax.validation.constraints.NotNull;
@Data
@Builder

public class SourceDTO {
    private Long id;
    @NotNull
    private String name;
    private String code;
    private String createdAt;
}