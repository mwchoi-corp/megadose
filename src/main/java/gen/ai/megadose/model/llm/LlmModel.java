package gen.ai.megadose.model.llm;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum LlmModel {
    GPT_4O("gpt-4o", LlmType.GPT)
    ;

    private final String name;
    private final LlmType type;

    @JsonValue
    @Override
    public String toString() {
        return name;
    }
}
