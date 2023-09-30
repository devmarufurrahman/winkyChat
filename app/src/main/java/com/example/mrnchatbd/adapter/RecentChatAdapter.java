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
import com.example.mrnchatbd.utils.AndroidUtils;
import com.example.mrnchatbd.utils.FirebaseUtils;
import com.example.mrnchatbd.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class RecentChatAdapter extends FirestoreRecyclerAdapter<SearchActivity.UserModel, RecentChatAdapter.UserModelViewHolder> {

    Context context;
    public RecentChatAdapter(@NonNull FirestoreRecyclerOptions<SearchActivity.UserModel> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull UserModelViewHolder holder, int position, @NonNull SearchActivity.UserModel model) {
        holder.userNameText.setText(model.getUserName());
        holder.userNamePhone.setText(model.getPhoneNumber());
        if (model.getUserId().equals(FirebaseUtils.currentUserId())){
            holder.userNameText.setText(model.getUserName()+ "  (Me)");
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ChatActivity.class);
                AndroidUtils.passUserModelIntent(intent,model);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public UserModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_users_recycler,parent,false);
        return new UserModelViewHolder(view);
    }

    class UserModelViewHolder extends RecyclerView.ViewHolder{
        TextView userNameText, userNamePhone;
        ImageView userProfileImg;
        public UserModelViewHolder(@NonNull View itemView) {
            super(itemView);
            userNameText = itemView.findViewById(R.id.userName);
            userNamePhone = itemView.findViewById(R.id.userPhone);
            userProfileImg = itemView.findViewById(R.id.profile_pic_img_view);
        }
    }
}
