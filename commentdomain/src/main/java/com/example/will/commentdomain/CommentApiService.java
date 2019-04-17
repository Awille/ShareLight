package com.example.will.commentdomain;

import com.example.will.network.retrofit.HttpCall;
import com.example.will.network.retrofit.RetrofitMrg;
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
    @GET(RetrofitMrg.commentUrl)
    HttpCall<QueryCommentResponse> queryComment(@Query("commentId") long commentId);

    @GET(RetrofitMrg.commentUrl)
    HttpCall<QueryCommentsResponse> queryComments(@Query("songId") Long songId, @Query("replyCommentId") Long replyCommentId);

    @POST(RetrofitMrg.commentUrl)
    HttpCall<AddCommentResponse> addComment(@Body AddCommentRequest request);

    @PUT(RetrofitMrg.commentUrl)
    HttpCall<LikeResponse> manageLike(@Body LikeRequest request);

    @DELETE(RetrofitMrg.commentUrl)
    HttpCall<DeleteCommentResponse> deleteComment(@Query("commentId") long commentId);
}
