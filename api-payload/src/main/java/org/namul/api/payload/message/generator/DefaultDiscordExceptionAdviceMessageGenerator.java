package org.namul.api.payload.message.generator;

import jakarta.servlet.http.HttpServletRequest;
import org.namul.api.payload.message.DiscordExceptionAdviceMessage;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;

/**
 * Default discord message generator
 */
public class DefaultDiscordExceptionAdviceMessageGenerator implements ExceptionAdviceMessageGenerator<DiscordExceptionAdviceMessage> {

    private static final String DISCORD_CONTENT = "# 🚨 Error";
    private static final String DISCORD_TITLE = "ℹ️ Error Info";
    private static final String DISCORD_DESCRIPTION_FORMAT = "### 🕖 Time\n%s\n### 🔗 Request URL\n%s\n### 📝 Message\n```\n%s\n```";
    private static final String DISCORD_STACK_TRACE_TITLE = "📚 Stack Trace";
    private static final String DISCORD_STACK_TRACE_DESCRIPTION_FORMAT = "```\n%s\n```";

    @Override
    public DiscordExceptionAdviceMessage createMessage(HttpServletRequest request, Exception exception) {
        String description = String.format(DISCORD_DESCRIPTION_FORMAT, LocalDateTime.now(), createRequestFullPath(request), getErrorMethodAndClass(exception));
        return DiscordExceptionAdviceMessage.from(DISCORD_CONTENT, DISCORD_TITLE, description, DISCORD_STACK_TRACE_TITLE, String.format(DISCORD_STACK_TRACE_DESCRIPTION_FORMAT, getStackTrace(exception).substring(0, 1000)));
    }

    /**
     * Create request uri for message
     * @return The created request path
     */
    private String createRequestFullPath(HttpServletRequest request) {
        String fullPath = request.getMethod() + " " + request.getRequestURL();

        String queryString = request.getQueryString();
        if (queryString != null) {
            fullPath += "?" + queryString;
        }

        return fullPath;
    }

    /**
     * Get stack trace for message
     * @param e The exception that occurs
     * @return The string contains stack trace
     */
    private String getStackTrace(Exception e) {
        StringWriter stringWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

    /**
     * The method and class data that exception occurs
     * @param e The exception that occurs
     * @return The method and class data
     */
    private String getErrorMethodAndClass(Exception e) {
        StackTraceElement[] stackTraces = e.getStackTrace();
        if (stackTraces.length > 0) {
            StackTraceElement stackTrace = stackTraces[0];
            return "Method: " + stackTrace.getMethodName() + "\n Class: " + stackTrace.getClassName();
        }
        return null;
    }
}
