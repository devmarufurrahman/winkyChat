package com.example.mrnchatbd.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mrnchatbd.utils.FirebaseUtils;
import com.example.mrnchatbd.R;
import com.example.mrnchatbd.utils.UserModel;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class SearchRecyclerAdapter extends FirestoreRecyclerAdapter<UserModel, SearchRecyclerAdapter.UserModelViewHolder> {

    Context context;
    public SearchRecyclerAdapter(@NonNull FirestoreRecyclerOptions<UserModel> options,Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull UserModelViewHolder holder, int position, @NonNull UserModel model) {
        holder.userNameText.setText(model.getUserName());
        holder.userNamePhone.setText(model.getPhoneNumber());
        if (model.getUserId().equals(FirebaseUtils.currentUserId())){
            holder.userNameText.setText(model.getUserName()+ "  (Me)");
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
