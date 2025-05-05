package tn.esprit.tic.timeforge.controllers;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tic.timeforge.service.ChatbotService;

@RestController
@RequestMapping("/api/chatbot")
@CrossOrigin(origins = "*")
public class ChatbotController {

    private final ChatbotService chatbotService;

    public ChatbotController(ChatbotService chatbotService) {
        this.chatbotService = chatbotService;
    }

    @GetMapping("/ask")
    public String askChatbot(@RequestParam String message) {
        return chatbotService.getChatbotResponse(message);
    }
}
