package com.kalz.blogbuddy;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.kalz.blogbuddy.R;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {

    private String user_id;
    private CircleImageView userImage;
    private TextView userName;
    private TextView userAbout;
    private TextView userContact;
    private Button editBtn;

    private StorageReference storageReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

//    private Context context;



    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view =  inflater.inflate(R.layout.fragment_account, container, false);

        firebaseAuth = FirebaseAuth.getInstance();

        user_id = firebaseAuth.getCurrentUser().getUid();

        firebaseFirestore=FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        userImage = view.findViewById(R.id.user_image);
        userName = view.findViewById(R.id.user_name);
        userAbout = view.findViewById(R.id.user_about);
        userContact = view.findViewById(R.id.user_contact);
        editBtn = view.findViewById(R.id.edit_btn);

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent setupIntent = new Intent(getActivity(),SetupActivity.class);
                startActivity(setupIntent);

            }
        });

        firebaseFirestore.collection("Users").document(user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if(task.isSuccessful()){

                    if(task.getResult().exists()){

                        String name = task.getResult().getString("name");
                        String about = task.getResult().getString("about");
                        String number = task.getResult().getString("mobileNumber");
                        String image = task.getResult().getString("image");

                        userName.setText(name);
                        userAbout.setText(about);
                        userContact.setText(number);

                        RequestOptions placeHolderRequest = new RequestOptions();
                        placeHolderRequest.placeholder(R.drawable.default_image);

                        Glide.with(getActivity()).setDefaultRequestOptions(placeHolderRequest).load(image).into(userImage);

                    }


                }else{

                    String e = task.getException().getMessage();
                    Toast.makeText(getActivity(), "Firestore retrive Error :"+ e,Toast.LENGTH_LONG).show();

                }


            }
        });



        return view;
    }

}
