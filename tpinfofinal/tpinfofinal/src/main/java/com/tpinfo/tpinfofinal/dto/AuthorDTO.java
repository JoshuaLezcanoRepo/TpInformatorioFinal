package com.tpinfo.tpinfofinal.dto;

import lombok.Builder;
import lombok.Data;
import javax.validation.constraints.NotBlank;
@Data
@Builder
public class AuthorDTO {
    private Long id;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    private String fullname;
    private String createdAt;
}