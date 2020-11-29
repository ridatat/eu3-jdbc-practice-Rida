package apitests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import static io.restassured.RestAssured.*;
import  static org.hamcrest.Matchers.*;

public class HamcrestMatchersApiTest_day5_2 {
    /*
      given accept type is Json
      And path param id is 15
      When user sends a get request to spartans/{id}
      Then status code is 200
      And content type is Json
      And json data has following
          "id": 15,
          "name": "Meta",
          "gender": "Female",
          "phone": 1938695106
       */
    @Test
    protected void hi(){
        given().accept(ContentType.JSON)
                .pathParam("id", 15)
                .when().get("http://54.91.205.197:8000/api/spartans/{id}")
                .then().statusCode(200)
                .and().contentType("application/json")
                .and().body("id",equalTo(15),
                "name",equalTo("Meta"),
                                    "gender",equalTo("Female"),
                                     "phone",equalTo(1938695106));
    }

    @Test
    public void OneSpartanWithHamcrest(){
        given().accept(ContentType.JSON)
                .and().pathParam("id",15).
                when().get("http://54.91.205.197:8000/api/spartans/{id}")
                .then().statusCode(200)
                .and().assertThat().contentType("application/json;charset=UTF-8")
                .and().assertThat().body("id",equalTo(15),
                "name",equalTo("Meta"),
                                     "gender",equalTo("Female"),
                                      "phone",equalTo(1938695106));


    }
    /*
    status code 200
    content type application/json;charset=UTF-8
    content-length is must be 240
    connection is Keep-Alive
    and header(Date) is there ?
    following options is match
        -first name is James assert
        -last name is Bond
        -gender is Male

    */
    @Test
    public void teacherData(){
        given().accept(ContentType.JSON)
                .and().pathParam("id",8261)
                .when().log().all().get("http://api.cybertektraining.com/teacher/{id}")
                .then().assertThat().statusCode(200)
                .and().contentType(equalTo("application/json;charset=UTF-8"))
                .and().header("Content-Length",equalTo("240"))
                .and().header("Connection",equalTo("Keep-Alive"))
                .and().header("Date",notNullValue())
                .and().assertThat().body("teachers.firstName[0]",equalTo("James"),
                "teachers.lastName[0]",equalTo("Bond"),
                                    "teachers.gender[0]",equalTo("Male"))
                .log().all();
    }
    @Test //more than one value verify example
    public void teacherwithdepartments(){
        given().accept(ContentType.JSON)
                .and().pathParam("name","Computer")
                .when().log().all().get("http://api.cybertektraining.com/teacher/department/{name}")
                .then().statusCode(200).and()
                .contentType(equalTo("application/json;charset=UTF-8")).and()
                .body("teachers.firstName",hasItems("Alexander","Marteen"));

    }
}