package com.example.will.songlistdomain;

import com.example.will.network.retrofit.HttpCallback;
import com.example.will.network.retrofit.RequestManager;
import com.example.will.network.retrofit.RetrofitMrg;
import com.example.will.protocol.CommonConstant;
import com.example.will.protocol.songlist.SongListProvider;
import com.example.will.protocol.songlist.callback.AddSongListCallback;
import com.example.will.protocol.songlist.callback.AddSongToSongListCallback;
import com.example.will.protocol.songlist.callback.DeleteSongFromSongListCallback;
import com.example.will.protocol.songlist.callback.DeleteSongListCallback;
import com.example.will.protocol.songlist.callback.QuerySongListCallback;
import com.example.will.protocol.songlist.callback.QuerySongListsCallback;
import com.example.will.protocol.songlist.callback.UpdateSongListInfoCallback;
import com.example.will.protocol.songlist.callback.UploadSongListFileCallback;
import com.example.will.protocol.songlist.request.AddSongListRequest;
import com.example.will.protocol.songlist.request.AddSongToSongListRequest;
import com.example.will.protocol.songlist.request.DeleteSongFromSongListRequest;
import com.example.will.protocol.songlist.request.UpdateSongListRequest;
import com.example.will.protocol.songlist.request.UploadSongListFileRequest;
import com.example.will.protocol.songlist.response.AddSongListResponse;
import com.example.will.protocol.songlist.response.AddSongToSongListResponse;
import com.example.will.protocol.songlist.response.DeleteSongFromSongListResponse;
import com.example.will.protocol.songlist.response.DeleteSongListResponse;
import com.example.will.protocol.songlist.response.QuerySongListResponse;
import com.example.will.protocol.songlist.response.QuerySongListsResponse;
import com.example.will.protocol.songlist.response.UpdateSongListResponse;
import com.example.will.protocol.songlist.response.UploadSongListFileResponse;

public class SongListProviderImpl implements SongListProvider {

    private static SongListApiService songListApiService = RetrofitMrg.getINSTANCE().getRetrofit().create(SongListApiService.class);

    private static String SUCCESS = CommonConstant.NetWork.SUCCESS;


    @Override
    public void querySongList(long songListId, final QuerySongListCallback callback) {
        RequestManager.call(songListApiService.querySongList(songListId), new HttpCallback<QuerySongListResponse>() {
            @Override
            public void onSuccess(QuerySongListResponse respObj) {
                if (respObj.getCode().equals(SUCCESS)) {
                    callback.onQuerySongListSuccess(respObj);
                } else {
                    callback.onQuerySongListFail(respObj.getCode(), respObj.getMessage());
                }
            }

            @Override
            public void onError(String errCode, String errMsg) {
                callback.onQuerySongListFail(errCode, errMsg);
            }

            @Override
            public void onSystemError(String errCode, String errMsg) {
                callback.onQuerySongListFail(errCode, errMsg);
            }
        });
    }

    @Override
    public void querySongLists(long userId, final QuerySongListsCallback callback) {
        RequestManager.call(songListApiService.querySongLists(userId), new HttpCallback<QuerySongListsResponse>() {
            @Override
            public void onSuccess(QuerySongListsResponse respObj) {
                if (respObj.getCode().equals(SUCCESS)) {
                    callback.onQuerySongListsSuccess(respObj);
                } else {
                    callback.onQuerySongListsFail(respObj.getCode(), respObj.getMessage());
                }
            }

            @Override
            public void onError(String errCode, String errMsg) {
                callback.onQuerySongListsFail(errCode, errMsg);
            }

            @Override
            public void onSystemError(String errCode, String errMsg) {
                callback.onQuerySongListsFail(errCode, errMsg);
            }
        });
    }

    @Override
    public void addSongList(final AddSongListRequest request, final AddSongListCallback callback) {
        RequestManager.call(songListApiService.addSongList(request), new HttpCallback<AddSongListResponse>() {
            @Override
            public void onSuccess(AddSongListResponse respObj) {
                if (SUCCESS.equals(respObj.getCode())) {
                    callback.onAddSongListSuccess(respObj);
                } else {
                    callback.onAddSongListFail(respObj.getCode(), respObj.getMessage());
                }
            }

            @Override
            public void onError(String errCode, String errMsg) {
                callback.onAddSongListFail(errCode, errMsg);
            }

            @Override
            public void onSystemError(String errCode, String errMsg) {
                callback.onAddSongListFail(errCode, errMsg);
            }
        });
    }

