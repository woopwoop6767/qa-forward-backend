package rf.subscribe.logic.services;

import io.qameta.allure.Step;
import org.apache.commons.io.FileUtils;
import rf.subscribe.logic.api.Specification;
import rf.subscribe.logic.pojo.uploadPhoto.noValid.UploadPhotoNoValidResponse;
import rf.subscribe.logic.pojo.uploadPhoto.postUploadPhoto.UploadPhotoResponse;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;

public class UploadPhotoService implements Specification {

    private HashMap getBodyPassport() {
        return new HashMap() {{
            try {
                put("imageData", new String(Base64.getEncoder().encode(FileUtils.readFileToByteArray
                        (new File("src/main/resources/pass.jpeg")))));
            } catch (IOException e) {
                e.printStackTrace();
            }
            put("imageType", "PASSPORT_RECOGNIZE");
        }};
    }

    private HashMap getBodyPicture() {
        return new HashMap() {{
            try {
                put("imageData", new String(Base64.getEncoder().encode(FileUtils.readFileToByteArray
                        (new File("src/main/resources/kartinka.jpg")))));
            } catch (IOException e) {
                e.printStackTrace();
            }
            put("imageType", "PASSPORT_RECOGNIZE");
        }};
    }

    private HashMap getBodySelfie() {
        return new HashMap<String, Object>() {{
            try {
                put("imageData", new String(Base64.getEncoder().encode(FileUtils.readFileToByteArray
                        (new File("src/main/resources/pass.jpeg")))));
            } catch (IOException e) {
                e.printStackTrace();
            }
            put("imageType", "SELFIE");
        }};
    }


    @Step("I post upload passport")
    public UploadPhotoResponse postUploadPassport(String applicationId, String authToken) {
        return given()
                .spec(getRequestSpecification("/application/v2/leasing-application/upload/".concat(applicationId).concat("/photo")))
                .header("authorization", "Bearer " + authToken)
                .body(getBodyPassport())
                .post()
                .then()
                .spec(getResponseSpecification(false))
                .body(matchesJsonSchema(new File("src/main/resources/jsonSchema/post_application_v2_leasing_application_upload_{applicationID}_photo.json")))
                .extract().body().as(UploadPhotoResponse.class)
                ;
    }

    @Step("I post upload picture instead passport")
    public UploadPhotoNoValidResponse postUploadPassportNoValid(String applicationId, String authToken) {
        return given()
                .spec(getRequestSpecification("/application/v2/leasing-application/upload/".concat(applicationId).concat("/photo")))
                .header("authorization", "Bearer " + authToken)
                .body(getBodyPicture())
                .post()
                .then()
                .spec(getResponseSpecification(true))
                .body(matchesJsonSchema(new File("src/main/resources/jsonSchema/noValid/post_application_v2_leasing_application_upload_{applicationID}_photo.json")))
                .extract().body().as(UploadPhotoNoValidResponse.class)
                ;
    }

    @Step("I post upload selfie")
    public UploadPhotoResponse postUploadSelfie(String applicationId, String authToken) {
        return given()
                .spec(getRequestSpecification("/application/v2/leasing-application/upload/".concat(applicationId).concat("/photo")))
                .header("authorization", "Bearer " + authToken)
                .body(getBodySelfie())
                .post()
                .then()
                .spec(getResponseSpecification(false))
                .body(matchesJsonSchema(new File("src/main/resources/jsonSchema/post_application_v2_leasing_application_upload_{applicationID}_photo.json")))
                .extract().body().as(UploadPhotoResponse.class)
                ;
    }

}
