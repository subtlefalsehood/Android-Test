package com.training.network.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.training.R;
import com.training.common.utlis.StringUtil;
import com.training.network.Constant;
import com.training.network.model.BirdResponse;
import com.training.network.model.ResponseObject;
import com.training.network.model.RqItem;
import com.training.network.model.RqLogin;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chenqiuyi on 16/12/7.
 */

public class OriginalHttpTestFragment extends Fragment {
    private View layout;
    private static final int GET = 0, POST = 1;
    @BindView(R.id.et_username)
    EditText et_username;

    @BindView(R.id.et_password)
    EditText et_password;

    @BindView(R.id.tv_display)
    TextView tv_display;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layout = LayoutInflater.from(getActivity()).inflate(R.layout.fra_login, null);
        ButterKnife.bind(this, layout);
        tv_display.setMovementMethod(ScrollingMovementMethod.getInstance());
        return layout;
    }

    @OnClick(R.id.btn_get)
    void clickGet() {
        RqItem rqItem = new RqItem();
//        MyAsyncTask myAsyncTask = new MyAsyncTask(GET, Constant.HTTP_URL + rqItem.getCmd() + "?" + "mac=" + rqItem.getMac());
        MyAsyncTask myAsyncTask = new MyAsyncTask(GET, Constant.HTTP_URL + Constant.BIRD_CONFIG_URL);
        myAsyncTask.execute();
    }

    //    @OnClick(R.id.btn_login)
    void clickLogin() {
        String username = et_username.getText().toString();
        String password = et_password.getText().toString();
        if (StringUtil.isBlank(username)
                || StringUtil.isBlank(password)) {
            showSnack("用户名密码为空");
        } else {
            if (username.trim().length() != 11) {
                showSnack("您输入的密码有误");
            } else {
//                ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
//                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
//                if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
//                   login();
//                } else {
//                    showSnack("您使用的是流量");
//                }
                login(username, password);

            }
        }
    }

    private void showSnack(String content) {
        Snackbar.make(layout, content, Snackbar.LENGTH_LONG).show();
    }


    RqLogin rqLogin;

    private void login(String username, String password) {
        rqLogin.setPhoneNum(username);
        rqLogin.setPassword(password);
        MyAsyncTask myAsyncTask = new MyAsyncTask(POST, Constant.HTTP_URL + Constant.LOGIN_URL);
        myAsyncTask.execute();
    }


    private class MyAsyncTask extends AsyncTask<Void, String, String> {

        private int requestType = GET;
        private String urlString;

        public MyAsyncTask(int requestType, String urlString) {
            this.requestType = requestType;
            this.urlString = urlString;
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                if (requestType == GET) {
                    return doGetRequest(urlString);
                } else {
                    return doPostRequest(urlString);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return "error";
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Logger.d("onPostExecute");
            if (s == null) {
                Logger.e("error");
            } else {
                Logger.d(s);
            }
            ResponseObject responseObject = parseJsonToEntity(s);
            if (responseObject != null) {
                List<BirdResponse> birdResponses = parseEndataJson2Entity(responseObject.getEndata());
                StringBuffer stringBuffer = new StringBuffer("");
                if (birdResponses != null) {
                    for (BirdResponse birdResponse : birdResponses) {
                        stringBuffer.append(birdResponse.getResourceKey() + ":" + birdResponse.getContent() + "\n");
                    }
                    tv_display.setText(stringBuffer.toString());
                }
            }
        }

    }

    private void handlerBuffer(StringBuffer buffer, String key, String value) {
        buffer.append(key);
        buffer.append("=");
        buffer.append(value);
        buffer.append("&");
    }

    private String doPostRequest(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream is = connection.getInputStream();
                Reader reader = new InputStreamReader(is, "UTF-8");
                char[] buffer = new char[is.available()];
                reader.read(buffer);
                Logger.d(connection.getResponseMessage());
                return String.valueOf(buffer);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //http://eyemiserver.criwell.com/v2.0/project/probably_info_all/get?mac=867068021147573&at=fb01cd06aa7f4b2e8bc5b5bbc8daab30
    //http://eyemiserver.criwell.com/v2.0/project/probably_info_all/get?mac=867068021147573&at=fa8c71be018c4f78a99d7edb87760cf9
    private String doGetRequest(String urlString) {
        try {
            URL url = new URL(urlString);
            Logger.i(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.setUseCaches(false);
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                StringBuffer bufferString = new StringBuffer("");
                InputStream is = connection.getInputStream();
                Reader reader = new InputStreamReader(is, "UTF-8");
                //                int size = 100;
//                int length = is.available();
//                for (int i = 0; i < (length % size > 0 ? length / 100 + 1 : length / 100); i++) {
//                    char[] buffer = new char[size];
//                    reader.read(buffer);
//                    bufferString.append(buffer);
//                }
//                Logger.d(connection.getResponseMessage());
                BufferedReader bufferedReader = new BufferedReader(reader);
                String s;
                while ((s = bufferedReader.readLine()) != null) {
                    bufferString.append(s);
                }

                return bufferString.toString();
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private ResponseObject parseJsonToEntity(String jsonString) {
        try {
            ResponseObject responseObject = new ResponseObject();
            JSONObject jsonObject = new JSONObject(jsonString);
            responseObject.setStatus(jsonObject.getInt("status"));
//            responseObject.setPageTotal(jsonObject.getInt("pageTotal"));
            responseObject.setEndata(jsonObject.getString("endata"));
            responseObject.setInfo(jsonObject.getString("info"));
            return responseObject;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<BirdResponse> parseEndataJson2Entity(String enDataString) {
        List<BirdResponse> birdResponses = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(enDataString);
            for (int j = 0; j < jsonArray.length(); j++) {
                BirdResponse birdResponse = new BirdResponse();
                JSONObject jsonObject = jsonArray.getJSONObject(j);
                Field[] fields = birdResponse.getClass().getDeclaredFields();
                for (Field field : fields) {
                    field.setAccessible(true);
                    if (field.getType().getName().equalsIgnoreCase("java.lang.String")) {
                        field.set(birdResponse, jsonObject.getString(field.getName()));
                    }else if (field.getType().getName().equalsIgnoreCase("int")){
                        field.set(birdResponse, jsonObject.getInt(field.getName()));
                    }
                }
                birdResponses.add(birdResponse);
            }
            return birdResponses;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}