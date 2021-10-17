package com.ashraff.cats_final;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rv ;
    private FloatingActionButton floatingActionButton;
    private Toolbar toolbar ;
    private SearchView searchView;
    private dataBaseAccess db;
    private recyclerViewAdapter adapter;
    private static final int permissionReqCode = 5 ;
    private int addReqCode = 1;
    private int editReqCode = 1;
    public static final String catKey = "cat_key" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},permissionReqCode);
        }


        rv = findViewById(R.id.rv);
        floatingActionButton = findViewById(R.id.fab);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        db = dataBaseAccess.getInstance(this);
        db.open();

        ArrayList<cat> cats = db.getAllCats();
        db.close();
        adapter = new recyclerViewAdapter(cats, new onCardItemClickListener() {
            @Override
            public void onClick(int id) {
                Intent intent = new Intent(getBaseContext(),catViewActivity.class);
                intent.putExtra(catKey,id);
                startActivityForResult(intent,editReqCode);
            }
        });
        rv.setAdapter(adapter);
        RecyclerView.LayoutManager lm = new GridLayoutManager(this, 2);
        rv.setLayoutManager(lm);
        rv.setHasFixedSize(true);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(),catViewActivity.class);
                startActivityForResult(intent,addReqCode);

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        searchView = (SearchView) menu.findItem(R.id.main_search).getActionView();
        searchView.setSubmitButtonEnabled(true);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(MainActivity.this, "Submit Clicked", Toast.LENGTH_SHORT).show();

                db.open();
                ArrayList<cat> cats = db.getCatWithArgs(query);
                db.close();
                adapter.setCats(cats);
                adapter.notifyDataSetChanged();

                return false;


            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Toast.makeText(MainActivity.this, "Text Changed", Toast.LENGTH_SHORT).show();
                db.open();
                ArrayList<cat> cats = db.getCatWithArgs(newText);
                db.close();
                adapter.setCats(cats);
                adapter.notifyDataSetChanged();
                return false;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                Toast.makeText(MainActivity.this, "Search Finished", Toast.LENGTH_SHORT).show();

                db.open();
                ArrayList<cat> cats = db.getAllCats();
                db.close();
                adapter.setCats(cats);
                adapter.notifyDataSetChanged();

                return false;
            }
        });

        return true;
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == addReqCode) {

            db.open();
            ArrayList<cat> cats = db.getAllCats();
            db.close();
            adapter.setCats(cats);
            adapter.notifyDataSetChanged();

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case permissionReqCode:
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                }
                else {
                    ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},permissionReqCode);
                }
        }

    }
}
