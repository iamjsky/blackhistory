package com.blackhistory.ui;

import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.blackhistory.R;
import com.blackhistory.db.DbOpenHelper;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserActivity extends BaseActivity {

    @BindView(R.id.name_et)
    EditText name_et;

    @BindView(R.id.number_et)
    EditText number_et;

    @BindView(R.id.memo_et)
    EditText memo_et;

    @BindView(R.id.cancel_btn)
    Button cancel_btn;
    @BindView(R.id.add_btn)
    Button add_btn;
    @BindView(R.id.modify_btn)
    Button modify_btn;
    @BindView(R.id.delete_btn)
    Button delete_btn;

    Bundle b;
    String id, name, memo, number, count;


    private DbOpenHelper mDbOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        ButterKnife.bind(this);



        mDbOpenHelper = new DbOpenHelper(this);
        mDbOpenHelper.open();
        mDbOpenHelper.create();

        Intent data = getIntent();
        b = data.getExtras();
        if (b.get("key").equals("add")) {
            delete_btn.setVisibility(View.GONE);
            modify_btn.setVisibility(View.GONE);
            cancel_btn.setVisibility(View.VISIBLE);
            add_btn.setVisibility(View.VISIBLE);


        } else if (b.get("key").equals("modify")) {
            add_btn.setVisibility(View.GONE);
            cancel_btn.setVisibility(View.VISIBLE);
            delete_btn.setVisibility(View.VISIBLE);
            modify_btn.setVisibility(View.VISIBLE);

            id = String.valueOf(b.get("id"));
            name = String.valueOf(b.get("name"));
            memo = String.valueOf(b.get("memo"));
            number = String.valueOf(b.get("number"));
            count = String.valueOf(b.get("count"));


            name_et.setText(name);
            memo_et.setText(memo);
            number_et.setText(number);

        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        number_et.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
    }

    @OnClick(R.id.address_btn)
    public void address_btnClicked() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setData(ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
        startActivityForResult(intent, 0);

    }

    @OnClick(R.id.cancel_btn)
    public void cancel_btnClicked() {
        Intent intent = new Intent(UserActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @OnClick(R.id.add_btn)
    public void add_btnClicked() {
        if (StringUtils.isEmpty(name_et.getText().toString())) {
            Toast.makeText(this, "이름을 입력해 주세요", Toast.LENGTH_SHORT).show();
        } else if (StringUtils.isEmpty(number_et.getText().toString())) {
            Toast.makeText(this, "전화번호를 입력해 주세요", Toast.LENGTH_SHORT).show();
        } else {
            long now = System.currentTimeMillis();
            Date date = new Date(now);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd:HHmmss");
            String getTime = sdf.format(date);

            number = number_et.getText().toString();
            number = number.replaceAll("-", "");

            mDbOpenHelper.insertColumn(name_et.getText().toString(), number, memo_et.getText().toString(), getTime);


            Intent intent = new Intent(UserActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }


    }

    @OnClick(R.id.delete_btn)
    public void delete_btnClicked() {


        mDbOpenHelper.deleteColumn(Long.parseLong(id));
        Log.d("~!", name_et.getText().toString());
        Intent intent = new Intent(UserActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }

    @OnClick(R.id.modify_btn)
    public void modify_btnClicked() {
        if (StringUtils.isEmpty(name_et.getText().toString())) {
            Toast.makeText(this, "이름을 입력해 주세요", Toast.LENGTH_SHORT).show();
        } else if (StringUtils.isEmpty(number_et.getText().toString())) {
            Toast.makeText(this, "전화번호를 입력해 주세요", Toast.LENGTH_SHORT).show();
        } else {
            long now = System.currentTimeMillis();
            Date date = new Date(now);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd:HHmmss");
            String getTime = sdf.format(date);

            number = number_et.getText().toString();
            number = number.replaceAll("-", "");

            mDbOpenHelper.updateColumn(Long.parseLong(id), name_et.getText().toString(), number, memo_et.getText().toString(), getTime);
            Intent intent = new Intent(UserActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Cursor cursor = getContentResolver().query(data.getData(), new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER}, null, null, null);
            cursor.moveToFirst();
            String name = cursor.getString(0);
            String number = cursor.getString(1);
            cursor.close();

            name_et.setText(String.valueOf(name));

            number_et.setText(String.valueOf(PhoneNumberUtils.formatNumber(number)));


        }


        super.onActivityResult(requestCode, resultCode, data);
    }
}
