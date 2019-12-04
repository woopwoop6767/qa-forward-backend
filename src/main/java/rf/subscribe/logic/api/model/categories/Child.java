
package rf.subscribe.logic.api.model.categories;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name",
    "code",
    "description",
    "isCatalog",
    "childs",
    "sortOrder"
})
public class Child {

    @JsonProperty("name")
    private String name;
    @JsonProperty("code")
    private String code;
    @JsonProperty("description")
    private Object description;
    @JsonProperty("isCatalog")
    private Boolean isCatalog;
    @JsonProperty("childs")
    private List<Object> childs = null;
    @JsonProperty("sortOrder")
    private Integer sortOrder;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("code")
    public String getCode() {
        return code;
    }

    @JsonProperty("code")
    public void setCode(String code) {
        this.code = code;
    }

    @JsonProperty("description")
    public Object getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(Object description) {
        this.description = description;
    }

    @JsonProperty("isCatalog")
    public Boolean getIsCatalog() {
        return isCatalog;
    }

    @JsonProperty("isCatalog")
    public void setIsCatalog(Boolean isCatalog) {
        this.isCatalog = isCatalog;
    }

    @JsonProperty("childs")
    public List<Object> getChilds() {
        return childs;
    }

    @JsonProperty("childs")
    public void setChilds(List<Object> childs) {
        this.childs = childs;
    }

    @JsonProperty("sortOrder")
    public Integer getSortOrder() {
        return sortOrder;
    }

    @JsonProperty("sortOrder")
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
