package nikitaverma.example.com.spotify.common;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;

import nikitaverma.example.com.spotify.MyBroadcastReceiver;

public class App extends Application {
    private static Context sContext;
    private Activity mCurrentActivity = null;
    private MyBroadcastReceiver broadcastReceiver;

    public static Context getContext() {
        return sContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
        broadcastReceiver = new MyBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.spotify.music.playbackstatechanged");
        intentFilter.addAction("com.spotify.music.metadatachanged");
        intentFilter.addAction("com.spotify.music.queuechanged");
        intentFilter.addAction("com.spotify.music.active");
        registerReceiver(broadcastReceiver, intentFilter);
    }


}
