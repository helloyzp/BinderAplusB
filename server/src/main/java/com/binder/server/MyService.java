package com.binder.server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.binder.aidl.IAdd;


public class MyService extends Service {

    private String TAG = "MyService";


    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind()");
        iBinder = new MyBinder();
        Log.i(TAG, "onBind(), iBinder=" + iBinder);
        return iBinder;//return MyBinder, 从而通过ServiceConnection在activity中拿到MyBinder
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);
    }

    public int addFunction(int a, int b) {
        Log.i(TAG, "(), a=" + a + " ,b=" + b );
        int result = a + b;
        return result;
    }

    private IBinder iBinder;

    class MyBinder extends IAdd.Stub {

        @Override
        public int add(int a, int b) throws RemoteException {
            int result =  addFunction(a, b);
            return result;
        } //通过Binder实例将service中的方法暴露出去
    }

}