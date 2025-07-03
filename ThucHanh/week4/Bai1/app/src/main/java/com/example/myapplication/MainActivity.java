package com.example.myapplication;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Debug;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 1;
    private BroadcastReceiver smsReceiver;
    private TextView tvContent;  // TextView để hiển thị tin nhắn

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Khởi tạo TextView
        tvContent = findViewById(R.id.tv_content);

        // Kiểm tra và yêu cầu quyền RECEIVE_SMS
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS}, PERMISSION_REQUEST_CODE);
        } else {
            // Đăng ký BroadcastReceiver nếu quyền đã được cấp
            registerSmsReceiver();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Đăng ký lại receiver khi Activity được mở lại
        registerSmsReceiver();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Hủy đăng ký receiver khi Activity không còn hiển thị
        if (smsReceiver != null) {
            unregisterReceiver(smsReceiver);
        }
    }

    // Hàm xử lý BroadcastReceiver
    private void registerSmsReceiver() {
        IntentFilter filter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        smsReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                processReceive(context, intent);
            }
        };
        registerReceiver(smsReceiver, filter);
    }

    private long lastReceivedTime = 0;
    // Hàm xử lý tin nhắn SMS khi nhận được
    public void processReceive(Context context, Intent intent) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastReceivedTime < 500) {
            // Nếu trong vòng 500ms đã nhận rồi thì bỏ qua
            return;
        }
        lastReceivedTime = currentTime;

        // Hiển thị Toast thông báo nhận tin nhắn
        Toast.makeText(context, "You have a new message!", Toast.LENGTH_LONG).show();

        Bundle bundle = intent.getExtras();
        if (bundle == null) return;

        Object[] pdus = (Object[]) bundle.get("pdus");
        StringBuilder sms = new StringBuilder();

        if (pdus != null) {
            for (Object pduObj : pdus) {
                byte[] pdu = (byte[]) pduObj;
                SmsMessage smsMessage = SmsMessage.createFromPdu(pdu);
                String address = smsMessage.getDisplayOriginatingAddress();
                String messageBody = smsMessage.getMessageBody();

                sms.append(address).append(": ").append(messageBody).append("\n");
            }
        }

        // Lấy nội dung hiện tại của TextView và nối thêm tin nhắn mới
        String currentText = tvContent.getText().toString();
        String updatedText = currentText + "\n" + sms.toString();

        // Cập nhật TextView với nội dung mới
        tvContent.setText(updatedText);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Quyền được cấp, đăng ký receiver
                registerSmsReceiver();
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
