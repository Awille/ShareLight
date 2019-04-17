package com.example.will.sharelight.comment;

import com.example.will.musicprovider.MusicProvider;
import com.example.will.protocol.comment.Comment;
import com.example.will.protocol.comment.callback.AddCommentCallback;
import com.example.will.protocol.comment.callback.QueryCommentsCallback;
import com.example.will.protocol.comment.reponse.AddCommentResponse;
import com.example.will.protocol.comment.reponse.QueryCommentsResponse;
import com.example.will.protocol.comment.request.AddCommentRequest;
import com.example.will.protocol.comment.request.AddCommentRequestData;

public class CommentPresnterImpl implements CommentContract.CommentPrenter {
    private static final String TAG = "CommentPresnterImpl";

    private CommentContract.CommentView commentView;

    public CommentPresnterImpl(CommentContract.CommentView commentView) {
        this.commentView = commentView;
    }

    @Override
    public void querySongComments(long songId) {
        MusicProvider.getINSTANCE().getCommentProvider().queryComments(songId, null, new QueryCommentsCallback() {
            @Override
            public void onQueryCommentsSuccess(QueryCommentsResponse response) {
                commentView.onQuerySongCommentsSuccess(response);
            }

            @Override
            public void onQueryCommentFail(String errCode, String errMsg) {
                commentView.onQuerySongCommentsFail(errCode, errMsg);
            }
        });
    }

    @Override
    public void addComment(Comment comment) {
        AddCommentRequest addCommentRequest = new AddCommentRequest();
        AddCommentRequestData data = new AddCommentRequestData();
        data.setComment(comment);
        addCommentRequest.setData(data);
        MusicProvider.getINSTANCE().getCommentProvider().addComment(addCommentRequest, new AddCommentCallback() {
            @Override
            public void onAddCommentSuccess(AddCommentResponse response) {
                commentView.onAddCommentSuccess(response.getData());
            }

            @Override
            public void onAddCommentFail(String errCode, String errMsg) {
                commentView.onAddCommentFail(errCode, errMsg);
            }
        });
    }
}
