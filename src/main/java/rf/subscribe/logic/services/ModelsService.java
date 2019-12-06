package rf.subscribe.logic.services;

import io.qameta.allure.Step;
import rf.subscribe.logic.api.Specification;
import rf.subscribe.logic.pojo.models.getModels.ModelsResponse;
import rf.subscribe.logic.pojo.models.getModelsModelGoods.ModelsModelGoodsResponse;

import java.io.File;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;

public class ModelsService implements Specification {


    @Step("I get models")
    public ModelsResponse getModels(String code) {
        return given()
                .spec(getRequestSpecification("leasing-content/v2/catalog/SUBSCRIBE-RF/models/"))
                .queryParam("categoryCode", code)
                .get()
                .then()
                .spec(getResponseSpecification(false))
                .body(matchesJsonSchema(new File("src/main/resources/jsonSchema/get_models.json")))
                .extract().body().as(ModelsResponse.class)
                ;
    }

    @Step("I get models goods")
    public ModelsModelGoodsResponse getGoodCode(String modelCode) {
        return given()
                .spec(getRequestSpecification("leasing-content/v2/catalog/SUBSCRIBE-RF/models/".concat(modelCode).concat("/goods")))
                .get()
                .then()
                .spec(getResponseSpecification(false))
                .body(matchesJsonSchema(new File("src/main/resources/jsonSchema/get_models_{models}_goods.json")))
                .extract().body().as(ModelsModelGoodsResponse.class)
                ;
    }
}
