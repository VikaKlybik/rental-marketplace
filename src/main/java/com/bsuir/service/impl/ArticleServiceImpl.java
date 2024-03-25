package com.bsuir.service.impl;

import com.bsuir.dto.ArticleRequest;
import com.bsuir.dto.ArticleResponse;
import com.bsuir.dto.ArticleResponseList;
import com.bsuir.dto.UserDTO;
import com.bsuir.entity.Article;
import com.bsuir.entity.EmailDetails;
import com.bsuir.entity.User;
import com.bsuir.exception.ArticleNotFoundException;
import com.bsuir.exception.UserNotFoundException;
import com.bsuir.mapper.ArticleMapper;
import com.bsuir.repository.ArticleRepository;
import com.bsuir.repository.UserRepository;
import com.bsuir.service.ArticleService;
import com.bsuir.service.NotificationService;
import com.bsuir.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.bsuir.constant.RentalPropertiesConstants.DefaultValue.*;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {
    private final UserService userService;
    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;
    private final NotificationService notificationService;
    private final SpringTemplateEngine templateEngine;
    private final HttpServletRequest servletRequest;

    @Override
    public ArticleResponse getArticleById(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(ArticleNotFoundException::new);
        return articleMapper.toDto(article);
    }

    @Override
    @Transactional
    public ArticleResponse saveArticle(ArticleRequest articleRequest, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new UserNotFoundException(username));
        Article article = Article.builder()
                .title(articleRequest.getTitle())
                .content(articleRequest.getContent())
                .imageUrl(articleRequest.getImageUrl())
                .description(articleRequest.getDescription())
                .user(user)
                .build();
        Article savedArticle = articleRepository.save(article);

        sendUserNotification(savedArticle);

        return articleMapper.toDto(savedArticle);
    }

    @Override
    public ArticleResponseList getAllArticle(int page) {
        Pageable paging = PageRequest.of(page - 1, ELEMENT_PER_PAGE);
        Page<Article> articleListPage = articleRepository.findAll(paging);

        return ArticleResponseList.builder()
                .articleResponses(articleMapper.toListOfDto(articleListPage.getContent()))
                .currentPage(articleListPage.getNumber() + 1)
                .pageCount(articleListPage.getTotalPages())
                .build();
    }

    private void sendUserNotification(Article article) {
        List<UserDTO> userList = userService.getAllUser();
        String subject = NEW_ARTICLE_SUBJECT;

        for (UserDTO userDTO : userList) {
            String recipient = userDTO.getEmail();
            String content = getHtmlContent(generateProperty(article, userDTO), ARTICLE_TEMPLATE_NAME);
            EmailDetails emailDetails = EmailDetails.builder()
                    .subject(subject)
                    .recipient(recipient)
                    .messageBody(content)
                    .build();
            notificationService.sendEmail(emailDetails);
        }
    }

    private Map<String, Object> generateProperty(Article article, UserDTO userDTO) {
        Map<String, Object> properties = new HashMap<>();
        properties.put("fullName", userDTO.getLastName() + " " + userDTO.getFirstName());
        properties.put("title", article.getTitle());
        properties.put("description", article.getDescription());
        properties.put("link", generateLinkToArticle(article.getId()));
        properties.put("author", article.getUser().getUserDetails().getLastName()
                + " "
                + article.getUser().getUserDetails().getFirstName());
        return properties;
    }

    private String generateLinkToArticle(Long articleId) {
        return "http://" + servletRequest.getServerName()
                + ":"
                + servletRequest.getServerPort()
                + servletRequest.getContextPath()
                + "/article/" + articleId;
    }

    private String getHtmlContent(Map<String, Object> properties, String templateName) {
        Context context = new Context();
        context.setVariables(properties);
        return templateEngine.process(templateName, context);
    }
}