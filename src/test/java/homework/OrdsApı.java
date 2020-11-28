package homework;

import static io.restassured.RestAssured.*;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import java.util.ArrayList;
import java.util.List;

public class OrdsApı {

    @BeforeClass
    public void beforeclass(){
        baseURI = ConfigurationReader.get("hr_api_url");
    }
    /*
    - Given accept type is Json
    - Path param value- US
    - When users sends request to /countries
    - Then status code is 200
    - And Content - Type is Json
    - And country_id is US
    - And Country_name is United States of America
    - And Region_id is
     */
    @Test
    public void q1(){

        Response response = given().accept(ContentType.JSON)
                .pathParam("country_id", "US")
                .when().get("/countries/{country_id}");

        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json");
        // Verify all country_id equals US
        assertEquals(response.path("country_id"),"US");
        // Verify all Country name same with result
        assertEquals(response.path("country_name"),"United States of America");
        // Verify all region_id  equals 2
        int id=response.path("region_id");
        assertEquals(response.path("region_id"),(Integer)2);


    }
    /*
    - Given accept type is Json
- Query param value - q={"department_id":80}
- When users sends request to /employees
- Then status code is 200
- And Content - Type is Json
- And all job_ids start with 'SA'
- And all department_ids are 80
- Count is 25
     */
    @Test
    public void q2(){
        Response response = given().accept(ContentType.JSON)
                .queryParam("q", "{\"department_id\":80}")
                .when().get("/employees");

        JsonPath jsonPath = response.jsonPath();
        List<Integer> id = jsonPath.getList("items.department_id");
        for (Integer i : id) {
            assertEquals(i,(Integer)80);
        }
        List<String> jobIds = jsonPath.getList("items.job_id");
        for (String i : jobIds) {
            assertEquals(i.startsWith("SA"),true);
        }
        assertEquals(response.statusCode(),200);
        assertEquals(response.contentType(),"application/json");
        assertEquals(jsonPath.getInt("count"),25);
        
    }
    /*
    Q3:
- Given accept type is Json
-Query param value q= region_id 3
- When users sends request to /countries
- Then status code is 200
- And all regions_id is 3
- And count is 6
- And hasMore is false
- And Country_name are;
Australia,China,India,Japan,Malaysia,Singapore
     */
    @Test
    public void q3(){
        Response response = given().accept(ContentType.JSON)
                .queryParam("q", "{\"region_id\":3}")
                .when().get("/countries");
        assertEquals(response.statusCode(),200);
        JsonPath jsonPath = response.jsonPath();
        List<Integer> regionId = jsonPath.getList("items.region_id");
        for (Integer i : regionId) {
            assertEquals((int)i,3);
        }
        assertEquals(jsonPath.getInt("count"),6);
        assertEquals(jsonPath.getBoolean("hasMore"),false);
        List<String> countriesName = jsonPath.getList("items.country_name");
        System.out.println("countriesName = " + countriesName);
        List<String > expectedName= new ArrayList<>();
        expectedName.add("Australia");
        expectedName.add("China");
        expectedName.add("India");
        expectedName.add("Japan");
        expectedName.add("Malaysia");
        expectedName.add("Singapore");
        System.out.println("expectedName = " + expectedName);
        for (int i = 0; i < countriesName.size(); i++) {
            for (int j=0 ; j < expectedName.size() ; j ++) {
                assertEquals(countriesName.get(i),expectedName.get(j));
            }
        }

    }
}
