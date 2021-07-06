package personal.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import personal.blog.dto.ArticleDto;
import personal.blog.entity.paging.Pagination;
import personal.blog.service.BlogService;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/")
public class BlogController {
    @Autowired
    private BlogService blogService;


    @PostMapping("article/create")
    public ResponseEntity<Void> createOrUpdate(@RequestBody @Valid ArticleDto articleDto) {
        blogService.createOrUpdate(articleDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("article/{id}")
    @ResponseBody
    public ArticleDto getArticle(@PathVariable("id") Integer id) {
        return blogService.findArticle(id);
    }

    @GetMapping("articles/")
    @ResponseBody
    public Pagination getPaginated(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page) {
        return blogService.getPagination(page);
    }
}
