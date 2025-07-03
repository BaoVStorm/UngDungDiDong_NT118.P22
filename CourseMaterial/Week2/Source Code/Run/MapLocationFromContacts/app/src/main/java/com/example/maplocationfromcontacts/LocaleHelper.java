package com.example.maplocationfromcontacts;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import java.util.Locale;

public class LocaleHelper {
    public static void setLocale(Context context, String langCode) {
        // Tạo đối tượng Locale mới với mã ngôn ngữ (langCode).
        Locale locale = new Locale(langCode);
        // Đặt Locale đó làm mặc định
        Locale.setDefault(locale);

        // Lấy Resources của context
        Resources resources = context.getResources();
        Configuration config = resources.getConfiguration();
        // Cập nhật Configuration với locale mới.
        config.setLocale(locale);
        // Áp dụng ngôn ngữ mới.
        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }
}
