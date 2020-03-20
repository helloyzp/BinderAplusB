package com.binder.client;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.binder.aidl.IAdd;


public class MainActivity extends AppCompatActivity {
    private String TAG = "MainActivity";

    Button btnPay;

    private IBinder binder;

    private IAdd iAdd;

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

            binder = iBinder;
            Log.i(TAG, "onServiceConnected(), iBinder=" + iBinder);
            iAdd = IAdd.Stub.asInterface(iBinder);

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent();
        intent.setAction("com.binder.server.MyService");

        /*android5.0之后，如果service不在同一个App的包中，
         需要设置service所在程序的包名,（包名可以到App的清单文件AndroidManifest中查看）*/
        intent.setPackage("com.binder.server");
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);//开启Service


        btnPay = (Button) findViewById(R.id.btnPay);
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    int result = iAdd.add(1, 2);
                    Toast.makeText(getApplicationContext(), "result=" + result, Toast.LENGTH_SHORT).show();
                } catch (RemoteException e) {
                    //因为是跨程序调用服务，可能会出现远程异常
                    e.printStackTrace();
                }
            }
        });

    }


}
