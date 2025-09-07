package gen.ai.megadose.service.tool;


import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class WeatherTools {
    @Tool(description = "현재 날씨 정보를 조회")
    public List<String> getWeather(String city) {
        log.info("city={}", city);
        return List.of("10도");
    }
}
