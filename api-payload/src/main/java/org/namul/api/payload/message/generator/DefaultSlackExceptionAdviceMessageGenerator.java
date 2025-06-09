package org.namul.api.payload.message.generator;

import jakarta.servlet.http.HttpServletRequest;
import org.namul.api.payload.message.SlackExceptionAdviceMessage;
import org.namul.api.payload.message.format.SlackDivider;
import org.namul.api.payload.message.format.SlackField;
import org.namul.api.payload.message.format.SlackHeader;
import org.namul.api.payload.message.format.SlackSection;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DefaultSlackExceptionAdviceMessageGenerator implements ExceptionAdviceMessageGenerator<SlackExceptionAdviceMessage> {

    private static final String SLACK_HEADER = "🚨 Error";
    private static final String SLACK_SUB_HEADER = "*ℹ️ Error Info*";
    private static final String SLACK_TIME_INFO = "*🕖 Time:*\n%s\n";
    private static final String SLACK_REQUEST_INFO = "*🔗 Request URL:*\n%s\n";
    private static final String SLACK_MESSAGE_INFO = "*📝 Message:*\n```\n%s\n```";
    private static final String SLACK_STACK_TRACE_TITLE = "*📚 Stack Trace*";
    private static final String SLACK_STACK_TRACE_DESCRIPTION_FORMAT = "```\n%s\n```";

    @Override
    public SlackExceptionAdviceMessage createMessage(HttpServletRequest request, Exception exception) {
        SlackExceptionAdviceMessage message = new SlackExceptionAdviceMessage();
        message.addBlock(SlackHeader.plainText(SLACK_HEADER));
        message.addBlock(SlackSection.Text.mrkdwn(SLACK_SUB_HEADER));

        List<SlackField> timeAndRequest = new ArrayList<>();
        timeAndRequest.add(SlackField.mrkdwn(String.format(SLACK_TIME_INFO, LocalDateTime.now())));
        timeAndRequest.add(SlackField.mrkdwn(String.format(SLACK_REQUEST_INFO, createRequestFullPath(request))));
        message.addBlock(SlackSection.Fields.builder().fields(timeAndRequest).build());

        message.addBlock(SlackSection.Text.mrkdwn(String.format(SLACK_MESSAGE_INFO, getErrorMethodAndClass(exception))));
        message.addBlock(SlackDivider.getInstance());
        message.addBlock(SlackSection.Text.mrkdwn(SLACK_STACK_TRACE_TITLE));
        message.addBlock(SlackSection.Text.mrkdwn(String.format(SLACK_STACK_TRACE_DESCRIPTION_FORMAT, getStackTrace(exception).substring(0, 1000))));
        return message;
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
            return "Method: " + stackTrace.getMethodName() + "\nClass: " + stackTrace.getClassName();
        }
        return null;
    }
}
