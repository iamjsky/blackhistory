package com.blackhistory.ui;


import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blackhistory.R;
import com.blackhistory.adapter.UserListAdapter;
import com.blackhistory.api.MainBannerApi;
import com.blackhistory.db.DbOpenHelper;
import com.blackhistory.model.MainBannerModel;
import com.blackhistory.model.UserListModel;
import com.bumptech.glide.Glide;
import com.synnapps.carouselview.CarouselView;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

public class MainActivity extends BaseActivity {

    ArrayList<UserListModel> userList = new ArrayList();

    @BindView(R.id.carouselView)
    CarouselView carouselView;


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.emptymsg_tv)
    TextView emptymsg_tv;

    @BindView(R.id.arrow_iv)
    ImageView arrow_iv;


    UserListAdapter adapter;
    DbOpenHelper mDbOpenHelper;

    private static int SORT = 0;    //0:등록일순-내림차순, 1:등록일순-오름차순, 2:이름가나다순 ......

    public boolean isCheckedSwitch = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        mDbOpenHelper = new DbOpenHelper(this);
        mDbOpenHelper.open();
        mDbOpenHelper.create();

        adapter = new UserListAdapter(this);


        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapter);

        getUserList();




    }   // onCreate

    @Override
    protected void onResume() {
        super.onResume();
//        session.setWeekTime(2);
        refresh();


    }





    private void mainBanner() {
        MainBannerApi.getMainBanner().subscribe(new Subscriber<List<MainBannerModel>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<MainBannerModel> mainBannerModels) {

                carouselView.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {

                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });
                carouselView.setImageListener((position, imageView) -> {

                    // GradientDrawable drawable = (GradientDrawable) ContextCompat.getDrawable(MainActivity.this, R.drawable.rounded_8dp);
                    // imageView.setBackground(drawable);
                    Glide.with(MainActivity.this).load(mainBannerModels.get(position).getImgurl()).into(imageView);


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        imageView.setClipToOutline(true);
                    }
                });
                carouselView.setPageCount(mainBannerModels.size());

            }
        });
    }

    public void getUserList() {


        Cursor iCursor = mDbOpenHelper.selectColumns();
        while (iCursor.moveToNext()) {
            long tempId = iCursor.getLong(iCursor.getColumnIndex("_id"));
            String tempName = iCursor.getString(iCursor.getColumnIndex("name"));
            String tempNumber = iCursor.getString(iCursor.getColumnIndex("number"));
            String tempMemo = iCursor.getString(iCursor.getColumnIndex("memo"));
            String tempCreatedAt = iCursor.getString(iCursor.getColumnIndex("createdat"));


            userList.add(new UserListModel(tempId, tempName, tempMemo, tempNumber, tempCreatedAt));

        }

        if (userList.size() == 0) {
            emptymsg_tv.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            emptymsg_tv.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);

//            sort_layerClicked();
            adapter.setData(userList);
            adapter.notifyDataSetChanged();

        }

    }

    public void refresh() {
        mainBanner();

    }

    @OnClick(R.id.adduser_btn)
    public void adduser_btnClicked() {
        Intent intent = new Intent(MainActivity.this, UserActivity.class);
        intent.putExtra("key", "add");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);


    }

//    @OnClick(R.id.sort_layer)
//    public void sort_layerClicked() {
//
//        switch (SORT) {
//            case 0:
//                Glide.with(MainActivity.this).load(R.drawable.icn_downarrow).into(arrow_iv);
//                SORT = 1;
//                Collections.reverse(userList);
//                adapter.notifyDataSetChanged();
//                break;
//
//            case 1:
//                Glide.with(MainActivity.this).load(R.drawable.icn_uparrow).into(arrow_iv);
//                SORT = 0;
//                Collections.sort(userList, sortByDate);
//                adapter.notifyDataSetChanged();
//                break;
//
//            default:
//                Glide.with(MainActivity.this).load(R.drawable.icn_downarrow).into(arrow_iv);
//                SORT = 0;
//
//
//        }
//
//
//    }

    private final static Comparator<UserListModel> sortByDate = new Comparator<UserListModel>() {
        @Override
        public int compare(UserListModel o1, UserListModel o2) {
            return Collator.getInstance().compare(o1.getCreatedat(), o2.getCreatedat());
        }
    };


}
