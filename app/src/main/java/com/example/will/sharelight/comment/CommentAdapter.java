package com.example.will.sharelight.comment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.will.network.imageloader.ImageLoader;
import com.example.will.network.retrofit.RetrofitMrg;
import com.example.will.protocol.comment.Comment;
import com.example.will.sharelight.R;

import java.util.List;
import java.util.zip.Inflater;

public class CommentAdapter extends RecyclerView.Adapter<CommentViewHolder> {

    private static final String TAG = "CommentAdapter";

    private List<Comment> commentList;
    private Context context;

    public CommentAdapter(List<Comment> commentList, Context context) {
        this.commentList = commentList;
        this.context = context;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new CommentViewHolder(
                LayoutInflater.from(context).inflate(R.layout.single_comment_item,
                        viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder commentViewHolder, int i) {
        ImageLoader.build(context).bindBitmap(RetrofitMrg.baseUrl + commentList.get(i).getUser().getAvatarUrl(),
                commentViewHolder.userAvatar, 200, 200);
        commentViewHolder.userNickName.setText(commentList.get(i).getUser().getNickName());
        int gender = commentList.get(i).getUser().getGender();
        if (gender == 0) {
            commentViewHolder.userGender.setImageResource(R.drawable.unknow_sexual);
        } else if (gender == 1) {
            commentViewHolder.userGender.setImageResource(R.drawable.male);
        } else {
            commentViewHolder.userGender.setImageResource(R.drawable.female);
        }
        commentViewHolder.commentDate.setText(commentList.get(i).getTime());
        commentViewHolder.commentContent.setText(commentList.get(i).getContent());
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }


}
