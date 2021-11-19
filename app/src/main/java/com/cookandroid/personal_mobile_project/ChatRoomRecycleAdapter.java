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

        System.out.println("어떻게 출력 되려나?"+ groupArrayList.size());
        return groupArrayList.size();

    }

    void addItem(Group data) {      //아이템을 여기다가 집어넣는다.

        groupArrayList.add(data);
    }

    //아이템 넣기
    public class ViewHolderChatRoom extends RecyclerView.ViewHolder {

        private View view;

        TextView destination;
        TextView people_count;
        TextView date;
        TextView time;

        public ViewHolderChatRoom(@NonNull View itemView) {
            super(itemView);

            destination = (TextView)itemView.findViewById(R.id.destination);
            destination.setSelected(true);      //이거 넣어야 긴 문장이 흘러서 보여준다.
            people_count = (TextView)itemView.findViewById(R.id.count);
            date = (TextView)itemView.findViewById(R.id.date);
            time = (TextView)itemView.findViewById(R.id.time);

            itemView.setOnClickListener(new View.OnClickListener() {            //클릭은 여기다가 한다.
                @Override
                public void onClick(View v) {

                    System.out.println("눌림");



                }
            });

        }

        public void onBind(Group gp){

            destination.setText(gp.destination);
            people_count.setText(Integer.toString(gp.users.size()));
            date.setText(Integer.toString(gp.year) + "-" + Integer.toString(gp.month) + "-" + Integer.toString(gp.day));
            time.setText(Integer.toString(gp.start_hours) + "시 " + Integer.toString(gp.start_minutes) +"분");

        }

    }

}



