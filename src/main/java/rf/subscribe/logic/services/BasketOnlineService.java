package rf.subscribe.logic.services;

import io.qameta.allure.Step;
import rf.subscribe.logic.BodyForTest;
import rf.subscribe.logic.api.Specification;
import rf.subscribe.logic.pojo.basketOnline.getBasketOnlineBasketId.BasketOnlineBasketIdResponse;
import rf.subscribe.logic.pojo.basketOnline.postBasketOnline.BasketOnlineResponse;

import java.io.File;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;

public class BasketOnlineService extends BodyForTest implements Specification {

    private BasketOnlineResponse basketOnlineResponse;
    private BasketOnlineBasketIdResponse basketOnlineBasketIdResponse;

    private BasketOnlineResponse postBasketOnlineWithGoodCode(String countModel, String goodCode) {
        if (basketOnlineResponse == null) {
            basketOnlineResponse = given()
                    .spec(getRequestSpecification("leasing-basket/v1/basket-online/"))
                    .body(getBodyBasketOnline(countModel, goodCode))
                    .post()
                    .then()
                    .spec(getResponseSpecification(false))
                    .body(matchesJsonSchema(new File("src/main/resources/jsonSchema/valid/post_v1_basket_online.json")))
                    .extract().body().as(BasketOnlineResponse.class)
            ;
        }
        return basketOnlineResponse;
    }


    private BasketOnlineBasketIdResponse getBasketOnlineWithBasketId(String basketId) {
        if (basketOnlineBasketIdResponse == null) {
            basketOnlineBasketIdResponse = given()
                    .spec(getRequestSpecification("leasing-basket/v1/basket-online/".concat(basketId)))
                    .get()
                    .then()
                    .spec(getResponseSpecification(false))
                    .body(matchesJsonSchema(new File("src/main/resources/jsonSchema/valid/get_v1_basket_online_{basketId}.json")))
                    .extract().body().as(BasketOnlineBasketIdResponse.class)
            ;
        }
        return basketOnlineBasketIdResponse;
    }

    @Step("I get basket ID")
    public String getBasketId(String countModel, String goodCode) {
        return postBasketOnlineWithGoodCode(countModel, goodCode)
                .getResultData()
                .getBasketId()
                ;
    }

    @Step("I get type application")
    public String getTypeApplication(String basketId) {
        return getBasketOnlineWithBasketId(basketId)
                .getResultData()
                .getBasketInfo()
                .getStatus()
                ;
    }
}
