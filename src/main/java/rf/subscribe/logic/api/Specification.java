package rf.subscribe.logic.api;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public interface Specification {

    default ResponseSpecification checkStatusCodeAndContentType() {
        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .build()
                ;
    }

    default RequestSpecification getRequestSpecification(String basePath) {
        return new RequestSpecBuilder()
                .addFilter(new AllureRestAssured())
                .setBaseUri("https://subscribe-rf-front-test.xn--d1aiavecq8cxb.xn--p1ai/")
                .setBasePath(basePath)
                .addHeader("Device-Type", "WEB")
                .setContentType(ContentType.JSON)
                .build()
                ;
    }
}
