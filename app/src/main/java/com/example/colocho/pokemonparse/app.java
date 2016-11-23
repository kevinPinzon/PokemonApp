package com.example.colocho.pokemonparse;

import android.app.Application;

import com.example.colocho.pokemonparse.Model.Pokemon;
import com.example.colocho.pokemonparse.Model.Type;
import com.parse.Parse;
import com.parse.ParseObject;

/**
 * Created by Colocho on 22/10/2016.
 */
public class app extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(Pokemon.class);
        ParseObject.registerSubclass(Type.class);
        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext()).applicationId("12345").server("http://papinzon.me/pokemon").enableLocalDataStore().build());
    }
}
