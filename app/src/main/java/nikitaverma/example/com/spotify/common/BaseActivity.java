package nikitaverma.example.com.spotify.common;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;

import nikitaverma.example.com.spotify.MyBroadcastReceiver;
import nikitaverma.example.com.spotify.utils.LoaderUtils;
import nikitaverma.example.com.spotify.utils.LoggerUtils;
import nikitaverma.example.com.spotify.utils.ToastUtils;

public class BaseActivity extends AppCompatActivity implements Connector.ConnectionListener {
    protected static SpotifyAppRemote mSpotifyAppRemote;
    protected static String TOKEN;

    private ConnectionParams connectionParams;
    private AuthenticationRequest request = null;
    private MyBroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerBroadcastListener();
    }

    @Override
    protected void onStart() {
        super.onStart();
        connectionParams =
                new ConnectionParams.Builder(Constants.CLIENT_ID)
                        .setRedirectUri(Constants.REDIRECT_URI)
                        .showAuthView(true)
                        .build();
        SpotifyAppRemote.connect(this, connectionParams, this);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    void registerBroadcastListener() {
        broadcastReceiver = new MyBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.BROADCAST_ACTION_PLAYBACKSTATECHANGED);
        intentFilter.addAction(Constants.BROADCAST_ACTION_METADATACHANGED);
        intentFilter.addAction(Constants.BROADCAST_ACTION_QUEUECHANGED);
        intentFilter.addAction(Constants.BROADCAST_ACTION_ACTIVE);

        registerReceiver(broadcastReceiver, intentFilter);
    }

    /**
     * call when AuthenticationClient recieved data from Spotify App
     *
     * @param requestCode
     * @param resultCode
     * @param intent
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (requestCode == Constants.REQUEST_CODE) {
            AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, intent);

            switch (response.getType()) {
                case TOKEN:
                    TOKEN = Constants.TOKEN_PREFIX + response.getAccessToken();
                    LoggerUtils.d(Constants.TOKEN, response.getAccessToken());
                    Intent intentBroadcast = new Intent();
                    intentBroadcast.setAction(Constants.BROADCAST_ACTION_BROWSE);
                    intentBroadcast.putExtra(Constants.API_NAME, Constants.BROWSE);
                    intentBroadcast.addCategory(Intent.CATEGORY_DEFAULT);
                    sendBroadcast(intentBroadcast);
                    break;

                case ERROR:
                    // Authenticate through browser
                    AuthenticationClient.openLoginInBrowser(this, request);
                    break;

                default:
            }
        }
    }

    /**
     * call when AuthenticationClient recieved data from Spotify Web
     *
     * @param intent
     */

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Uri uri = intent.getData();
        if (uri != null) {
            AuthenticationResponse response = AuthenticationResponse.fromUri(uri);
            switch (response.getType()) {
                // Response was successful and contains auth token
                case TOKEN:
                    TOKEN = Constants.TOKEN_PREFIX + response.getAccessToken();
                    LoggerUtils.d(Constants.TOKEN, response.getAccessToken());
                    Intent intentBroadcast = new Intent();
                    intentBroadcast.setAction(Constants.BROADCAST_ACTION_BROWSE);
                    intentBroadcast.putExtra(Constants.API_NAME, Constants.BROWSE);
                    intentBroadcast.addCategory(Intent.CATEGORY_DEFAULT);
                    sendBroadcast(intentBroadcast);

                    break;

                case ERROR:
                    ToastUtils.showLongToast(getApplication(), Constants.TOKEN_ERROR);
                    break;

                default:
                    ToastUtils.showLongToast(getApplication(), "default");

            }
        }
    }

    @Override
    public void onConnected(SpotifyAppRemote spotifyAppRemote) {
        mSpotifyAppRemote = spotifyAppRemote;

        request = new AuthenticationRequest.Builder(Constants.CLIENT_ID, AuthenticationResponse.Type.TOKEN, Constants.REDIRECT_URI)
                .setScopes(new String[]{Constants.USER_READ_PRIVATE_SCOPE, Constants.PLAYLIST_READ, Constants.PLAYLIST_READ_PRIVATE, Constants.STREAMING})
                .setShowDialog(true)
                .build();

        AuthenticationClient.openLoginActivity(this, Constants.REQUEST_CODE, request);
    }

    @Override
    public void onFailure(Throwable throwable) {
        ToastUtils.showLongToast(getApplicationContext(), Constants.SPOTIFY_CONNECTION_ERROR);
        ToastUtils.showLongToast(getApplicationContext(), Constants.PLEASE_INSTALL_SPOTIFY_APP);

    }

     /*void spotifyConnection() {
        SpotifyAppRemote.connect(this, new ConnectionParams.Builder(Constants.CLIENT_ID)
                        .setRedirectUri(Constants.REDIRECT_URI)
                        .showAuthView(true)
                        .build(),
                new Connector.ConnectionListener() {

                    @Override
                    public void onConnected(SpotifyAppRemote spotifyAppRemote) {
                        mSpotifyAppRemote = spotifyAppRemote;
                        Log.d("App", "Connected! Yay!");
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Log.e("MainActivity", throwable.getMessage(), throwable);
                        Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }*/


}
