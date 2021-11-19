package com.cookandroid.personal_mobile_project;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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

    }

    public void onBind(Group gp){

        destination.setText(gp.destination);
        people_count.setText(Integer.toString(gp.users.size()));
        date.setText(Integer.toString(gp.year) + "-" + Integer.toString(gp.month) + "-" + Integer.toString(gp.day));
        time.setText(Integer.toString(gp.start_hours) + "시 " + Integer.toString(gp.start_minutes) +"분");

    }

}
