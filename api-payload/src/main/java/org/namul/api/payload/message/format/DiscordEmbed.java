package org.namul.api.payload.message.format;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class DiscordEmbed {

    private String title;
    private String description;

    public static DiscordEmbed from(String title, String description) {
        return DiscordEmbed.builder()
                .title(title)
                .description(description)
                .build();
    }
}