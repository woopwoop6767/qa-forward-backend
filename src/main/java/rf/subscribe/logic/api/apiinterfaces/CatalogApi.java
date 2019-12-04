package rf.subscribe.logic.api.apiinterfaces;

import io.qameta.allure.Step;
import io.restassured.RestAssured;

import java.util.HashMap;

public class CatalogApi {
    private static CatalogApi catalogApi;

    public static CatalogApi getCatalogApi() {
        if (catalogApi == null) catalogApi = new CatalogApi();
        return catalogApi;
    }

    public CatalogApi getAllModels(String code) {
        RestAssured
                .given()
                .queryParam("categoryCode", code)
                .get("SUBSCRIBE-RF/models")
                .then().extract().body().as(HashMap.class);
        return this;
    }

    @Step("I get model name")
    public HashMap getModelItem(String model) {
        return RestAssured                .given()
                .get("SUBSCRIBE-RF/models/".concat(model).concat("/goods"))
                .then().extract().body().as(HashMap.class);
    }


}
