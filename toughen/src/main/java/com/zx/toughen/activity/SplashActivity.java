package com.zx.toughen.activity;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.animation.BounceInterpolator;


import com.zx.toughen.base.BaseActivity;
import com.zx.toughen.R;

import java.util.ArrayList;

public class SplashActivity extends BaseActivity {
    private View app_name_textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        app_name_textview = findViewById(R.id.app_name_textview);
    }

    @Override
    public void onAttachedToWindow() {
        startAnimation();//Property Animation
    }

    private void startAnimation() {
        ObjectAnimator animator1X = ObjectAnimator.ofFloat(app_name_textview, "scaleX", 0, 1);
        ObjectAnimator animator1Y = ObjectAnimator.ofFloat(app_name_textview, "scaleY", 0, 1);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(app_name_textview, "alpha", 0, 1);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(animator1X).with(animator1Y).with(animator2);
        animatorSet.setDuration(2000);
        animatorSet.setInterpolator(new BounceInterpolator());
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    initPermission();
                else {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animatorSet.start();
    }


    @Override
    public void initView() {

    }

    @Override
    public void setListener() {

    }

    @Override
    public void onBackPressed() {
    }

    /**
     * android 6.0 以上需要动态申请权限
     */
    private void initPermission() {
        String permissions[] = {Manifest.permission.RECORD_AUDIO,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.INTERNET,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        ArrayList<String> toApplyList = new ArrayList<String>();
        for (String perm : permissions) {
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(this, perm)) {
                toApplyList.add(perm);
                //进入到这里代表没有权限.
            }
        }
        String tmpList[] = new String[toApplyList.size()];
        if (!toApplyList.isEmpty()) {
            ActivityCompat.requestPermissions(this, toApplyList.toArray(tmpList), 123);
        } else {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Log.e(SplashActivity.class.getSimpleName(), "permissions==" + permissions);
        Log.e(SplashActivity.class.getSimpleName(), "grantResults==" + grantResults);
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
    }
}