package rf.subscribe.tests.checkout;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.Test;
import rf.subscribe.logic.Data;
import rf.subscribe.logic.api.Specification;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;

public class CheckOrder extends Data implements Specification {

    private String code;
    private String modelCode;
    private String goodCode;
    private String ussid = UUID.randomUUID().toString();
    private String countModel = "1";
    private String basketId;
    private String applicationID;
    private String typeApplication;
    private String email = RandomStringUtils.randomAlphabetic(10).concat("@test.test");
    private String mobilePhone = "9".concat(RandomStringUtils.randomNumeric(9));
    private String mobileCode = "1111";
    private String authToken;

    @Test
    void getCategoriesCode() {
        code = given()
                .spec(getRequestSpecification("leasing-content/v2/category/SUBSCRIBE-RF/categories"))
                .get()
                .then()
                .spec(checkStatusCodeAndContentType())
                .body(matchesJsonSchema(new File(pathJsonSchema.concat("get_categories.json"))))
                .extract().body().jsonPath().getString("resultData.categories.childs[0].code[0]")
        ;
    }

    @Test(dependsOnMethods = {"getCategoriesCode"})
    void getModelCode() {
        modelCode = given()
                .spec(getRequestSpecification("leasing-content/v2/catalog/SUBSCRIBE-RF/models"))
                .queryParam("categoryCode", code)
                .get()
                .then()
                .spec(checkStatusCodeAndContentType())
                .body(matchesJsonSchema(new File(pathJsonSchema.concat("get_models.json"))))
                .extract().body().jsonPath().getString("resultData.models[0].code")
        ;
    }

    @Test(dependsOnMethods = {"getModelCode"})
    void getGoodCode() {
        goodCode = given()
                .spec(getRequestSpecification("leasing-content/v2/catalog/SUBSCRIBE-RF/models/".concat(modelCode).concat("/goods")))
                .get()
                .then()
                .spec(checkStatusCodeAndContentType())
                .body(matchesJsonSchema(new File(pathJsonSchema.concat("get_models_{models}_goods.json"))))
                .extract().body().jsonPath().getString("resultData.goods[3].code")
        ;
    }

    @Test(dependsOnMethods = {"getGoodCode"})
    void getBasketId() {
        basketId = given()
                .spec(getRequestSpecification(pathLeasingBasket))
                .body((new HashMap<String, Object>() {{
                    put("agentId", "null");
                    put("basketData", new ArrayList<HashMap<String, Object>>() {{
                        add(new HashMap<String, Object>() {{
                            put("count", countModel);
                            put("goodCode", goodCode);
                            put("tradeInfo", "null");
                        }});
                    }});
                    put("channel", "SUBSCRIBE-RF");
                    put("lang", "ru");
                    put("partnerData", new HashMap<String, Object>() {{
                        put("infoData", "{}");
                    }});
                    put("usid", ussid);
                }}))
                .post()
                .then()
                .spec(checkStatusCodeAndContentType())
                .body(matchesJsonSchema(new File(pathJsonSchema.concat("post_v1_basket_online.json"))))
                .extract().body().jsonPath().getString("resultData.basketId")
        ;
    }

    @Test(dependsOnMethods = {"getBasketId"})
    void getTypeApplication() {
        typeApplication = given()
                .spec(getRequestSpecification(pathLeasingBasket.concat(basketId)))
                .get()
                .then()
                .spec(checkStatusCodeAndContentType())
                .body(matchesJsonSchema(new File(pathJsonSchema.concat("get_v1_basket_online_{basketId}.json"))))
                .extract().body().jsonPath().getString("resultData.basketInfo.status")
        ;
    }

    @Test(dependsOnMethods = {"getTypeApplication"})
    void getApplicationId() {
        applicationID = given()
                .spec(getRequestSpecification(pathAppLeasing))
                .body(new HashMap<String, Object>() {{
                    put("agreementSimpleSign", "true");
                    put("basketId", basketId);
                    put("email", email);
                    put("mobilePhone", mobilePhone);
                    put("previousApplicationId", null);
                    put("type", typeApplication);
                }})
                .post()
                .then()
                .spec(checkStatusCodeAndContentType())
                .body(matchesJsonSchema(new File(pathJsonSchema.concat("post_v2_leasing_application.json"))))
                .extract().body().jsonPath().getString("resultData.applicationID")
        ;
    }

    @Test(dependsOnMethods = {"getApplicationId"})
    void postConsentsOtp() {
        authToken = given()
                .spec(getRequestSpecification(pathAppLeasing.concat(applicationID).concat("/check-consents-otp")))
                .body(new HashMap<String, Object>() {{
                    put("otp", mobileCode);
                }})
                .post()
                .then()
                .spec(checkStatusCodeAndContentType())
                .body(matchesJsonSchema
                        (new File(pathJsonSchema.concat("/post_application_v2_leasing_application_{applicationID}_check_consents_otp.json"))))
                .extract().body().jsonPath().getString("resultData.token")
        ;
    }

    @Test(dependsOnMethods = {"postConsentsOtp"})
    void postUploadPhoto() {
        given()
                .spec(getRequestSpecification(pathAppLeasing.concat("upload/").concat(applicationID).concat("/photo")))
                .header("authorization", "Bearer " + authToken)
                .header("device-type", "WEB")
                .body(new HashMap<String, Object>() {{
                    try {
                        put("imageData", new String(Base64.getEncoder().encode(FileUtils.readFileToByteArray(new File("src/main/resources/pass.jpeg")))));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    put("imageType", "PASSPORT_RECOGNIZE");
                }})
                .post()
                .then()
                .spec(checkStatusCodeAndContentType())
                .body(matchesJsonSchema(new File(pathJsonSchema.concat("post_application_v2_leasing_application_upload_{applicationID}_photo.json"))))
        ;
    }

    @Test(dependsOnMethods = {"postUploadPhoto"})
    void postUploadSelfie() {
        given()
                .spec(getRequestSpecification(pathAppLeasing.concat("upload/").concat(applicationID).concat("/photo")))
                .header("authorization", "Bearer " + authToken)
                .header("device-type", "WEB")
                .body(new HashMap<String, Object>() {{
                    put("imageType", "SELFIE");
                    try {
                        put("imageData", new String(Base64.getEncoder().encode(FileUtils.readFileToByteArray(new File("src/main/resources/pass.jpeg")))));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    put("imageType", "SELFIE");
                }})
                .post()
                .then()
                .spec(checkStatusCodeAndContentType())
                .body(matchesJsonSchema(new File(pathJsonSchema.concat("post_application_v2_leasing_application_upload_{applicationID}_photo.json"))))
        ;
    }

    @Test(dependsOnMethods = {"postUploadSelfie"})
    void postClientData() {
        given()
                .spec(getRequestSpecification(pathAppLeasing.concat(applicationID).concat("/client-data")))
                .header("authorization", "Bearer " + authToken)
                .body(clientData)
                .post()
                .then()
                .spec(checkStatusCodeAndContentType())
                .body(matchesJsonSchema(new File(pathJsonSchema.concat("post_application_v2_leasing_application_{applicationID}_client_data.json"))))
        ;
    }

}

