package com.example.colocho.pokemonparse;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.colocho.pokemonparse.Activity.MainActivity;
import com.example.colocho.pokemonparse.Model.Pokemon;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;

public class PokemonAddActivity extends AppCompatActivity {

    ImageButton imageButton;
    final int ACTIVITY_SELECT_IMAGE = 123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_pokemon_add);
        imageButton = (ImageButton) findViewById(R.id.pokemonImagenButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(intent,ACTIVITY_SELECT_IMAGE);
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_pokemon_add,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId =item.getItemId();
        if (itemId == R.id.actionDone) {
            final ProgressDialog progress = new ProgressDialog(this);
            progress.setTitle("Guardando");
            progress.setMessage("Cargando imagen...");
            progress.setCancelable(true);
            progress.show();

            EditText name = (EditText) findViewById(R.id.pokemonNameEditText);
            EditText numId = (EditText) findViewById(R.id.pokemonNumberEditText);
            EditText descrip = (EditText) findViewById(R.id.pokemonNameDescripText);

            final ImageButton imagen = (ImageButton) findViewById(R.id.pokemonImagenButton);

            if ( !(name.getText().toString().equals("") || numId.getText().toString().equals("") ||
                    name.getText().toString().equals("") || descrip.getText().toString().equals("") || imagen == null)){

                final Pokemon pokemon = new Pokemon();

                pokemon.setDesc(descrip.getText().toString());
                pokemon.setName(name.getText().toString());
                pokemon.setNumId((Integer.parseInt(numId.getText().toString())));

                Bitmap image = ((BitmapDrawable) imageButton.getDrawable()).getBitmap();
                ByteArrayOutputStream byteTemp = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.PNG, 10, byteTemp);

                final ParseFile file = new ParseFile("imagePng", byteTemp.toByteArray());
                file.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        progress.dismiss();
                        if (e != null) {

                            progress.setTitle("Error");
                            progress.setMessage(e.getMessage());
                            progress.setCancelable(true);
                            progress.show();
                            return;
                        }
                        pokemon.setImage(file);
                        pokemon.saveEventually();

                        finish();
                    }

                });
            }else {
                progress.dismiss();
                progress.setTitle("Error");
                progress.setMessage("por favor llene todos los campos");
                progress.setCancelable(true);
                progress.show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case ACTIVITY_SELECT_IMAGE:
                if (resultCode == RESULT_OK){
                    Uri selectedImage = data.getData();
                    String[] filepathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(selectedImage,filepathColumn,null,null,null);
                    cursor.moveToFirst();
                    int columIndex = cursor.getColumnIndex(filepathColumn[0]);
                    String filePath = cursor.getString(columIndex);
                    cursor.close();

                    Bitmap image = BitmapFactory.decodeFile(filePath);
                    imageButton.setImageBitmap(image);
                }

        }
    }
}
