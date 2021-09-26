package com.cookandroid.personal_mobile_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity {

    //DB 관련
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    //버튼 관련
    private Button loginButton;
    private Button signUpButton;
    User mine;


    @Override
    protected void onCreate(Bundle savedInstanceState) {    //main문 이라고 생각하면 편함
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        //파이어 베이스 데이터베이스 연동
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        login();
        sign_up();

    }

    void login(){   //로그인 버튼 누르면 발생시

        loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {










            }
        });


    }

    void sign_up(){     //회원가입 버튼 누르면 발생시

        signUpButton = findViewById(R.id.sign_up_button);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //첫 번째 매개변수는 자신, 두 번째는 이동
                Intent loginIntent = new Intent(Login.this, SignUp.class);
                //이거 아래 2개 해줘야 뒤로가기 버튼 눌러도 뒤로 안가진다.
                loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //시작
                startActivity(loginIntent);

            }
        });

    }
}