package gen.ai.megadose.model.llm;

import gen.ai.megadose.model.agent.AgentRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LlmChatRequest {
    private String userRequest;
    private String systemPrompt;
    private boolean useJson;
    private LlmModel llmModel;
    private List<Map<String, Object>> tools = new ArrayList<>();

    public LlmChatRequest(AgentRequest agentRequest) {
        this.userRequest = agentRequest.getPrompt();
        this.systemPrompt = agentRequest.getSystemPrompt();
        this.useJson = true;
        this.llmModel = agentRequest.getLlmModel();
        // this.tools;
    }
}
