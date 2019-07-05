package nikitaverma.example.com.spotify;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import nikitaverma.example.com.spotify.common.Constants;
import nikitaverma.example.com.spotify.common.listener.CallBrowseApiListener;
import nikitaverma.example.com.spotify.helpers.NotificationManager;

public class MyBroadcastReceiver extends BroadcastReceiver {
    private NotificationManager mNotificationManagerUtils;
    private NotificationCompat.Builder nb;
    private int mId = 101;
    private CallBrowseApiListener mCallBrowseApiListener;

    public MyBroadcastReceiver(){}
    public MyBroadcastReceiver(CallBrowseApiListener callBrowseApiListener){
        mCallBrowseApiListener = callBrowseApiListener;
    }

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
        if (action.equals(Constants.BROADCAST_ACTION_METADATACHANGED)) {
            trackId = intent.getStringExtra("id");
            artistName = intent.getStringExtra("artist");
            albumName = intent.getStringExtra("album");
            trackName = intent.getStringExtra("track");
          //  Toast.makeText(context, "Broadcast", Toast.LENGTH_LONG).show();
          //  sendNotification(trackName, artistName, albumName);
            int trackLengthInSec = intent.getIntExtra("length", 0);
            // Do something with extracted information...
        }
        else if (action.equals(Constants.BROADCAST_ACTION_PLAYBACKSTATECHANGED)) {
            boolean playing = intent.getBooleanExtra("playing", false);
            int positionInMs = intent.getIntExtra("playbackPosition", 0);
            // Do something with extracted information
        }
        else if (action.equals(Constants.BROADCAST_ACTION_QUEUECHANGED)) {
            // Sent only as a notification, your app may want to respond accordingly.
        }
        else if(action.equals(Constants.BROADCAST_ACTION_BROWSE)){
            mCallBrowseApiListener.callBrowseApi(intent.getStringExtra(Constants.API_NAME));
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

}