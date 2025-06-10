package org.namul.api.payload.message.format;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SlackHeader implements SlackBlock {

    private final String type = "header";
    private SlackText text;

    public static SlackHeader plainText(String text) {
        return SlackHeader.builder()
                .text(SlackText.plainText(text))
                .build();
    }

    public static SlackHeader from(String type, String text) {
        return SlackHeader.builder()
                .text(SlackText.from(type, text))
                .build();
    }
}
