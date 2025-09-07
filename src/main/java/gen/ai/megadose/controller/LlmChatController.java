package gen.ai.megadose.controller;


import gen.ai.megadose.model.llm.LlmChatRequest;
import gen.ai.megadose.model.llm.LlmChatResponse;
import gen.ai.megadose.service.LlmChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/llm/chat")
@RequiredArgsConstructor
public class LlmChatController {
    private final LlmChatService llmChatService;

    @PostMapping("/oneshot")
    public Mono<LlmChatResponse> chatOneShot(@RequestBody LlmChatRequest llmChatRequest) {
        return llmChatService.chatCompletion(llmChatRequest);
    }
}
