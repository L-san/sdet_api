package api;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import lombok.Builder;

import java.net.MalformedURLException;
import java.net.URL;

@Builder
public class PokemonAPI {

    private final String BASE_URL = "https://pokeapi.co/api/v2/pokemon/";

    @Step("GET-запрос к API получение списка покемонов")
    public String doGetPokemon(int limit, int statusCode) throws MalformedURLException {
        return RestAssured.given()
                .queryParam("limit", limit)
                .when()
                .get(new URL(BASE_URL))
                .then().assertThat().statusCode(statusCode)
                .extract().asString();
    }

    @Step("GET-запрос к API поиск покемона по имени")
    public String doGetByPokemonName(String name, int statusCode) throws MalformedURLException {
        return RestAssured.when()
                .get(new URL(BASE_URL + name + "/"))
                .then().assertThat().statusCode(statusCode)
                .extract().asString();
    }

}
