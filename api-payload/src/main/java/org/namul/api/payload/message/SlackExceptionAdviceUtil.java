package org.namul.api.payload.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class SlackExceptionAdviceUtil {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Send message to discord webhook url
     * @param webHookUrl The Slack server webhook url
     * @param slackExceptionAdviceMessage The message will be sent to discord
     */
    public void sendAlarm(String webHookUrl, SlackExceptionAdviceMessage slackExceptionAdviceMessage) {
        if (webHookUrl == null || webHookUrl.isEmpty() || slackExceptionAdviceMessage == null) {
            log.warn("SlackExceptionAdviceUtil: Cannot find Web hook url or slack message");
            return;
        }
        try {
            String jsonMessage = objectMapper.writeValueAsString(slackExceptionAdviceMessage);

            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-type", "application/json");

            HttpEntity<String> request = new HttpEntity<>(jsonMessage, headers);

            ResponseEntity<String> response = restTemplate.exchange(webHookUrl, HttpMethod.POST, request, String.class);

            if (!response.getStatusCode().is2xxSuccessful()) {
                log.warn("Failed to send Slack message: {}", response.getStatusCode());
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            log.warn("Failed to send Slack message", e);
        }
    }
}
