package com.training.network.activity;

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

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;
import com.training.R;
import com.training.common.utlis.StringUtil;
import com.training.network.Constant;
import com.training.network.model.ResponseObject;
import com.training.network.model.ResponseRetrofit;
import com.training.network.model.RpRetrofitBird;
import com.training.network.model.RqLogin;
import com.training.network.security.AES;
import com.training.network.security.Base64;
import com.training.network.security.Rsa;
import com.training.network.utils.GsonUtil;
import com.training.network.utils.RandomUtils;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by chenqiuyi on 16/12/7.
 */

public class RetrofitTestFragment extends Fragment {
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

    @OnClick(R.id.btn_login)
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

    public interface ResponseService {
        @GET(Constant.BIRD_CONFIG_URL)
        Call<ResponseRetrofit<List<RpRetrofitBird>>> getBase();

        @POST(Constant.LOGIN_URL)
        Call<ResponseObject> postLogin(@Body RqLogin rqLogin);
    }

    private void login(String username, String password) {
//        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//        builder.addInterceptor(new CreateInterceptor());

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(new PostConverterFactory(new Gson()).create())
//                .addConverterFactory(GsonConverterFactory.create())
//                .client(builder.build())
                .baseUrl(Constant.HTTP_URL)
                .build();
        ResponseService loginService = retrofit.create(ResponseService.class);
        RqLogin rqLogin = new RqLogin();
        rqLogin.setPhoneNum(username);
        rqLogin.setPassword(password);

        Call<ResponseObject> call = loginService.postLogin(rqLogin);
        call.enqueue(new Callback<ResponseObject>() {
            @Override
            public void onResponse(Call<ResponseObject> call, Response<ResponseObject> response) {
                response.code();
                tv_display.setText(response.body().getEndata());
            }

            @Override
            public void onFailure(Call<ResponseObject> call, Throwable t) {
                showSnack(t.getMessage());
            }
        });
    }


    @OnClick(R.id.btn_get)
    void clickGet() {
        doGet();
    }

    private void doGet() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constant.HTTP_URL)
                .build();
        ResponseService responseService = retrofit.create(ResponseService.class);
        Call<ResponseRetrofit<List<RpRetrofitBird>>> call = responseService.getBase();
        call.enqueue(new Callback<ResponseRetrofit<List<RpRetrofitBird>>>() {
            @Override
            public void onResponse(Call<ResponseRetrofit<List<RpRetrofitBird>>> call, Response<ResponseRetrofit<List<RpRetrofitBird>>> response) {
                if (response != null) {
                    StringBuffer buffer = new StringBuffer("");
                    for (RpRetrofitBird rpRetrofitBird : response.body().getEndata()) {
                        buffer.append(rpRetrofitBird.getResourceKey() + ":" + rpRetrofitBird.getContent() + "\n");
                    }
                    tv_display.setText(buffer.toString());
                }
            }

            @Override
            public void onFailure(Call<ResponseRetrofit<List<RpRetrofitBird>>> call, Throwable t) {

            }
        });
    }

    String rsaKey;

    private String getEncodeJsonRequest(Object object) {
        try {
            rsaKey = RandomUtils.getRandomString(16);
            Rsa rsa = new Rsa();

            byte[] encRsaBytes = rsa.encryptByPublicKey(rsaKey.getBytes());
            String encodeRsaKey = Base64.encryptBase64(encRsaBytes);

            AES aes = new AES(rsaKey);
            String requestJson = GsonUtil.toJson(object);
            Logger.d(requestJson);
            String encodeRequestData = aes.encrypt(requestJson);

            // 将RSA的key值和加密后的请求参数Json串组合成一个JSON串
            String postJson = "{\"enkey\":\"" + encodeRsaKey
                    + "\",\"endata\":\"" + encodeRequestData + "\"}";
            postJson = URLEncoder.encode(postJson, "utf-8");
            requestJson = "criJson=" + postJson;
            Logger.d(requestJson);
            return requestJson;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


    public class PostEncodeRequestBodyConverter<T> implements Converter<T, RequestBody> {
        private final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");
        private final Charset UTF_8 = Charset.forName("UTF-8");

        private final TypeAdapter<T> adapter;

        PostEncodeRequestBodyConverter(TypeAdapter<T> adapter) {
            this.adapter = adapter;
        }


        @Override
        public RequestBody convert(T value) throws IOException {
            return RequestBody.create(MEDIA_TYPE, getEncodeJsonRequest(value));
        }
    }

    public class PostDecodeResponseBodyConverter<T> implements Converter<ResponseBody, T> {
        private final TypeAdapter<T> adapter;

        PostDecodeResponseBodyConverter(TypeAdapter<T> adapter) {
            this.adapter = adapter;
        }

        @Override
        public T convert(ResponseBody value) throws IOException {

            //解密字符串
            AES aes = new AES(rsaKey);
            return adapter.fromJson(aes.decrypt(value.string()));
//            return adapter.fromJson(value.string());
        }
    }

    private final class PostConverterFactory<T> extends Converter.Factory {

        public PostConverterFactory create() {
            return create(new Gson());
        }

        public PostConverterFactory create(Gson gson) {
            return new PostConverterFactory(gson);
        }

        private final Gson gson;

        private PostConverterFactory(Gson gson) {
            if (gson == null) throw new NullPointerException("gson == null");
            this.gson = gson;
        }

        @Override
        public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
            TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
            return new PostDecodeResponseBodyConverter<>(adapter);
        }


        @Override
        public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
            TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
            return new PostEncodeRequestBodyConverter<>(adapter);
        }
    }


    public class CreateInterceptor implements Interceptor {

        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            okhttp3.Response response = chain.proceed(chain.request());  //如果401了，会先执行TokenAuthenticator
            Logger.e("CreateInterceptor request url " + response.request().url());
            Logger.e("CreateInterceptor  response code " + response.code());
            return response;
        }
    }
}