package me.aidimirov.weatherbot.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
@NoArgsConstructor
public class TelegramBotConfig {

    @Value("${bot.username}")
    private String username;
    @Value("${bot.token}")
    private String token;
}
