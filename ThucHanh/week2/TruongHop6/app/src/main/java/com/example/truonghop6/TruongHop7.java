package com.example.truonghop6;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TruongHop7 extends AppCompatActivity {

    ArrayList<Employee_bai7> employees;
    TruongHop7_EmployeeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.truonghop7);

        EditText nv_id = (EditText) findViewById(R.id.id_input);
        EditText nv_name = (EditText) findViewById(R.id.name_input);
        CheckBox manager_checkBox = (CheckBox) findViewById(R.id.checkbox_isManager);
        Button button = (Button) findViewById(R.id.button_name);

        RecyclerView rvPerson = (RecyclerView) findViewById(R.id.rv_person);

        employees = new ArrayList<Employee_bai7>();

//        adapter = new TruongHop7_EmployeeAdapter(this, android.R.layout.simple_list_item_1, employees);
        TruongHop7_EmployeeAdapter adapter = new TruongHop7_EmployeeAdapter(employees);
        rvPerson.setLayoutManager(new LinearLayoutManager(this)); // Hoặc GridLayoutManager nếu cần
        rvPerson.setAdapter(adapter);


        rvPerson.setAdapter(adapter);

        // sư kiên button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String id = nv_id.getText().toString();
                String name = nv_name.getText().toString();
                Employee_bai7 employee = new Employee_bai7(id, name, manager_checkBox.isChecked());

                employees.add(employee);
                adapter.notifyDataSetChanged();

                Log.d("MyTag", employees.get(employees.size() - 1).toString());
            }
        });

//        // Xử lý sự kiện chọn một phần tử trong ListView
//        lvPerson.setOnItemClickListener (new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                tvSelection.setText("position: " + position + "; value =" + employees.get(position));
//            }
//        });
//
//
//        // ử lý sự kiện Long Click
//        lvPerson.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                employees.remove(position);
//
//                adapter.notifyDataSetChanged();
//
//                return true;
//            }
//        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
