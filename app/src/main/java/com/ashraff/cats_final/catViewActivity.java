package com.ashraff.cats_final;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.net.URI;

public class catViewActivity extends AppCompatActivity {

    public static final int addSucResCode =2;
    public static final int editSucResCode =2;
    private Toolbar toolbar;
    private TextInputEditText name, color, weight,eyeColor;
    private TextInputLayout weighttt;
    private ImageView catImage;
    private Menu menu;
    private int catId = -1 ;
    private static final int imgReqCode =1;
    dataBaseAccess db;
    private Uri uri;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_view);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        name = findViewById(R.id.et_cat_name);
        color = findViewById(R.id.et_cat_color);
        weight = findViewById(R.id.et_cat_weight);
        eyeColor = findViewById(R.id.et_cat_eye_color);
        catImage = findViewById(R.id.iv_cat_image);
        weighttt = findViewById(R.id.wieghth);

        db = dataBaseAccess.getInstance(this);

        Intent intent = getIntent();
        catId =  intent.getIntExtra(MainActivity.catKey,-1);

        if (catId == -1) {
            enableFields();
            clearFields();

        }else {

            disableFields();
            db.open();
            cat c = db.getCat(catId);
            db.close();
            if(c != null)
                fillCatFields(c);

        }

        catImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,imgReqCode);
            }
        });


    }

    private void disableFields(){
        name.setEnabled(false);
        catImage.setEnabled(false);
        weight.setEnabled(false);
        color.setEnabled(false);
        eyeColor.setEnabled(false);
        weighttt.setHelperTextEnabled(false);

    }

    private void enableFields(){
        name.setEnabled(true);
        catImage.setEnabled(true);
        weight.setEnabled(true);
        color.setEnabled(true);
        eyeColor.setEnabled(true);
    }
    private void clearFields(){
        name.setText("");
        catImage.setImageURI(null);
        weight.setText("");
        color.setText("");
        eyeColor.setText("");
    }

    private void fillCatFields (cat c) {
        if (c.getImage() != null && !c.getImage().equals("")) {
            catImage.setImageURI(Uri.parse(c.getImage()));
        }
            name.setText(c.getName());
            weight.setText(c.getWeight() + "");
            color.setText(c.getColor());
            eyeColor.setText(c.getEyeColor());


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.details_menu,menu);

        MenuItem save = menu.findItem(R.id.save);
        MenuItem edit = menu.findItem(R.id.edit);
        MenuItem delete = menu.findItem(R.id.delete);

        if (catId == -1) {
            save.setVisible(true);
            edit.setVisible(false);
            delete.setVisible(false);
        }else {
            save.setVisible(false);
            edit.setVisible(true);
            delete.setVisible(true);

        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        String catImage="",catName,catColor,catEyeColor;
        double catWeight;
        db.open();

        switch (item.getItemId()) {
            case R.id.save:

            if (uri != null){
                catImage = uri.toString();
            }

             catName = name.getText().toString();
             catColor = color.getText().toString();
             catWeight = Double.parseDouble(weight.getText().toString());
             catEyeColor = eyeColor.getText().toString();
             boolean res;
             cat c = new cat(catId,catName,catColor,catWeight,catEyeColor,catImage);


            if (catId == -1){
                 res = db.insertCat(c);
                if (res) {
                    Toast.makeText(getBaseContext(), "Insertion Successful", Toast.LENGTH_SHORT).show();
                    setResult(addSucResCode,null);
                    finish();
                }
            }else {
                res = db.updateCat(c);
                if (res) {
                    Toast.makeText(getBaseContext(), "Update Successful", Toast.LENGTH_SHORT).show();
                    setResult(editSucResCode, null);
                    finish();
                }
            }


                return true;
            case R.id.edit:
                enableFields();
                MenuItem save = toolbar.getMenu().findItem(R.id.save);
                MenuItem edit =toolbar.getMenu().findItem(R.id.edit);
                MenuItem delete = toolbar.getMenu().findItem(R.id.delete);
                save.setVisible(true);
                edit.setVisible(false);
                delete.setVisible(false);

                return true;
            case R.id.delete:
                c = new cat(catId,null,null,0,null,null);
                res = db.deleteCat(c);
                if (res){
                    Toast.makeText(getBaseContext(), "Delete Successful", Toast.LENGTH_SHORT).show();
                    setResult(editSucResCode, null);
                    finish();
                }
                return true;
        }
        db.close();
        return false;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == imgReqCode && resultCode== RESULT_OK) {
            if (data != null){
                uri = data.getData();
                catImage.setImageURI(uri);
            }



        }
    }
}
