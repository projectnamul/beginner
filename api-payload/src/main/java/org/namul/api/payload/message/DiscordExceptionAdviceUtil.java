package org.namul.api.payload.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

/**
 * The utility class for sending message to discord
 */
@Slf4j
public class DiscordExceptionAdviceUtil {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Send message to discord webhook url
     * @param webHookUrl The discord server webhook url
     * @param discordMessage The message will be sent to discord
     */
    public void sendAlarm(String webHookUrl, DiscordExceptionAdviceMessage discordMessage) {
        if (webHookUrl == null || webHookUrl.isEmpty() || discordMessage == null) {
            log.warn("DiscordExceptionAdviceUtil: Cannot find Web hook url or discord message");
            return;
        }
        try {
            String jsonMessage = objectMapper.writeValueAsString(discordMessage);

            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");

            HttpEntity<String> request = new HttpEntity<>(jsonMessage, headers);

            ResponseEntity<String> response = restTemplate.exchange(webHookUrl, HttpMethod.POST, request, String.class);

            if (response.getStatusCode() != HttpStatus.NO_CONTENT) {
                log.warn("Failed to send Discord message: {}", response.getStatusCode());
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            log.warn("Failed to send Discord message", e);
        }
    }
}
