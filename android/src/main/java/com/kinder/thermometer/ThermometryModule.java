package com.kinder.thermometer;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

public class ThermometryModule extends ReactContextBaseJavaModule {

    private volatile boolean isRun = false;

    private ReactApplicationContext	mReactContext;

    private ThermometerRecord mThermometerRecord = null;

    @Override
    public String getName() {
        return "ThermometryModule";
    }

    public ThermometryModule(ReactApplicationContext reactContext) {
        super(reactContext);
        mReactContext = reactContext;
    }

    @ReactMethod
    public boolean start(Callback callback) {
        if (mThermometerRecord == null) {
            mThermometerRecord = new ThermometerRecord(mReactContext);
        }
        callback(null, mThermometerRecord.start());
    }

    @ReactMethod
    public double readTemperature(Callback callback) {
        callback(null, mThermometerRecord.getBodyTemperature());
    }

    @ReactMethod
    public boolean stop(Callback callback) {
        callback(null, mThermometerRecord.stop());
    }
}