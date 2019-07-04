package nikitaverma.example.com.spotify;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import java.util.Random;

import nikitaverma.example.com.spotify.common.App;
import nikitaverma.example.com.spotify.helpers.NotificationManager;

public class MyBroadcastReceiver extends BroadcastReceiver {
    private NotificationManager mNotificationManagerUtils;
    private NotificationCompat.Builder nb;
    private int mId = 101;

    @Override
    public void onReceive(Context context, Intent intent) {
        // This is sent with all broadcasts, regardless of type. The value is taken from
        // System.currentTimeMillis(), which you can compare to in order to determine how
        // old the event is.
        long timeSentInMs = intent.getLongExtra("timeSent", 0L);
        if (mNotificationManagerUtils == null)
            mNotificationManagerUtils = new NotificationManager(context);

        String action = intent.getAction();
        String trackId = "";
        String artistName = "";
        String albumName = "";
        String trackName = "";
        if (action.equals(BroadcastTypes.METADATA_CHANGED)) {
            trackId = intent.getStringExtra("id");
            artistName = intent.getStringExtra("artist");
            albumName = intent.getStringExtra("album");
            trackName = intent.getStringExtra("track");
            Toast.makeText(App.getContext(),"Broadcast", Toast.LENGTH_LONG).show();
            sendNotification(trackName, artistName, albumName);
            int trackLengthInSec = intent.getIntExtra("length", 0);
            // Do something with extracted information...
        } else if (action.equals(BroadcastTypes.PLAYBACK_STATE_CHANGED)) {
            boolean playing = intent.getBooleanExtra("playing", false);
            int positionInMs = intent.getIntExtra("playbackPosition", 0);
            // Do something with extracted information
        } else if (action.equals(BroadcastTypes.QUEUE_CHANGED)) {
            // Sent only as a notification, your app may want to respond accordingly.
        }
     //
    }

    private void sendNotification(String title, String body, String url) {
        if (mNotificationManagerUtils != null) {
            nb = mNotificationManagerUtils.
                    getAndroidChannelNotification(title, body, url);
            mNotificationManagerUtils.getManager().notify(mId, nb.build());


        }

    }

    static final class BroadcastTypes {
        static final String SPOTIFY_PACKAGE = "com.spotify.music";
        static final String PLAYBACK_STATE_CHANGED = SPOTIFY_PACKAGE + ".playbackstatechanged";
        static final String QUEUE_CHANGED = SPOTIFY_PACKAGE + ".queuechanged";
        static final String METADATA_CHANGED = SPOTIFY_PACKAGE + ".metadatachanged";
    }

}