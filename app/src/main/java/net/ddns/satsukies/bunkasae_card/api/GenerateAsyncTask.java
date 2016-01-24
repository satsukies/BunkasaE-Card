package net.ddns.satsukies.bunkasae_card.api;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import net.ddns.satsukies.bunkasae_card.R;
import net.ddns.satsukies.bunkasae_card.pubsub.AsyncBus;
import net.ddns.satsukies.bunkasae_card.util.NetworkUtil;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by satsukies on 16/01/24.
 */
public class GenerateAsyncTask extends AsyncTask<String, Void, String> {

    Context context;
    Handler handler;

    public GenerateAsyncTask(Context c, Handler h) {
        context = c;
        handler = h;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    /**
     * @param params 1st: owner, 2nd:auth_value, 3rd:qty
     * @return
     */
    @Override
    protected String doInBackground(String... params) {
        HttpURLConnection connect = null;
        URL url = null;
        String apiGenerate = context.getResources().getString(R.string.api_generate);

        //validate
        if (params.length != 3) {
            Log.e("NETWORK", "Error: Arguments length must be 3.");
            return null;
        }

        //URL construction
        String constructedUrl = apiGenerate + "?owner=" + params[0] + "&auth=" + params[1] + "&qty=" + params[2];

        InputStream stream = null;

        Log.d("debug", constructedUrl);

        try {
            url = new URL(constructedUrl);

            connect = (HttpURLConnection) url.openConnection();

            connect.setRequestMethod("GET");

            connect.setInstanceFollowRedirects(false);

            connect.setDoInput(true);

            connect.setDoOutput(true);

            connect.connect();

            stream = connect.getInputStream();
            final String s = NetworkUtil.getHttpMain(stream);

            handler.post(new Runnable() {
                @Override
                public void run() {
                    AsyncBus.get().post(s);
                }
            });
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
