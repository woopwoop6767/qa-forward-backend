package rf.subscribe.logic.services;

import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;
import rf.subscribe.logic.api.Specification;
import rf.subscribe.logic.pojo.leasingApplication.postLeasingApplication.LeasingApplicationResponse;
import rf.subscribe.logic.pojo.leasingApplication.postLeasingApplicationApplicationIdCheckConsentsOtp.CheckConsentsOtpResponse;
import rf.subscribe.logic.pojo.leasingApplication.postLeasingApplicationApplicationClientData.ClientDataResponse;

import java.io.File;
import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;

public class LeasingApplicationService implements Specification {

    private String mobileCode = "1111";

    private HashMap getBodyForApplicationId(String basketId, String typeApplication) {
        return new HashMap() {{
            put("agreementSimpleSign", "true");
            put("basketId", basketId);
            put("email", RandomStringUtils.randomAlphabetic(10).concat("@test.test"));
            put("mobilePhone", "9".concat(RandomStringUtils.randomNumeric(9)));
            put("previousApplicationId", null);
            put("type", typeApplication);
        }};
    }

    private HashMap getBodyForAuthToken() {
        return new HashMap() {{
            put("otp", mobileCode);
        }};
    }

    private HashMap getBodyForClientData() {
        return new HashMap() {{
            put("clientInfo", new HashMap() {{
                put("birthDate", "1972-07-23");
                put("birthPlace", "С. ФРОНТОВКА ОРАТОВСКОГО Р-НА ВИННИЦКОЙ ОБЛ.");
                put("documentType", "PASSPORT");
                put("firstName", "СЕРГЕЙ");
                put("hasMiddleName", true);
                put("issueDate", "2015-12-18");
                put("issuer", "ОТДЕЛ УФМС РОССИИ ПО РЕСПУБЛИКЕ КРЫМ В КИЕВСКОМ РАЙОНЕ Г. СИМФЕРОПОЛЯ");
                put("issuerCode", "910-003");
                put("lastName", "ЗДРИЛЮК");
                put("patronymicName", "АНАТОЛЬЕВИЧ");
                put("registrationAddress", new HashMap() {{
                    put("blockNum", null);
                    put("buildingNum", null);
                    put("district", null);
                    put("districtCode", "000");
                    put("flatNum", "3");
                    put("locality", "Авиационный");
                    put("localityCode", "000");
                    put("localityType", null);
                    put("region", "Московская");
                    put("regionCode", "50");
                    put("street", "Королева");
                    put("streetCode", "0784");
                    put("streetNum", "10");
                    put("taxCode", "142007");
                    put("town", "Домодедово");
                    put("townCode", "001");
                }});
                put("sex", "MALE");
            }});
            put("monthlySalary", "70000");
            put("occupation", "COMMERCIAL_EMPLOYEE");
        }};
    }

    @Step("I post request leasing-application ")
    public LeasingApplicationResponse postLeasingApplication(String basketId, String typeApplication) {
        return given()
                .spec(getRequestSpecification("/application/v2/leasing-application/"))
                .header("device-type", "WEB")
                .body(getBodyForApplicationId(basketId, typeApplication))
                .post()
                .then()
                .spec(getResponseSpecification(false))
                .body(matchesJsonSchema(new File("src/main/resources/jsonSchema/post_v2_leasing_application.json")))
                .extract().body().as(LeasingApplicationResponse.class)
                ;
    }

    @Step("I post leasing application check consents Otp")
    public CheckConsentsOtpResponse postLeasingAppCheckConsentsOtp(String applicationId) {
        return given()
                .spec(getRequestSpecification("/application/v2/leasing-application/".concat(applicationId).concat("/check-consents-otp")))
                .header("device-type", "WEB")
                .body(getBodyForAuthToken())
                .post()
                .then()
                .spec(getResponseSpecification(false))
                .body(matchesJsonSchema(new File("src/main/resources/jsonSchema/post_application_v2_leasing_application_{applicationID}_check_consents_otp.json")))
                .extract().body().as(CheckConsentsOtpResponse.class)
                ;
    }

    @Step("I post leasing application client data")
    public ClientDataResponse postLeasingAppClientData(String applicationId, String authToken) {
        return given()
                .spec(getRequestSpecification("/application/v2/leasing-application/".concat(applicationId).concat("/client-data")))
                .header("authorization", "Bearer " + authToken)
                .body(getBodyForClientData())
                .post()
                .then()
                .spec(getResponseSpecification(false))
                .body(matchesJsonSchema(new File("src/main/resources/jsonSchema/post_application_v2_leasing_application_{applicationID}_client_data.json")))
                .extract().body().as(ClientDataResponse.class)
                ;
    }

}
