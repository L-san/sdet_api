package api;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import lombok.Builder;

import java.net.MalformedURLException;
import java.net.URL;

@Builder
public class PokemonAPI {
    private final RequestSpecification requestSpecification = RestAssured.given().filter(new AllureRestAssured()).baseUri("https://pokeapi.co/api/v2/pokemon/");

    @Step("GET-запрос к API получение списка покемонов")
    public String doGetPokemon(int limit, int statusCode) {
        return requestSpecification
                .queryParam("limit", limit)
                .when()
                .get()
                .then().assertThat().statusCode(statusCode)
                .extract().asString();
    }

    @Step("GET-запрос к API поиск покемона по имени")
    public String doGetByPokemonName(String name, int statusCode) {
        return requestSpecification
                .pathParam("name", name)
                .when()
                .get("{name}/")
                .then().assertThat().statusCode(statusCode)
                .extract().asString();
    }

}
