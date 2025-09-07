package gen.ai.megadose.model.llm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class LlmChatResponse {
    private String title;
    private String llmResponse;
}
