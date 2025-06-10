package org.namul.api.payload.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@ConfigurationProperties(prefix = "beginner.api.payload.slack")
public class SlackExceptionAdviceProperties {

    /**
     * Whether to enable sending message to slack
     */
    private boolean enable = false;

    /**
     * The exception name to use discord when it occurs
     */
    private List<String> scope = new ArrayList<>();

    /**
     * The slack incoming web hook url to send message
     */
    private String webHookUrl = null;
}
