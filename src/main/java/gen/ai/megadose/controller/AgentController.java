package gen.ai.megadose.controller;


import gen.ai.megadose.model.agent.AgentRequest;
import gen.ai.megadose.model.agent.AgentResponse;
import gen.ai.megadose.model.llm.LlmModel;
import gen.ai.megadose.service.AgentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/agent")
@RequiredArgsConstructor
public class AgentController {
    private final AgentService agentService;

    @PostMapping("/report")
    public Flux<AgentResponse> call(@RequestBody AgentRequest agentRequest) {
        agentRequest.setLlmModel(LlmModel.GPT_4O);
        return agentService.callTechReportAgent(agentRequest);
    }
}
