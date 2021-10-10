package com.cookandroid.personal_mobile_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    //나의 고유번호를 저장한다.
    private String mykey;
    private User myinfo;
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //서버 연결
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        mykey = getIntent().getStringExtra("mykey");        //자기 인덱스 키값 가져옴
        System.out.println(mykey);
        getMyinfo();

        moveMyPage();

    }

    void  getMyinfo(){  //나의 회원정보를 가져옴.





    }

    void moveMyPage(){          //이거 임시용

        Button button = findViewById(R.id.mypage_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //첫 번째 매개변수는 자신, 두 번째는 이동
                Intent loginIntent = new Intent(MainActivity.this, MyPAge.class);
                //이거 아래 2개 해줘야 뒤로가기 버튼 눌러도 뒤로 안가진다.
                loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //시작
                startActivity(loginIntent);


            }
        });


    }

}