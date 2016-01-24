package net.ddns.satsukies.bunkasae_card;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.otto.Subscribe;

import net.ddns.satsukies.bunkasae_card.api.GenerateAsyncTask;
import net.ddns.satsukies.bunkasae_card.pubsub.AsyncBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GenerateTicketActivity extends AppCompatActivity {

    @Bind(R.id.gene_txt_result)
    TextView txt_result;

    @Bind(R.id.gene_edit_owner)
    EditText edit_owner;

    @Bind(R.id.gene_edit_auth)
    EditText edit_auth;

    @Bind(R.id.gene_spin_qty)
    Spinner spin_qty;

    @Bind(R.id.gene_btn_exe)
    Button btn_exe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_ticket);

        ButterKnife.bind(this);
    }

    @OnClick({R.id.gene_btn_exe})
    public void executeGenerate(){
        new GenerateAsyncTask(this, new Handler()).execute(edit_owner.getEditableText().toString(), edit_auth.getEditableText().toString(), spin_qty.getSelectedItem().toString());
    }

    @Override
    protected void onResume() {
        super.onResume();

        AsyncBus.get().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();

        AsyncBus.get().unregister(this);
    }

    @Subscribe
    public void refreshResult(String result){
        txt_result.setText(result);
    }
}
