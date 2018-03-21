package com.training.others.screenshot;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.Image;
import android.media.ImageReader;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.os.AsyncTaskCompat;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import com.subtlefalsehood.base.utils.JumpUtils;
import com.training.R;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;

public class ScreenCaptureActivity extends Activity {
    public static final int REQUEST_MEDIA_PROJECTION = 18;
    private DisplayMetrics metrics;
    private MediaProjectionManager mediaProjectionManager;
    private MediaProjection mediaProjection;
    private ImageReader imageReader;
    private VirtualDisplay virtualDisplay;

    private FloatViewService floatViewService;

    private int delay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screenshot);
        initData();
        requestCapturePermission();
    }

    private void initData() {
        delay = 50;
        metrics = new DisplayMetrics();
        ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay().getMetrics(metrics);
        imageReader = ImageReader.newInstance(metrics.widthPixels, metrics.heightPixels, PixelFormat.RGBA_8888, 1);
    }

    public void requestCapturePermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            //5.0 之后才允许使用屏幕截图
            return;
        }
        //拉起获取屏幕内容的系统权限，必须手动拉起
        mediaProjectionManager = (MediaProjectionManager)
                getSystemService(Context.MEDIA_PROJECTION_SERVICE);
        startActivityForResult(
                mediaProjectionManager.createScreenCaptureIntent(),
                REQUEST_MEDIA_PROJECTION);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_MEDIA_PROJECTION:
                if (resultCode == RESULT_OK && data != null) {
                    floatViewService = FloatViewService.getFloatViewService();
                    floatViewService.setFloatViewVisibility(View.GONE);
                    mediaProjection = mediaProjectionManager.getMediaProjection(RESULT_OK, data);
                    startCapture();
                } else {
                    showError("授权问题");
                }
                break;
        }
    }

    private void startCapture() {
        if (mediaProjection != null) {
            Handler handler1 = new Handler();
            handler1.postDelayed(new Runnable() {
                public void run() {
                    //start virtual
                    createVirtualEnvironment();
                }
            }, 5);

            handler1.postDelayed(new Runnable() {
                public void run() {
                    //capture the screen
                    screenCapture();
                }
            }, 180);
        } else {
            showError("mediaProjection为空");
        }
    }

    private void createVirtualEnvironment() {
        virtualDisplay = mediaProjection.
                createVirtualDisplay("screen-mirror",
                        metrics.widthPixels, metrics.heightPixels, metrics.densityDpi,
                        DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR, imageReader.getSurface(), null, null);
//        if (virtualDisplay == null) {
//            showError();
//        } else {
//            screenCapture();
//        }
    }

    private void screenCapture() {
        Image image = imageReader.acquireLatestImage();
        if (image != null) {
            SaveImageAsyncTask saveImageAsyncTask = new SaveImageAsyncTask();
//            saveImageAsyncTask.execute(image);
            AsyncTaskCompat.executeParallel(saveImageAsyncTask, image);
        } else {
            showError("image为空");
            startCapture();
        }
    }

    private void showError() {
        JumpUtils.showToast(getApplicationContext(), "扫描失败");
        finish();
    }

    private void showError(String message) {
        JumpUtils.showToast(getApplicationContext(), "扫描失败:" + message);
        finish();
    }

    private void showSuccess() {
        JumpUtils.showToast(getApplicationContext(), "扫描成功");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(contentUri,"image/*");
//        intent.setData(contentUri);
        startActivity(intent);
        finish();
    }

    private Uri contentUri;
    class SaveImageAsyncTask extends AsyncTask<Image, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(Image... params) {
            if (params == null || params.length < 1 || params[0] == null) {
                return null;
            }
            Image image = params[0];
            int width = image.getWidth();
            int height = image.getHeight();
            final Image.Plane[] planes = image.getPlanes();
            final ByteBuffer buffer = planes[0].getBuffer();

            //每个像素的间距
            int pixelStride = planes[0].getPixelStride();
            //总的间距
            int rowStride = planes[0].getRowStride();
            int rowPadding = rowStride - pixelStride * width;
            Bitmap bitmap = Bitmap.createBitmap(width + rowPadding / pixelStride, height, Bitmap.Config.ARGB_8888);
            bitmap.copyPixelsFromBuffer(buffer);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height);
            image.close();
            File fileImage = null;
            if (bitmap != null) {
                try {
                    fileImage = new File(FileUtil.getScreenShotsName(getApplicationContext()));
                    if (!fileImage.exists()) {
                        fileImage.createNewFile();
                    }
                    FileOutputStream out = new FileOutputStream(fileImage);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                    out.flush();
                    out.close();
                    Intent media = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    contentUri = Uri.fromFile(fileImage);
                    media.setData(contentUri);
                    sendBroadcast(media);
                } catch (Exception e) {
                    JumpUtils.showToast(getApplicationContext(), "文件错误");
                    e.printStackTrace();
                    fileImage = null;
                }
            }
            return fileImage == null ? null : bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap == null) {
                showError("文件问题");
            } else {
                showSuccess();
            }
            super.onPostExecute(bitmap);
        }
    }

    private void releaseData() {
        if (virtualDisplay != null) {
            virtualDisplay.release();
            virtualDisplay = null;
        }
        if (imageReader != null) {
            imageReader.close();
            imageReader = null;
        }
        if (mediaProjection != null) {
            mediaProjection.stop();
            mediaProjection = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseData();
        if (floatViewService != null) {
            floatViewService.setFloatViewVisibility(View.VISIBLE);
        }
    }
}
