package com.example.pokejava.interfaz;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Path;

import com.example.pokejava.modelo.PokemonLista;

public interface PokemonApi {


    //@GET("pokemon/")
    //Call<PokemonLista> obtenerListaPokemon();
    @GET("pokemon/{a}/")
    Call<PokemonLista> obtenerListaPokemon(@Path("a")int a);
}
