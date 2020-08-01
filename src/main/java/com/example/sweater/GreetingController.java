package com.example.sweater;

import com.example.sweater.domain.Message;
import com.example.sweater.repos.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class GreetingController {
    @Autowired
    private MessageRepository messageRepository;

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name = "name", required = false, defaultValue = "World") String name,
                           Map<String, Object> model) {
        model.put("name", name);
        return "greeting";
    }

    @GetMapping
    public String mainPage(Map<String, Object> model) {
        model.put("messages", messageRepository.findAll());
        return "main-page";
    }

    @PostMapping
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
        if(filter!=null && !filter.isEmpty()){
            messages = messageRepository.findByTag(filter);
        } else {
            messages = messageRepository.findAll();
        }

        model.put("messages", messages);

        return "main-page";
    }
}
