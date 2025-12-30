package uz.railway.ticket.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import uz.railway.ticket.config.TelegramProperties;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class TelegramService {

    private final TelegramProperties properties;
    private final RestClient restClient = RestClient.create();

    public void sendMessage(String message) {
        String url = String.format("https://api.telegram.org/bot%s/sendMessage",
                properties.getBotToken());

        for (String chatId : properties.getChatId()) {
            Map<String, Object> payload = new HashMap<>();
            payload.put("chat_id", chatId);
            payload.put("text", message);
            payload.put("parse_mode", "HTML");
            payload.put("disable_web_page_preview", true);

            try {
                restClient.post()
                        .uri(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(payload)
                        .retrieve()
                        .body(String.class);

                log.info("Telegram xabar yuborildi");

                Thread.sleep(Duration.ofSeconds(2));
            } catch (Exception e) {
                log.error("Telegram xabar yuborishda xato: {}", e.getMessage());
            }
        }
    }
}
