package com.example.myapplication;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText editX1, editX2;
    private Button btnCalculate;
    private TextView txtResult;

    @Override
    public void onCreate(Bundle savedInstanceState) {


        // Required call through to Activity.onCreate()
        // Restore any saved instance state
        super.onCreate(savedInstanceState);

        // Set up the application's user interface (content view)
        setContentView(R.layout.activity_main);

        // Ánh xạ các view
        editX1 = findViewById(R.id.editX1);
        editX2 = findViewById(R.id.editX2);
        btnCalculate = findViewById(R.id.btnCalculate);
        txtResult = findViewById(R.id.txtResult);

        // Xử lý sự kiện click button
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    double x1 = Double.parseDouble(editX1.getText().toString());
                    double x2 = Double.parseDouble(editX2.getText().toString());
                    double sum = x1 + x2;
                    txtResult.setText("Kết quả: " + sum);
                } catch (NumberFormatException e) {
                    txtResult.setText("Vui lòng nhập số hợp lệ!");
                }
            }
        });



    }
}