package com.bsuir.mapper;

import com.bsuir.dto.ArticleResponse;
import com.bsuir.entity.Article;
import com.bsuir.entity.User;
import com.bsuir.entity.UserDetails;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-04-28T22:37:26+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.9 (GraalVM Community)"
)
@Component
public class ArticleMapperImpl implements ArticleMapper {

    @Override
    public Article toEntity(ArticleResponse articleResponse) {
        if ( articleResponse == null ) {
            return null;
        }

        Article.ArticleBuilder article = Article.builder();

        article.id( articleResponse.getId() );
        article.content( articleResponse.getContent() );
        article.title( articleResponse.getTitle() );
        article.createdAt( articleResponse.getCreatedAt() );
        article.imageUrl( articleResponse.getImageUrl() );
        article.description( articleResponse.getDescription() );

        return article.build();
    }

    @Override
    public ArticleResponse toDto(Article article) {
        if ( article == null ) {
            return null;
        }

        ArticleResponse articleResponse = new ArticleResponse();

        articleResponse.setUserId( articleUserId( article ) );
        articleResponse.setEmail( articleUserUserDetailsEmail( article ) );
        articleResponse.setFirstName( articleUserUserDetailsFirstName( article ) );
        articleResponse.setLastName( articleUserUserDetailsLastName( article ) );
        articleResponse.setId( article.getId() );
        articleResponse.setTitle( article.getTitle() );
        articleResponse.setContent( article.getContent() );
        articleResponse.setCreatedAt( article.getCreatedAt() );
        articleResponse.setImageUrl( article.getImageUrl() );
        articleResponse.setDescription( article.getDescription() );

        return articleResponse;
    }

    @Override
    public Article partialUpdate(ArticleResponse articleResponse, Article article) {
        if ( articleResponse == null ) {
            return article;
        }

        if ( articleResponse.getId() != null ) {
            article.setId( articleResponse.getId() );
        }
        if ( articleResponse.getContent() != null ) {
            article.setContent( articleResponse.getContent() );
        }
        if ( articleResponse.getTitle() != null ) {
            article.setTitle( articleResponse.getTitle() );
        }
        if ( articleResponse.getCreatedAt() != null ) {
            article.setCreatedAt( articleResponse.getCreatedAt() );
        }
        if ( articleResponse.getImageUrl() != null ) {
            article.setImageUrl( articleResponse.getImageUrl() );
        }
        if ( articleResponse.getDescription() != null ) {
            article.setDescription( articleResponse.getDescription() );
        }

        return article;
    }

    @Override
    public List<ArticleResponse> toListOfDto(List<Article> articleList) {
        if ( articleList == null ) {
            return null;
        }

        List<ArticleResponse> list = new ArrayList<ArticleResponse>( articleList.size() );
        for ( Article article : articleList ) {
            list.add( toDto( article ) );
        }

        return list;
    }

    private Long articleUserId(Article article) {
        if ( article == null ) {
            return null;
        }
        User user = article.getUser();
        if ( user == null ) {
            return null;
        }
        Long id = user.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String articleUserUserDetailsEmail(Article article) {
        if ( article == null ) {
            return null;
        }
        User user = article.getUser();
        if ( user == null ) {
            return null;
        }
        UserDetails userDetails = user.getUserDetails();
        if ( userDetails == null ) {
            return null;
        }
        String email = userDetails.getEmail();
        if ( email == null ) {
            return null;
        }
        return email;
    }

    private String articleUserUserDetailsFirstName(Article article) {
        if ( article == null ) {
            return null;
        }
        User user = article.getUser();
        if ( user == null ) {
            return null;
        }
        UserDetails userDetails = user.getUserDetails();
        if ( userDetails == null ) {
            return null;
        }
        String firstName = userDetails.getFirstName();
        if ( firstName == null ) {
            return null;
        }
        return firstName;
    }

    private String articleUserUserDetailsLastName(Article article) {
        if ( article == null ) {
            return null;
        }
        User user = article.getUser();
        if ( user == null ) {
            return null;
        }
        UserDetails userDetails = user.getUserDetails();
        if ( userDetails == null ) {
            return null;
        }
        String lastName = userDetails.getLastName();
        if ( lastName == null ) {
            return null;
        }
        return lastName;
    }
}
