package com.findsoulmate.controllers;

import com.findsoulmate.models.Post;
import com.findsoulmate.repo.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

import static com.findsoulmate.models.Post.ASIA_NOVOSIBIRSK;
import static com.findsoulmate.models.Post.DATE_FORMAT;


@Controller
@RequiredArgsConstructor
public class BlogController {

    public static final String REDIRECT_BLOG = "redirect:/blog";
    private final PostRepository postRepository;

    @GetMapping("/blog")
    public String blogMain(@AuthenticationPrincipal String customerName, Model model) {
        Iterable<Post> posts = postRepository.findAll()
                .stream()
                .sorted(Comparator.reverseOrder())
                .toList();
        if (customerName != null) {
            model.addAttribute("customer", "Bonjour, " + customerName);
        }
        model.addAttribute("posts", posts);
        return "blog-main";
    }

    @GetMapping("/blog/add")
    public String blogAdd(Model model) {
        return "blog-add";
    }

    @PostMapping("/blog/add")
    public String blogPostAdd(@RequestParam String title, String anons, String text, Model model,
                              @AuthenticationPrincipal String customerName) {

        Post post = new Post(title, anons, text, customerName);
        postRepository.save(post);
        return REDIRECT_BLOG;
    }

    private boolean getPost(@PathVariable(value = "id") long id, Model model) {
        if (!postRepository.existsById(id)) {
            return true;
        }
        Optional<Post> optionalPost = postRepository.findById(id);
        List<Post> post = new ArrayList<>();
        optionalPost.ifPresent(post::add);
        model.addAttribute("post", post);
        return false;
    }

    @GetMapping("/blog/{id}/edit")
    public String blogEdit(@PathVariable(value = "id") long id, Model model) {
        if (getPost(id, model)) {
            return REDIRECT_BLOG;
        }
        return "blog-edit";
    }

    @PostMapping("/blog/{id}/edit")
    public String blogUpdate(@PathVariable(value = "id") long id, @RequestParam String title,
                             String anons, String text, Model model,
                             @AuthenticationPrincipal String customerName) {

        Post post = postRepository.findById(id).orElseThrow();
        post.setAnons(anons);
        post.setText(text);
        DATE_FORMAT.setTimeZone(TimeZone.getTimeZone(ASIA_NOVOSIBIRSK));
        post.setTime(DATE_FORMAT.format(new Date()));
        post.setTitle(title);
        post.setUserName(customerName);
        postRepository.save(post);
        return REDIRECT_BLOG;
    }

    @PostMapping("/blog/{id}/remove")
    public String blogDelete(@PathVariable(value = "id") long id, Model model) {
        Post post = postRepository.findById(id).orElseThrow();
        postRepository.delete(post);
        return REDIRECT_BLOG;
    }
}
