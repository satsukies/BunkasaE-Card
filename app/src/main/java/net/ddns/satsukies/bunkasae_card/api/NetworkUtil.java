package net.ddns.satsukies.bunkasae_card.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * Created by satsukies on 16/01/23.
 */
public class NetworkUtil {
    static String getHttpMain(InputStream in) throws IOException, UnsupportedEncodingException {
        StringBuffer sBuffer = new StringBuffer();
        String st = "";

        BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));

        while ((st = reader.readLine()) != null) {
            sBuffer.append(st);
        }

        in.close();

        return sBuffer.toString();
    }

}
