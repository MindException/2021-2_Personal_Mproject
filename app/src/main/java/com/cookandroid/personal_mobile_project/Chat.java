package com.cookandroid.personal_mobile_project;


/*
*
*           채팅할 때 이미지 처리 자체를 다르게 하자.
*           닉네임//채팅내용//이미지
*
* */

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Chat extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference myRef;

    //기본으로 인탠트에서 가져오는 것
    private String group_key;
    private String my_key;
    private String mynickname;

    //서버에서 가져오는 클래스
    Group gp = null;
    UserInfo userInfo = null;
    String myimg = null;

    //텍스트 뷰
    EditText mychat = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);

        //서버 연결
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        //인탠트 값 가져오기
        group_key = getIntent().getStringExtra("mygroup");
        my_key = getIntent().getStringExtra("mykey");
        mynickname = getIntent().getStringExtra("mynickname");

        setting();
        insertButton();
        cancleButton();

    }
    public void setting() {         //세팅

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                //먼저 그룹 클래스를 가져온다.
                gp = snapshot.child("Group").child(group_key).getValue(Group.class);
                userInfo = snapshot.child("UserInfo").child(my_key).getValue(UserInfo.class);


                //가져온 그룹 클래스로 이제 나타낸다.(리사이클 뷰)



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void insertButton() {    //채팅 입력

        mychat = (EditText)findViewById(R.id.insert_chat);                      //챗 연결
        Button insertButton = (Button)findViewById(R.id.insert_button);         //버튼 연결
        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("d");
                String chat = mychat.getText().toString();                  //값을 가져온다.(입력 안하여도 ""으로 가져온다. 서버도 똑같이 저장됨)
                ChatFormat chatFormat = null;
                if(userInfo.img == null){                                   //사용자가 이미지를 저장하지 못했을 경우

                    chatFormat = new ChatFormat(mynickname,chat);

                }else{                                                      //사용자가 이미지가 있을 경우
                    chatFormat = new ChatFormat(mynickname,chat,userInfo.img);
                }
                if(gp.chat == null){            //채팅이 처음일 경우
                    gp.chat = new ArrayList<ChatFormat>();
                }
                gp.chat.add(chatFormat);        //채팅 넣기
                myRef.child("Group").child(group_key).setValue(gp);     //서버에 저장장



               //이거 이미지 없을 경우도 처리 어떻게 할지 해야한다.



            }
        });


    }

    public void cancleButton(){     //취소 버튼




    }

}