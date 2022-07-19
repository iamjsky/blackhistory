package com.blackhistory.ui;

import android.content.Intent;
import android.os.Bundle;

import com.blackhistory.R;

public class ResetCountActivity extends BaseActivity {

    Bundle b;
    String id, name, number;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_count);
        session.setCount(0);
        Intent data = getIntent();
        b = data.getExtras();

            id = String.valueOf(b.get("id"));
            name = String.valueOf(b.get("name"));
            number = String.valueOf(b.get("number"));

        Intent intent = new Intent(this, AdMobActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        intent.putExtra("type", "outgoing");
        intent.putExtra("id", id);
        intent.putExtra("name", name);
        intent.putExtra("number", number);

        startActivity(intent);


    }


}
