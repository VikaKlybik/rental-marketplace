package com.bsuir.service;

import com.bsuir.dto.ArticleRequest;
import com.bsuir.dto.ArticleResponse;
import com.bsuir.dto.ArticleResponseList;

public interface ArticleService {
    ArticleResponse getArticleById(Long id);
    ArticleResponse saveArticle(ArticleRequest articleRequest, String username);
    ArticleResponseList getAllArticle(int page);
}