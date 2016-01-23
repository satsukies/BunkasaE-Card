package net.ddns.satsukies.bunkasae_card;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.squareup.otto.Subscribe;

import net.ddns.satsukies.bunkasae_card.api.GetAsyncTask;
import net.ddns.satsukies.bunkasae_card.model.Ticket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TicketActivity extends AppCompatActivity {

    @Bind(R.id.list)
    ListView list;

    ArrayList<Ticket> ticketList;

    TicketAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);
        ButterKnife.bind(this);

        ticketList = new ArrayList<>();
        adapter = new TicketAdapter(this);

        adapter.setTicketList(ticketList);
        list.setAdapter(adapter);

        Handler h = new Handler();

        new GetAsyncTask(this).execute(h);
    }

    @Subscribe
    public void fetchTickets(JSONArray array) {
        try {
            for (int x = 0; x < array.length(); x++) {
                ticketList.add(new Ticket(array.getJSONObject(x)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        AsyncHolder.get().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        AsyncHolder.get().unregister(this);
    }
}
