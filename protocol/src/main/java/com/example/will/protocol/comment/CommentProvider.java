package com.example.will.protocol.comment;

import com.example.will.protocol.BasicProvider;
import com.example.will.protocol.comment.callback.AddCommentCallback;
import com.example.will.protocol.comment.callback.DeleteCommentCallback;
import com.example.will.protocol.comment.callback.ManageLikeCallback;
import com.example.will.protocol.comment.callback.QueryCommentCallback;
import com.example.will.protocol.comment.callback.QueryCommentsCallback;
import com.example.will.protocol.comment.request.AddCommentRequest;
import com.example.will.protocol.comment.request.LikeRequest;

/**
 * 评论基础能力
 */
public interface CommentProvider extends BasicProvider {
    /**
     * 查询单个评论
     * @param commentId
     * @param callback
     */
    void queryComment(long commentId, QueryCommentCallback callback);

    /**
     * 查询歌曲下的以及评论或者某个一级评论下的二级评论
     * @param key
     * @param callback
     */
    void queryComments(long key, QueryCommentsCallback callback);

    /**
     * 添加评论
     * @param request
     * @param callback
     */
    void addComment(AddCommentRequest request, AddCommentCallback callback);

    /**
     * 踩与赞系统
     * @param request
     * @param callback
     */
    void manageLike(LikeRequest request, ManageLikeCallback callback);

    /**
     * 删除评论
     * @param commentId
     * @param callback
     */
    void deleteComment(long commentId, DeleteCommentCallback callback);
}
