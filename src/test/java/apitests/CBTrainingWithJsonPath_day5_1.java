package apitests;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;
import static org.testng.Assert.assertEquals;

public class CBTrainingWithJsonPath_day5_1 {
    @BeforeClass
    public void beforeclass(){
        baseURI= ConfigurationReader.get("cbt_api_url");
    }

    @Test
    public void test1_29_11_2020(){
        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", 17982)
                .when().get("/student/{id}");
        //verify status code
        assertEquals(response.statusCode(),200);

        //assign response to jsonpath
        JsonPath jsonPath = response.jsonPath();

        String firstName = jsonPath.getString("students.firstName[0]");
        System.out.println("firstName = " + firstName);
        assertEquals(firstName,"Vera");

        String lastName = jsonPath.getString("students.lastName[0]");
        System.out.println("lastName = " + lastName);
        assertEquals(lastName,"Jakson");

        String phone = jsonPath.getString("students.contact[0].phone");
        System.out.println("phone = " + phone);
        assertEquals(phone,"7738557985");

        String city = jsonPath.getString("students.company[0].address.city");
        System.out.println("City = " + city);
        assertEquals(city,"Chicago");

        int zipCode = jsonPath.getInt("students.company[0].address.zipCode");
        System.out.println("zipCode = " + zipCode);
        assertEquals(zipCode,60606);
    }
}
