package com.semantiq.server.controller;

import com.semantiq.server.service.BotDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/botdata")
public class BotDataController {
    private final BotDataService botDataService;

    @Autowired
    public BotDataController(BotDataService botDataService) {
        this.botDataService = botDataService;
    }

    @PostMapping("/upvote/{chatBotId}")
    // countPos'u bir arttır (parametre chatbotun id'si)
    // BotDataService'in içinde de implemente edilip burada fonskiyon gibi çağırılabilir.
    // yorumları sil
    // yapılan arttırmalar veritabanına kaydeilmesi için işlem bitince servisin içindeki saveBotData kullanılmalı

    @PostMapping("/downvote/{chatBotId}")
    // countNeg'i bir arttır (parametre chatbotun id'si)

    @GetMapping("/{chatBotId}/votes")
    // vote sayılarını json olarak geri döndür

    @GetMapping("/{chatBotId}") // bu şimdilik durabilir formdatatı geri döndürecek
}