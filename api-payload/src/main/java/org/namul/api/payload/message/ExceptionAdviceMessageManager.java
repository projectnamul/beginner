package org.namul.api.payload.message;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.namul.api.payload.message.generator.ExceptionAdviceMessageGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * The message manager that manage sending message to another platform. Send message to other platforms by using sender and message generator
 */
@Getter
public class ExceptionAdviceMessageManager {

    private final List<ExceptionAdviceMessageSenderRegistry<?>> senders = new ArrayList<>();

    /**
     * Add message sender and generator
     * @param sender The message sender that send to another platform.
     * @param generator The message generator that generate message will be sent
     * @param <T> The type of ExceptionAdviceMessage
     */
    public final <T extends ExceptionAdviceMessage> void addSender(ExceptionAdviceMessageSender<T> sender, ExceptionAdviceMessageGenerator<T> generator) {
        senders.add(new ExceptionAdviceMessageSenderRegistry<>(sender, generator));
    }

    /**
     * Send message about exception with each sender and generator
     * @param request The HttpServletRequest to generate message.
     * @param cls The class that register in properties as scope
     * @param e The exception class that occurs actually
     */
    public void sendMessage(HttpServletRequest request, Class<? extends Exception> cls, Exception e) {
        for (ExceptionAdviceMessageSenderRegistry<?> registry : senders) {
            registry.sendMessage(request, cls, e);
        }
    }

    /**
     * The registry contains sender and generator
     * @param <T> The type of ExceptionAdviceMessage
     */
    @Getter
    @RequiredArgsConstructor
    private static class ExceptionAdviceMessageSenderRegistry<T extends ExceptionAdviceMessage> {
        private final ExceptionAdviceMessageSender<T> sender;
        private final ExceptionAdviceMessageGenerator<T> generator;

        public <E extends Exception> void sendMessage(HttpServletRequest request, Class<E> cls, Exception e) {
            if (this.sender.isEnable(cls)) {
                this.sender.sendMessage(this.generator.createMessage(request, e));
            }
        }
    }
}
