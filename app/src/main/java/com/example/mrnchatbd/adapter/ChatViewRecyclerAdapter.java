package com.example.mrnchatbd.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mrnchatbd.ChatActivity;
import com.example.mrnchatbd.SearchActivity;
import com.example.mrnchatbd.model.ChatMessageModel;
import com.example.mrnchatbd.utils.AndroidUtils;
import com.example.mrnchatbd.utils.FirebaseUtils;
import com.example.mrnchatbd.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class ChatViewRecyclerAdapter extends FirestoreRecyclerAdapter<ChatMessageModel, ChatViewRecyclerAdapter.ChatModelViewHolder> {

    Context context;
    public ChatViewRecyclerAdapter(@NonNull FirestoreRecyclerOptions<ChatMessageModel> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull ChatModelViewHolder holder, int position, @NonNull ChatMessageModel model) {
        if (model.getSenderId().equals(FirebaseUtils.currentUserId())){
            holder.leftChatLayout.setVisibility(View.GONE);
            holder.rightChatLayout.setVisibility(View.VISIBLE);
            holder.rightChatText.setText(model.getMessage());
        } else {
            holder.rightChatLayout.setVisibility(View.GONE);
            holder.leftChatLayout.setVisibility(View.VISIBLE);
            holder.leftChatText.setText(model.getMessage());
        }
    }

    @NonNull
    @Override
    public ChatModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chat_message_recycler,parent,false);
        return new ChatModelViewHolder(view);
    }

    class ChatModelViewHolder extends RecyclerView.ViewHolder{
        TextView leftChatText, rightChatText;
        LinearLayout leftChatLayout, rightChatLayout;
        public ChatModelViewHolder(@NonNull View itemView) {
            super(itemView);
            leftChatLayout = itemView.findViewById(R.id.leftChatLayout);
            leftChatText = itemView.findViewById(R.id.leftChatText);
            rightChatLayout = itemView.findViewById(R.id.rightChatLayout);
            rightChatText = itemView.findViewById(R.id.rightChatText);
        }
    }
}
