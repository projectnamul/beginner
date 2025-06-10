package org.namul.api.payload.message.format;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SlackText {

    private String type;
    private String text;

    public static SlackText mrkdwn(String text) {
        return from("mrkdwn", text);
    }

    public static SlackText plainText(String text) {
        return from("plain_text", text);
    }

    public static SlackText from(String type, String text) {
        return SlackText.builder()
                .type(type)
                .text(text)
                .build();
    }
}
