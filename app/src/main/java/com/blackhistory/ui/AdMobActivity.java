package com.blackhistory.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blackhistory.R;
import com.blackhistory.db.DbOpenHelper;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AdMobActivity extends BaseActivity {


    private RewardedVideoAd mRewardedVideoAd;
    CountDownTimer mCountDown = null;

    @BindView(R.id.count)
    TextView count;
    @BindView(R.id.arith_layer)
    LinearLayout arith_layer;
    @BindView(R.id.result_btn)
    Button result_btn;
    @BindView(R.id.admob_btn)
    Button admob_btn;


    @BindView(R.id.x_tv)
    TextView x_tv;
    @BindView(R.id.y_tv)
    TextView y_tv;
    @BindView(R.id.arith_tv)
    TextView arith_tv;
    @BindView(R.id.arithResult_et)
    EditText arithResult_et;

    @BindView(R.id.isoutgoing)
    LinearLayout isoutgoing;

    @BindView(R.id.isdefault)
    LinearLayout isdefault;

    int timer = 20;
    int x = (int) (Math.random() * 99);
    int y = (int) (Math.random() * 99);
    int arithTemp = (int) (Math.random() * 4);
    String arith;
    int result;


    Bundle b;
    String id, name, number;
    String type;

    private DbOpenHelper mDbOpenHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admob);


        ButterKnife.bind(this);





        mDbOpenHelper = new DbOpenHelper(this);
        mDbOpenHelper.open();
        mDbOpenHelper.create();

        Intent data = getIntent();
        b = data.getExtras();
        type = String.valueOf(b.get("type"));

         if(type.equals("default")) {

            isoutgoing.setVisibility(View.GONE);
            isdefault.setVisibility(View.VISIBLE);
        } else {

            isdefault.setVisibility(View.GONE);
            isoutgoing.setVisibility(View.VISIBLE);

            id = (String) b.get("id");
            name = (String) b.get("name");
            number = (String) b.get("number");


        }







        MobileAds.initialize(this, "ca-app-pub-2054632754477298~4404064856");
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("6AB578F6D2E60C5CEB141F3E3564A9C3").build();
        mAdView.loadAd(adRequest);


        // Use an activity context to get the rewarded video instance.
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(new RewardedVideoAdListener() {
            @Override
            public void onRewarded (RewardItem reward){
//                Toast.makeText(AdMobActivity.this, "onRewarded! currency: " + reward.getType() + "  amount: " +
//                        reward.getAmount(), Toast.LENGTH_SHORT).show();
                // Reward the user.
                admob_btn.setVisibility(View.GONE);
                count.setVisibility(View.GONE);
                arith_layer.setVisibility(View.VISIBLE);
            }

            @Override
            public void onRewardedVideoAdLeftApplication () {
//                Toast.makeText(AdMobActivity.this, "onRewardedVideoAdLeftApplication",
//                        Toast.LENGTH_SHORT).show();
            }


            @Override
            public void onRewardedVideoAdClosed () {
                // Load the next rewarded video ad.
               // Toast.makeText(AdMobActivity.this, "close", Toast.LENGTH_SHORT).show();
                loadRewardedVideoAd();

            }
            @Override
            public void onRewardedVideoAdFailedToLoad ( int errorCode){
//                Toast.makeText(AdMobActivity.this, "onRewardedVideoAdFailedToLoad", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRewardedVideoAdLoaded () {
//                Toast.makeText(AdMobActivity.this, "onRewardedVideoAdLoaded", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRewardedVideoAdOpened () {
//                Toast.makeText(AdMobActivity.this, "onRewardedVideoAdOpened", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRewardedVideoStarted () {
//                Toast.makeText(AdMobActivity.this, "onRewardedVideoStarted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRewardedVideoCompleted () {
              //  Toast.makeText(AdMobActivity.this, "onRewardedVideoCompleted", Toast.LENGTH_SHORT).show();
            }
        });


        loadRewardedVideoAd();


switch (arithTemp) {
    case 0:
        arith = "*";
        result = x * y;
        break;
//    case 2:
//        arith = "-";
//        result = x - y;
//        if (result < 0) {
//            result = result * -1;
//        }
//        break;
//    case 3:
//        arith = "*";
//        result = x * y;
//        break;
//    case 4:
//        arith = "/";
//        result = x / y;
//        break;
default:
    arith = "*";
    result = x * y;

}


        x_tv.setText(String.valueOf(x));
        y_tv.setText(String.valueOf(y));
        arith_tv.setText(arith);






    }   //onCreate



    @OnClick(R.id.admob_btn)
    public void admob_btnClicked() {
        if (mRewardedVideoAd.isLoaded()) {
            mRewardedVideoAd.show();
        } else {
            admob_btn.setVisibility(View.GONE);
            countDown();
        }


    }

    public void countDown() {
        count.setVisibility(View.VISIBLE);
        arith_layer.setVisibility(View.GONE);
        mCountDown = new CountDownTimer(20000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                timer -= 1;
                count.setText(String.valueOf(timer));
                Log.d("~!", String.valueOf(timer));
            }

            @Override
            public void onFinish() {
                count.setVisibility(View.GONE);
                arith_layer.setVisibility(View.VISIBLE);
            }

        }.start();
    }

    @OnClick(R.id.cancel_btn)
    public void cancel_btnClicked() {

   finish();
    }

    @OnClick(R.id.result_btn)
    public void result_btnClicked() {
       if(arithResult_et.getText().toString().equals(String.valueOf(result))) {
            if (type.equals("default")) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(arithResult_et.getWindowToken(), 0);

                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            } else {


                mDbOpenHelper.deleteColumn(Long.parseLong(id));
                Toast.makeText(this, name + " : " + number + " 가 삭제 되었습니다.", Toast.LENGTH_LONG).show();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(arithResult_et.getWindowToken(), 0);

                // Log.d("~!", name_et.getText().toString());
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }



       } else {
           Toast.makeText(this, "정답이 아닙니다 !", Toast.LENGTH_SHORT).show();
           InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
           imm.hideSoftInputFromWindow(arithResult_et.getWindowToken(), 0);

       }
    }



    @Override
    public void onResume() {



        mRewardedVideoAd.resume(this);
        super.onResume();
    }

    @Override
    public void onPause() {
        mRewardedVideoAd.pause(this);
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mRewardedVideoAd.setRewardedVideoAdListener(null);
        mRewardedVideoAd.destroy(this);
        super.onDestroy();
        try{
            mCountDown.cancel();
        } catch (Exception e) {}
        mCountDown = null;


    }

    private void loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd(("ca-app-pub-2054632754477298/3186228241"),
                new AdRequest.Builder().build());

    }


    }
