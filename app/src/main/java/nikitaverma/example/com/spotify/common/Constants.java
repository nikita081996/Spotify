package nikitaverma.example.com.spotify.common;

public class Constants {
    public static final String ANDROID_CHANNEL_NAME = "ANDROID CHANNEL";
    public static final String ANDROID_CHANNEL_ID = "nikitaverma.example.com.spotify.ANDROID";

    public static final String EXCEPTION_LOG_TAG = "exception";
    public static final String MESSAGE_EXCEPTION_TAG = "message";
    public static final String EXCEPTION_TAG = "exception : ";
    public static final String API_NAME = "api_name";
    public static final String TOKEN = "TOKEN";

    /**
     * Api constants
     */
    public static final String TOKEN_PREFIX = "Authorization: Bearer ";
    public static final String TOKEN_ERROR = "Unable to access token";
    public static final String SPOTIFY_CONNECTION_ERROR = "Unable to connect to Spotify App";
    public static final String PLEASE_INSTALL_SPOTIFY_APP = "Please install Spotify App";

    public static final String CLIENT_ID = "b8db16d30bca4b9bbebe71349baf998c";
    public static final String REDIRECT_URI = "nikitaverma://callback";
    public static final int REQUEST_CODE = 1337;

    /**
     * scope constants
     */
    public static final String USER_READ_PRIVATE_SCOPE = "user-read-private";
    public static final String PLAYLIST_READ = "playlist-read";
    public static final String PLAYLIST_READ_PRIVATE = "playlist-read-private";
    public static final String STREAMING = "streaming";


    /**
     * Api name
     */

    public static final String BROWSE = "browse";
    /**
     * Broadcast Action Constants
     */
    public static final String BROADCAST_ACTION_PLAYBACKSTATECHANGED = "com.spotify.music.playbackstatechanged";
    public static final String BROADCAST_ACTION_METADATACHANGED = "com.spotify.music.metadatachanged";
    public static final String BROADCAST_ACTION_QUEUECHANGED = "com.spotify.music.queuechanged";
    public static final String BROADCAST_ACTION_ACTIVE = "com.spotify.music.active";
    public static final String BROADCAST_ACTION_BROWSE = "com.browse.api";

}
