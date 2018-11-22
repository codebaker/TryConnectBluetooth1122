package com.codebaker.joan.tryconnectbluetooth1122;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import java.io.IOException;
import java.util.UUID;

public class BluetoothClientActivity extends AppCompatActivity {
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private ProgressBar progressBarConnecting;
    private String address;
    private BluetoothSocket bluetoothSocket;
    private BluetoothAdapter bluetoothAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_client);

        progressBarConnecting = findViewById(R.id.progressBarConnecting);

        //버튼 리스너
        ((Button)findViewById(R.id.btn_U)).setOnClickListener(this::onClick);
        ((Button)findViewById(R.id.btn_D)).setOnClickListener(this::onClick);
        ((Button)findViewById(R.id.btn_C)).setOnClickListener(this::onClick);
        ((Button)findViewById(R.id.btn_R)).setOnClickListener(this::onClick);
        ((Button)findViewById(R.id.btn_L)).setOnClickListener(this::onClick);
        ((Button)findViewById(R.id.btn_a)).setOnClickListener(this::onClick);
        ((Button)findViewById(R.id.btn_b)).setOnClickListener(this::onClick);
        ((Button)findViewById(R.id.btn_c)).setOnClickListener(this::onClick);
        ((Button)findViewById(R.id.btn_d)).setOnClickListener(this::onClick);

        address = getIntent().getStringExtra("device_address");
        new ConnectBluetoothTask().execute();
    }

    private void onClick(View view) {
        String cmd = null;
        switch (view.getId()){
            case R.id.btn_U: cmd = "U"; break;
            case R.id.btn_D: cmd = "D"; break;
            case R.id.btn_C: cmd = "C"; break;
            case R.id.btn_R: cmd = "R"; break;
            case R.id.btn_L: cmd = "L"; break;
            case R.id.btn_a: cmd = "a"; break;
            case R.id.btn_b: cmd = "b"; break;
            case R.id.btn_c: cmd = "c"; break;
            case R.id.btn_d: cmd = "d"; break;
        }
        try {
            bluetoothSocket.getOutputStream().write(cmd.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {

            try {
                if(bluetoothSocket!=null) bluetoothSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }




    private class ConnectBluetoothTask extends AsyncTask<Void,Void,Void> {
        @Override
        protected void onPreExecute() {
            progressBarConnecting.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... devices) {
            try {
                if(bluetoothSocket == null) {
                    bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                    BluetoothDevice bluetooth = bluetoothAdapter.getRemoteDevice(address);
                    bluetoothSocket = bluetooth.createInsecureRfcommSocketToServiceRecord(myUUID);
                    bluetoothSocket.connect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(bluetoothAdapter!=null) bluetoothAdapter.cancelDiscovery();
                // TODO: 2018-11-23 bluetoothSocket을 닫아야 하나?
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressBarConnecting.setVisibility(View.INVISIBLE);
        }
    }

}
