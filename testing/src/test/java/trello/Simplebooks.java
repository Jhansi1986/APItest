package trello;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class Simplebooks {
	public static String BaseURL = "https://simple-books-api.glitch.me";
	
	@Test
	public void Authentication()
	{
		RestAssured.baseURI = BaseURL;
		
		String requestbody = "{\r\n"
				+ "   \"clientName\": \"jhansi\",\r\n"
				+ "   \"clientEmail\": \"jhansi@example.com\"\r\n"
				+ "}";
		Response respo = given()
		.header("Content-Type","application/json")
		.body(requestbody)
		
		.when()
		.post("/api-clients/")
		
		.then()
		.assertThat().statusCode(201)
		.extract().response();
		
		System.out.println(respo.asPrettyString());
		
		
	}

}
