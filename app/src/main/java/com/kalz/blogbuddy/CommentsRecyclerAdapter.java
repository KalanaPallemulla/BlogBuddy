package com.kalz.blogbuddy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
//import com.kalz.blogbuddyAdmin.R;

import java.util.List;

public class CommentsRecyclerAdapter extends RecyclerView.Adapter<CommentsRecyclerAdapter.ViewHolder> {

    public List<Comments> commentsList;
    public Context context;

    public FirebaseFirestore firebaseFirestore;
    public FirebaseAuth firebaseAuth;

    public CommentsRecyclerAdapter (List<Comments> commentsList){

        this.commentsList = commentsList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        firebaseAuth =FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_list_item, parent, false);
        context = parent.getContext();
        return new CommentsRecyclerAdapter.ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        holder.setIsRecyclable(false);

        String commentMessage = commentsList.get(position).getMessage();
        holder.setComment_message(commentMessage);

        String userData = commentsList.get(position).getUser_id();



        try {
        firebaseFirestore.collection("Users").document(userData).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if(task.isSuccessful()){

                    String userName = task.getResult().getString("name");
                    String userImage = task.getResult().getString("image");

                    holder.setUser_data(userName,userImage);

                }else{

//                    Toast.makeText(BlogRecyclerAdapter.class, "Error",Toast.LENGTH_LONG).show();


                }

            }
        });

    } catch (Exception e){
            Toast.makeText(context, "Error :" + e, Toast.LENGTH_LONG).show();
        }



    }

    @Override
    public int getItemCount() {

        if(commentsList != null){

            return commentsList.size();

        }else {

            return 0;
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private View mView;

        private TextView comment_message;
        private TextView user_name;
        private ImageView commentUserImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setComment_message(String message){
            comment_message = mView.findViewById(R.id.comment_message);
            comment_message.setText(message);
        }

        public void setUser_data(String userName, String imageUrl){

            user_name = mView.findViewById(R.id.comment_username);
            commentUserImage = mView.findViewById(R.id.notification_image);

            user_name.setText(userName);

            RequestOptions placeHolderOption = new RequestOptions();
            placeHolderOption.placeholder(R.drawable.blog_user_image);

            Glide.with(context).applyDefaultRequestOptions(placeHolderOption).load(imageUrl).into(commentUserImage);

        }



        
    }
}
