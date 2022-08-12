package vttp2022.ssf.ssfa.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import vttp2022.ssf.ssfa.models.Article;
import vttp2022.ssf.ssfa.services.NewsService;

@RestController
@RequestMapping(path="/news")
public class NewsRESTController {

    @Autowired
    private NewsService newsSvc;
    
    @GetMapping(value="{id}")
    public ResponseEntity<String> getNewsArticle(@PathVariable String id) {
        
       Optional<Article> opt = newsSvc.getNewsById(id);

       if (opt.isEmpty()) {
        JsonObject err = Json.createObjectBuilder()
            .add("error", "Cannot find news article %s".formatted(id))
            .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(err.toString());
        }
        
        Article article = opt.get();
        return ResponseEntity.ok(article.toJson().toString());
    }
}
