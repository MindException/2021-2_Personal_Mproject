package com.cookandroid.personal_mobile_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //나의 고유번호를 저장한다.
    private String mykey;
    public UserInfo myinfo;
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    //임시 그룹이다.
    String testgroup = "-MogdnSpcaG4Rvwd54vK";

    /* 클래스에 있는 리스트 전부 제네릭 넣어야 버그 안터진다!!  */

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
        createGroup();

    }

    void  getMyinfo(){  //나의 회원정보를 가져옴.


        myRef.child("UserInfo").child(mykey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                myinfo = snapshot.getValue(UserInfo.class);
                //이미지 기본 세팅(완성)
                System.out.println(myinfo.img);


            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });     //기본으로 나의 정보를 가져온다.



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
                loginIntent.putExtra("mykey",mykey);
                //시작
                startActivity(loginIntent);


            }
        });


    }

    void createGroup(){     //그룹 생성

        Button b1 = (Button)findViewById(R.id.create_button);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Group a = new Group();
                a.limit_person = 999;
                a.year = 1;
                a.month = 2;
                a.day = 3;
                a.start_hours = 4;
                a.start_minutes = 5;
                a.start_x = 11.11;
                a.start_y = 11.11;
                a.finish_x = 11.11;
                a.finish_y= 11.11;
                if(a.users == null){

                    a.users = new ArrayList<String>();
                    a.users.add(mykey);

                }else {
                    a.users.add(mykey);
                }

                //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ여기부터가 실제 코딩 합치기ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
                String mx = myRef.child("Group").push().getKey();               //랜덤 키 생성 후 저장
                myRef.child("Group").child(mx).setValue(a);                     //키 값에 데이터 입력 후 서버 저장
                if(myinfo.groups == null){                                            //생성자에 그룹에 넣어준다.

                    myinfo.groups = new ArrayList<String>();
                    myinfo.groups.add(mx);

                }else {
                    myinfo.groups.add(mx);
                }
                myRef.child("UserInfo").child(mykey).setValue(myinfo);           //서버에 저장
                //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ그룹 생성 완료ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ

            }
        });

    }

    void joinGroup(){

        //testgroup 키 값을 사용하여 테스트한다.
        /*
                    순서
                    1.먼저 자신이 들어가려는 키값의 그룹이 자신이 이미 속해 있는지 확인한다.
                    2.속하지 않았다면 그룹에 리스트에 자신의 목록을 추가한다.
                    3.이제 사용자 그룹 리스트에 자신을 추가한다.

         */

        Button b2 = (Button)findViewById(R.id.join_button);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //한 번만 조회함으로 이것이 필요.
                myRef.child("Group").child(testgroup).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        Group a = snapshot.getValue(Group.class);
                        System.out.println(a.users.get(0).toString());


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });






    }

}