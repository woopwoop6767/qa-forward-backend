package rf.subscribe.logic.services;

import io.qameta.allure.Step;
import rf.subscribe.logic.api.Specification;
import rf.subscribe.logic.pojo.categories.getCategories.CategoriesResponse;
import java.io.File;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;

public class CategoriesService implements Specification {

    @Step("I get categories")
    public CategoriesResponse getCategories() {
        return given()
                .spec(getRequestSpecification("leasing-content/v2/category/SUBSCRIBE-RF/categories"))
                .get()
                .then()
                .spec(getResponseSpecification(false))
                .body(matchesJsonSchema(new File("src/main/resources/jsonSchema/get_categories.json")))
                .extract().body().as(CategoriesResponse.class)
                ;
    }
}
