package com.example.mrnchatbd.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mrnchatbd.ChatActivity;
import com.example.mrnchatbd.SearchActivity;
import com.example.mrnchatbd.model.ChatroomModel;
import com.example.mrnchatbd.utils.AndroidUtils;
import com.example.mrnchatbd.utils.FirebaseUtils;
import com.example.mrnchatbd.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

public class RecentChatAdapter extends FirestoreRecyclerAdapter<ChatroomModel, RecentChatAdapter.ChatroomModelViewHolder> {

    Context context;
    public RecentChatAdapter(@NonNull FirestoreRecyclerOptions<ChatroomModel> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull ChatroomModelViewHolder holder, int position, @NonNull ChatroomModel model) {
        FirebaseUtils.getOtherUserFromChatroom(model.getUserIds())
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        SearchActivity.UserModel otherUserModel  = task.getResult().toObject(SearchActivity.UserModel.class);
                        assert otherUserModel != null;
                        holder.userNameText.setText(otherUserModel.getUserName());
                        holder.lastMessageText.setText(model.getLastMessage());
                        holder.lastMessageTime.setText(model.getLastMessageTimeStamp().toString());
                    }
                });
    }

    @NonNull
    @Override
    public ChatroomModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recent_chat_layout_recycler,parent,false);
        return new ChatroomModelViewHolder(view);
    }

    class ChatroomModelViewHolder extends RecyclerView.ViewHolder{
        TextView userNameText, lastMessageText, lastMessageTime;
        ImageView userProfileImg;
        public ChatroomModelViewHolder(@NonNull View itemView) {
            super(itemView);
            userNameText = itemView.findViewById(R.id.userName);
            lastMessageText = itemView.findViewById(R.id.lastMessageText);
            lastMessageTime = itemView.findViewById(R.id.lastMessageTime);
        }
    }
}
