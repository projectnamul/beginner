package org.namul.api.payload.properties;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@ConfigurationProperties(prefix = "beginner.api.payload.discord")
public class DiscordProperties {

    /**
     * Whether to enable sending message to discord
     */
    private boolean enable = false;

    /**
     * The exception name to use discord when it occurs
     */
    private List<String> scope = new ArrayList<>();

    /**
     * The discord web hook url to send message
     */
    private String webHookUrl = null;

}
