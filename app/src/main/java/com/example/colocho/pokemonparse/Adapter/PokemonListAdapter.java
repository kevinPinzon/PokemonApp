package com.example.colocho.pokemonparse.Adapter;

/**
 * Created by Colocho on 29/10/2016.
 */

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.ImageView;
        import android.widget.TextView;

import com.example.colocho.pokemonparse.Activity.PokemonDetailActivity;
import com.example.colocho.pokemonparse.GetImageCallBack;
import com.example.colocho.pokemonparse.Model.Pokemon;
import com.example.colocho.pokemonparse.R;

import java.util.List;

/**
 * TODO: document your custom view class.
 */
public class PokemonListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final List pokemonList;

    public PokemonListAdapter(Activity context, List pokemonList){
        super(context, R.layout.pokemon_list_adapter,pokemonList);
        this.context = context;
        this.pokemonList = pokemonList;
    }

    @NonNull
    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        final View rowview = inflater.inflate(R.layout.pokemon_list_adapter,null,true);

        Pokemon pokemon = (Pokemon) pokemonList.get(position);
        final String objectId = pokemon.getObjectId();
        String name = pokemon.getName();
        Number num = pokemon.getNumId();

//        ParseFile image = pokemon.getParseFile("image");

        TextView nameTextView = (TextView)rowview.findViewById(R.id.nameTextView);
        TextView numTextView = (TextView)rowview.findViewById(R.id.numIdTextView);
        final ImageView imageView = (ImageView)rowview.findViewById(R.id.pokmeonImageView);

       // image.getDataInBackground(new GetDataCallback() {
      /*      @Override
            public void done(byte[] data, ParseException e) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(data,0,data.length);
                imageView.setImageBitmap(bitmap);
            }
        });*/
            pokemon.getImage(new GetImageCallBack(){
                @Override
                public void done(Object o, Object o2) {
                    if (o2 == null){
                        imageView.setImageBitmap((Bitmap)o);
                    }
                }



    });
        nameTextView.setText(name);
        numTextView.setText(num.toString());


        rowview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PokemonDetailActivity.class);
                intent.putExtra("ObjectId",objectId);
                context.startActivity(intent);
            }
        });
        return rowview;
    }


}
