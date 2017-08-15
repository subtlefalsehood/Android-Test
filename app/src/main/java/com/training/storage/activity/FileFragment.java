package com.training.storage.activity;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.subtlefalsehood.base.utils.ContextUtils;
import com.training.R;
import com.training.storage.model.RecipeInfo;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chenqiuyi on 16/10/25.
 */

public class FileFragment extends Fragment {
    @BindView(R.id.et_file)
    EditText et_file;
    @BindView(R.id.tv_file)
    TextView tv_file;
//    @BindView(R.id.img_test)
//    ImageView img;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.frg_file, null);
        ButterKnife.bind(this, layout);
        return layout;
    }

    @OnClick({R.id.btn_read, R.id.btn_write, R.id.btn_delete})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_read:
                tv_file.setText(read());

//                Map<String, RecipeInfo> recipeInfos = getInfo();
//                tv_file.setText("");
//                for (String key : recipeInfos.keySet()) {
//                    tv_file.append(
//                            "\nkey = " + key +
//                                    "\nname = " + recipeInfos.get(key).getName()
//                                    + "\nimg = " + recipeInfos.get(key).getBitmapPath());
//                    img.setImageResource(
//                            getResources().getIdentifier(recipeInfos.get(key).getBitmapPath(),
//                                    "drawable",getActivity().getApplicationInfo().packageName));
//                }
                break;
            case R.id.btn_write:
                write(et_file.getText().toString());
                et_file.getText().clear();
                break;
            case R.id.btn_delete:
                delete();
                break;
        }
    }

    private void delete() {
        if (isExternalStorageAvailable()) {
            File file = new File(
                    Environment.getExternalStorageDirectory().getAbsolutePath() + "/file", "test1.txt");
            if (file.exists()) {
                file.delete();
            }
        }
    }

    public void write(String text) {
        if (isExternalStorageAvailable()) {
            File fdir = new File(Environment
                    .getExternalStorageDirectory().getAbsolutePath() + "/file");
            if (!fdir.exists()) {
                fdir.mkdir();
            }
            File file = new File(fdir, "test1.txt");
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            FileOutputStream fileOutputStream;
            try {
                fileOutputStream = new FileOutputStream(file, true);
                fileOutputStream.write(text.getBytes());
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String read() {
        String text = "";
        if (isExternalStorageAvailable()) {
            File file = new File(
                    Environment.getExternalStorageDirectory().getAbsolutePath()
                            + "/file" + "/test1.txt");
            try {
                FileInputStream fileInputStream = new FileInputStream(file);

                int size = fileInputStream.available();
                byte[] bytes = new byte[size];
                while (fileInputStream.read(bytes) != -1) {
                    text = new String(bytes, 0, size);
                }

//            int hasRead;
//            while ((hasRead = fileInputStream.read(bytes)) != -1) {
//                text = text + new String(bytes, 0, hasRead);
//            }

                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
                ContextUtils.showToast(getActivity(), "文件不存在");
            }
        }
        return text;
    }

    private boolean isExternalStorageAvailable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());

    }

    private Map<String, RecipeInfo> getInfo() {
        Map<String, RecipeInfo> recipeInfos = new HashMap<>();
        RecipeInfo recipeInfo = new RecipeInfo();
        String key = "";
        try {
            InputStream inputStream = getResources().getAssets().open("myXml");
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(inputStream, "utf-8");
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (parser.getName().equals("recipe")) {
                            recipeInfo = new RecipeInfo();
                            key = parser.getAttributeValue(0);
                        } else if (parser.getName().equals("name")) {
                            recipeInfo.setName(parser.nextText());
                        } else if (parser.getName().equals("img")) {
                            recipeInfo.setBitmapPath(parser.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (!key.isEmpty()) {
                            recipeInfos.put(key, recipeInfo);
                        }
                        break;
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return recipeInfos;
    }
}
