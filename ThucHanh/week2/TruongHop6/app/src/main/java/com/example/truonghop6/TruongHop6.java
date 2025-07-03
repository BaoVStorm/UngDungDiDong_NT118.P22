package com.example.truonghop6;

import android.os.Bundle;
//import android.support.v7.widget.LinearLayoutManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class TruongHop6 extends AppCompatActivity {
    private ArrayList<TruongHop6_Hero> mHeros;
    private RecyclerView mRecyclerHero;
    private TruongHop6_HeroAdapter mHeroAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.truonghop6);

        mRecyclerHero = findViewById(R.id.recyclerHero);

        // Khởi tạo danh sách hero
        mHeros = new ArrayList<>();
        createHeroList();

        // Tạo adapter và thiết lập RecyclerView
        mHeroAdapter = new TruongHop6_HeroAdapter(this, mHeros);
        mRecyclerHero.setAdapter(mHeroAdapter);
        mRecyclerHero.setLayoutManager(new LinearLayoutManager(this));

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_th6), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // Phương thức tạo danh sách hero
    private void createHeroList() {
        mHeros.add(new TruongHop6_Hero("Thor", R.drawable.thor));
        mHeros.add(new TruongHop6_Hero("IronMan", R.drawable.ironman));
        mHeros.add(new TruongHop6_Hero("Hulk", R.drawable.hulk));
        mHeros.add(new TruongHop6_Hero("SpiderMan", R.drawable.spiderman));
        mHeros.add(new TruongHop6_Hero("Thor", R.drawable.thor));
        mHeros.add(new TruongHop6_Hero("IronMan", R.drawable.ironman));
        mHeros.add(new TruongHop6_Hero("Hulk", R.drawable.hulk));
        mHeros.add(new TruongHop6_Hero("SpiderMan", R.drawable.spiderman));
        mHeros.add(new TruongHop6_Hero("Thor", R.drawable.thor));
        mHeros.add(new TruongHop6_Hero("IronMan", R.drawable.ironman));
    }
}
