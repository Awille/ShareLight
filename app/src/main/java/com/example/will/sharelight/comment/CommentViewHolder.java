package com.example.will.sharelight.comment;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.will.sharelight.R;
import com.example.will.utils.CircleImageView;

public class CommentViewHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "CommentViewHolder";

    public CircleImageView userAvatar;
    public TextView userNickName;
    public CircleImageView userGender;
    public TextView commentDate;
    public TextView commentContent;


    public CommentViewHolder(@NonNull View itemView) {
        super(itemView);
        initView(itemView);
    }

    private void initView(View view) {
        userAvatar = view.findViewById(R.id.user_avatar);
        userNickName = view.findViewById(R.id.user_nick_name);
        userGender = view.findViewById(R.id.user_gender);
        commentDate = view.findViewById(R.id.comment_date);
        commentContent = view.findViewById(R.id.comment_content);
    }
}
