package com.training.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.training.BaseActivity;
import com.training.R;

import java.util.ArrayList;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenqiuyi on 17/3/14.
 */

public class MessageListActivity extends BaseActivity {
    @BindView(R.id.rv)
    RecyclerView recyclerView;

    //    private ArrayList<MessageInfo> messageInfos;
    private Map<String, String> messageMap;
    private ArrayList<String> pkgNameList;
    private MyNotificationManager mNotificationManager;

    private MessageBroadCast messageBroadCast;

    private RecyclerView.Adapter<MyHolder> adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        mKnife = ButterKnife.bind(this);

        mNotificationManager = MyNotificationManager.getInstance(this);
        messageMap = mNotificationManager.getMessageInfos();
        pkgNameList = mNotificationManager.getPackageNameList();

        messageBroadCast = new MessageBroadCast();
        registerReceiver(messageBroadCast, new IntentFilter(MyNotificationManager.ACTION_UPADTE_MESSAGE));
        initRv();
    }


    private void initRv() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new RecyclerView.Adapter<MyHolder>() {
            @Override
            public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View layout = LayoutInflater.from(getApplicationContext()).inflate(R.layout.notification_item, null);
                return new MyHolder(layout);
            }

            @Override
            public void onBindViewHolder(MyHolder holder, int position) {
                if (messageMap == null || pkgNameList == null) {
                    return;
                }
                String pkg = pkgNameList.get(position);
                PackageInfo info = getPackageInfo(pkg);
                if (messageMap.get(pkgNameList.get(position)) == null || info == null) {
                    return;
                }
                holder.ic.setImageDrawable(info.applicationInfo.loadIcon(getPackageManager()));
                holder.name.setText(info.applicationInfo.name);
                holder.text.setText(messageMap.get(pkg));
                holder.btn.setTag(position);
                holder.btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = (int) v.getTag();
                        mNotificationManager.removeMessage(pkgNameList.get(position));
                        recyclerView.invalidate();
                    }
                });
            }

            @Override
            public int getItemCount() {
                return pkgNameList.size();
            }
        };

        recyclerView.setAdapter(adapter);
    }

    private class MyHolder extends RecyclerView.ViewHolder {
        private ImageView ic;
        private TextView name;
        private TextView text;
        private Button btn;

        MyHolder(View itemView) {
            super(itemView);
            ic = (ImageView) itemView.findViewById(R.id.ic_app);
            name = (TextView) itemView.findViewById(R.id.name_app);
            text = (TextView) itemView.findViewById(R.id.no_text);
            btn = (Button) itemView.findViewById(R.id.clear);
        }
    }

    private PackageInfo getPackageInfo(String pkg) {
        try {
            return getPackageManager().getPackageInfo(pkg, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private class MessageBroadCast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(MyNotificationManager.ACTION_UPADTE_MESSAGE)) {
                messageMap = mNotificationManager.getMessageInfos();
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(messageBroadCast);
    }
}
