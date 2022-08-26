package com.tpinfo.tpinfofinal.controller.util;

import lombok.Builder;
import lombok.Data;
import java.util.List;
@Data
@Builder

public class PageResponse<T> {
    private List<T> content;
    private int pageNumber;
    private Long totalElements;
    private int totalPage;
    private String nextPage;
    private String previousPage;
}