package org.namul.api.payload.message;

import lombok.Builder;
import lombok.Getter;
import org.namul.api.payload.message.format.SlackBlock;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class SlackExceptionAdviceMessage implements ExceptionAdviceMessage {

    private List<SlackBlock> blocks;

    public SlackExceptionAdviceMessage() {
        this.blocks = new ArrayList<>();
    }

    public SlackExceptionAdviceMessage(List<SlackBlock> blocks) {
        this.blocks = blocks;
    }

    public void addBlock(SlackBlock block) {
        this.blocks.add(block);
    }
}
