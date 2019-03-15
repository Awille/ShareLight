package com.example.will.commentdomain;

import com.example.will.network.retrofit.HttpCall;
import com.example.will.protocol.comment.reponse.AddCommentResponse;
import com.example.will.protocol.comment.reponse.DeleteCommentResponse;
import com.example.will.protocol.comment.reponse.LikeResponse;
import com.example.will.protocol.comment.reponse.QueryCommentResponse;
import com.example.will.protocol.comment.reponse.QueryCommentsResponse;
import com.example.will.protocol.comment.request.AddCommentRequest;
import com.example.will.protocol.comment.request.LikeRequest;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface CommentApiService {
    @GET("restful//comment.jsp")
    HttpCall<QueryCommentResponse> queryComment(@Query("commentId") long commentId);

    @GET("restful//comment.jsp")
    HttpCall<QueryCommentsResponse> queryComments(@Query("songId") long songId, @Query("replyCommentId") long replyCommentId);

    @POST("restful//comment.jsp")
    HttpCall<AddCommentResponse> addComment(@Body AddCommentRequest request);

    @PUT("restful//comment.jsp")
    HttpCall<LikeResponse> manageLike(@Body LikeRequest request);

    @DELETE("restful//comment.jsp")
    HttpCall<DeleteCommentResponse> deleteComment(@Query("commentId") long commentId);
}
