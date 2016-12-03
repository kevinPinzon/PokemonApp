package com.example.colocho.pokemonparse.Helper;

import android.app.Activity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;

import com.example.colocho.pokemonparse.Adapter.PokemonListAdapter;
import com.example.colocho.pokemonparse.GetImageCallBack;
import com.example.colocho.pokemonparse.Model.Pokemon;
import com.example.colocho.pokemonparse.Model.Type;
import com.example.colocho.pokemonparse.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Colocho on 11/11/2016.
 */
public class DataManager {
    SwipeRefreshLayout swipeRefreshLayout;
    private DataManager() {
    }


    public static void getType(final GetObjectsCallBack<List, Exception,Boolean > getobjectcallback) {
        getTypeWhere(null,getobjectcallback);
    }

    public static void getTypeWhere(Map<String,Object> options, final GetObjectsCallBack<List, Exception,Boolean> getobjectcallback) {
        final ParseQuery localquery = new ParseQuery(Type.class);

        ParseQuery q = new ParseQuery(Type.class);
        if( options != null){
            for (String key : options.keySet()){
                localquery.whereEqualTo(key,options.get(key));
            }
        }
        localquery.fromLocalDatastore();
        q.orderByAscending("name");
        localquery.orderByAscending("name");

        localquery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List objects, ParseException e) {
                getobjectcallback.done(objects,e,true);
            }

        });
        q.findInBackground(new FindCallback<ParseObject>() {

            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if (e ==null){
                    ParseObject.unpinAllInBackground();
                }
                getobjectcallback.done(objects,e,false);

                if (e ==null){
                    ParseObject.pinAllInBackground(objects);
                }
            }

        });

    }

    public static void getPokemons(final GetObjectsCallBack<List, Exception,Boolean > getobjectcallback) {
        getPokemonsWhere(null,getobjectcallback);
    }

    public static void getPokemonsWhere(Map<String,Object> options, final GetObjectsCallBack<List, Exception,Boolean> getobjectcallback) {
        final ParseQuery localquery = new ParseQuery(Pokemon.class);

        ParseQuery q = new ParseQuery(Pokemon.class);
        if( options != null){
            for (String key : options.keySet()){
                localquery.whereEqualTo(key,options.get(key));

            }

        }
        localquery.fromLocalDatastore();
        q.orderByAscending("numId");
        localquery.orderByAscending("numId");

        q.include("type");
        localquery.include("type");

        localquery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List objects, ParseException e) {
                getobjectcallback.done(objects,e,true);
            }

        });
        q.findInBackground(new FindCallback<ParseObject>() {

            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if (e ==null){
                    ParseObject.unpinAllInBackground();
                }
                getobjectcallback.done(objects,e,false);

                if (e ==null){
                    ParseObject.pinAllInBackground(objects);
                }
            }

        });

    }
}