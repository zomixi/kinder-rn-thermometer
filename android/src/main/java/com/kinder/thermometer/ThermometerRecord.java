package com.kinder.thermometer;

import android.annotation.SuppressLint;
import android.util.Log;
import com.hc.so.SerialPortManager;
import com.hc.so.listener.OnSerialPortDataListener;

import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactApplicationContext;

import java.io.File;
import java.math.BigDecimal;

/**
 * 测温记录仪
 */
public class ThermometerRecord implements OnSerialPortDataListener {

    // 体内温度
    public static double bodyDegree = 0;
    // 体表温度
    public static double bodyOutDegree = 0;
    // 环境温度
    public static double envDegree = 0;

    private SerialPortManager serialPortManager;

    private ReactContext mReactContext;

    public ThermometerRecord(ReactApplicationContext reactContext) {
        if (serialPortManager == null) {
            serialPortManager = new SerialPortManager();
        }
        mReactContext = reactContext;
    }

    public boolean start() {
        Log.e("TAG", "===================start");
        boolean successFlag = serialPortManager.openSerialPort(new File("/dev/ttysWK0"), 9600);
        serialPortManager.setOnSerialPortDataListener(this);
        return successFlag;
    }

    public boolean stop() {
        Log.e("TAG", "===================stop");
        serialPortManager.closeSerialPort();
        return true;
    }

    public double getBodyTemperature() {
        return bodyDegree;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onDataReceived(byte[] bytes) {
        Log.e("TAG", "===================onDataReceived");
        Log.e("TAG", bytes != null ? bytes.toString() : "bytes为空");
        if (bytes != null) {
            String s = bytesToHex(bytes);
            Log.e("TAG", "接收: " + s);
            if (s.equals("53657474696E67204F4B")) {
//                speak("标定成功");
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        tv_t.setText("标定成功");
//                    }
//                });
            }
            Log.e("TAG", "bytesToHex(bytes)" + s);
            if (s.startsWith("FFFFFE23") && s.endsWith("FEFFFF")) {
                String bodyT = s.substring(8, 12);
                String bodyOutT = s.substring(12, 16);
                String envT = s.substring(16, 20);
                Log.e("TAG", "16进制体内温度 : " + bodyT);
                Log.e("TAG", "16进制体表温度 : " + bodyOutT);
                Log.e("TAG", "16进制环境温度 : " + envT);
                bodyDegree = div(Integer.parseInt(bodyT, 16), 10, 2);
                bodyOutDegree = div(Integer.parseInt(bodyOutT, 16), 10, 2);
                envDegree = div(Integer.parseInt(envT, 16), 10, 2);
                Log.e("TAG", "体内温度换算: " + bodyDegree);
                Log.e("TAG", "体表温度换算: " + bodyOutDegree);
                Log.e("TAG", "环境温度换算: " + envDegree);
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        double div = div(Integer.parseInt(bodyT, 16), 10, 2);
//                        speak(div + "");
//                        tv_t.setText(div + "°");
//                    }
//                });
            }
        } else {
            Log.e("TAG", "空数据: ");
        }
    }

    @Override
    public void onDataSent(byte[] bytes) {
    }

    public static double div(double v1, double v2, int scale) {
        BigDecimal bi = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return bi.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static String bytesToHex(byte[] b) {
        if (b == null || b.length == 0)
            return null;
        StringBuilder sb = new StringBuilder();
        try {
            for (int i = 0; i < b.length; i++) {
                String hex = Integer.toHexString(b[i] & 0xFF);
                if (hex.length() == 1) {
                    hex = "0" + hex;
                }
                sb.append(hex.toUpperCase());
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return sb.toString();
    }
}
