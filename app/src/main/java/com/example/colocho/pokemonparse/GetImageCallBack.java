package com.example.colocho.pokemonparse;

import com.parse.ParseClassName;

/**
 * Created by Douglas Verdial on 29/10/2016.
 */

public interface GetImageCallBack<Bitmap,Exception>{
 void done(Bitmap bitmap,Exception e);
}
