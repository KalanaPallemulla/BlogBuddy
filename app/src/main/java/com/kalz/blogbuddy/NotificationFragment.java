package com.kalz.blogbuddy;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
//import com.kalz.blogbuddyAdmin.R;

import org.w3c.dom.Comment;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment extends Fragment {

    private RecyclerView noti_list_view;
    private List<Comments> noti_list;

    private FirebaseFirestore firebaseFirestore;
    private NotificationRecyclerAdapter notificationRecyclerAdapter;


    public NotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        noti_list = new ArrayList<>();
        noti_list_view = view.findViewById(R.id.notification_list_view);

        notificationRecyclerAdapter = new NotificationRecyclerAdapter(noti_list);
        noti_list_view.setLayoutManager(new LinearLayoutManager(getActivity()));
        noti_list_view.setAdapter(notificationRecyclerAdapter);

        firebaseFirestore = FirebaseFirestore.getInstance();

        firebaseFirestore.collection("Posts").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {

                for (DocumentChange doc: queryDocumentSnapshots.getDocumentChanges()) {

                    if(doc.getType() == DocumentChange.Type.ADDED){

                        Comments comments = doc.getDocument().toObject(Comments.class);
                        noti_list.add(comments);

                        notificationRecyclerAdapter.notifyDataSetChanged();

                    }

                }

            }
        });

        // Inflate the layout for this fragment
        return view;


    }

}
