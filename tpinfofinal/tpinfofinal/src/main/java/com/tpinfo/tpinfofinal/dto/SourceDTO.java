package com.tpinfo.tpinfofinal.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Builder
public class SourceDTO {

    private Long id;
    @NotNull
    private String name;
    private String code;
    private String createdAt;
}