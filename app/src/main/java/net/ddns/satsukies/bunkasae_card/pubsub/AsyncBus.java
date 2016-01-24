package net.ddns.satsukies.bunkasae_card.pubsub;

import com.squareup.otto.Bus;

/**
 * Created by satsukies on 16/01/13.
 */
public class AsyncBus {

    private static Bus sBus = new Bus();

    public static Bus get(){
        return sBus;
    }
}
