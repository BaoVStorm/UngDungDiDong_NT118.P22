package com.example.maplocationfromcontacts;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

// Khai báo biến và quyền
import android.Manifest;
import android.content.pm.PackageManager;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MapLocationFromContactsActivity extends AppCompatActivity {
    // These variables are shorthand aliases for data items in Contacts-related database tables
    private static final String DATA_MIMETYPE = ContactsContract.Data.MIMETYPE;
    private static final Uri DATA_CONTENT_URI = ContactsContract.Data.CONTENT_URI;
    private static final String DATA_CONTACT_ID = ContactsContract.Data.CONTACT_ID;

    private static final String CONTACTS_ID = ContactsContract.Contacts._ID;
    private static final Uri CONTACTS_CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;

    private static final String STRUCTURED_POSTAL_CONTENT_ITEM_TYPE = ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE;
    private static final String STRUCTURED_POSTAL_FORMATTED_ADDRESS = ContactsContract.CommonDataKinds.StructuredPostal.FORMATTED_ADDRESS;

    private static final int PICK_CONTACT_REQUEST = 0;
    static String TAG = "MapLocation";

    // mã định danh cho quyền yêu cầu danh bạ
    private static final int REQUEST_CONTACT_PERMISSION = 100;

    private ActivityResultLauncher<Intent> contactPickerLauncher;   // Thay thế startActivityForResult bằng ActivityResultLauncher


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Edit language
        LocaleHelper.setLocale(this, "vi");

        EdgeToEdge.enable(this);
        setContentView(R.layout.main);

        // Kiểm tra quyền trước khi truy cập danh bạ
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_CONTACT_PERMISSION);
        } else {
            Log.d("TAG", "Quyền đã được cấp, có thể truy cập danh bạ");
        }

        // Đăng ký ActivityResultLauncher để chọn danh bạ (positive: startActivity() ko cần result)
        contactPickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri contactUri = result.getData().getData();
                        Log.d(TAG, "Chọn liên hệ: " + contactUri.toString());


                        // ----------
                        String[] projection = new String[]{ContactsContract.Contacts._ID};

                        // Truy vấn ContentResolver để lấy ID
                        String Location = "";
                        Cursor cursor = getContentResolver().query(contactUri, projection, null, null, null);
                        if (cursor != null && cursor.moveToFirst()) {
                            long contactId = cursor.getLong(0);  // ID thực của liên hệ
                            cursor.close();

                            // Lấy thông tin liên hệ từ contactId
                            Location = getContactDetails(contactId);
                        }

                        // ----------

                        // Mở Google Maps với địa chỉ của liên hệ
                        Intent geoIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=" + Location));
                        startActivity(geoIntent);
                    }
                }
        );
        // --------

        final Button button = (Button) findViewById(R.id.mapButton);
        button.setOnClickListener(new Button.OnClickListener() {

            // Called when user clicks the Show Map button
            @Override
            public void onClick(View v) {
                try {
                    // Create Intent object for picking data from Contacts database
                    Intent intent = new Intent(Intent.ACTION_PICK, CONTACTS_CONTENT_URI);

                    // Use intent to start Contacts application
                    // Variable PICK_CONTACT_REQUEST identifies this operation
//                    startActivityForResult(intent, PICK_CONTACT_REQUEST);
                    contactPickerLauncher.launch(intent);

                } catch (Exception e) {
                    // Log any error messages to LogCat using Log.e()
                    Log.e(TAG, e.toString());
                }
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onRestart() {
        Log.i(TAG, "The activity is about to be restarted.");
        super.onRestart();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "The activity is about to become visible.");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "The activity has become visible (it is now \"resumed\")");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG,
                "Another activity is taking focus (this activity is about to be \"paused\")");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "The activity is no longer visible (it is now \"stopped\")");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "The activity is about to be destroyed.");
    }

    private String getContactDetails(long contactId) {
        String value = "";
        ContentResolver contentResolver = getContentResolver();

        // Truy vấn địa chỉ của contact
        Uri uri = ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_URI;
        Cursor cursor = getContentResolver().query(
                uri,
                new String[]{ContactsContract.CommonDataKinds.StructuredPostal.FORMATTED_ADDRESS},
                ContactsContract.CommonDataKinds.StructuredPostal.CONTACT_ID + " = ?",
                new String[]{String.valueOf(contactId)},
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            String address = cursor.getString(cursor.getColumnIndexOrThrow(
                    ContactsContract.CommonDataKinds.StructuredPostal.FORMATTED_ADDRESS));

            Log.d("TAG", "Địa chỉ: " + address);
            value = address;

            cursor.close();
        } else {
            Log.e("TAG", "Không tìm thấy địa chỉ");
            value = "Không tìm thấy địa chỉ";
        }

        return value;

//        // Lấy tên liên hệ
//        Uri uri = ContactsContract.Contacts.CONTENT_URI;
//        Cursor cursor = contentResolver.query(
//                uri,
//                new String[]{ContactsContract.Contacts.DISPLAY_NAME},
//                ContactsContract.Contacts._ID + " = ?",
//                new String[]{String.valueOf(contactId)},
//                null
//        );
//
//        if (cursor != null && cursor.moveToFirst()) {
//            String contactName = cursor.getString(0);
//            Log.d(TAG, "Tên liên hệ: " + contactName);
//            cursor.close();
//        }

//        // Lấy số điện thoại
//        uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
//        cursor = contentResolver.query(
//                uri,
//                new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},
//                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
//                new String[]{String.valueOf(contactId)},
//                null
//        );
//
//        while (cursor != null && cursor.moveToNext()) {
//            String phoneNumber = cursor.getString(0);
//            Log.d(TAG, "Số điện thoại: " + phoneNumber);
//        }
//        if (cursor != null) cursor.close();

//        // Lấy email
//        uri = ContactsContract.CommonDataKinds.Email.CONTENT_URI;
//        cursor = contentResolver.query(
//                uri,
//                new String[]{ContactsContract.CommonDataKinds.Email.ADDRESS},
//                ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
//                new String[]{String.valueOf(contactId)},
//                null
//        );
//
//        while (cursor != null && cursor.moveToNext()) {
//            String email = cursor.getString(0);
//            Log.d(TAG, "Email: " + email);
//        }
//        if (cursor != null) cursor.close();
    }

    // Xử lý kết quả khi người dùng cấp hoặc từ chối quyền (optional)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CONTACT_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("TAG", "Quyền truy cập danh bạ đã được cấp");
            } else {
                Log.e("TAG", "Quyền truy cập danh bạ bị từ chối");
            }
        }
    }
}