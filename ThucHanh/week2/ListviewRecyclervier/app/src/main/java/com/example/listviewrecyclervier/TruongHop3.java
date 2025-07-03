package com.example.listviewrecyclervier;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class TruongHop3 extends AppCompatActivity {

    ArrayList<Employee> employees;
    ArrayAdapter<Employee> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.truonghop3);

        EditText nv_id = (EditText) findViewById(R.id.id_input);
        EditText nv_name = (EditText) findViewById(R.id.name_input);
        Button button = (Button) findViewById(R.id.button_name);
        RadioGroup box_radio = (RadioGroup) findViewById(R.id.box_radio);
        TextView tvSelection = (TextView) findViewById(R.id.tv_person);
        ListView lvPerson = (ListView) findViewById(R.id.lv_person);

        employees = new ArrayList<Employee>();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, employees);

        lvPerson.setAdapter(adapter);

        // sư kiên button
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                addNewEmployee(nv_id, nv_name, box_radio);

                Log.d("MyTag", employees.get(employees.size() - 1).toString());
            }
        });

        // Xử lý sự kiện chọn một phần tử trong ListView
        lvPerson.setOnItemClickListener (new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tvSelection.setText("position: " + position + "; value =" + employees.get(position));
            }
        });


        // ử lý sự kiện Long Click
        lvPerson.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                employees.remove(position);

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

    public void addNewEmployee(EditText nv_id, EditText nv_name, RadioGroup box_radio) {
        int radId = box_radio.getCheckedRadioButtonId();
        String id = nv_id.getText().toString();
        String name = nv_name.getText().toString();

        Employee employee;

        if(radId == R.id.radio_ChinhThuc) {
            employee = new EmployeeFulltime(id, name);
        }
        else {
            employee = new EmployeeParttime(id, name);
        }

        employees.add(employee);
        adapter.notifyDataSetChanged();
    }
}


//   --------------- Employee
class Employee {
    public String id, name;

    Employee() {
        id = name = "";
    }

    Employee(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public double tinhLuong() {
        return 0;
    }

    public String toString() {
        return String.format("%s -", id);
    }
}

class EmployeeFulltime extends Employee {
    EmployeeFulltime() {
        super();
    }

    EmployeeFulltime(String id, String name) {
        super(id, name);
    }

    @Override
    public double tinhLuong() {
        return 500.0;
    }

    @Override
    public String toString() {
        String value = String.format("%s %s --> FullTime=%.0f", super.toString(), name, tinhLuong());

        return value;
    }
}

class EmployeeParttime extends Employee {
    EmployeeParttime() {
        super();
    }

    EmployeeParttime(String id, String name) {
        super(id, name);
    }


    @Override
    public double tinhLuong() {
        return 150.0;
    }

    @Override
    public String toString() {
        String value = String.format("%s %s --> PartTime=%.0f", super.toString(), name, tinhLuong());

        return value;
    }
}