package com.example.colocho.pokemonparse.Fragment;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.colocho.pokemonparse.Adapter.PokemonListAdapter;
import com.example.colocho.pokemonparse.Helper.DataManager;
import com.example.colocho.pokemonparse.Helper.GetObjectsCallBack;
import com.example.colocho.pokemonparse.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
 private View view;
    SwipeRefreshLayout swipeRefreshLayout;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.fragment_main, container, false);

        view = inflater.inflate(R.layout.fragment_main,container,false);
        loadPokemon();

swipeRefreshLayout= (SwipeRefreshLayout) view.findViewById(R.id.swipeContaneir);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadPokemon();
            }
        });
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
   return  view;

    }

    public void loadPokemon (){

        Map<String,Object> options= new HashMap<>();

      // options.put("numId",13);
     //   DataManager.getPokemonWhere(options,new GetObjectsCallBack<List, Exception,Boolean>()

                DataManager.getPokemonWhere(options,new GetObjectsCallBack<List, Exception,Boolean>()
                {
            @Override
            public void done(List objects, Exception e, Boolean local) {

                swipeRefreshLayout.setRefreshing(false);
                if (local==true){
                   // Toast.makeText("Pulsaste el baton", Toast.LENGTH_SHORT).show();
                    Toast toast;
                    toast = Toast.makeText(getActivity(),"Cargando",Toast.LENGTH_SHORT );
                    toast.show();
                }
                if (e == null) {


                    // ArrayAdapter<String> PokemonAdapter = new ArrayAdapter<String>(getActivity(), R.layout.pokemon_item,R.id.pokemonListView,PokemonList) ;

                    PokemonListAdapter PokemonAdapter = new PokemonListAdapter(getActivity(), objects);
                    ListView listView = (ListView) view.findViewById(R.id.pokemonListView);
                    listView.setAdapter(PokemonAdapter);
                }
            }
        });

    }

}
