package rf.subscribe.logic.services;

import io.qameta.allure.Step;
import rf.subscribe.logic.api.Specification;
import rf.subscribe.logic.pojo.basketOnline.getBasketOnlineBasketId.BasketOnlineBasketIdResponse;
import rf.subscribe.logic.pojo.basketOnline.postBasketOnline.BasketOnlineResponse;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;

public class BasketOnlineService implements Specification {

    private String countModel = "1";

    private HashMap getBody(String goodCode) {
        return new HashMap() {{
            put("agentId", "null");
            put("basketData", new ArrayList<HashMap>() {{
                add(new HashMap() {{
                    put("count", countModel);
                    put("goodCode", goodCode);
                    put("tradeInfo", "null");
                }});
            }});
            put("channel", "SUBSCRIBE-RF");
            put("lang", "ru");
            put("partnerData", new HashMap() {{
                put("infoData", "{}");
            }});
            put("usid", UUID.randomUUID());
        }};
    }

    @Step("I post request basket-online with good code {goodCode}")
    public BasketOnlineResponse postBasketOnlineWithGoodCode(String goodCode) {
        return given()
                .spec(getRequestSpecification("leasing-basket/v1/basket-online/"))
                .body(getBody(goodCode))
                .post()
                .then()
                .spec(getResponseSpecification(false))
                .body(matchesJsonSchema(new File("src/main/resources/jsonSchema/post_v1_basket_online.json")))
                .extract().body().as(BasketOnlineResponse.class)
                ;
    }

    @Step("I get basket-online with {basketId}")
    public BasketOnlineBasketIdResponse getBasketOnlineWithBasketId(String basketId) {
        return given()
                .spec(getRequestSpecification("leasing-basket/v1/basket-online/".concat(basketId)))
                .get()
                .then()
                .spec(getResponseSpecification(false))
                .body(matchesJsonSchema(new File("src/main/resources/jsonSchema/get_v1_basket_online_{basketId}.json")))
                .extract().body().as(BasketOnlineBasketIdResponse.class)
                ;
    }
}
