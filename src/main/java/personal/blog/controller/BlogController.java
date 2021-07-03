package personal.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import personal.blog.dto.ArticleDto;
import personal.blog.service.BlogService;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "http://127.0.0.1:5500/")
@RestController
@RequestMapping("/api/")
public class BlogController {
    @Autowired
    private BlogService blogService;


    @PostMapping("article/create")
    public ResponseEntity<Void> create(@RequestBody @Valid ArticleDto articleDto){
        blogService.create(articleDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/articles/{page}")
    @ResponseBody
    public Page<ArticleDto> getArticles(@PathVariable("page") Integer page){
       return blogService.getAllArticles(page);
    }
}
