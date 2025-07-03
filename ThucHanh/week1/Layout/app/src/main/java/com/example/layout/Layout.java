package com.example.layout;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Layout extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);

//        setContentView(R.layout.layout_frame);
//        setContentView(R.layout.layout_linear);
//        setContentView(R.layout.layout_linear_vertical);
//        setContentView(R.layout.layout_linear_2);
//        setContentView(R.layout.layout_table);
        setContentView(R.layout.layout_constraint);
//        setContentView(R.layout.layout_relative);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }
}