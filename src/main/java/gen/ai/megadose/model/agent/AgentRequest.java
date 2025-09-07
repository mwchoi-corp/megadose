package gen.ai.megadose.model.agent;

import gen.ai.megadose.model.llm.LlmModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AgentRequest {
    private String prompt;
    private String systemPrompt;
    private LlmModel llmModel;
}
