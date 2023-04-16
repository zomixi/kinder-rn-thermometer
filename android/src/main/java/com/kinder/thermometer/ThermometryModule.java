package com.kinder.thermometer;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

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
    public boolean start() {
        if (mThermometerRecord == null) {
            mThermometerRecord = new ThermometerRecord(mReactContext);
        }
        return (mThermometerRecord.start());
    }

    @ReactMethod
    public double readTemperature() {
        return mThermometerRecord.getBodyTemperature();
    }

    @ReactMethod
    public boolean stop() {
        return (mThermometerRecord.stop());
    }
}