package com.cookandroid.personal_mobile_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MyPAge extends AppCompatActivity {

    //서버
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    ImageView iv;
    private int requestCode;
    private UserInfo userInfo;
    private ArrayList<String> groups_name = new ArrayList<String>();       //이름을 임시저장
    private ArrayList<Group> groups = null;
    private String aid;
    private String mynickname;
    private RecyclerView lv;
    public ChatRoomRecycleAdapter adapter;
    private TextView nickTextView;
    //평점과 취소율
    private TextView starsview;         //별점 평점
    private TextView pcancleview;       //취소율
    private TextView speople;           //평가 인원 나타냄
    private TextView c_counts;          //탑승 완료 회수 나타냄
    //취소율 구할 때 -------> 벌점/벌점+탑승완료 그룹 개수



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage);


        //기본 세팅
        iv = findViewById(R.id.myimg);
        lv = (RecyclerView)findViewById(R.id.chatroom);
        setting();
        //프로필 사진 관련
        imageButton();
        cancle();

    }

    void setting(){         //초반 기본 세팅

        //자기 인덱스 키값 가져옴
        aid = getIntent().getStringExtra("mykey");
        mynickname = getIntent().getStringExtra("mynickname");  //닉네임 인덱스 키값 가져옴

        //자신의 닉네임 저장
        nickTextView = (TextView)findViewById(R.id.myname);
        nickTextView.setText("안녕하세요!\n" + mynickname +"님");

        //서버에서 유저정보 가져옴
        database = FirebaseDatabase.getInstance();;
        myRef = database.getReference();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {

                DataSnapshot dsphoto = snapshot;
                userInfo = dsphoto.child("UserInfo").child(aid).getValue(UserInfo.class);
                if(userInfo.img != null){       //기본 자기 프로필 사진이 있다면 보여준다.

                    Bitmap bmp;
                    byte[] bytes = UserInfo.binaryStringToByteArray(userInfo.img);
                    bmp = BitmapFactory.decodeByteArray(bytes, 0 , bytes.length);
                    iv.setImageBitmap(bmp);
                    iv.setClipToOutline(true);              //모양에 맞게 사진 자르기

                }

                //평점 세팅
                //별점 평균
                starsview = (TextView)findViewById(R.id.stars);
                if(userInfo.stars == null || userInfo.stars.size() < 10){           //10회 미만일 경우

                    starsview.setText("10회 미만입니다.");

                }else{                                                              //10회 이상인경우



                }
                //별점 평가자 수
                speople = (TextView)findViewById(R.id.stars_people);
                speople.setText(userInfo.stars.size() + "명 평가");    //이것 때문에 다시 짜야한다.


                //취소율 세팅
                //여기는 실제 취소율
                pcancleview = (TextView)findViewById(R.id.cancle_percentage);           //텍스트뷰 연결
                if(userInfo.end_groups == null || userInfo.end_groups.size() < 10){     //10회 미만일 경우



                }else{                                                              //10회 이상인경우




                }
                //취소율 명 수
                c_counts = (TextView)findViewById(R.id.reserve_count);



                //채팅방 세팅
                groups_name = new ArrayList<String>();
                DataSnapshot dsgroup = snapshot.child("Group");
                for(DataSnapshot dsSelect : dsgroup.getChildren()){             //키 값으로 찾아 다닌다.

                    for(int i = 0; i < userInfo.groups.size(); i++){            //키값이 맞는게 있는지 확인한다.

                        if(dsSelect.getKey().equals(userInfo.groups.get(i).toString())){        //키 값에 맞는 것을 발견

                            groups_name.add(dsSelect.getKey());     //일치하는 키값을 저장

                        }

                    }

                }

                //이제 얻은 키값으로 그룹들을 저장
                groups = new ArrayList<>();
                for(int j = 0; j < groups_name.size(); j++){

                    groups.add(dsgroup.child(groups_name.get(j)).getValue(Group.class));

                }

                init();

            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

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

    private void init() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        lv.setLayoutManager(linearLayoutManager);

        adapter = new ChatRoomRecycleAdapter();
        adapter.setOnItemClickListener(new ChatRoomRecycleAdapter.OnItemClickListener()
        {
            @Override
            public void onItemClick(View v, int pos)
            {
                // 실행 내용
                System.out.println(groups_name.get(pos));

                //이제 여기다가 채팅방 이동을 넣으면 된다.
                Intent chatIntent = new Intent(MyPAge.this,Chat.class);
                chatIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                chatIntent.putExtra("mykey",aid);                           //자신의 키를 넘긴다.
                chatIntent.putExtra("mygroup",groups_name.get(pos));        //그룹의 키를 넘긴다.
                chatIntent.putExtra("mynickname", mynickname);              //자신의 닉네임을 넘긴다.
                chatIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(chatIntent);

            }
        });

        for (int i = 0; i < groups.size(); i++) {      //이거 돌려야 들어간다.

            adapter.addItem(groups.get(i));          //이거 해줘야 순서대로 다 들어간다.

        }
        lv.setAdapter(adapter);


    }

    //취소 버튼
    void cancle(){

        ImageButton cancle = (ImageButton) findViewById(R.id.move_main);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent escapeIntent = new Intent(MyPAge.this, MainActivity.class);
                escapeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                escapeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                escapeIntent.putExtra("mykey", aid);
                escapeIntent.putExtra("mynickname", mynickname);
                startActivity(escapeIntent);

            }
        });





    }


}



