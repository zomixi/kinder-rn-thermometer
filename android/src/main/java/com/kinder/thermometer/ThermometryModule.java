package com.kinder.thermometer;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

public class ThermometryModule extends ReactContextBaseJavaModule {

    private volatile boolean isRun = false;

    private ReactApplicationContext	mReactContext;

    private static ThermometerRecord mThermometerRecord = null;

    @Override
    public String getName() {
        return "ThermometryModule";
    }

    public ThermometryModule(ReactApplicationContext reactContext) {
        super(reactContext);
        mReactContext = reactContext;
    }

    @ReactMethod
    public void start(Callback callback) {
        if (mThermometerRecord == null) {
            mThermometerRecord = new ThermometerRecord(mReactContext);
        }
        callback.invoke(null, mThermometerRecord.start());
    }

    @ReactMethod
    public void readTemperature(Callback callback) {
        callback.invoke(null, mThermometerRecord.getBodyTemperature());
    }

    @ReactMethod
    public void stop(Callback callback) {
        callback.invoke(null, mThermometerRecord.stop());
    }
}