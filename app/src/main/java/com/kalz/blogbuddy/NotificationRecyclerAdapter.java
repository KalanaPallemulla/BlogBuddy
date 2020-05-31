package com.kalz.blogbuddy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class NotificationRecyclerAdapter extends RecyclerView.Adapter<NotificationRecyclerAdapter.ViewHolder> {

    public Context context;

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth mAuth;

    public List<Comments> noti_list;

    public NotificationRecyclerAdapter(List<Comments> noti_list){
        this.noti_list = noti_list;
    }

    @NonNull
    @Override
    public NotificationRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_list_item, parent, false);


        return new NotificationRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationRecyclerAdapter.ViewHolder holder, int position) {

        final String currentUser = mAuth.getCurrentUser().getUid();
        final String blog_user_id = noti_list.get(position).getUser_id();

        if(blog_user_id.equals(currentUser)) {
            String user_name = noti_list.get(position).getUser_id();
            holder.setUserName(user_name);

        }
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private View mView;
        private CircleImageView userImage;
        private TextView userName;
        private TextView comment;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mView = itemView;

            userImage = mView.findViewById(R.id.notification_image);
            userName = mView.findViewById(R.id.noti_user_name);
            comment = mView.findViewById(R.id.noti_comment);


        }
        public void setUserName(String uName){

            userName = mView.findViewById(R.id.noti_user_name);
            userName.setText(uName);

        }

    }
}


