package com.example.colocho.pokemonparse.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ParseException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.colocho.pokemonparse.GetImageCallBack;
import com.example.colocho.pokemonparse.Model.Pokemon;
import com.example.colocho.pokemonparse.Model.Type;
import com.example.colocho.pokemonparse.R;
import com.parse.GetCallback;
import com.parse.ParseObject;

public class PokemonDetailActivity extends AppCompatActivity {

    Pokemon pokemon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.activity_pokemon_detail);
        Intent intent = getIntent();

       String objectId = intent.getStringExtra("ObjectId");

        pokemon = (Pokemon) ParseObject.createWithoutData(Pokemon.class,objectId);

        /*try {
            pokemon.fetchIfNeededInBackground();
        }catch (ParseException e){

        }*/
        pokemon.fetchInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, com.parse.ParseException e) {
                if (e == null){
                    pokemon = (Pokemon)object;
                }else{
                    Toast.makeText(PokemonDetailActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
                refreshView();
            }
        });
        refreshView();

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    public void refreshView(){
        TextView text1 = (TextView) findViewById(R.id.textNombre);
        TextView text2 = (TextView) findViewById(R.id.textDesc);
        TextView text3 = (TextView) findViewById(R.id.typeTextView);
        final ImageView imagenView = (ImageView) findViewById(R.id.pokemonImagen);
        final ImageView imagenTypeView = (ImageView) findViewById(R.id.typeImageView);

        text1.setText(pokemon.getName());
        text2.setText(pokemon.getDescription());
        Type type = pokemon.getType();
        if(type != null){

            try {
                type.fetchIfNeeded();
                text3.setText(type.getName());
                type.getImage(new GetImageCallBack<Bitmap,Exception>() {
                    @Override
                    public void done(Bitmap o, Exception e) {
                        imagenTypeView.setImageBitmap(o);
                    }
                });
            } catch (com.parse.ParseException e) {
                e.printStackTrace();
            }

        }


        pokemon.getImage(new GetImageCallBack <Bitmap,Exception>() {
            @Override
            public void done(Bitmap imagen, Exception e) {

                if (e == null){
                    imagenView.setImageBitmap(imagen);
                }
            }
        });

    }
}
