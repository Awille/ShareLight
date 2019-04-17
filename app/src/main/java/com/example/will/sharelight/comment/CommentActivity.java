package com.example.will.sharelight.comment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.example.will.datacontext.MusicDataContext;
import com.example.will.network.imageloader.ImageLoader;
import com.example.will.network.retrofit.RetrofitMrg;
import com.example.will.protocol.comment.Comment;
import com.example.will.protocol.comment.reponse.QueryCommentsResponse;
import com.example.will.protocol.song.Song;
import com.example.will.sharelight.R;
import com.example.will.utils.TextUtils;
import com.example.will.utils.loadingutils.LoadingUtils;
import com.example.will.utils.toast.ToastUtils;

import java.util.ArrayList;
import java.util.List;

public class CommentActivity extends AppCompatActivity implements View.OnClickListener, CommentContract.CommentView {
    private static final String TAG = "CommentActivity";

    private Song currentSong;

    private ImageView songAvatar;
    private RecyclerView commentRecyclerView;
    private EditText commentEdit;
    private ImageView postAction;

    private CommentAdapter commentAdapter;

    private CommentPresnterImpl commentPresnter;


    private List<Comment> commentList = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment_layout);

        Bundle bundle = getIntent().getExtras();
        currentSong = JSON.parseObject(bundle.getString("CURRENT_SONG"), Song.class);
        initView();
    }

    private void initView() {
        songAvatar = findViewById(R.id.song_avatar);
        commentRecyclerView = findViewById(R.id.comment_recycler_view);
        commentEdit = findViewById(R.id.comment_edit);
        postAction = findViewById(R.id.post_action);
        postAction.setOnClickListener(this);
        ImageLoader.build(this).bindBitmap(RetrofitMrg.baseUrl + currentSong.getAvatarUrl(),
                songAvatar, 300, 300);

        commentPresnter = new CommentPresnterImpl(this);

        LinearLayoutManager linearLayoutManagerForSongList = new LinearLayoutManager(this);
        commentRecyclerView.setLayoutManager(linearLayoutManagerForSongList);
        commentAdapter = new CommentAdapter(commentList, this);
        commentRecyclerView.setAdapter(commentAdapter);

        LoadingUtils.getINSTANCE(this).showLoadingViewGhost();
        commentPresnter.querySongComments(currentSong.getSongId());
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        switch (viewId) {
            case R.id.post_action : {
                clickPost();
                break;
            }
        }
    }

    private void clickPost() {
        String text = commentEdit.getText().toString();
        if (TextUtils.isEmpty(text)) {
            ToastUtils.showWarningToast(this, "输入不能为空", ToastUtils.LENGTH_LONG);
        } else {
            Comment comment = new Comment();
            comment.setCommentLevel(0);
            comment.setContent(text);
            //0代表回复等级
            comment.setReplyCommentId(0);
            comment.setUserId(MusicDataContext.getINSTANCE().getUser().getUserId());
            comment.setSongId(currentSong.getSongId());
            LoadingUtils.getINSTANCE(this).showLoadingViewGhost();
            commentPresnter.addComment(comment);
        }
    }

    @Override
    public void onQuerySongCommentsSuccess(QueryCommentsResponse response) {
        LoadingUtils.getINSTANCE(this).dismisDialog();
        commentList = response.getData();
        commentAdapter.setCommentList(commentList);
        commentAdapter.notifyDataSetChanged();
    }

    @Override
    public void onQuerySongCommentsFail(String errCode, String errMsg) {
        LoadingUtils.getINSTANCE(this).dismisDialog();
        ToastUtils.showErrorToast(this, errMsg, ToastUtils.LENGTH_LONG);
    }

    @Override
    public void onAddCommentSuccess(Comment comment) {
        ToastUtils.showSuccessToast(this, "评论成功", ToastUtils.LENGTH_SHORT);
        commentPresnter.querySongComments(currentSong.getSongId());
        commentEdit.setText("");
        commentEdit.clearFocus();
    }

    @Override
    public void onAddCommentFail(String errCode, String errMsg) {
        LoadingUtils.getINSTANCE(this).dismisDialog();
        ToastUtils.showErrorToast(this, errMsg, ToastUtils.LENGTH_LONG);
    }
}
