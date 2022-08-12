package vttp2022.ssf.ssfa.controllers;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.json.Json;
import vttp2022.ssf.ssfa.models.Article;
import vttp2022.ssf.ssfa.services.NewsService;

@Controller
@RequestMapping
public class NewsController {
    
    @Autowired
    private NewsService newsSvc;

    @GetMapping(path="/")
    public String getArticles(Model model, HttpSession sess) {
        List<Article> news = newsSvc.getArticles();
        sess.setAttribute("sess", news);

        // System.out.println(news.get(0).getId());
        // System.out.println(news.get(0).getPublished_on());
        // System.out.println(news.get(0).getTitle());
        // System.out.println(news.get(0).getUrl());
        // System.out.println(news.get(0).getImageurl());
        // System.out.println(news.get(0).getBody());
        // System.out.println(news.get(0).getTags());
        // System.out.println(news.get(0).getCategories());
        // System.out.println();

        model.addAttribute("news", news);
        return "news";
    }

    @PostMapping(path="/articles")
    public String saveArticles(HttpSession sess) {

        List<Article> myArt = (List<Article>) sess.getAttribute("sess");
        //System.out.println(myArt.toString());
        // for (int i=0; i < myArt.size(); i++) {
        for (int i=0; i < 10; i++) {
            String payload = Json.createObjectBuilder()
                .add("id", myArt.get(i).getId())
                .add("title", myArt.get(i).getTitle())
                .add("body", myArt.get(i).getBody())
                .add("published_on", myArt.get(i).getPublished_on())
                .add("url", myArt.get(i).getUrl())
                .add("imageurl", myArt.get(i).getImageurl())
                .add("tags", myArt.get(i).getTags())
                .add("categories", myArt.get(i).getCategories())
                .build().toString();

            newsSvc.saveToRepo(myArt.get(i).getId(), payload);
            //System.out.println(payload);
        }
        System.out.println("All Saved");
        return "redirect:/";
    }
}
