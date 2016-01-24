package net.ddns.satsukies.bunkasae_card.api;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import net.ddns.satsukies.bunkasae_card.pubsub.AsyncBus;
import net.ddns.satsukies.bunkasae_card.R;
import net.ddns.satsukies.bunkasae_card.card.AccountStorage;
import net.ddns.satsukies.bunkasae_card.util.NetworkUtil;

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
public class GetAsyncTask extends AsyncTask<Handler, Void, JSONArray> {

    Context context;
    final int FLAG_ACCOUNT_NOT_INITIALISED = 0;
    final int FLAG_RESULT_EMPTY = 1;

    public GetAsyncTask(Context c) {
        context = c;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected JSONArray doInBackground(Handler... params) {

        HttpURLConnection connect = null;
        URL url = null;

        //get account state.
        //if account is not initialised, call onCanceled.
        if (AccountStorage.GetAccount(context).equals("00000000")) {
            error(params[0], FLAG_ACCOUNT_NOT_INITIALISED);
        }

        String apiUpdate = context.getResources().getString(R.string.api_get) + "?owner=" + AccountStorage.GetAccount(context);

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
                final JSONArray json = new JSONArray(s);

                if (!AccountStorage.GetAccount(context).equals("00000000") && json.length() == 0) {
                    error(params[0], FLAG_RESULT_EMPTY);
                } else {

                    params[0].post(new Runnable() {
                        @Override
                        public void run() {
                            AsyncBus.get().post(json);
                        }
                    });
                }
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
    protected void onPostExecute(JSONArray s) {
        super.onPostExecute(s);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();

        Log.d("debug", "canceled");
    }

    protected void error(Handler h, final int flag) {
        h.post(new Runnable() {
            @Override
            public void run() {
                try {
                    switch (flag) {
                        case FLAG_ACCOUNT_NOT_INITIALISED:
                            AsyncBus.get().post(new JSONArray("[{'owner':'null', 'id': 'Account is not initialised', 'auth_value': 'null', 'used': 0}]"));
                            break;
                        case FLAG_RESULT_EMPTY:
                            AsyncBus.get().post(new JSONArray("[{'owner':'null', 'id': 'Ticket not found', 'auth_value': 'null', 'used': 0}]"));
                            break;
                        default:
                            AsyncBus.get().post(new JSONArray("[{'owner':'null', 'id': 'Unknown internal error', 'auth_value': 'null', 'used': 0}]"));
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}