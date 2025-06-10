package org.namul.api.payload.message.format;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SlackField {
    private String type;
    private String text;

    public static SlackField mrkdwn(String text) {
        return from("mrkdwn", text);
    }

    public static SlackField plainText(String text) {
        return from("plain_text", text);
    }

    public static SlackField from(String type, String text) {
        return SlackField.builder()
                .type(type)
                .text(text)
                .build();
    }
}
