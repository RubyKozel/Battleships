package com.example.kozel.battleship;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;


public class OrientationsSensorService extends Service implements SensorEventListener {
    private Sensor mSensor;
    private SensorManager mSensorManager;
    private IBinder mBinder;
    private double currentValue = SensorManager.GRAVITY_EARTH;
    private double mAccel;
    private AccelerationListener mListener;
    private boolean changed;
    private long time;

    @Override
    public void onCreate() {
        super.onCreate();
        mBinder = new LocalBinder();
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor s = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        Log.d("SENSOR", s + "");
        if (s != null) {
            mSensor = s;
        } else {
            Toast.makeText(this, "Sorry, no sensors available for you!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        mSensorManager.registerListener(this, mSensor, 1000000);
        return mBinder;
    }

    public Sensor getSensor() {
        return mSensor;
    }

    public SensorManager getSensorManager() {
        return mSensorManager;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (this.mListener != null) {
            if (!changed) {
                float[] mGravity = event.values.clone();
                double lastValue = currentValue;
                currentValue = Math.sqrt(mGravity[0] * mGravity[0] + mGravity[1] * mGravity[1] + mGravity[2] * mGravity[2]);
                mAccel = mAccel * 0.9f + (currentValue - lastValue);
                if (mAccel > 3) {
                    Toast.makeText(this, "You're moving too fast! your ships got hit!", Toast.LENGTH_SHORT).show();
                    this.mListener.onAcceleration();
                    changed = true;
                    time = System.currentTimeMillis();
                }
            } else {
                if (System.currentTimeMillis() - time >= 3000)
                    changed = false;
            }
        }
    }

    public void registerListener(AccelerationListener listener) {
        if (this.mListener == null)
            this.mListener = listener;
    }

    public void unregisterListener() {
        if (this.mListener != null)
            this.mListener = null;
    }

    class LocalBinder extends Binder {
        OrientationsSensorService getService() {
            return OrientationsSensorService.this;
        }
    }

    interface AccelerationListener {
        void onAcceleration();
    }
}