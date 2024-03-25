package com.bsuir.controller;

import com.bsuir.dto.ArticleRequest;
import com.bsuir.dto.ArticleResponse;
import com.bsuir.dto.ArticleResponseList;
import com.bsuir.dto.ImageLocation;
import com.bsuir.entity.User;
import com.bsuir.service.ArticleService;
import com.bsuir.service.GoogleDriverService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequiredArgsConstructor
@RequestMapping("/article")
public class ArticleController {
    private final ArticleService articleService;
    private final GoogleDriverService googleDriverService;
    private final HttpServletRequest request;

    @GetMapping
    public String getAllArticlePage(Model model, @RequestParam(defaultValue = "1") int page) {
        ArticleResponseList articleResponseList = articleService.getAllArticle(page);

        model.addAttribute("articles", articleResponseList);
        return "article-list";
    }

    @GetMapping("/create")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'ADMIN')")
    public String getCreateArticlePage(Model model) {
        model.addAttribute("article", new ArticleRequest());
        return "article-create";
    }

    @GetMapping("{id}")
    public String getArticleById(@PathVariable Long id, Model model) {
        ArticleResponse article = articleService.getArticleById(id);
        model.addAttribute("article", article);
        return "article-details";
    }

    @PostMapping("/create")
    @ResponseBody
    @PreAuthorize("hasAnyAuthority('MANAGER', 'ADMIN')")
    public ResponseEntity<?> saveArticle(@RequestBody ArticleRequest articleRequest, @AuthenticationPrincipal User user) {
        ArticleResponse article = articleService.saveArticle(articleRequest, user.getUsername());
        String redirectUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/article/" + article.getId();
        RedirectView redirectView = new RedirectView(redirectUrl);
        return ResponseEntity.ok().body(redirectView);
    }

    @PostMapping("/image")
    @PreAuthorize("hasAnyAuthority('MANAGER', 'ADMIN')")
    public ResponseEntity<ImageLocation> uploadImage(@RequestParam("file") MultipartFile file) {
        ImageLocation imageLocation = googleDriverService.uploadArticleImage(file);
        return ResponseEntity.ok(imageLocation);
    }
}