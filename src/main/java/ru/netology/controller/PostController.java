package ru.netology.controller;

import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;
import ru.netology.service.PostService;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;

@Controller
public class PostController {
    public static final String APPLICATION_JSON = "application/json";
    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    public void all(HttpServletResponse response) throws IOException {
        response.setContentType(APPLICATION_JSON);
        final var gson = new Gson();
        final var data = service.all();
        response.getWriter().print(gson.toJson(data));
    }

    public void getById(long id, HttpServletResponse response) throws IOException {
        // Deserialize request & serialize response
        response.setContentType(APPLICATION_JSON);
        final var gson = new Gson();
        try {
            final var data = service.getById(id);
            response.getWriter().print(gson.toJson(data));
        } catch (NotFoundException exception) {
            response.getWriter().print(gson.toJson("Id not found!"));
        }
    }

    public void save(Reader body, HttpServletResponse response) throws IOException {
        response.setContentType(APPLICATION_JSON);
        final var gson = new Gson();
        final var post = gson.fromJson(body, Post.class);
        try {
            final var data = service.save(post);
            response.getWriter().print(gson.toJson(data));
        } catch (NotFoundException exception) {
            response.getWriter().print(gson.toJson("Id not found!"));
        }
    }

    public void removeById(long id, HttpServletResponse response) throws IOException {
        // Deserialize request & serialize response
        response.setContentType(APPLICATION_JSON);
        final var gson = new Gson();
        try {
            service.removeById(id);
            response.getWriter().print(gson.toJson("Deleted post with id: " + id + "."));
        } catch (NotFoundException exception) {
            response.getWriter().print(gson.toJson("Id not found!"));
        }
    }
}
