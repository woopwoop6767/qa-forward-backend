package rf.subscribe.tests.checkout;

import io.restassured.RestAssured;
import org.testng.annotations.Test;
import rf.subscribe.logic.api.Specification;

import java.io.File;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;

public class CheckoutAPI implements Specification {
    private String code;
    private String nameModel;
    private String goodCode;


    @Test
    void getCategories() {
        code = RestAssured
                .given()
                .spec(getRequestSpecification())
                .basePath("leasing-content/v2/category/SUBSCRIBE-RF/categories")
                .get()
                .then()
                .assertThat().statusCode(200)
                .body(matchesJsonSchema(new File("src/main/resources/jsonSchema/get_categories.json")))
                .extract().body().jsonPath().getString("resultData.categories.childs[0].code").split(",")[0].substring(1)
        ;
    }

    @Test(dependsOnMethods = {"getCategories"})
    void getNameIphone() {
        nameModel = RestAssured
                .given()
                .spec(getRequestSpecification())
                .basePath("leasing-content/v2/catalog/SUBSCRIBE-RF/models")
                .queryParam("categoryCode", code)
                .get()
                .then()
                .assertThat().statusCode(200)
                .body(matchesJsonSchema(new File("src/main/resources/jsonSchema/get_models.json")))
                .extract().body().jsonPath().getString("resultData.models[0].code")
        ;
    }

    @Test(dependsOnMethods = {"getNameIphone"})
    void getModelIphone() {
        goodCode = RestAssured
                .given()
                .spec(getRequestSpecification())
                .basePath("leasing-content/v2/catalog/SUBSCRIBE-RF/models/".concat(nameModel).concat("/goods"))
                .get()
                .then()
                .assertThat().statusCode(200)
                .body(matchesJsonSchema(new File("src/main/resources/jsonSchema/get_models_apple_iPhone_11_goods.json")))
                .extract().body().jsonPath().getString("resultData.goods[3].code")
        ;
    }
}

