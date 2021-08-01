package jbcodeforce.app;

import static org.junit.Assert.assertThat;

import org.junit.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.text.IsEmptyString.emptyString;

@QuarkusTest
public class MeetingsEndpointTest {
    public static String ENDPOINT = "/api/v1/meetings"; 
    @Test
    public void testAllMeetings(){
        Response response = given()
                .when()
                .get(ENDPOINT)
                .then()
                .statusCode(200)
                .contentType("application/json")
                .extract().response();
        assertThat(response.jsonPath().getList("title")).containsExactlyInAnyOrder("First Meeting");
        
        given()
        .when()
        .body("{\"title\" : \"Meeting 2\"}")
        .contentType("application/json")
        .post(ENDPOINT)
        .then()
        .statusCode(201)
        .body(
                containsString("\"id\":"),
                containsString("\"title\":\"Meeting 2\""));
    }
}
