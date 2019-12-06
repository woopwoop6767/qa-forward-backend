package rf.subscribe.logic.pojo.models.getModelsModelGoods;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ParamItem {

    @JsonProperty("name")
    private String name;

    @JsonProperty("type")
    private String type;

    @JsonProperty("value")
    private String value;

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return
                "ParamItem{" +
                        "name = '" + name + '\'' +
                        ",type = '" + type + '\'' +
                        ",value = '" + value + '\'' +
                        "}";
    }
}