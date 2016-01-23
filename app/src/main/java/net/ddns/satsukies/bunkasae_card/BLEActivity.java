package net.ddns.satsukies.bunkasae_card;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class BLEActivity extends AppCompatActivity {
    // BLE用
    private BluetoothManager mBluetoothManager;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothAdapter.LeScanCallback mLeScanCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ble);
        ButterKnife.bind(this);

        // BLE
        mBluetoothManager = (BluetoothManager)getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = mBluetoothManager.getAdapter();

        // BLEスキャンした際のコールバック
        mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
            @Override
            public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
                String msg = "ADDRESS=" + device.getAddress() + "\nRSSI=" + rssi;
                Log.d("BLE", msg);
            }
        };
    }

    // BLEスキャン開始ボタン
    @OnClick({R.id.ble_start})
    public void onBtnStartBleScanClicked(View view) {
        Log.d("debug", "start");
        mBluetoothAdapter.startLeScan(mLeScanCallback);
    }

    // BLEスキャン停止ボタン
    @OnClick({R.id.ble_stop})
    public void onBtnStopBleScanClicked(View view) {
        Log.d("debug", "stop");
        mBluetoothAdapter.stopLeScan(mLeScanCallback);
    }
}
