package com.codebaker.joan.tryconnectbluetooth1122;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

import java.io.IOException;
import java.util.UUID;

public class BluetoothClientActivity extends AppCompatActivity {
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private ProgressBar progressBarConnecting;
    private String address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_client);

        progressBarConnecting = findViewById(R.id.progressBarConnecting);

        address = getIntent().getStringExtra("device_address");
        new ConnectBluetoothTask().execute();
    }

    private class ConnectBluetoothTask extends AsyncTask<Void,Void,Void> {
        private boolean isBtConnected = false;
        private BluetoothSocket bluetoothSocket;
        private BluetoothAdapter bluetoothAdapter;

        @Override
        protected void onPreExecute() {
            progressBarConnecting.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... devices) {
            try {
                if(bluetoothSocket == null || !isBtConnected) {
                    bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                    BluetoothDevice dispositiv = bluetoothAdapter.getRemoteDevice(address);
                    bluetoothSocket = dispositiv.createInsecureRfcommSocketToServiceRecord(myUUID);
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    bluetoothSocket.connect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}
