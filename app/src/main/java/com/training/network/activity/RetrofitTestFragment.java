package com.training.network.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.training.R;
import com.training.network.Constant;
import com.training.network.model.ResponseRetrofit;
import com.training.network.model.RpRetrofitBird;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

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

    public interface ResponseService {
        @GET(Constant.BIRD_CONFIG_URL)
        Call<ResponseRetrofit<List<RpRetrofitBird>>> getBase();
    }

}
