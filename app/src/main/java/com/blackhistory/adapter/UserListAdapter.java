package com.blackhistory.adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blackhistory.R;
import com.blackhistory.model.UserListModel;
import com.blackhistory.ui.UserActivity;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> {

    List<UserListModel> data;
    Context context;


    public UserListAdapter(Context context) {
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_userlist_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        UserListModel userListModel = data.get(position);

        holder.number_tv.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        holder.name_tv.setText(userListModel.getName());
        holder.memo_tv.setText(userListModel.getMemo());
        holder.number_tv.setText(userListModel.getNumber());

        String createdAt = userListModel.getCreatedat();
        String[] arr = createdAt.split(":");

        SimpleDateFormat beforeFormat = new SimpleDateFormat("yyyymmdd");
        SimpleDateFormat afterFormat = new SimpleDateFormat("yyyy. mm. dd");

        java.util.Date tempDate = null;

        try {
            tempDate = beforeFormat.parse(arr[0]);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String transDate = afterFormat.format(tempDate);



        holder.createdat_tv.setText(transDate);

        holder.item_layout.setOnClickListener(v -> {

            context.startActivity(new Intent(context, UserActivity.class)
                    .putExtra("key", "modify")
                    .putExtra("id", userListModel.getId())
                    .putExtra("name", userListModel.getName())
                    .putExtra("memo", userListModel.getMemo())
                    .putExtra("number", userListModel.getNumber())


                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

        });

    }

    @Override
    public int getItemCount() {
        return data != null ? data.size() : 0;
    }

    public void setData(List<UserListModel> userListModels) {
        this.data = userListModels;
    }

    public void addData(List<UserListModel> models) {
        this.data.addAll(models);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name_tv)
        TextView name_tv;
        @BindView(R.id.memo_tv)
        TextView memo_tv;
        @BindView(R.id.number_tv)
        TextView number_tv;
        @BindView(R.id.createdat_tv)
        TextView createdat_tv;
        @BindView(R.id.item_layout)
        LinearLayout item_layout;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}