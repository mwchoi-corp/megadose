package gen.ai.megadose.service;

import gen.ai.megadose.model.llm.LlmChatRequest;
import gen.ai.megadose.model.llm.LlmChatResponse;
import gen.ai.megadose.model.llm.LlmModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LlmChatService {
    private final OpenAiChatModel openAiChatModel;

    public Mono<LlmChatResponse> chatCompletion(LlmChatRequest llmChatRequest) {
        ChatClient.ChatClientRequestSpec requestSpec = getChatClient(llmChatRequest.getLlmModel())
                .prompt(llmChatRequest.getUserRequest());

        if (llmChatRequest.getSystemPrompt() != null && !llmChatRequest.getSystemPrompt().isEmpty()) {
            requestSpec.system(llmChatRequest.getSystemPrompt());
        }

        Mono<List<String>> listMono = requestSpec
                .stream()
                .content()
                .collectList()
                //.doOnNext(strings -> log.info("llm res={}", strings.toString()))
                ;

        return listMono.flatMap(res -> Mono.just(new LlmChatResponse("", String.join("", res))));
    }

    private ChatClient getChatClient(LlmModel llmModel) {
        return switch (llmModel.getType()) {
            case GPT -> ChatClient.create(openAiChatModel);
        };
    }
}
