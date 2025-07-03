package com.example.listviewrecyclervier;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class TruongHop2 extends AppCompatActivity {

    ArrayList<String> names;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.truonghop2);

        // Khởi tạo đối tượng TextView
        TextView tvSelection = (TextView) findViewById(R.id.tv_person);

        EditText input = (EditText) findViewById(R.id.input_name);

        // Khởi tạo đói tượng listview: findViewById từ file XML hoặc tạo bằng code
        ListView lvPerson = (ListView) findViewById(R.id.lv_person);

        // Khởi tạo đối tượng input để tiến hành nhập text


        // Khởi tạo đối tượng button
        Button btnSubmit = (Button) findViewById(R.id.button_name);

        // 1. Tạo ArrayList object
        names = new ArrayList<String>();

        // tạo adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, names);

        lvPerson.setAdapter(adapter);

        // Xử lý sự kiện nhấn nút Nhập
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                names.add(input.getText().toString());

                Log.d("MyTag", names.get(names.size() - 1));

                adapter.notifyDataSetChanged();
            }
        });

        // Xử lý sự kiện chọn một phần tử trong ListView
        lvPerson.setOnItemClickListener (new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // đối sối <position> là bị trí phần tử trong Data Source (arr)
                tvSelection.setText("position: " + position + "; value =" + names.get(position));
            }
        });

        // ử lý sự kiện Long Click
         lvPerson.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
             @Override
             public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                 names.remove(position);

                 adapter.notifyDataSetChanged();

                 return true;
             }
         });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}