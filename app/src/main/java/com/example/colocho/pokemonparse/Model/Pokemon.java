package com.example.colocho.pokemonparse.Model;

/**
 * Created by Colocho on 29/10/2016.
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.colocho.pokemonparse.GetImageCallBack;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseClassName;
        import com.parse.ParseObject;

@ParseClassName("Pokemon")
public class Pokemon extends ParseObject {

    private final String nameKey = "name";
    private final String numIdKey = "numId";
    private final String descKey = "descrip";
    private Bitmap image ;
    private final String imageKey = "image";

    public Type getType(){
        return (Type) getParseObject("type");
    }

    //get
    public String getName() {
        return getString(nameKey);
    }

    public Number getNumId() {
        return getNumber(numIdKey);
    }

    public String getDescription() {
        return getString(descKey);
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
    //Set
    public void setType(Type type) {
        put("type", type);
    }

    public void setName(String name) {
        put(nameKey, name);
    }

    public void setNumId(Number numId) {
        put(numIdKey, numId);
    }

    public void setDesc(String desc) {
        put(descKey, desc);
    }

    public void setImage(ParseFile image){
        put(imageKey,image);
    }

}