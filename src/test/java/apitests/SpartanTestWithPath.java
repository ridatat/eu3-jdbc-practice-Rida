package apitests;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

public class SpartanTestWithPath {
    @BeforeClass
    public void beforeClass(){
        baseURI="http://18.212.66.90:8000";
    }

    /*
   Given accept type is json
   And path param id is 10
   When user sends a get request to "api/spartans/{id}"
   Then status code is 200
   And content-type is "application/json;charset=UTF-8"
   And response payload values match the following:
           id is 10,
           name is "Lorenza",
           gender is "Female",
           phone is 3312820936
    */
    @Test
    public void getOneSpartanWith_path(){
        Response response = given().contentType(ContentType.JSON)
                .and().pathParam("id", 10)
                .when().get("/api/spartans/{id}");

        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json;charset=UTF-8");

        //I m getting from the json response this information
        //printing each key value in the json body/payload
        System.out.println(response.path("id").toString());
        System.out.println(response.path("name").toString());
        System.out.println(response.path("gender").toString());
        System.out.println(response.body().path("phone").toString());

        //save json key values
        int id = response.path("id");
        String name = response.path("name");
        String gender = response.path("gender");
        long phone = response.path("phone");

        //assert one by one
        assertEquals(id,10);
        assertEquals(name,"Lorenza");
        assertEquals(gender,"Female");
        assertEquals(phone,3312820936l);

        System.out.println("response.path(\"gender[1]\") = " + response.path("gender[1]"));

    }

    @Test
    public void getOneSpartanWith_path2(){
        Response response = given().contentType(ContentType.JSON)
                .when().get("/api/spartans");

        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json;charset=UTF-8");

        System.out.println("response.path(\"gender[0]\") = " + response.path("gender[1]"));

    }

    @Test
    public void getAllSpartanWithPath(){

        Response response = given().accept(ContentType.JSON)
                .when().get("/api/spartans");

        assertEquals(response.statusCode(),200);
        assertEquals(response.getHeader("Content-Type"),"application/json;charset=UTF-8");

        int firstId= response.path("id[0]");
        System.out.println("firstId = " + firstId);

        String firstName = response.path("name[0]");
        System.out.println("firstName = " + firstName);

        int lastId = response.path("id[-1]");
        System.out.println("lastId = " + lastId);


        //GPATH
        //print all name of spartans
        List<String> names = response.path("name");
        System.out.println("names = " + names);

        List<Object> phones = response.path("phone");
        for (Object phone : phones) {

            System.out.print(phone);
            System.out.print(",");
        }

    }


}
