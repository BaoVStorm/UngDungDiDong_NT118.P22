package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private Button btnFadeInXml, btnFadeInCode, btnFadeOutXml, btnFadeOutCode,
            btnBlinkXml,
            btnBlinkCode, btnZoomInXml, btnZoomInCode, btnZoomOutXml,
            btnZoomOutCode, btnRotateXml,
            btnRotateCode, btnMoveXml, btnMoveCode, btnSlideUpXml, btnSlideUpCode,
            btnBounceXml,
            btnBounceCode, btnCombineXml, btnCombineCode;

    private ImageView ivUitLogo;
    private Animation.AnimationListener animationListener;

    private void findViewsByIds() {
        ivUitLogo = (ImageView) findViewById(R.id.iv_uit_logo);
        btnFadeInXml = (Button) findViewById(R.id.btn_fade_in_xml);
        btnFadeInCode = (Button) findViewById(R.id.btn_fade_in_code);
        btnFadeOutXml = (Button) findViewById(R.id.btn_fade_out_xml);
        btnFadeOutCode = (Button) findViewById(R.id.btn_fade_out_code);
        btnBlinkXml = (Button) findViewById(R.id.btn_blink_xml);
        btnBlinkCode = (Button) findViewById(R.id.btn_blink_code);
        btnZoomInXml = (Button) findViewById(R.id.btn_zoom_in_xml);
        btnZoomInCode = (Button) findViewById(R.id.btn_zoom_in_code);
        btnZoomOutXml = (Button) findViewById(R.id.btn_zoom_out_xml);
        btnZoomOutCode = (Button) findViewById(R.id.btn_zoom_out_code);
        btnRotateXml = (Button) findViewById(R.id.btn_rotate_xml);
        btnRotateCode = (Button) findViewById(R.id.btn_rotate_code);
        btnMoveXml = (Button) findViewById(R.id.btn_move_xml);
        btnMoveCode = (Button) findViewById(R.id.btn_move_code);
        btnSlideUpXml = (Button) findViewById(R.id.btn_slide_up_xml);
        btnSlideUpCode = (Button) findViewById(R.id.btn_slide_up_code);
        btnBounceXml = (Button) findViewById(R.id.btn_bounce_xml);
        btnBounceCode = (Button) findViewById(R.id.btn_bounce_code);
        btnCombineXml = (Button) findViewById(R.id.btn_combine_xml);
        btnCombineCode = (Button) findViewById(R.id.btn_combine_code);
    }

    private void initVariables() {
        animationListener = new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                Toast.makeText(getApplicationContext(), "Animation Stopped",
                        Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        };
    }

    // ------------------------------------ xml

    private void handleClickAnimationXml(Button btn, int animId)
    {
        // load the animation
        final Animation animation = AnimationUtils.loadAnimation(MainActivity.this,
                animId);

        // set animation listener
        animation.setAnimationListener(animationListener);

        // Handle onclickListenner to start animation
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivUitLogo.startAnimation(animation);
            }
        });
    }

    // ------------------------------------ code
    private void handleClickAnimationCode(Button btn, final Animation animation) {
        // Handle onclickListenner to start animation
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivUitLogo.startAnimation(animation);
            }
        });
    }

    private Animation initFadeInAnimation()
    {
        AlphaAnimation animation = new AlphaAnimation(0f, 1f);
        animation.setDuration(1000);
        animation.setFillAfter(true);
        animation.setAnimationListener(animationListener);
        return animation;
    }

    private Animation initBlinkAnimation() {
        AlphaAnimation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(300);
        animation.setRepeatMode(Animation.REVERSE);
        animation.setRepeatCount(3);
        return animation;
    }
    private Animation initBounceAnimation() {
        ScaleAnimation animation = new ScaleAnimation(
                1.0f, 1.0f, // X: no scale change
                0.0f, 1.0f, // Y: from 0 to normal
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f);
        animation.setDuration(500);
        animation.setFillAfter(true);
        animation.setInterpolator(new BounceInterpolator());
        return animation;
    }
    private Animation initCombineAnimation() {
        AnimationSet set = new AnimationSet(true);
        set.setFillAfter(true);
        set.setInterpolator(new LinearInterpolator());

        ScaleAnimation scale = new ScaleAnimation(
                1f, 3f, 1f, 3f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        scale.setDuration(4000);

        RotateAnimation rotate = new RotateAnimation(
                0f, 360f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(500);
        rotate.setRepeatCount(2);
        rotate.setRepeatMode(Animation.RESTART);

        set.addAnimation(scale);
        set.addAnimation(rotate);
        return set;
    }
    private Animation initFadeOutAnimation() {
        AlphaAnimation animation = new AlphaAnimation(1.0f, 0.0f);
        animation.setDuration(1000);
        animation.setFillAfter(true);
        return animation;
    }
    private Animation initMoveAnimation() {
        TranslateAnimation animation = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.75f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        animation.setDuration(800);
        animation.setFillAfter(true);
        animation.setInterpolator(new LinearInterpolator());
        return animation;
    }
    private Animation initRotateAnimation() {
        RotateAnimation animation = new RotateAnimation(
                0f, 360f, // Từ 0 đến 360 độ
                Animation.RELATIVE_TO_SELF, 0.5f, // pivot X tại giữa
                Animation.RELATIVE_TO_SELF, 0.5f  // pivot Y tại giữa
        );
        animation.setDuration(600);

        animation.setRepeatMode(Animation.RESTART);
        animation.setRepeatCount(2);
        animation.setInterpolator(new LinearInterpolator()); // giống android:anim/cycle_interpolator
        return animation;
    }

    private Animation initSlideUpAnimation() {
        ScaleAnimation animation = new ScaleAnimation(
                1.0f, 1.0f,
                1.0f, 0.0f,
                Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 0f);
        animation.setDuration(500);
        animation.setFillAfter(true);
        return animation;
    }
    private Animation initZoomInAnimation() {
        ScaleAnimation animation = new ScaleAnimation(
                1.0f, 3.0f,
                1.0f, 3.0f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(1000);
        animation.setFillAfter(true);
        return animation;
    }
    private Animation initZoomOutAnimation() {
        ScaleAnimation animation = new ScaleAnimation(
                1.0f, 0.5f,
                1.0f, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(1000);
        animation.setFillAfter(true);
        return animation;
    }


    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewsByIds();
        initVariables();

        // HandleClickAnimationXML
        handleClickAnimationXml (btnFadeInXml, R.anim.anim_fade_in);
        handleClickAnimationXml (btnFadeOutXml, R.anim.anim_fade_out);
        handleClickAnimationXml (btnBlinkXml, R.anim.anim_blink);
        handleClickAnimationXml (btnZoomInXml, R.anim.anim_zoom_in);
        handleClickAnimationXml (btnZoomOutXml, R.anim.anim_zoom_out);
        handleClickAnimationXml (btnRotateXml, R.anim.anim_rotate);
        handleClickAnimationXml (btnMoveXml, R.anim.anim_move);
        handleClickAnimationXml (btnSlideUpXml, R.anim.anim_slide_up);
        handleClickAnimationXml (btnBounceXml, R.anim.anim_bounce);
        handleClickAnimationXml (btnCombineXml, R.anim.anim_combine);

        // HandleClickAnimationCode
        handleClickAnimationCode (btnFadeInCode, initFadeInAnimation());
        handleClickAnimationCode(btnFadeOutCode, initFadeOutAnimation());
        handleClickAnimationCode(btnBlinkCode, initBlinkAnimation());
        handleClickAnimationCode(btnZoomInCode, initZoomInAnimation());
        handleClickAnimationCode(btnZoomOutCode, initZoomOutAnimation());
        handleClickAnimationCode(btnRotateCode, initRotateAnimation());
        handleClickAnimationCode(btnMoveCode, initMoveAnimation());
        handleClickAnimationCode(btnSlideUpCode, initSlideUpAnimation());
        handleClickAnimationCode(btnBounceCode, initBounceAnimation());
        handleClickAnimationCode(btnCombineCode, initCombineAnimation());

        // go to new activity
        ivUitLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }
}