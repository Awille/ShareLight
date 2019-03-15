package com.example.will.commentdomain;

import com.example.will.network.retrofit.HttpCallback;
import com.example.will.network.retrofit.RequestManager;
import com.example.will.network.retrofit.RetrofitMrg;
import com.example.will.protocol.CommonConstant;
import com.example.will.protocol.comment.CommentProvider;
import com.example.will.protocol.comment.callback.AddCommentCallback;
import com.example.will.protocol.comment.callback.DeleteCommentCallback;
import com.example.will.protocol.comment.callback.ManageLikeCallback;
import com.example.will.protocol.comment.callback.QueryCommentCallback;
import com.example.will.protocol.comment.callback.QueryCommentsCallback;
import com.example.will.protocol.comment.reponse.AddCommentResponse;
import com.example.will.protocol.comment.reponse.DeleteCommentResponse;
import com.example.will.protocol.comment.reponse.LikeResponse;
import com.example.will.protocol.comment.reponse.QueryCommentResponse;
import com.example.will.protocol.comment.reponse.QueryCommentsResponse;
import com.example.will.protocol.comment.request.AddCommentRequest;
import com.example.will.protocol.comment.request.LikeRequest;

public class CommentProviderImpl implements CommentProvider {

    private static CommentApiService commentApiService = RetrofitMrg.getINSTANCE().getRetrofit().create(CommentApiService.class);

    private static String SUCCESS = CommonConstant.NetWork.SUCCESS;

    @Override
    public void queryComment(long commentId, final QueryCommentCallback callback) {
        RequestManager.call(commentApiService.queryComment(commentId), new HttpCallback<QueryCommentResponse>() {
            @Override
            public void onSuccess(QueryCommentResponse respObj) {
                if (SUCCESS.equals(respObj.getCode())) {
                    callback.onQueryCommentSuccess(respObj);
                } else {
                    callback.onQueryCommentFail(respObj.getCode(), respObj.getMessage());
                }
            }

            @Override
            public void onError(String errCode, String errMsg) {
                callback.onQueryCommentFail(errCode, errMsg);
            }

            @Override
            public void onSystemError(String errCode, String errMsg) {
                callback.onQueryCommentFail(errCode, errMsg);
            }
        });
    }

    @Override
    public void queryComments(long songId, long replyCommentId, final QueryCommentsCallback callback) {
        RequestManager.call(commentApiService.queryComments(songId, replyCommentId), new HttpCallback<QueryCommentsResponse>() {
            @Override
            public void onSuccess(QueryCommentsResponse respObj) {
                if (SUCCESS.equals(respObj.getCode())) {
                    callback.onQueryCommentsSuccess(respObj);
                } else {
                    callback.onQueryCommentFail(respObj.getCode(), respObj.getMessage());
                }
            }

            @Override
            public void onError(String errCode, String errMsg) {
                callback.onQueryCommentFail(errCode, errMsg);
            }

            @Override
            public void onSystemError(String errCode, String errMsg) {
                callback.onQueryCommentFail(errCode, errMsg);
            }
        });
    }

    @Override
    public void addComment(AddCommentRequest request, final AddCommentCallback callback) {
        RequestManager.call(commentApiService.addComment(request), new HttpCallback<AddCommentResponse>() {
            @Override
            public void onSuccess(AddCommentResponse respObj) {
                if (SUCCESS.equals(respObj.getCode())) {
                    callback.onAddCommentSuccess(respObj);
                } else {
                    callback.onAddCommentFail(respObj.getCode(), respObj.getMessage());
                }
            }

            @Override
            public void onError(String errCode, String errMsg) {
                callback.onAddCommentFail(errCode, errMsg);
            }

            @Override
            public void onSystemError(String errCode, String errMsg) {
                callback.onAddCommentFail(errCode, errMsg);
            }
        });
    }

    @Override
    public void manageLike(LikeRequest request, final ManageLikeCallback callback) {
        RequestManager.call(commentApiService.manageLike(request), new HttpCallback<LikeResponse>() {
            @Override
            public void onSuccess(LikeResponse respObj) {
                if (SUCCESS.equals(respObj.getCode())) {
                    callback.onManageLikeSuccess(respObj);
                } else {
                    callback.onManageLikeFail(respObj.getCode(), respObj.getMessage());
                }
            }

            @Override
            public void onError(String errCode, String errMsg) {
                callback.onManageLikeFail(errCode, errMsg);
            }

            @Override
            public void onSystemError(String errCode, String errMsg) {
                callback.onManageLikeFail(errCode, errMsg);
            }
        });
    }

    @Override
    public void deleteComment(long commentId, final DeleteCommentCallback callback) {
        RequestManager.call(commentApiService.deleteComment(commentId), new HttpCallback<DeleteCommentResponse>() {
            @Override
            public void onSuccess(DeleteCommentResponse respObj) {
                if (SUCCESS.equals(respObj.getCode())) {
                    callback.onDeleteCommentSuccess(respObj);
                } else {
                    callback.onDeleteCommentFail(respObj.getCode(), respObj.getMessage());
                }
            }

            @Override
            public void onError(String errCode, String errMsg) {
                callback.onDeleteCommentFail(errCode, errMsg);
            }

            @Override
            public void onSystemError(String errCode, String errMsg) {
                callback.onDeleteCommentFail(errCode, errMsg);
            }
        });
    }
}
