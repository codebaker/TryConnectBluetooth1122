package com.codebaker.joan.tryconnectbluetooth1122;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private BluetoothAdapter bluetoothAdapter = null;
    private Set<BluetoothDevice> pairedDeviceList=null;
    private Button button;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button= findViewById(R.id.button);
        button.setOnClickListener(this::onClick);

        if(bluetoothAdapter == null)
            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        listView = findViewById(R.id.listView);
    }

    private void onClick(View view) {
        pairedDeviceList = bluetoothAdapter.getBondedDevices();
        ArrayList pairedList = new ArrayList();
        for(BluetoothDevice d : pairedDeviceList) {
            pairedList.add(d.getName() + "\n" + bluetoothAdapter.getAddress());
        }
        final ArrayAdapter adapter = new ArrayAdapter(this,R.layout.list_item,pairedList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this::OnItemClick);
    }

    private void OnItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String info = ((TextView)view).getText().toString();
        String address = info.substring(info.length()-17);
        Intent intent = new Intent(view.getContext(),BluetoothClientActivity.class);
        intent.putExtra("device_address",address);
        startActivity(intent);
    }


}
