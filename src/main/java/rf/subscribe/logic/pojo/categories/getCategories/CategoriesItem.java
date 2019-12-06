package rf.subscribe.logic.pojo.categories.getCategories;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CategoriesItem{

	@JsonProperty("code")
	private String code;

	@JsonProperty("sortOrder")
	private int sortOrder;

	@JsonProperty("name")
	private String name;

	@JsonProperty("description")
	private Object description;

	@JsonProperty("childs")
	private List<ChildsItem> childs;

	@JsonProperty("isCatalog")
	private boolean isCatalog;

	public String getCode(){
		return code;
	}

	public int getSortOrder(){
		return sortOrder;
	}

	public String getName(){
		return name;
	}

	public Object getDescription(){
		return description;
	}

	public List<ChildsItem> getChilds(){
		return childs;
	}

	public boolean isIsCatalog(){
		return isCatalog;
	}

	@Override
 	public String toString(){
		return 
			"CategoriesItem{" + 
			"code = '" + code + '\'' + 
			",sortOrder = '" + sortOrder + '\'' + 
			",name = '" + name + '\'' + 
			",description = '" + description + '\'' + 
			",childs = '" + childs + '\'' + 
			",isCatalog = '" + isCatalog + '\'' + 
			"}";
		}
}