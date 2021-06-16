package com.example.pokejava;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.pokejava.interfaz.PokemonApi;
import com.example.pokejava.modelo.PokemonLista;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG= "Lol";
    CountDownTimer tiempo ;
    private Retrofit retrofit;
    private boolean bandera= true;


    private TextView nombre_pokemon;
   // private TextView url_pokemon;
    private ImageView foto_pokemon;
    public String nom_poke="";
    private Button btn_pokemon;
    private TextView crono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        crono = findViewById(R.id.txt_crono);

        obtenerPokemon();
        cuenta();





         btn_pokemon = findViewById(R.id.btn_pokemon);
         btn_pokemon.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 obtenerPokemon();
tiempo.cancel();
tiempo.start();

             }
         });






    }

    public void obtenerPokemon(){
        retrofit = new Retrofit.Builder().baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create()).build();


        PokemonApi service = retrofit.create(PokemonApi.class);
        int a= (int) (Math.random()*100);
        Call<PokemonLista> pokemonListaCall = service.obtenerListaPokemon(a);

        pokemonListaCall.enqueue(new Callback<PokemonLista>() {
            @Override
            public void onResponse(Call<PokemonLista> call, Response<PokemonLista> response) {
                if(response.isSuccessful())
                {
                    PokemonLista pokemonLista = response.body();
                    String nombre = pokemonLista.getName();
                    nom_poke = nombre;
                    Log.e(TAG,"Pokemon: " + nombre);

                    nombre_pokemon = findViewById(R.id.txt_pokemon);
                    nombre_pokemon.setText(nom_poke);

                    String url_foto= "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"+a+".png";
                    Log.e(TAG,"Pokemon foto: " + url_foto);
                   //url_pokemon = findViewById(R.id.txt_pokemon2);
                    //url_pokemon.setText(url_foto);

                    foto_pokemon= findViewById(R.id.foto_pokemon);
                    Glide.with(getApplication())
                            .load(url_foto).into(foto_pokemon);




                }else{
                    Log.e(TAG, "onResponse: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<PokemonLista> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
                Toast toast = Toast.makeText(getApplicationContext(),"Por favor, conéctate a Intenet", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

    }

    public void cuenta()
    {
        tiempo = new CountDownTimer(30000,1000){

            @Override
            public void onTick(long millisUntilFinished) {
                crono.setText("Nuevo pokemon en : " + millisUntilFinished/1000 + " segundos");
            }

            @Override
            public void onFinish() {
                crono.setText("Nuevo");
                obtenerPokemon();
                tiempo.start();
            }

        }.start();
    }

}