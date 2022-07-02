package com.example.cocktailsrecipe;


import com.example.cocktailsrecipe.Models.CocktailsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

// https://www.thecocktaildb.com/api/json/v1/1/search.php?s=margarita
public interface CocktailsAPI {
    @GET("v1/{apiKey}/search.php")
    Call<CocktailsResponse> searchCocktails( @Path("apiKey") String apiKey, @Query("s") String s);

    @GET("v1/1/random.php")
    Call<CocktailsResponse> getRandomCocktail();
}
