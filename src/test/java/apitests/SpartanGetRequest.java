package apitests;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;


import static io.restassured.RestAssured.*;
public class SpartanGetRequest {

    String spartanUrl= "http://18.212.66.90:8000";

    @Test
    public void test1(){
        Response response = when().get(spartanUrl + "/api/spartans");
        System.out.println(response.statusCode());
        response.prettyPrint();

    }
    /* TASK
        When users sends a get request to /api/spartans/3
        Then status code should be 200
        And content type should be application/json;charset=UTF-8
        and json body should contain Fidole
     */
    @Test
    public void test2(){
       // when().get(spartanUrl+"/api/spartans/3").then()
       //         .assertThat().statusCode(200).and()
       //         .contentType("application/json;charset=UTF-8");


        Response response = when().get(spartanUrl + "/api/spartans/3");
        //verify status code
        Assert.assertEquals(response.statusCode(),200);
        //verify content type
        Assert.assertEquals(response.contentType(),"application/json;charset=UTF-8");
        //verify Fidole
        Assert.assertTrue(response.body().asString().contains("Fidole"));

    }

    /*
    Given no headers provided
    When Users sends GET request to /api/hello
    Then response status code should be 200
    And Content type header should be "text/plain;charset=UTF-8"
    And header should contain date
    And Content-Length should be 17
    And body should be "Hello from Sparta"
    */
    @Test
    public void test3(){
        Response response = when().get(spartanUrl + "/api/hello");
        //verify status code
        Assert.assertEquals(response.statusCode(),200);
        //verify content type
        Assert.assertEquals(response.contentType(),"text/plain;charset=UTF-8");
        //verify we have headers named date
        Assert.assertTrue(response.headers().hasHeaderWithName("Date"));
        // different
        Assert.assertTrue(response.headers().hasHeaderWithName("Content-Type"));
        //to get and header passing as a key

        System.out.println(response.header("Content-Length"));
        System.out.println(response.header("Date"));

        //verify content length is 17
        Assert.assertEquals(response.header("Content-Length"),"17");

        Assert.assertTrue(response.body().asString().contains("Hello from Sparta"));


    }
}
