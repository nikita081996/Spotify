package nikitaverma.example.com.spotify;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.spotify.protocol.client.CallResult;
import com.spotify.protocol.types.PlayerState;

import nikitaverma.example.com.spotify.common.BaseActivity;
import nikitaverma.example.com.spotify.common.Constants;
import nikitaverma.example.com.spotify.common.listener.CallBrowseApiListener;
import nikitaverma.example.com.spotify.helpers.api.ApiClient;
import nikitaverma.example.com.spotify.helpers.api.ApiInterface;
import nikitaverma.example.com.spotify.helpers.api.MakeCalls;
import nikitaverma.example.com.spotify.model.search_api.Search;
import nikitaverma.example.com.spotify.utils.ToastUtils;
import retrofit2.Call;

public class MainActivity extends BaseActivity implements MakeCalls.CallListener, CallResult.ResultCallback, CallBrowseApiListener {

    private MyBroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerBroadcastListener();
    }

    void registerBroadcastListener() {
        broadcastReceiver = new MyBroadcastReceiver(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.BROADCAST_ACTION_BROWSE);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);

        registerReceiver(broadcastReceiver, intentFilter);
    }


    public void callSearch(String apiName) {
        ApiInterface apiInterface = ApiClient.createService(ApiInterface.class);
        Call<Search> call = apiInterface.accessToken(TOKEN, "Chainsmoker", "album,artist,playlist,track");

        if (call != null)
            MakeCalls.commonCall(call, this, apiName);
    }


    @Override
    public void onResult(Object o) {
        PlayerState playerState = (PlayerState) o;
        ToastUtils.showLongToast(getApplicationContext(),playerState.playbackOptions + "");
    }

    @Override
    public void callBrowseApi(String browseApi) {
        callSearch(browseApi);
    }

    @Override
    public void onCallSuccess(@NonNull Object result, String apiName) {
        if (apiName == Constants.BROWSE) {
            Search response = (Search) result;
            ToastUtils.showLongToast(getApplicationContext(), response.getArtists().getItems()[0].getUri() + " success");
            mSpotifyAppRemote.getPlayerApi().play(response.getPlaylists().getItems()[0].getUri());
            //     mSpotifyAppRemote.getPlayerApi().play("spotify:playlist:6iVecbNLLdHHmeVP1mPzVd");

        } else {


        }


    }

    @Override
    public void onCallFailure(@NonNull Object result, String apiName) {
        ToastUtils.showLongToast(getApplicationContext(), result.toString() + " failure");

    }

}

