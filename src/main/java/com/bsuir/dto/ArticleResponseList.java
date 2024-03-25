package com.bsuir.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleResponseList {
    private List<ArticleResponse> articleResponses;
    private Integer pageCount;
    private Integer currentPage;
}