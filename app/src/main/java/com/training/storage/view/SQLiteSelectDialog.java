package com.training.storage.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.training.R;
import com.training.storage.model.SQLiteInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenqiuyi on 16/11/21.
 */

public class SQLiteSelectDialog extends Dialog {
    private ListView listView;
    private List<SQLiteInfo> sqLiteInfos;
    private Context context;

    public SQLiteSelectDialog(Context context, List<SQLiteInfo> sqLiteInfos) {
        super(context);
        this.sqLiteInfos = new ArrayList<>();
        this.sqLiteInfos = sqLiteInfos;
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_list);

//        WindowManager windowManager = ((Activity) context).getWindowManager();
//        Display display = windowManager.getDefaultDisplay();
//        Window dialogWindow = getWindow();
//        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//        lp.width = display.getWidth();
//        dialogWindow.setAttributes(lp);
//        dialogWindow.setGravity(Gravity.CENTER);

        listView = (ListView) findViewById(R.id.dialog_list);
        listView.setAdapter(new SQLiteListAdapt());
    }

    class SQLiteListAdapt extends BaseAdapter {

        @Override
        public int getCount() {
            return sqLiteInfos.size();
        }

        @Override
        public Object getItem(int position) {
            return sqLiteInfos.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            String id = sqLiteInfos.get(position).getId();
            String entryid = sqLiteInfos.get(position).getEntryid();
            String title = sqLiteInfos.get(position).getTitle();
            String content = sqLiteInfos.get(position).getCotent();
            convertView = addLineaLayout(new String[]{id, entryid, title, content}, position);
            return convertView;
        }

    }

    private LinearLayout addLineaLayout(String[] strings, int position) {
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        layout.setPadding(100, 0, 100, 0);
        layout.setGravity(Gravity.CENTER);
        TextView row = new TextView(context);
        row.setText("第" + (position + 1) + "行:");
        row.setLayoutParams(
                new LinearLayout.LayoutParams(0,
                        LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        layout.addView(row);
        for (String s : strings) {
            TextView textView = new TextView(context);
            textView.setText(s);
            textView.setLayoutParams(
                    new LinearLayout.LayoutParams(0,
                            LinearLayout.LayoutParams.WRAP_CONTENT, 1));
            layout.addView(textView);
        }
        return layout;
    }
}
