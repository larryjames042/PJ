package mirror.co.larry.pj.NetworkUtil;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtils {

    public static boolean isConnected(Context context){
        ConnectivityManager connMgr = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo info = connMgr.getActiveNetworkInfo();
        if(info!=null && info.isConnected()){
            return true;
        }else{
            return false;
        }
    }
}
