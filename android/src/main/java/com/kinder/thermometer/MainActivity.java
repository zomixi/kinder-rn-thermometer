package com.kinder.thermometer;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.app.ThermometryManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.hc.so.HcPowerCtrl;
import com.hc.so.SerialPortManager;
import com.hc.so.listener.OnSerialPortDataListener;

import java.io.File;
import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {
    SerialPortManager serialPortManager;
    HcPowerCtrl ctrl;
    TextView tv_t;
    private TextToSpeech tts = null;
    EditText et_target;
    String target;
    int count = 0;
    double[] doubles = new double[5];
    ThermometryManager mThermometryManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ctrl = new HcPowerCtrl();
        tv_t = findViewById(R.id.tv_t);

        ctrl.identityPower(1);

        tts = new TextToSpeech(this, this);
//        new InitTask().execute();
    }
//    FFFFFFFF 68EC C102 001D 316D C0 08 000A 0000 01 14 00005D10 0000000000000003800101522D37DA
    //标签ID
//    private void open24G() {
//        if (serialPortManager == null) {
//            serialPortManager = new SerialPortManager();
//        }
//        boolean b = serialPortManager.openSerialPort(new File("/dev/ttysWK1"), 38400);
//
//        Log.e("TAG", "2.4G打开: " + b);
//        serialPortManager.setOnSerialPortDataListener(new OnSerialPortDataListener() {
//            @SuppressLint("SetTextI18n")
//            @Override
//            public void onDataReceived(byte[] bytes) {
//                if (bytes != null) {
//                    String s = bytesToHex(bytes);
//                    Log.e("TAG", "接收: " + s);
//                    if (s.equals("53657474696E67204F4B")) {
//                        speak("标定成功");
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                tv_t.setText("标定成功");
//                            }
//                        });
//                    }
//                    if (s.startsWith("FFFFFE23")&&s.endsWith("FEFFFF")) {
//                        String bodyT = s.substring(8, 12);
//                        String bodyOutT = s.substring(12, 16);
//                        String envT = s.substring(16, 20);
//                        Log.e("TAG", "16进制体内温度 : " +  bodyT  );
//                        Log.e("TAG", "16进制体表温度 : " +  bodyOutT );
//                        Log.e("TAG", "16进制环境温度 : " +  envT );
//                        Log.e("TAG", "体内温度换算: " + div(Integer.parseInt(bodyT,16),10,2) );
//                        Log.e("TAG", "体表温度换算: " + div(Integer.parseInt(bodyOutT,16),10,2) );
//                        Log.e("TAG", "环境温度换算: " + div(Integer.parseInt(envT,16),10,2) );
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                double div = div(Integer.parseInt(bodyT, 16), 10, 2);
//                                speak(div+"");
//                                tv_t.setText( div+"°");
//                            }
//                        });
//                    }
//
//
//                } else {
//                    Log.e("TAG", "空数据: ");
//                }
//            }
//
//            @Override
//            public void onDataSent(byte[] bytes) {
//
//            }
//        });
//    }

    private void openTemperatureYY() {
        if (serialPortManager == null) {
            serialPortManager = new SerialPortManager();
        }
        boolean b = serialPortManager.openSerialPort(new File("/dev/ttysWK0"), 9600);

        Log.e("TAG", "测温打开: " + b);
        serialPortManager.setOnSerialPortDataListener(new OnSerialPortDataListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataReceived(byte[] bytes) {
                if (bytes != null) {
                    String s = bytesToHex(bytes);
                    Log.e("TAG", "接收: " + s);
                    if (s.equals("53657474696E67204F4B")) {
                        speak("标定成功");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tv_t.setText("标定成功");
                            }
                        });
                    }
                    if (s.startsWith("FFFFFE23")&&s.endsWith("FEFFFF")) {
                        String bodyT = s.substring(8, 12);
                        String bodyOutT = s.substring(12, 16);
                        String envT = s.substring(16, 20);
                        Log.e("TAG", "16进制体内温度 : " +  bodyT  );
                        Log.e("TAG", "16进制体表温度 : " +  bodyOutT );
                        Log.e("TAG", "16进制环境温度 : " +  envT );
                        Log.e("TAG", "体内温度换算: " + div(Integer.parseInt(bodyT,16),10,2) );
                        Log.e("TAG", "体表温度换算: " + div(Integer.parseInt(bodyOutT,16),10,2) );
                        Log.e("TAG", "环境温度换算: " + div(Integer.parseInt(envT,16),10,2) );
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                double div = div(Integer.parseInt(bodyT, 16), 10, 2);
                                speak(div+"");
                                tv_t.setText( div+"°");
                            }
                        });
                    }
                } else {
                    Log.e("TAG", "空数据: ");
                }
            }

            @Override
            public void onDataSent(byte[] bytes) {

            }
        });
    }
    private void openTemperature() {
        if (serialPortManager == null) {
            serialPortManager = new SerialPortManager();
        }
        boolean b = serialPortManager.openSerialPort(new File("/dev/ttysWK0"), 9600);

        Log.e("TAG", "测温打开: " + b);
        serialPortManager.setOnSerialPortDataListener(new OnSerialPortDataListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataReceived(byte[] bytes) {
                if (bytes != null) {
                    String s = bytesToHex(bytes);
                    Log.e("TAG", "接收: " + s);
                    if (s.equals("53657474696E67204F4B")) {
                        speak("标定成功");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tv_t.setText("标定成功");
                            }
                        });
                    }
                    if (s.startsWith("FFFFFE23")&&s.endsWith("FEFFFF")) {
                        String bodyT = s.substring(8, 12);
                        String bodyOutT = s.substring(12, 16);
                        String envT = s.substring(16, 20);
                        Log.e("TAG", "16进制体内温度 : " +  bodyT  );
                        Log.e("TAG", "16进制体表温度 : " +  bodyOutT );
                        Log.e("TAG", "16进制环境温度 : " +  envT );
                        Log.e("TAG", "体内温度换算: " + div(Integer.parseInt(bodyT,16),10,2) );
                        Log.e("TAG", "体表温度换算: " + div(Integer.parseInt(bodyOutT,16),10,2) );
                        Log.e("TAG", "环境温度换算: " + div(Integer.parseInt(envT,16),10,2) );
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                double div = div(Integer.parseInt(bodyT, 16), 10, 2);
                                speak(div+"°");
                                tv_t.setText( div+"°");
                            }
                        });
                    }


                } else {
                    Log.e("TAG", "空数据: ");
                }
            }

            @Override
            public void onDataSent(byte[] bytes) {

            }
        });
    }

    public static double div(double v1, double v2, int scale) {
        BigDecimal bi = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return bi.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == 131 && event.getRepeatCount() == 0) {
            getTemperature();
        }
        if (keyCode == 301 && event.getRepeatCount() == 0) {
            getTemperature();
        }
        if (keyCode == 290 && event.getRepeatCount() == 0) {
            getTemperature();
        }
        Log.e("TAG", "onKeyDown: " + keyCode );
        if (keyCode == 288 && event.getRepeatCount() == 0) {
//            scan();
        }

        return super.onKeyDown(keyCode, event);
    }
    public void calibration37() {
        byte[] data = new byte[9];
        data[0] = (byte) 0xFF;
        data[1] = (byte) 0xFF;
        data[2] = (byte) 0xFE;
        data[3] = 0x21;
        data[4] = 0x01;
        data[5] = 0x72;
        data[6] = (byte) 0xFE;
        data[7] = (byte) 0xFF;
        data[8] = (byte) 0xFF;
        serialPortManager.sendBytes(data);
        Log.e("TAG", "发送: " + bytesToHex(data));
        tv_t.setText("标定37度...");
        speak("标定37度");
    }
    public void calibration42() {
        byte[] data = new byte[9];
        data[0] = (byte) 0xFF;
        data[1] = (byte) 0xFF;
        data[2] = (byte) 0xFE;
        data[3] = 0x20;
        data[4] = 0x01;
        data[5] = (byte) 0xA4;
        data[6] = (byte) 0xFE;
        data[7] = (byte) 0xFF;
        data[8] = (byte) 0xFF;
        serialPortManager.sendBytes(data);
        Log.e("TAG", "发送: " + bytesToHex(data));
        tv_t.setText("标定42度...");
        speak("标定42度");
    }
    private void openUpFor() {
        tv_t.setText("开启补偿...");
        byte[] data = new byte[9];
        data[0] = (byte) 0xFF;
        data[1] = (byte) 0xFF;
        data[2] = (byte) 0xFE;
        data[3] = 0x22;
        data[4] = 0x00;
        data[5] = 0x01;
        data[6] = (byte) 0xFE;
        data[7] = (byte) 0xFF;
        data[8] = (byte) 0xFF;
        serialPortManager.sendBytes(data);
    }
    public void getTemperature() {
        byte[] data = new byte[9];
        data[0] = (byte) 0xFF;
        data[1] = (byte) 0xFF;
        data[2] = (byte) 0xFE;
        data[3] = 0x23;
        data[4] = 0x00;
        data[5] = (byte) 0x00;
        data[6] = (byte) 0xFE;
        data[7] = (byte) 0xFF;
        data[8] = (byte) 0xFF;
        serialPortManager.sendBytes(data);
        Log.e("TAG", "发送: " + bytesToHex(data));
    }
    public String convertStringToHex(String str) {
        char[] chars = str.toCharArray();
        StringBuffer hex = new StringBuffer();
        for (int i = 0; i < chars.length; i++) {
            hex.append(Integer.toHexString((int) chars[i]));
        }
        return hex.toString();
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


    boolean stop = true;

    @Override
    protected void onPause() {
        super.onPause();
//        ctrl = new HcPowerCtrl();
//        ctrl.identityCtrl(0);
        stop = false;
        Log.e("TAG", "onPause: "  );
        serialPortManager.closeSerialPort();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (mThermometryManager==null) {
//            Log.e("TAG", "onResume: "  );
//            mThermometryManager = new ThermometryManager(this);
//            mThermometryManager.PowerOff();
//        }
        ctrl.identityCtrl(1);
//        open24G();
        openTemperatureYY();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        mThermometryManager.PowerOff();
//        mThermometryManager = null;
    }

    @Override
    public void onInit(int status) {
        speak("");
    }

    private void speak(String str) {
        if (tts == null)
            return;
        tts.setPitch(1.0f);//正常音调
        tts.setSpeechRate(1.5f);//正常语速
        tts.speak(str, TextToSpeech.QUEUE_ADD, null, null);//转换语音输出
    }

    public void target(View view) {
    }

    public void calibration37(View view) {
        calibration37();
    }

    public void calibration42(View view) {
        calibration42();
    }

    public void openUpFor(View view) {
        openUpFor();
    }



    public void getT(View view) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (stop) {
//                    getTemperature();
//                    SystemClock.sleep(1000);
//                }
//            }
//        }).start();
        getTemperature();

    }

    public void scan() {
        ctrl.scanTrig(1);
        ctrl.scanPower(1);
        ctrl.scanWakeup(1);
        ctrl.scanPwrdwn(1);
        ctrl.scanTrig(0);
    }

    public class InitTask extends AsyncTask<Boolean, Boolean, Boolean> {
        ProgressDialog mypDialog;

        @Override
        protected Boolean doInBackground(Boolean... booleans) {
            ctrl.scanTrig(1);
            ctrl.scanPower(1);
            ctrl.scanWakeup(1);
            ctrl.scanPwrdwn(1);
            ctrl.scanTrig(0);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ctrl.scanTrig(1);
            ctrl.scanPower(1);
            ctrl.scanWakeup(1);
            ctrl.scanPwrdwn(1);


            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean) {
                mypDialog.cancel();
            }
            super.onPostExecute(aBoolean);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mypDialog = new ProgressDialog(MainActivity.this);
            mypDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mypDialog.setMessage("init...");
            mypDialog.setCanceledOnTouchOutside(false);
            mypDialog.show();
        }
    }
}