package com.cookandroid.personal_mobile_project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;



public class ChatRoomRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private  ArrayList<Group> groupArrayList = new ArrayList<>();

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chatroomitem, parent, false);
        return new ViewHolderChatRoom(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ((ViewHolderChatRoom)holder).onBind(groupArrayList.get(position));

    }

    @Override
    public int getItemCount() {     //들어간 아이템 개수 체크

        return groupArrayList.size();
    }

    void addItem(Group data) {      //아이템을 여기다가 집어넣는다.

        groupArrayList.add(data);
    }

}



