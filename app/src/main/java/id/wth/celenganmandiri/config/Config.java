package id.wth.celenganmandiri.config;

/**
 * Created by adminmc on 13/03/17.
 */
public class Config {

    public static final String URL_HTTP_STRING = "http://";
    public static final String URL_HTTPS_STRING = "https://";

    /*
    |-----------------------------------------------------------------------------------------------
    | URL server for this application to transfer data.
    |-----------------------------------------------------------------------------------------------
     */
    //public static final String url_Server = "36.67.51.253/api/";
    public static final String url_Server = "apigateway.mandiriwhatthehack.com";

    /*
    |-----------------------------------------------------------------------------------------------
    | Key for session
    |-----------------------------------------------------------------------------------------------
    */
    public static final String KEY_SESSION = "session";

    /*
    |-----------------------------------------------------------------------------------------------
    | Directory name to store captured images and videos
    |-----------------------------------------------------------------------------------------------
    */
    public static final String IMAGE_DIRECTORY_NAME = "Android File Upload";

    /*
    |-----------------------------------------------------------------------------------------------
    | Database name
    |-----------------------------------------------------------------------------------------------
    */
    public static final String DATABASE_NAME = "wthdb";

    /*
    |-----------------------------------------------------------------------------------------------
    | Database path
    |-----------------------------------------------------------------------------------------------
    */
    public static final String DATABASE_PATH = "/data/data/com.infomedia.teamasiv/databases/";

    /*
   |-----------------------------------------------------------------------------------------------
   | Key for imei
   |-----------------------------------------------------------------------------------------------
   */
    public static final String KEY_IMEI = "imei";

    /*
    |-----------------------------------------------------------------------------------------------
    | Key for imei
    |-----------------------------------------------------------------------------------------------
    */
    public static final String DEVICE = "android";

    /*
    |-----------------------------------------------------------------------------------------------
    | Method for append URL and URI as API
    |-----------------------------------------------------------------------------------------------
    */
    public static String makeUrlString(String uri) {
        StringBuilder url = new StringBuilder(URL_HTTPS_STRING);
        url.append(url_Server);
        url.append("/");
        url.append(uri);
        return url.toString();
    }

    /*
    |-----------------------------------------------------------------------------------------------
    | Method for append URL and URI as API
    |-----------------------------------------------------------------------------------------------
    */
    public static String makeUrlStringBoarding(String uri) {
        StringBuilder url = new StringBuilder(URL_HTTPS_STRING);
        url.append("oob.mandiriwhatthehack.com");
        url.append("/");
        url.append(uri);
        return url.toString();
    }
}
