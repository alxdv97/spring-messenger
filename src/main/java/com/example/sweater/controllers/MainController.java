package com.example.sweater.controllers;

import com.example.sweater.domain.Message;
import com.example.sweater.repos.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class MainController {
    @Autowired
    private MessageRepository messageRepository;

    @GetMapping("/")
    public String greeting(Map<String, Object> model) {
        return "greeting";
    }

    @GetMapping("/main")
    public String mainPage(Map<String, Object> model) {
        model.put("messages", messageRepository.findAll());
        return "main-page";
    }

    @PostMapping("main")
    public String createMessage(@RequestParam String text,
                                @RequestParam String tag,
                                Map<String, Object> model) {

        messageRepository.save(new Message(text, tag));
        model.put("messages", messageRepository.findAll());

        return "main-page";
    }

    @GetMapping("/error")
    public String error(Map<String, Object> model){
        model.put("error", "Error!");
        return "error";
    }

    @PostMapping("filter")
    public String filter(@RequestParam String filter,
                         Map<String, Object> model){
        Iterable<Message> messages;
        if (filter != null && !filter.isEmpty()) {
            messages = messageRepository.findByTag(filter);
        } else {
            messages = messageRepository.findAll();
        }

        model.put("messages", messages);

        return "main-page";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
