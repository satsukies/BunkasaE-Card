package net.ddns.satsukies.bunkasae_card.api;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import net.ddns.satsukies.bunkasae_card.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by satsukies on 16/01/23.
 */
public class UpdateAsyncTask extends AsyncTask<String, Void, String> {

    private Context context;

    public UpdateAsyncTask(Context c) {
        context = c;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    /**
     * 引数の情報をもとにDBからチケットのリストを引いてくる。
     *
     * @param params 1st:owner, 2nd:id, 3rd:auth_key
     * @return
     */
    @Override
    protected String doInBackground(String... params) {

        HttpURLConnection connect = null;
        URL url = null;
        String apiUpdate = context.getResources().getString(R.string.api_update);

        //validate
        if (params.length != 3) {
            Log.e("NETWORK", "Error: Arguments length must be 3.");
            return null;
        }

        //URL construction
        String constructedUrl = apiUpdate + "?owner=" + params[0] + "&id=" + params[1] + "&auth=" + params[2];

        InputStream stream = null;

        try {
            url = new URL(constructedUrl);

            connect = (HttpURLConnection) url.openConnection();

            connect.setRequestMethod("GET");

            connect.setInstanceFollowRedirects(false);

            connect.setDoInput(true);

            connect.setDoOutput(true);

            connect.connect();

            stream = connect.getInputStream();
            String s = NetworkUtil.getHttpMain(stream);

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
