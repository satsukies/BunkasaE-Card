package net.ddns.satsukies.bunkasae_card.api;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import net.ddns.satsukies.bunkasae_card.R;
import net.ddns.satsukies.bunkasae_card.reader.CardReaderFragment;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by satsukies on 16/01/23.
 */
public class GetAsyncTask extends AsyncTask<Void, Void, String> {

    Context context;

    public GetAsyncTask(Context c) {
        context = c;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Void... params) {

        HttpURLConnection connect = null;
        URL url = null;
//        String apiUpdate = "http://satsukies.ddns.net/down.php";
        String apiUpdate = context.getResources().getString(R.string.api_update);

        InputStream stream = null;

        try {
            url = new URL(apiUpdate);

            connect = (HttpURLConnection) url.openConnection();

            connect.setRequestMethod("GET");

            connect.setInstanceFollowRedirects(false);

            connect.setDoInput(true);

            connect.setDoOutput(true);

            connect.connect();

            stream = connect.getInputStream();
            try {
                String s = NetworkUtil.getHttpMain(stream);
                JSONArray json = new JSONArray(s);
            } catch (JSONException e) {
                e.printStackTrace();
            }

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