package mitso.volodymyr.tryretrofit.support;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import mitso.volodymyr.tryretrofit.R;

public class Support {

    public boolean checkNetworkConnection(Context _context) {

        final ConnectivityManager connectivityManager = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo networkInfoWifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        final NetworkInfo networkInfoMobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        return ((networkInfoWifi != null && networkInfoWifi.isConnected()) || (networkInfoMobile != null && networkInfoMobile.isConnected()));
    }

    public void showToastNoNetworkConnection(Context _context) {

        Toast.makeText(_context, R.string.s_no_network_connection, Toast.LENGTH_LONG).show();
    }

    public void showToastError(Context _context) {

        Toast.makeText(_context, R.string.s_error, Toast.LENGTH_LONG).show();
    }

    public void showToastTaskRunning(Context _context) {

        Toast.makeText(_context, R.string.s_task_running, Toast.LENGTH_LONG).show();
    }
}
