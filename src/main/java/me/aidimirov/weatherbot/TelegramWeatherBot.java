package me.aidimirov.weatherbot;

import com.vdurmont.emoji.EmojiParser;
import me.aidimirov.weatherbot.config.TelegramBotConfig;
import me.aidimirov.weatherbot.model.TelegramBotState;
import me.aidimirov.weatherbot.service.TelegramChatService;
import me.aidimirov.weatherbot.service.TelegramMessageService;
import me.aidimirov.weatherbot.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;

public class TelegramWeatherBot extends TelegramLongPollingBot {

    @Autowired private TelegramBotConfig config;
    @Autowired private WeatherService weatherService;
    @Autowired private TelegramChatService chatService;
    @Autowired private TelegramMessageService messageService;

    @Override
    public String getBotUsername() {
        return config.getUsername();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        var message = update.getMessage();
        var chatId = message.getChatId();
        if (!chatService.isChatInit(chatId)) {
            chatService.init(chatId);
            send(chatId, messageService.startMessage());
        } else {
            try {
                handleUpdate(update, chatId);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void handleUpdate(Update update, Long chatId) throws IOException {
        var state = chatService.getState(chatId);
        var message = update.getMessage().getText();

        switch (state) {
            case DEFAULT -> {
                if (message.equals("/city")) {
                    chatService.setState(chatId, TelegramBotState.SET_CITY);
                    send(chatId, "Введите город");
                }
            }
            case SET_CITY -> {
                if (weatherService.isCityExists(message)) {
                    chatService.setState(chatId, TelegramBotState.DEFAULT);
                    chatService.setCity(chatId, message);
                    send(chatId, "Город " + message + " успешно установлен");
                } else {
                    send(chatId, "Такого города не существует");
                }
            }
            case NOW -> {

            }
        }
    }

    public void send(Long chatId, String text) {
        var sendMessage = SendMessage.builder()
                .chatId(chatId.toString())
                .text(EmojiParser.parseToUnicode(text)).build();
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            try {
                botsApi.registerBot(this);
            } catch (TelegramApiRequestException e) {
                e.printStackTrace();
            }
        };
    }
}
