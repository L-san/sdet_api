package api;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import lombok.Builder;

import java.net.MalformedURLException;
import java.net.URL;

@Builder
public class PokemonAPI {

    @Step("GET-запрос к API")
    public String doGetByURL(String url, int statusCode) throws MalformedURLException {
        String BASE_URL = "https://pokeapi.co/api/v2/pokemon/";
        return RestAssured.when()
                .get(new URL(BASE_URL + url))
                .then().assertThat().statusCode(statusCode)
                .extract().asString();
    }

}
