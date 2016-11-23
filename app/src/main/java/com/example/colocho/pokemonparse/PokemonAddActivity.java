package com.example.colocho.pokemonparse;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.example.colocho.pokemonparse.Activity.MainActivity;

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
        if (itemId == R.id.actionDone){
            final ProgressDialog progress= new ProgressDialog(this);
            progress.setTitle("Guardando");
            progress.setMessage("Cargando imagen...");
            progress.setCancelable(false);
            progress.show();

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
