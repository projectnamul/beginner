package org.namul.api.payload.message;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * The discord message format
 */
@Builder
@Getter
public class DiscordExceptionAdviceMessage implements ExceptionAdviceMessage {

    private String content;
    private List<Embed> embeds;

    public static DiscordExceptionAdviceMessage from(String content, String title1, String description1, String title2, String description2) {
        List<Embed> embeds = new ArrayList<>();
        embeds.add(Embed.from(title1, description1));
        embeds.add(Embed.from(title2, description2));
        return from(content, embeds);
    }

    public static DiscordExceptionAdviceMessage from(String content, List<Embed> embeds) {
        return DiscordExceptionAdviceMessage.builder()
                .content(content)
                .embeds(embeds)
                .build();
    }

    @Builder
    @Getter
    public static class Embed {

        private String title;
        private String description;

        public static Embed from(String title, String description) {
            return Embed.builder()
                    .title(title)
                    .description(description)
                    .build();
        }
    }
}