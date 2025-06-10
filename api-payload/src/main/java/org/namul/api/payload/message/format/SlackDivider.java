package org.namul.api.payload.message.format;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SlackDivider implements SlackBlock {
    private final String type = "divider";

    public static SlackDivider getInstance() {
        return new SlackDivider();
    }
}
