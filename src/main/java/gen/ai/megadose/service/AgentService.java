package gen.ai.megadose.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gen.ai.megadose.model.agent.AgentRequest;
import gen.ai.megadose.model.agent.AgentResponse;
import gen.ai.megadose.model.agent.InputDataInfo;
import gen.ai.megadose.model.llm.LlmChatRequest;
import gen.ai.megadose.util.ChatUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
@Service
@RequiredArgsConstructor
public class AgentService {
    private final LlmChatService llmChatService;
    private final ObjectMapper objectMapper;

    public Flux<AgentResponse> callTechReportAgent(AgentRequest agentRequest) {
        return Flux.create(sink -> {
            /* 1단계 : 사용자 입력, 데이터 가공, 정보 수집 */
            String sysPrompt = String.format("""
                    다음 사용자의 입력을 기반으로 기술 분석 요청 정보를 JSON 형식으로 정제하세요. \s
                    누락된 필드는 `null`로 표시하고, 리스트 형태로 입력된 값은 배열로 변환하세요.
                    
                    입력:
                    ""\"
                    %s
                    ""\"
                    
                    정제된 JSON 형식:
                    ```json
                    {
                      "keywords": [ ... ],
                      "competitors": [ ... ],
                      "reference_urls": [ ... ],
                      "report_scope": [ ... ]
                    }
                    """, agentRequest.getPrompt());
            agentRequest.setSystemPrompt(sysPrompt);

            Mono<InputDataInfo> inputDataInfoMono = llmChatService.chatCompletion(new LlmChatRequest(agentRequest))
                    .handle((response, sink1) -> {
                        /* json output parse */
                        String llmResponse = response.getLlmResponse();
                        String extractJsonString = ChatUtils.extractJsonString(llmResponse);

                        try {
                            sink1.next(objectMapper.readValue(extractJsonString, InputDataInfo.class));
                        } catch (JsonProcessingException e) {
                            log.error("json parse error. llmResponse={}", llmResponse,e);
                            sink1.error(new RuntimeException(e));
                        }
                    })
//                    .doOnNext(inputDataInfo -> {
//                        log.info("[1단계] {}", inputDataInfo);
//                        sink.next(new AgentResponse("[1단계]", inputDataInfo.toString()));
//                    })
                    ;

            /* 경쟁사 URL (search_urls) 생성 */
            Mono<InputDataInfo> searchMono = inputDataInfoMono.flatMap(inputDataInfo -> {
                String searchUrlsPrompt = """
                        다음 JSON 입력을 기반으로, 기술 분석에 사용할 검색 대상 URL 목록 (`search_urls`)을 생성하세요.
                        
                        규칙:
                        1. `competitors`의 각 항목에 대해 공식 웹사이트를 추정하거나 일반적인 도메인을 기반으로 `https://{company}.com` 또는 검색을 위한 Google Search URL을 생성합니다.
                        2. `reference_urls` 항목은 그대로 포함합니다.
                        3. 중복되는 URL은 제거하세요.
                        4. 결과는 JSON의 사용자 입력에 `search_urls` 필드추가하여 배열로 정리합니다.
                        
                        출력 예시:
                        ```json
                        {
                          "keywords": [ ... ],
                          "competitors": [ ... ],
                          "reference_urls": [ ... ],
                          "report_scope": [ ... ],
                          "search_urls": [
                            "https://www.naver.com/",
                            "https://www.irobotnews.com/",
                            "https://www.google.com/search?q=Starship+Technologies+자율주행+순찰로봇",
                            "https://www.google.com/search?q=Kiwibot+자율주행+순찰로봇"
                          ]
                        }
                        ```
                        """;

                LlmChatRequest llmChatRequest = new LlmChatRequest(agentRequest);
                llmChatRequest.setSystemPrompt(searchUrlsPrompt);
                llmChatRequest.setUserRequest(inputDataInfo.toString());

                return llmChatService.chatCompletion(llmChatRequest);
            }).<InputDataInfo>handle((response, sink1) -> {
                /* json output parse */
                String llmResponse = response.getLlmResponse();
                String extractJsonString = ChatUtils.extractJsonString(llmResponse);

                try {
                    sink1.next(objectMapper.readValue(extractJsonString, InputDataInfo.class));
                } catch (JsonProcessingException e) {
                    log.error("json parse error. llmResponse={}", llmResponse,e);
                    sink1.error(new RuntimeException(e));
                }
            })
            .doOnNext(inputDataInfo -> {
                log.info("[1단계] {}", inputDataInfo);
                sink.next(new AgentResponse("[1단계]", inputDataInfo.toString()));
            });


            /* 2단계 - 분석 및 1차 필터링 */


            /* 3단계 - 중복 및 신규성 검증 */


            /* 4단계 - 브리핑 생성 및 저장 */


            /* 최종 응답 */
            searchMono.subscribeOn(Schedulers.boundedElastic()).subscribe(finalResponse -> {
                sink.next(new AgentResponse("[최종 응답]", finalResponse.toString()));
                sink.complete();
                log.info("[finalResponse] {}]", finalResponse);
            }, error -> {
                log.error("tech report response error", error);
                sink.error(error);
            });
        });
    }
}
