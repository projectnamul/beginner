package org.namul.api.payload.message.format;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class SlackSection {

    @Getter
    @Builder
    public static class Fields implements SlackBlock{
        private final String type = "section";
        private List<SlackField> fields;
    }

    @Getter
    @Builder
    public static class Text implements SlackBlock {
        private final String type = "section";
        private SlackText text;

        public static SlackSection.Text mrkdwn(String text) {
            return SlackSection.Text.builder()
                    .text(SlackText.mrkdwn(text))
                    .build();
        }

        public static SlackSection.Text plainText(String text) {
            return SlackSection.Text.builder()
                    .text(SlackText.plainText(text))
                    .build();
        }

        public static SlackSection.Text from(String type, String text) {
            return SlackSection.Text.builder()
                    .text(SlackText.from(type, text))
                    .build();
        }
    }
}
