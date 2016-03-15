package it.artefedeacireale.api;

/**
 * Created by davide on 13/03/16.
 */
public class APIConstants {
    public final static String API_LOCAL_BASEURL = "http://192.168.3.12:3000/resources";
    public final static String API_REMOTE_BASEURL = "http://artefedeacireale.it/api/v1/";
    public final static boolean useLocal = false;

    public enum DebugApi {full, response_only, none}
    public static DebugApi debugApiType;

    public static String getBaseUrl(){
        if(useLocal){
            return API_LOCAL_BASEURL;
        }else{
            return API_REMOTE_BASEURL;
        }
    }
}
