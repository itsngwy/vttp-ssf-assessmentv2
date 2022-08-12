package vttp2022.ssf.ssfa.services;

import java.io.Reader;
import java.io.StringReader;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp2022.ssf.ssfa.models.Article;
import vttp2022.ssf.ssfa.repositories.NewsRepository;

@Service
public class NewsService {

    // Autowired repo
    private static final String URL = "https://min-api.cryptocompare.com/data/v2/news/";

    @Value("${API_KEY}")
    private String key;

    @Autowired
    private NewsRepository newsRepo;
    
    public List<Article> getArticles() {

        String payload;

        String url = UriComponentsBuilder.fromUriString(URL)
        .queryParam("lang", "EN")
        .queryParam("api_key", key)
        .toUriString();
        System.out.println(url);
        

        // Create the GET request, the GET url
        RequestEntity<Void> req = RequestEntity.get(url).build();
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp;

        try {
            // Throws an exception if status code not in between 200 - 399
            // Get response from the client with our request
            resp = template.exchange(req, String.class);
        } catch (Exception ex) {
            System.err.printf("Error: %s\n", ex.getMessage());
            return Collections.emptyList();
        }

        payload = resp.getBody();
        //System.out.println("payload: " + payload);

        Reader strReader = new StringReader(payload);
        JsonReader jsonReader = Json.createReader(strReader);
        JsonObject newsResult = jsonReader.readObject();

        JsonArray newsData = newsResult.getJsonArray("Data");

        List<Article> list = new LinkedList<>();

        for (int i = 0; i < newsData.size(); i++) {
            // weather array=cities so weather[0] to get the first array
            // Get object in the weather array
            JsonObject jo = newsData.getJsonObject(i);
            // static use of create method
            list.add(Article.create(jo));
        }

        return list;
    }

    public void saveToRepo(String id, String payload) {
        newsRepo.save(id, payload);
    }

    public Optional<Article> getNewsById(Integer id) {
        return getNewsById(id.toString());
    }
    public Optional<Article> getNewsById(String id) {
        // Check if repo already has the value 
        String result = newsRepo.get(id);
        if (null == result)
            // Return an empty container if no value is found based on id given 
            return Optional.empty();

        // Creating a Boardgame object with values retrieved from redis
        return Optional.of(Article.createNew(result));
    }
}
