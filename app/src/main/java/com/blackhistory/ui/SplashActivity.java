package com.blackhistory.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.blackhistory.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SplashActivity extends BaseActivity {

    @BindView(R.id.splash_iv)
    ImageView splash_iv;
    int randomSplash = (int) (Math.random() * 3);
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS  = {Manifest.permission.PROCESS_OUTGOING_CALLS,
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_CONTACTS};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

//        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.alpha);
//        splash_iv.startAnimation(animation);










        Log.d("~!", String.valueOf(randomSplash));
        switch (randomSplash) {

            case 0:
                splash_iv.setImageResource(R.drawable.bg_splash_01);
                break;
            case 1:
                splash_iv.setImageResource(R.drawable.bg_splash_02);
                break;
            case 2:
                splash_iv.setImageResource(R.drawable.bg_splash_03);
                break;
            default:
                splash_iv.setImageResource(R.drawable.bg_splash_01);
        }



        int processOutgoingCallPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.PROCESS_OUTGOING_CALLS);
        int writeExternalStoragePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readContatsPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);


        if ( processOutgoingCallPermission == PackageManager.PERMISSION_GRANTED
                && writeExternalStoragePermission == PackageManager.PERMISSION_GRANTED && readContatsPermission == PackageManager.PERMISSION_GRANTED) {

            splashStart();

        }else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])
                    || ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[1])) {

                Toast.makeText(this, "이 앱을 실행하려면 접근 권한이 필요합니다.",
                        Toast.LENGTH_SHORT).show();

                        ActivityCompat.requestPermissions( this, REQUIRED_PERMISSIONS,
                                PERMISSIONS_REQUEST_CODE);



            } else {
                // 2. 사용자가 퍼미션 거부를 한 적이 없는 경우에는 퍼미션 요청을 바로 합니다.
                // 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions( this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            }

        }



    }   //OnCreate




    @Override
    protected void onResume() {
        super.onResume();






    }


    public void splashStart() {
        rx.Observable.timer(2000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        long now = System.currentTimeMillis();
                        Date date = new Date(now);
                        SimpleDateFormat sdf = new SimpleDateFormat("HHmm");
                        String getTime = sdf.format(date);
                        long time = Long.parseLong(getTime);
                        if (time <= Long.parseLong(getString(R.string.end_time)) || time >= Long.parseLong(getString(R.string.start_time))) {
                            Intent intent = new Intent(SplashActivity.this, AdMobActivity.class);
                            intent.putExtra("type", "default");
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        } else {
                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        }

                    }
                });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grandResults) {

        if ( requestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length) {

            boolean check_result = true;

            for (int result : grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }
            }

            if ( check_result ) {
                splashStart();
            }
            else {

                if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])
                        || ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[1])) {

                    Toast.makeText(this, "권한이 거부되었습니다. 앱을 다시 실행하여 권한을 허용해주세요. ",
                            Toast.LENGTH_SHORT).show();


                            finish();



                }else {

                    Toast.makeText(this, "설정(앱 정보)에서 권한을 허용해야 합니다. ",
                            Toast.LENGTH_SHORT).show();



                            finish();

                }
            }

        }


    }




}
