package trello;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class trellodatadriven {

	public static String baseurl = "https://api.trello.com";
	public static String key = "2d08d37589e5a76be1aa9073fb829395";
	public static String token = "ATTAdf39ac3486ac164d1d9c99fc6b656dd0f2cb1b28e66f6c41f93f7007ba073e59E6E48E85";
	public static String ID;
	public static int rowcount;
	public static String boardname, color, desc;
	
	@Test
	
	public void datadriven() throws IOException 
	{
	File excel = new File("C:\\Users\\jhans\\Documents\\workspace-spring-tool-suite-4-4.19.1.RELEASE\\Restassuredapi\\Testdata.xlsx");
	
	FileInputStream fls = new FileInputStream(excel);
	
	XSSFWorkbook wb = new XSSFWorkbook(fls);
	
	XSSFSheet sh = wb.getSheet("Sheet1");
	
	rowcount = 	sh.getLastRowNum();
	for (int i=1; i<=rowcount; i++)
			{
		boardname = sh.getRow(i).getCell(0).getStringCellValue();
		System.out.println(boardname);
		color = sh.getRow(i).getCell(1).getStringCellValue();
		System.out.println(color);
		desc =sh.getRow(i).getCell(2).getStringCellValue();
		System.out.println(desc);
		create_a_board();
		update_a_board();
		get_a_board();
		delete_a_board();
		
		
			}
	}
	
	@Test(priority = 0, enabled = false)
	public void create_a_board()
	{
		RestAssured.baseURI = baseurl;
		
	Response respo = given()
	
		.queryParam("key", key)
		.queryParam("token", token)
		.queryParam("name", boardname)
		.header("content-Type","application/json")
		
		.when()
		.post("/1/boards/")
		
		.then()
		.assertThat().statusCode(200)
		
		.extract().response();
//	String responsejson = respo.asString()
	JsonPath js = new JsonPath(respo.asString());
	ID = js.get("id");
    System.out.println(respo.asPrettyString());
		
	}
	
	@Test(priority = 1, enabled = false)
	public void get_a_board()
	{
		RestAssured.baseURI = baseurl;
		
		Response respo = given()
	
		.queryParam("key", key)
		.queryParam("token", token)
		.header("content-Type","application/json")
		
		.when()
		.get("/1/boards/"+ID)
		
		.then()
		.assertThat().statusCode(200)
		.extract().response();
		
		System.out.println(respo.asPrettyString());
	}
	
	@Test(priority = 2, enabled = false)
	public void update_a_board()
	{
		RestAssured.baseURI = baseurl;
		
		Response respo = given()
	
		.queryParam("key", key)
		.queryParam("token", token)
		.queryParam("name", boardname)
		.queryParam("prefs/background", color)
		.queryParam("desc", desc)
		.header("content-Type","application/json")
		
		.when()
		.put("/1/boards/"+ID)
		
		.then()
		.assertThat().statusCode(200)
		.extract().response();
		System.out.println(respo.asPrettyString());
	}

	@Test(priority = 3, enabled = false)
	
	public void delete_a_board()
	{
		RestAssured.baseURI = baseurl;
		Response respo = given()
				
				.queryParam("key", key)
				.queryParam("token", token)
				.header("content-Type","application/json")
				
				.when()
				.delete("/1/boards/"+ID)
				
				.then()
				.assertThat().statusCode(200)
				.extract().response();
		System.out.println(respo.asPrettyString());
}
}
