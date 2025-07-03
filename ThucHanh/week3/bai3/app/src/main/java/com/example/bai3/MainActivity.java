package com.example.bai3;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    String DB_PATH_SUFFIX = "/databases/";
    SQLiteDatabase database = null;
    String DATABASE_NAME="qlsv.db";

    //Khai báo ListView
    ListView lv;
    ArrayList<String> mylist;
    ArrayAdapter<String> myadapter;

    Button btn_insert, btn_delete, btn_update, btn_query;
    TextView text_mssv, text_hoten, text_lop;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Ham Copy CSDL từ assets vào thư mục Databases
        processCopy();
        //Mo CSDL trong ung dung len
        database = openOrCreateDatabase("qlsv.db", MODE_PRIVATE, null);
        // Tạo ListView
        lv = findViewById(R.id.lv);

        btn_insert = findViewById(R.id.button_insert);
        btn_delete = findViewById(R.id.button_delete);
        btn_update = findViewById(R.id.button_update);
        btn_query = findViewById(R.id.button_query);
        text_mssv = findViewById(R.id.editText1);
        text_hoten = findViewById(R.id.editText2);
        text_lop = findViewById(R.id.editText3);


        mylist = new ArrayList<>();
        myadapter = new ArrayAdapter<>(MainActivity.this,
                android.R.layout.simple_list_item_1, mylist);

        lv.setAdapter(myadapter);

        updateListView();

        btn_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // code
                ContentValues values = new ContentValues();
                values.put("MSSV", text_mssv.getText().toString());
                values.put("HoTen", text_hoten.getText().toString());
                values.put("Lop", text_lop.getText().toString());

                database.insert("qlsv", null, values);
                updateListView();
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // code
                database.delete("qlsv", "mssv = ?", new String[]{String.valueOf(text_mssv.getText().toString())});
                updateListView();
            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // code
                ContentValues values = new ContentValues();
                values.put("Lop", text_lop.getText().toString());
                values.put("HoTen", text_hoten.getText().toString());

                database.update("qlsv", values, "mssv = ?", new String[]{String.valueOf(text_mssv.getText().toString())});
                updateListView();
            }
        });

        btn_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mylist.clear();

                String selectQuery = "SELECT * FROM qlsv WHERE 1 = 1 ";

                if(!text_mssv.getText().toString().isEmpty()) {
                    selectQuery += " AND mssv = '" + text_mssv.getText().toString() + "'";
                }

                if(!text_hoten.getText().toString().isEmpty()) {
                    selectQuery += " AND hoten = '" + text_hoten.getText().toString() + "'";
                }

                if(!text_lop.getText().toString().isEmpty()) {
                    selectQuery += " AND lop = '" + text_lop.getText().toString() + "'";
                }

                // Truy vấn CSDL và cập nhật hiển thị lên Listview
                Cursor c = database.rawQuery(selectQuery, null);

                c.moveToFirst();
                String data = "";
                while (!c.isAfterLast())
                {
                    data = c.getString(0) + "-" + c.getString(1) + "-" + c.getString(2);
                    mylist.add(data);
                    c.moveToNext();
                }
                c.close();
                myadapter.notifyDataSetChanged();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            v.setPadding(0, systemBars.top, 0, 0);
            return insets;
        });
    }

    private void updateListView() {
        mylist.clear();

        // Truy vấn CSDL và cập nhật hiển thị lên Listview
        Cursor c =
                database.query("qlsv",null,null,null,null,null,null);
        c.moveToFirst();
        String data = "";
        while (!c.isAfterLast())
        {
            data = c.getString(0) + "-" + c.getString(1) + "-" + c.getString(2);
            mylist.add(data);
            c.moveToNext();
        }
        c.close();
        myadapter.notifyDataSetChanged();
    }

    private void processCopy() {
        //private app
        File dbFile = getDatabasePath(DATABASE_NAME);
        if (!dbFile.exists())
        {
            try {
                CopyDataBaseFromAsset();
                Toast.makeText(this, "Copying sucess from Assets folder", Toast.LENGTH_LONG).show();
            }
            catch (Exception e){
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private String getDatabasePath() {
        return getApplicationInfo().dataDir + DB_PATH_SUFFIX + DATABASE_NAME;
    }

    // Ham copy file DB tu thu muc Asset vao file DB moi tao ra trong ung dung
    public void CopyDataBaseFromAsset() {
        try {
            InputStream myInput;
            myInput = getAssets().open(DATABASE_NAME);
            String outFileName = getDatabasePath();

            // Kiem tra neu duong dan khong co, thi tao moi file
            File f = new File(getApplicationInfo().dataDir + DB_PATH_SUFFIX);

            if (!f.exists())
                f.mkdir();

            // Mo empty db su dung output stream
            OutputStream myOutput = new FileOutputStream(outFileName);
            // Sao chep du lieu bytes tu input toi ouput
            int size = myInput.available();

            byte[] buffer = new byte[size];
            myInput.read(buffer);
            myOutput.write(buffer);
            myOutput.flush();
            myOutput.close();
            myInput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
