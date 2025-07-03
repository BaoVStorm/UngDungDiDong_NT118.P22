package com.example.listviewrecyclervier;

import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class TruongHop1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.truonghop1);

        // Khởi tạo đối tượng TextView
        TextView tvSelection = (TextView) findViewById(R.id.tv_person);

        // Khởi tạo đói tượng listview: findViewById từ file XML hoặc tạo bằng code
        ListView lvPerson = (ListView) findViewById(R.id.lv_person);

        // Load/ Khởi tạo mảng chứa dữ liueej sẽ được hiển thị trong listview
        final String arr[] = {"Teo", "Ty", "Bin", "Bo"};

        // Xây dựng adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arr);

        // SetAdapter cho listview
        lvPerson.setAdapter(adapter);

        // Xử lý các thao tác trên listview (click, longClick, ...)
        lvPerson.setOnItemClickListener (new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // đối sối <position> là bị trí phần tử trong Data Source (arr)
                tvSelection.setText("position: " + position + "; value =" + arr[position]);
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}