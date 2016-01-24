package net.ddns.satsukies.bunkasae_card.util;

import android.content.Context;
import android.content.Intent;

import net.ddns.satsukies.bunkasae_card.BLEActivity;
import net.ddns.satsukies.bunkasae_card.SettingsActivity;
import net.ddns.satsukies.bunkasae_card.TicketActivity;
import net.ddns.satsukies.bunkasae_card.card.CardActivity;
import net.ddns.satsukies.bunkasae_card.model.Ticket;
import net.ddns.satsukies.bunkasae_card.reader.ReaderActivity;

/**
 * Created by satsukies on 16/01/24.
 */
public class DrawerUtil {
    public static Intent generateDrawerMenuIntent(Context context, String menu_label) {

        try {
            // Menu アイテムのみ
            if (menu_label.equals("About")) {
//                return new Intent(context, JUMP.class);
            }

            if (menu_label.equals("Get Your Card ID")) {
                return new Intent(context, CardActivity.class);
            }

            if (menu_label.equals("Card Mode")) {
                return new Intent(context, CardActivity.class);
            }

            if (menu_label.equals("Reader Mode")) {
                return new Intent(context, ReaderActivity.class);
            }

            if (menu_label.equals("How to Use")) {
                return new Intent(context, BLEActivity.class);
            }

            if (menu_label.equals("Settings")) {
                return new Intent(context, SettingsActivity.class);
            }

            if(menu_label.equals("Tickets")){
                return new Intent(context, TicketActivity.class);
            }

            if(menu_label.equals("Generate Tickets")){
//                return new Intent(context, GenerateActivity.class);
            }


        } catch (NullPointerException exp) {
            exp.printStackTrace();
            return null;
        }

        return null;
    }
}
