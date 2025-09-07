package gen.ai.megadose.model.agent;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class InputDataInfo {
    private List<String> keywords;
    private List<String> competitors;
    private List<String> reference_urls;
    private List<String> report_scope;
    private List<String> search_urls;
}
