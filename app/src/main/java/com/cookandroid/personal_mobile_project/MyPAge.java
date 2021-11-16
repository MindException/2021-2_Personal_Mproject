package com.cookandroid.personal_mobile_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MyPAge extends AppCompatActivity {

    //서버
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    ImageView iv;
    private int requestCode;
    private UserInfo userInfo;
    private Group group;
    private String aid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage);


        //기본 세팅
        iv = findViewById(R.id.myimg);
        setting();
        //프로필 사진 관련
        imageButton();

    }

    void setting(){         //초반 기본 세팅

        //자기 인덱스 키값 가져옴
        aid = getIntent().getStringExtra("mykey");
        System.out.println("마이페이지 에서의 키값" + aid);

        //서버에서 유저정보 가져옴
        database = FirebaseDatabase.getInstance();;
        myRef = database.getReference();

        myRef.child("UserInfo").child(aid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                userInfo = snapshot.getValue(UserInfo.class);
                //이미지 기본 세팅(완성)
                System.out.println(userInfo.img);
                if(userInfo.img != null){       //기본 자기 프로필 사진이 있다면 보여준다.

                    Bitmap bmp;
                    byte[] bytes = UserInfo.binaryStringToByteArray(userInfo.img);
                    bmp = BitmapFactory.decodeByteArray(bytes, 0 , bytes.length);
                    iv.setImageBitmap(bmp);
                    iv.setClipToOutline(true);              //모양에 맞게 사진 자르기

                }

                //평점 세팅
                //취소율 세팅
                //채팅 리스트 세팅








            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }//기본세팅 끝

    //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 이미지 처리 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ

    void imageButton(){         //프로필 사진 변경 및 저장

        iv.setOnClickListener(new View.OnClickListener() {      //프로필 사진이 눌렸을 경우
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                requestCode = 1;
                startActivityForResult(intent, 1);      //코드 번호로 아래에서 동작한다.

            }
        });

    }

    //갤러리에서 가져온 이미지 저장(완성)
    @Override
    protected void onActivityResult(int requstCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == RESULT_OK) {
            Uri uri = data.getData();
            InputStream inputStream = null;
            try {
                inputStream = getContentResolver().openInputStream(uri);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            iv.setImageBitmap(bitmap);


            //이제 여기서 서버에도 저장
            //먼저 바이트 처리하기
            BitmapDrawable drawable = (BitmapDrawable)iv.getDrawable();
            Bitmap bitmap1 = drawable.getBitmap();
            ByteArrayOutputStream stream1 = new ByteArrayOutputStream();
            bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, stream1);
            byte[] img1Byte = stream1.toByteArray();
            String img = UserInfo.byteArrayToBinaryString(img1Byte); //(성공)

            //이제 서버에 저장
            userInfo.img = img;
            myRef.child("UserInfo").child(aid).setValue(userInfo);

        }
    }

    //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ   이미지 끝 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ


}