package net.ddns.satsukies.bunkasae_card.card;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.squareup.otto.Subscribe;

import net.ddns.satsukies.bunkasae_card.BusHolder;
import net.ddns.satsukies.bunkasae_card.ButtonClickEvent;
import net.ddns.satsukies.bunkasae_card.R;

/**
 * Created by satsukies on 16/01/07.
 */
public class CardFragment extends Fragment {

    public static final String TAG = "CardEmulationFragment";

    EditText account;

    /**
     * Called when sample is created. Displays generic UI with welcome text.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_card, container, false);
        account = (EditText) v.findViewById(R.id.card_account_field);
        account.setText(AccountStorage.GetAccount(getActivity()));
        account.addTextChangedListener(new AccountUpdater());
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        BusHolder.get().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        BusHolder.get().unregister(this);
    }

    @Subscribe
    public void subscribe(ButtonClickEvent e){
        //When this method is called, TextWatcher will work and update account number
        account.setText(e.message);
    }

    private class AccountUpdater implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // Not implemented.
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // Not implemented.
        }

        @Override
        public void afterTextChanged(Editable s) {
            String account = s.toString();
            AccountStorage.SetAccount(getActivity(), account);
        }
    }
}

