package net.ddns.satsukies.bunkasae_card;

import com.squareup.otto.Bus;

/**
 * Created by satsukies on 16/01/13.
 */
public class AsyncHolder {

    private static Bus sBus = new Bus();

    public static Bus get(){
        return sBus;
    }
}
