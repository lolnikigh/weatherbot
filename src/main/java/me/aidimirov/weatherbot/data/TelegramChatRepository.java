package me.aidimirov.weatherbot.data;

import me.aidimirov.weatherbot.model.TelegramChat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TelegramChatRepository extends JpaRepository<TelegramChat, Long> {

    TelegramChat findByChatId(Long chatId);

    void deleteByChatId(Long chatId);
}
