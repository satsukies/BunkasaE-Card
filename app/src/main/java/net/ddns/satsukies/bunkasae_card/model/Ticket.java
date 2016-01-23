package net.ddns.satsukies.bunkasae_card.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by satsukies on 16/01/23.
 */
public class Ticket {
    JSONObject row;

    long id;
    String ticket_id;
    String owner;
    String used;

    //hidden values
    String date;
    String auth_value;

    public Ticket(JSONObject obj){
        row = obj;

        parseObject();
    }

    private void parseObject(){
        try {
            ticket_id = row.getString("id");
        }catch (JSONException e){
            e.printStackTrace();
        }

        return;
    }

    public long getId(){
        return id;
    }

    public String getTicketId(){
        return ticket_id;
    }
}
