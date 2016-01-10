package net.ddns.satsukies.bunkasae_card.card;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import net.ddns.satsukies.bunkasae_card.R;

public class CardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            CardFragment fragment = new CardFragment();
            transaction.replace(R.id.sample_content_fragment_card, fragment);
            transaction.commit();
        }

    }
}
