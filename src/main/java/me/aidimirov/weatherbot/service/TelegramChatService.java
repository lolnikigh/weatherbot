package me.aidimirov.weatherbot.service;

import me.aidimirov.weatherbot.data.TelegramChatRepository;
import me.aidimirov.weatherbot.model.TelegramBotState;
import me.aidimirov.weatherbot.model.TelegramChat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TelegramChatService {

    @Autowired private TelegramChatRepository repository;


    public void setCity(Long chatId, String city) {
        var chat = repository.findByChatId(chatId);
        chat.setCity(city);
        repository.save(chat);
    }

    public String getCity(Long chatId) {
        return repository.findByChatId(chatId).getCity();
    }

    public void setState(Long chatId, TelegramBotState state) {
        var chat = repository.findByChatId(chatId);
        chat.setBotState(state);
        repository.save(chat);
    }

    public TelegramBotState getState(Long chatId) {
        return repository.findByChatId(chatId).getBotState();
    }

    public boolean isChatInit(Long chatId) {
        return (repository.findByChatId(chatId) != null);
    }

    public TelegramChat init(Long chatId) {
        return repository.save(new TelegramChat(null, chatId, TelegramBotState.DEFAULT, null));
    }
}
