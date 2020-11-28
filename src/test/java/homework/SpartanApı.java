package homework;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import static org.testng.Assert.*;
import static io.restassured.RestAssured.*;

public class SpartanApı {
    /*
    Given accept type is json
And path param id is 20
When user sends a get request to "/api/spartans/{id}"
Then status code is 200
And content-type is "application/json;charset=UTF-8"
And response header contains Date
And Transfer-Encoding is chunked
And response payload values match the following:
id is 20,
name is "Lothario",
gender is "Male",
phone is 7551551687
     */
    @BeforeClass
    public void beforeclass(){
        baseURI= ConfigurationReader.get("spartan_api_url");
    }
    @Test
    public void q1(){

            Response response = given().accept(ContentType.JSON)
                    .and().pathParam("id",20)
                    .when().get("/api/spartans/{id}");

            assertEquals(response.statusCode(),200);
            assertEquals(response.contentType(),"application/json;charset=UTF-8");
            // response header contains Date
            assertTrue(response.headers().hasHeaderWithName("Date"));
            //to see Date
            System.out.println("response.header(\"Date\") = " + response.header("Date"));
            // response header contains Date
            assertEquals(response.header("Transfer-Encoding"),"chunked");
            //to see chunked
            System.out.println("response.header(\"Transfer-Encoding\") = " + response.header("Transfer-Encoding"));
            // id is 20,
            assertEquals(response.path("id"),(Integer)20);
            //  name is "Lothario",
            assertEquals(response.path("name"),"Lothario");
            //  gender is "Male",
            assertEquals(response.path("gender"),"Male");
            //  phone is 7551551687
            long phoneNumber = response.path("phone");
            assertEquals(phoneNumber,7551551687l);


        }
        /*
        Given accept type is json
And query param gender = Female
And queary param nameContains = r
When user sends a get request to "/api/spartans/search"
Then status code is 200
And content-type is "application/json;charset=UTF-8"
And all genders are Female
And all names contains r
And size is 20
And totalPages is 1
And sorted is false
         */



}
