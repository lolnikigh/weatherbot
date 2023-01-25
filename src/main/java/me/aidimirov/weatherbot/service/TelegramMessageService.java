package me.aidimirov.weatherbot.service;

import com.vdurmont.emoji.EmojiParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TelegramMessageService {

    @Autowired private WeatherService weatherService;

    public String startMessage() {
        return EmojiParser.parseToUnicode("Привет! Я бот-помощник по погоде в твоем городе. Вот список доступных команд");
    }

}
