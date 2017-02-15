package com.training.qr;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.training.R;
import com.training.network.activity.WebActivity;

/**
 * Created by chenqiuyi on 17/2/13.
 */

public class QRMainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_main);
    }

    public void onTakePhoto(View view) {
        new IntentIntegrator(this)
                .setOrientationLocked(false)
                .setCaptureActivity(CustomScanActivity.class) // 设置自定义的activity是CustomActivity
                .initiateScan(); // 初始化扫描
    }

    @Override
    // 通过onActivityResult的方法获取扫描回来的值
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult != null) {
            if (intentResult.getContents() == null) {
                Toast.makeText(this, "内容为空", Toast.LENGTH_LONG).show();
            } else {
                // ScanResult 为 获取到的字符串
                String scanResult = intentResult.getContents();
                if (!(scanResult.startsWith("http://") | scanResult.startsWith("https://"))) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(QRMainActivity.this);
                    builder.setMessage(scanResult);
                    builder.setCancelable(true);
                    builder.create().show();
                    return;
                }

                Intent intent = new Intent(QRMainActivity.this, WebActivity.class);
                intent.putExtra("url", scanResult);
                startActivity(intent);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