    @Override
    public void uploadSongListFile(final UploadSongListFileRequest request, final UploadSongListFileCallback callback) {
        RequestManager.call(songListApiService.uploadSongListFile(request), new HttpCallback<UploadSongListFileResponse>() {
            @Override
            public void onSuccess(UploadSongListFileResponse respObj) {
                if (SUCCESS.equals(respObj.getCode())) {
                    callback.onUploadSongListFileSuccess(respObj);
                } else {
                    callback.onUploadSongListFileFail(respObj.getCode(), respObj.getMessage());
                }
            }

            @Override
            public void onError(String errCode, String errMsg) {
                callback.onUploadSongListFileFail(errCode, errMsg);
            }

            @Override
            public void onSystemError(String errCode, String errMsg) {
                callback.onUploadSongListFileFail(errCode, errMsg);
            }
        });
    }

    @Override
    public void updateSongListInfo(final UpdateSongListRequest request, final UpdateSongListInfoCallback callback) {
        RequestManager.call(songListApiService.updateSongListInfo(request), new HttpCallback<UpdateSongListResponse>() {
            @Override
            public void onSuccess(UpdateSongListResponse respObj) {
                if (SUCCESS.equals(respObj.getCode())) {
                    callback.onUpdateSongListInfoSucess(respObj);
                } else {
                    callback.onUpdateSongListInfoFail(respObj.getCode(), respObj.getMessage());
                }
            }

            @Override
            public void onError(String errCode, String errMsg) {
                callback.onUpdateSongListInfoFail(errCode, errMsg);
            }

            @Override
            public void onSystemError(String errCode, String errMsg) {
                callback.onUpdateSongListInfoFail(errCode, errMsg);
            }
        });
    }

    @Override
    public void addSongToSongList(AddSongToSongListRequest request, final AddSongToSongListCallback callback) {
        RequestManager.call(songListApiService.addSongToSongList(request), new HttpCallback<AddSongToSongListResponse>() {
            @Override
            public void onSuccess(AddSongToSongListResponse respObj) {
                if (SUCCESS.equals(respObj.getCode())) {
                    callback.onAddSongToSongListSuccess(respObj);
                } else {
                    callback.onAddSongToSongListFail(respObj.getCode(), respObj.getMessage());
                }
            }

            @Override
            public void onError(String errCode, String errMsg) {
                callback.onAddSongToSongListFail(errCode, errMsg);
            }

            @Override
            public void onSystemError(String errCode, String errMsg) {
                callback.onAddSongToSongListFail(errCode, errMsg);
            }
        });
    }

    @Override
    public void deleSongFromSongList(DeleteSongFromSongListRequest request, final DeleteSongFromSongListCallback callback) {
        RequestManager.call(songListApiService.deleteSongFromSongList(request), new HttpCallback<DeleteSongFromSongListResponse>() {
            @Override
            public void onSuccess(DeleteSongFromSongListResponse respObj) {
                if (SUCCESS.equals(respObj.getCode())) {
                    callback.onDelteSongFromSongListSuccess(respObj);
                } else {
                    callback.onDeleteSongFromSongListFail(respObj.getCode(), respObj.getMessage());
                }
            }

            @Override
            public void onError(String errCode, String errMsg) {
                callback.onDeleteSongFromSongListFail(errCode, errMsg);
            }

            @Override
            public void onSystemError(String errCode, String errMsg) {
                callback.onDeleteSongFromSongListFail(errCode, errMsg);
            }
        });
    }

    @Override
    public void deleteSongList(long songListId, final DeleteSongListCallback callback) {
        RequestManager.call(songListApiService.deleteSongList(songListId), new HttpCallback<DeleteSongListResponse>() {
            @Override
            public void onSuccess(DeleteSongListResponse respObj) {
                if (SUCCESS.equals(respObj.getCode())) {
                    callback.onDeleteSongListSuccess(respObj);
                } else {
                    callback.onDeleteSongListFail(respObj.getCode(), respObj.getCode());
                }
            }

            @Override
            public void onError(String errCode, String errMsg) {
                callback.onDeleteSongListFail(errCode, errMsg);
            }

            @Override
            public void onSystemError(String errCode, String errMsg) {
                callback.onDeleteSongListFail(errCode, errMsg);
            }
        });
    }
}
