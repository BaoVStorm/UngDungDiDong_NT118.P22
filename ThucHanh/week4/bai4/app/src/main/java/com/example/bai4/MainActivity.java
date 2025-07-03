package com.example.bai4;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.Manifest;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class MainActivity extends AppCompatActivity {

    private ReentrantLock reentrantLock;
    private Switch swAutoResponse;
    private LinearLayout llButtons;
    private Button btnSafe, btnMayday;
    private ArrayList<String> requesters;
    private ArrayAdapter<String> adapter;
    private ListView lvMessages;
    private BroadcastReceiver broadcastReceiver;
    public static boolean isRunning;
    private SharedPreferences.Editor editor;
    private SharedPreferences sharedPreferences;
    private final String AUTO_RESPONSE = "auto_response";
    private static final int NOTIFICATION_PERMISSION_CODE = 1001;

    private static final long MIN_TIME_BETWEEN_RESPONSES = 1000; // 1 giây
    private long lastResponseTime = 0;

    private void findViewsByIds() {
        swAutoResponse = (Switch) findViewById(R.id.sw_auto_response);
        llButtons = (LinearLayout) findViewById(R.id.ll_buttons);
        lvMessages = (ListView) findViewById(R.id.lv_messages);
        btnSafe = (Button) findViewById(R.id.btn_safe);
        btnMayday = (Button) findViewById(R.id.btn_mayday);
    }

    private void respond (String to, String response) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastResponseTime < MIN_TIME_BETWEEN_RESPONSES) {
            return; // Bỏ qua nếu vừa phản hồi gần đây
        }
        lastResponseTime = currentTime;

        reentrantLock.lock();
        requesters.remove(to);
        adapter.notifyDataSetChanged();
        reentrantLock.unlock();

        // Send the message
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(to, null, response, null, null);
    }

    public void respond (boolean ok) {
        String okString = getString(R.string.i_am_safe_and_well_worry_not);
        String notOkString = getString(R.string.tell_my_mother_i_love_her);
        String outputString = ok ? okString : notOkString;
        ArrayList<String> requestersCopy =
                (ArrayList<String>) requesters.clone();

        for (String to : requestersCopy)
            respond (to, outputString);
    }

    public void processReceiveAddresses(ArrayList<String> addresses) {
        for (int i = 0; i < addresses.size(); i++) {
            if (!requesters.contains(addresses.get(i))) {
                reentrantLock.lock();
                requesters.add(addresses.get(i));
                adapter.notifyDataSetChanged();
                reentrantLock.unlock();
            }

            // Check to response automatically
            if (swAutoResponse.isChecked())
                respond(true);
        }
    }

    private void handleOnClickListenner() {
        // Handle onClickListenner
        btnSafe.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                respond(true);
            }
        });

        btnMayday.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                respond (false);
            }
        });

        swAutoResponse.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged (CompoundButton buttonView, boolean isChecked) {

                if (isChecked)
                    llButtons.setVisibility(View.GONE);
                else
                    llButtons.setVisibility(View.VISIBLE);

                // Save auto response setting
                editor.putBoolean(AUTO_RESPONSE, isChecked);
                editor.commit();
            }
        });
    }

    private void initBroadcastReceiver() {
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive (Context context, Intent intent) {
                // Get ArrayList addresses
                ArrayList<String> addresses = intent.
                        getStringArrayListExtra(SmsReceiver.SMS_MESSAGE_ADDRESS_KEY);

                // Process these addresses
                processReceiveAddresses(addresses);
            }
        };
    }

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    protected void onResume() {
        super.onResume();
        isRunning = true;

        // Make sure broadcastReceiver was inited
        if (broadcastReceiver == null)
            initBroadcastReceiver();

        // RegisterReceiver
        IntentFilter intentFilter = new IntentFilter(SmsReceiver.SMS_FORWARD_BROADCAST_RECEIVER);

        // Chỉ cần nếu bạn target API 33+
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            registerReceiver(broadcastReceiver, intentFilter, Context.RECEIVER_NOT_EXPORTED);
//        } else {
//            registerReceiver(broadcastReceiver, intentFilter);
//        }
//        registerReceiver(broadcastReceiver, intentFilter, Context.RECEIVER_EXPORTED);

        registerReceiver(broadcastReceiver, intentFilter, Context.RECEIVER_EXPORTED);

    }

    @Override
    protected void onStop() {
        super.onStop();
        isRunning = false;
        // UnregisterReceiver
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
        }
    }

    private void initVariables() {
        sharedPreferences = getPreferences(MODE_PRIVATE);
        editor = sharedPreferences.edit();
        reentrantLock = new ReentrantLock();
        requesters = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, requesters);
        lvMessages.setAdapter(adapter);

        // Load auto response setting
        boolean autoResponse = sharedPreferences.getBoolean(AUTO_RESPONSE, false);
        swAutoResponse.setChecked(autoResponse);
        if (autoResponse)
            llButtons.setVisibility(View.GONE);

        initBroadcastReceiver();

        // Send addresses to broadcastReceiver
        Intent i = getIntent();
        ArrayList<String> addresses = i.getStringArrayListExtra(SmsReceiver.SMS_MESSAGE_ADDRESS_KEY);
        if (addresses != null) {
            processReceiveAddresses(addresses);
        }
    }

//    private boolean hasProcessedInitialIntent = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewsByIds();
        initVariables();
        handleOnClickListenner();

        // xin quyền từ người dùng
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECEIVE_SMS, Manifest.permission.SEND_SMS},
                    1);
        }

        // Chỉ yêu cầu trên Android 13 trở lên
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Kiểm tra nếu chưa có quyền
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {

                // Yêu cầu quyền đơn giản không giải thích
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS},
                        NOTIFICATION_PERMISSION_CODE
                );
            }
        }

        // Nhằm hiển thị số gửi tin nhắn khi app vừa được khởi động.
        Intent i = getIntent();
        ArrayList<String> addresses = i.getStringArrayListExtra(SmsReceiver.SMS_MESSAGE_ADDRESS_KEY);
        if (addresses != null) {
            processReceiveAddresses(addresses);
        }
    }


}