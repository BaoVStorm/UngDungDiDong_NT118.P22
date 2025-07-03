package com.example.thuchanh5;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<TruongHop5_Dish> dishList;
    private TruongHop5_DishAdapter dishAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.truonghop5);

        dishList = new ArrayList<TruongHop5_Dish>();
        GridView gridView = findViewById(R.id.gv_dish);
        dishAdapter = new TruongHop5_DishAdapter(this, android.R.layout.simple_list_item_1, dishList);
        gridView.setAdapter(dishAdapter);

        EditText editTextName = findViewById(R.id.id_input);
        Spinner spinnerThumbnail = findViewById(R.id.spinner_thumbnail);
        CheckBox checkBox = (CheckBox) findViewById(R.id.checkbox_isPromotion);
        Button buttonAdd = findViewById(R.id.button_name);

        TruongHop5_ThumbnailAdapter thumbnailAdapter = new TruongHop5_ThumbnailAdapter(this, TruongHop5_Thumbnail.values());
        spinnerThumbnail.setAdapter(thumbnailAdapter);

        buttonAdd.setOnClickListener(view -> {
            String name = editTextName.getText().toString().trim();
            if (name.isEmpty()) {
                Toast.makeText(this, "Please enter a dish name", Toast.LENGTH_SHORT).show();
                return;
            }

            TruongHop5_Thumbnail selectedThumbnail = (TruongHop5_Thumbnail) spinnerThumbnail.getSelectedItem();

            TruongHop5_Dish newDish = new TruongHop5_Dish(name, selectedThumbnail.getImg(), checkBox.isChecked());
            dishList.add(newDish);
            dishAdapter.notifyDataSetChanged();

            editTextName.setText("");
            spinnerThumbnail.setSelection(0);
            checkBox.setChecked(false);
            Toast.makeText(this, "Added successfully", Toast.LENGTH_SHORT).show();
        });
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}