package nikitaverma.example.com.spotify;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.protocol.client.CallResult;
import com.spotify.protocol.types.PlayerState;
import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;

import nikitaverma.example.com.spotify.helpers.api.ApiClient;
import nikitaverma.example.com.spotify.helpers.api.ApiInterface;
import nikitaverma.example.com.spotify.helpers.api.MakeCalls;
import nikitaverma.example.com.spotify.model.search_api.Search;
import retrofit2.Call;

public class MainActivity extends AppCompatActivity implements MakeCalls.CallListener, CallResult.ResultCallback {

    public static String TOKEN;
    private static final int REQUEST_CODE = 1337;
    AuthenticationRequest request = null;
    private SpotifyAppRemote mSpotifyAppRemote;
    ConnectionParams connectionParams;
    String client_id = "b8db16d30bca4b9bbebe71349baf998c";
    String redirect_uri ="nikitaverma://callback";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        request = new AuthenticationRequest.Builder("b8db16d30bca4b9bbebe71349baf998c", AuthenticationResponse.Type.TOKEN, "nikitaverma://callback")
                .setScopes(new String[]{"user-read-private", "playlist-read", "playlist-read-private", "streaming"})
                .setShowDialog(true)
                .build();

        AuthenticationClient.openLoginActivity(this, REQUEST_CODE, request);
      //  spotifyConnection();
    }

    @Override
    public void onCallSuccess(@NonNull Object result, String apiName) {
        if (apiName == "search") {
            Search response = (Search) result;
            Toast.makeText(getApplicationContext(), response.getArtists().getItems()[0].getUri() + " success", Toast.LENGTH_LONG).show();
            mSpotifyAppRemote.getPlayerApi().play(response.getArtists().getItems()[0].getUri());
        } else {

            Toast.makeText(getApplicationContext(), "Successssss", Toast.LENGTH_LONG).show();

        }


    }

    @Override
    public void onCallFailure(@NonNull Object result, String apiName) {

        Toast.makeText(getApplicationContext(), result.toString() + " failure", Toast.LENGTH_LONG).show();

    }

    private void callSearch() {

        ApiInterface apiInterface = ApiClient.createService(ApiInterface.class);
        //    Call<Search> call = apiInterface.accessToken("Authorization: Basic "+eStr, requestBody);
        Call<Search> call = apiInterface.accessToken("Authorization: Bearer "+TOKEN, "Chainsmoker", "album,artist,playlist,track");

        if (call != null)
            MakeCalls.commonCall(call, this, "search");
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Uri uri = intent.getData();
        if (uri != null) {
            AuthenticationResponse response = AuthenticationResponse.fromUri(uri);
            switch (response.getType()) {
                // Response was successful and contains auth token
                case TOKEN:
                    // Handle successful response
                    TOKEN = response.getAccessToken();
                    Log.d("TOKEN", response.getAccessToken());
                    Log.d("Expire", response.getExpiresIn() + "");
                    Log.d("type", response.getType().toString());
                    Toast.makeText(getApplicationContext(), response.getExpiresIn() + "", Toast.LENGTH_LONG).show();
                    Toast.makeText(getApplicationContext(), response.getType() + "", Toast.LENGTH_LONG).show();
                    callSearch();
                    break;

                // Auth flow returned an error
                case ERROR:
                    // Handle error response
                    Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();

                    break;

                // Most likely auth flow was cancelled
                default:
                    Toast.makeText(getApplicationContext(), "default", Toast.LENGTH_LONG).show();

            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        // Check if result comes from the correct activity
        if (requestCode == REQUEST_CODE) {
            AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, intent);

            switch (response.getType()) {
                // Response was successful and contains auth token
                case TOKEN:
                    TOKEN = response.getAccessToken();
                    Log.d("TOKEN", response.getAccessToken());
                    Log.d("Expire", response.getExpiresIn() + "");
                    Log.d("type", response.getType().toString());
                    Toast.makeText(getApplicationContext(), response.getExpiresIn() + "", Toast.LENGTH_LONG).show();
                    Toast.makeText(getApplicationContext(), response.getType() + "", Toast.LENGTH_LONG).show();
                    callSearch();
                    // Handle successful response
                    break;

                // Auth flow returned an error
                case ERROR:
                    AuthenticationClient.openLoginInBrowser(this, request);
                    Toast.makeText(getApplicationContext(), response.getError() + "", Toast.LENGTH_LONG).show();

                    // Handle error response
                    break;

                // Most likely auth flow was cancelled
                default:
                    // Handle other cases
            }
        }
    }

    void spotifyConnection(){
        SpotifyAppRemote.connect(this, new ConnectionParams.Builder(client_id)
                        .setRedirectUri(redirect_uri)
                        .showAuthView(true)
                        .build(),
                new Connector.ConnectionListener() {

                    @Override
                    public void onConnected(SpotifyAppRemote spotifyAppRemote) {
                        mSpotifyAppRemote = spotifyAppRemote;
                        Log.d("MainActivity", "Connected! Yay!");

                        // Now you can start interacting with App Remote
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Log.e("MainActivity", throwable.getMessage(), throwable);
                        Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_LONG).show();
                        // Something went wrong when attempting to connect! Handle errors here
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        connectionParams =
                new ConnectionParams.Builder(client_id)
                        .setRedirectUri(redirect_uri)
                        .showAuthView(true)
                        .build();
       // if(connectionParams != null)
            spotifyConnection();
    }

    void connected(String uri){
        CallResult<PlayerState> playerState = mSpotifyAppRemote.getPlayerApi().getPlayerState();
        playerState.setResultCallback(this);
//        playerState.setResultCallback(new CallResult.ResultCallback<PlayerState>() {
//            @Override
//            public void onResult(PlayerState playerState) {
//               // PlayerState playerState = playerState;
//                Toast.makeText(getApplicationContext(),playerState.playbackOptions+"", Toast.LENGTH_LONG).show();
//            }
//        });
        mSpotifyAppRemote.getPlayerApi().play("spotify:playlist:37i9dQZF1DX2sUQwD7tbmL");

      //  Toast.makeText(getApplicationContext(),mSpotifyAppRemote.getPlayerApi().getPlayerState(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResult(Object o) {
        PlayerState playerState = (PlayerState) o;
                        Toast.makeText(getApplicationContext(),playerState.playbackOptions+"", Toast.LENGTH_LONG).show();

        SpotifyAppRemote.disconnect(mSpotifyAppRemote);

    }
}
