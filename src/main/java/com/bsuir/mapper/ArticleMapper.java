package com.bsuir.mapper;

import com.bsuir.dto.ArticleResponse;
import com.bsuir.entity.Article;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ArticleMapper {
    Article toEntity(ArticleResponse articleResponse);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.userDetails.email", target = "email")
    @Mapping(source = "user.userDetails.firstName", target = "firstName")
    @Mapping(source = "user.userDetails.lastName", target = "lastName")
    ArticleResponse toDto(Article article);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Article partialUpdate(ArticleResponse articleResponse, @MappingTarget Article article);

    List<ArticleResponse> toListOfDto(List<Article> articleList);
}