package trello;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;


import static io.restassured.RestAssured.given;

public class Simplebooks {
	public static String BaseURL = "https://simple-books-api.glitch.me";
	public static String Token;
	public static String ID;
	
	
	@Test(priority = 0)
	public void Authentication()
	
	{
		RestAssured.baseURI = BaseURL;
		
		String requestbody = "{\r\n"
				+ "   \"clientName\": \"train\",\r\n"
				+ "   \"clientEmail\": \"train@example.com\"\r\n"
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

		JsonPath js =new JsonPath(respo.asString());
		Token = js.get("accessToken");
	}
	
		@Test(priority = 1)
		public void create_an_order()
		{
			RestAssured.baseURI = BaseURL;
			String requestbody ="{\r\n"
					+ "  \"bookId\": 1,\r\n"
					+ "  \"customerName\": \"John\"\r\n"
					+ "}";
			
		Response respo = given()
				.header("Content-Type","application/json")
				.body(requestbody)
				.header("Authorization","Bearer "+Token)
				
				.when()
				.post("/orders")
				
				.then()
				.assertThat().statusCode(201)
				.extract().response();
		JsonPath js = new JsonPath(respo.asString());
		ID =js.get("id");
		System.out.println(respo.asPrettyString());
					
		}
		
		@Test(priority = 2)
		
		public void  get_a_book()
		{
	      RestAssured.baseURI = BaseURL;
	      
	      Response respo = given()
	    		  
	    		  .queryParam("token", Token)
	    		  .header("content-type","application/json")
	    		  
	    		  .when()
	    		  .get("/books/"+ ID)
	    		  
	    		  .then()
	    		  .assertThat().statusCode(200)
	    		  
	    		  .extract().response();
	      
	      System.out.println(respo.asPrettyString());
		}
		
		@Test(priority = 3)
		
		public void update_a_order() {
			RestAssured.baseURI = BaseURL;
			
			String requestbody="{\r\n"
					+ "  \"customerName\": \"John\"\r\n"
					+ "}";
			
			Response respo = given()
			.queryParam("token", Token)
			.header("content-Type","aapplication/json")
			.body(requestbody)
			
			.when()
			.patch("/orders/PF6MflPDcuhWobZcgmJy5")
			
			.then()
			.assertThat().statusCode(200)
			.extract().response();
			
			System.out.println(respo.asPrettyString());
			
			
		}
		
		@Test(priority = 4)
		
		public void delete_a_board()
		{
			RestAssured.baseURI = BaseURL;
			Response respo = given()
			
					.header("content-Type","application/json")
					
					.when()
					.delete("/orders/"+ID)
					
					.then()
					.assertThat().statusCode(200)
					.extract().response();
			System.out.println(respo.asPrettyString());
			
		}
		
		
	

}
