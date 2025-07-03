package com.example.listviewrecyclervier;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class TruongHop4 extends AppCompatActivity {

    ArrayList<Employee_bai4> employees;
    TruongHop4_EmployeeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.truonghop4);

        EditText nv_id = (EditText) findViewById(R.id.id_input);
        EditText nv_name = (EditText) findViewById(R.id.name_input);
        CheckBox manager_checkBox = (CheckBox) findViewById(R.id.checkbox_isManager);
        Button button = (Button) findViewById(R.id.button_name);

        ListView lvPerson = (ListView) findViewById(R.id.lv_person);

        employees = new ArrayList<Employee_bai4>();

        adapter = new TruongHop4_EmployeeAdapter(this, android.R.layout.simple_list_item_1, employees);

        lvPerson.setAdapter(adapter);

        // sư kiên button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String id = nv_id.getText().toString();
                String name = nv_name.getText().toString();
                Employee_bai4 employee = new Employee_bai4(id, name, manager_checkBox.isChecked());

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
