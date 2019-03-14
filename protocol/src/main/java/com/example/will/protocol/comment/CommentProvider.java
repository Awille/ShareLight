package com.example.will.protocol.comment;

import com.example.will.network.retrofit.HttpCallback;
import com.example.will.protocol.BasicProvider;
import com.example.will.protocol.comment.reponse.AddCommentResponse;
import com.example.will.protocol.comment.reponse.DeleteCommentResponse;
import com.example.will.protocol.comment.reponse.QueryCommentResponse;
import com.example.will.protocol.comment.reponse.QueryCommentsResponse;
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
    void queryComment(long commentId, HttpCallback<QueryCommentResponse> callback);

    /**
     * 查询歌曲下的以及评论或者某个一级评论下的二级评论
     * @param key
     * @param callback
     */
    void queryComments(long key, HttpCallback<QueryCommentsResponse> callback);

    /**
     * 添加评论
     * @param request
     * @param callback
     */
    void addComment(AddCommentRequest request, HttpCallback<AddCommentResponse> callback);

    /**
     * 踩与赞系统
     * @param request
     * @param callback
     */
    void manageLike(LikeRequest request, HttpCallback<AddCommentResponse> callback);

    /**
     * 删除评论
     * @param commentId
     * @param callback
     */
    void deleteComment(long commentId, HttpCallback<DeleteCommentResponse> callback);
}
