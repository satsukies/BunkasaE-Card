package net.ddns.satsukies.bunkasae_card;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import net.ddns.satsukies.bunkasae_card.model.Ticket;

import java.util.ArrayList;

/**
 * Created by satsukies on 16/01/23.
 */
public class TicketAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    ArrayList<Ticket> ticketList;

    TicketAdapter(Context c){
        context = c;
        inflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setTicketList(ArrayList<Ticket> list){
        ticketList = list;
    }

    @Override
    public int getCount() {
        return ticketList.size();
    }

    @Override
    public Object getItem(int position) {
        return ticketList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return ticketList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);

        ((TextView)convertView.findViewById(android.R.id.text1)).setText(ticketList.get(position).getTicketId());
        return convertView;
    }
}
