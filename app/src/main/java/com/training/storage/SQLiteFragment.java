package com.training.storage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.training.R;
import com.training.common.utlis.ContextUtils;
import com.training.storage.dao.MyDatabaseOpenHelp;
import com.training.storage.model.SQLiteInfo;
import com.training.storage.utils.SQLUtils;
import com.training.storage.view.SQLiteSelectDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chenqiuyi on 16/10/25.
 */

public class SQLiteFragment extends Fragment {
    private SQLiteSelectDialog sqLiteSelectDialog;
    private List<SQLiteInfo> sqLiteInfos;

    private MyDatabaseOpenHelp openHelp;
    private static final int INSERT = 0, DELETE = 1;
    @BindViews({R.id.et_insert, R.id.et_delete})
    EditText[] editTexts;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        LinearLayout layout = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.frg_database, null);
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.frg_database, null);
        ButterKnife.bind(this, layout);
        getActivity().setTitle(R.string.database);
        openHelp = new MyDatabaseOpenHelp(getActivity());
        sqLiteInfos = new ArrayList<>();
        return layout;
    }

    @OnClick({R.id.btn_search, R.id.btn_insert, R.id.btn_delete})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.btn_search:
                sqLiteInfos.clear();
                SQLUtils.searchData(openHelp, sqLiteInfos);
                showDialog();
                break;
            case R.id.btn_insert:
                String content = editTexts[INSERT].getText().toString();
                if (content.isEmpty()) {
                    ContextUtils.showToast(getActivity(), "输入为空");
                } else {
                    long row = SQLUtils.insertData(openHelp, editTexts[INSERT].getText().toString());
                    ContextUtils.showToast(getActivity(), "\"" + content + "\"" + "被插入第" + row + "行");
                }
                editTexts[INSERT].getText().clear();
                break;
            case R.id.btn_delete:
                int count = SQLUtils.deleteData(openHelp, editTexts[DELETE].getText().toString());
                switch (count) {
                    case -1:
                        ContextUtils.showToast(getActivity(), "数据库不存在");
                        break;
                    case 0:
                        ContextUtils.showToast(getActivity(), "数据不存在");
                        break;
                    default:
                        ContextUtils.showToast(getActivity(),
                                "\"" + editTexts[DELETE].getText().toString() + "\"" + "已被删除\n"
                                        + "您有" + count + "行数据被删除");
                        break;
                }
                editTexts[DELETE].getText().clear();
                break;

        }
    }

    private void showDialog() {
        if (sqLiteSelectDialog != null) {
            sqLiteSelectDialog.dismiss();
            sqLiteSelectDialog.cancel();
            sqLiteSelectDialog = null;
        }
        sqLiteSelectDialog = new SQLiteSelectDialog(getActivity(), sqLiteInfos);
        sqLiteSelectDialog.show();
    }
}
