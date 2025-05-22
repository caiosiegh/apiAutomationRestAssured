package test;

import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperDeserializationContext;
import io.restassured.mapper.ObjectMapperSerializationContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.core.type.TypeReference;

public class automationPratice {

    @Test
    public void getAllProductsList() {
        given()
                .get("https://automationexercise.com/api/productsList")
                .then().log().all().statusCode(200);
    }

    @Test
    public void postToAllProductsList() {
        given()
                .post("https://automationexercise.com/api/productsList")
                .then().log().all().body(containsString("responseCode")).body(containsString("405"));
    }

    @Test
    public void getAllBrandsList() {
        given()
                .get("https://automationexercise.com/api/brandsList")
                .then().log().all().body(containsString("brands")).statusCode(200);
    }

    @Test
    public void putToAllBrandsList() {
        given()
                .put("https://automationexercise.com/api/brandsList")
                .then().log().all().body(containsString("responseCode")).body(containsString("405"))
                .body(containsString("This request method is not supported."));
    }

    @Test
    public void postToSearchProduct() {
        given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("search_product", "jean")
                .post("https://automationexercise.com/api/searchProduct")
                .then().log().all().statusCode(200);
    }

    @Test
    public void postToSearchProductWithoutParameter() {
        given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("no_search_product", "jean")
                .post("https://automationexercise.com/api/searchProduct")
                .then().log().all().body(containsString("responseCode"))
                .body(containsString("400"))
                .body(containsString("search_product parameter is missing"));
    }

    @Test
    public void postToVerifyLoginWithValidDetails() {
        given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("email", "teste3@siegh.com")
                .formParam("password", "123456")
                .post("https://automationexercise.com/api/verifyLogin")
                .then().statusCode(200).log().all().body(containsString("responseCode")).body(containsString("200"))
                .body(containsString("User exists!"));
    }

    @Test
    public void postToVerifyLoginWithoutEmailParameter() {
        given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("password", "123456")
                .post("https://automationexercise.com/api/verifyLogin")
                .then().statusCode(200).log().all().body(containsString("responseCode")).body(containsString("400"))
                .body(containsString("Bad request, email or password parameter is missing in POST request."));
    }

    @Test
    public void deleteToVerifyLogin() {
        given()
                .delete("https://automationexercise.com/api/verifyLogin")
                .then().statusCode(200).log().all().body(containsString("responseCode")).body(containsString("405"))
                .body(containsString("This request method is not supported."));
    }

    @Test
    public void postToVerifyLoginWithInvalidDetails() {
        given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("email", "xumbreguinho@gmail.com")
                .formParam("password", "xablau123!")
                .post("https://automationexercise.com/api/verifyLogin")
                .then().statusCode(200).log().all().body(containsString("responseCode")).body(containsString("404"))
                .body(containsString("User not found!"));
    }

    @Test
    public void postToCreateRegisterUserAccount() {
        given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("name", "Jhon")
                .formParam("email", "xumbreguinho@gmail.com")
                .formParam("password", "Testing123!")
                .formParam("title", "Mr")
                .formParam("birth_date", "01")
                .formParam("birth_month", "02")
                .formParam("birth_year", "2001")
                .formParam("firstname", "Jhond")
                .formParam("lastname", "Dee")
                .formParam("company", "Emprego")
                .formParam("address1", "Rua tal")
                .formParam("address2", "Endereço tal 123")
                .formParam("country", "Brazil")
                .formParam("zipcode", "30147523")
                .formParam("state", "MG")
                .formParam("city", "BH")
                .formParam("mobile_number", "88888888")
                .post("https://automationexercise.com/api/createAccount")
                .then().statusCode(200).log().all().body(containsString("responseCode")).body(containsString("201"))
                .body(containsString("User created!"));
    }

    @Test
    public void deleteMethodtoDeleteUserAccount() {
        given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("email", "xumbreguinho@gmail.com")
                .formParam("password", "Testing123!")
                .delete("https://automationexercise.com/api/deleteAccount")
                .then().statusCode(200).log().all().body(containsString("responseCode")).body(containsString("200"))
                .body(containsString("Account deleted!"));
    }

    @Test
    public void putMethodToUpdateUserAccount() {
        given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("name", "Jhon")
                .formParam("email", "xumbreguinho@gmail.com")
                .formParam("password", "Testing123!")
                .formParam("title", "Mrs")
                .formParam("birth_date", "01")
                .formParam("birth_month", "02")
                .formParam("birth_year", "2001")
                .formParam("firstname", "Switch Name")
                .formParam("lastname", "Dee")
                .formParam("company", "Quero um emprego")
                .formParam("address1", "Rua tal")
                .formParam("address2", "Endereço tal 123")
                .formParam("country", "Brazil")
                .formParam("zipcode", "30147523")
                .formParam("state", "MG")
                .formParam("city", "BH")
                .formParam("mobile_number", "88888888")
                .put("https://automationexercise.com/api/updateAccount")
                .then().statusCode(200).log().all().body(containsString("responseCode")).body(containsString("200"))
                .body(containsString("User updated!"));
    }

    @Test
    public void getUserAccountDetailByEmail() {
        given()
                .contentType("application/x-www-form-urlencoded")
                .accept("application/json")
                .formParam("email", "xumbreguinho@gmail.com")
                .get("https://automationexercise.com/api/getUserDetailByEmail")
                .then().statusCode(200).log().body();
    }

    @Test
    public void postToCreateRegisterUserAccountPOJO() {

        Register register = new Register("testName", "testEmailSiegh2@outlook.com", "Teste123!", "Mr", 29, 8, 1987, "Siegh", "Wind", "YT", "Rua tal",
                "Bairro tal", "Brazil", "88888-963", "MG", "BH", "8888-8888");

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> params = mapper.convertValue(register, new TypeReference<>() {});


        given()
                .contentType("application/x-www-form-urlencoded")
                .formParams(params)
                .post("https://automationexercise.com/api/createAccount")
                .then().statusCode(200).log().all().body(containsString("responseCode")).body(containsString("201"))
                .body(containsString("User created!"));
    }
}
