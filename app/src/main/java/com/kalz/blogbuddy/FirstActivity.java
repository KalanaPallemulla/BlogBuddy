package com.kalz.blogbuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

//import com.kalz.blogbuddyAdmin.R;

public class FirstActivity extends AppCompatActivity {

    private Button loginBtn;
    private Button regBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        loginBtn = findViewById(R.id.first_login_btn);
        regBtn = findViewById(R.id.first_join_btn);


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logIntent = new Intent(FirstActivity.this, LoginActivity.class);
                startActivity(logIntent);
                finish();
            }
        });

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent regIntent = new Intent(FirstActivity.this, RegisterActivity.class);
                startActivity(regIntent);
                finish();

            }
        });

    }
}
