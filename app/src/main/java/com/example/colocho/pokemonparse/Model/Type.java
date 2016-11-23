package com.example.colocho.pokemonparse.Model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.colocho.pokemonparse.GetImageCallBack;
import com.parse.GetDataCallback;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;

/**
 * Created by alexp on 18/11/2016.
 */
@ParseClassName("Type")
public class Type extends ParseObject {

    private final String nameKey = "name";
    private Bitmap image ;
    private final String imageKey = "icon";


    public String getName() {
        return getString(nameKey);
    }

    public void getImage(final GetImageCallBack callBack) {
        if (image == null) {
            ParseFile imageFile = getParseFile(imageKey);
            if (imageFile==null){
                return;
            }
            imageFile.getDataInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] data, ParseException e) {
                    if (e != null) {
                        callBack.done(image,null);
                        return;
                    }
                    image = BitmapFactory.decodeByteArray(data, 0, data.length);
                    callBack.done(image,null);


                }
            });
        }   else {
            callBack.done(image,null);
        }
    }

    public void setName(String name) {
        put(nameKey, name);
    }

}
