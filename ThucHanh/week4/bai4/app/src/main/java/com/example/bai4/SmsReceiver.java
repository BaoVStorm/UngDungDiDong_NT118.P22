package com.example.bai4;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;

import androidx.core.app.NotificationCompat;

import java.util.ArrayList;

public class SmsReceiver extends BroadcastReceiver {
    public static final String SMS_FORWARD_BROADCAST_RECEIVER = "sms_forward_broadcast_receiver";
    public static final String SMS_MESSAGE_ADDRESS_KEY = "sms_messages_key";

    private static final long MIN_TIME_BETWEEN_SMS = 500; // 500ms
    private static long lastReceivedTime = 0;

    @Override
    public void onReceive(Context context, Intent intent) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastReceivedTime < MIN_TIME_BETWEEN_SMS) {
            return; // Bỏ qua nếu nhận quá nhanh
        }
        lastReceivedTime = currentTime;

        String queryString = "Are you OK?".toLowerCase();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            Object[] pdus = (Object[]) bundle.get("pdus");
            String format = bundle.getString("format");
            SmsMessage[] messages = new SmsMessage [pdus.length];

            for (int i = 0; i < pdus.length; i++) {
                SmsMessage smsMessage;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    smsMessage = SmsMessage.createFromPdu((byte[]) pdus[i], format);
                } else {
                    smsMessage = SmsMessage.createFromPdu((byte[]) pdus[i]);
                }
                messages[i] = smsMessage;
            }

            // Create ArrayList of OriginatingAddress of messages which contain queryString
            ArrayList<String> addresses = new ArrayList<>();

            for (SmsMessage message : messages) {
                if (message.getMessageBody().toLowerCase().contains (queryString)) {
                    addresses.add(message.getOriginatingAddress());
                }
            }

            if (!addresses.isEmpty()) {
                if (!MainActivity.isRunning) {
                    NotificationManager notificationManager =
                            (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

// Tạo channel cho Android O+
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        NotificationChannel channel = new NotificationChannel(
                                "emergency_channel",
                                "Emergency Alerts",
                                NotificationManager.IMPORTANCE_HIGH
                        );
                        notificationManager.createNotificationChannel(channel);
                    }

                    Intent iMainActivity = new Intent(context, MainActivity.class);
                    iMainActivity.putStringArrayListExtra(SMS_MESSAGE_ADDRESS_KEY, addresses);
                    iMainActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                            Intent.FLAG_ACTIVITY_CLEAR_TASK |
                            Intent.FLAG_ACTIVITY_SINGLE_TOP);

                    PendingIntent pendingIntent = PendingIntent.getActivity(
                            context,
                            0,
                            iMainActivity,
                            PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
                    );

                    NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "emergency_channel")
                            .setSmallIcon(android.R.drawable.ic_dialog_alert)
                            .setContentTitle("Emergency Alert")
                            .setContentText("You received an emergency message")
                            .setPriority(NotificationCompat.PRIORITY_MAX)
                            .setContentIntent(pendingIntent)
                            .setAutoCancel(true)
                            .setFullScreenIntent(pendingIntent, true); // Quan trọng: Hiển thị ngay lập tức

                    notificationManager.notify(0, builder.build());

                } else {
                    // Forward these addresses to MainActivity to process
                    Intent iForwardBroadcastReceiver = new Intent(SMS_FORWARD_BROADCAST_RECEIVER);
                    iForwardBroadcastReceiver.putStringArrayListExtra(SMS_MESSAGE_ADDRESS_KEY, addresses);
                    context.sendBroadcast (iForwardBroadcastReceiver);
                }
            }
        }
    }
}
