package com.example.will.sharelight.comment;

import com.example.will.protocol.comment.Comment;
import com.example.will.protocol.comment.reponse.QueryCommentsResponse;

public class CommentContract {
    public interface CommentPrenter {
        void querySongComments(long songId);
        void addComment(Comment comment);
    }

    public interface CommentView {
        void onQuerySongCommentsSuccess(QueryCommentsResponse response);
        void onQuerySongCommentsFail(String errCode, String errMsg);

        void onAddCommentSuccess(Comment comment);
        void onAddCommentFail(String errCode, String errMsg);

    }
}
