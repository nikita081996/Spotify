package nikitaverma.example.com.spotify.common;

import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;

import nikitaverma.example.com.spotify.MyBroadcastReceiver;

public class App extends Application {
    private static Context sContext;


    public static Context getContext() {
        return sContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;


    }


}
