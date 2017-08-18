package com.training.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.training.BaseActivity;
import com.training.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by chenqiuyi on 17/3/9.
 */

public class NotificationListActivity extends BaseActivity {
    @BindView(R.id.rv)
    RecyclerView recyclerView;

    public static String NOTIFICATION_INFO = "notification_info";
    private ArrayList<AppInfo> appInfos;
    private MyNotificationManager myNotificationManager;

    private InfoBroadCast infoBroadCast;
    public static final String ACTION_GETDATA_FROM_DB = "action_getdata_from_db";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        mKnife = ButterKnife.bind(this);

        infoBroadCast = new InfoBroadCast();
        IntentFilter filter = new IntentFilter(ACTION_GETDATA_FROM_DB);
        registerReceiver(infoBroadCast, filter);

        myNotificationManager = MyNotificationManager.getInstance(this);
        appInfos = myNotificationManager.getAppInfosFromDb();
    }


    private void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        RecyclerView.Adapter<MyViewHolder> viewHolderAdapter = new RecyclerView.Adapter<MyViewHolder>() {
            @Override
            public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_layout, null);
                return new MyViewHolder(itemView);
            }

            @Override
            public void onBindViewHolder(MyViewHolder holder, int position) {
                AppInfo info = appInfos.get(position);
                holder.name.setText(info.getApp_name());
                holder.icon.setImageDrawable(getIcon(info.getPkgName()));
                holder.switchView.setOnCheckedChangeListener(null);
                holder.switchView.setChecked(info.isListen());
                holder.switchView.setTag(position);
                holder.switchView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        int position = (int) buttonView.getTag();
                        appInfos.get(position).setListen(isChecked);
                        myNotificationManager.update(appInfos.get(position).getPkgName(), isChecked);
                    }
                });
            }

            @Override
            public int getItemCount() {
                return appInfos.size();
            }
        };
        recyclerView.setAdapter(viewHolderAdapter);
    }


    private class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private ImageView icon;
        private Switch switchView;

        MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.tv_name);
            icon = (ImageView) itemView.findViewById(R.id.icon);
            switchView = (Switch) itemView.findViewById(R.id.switchView);
        }
    }

    private static boolean isNotificationListenServiceEnabled(Context context) {
        Set<String> packageNames = NotificationManagerCompat.getEnabledListenerPackages(context);
        return packageNames.contains(context.getPackageName());
    }


    private void initInfos() {
        PackageManager pm = getPackageManager();
        Intent query = new Intent(Intent.ACTION_MAIN);
        query.addCategory("android.intent.category.LAUNCHER");
        List<ResolveInfo> resolves = pm
                .queryIntentActivities(query, PackageManager.GET_RESOLVED_FILTER);
        Collections.sort(resolves, new ResolveInfo.DisplayNameComparator(pm));
        for (ResolveInfo info : resolves) {
            AppInfo itemInfo = new AppInfo();
            itemInfo.setPkgName(info.activityInfo.packageName);
            itemInfo.setApp_name((String) info.loadLabel(pm));
            try {
                itemInfo.setVerCode(pm.getPackageInfo(info.activityInfo.packageName, 0).versionCode);
                itemInfo.setVerName(pm.getPackageInfo(info.activityInfo.packageName, 0).versionName);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            appInfos.add(itemInfo);
        }
        myNotificationManager.insert(appInfos);
    }

    private Drawable getIcon(String pkg) {
        PackageManager packageManager = getPackageManager();
        try {
            return packageManager.getApplicationIcon(pkg);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return getDrawable(R.drawable.ic_wing_1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(infoBroadCast);
    }

    class InfoBroadCast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ACTION_GETDATA_FROM_DB)) {
                if (appInfos.size() == 0) {
                    initInfos();
                }
                initView();
            }
        }
    }
}
