package org.namul.api.payload.message;

import lombok.Builder;
import lombok.Getter;
import org.namul.api.payload.message.format.DiscordEmbed;

import java.util.ArrayList;
import java.util.List;

/**
 * The discord message format
 */
@Builder
@Getter
public class DiscordExceptionAdviceMessage implements ExceptionAdviceMessage {

    private String content;
    private List<DiscordEmbed> discordEmbeds;

    public static DiscordExceptionAdviceMessage from(String content, String title1, String description1, String title2, String description2) {
        List<DiscordEmbed> discordEmbeds = new ArrayList<>();
        discordEmbeds.add(DiscordEmbed.from(title1, description1));
        discordEmbeds.add(DiscordEmbed.from(title2, description2));
        return from(content, discordEmbeds);
    }

    public static DiscordExceptionAdviceMessage from(String content, List<DiscordEmbed> discordEmbeds) {
        return DiscordExceptionAdviceMessage.builder()
                .content(content)
                .discordEmbeds(discordEmbeds)
                .build();
    }

}