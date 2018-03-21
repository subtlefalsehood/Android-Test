package com.training.main;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.subtlefalsehood.base.utils.JumpUtils;
import com.training.R;
import com.training.douban.MovieActivity;
import com.training.mdtest.MDTestActivity;
import com.training.network.activity.NetWorkTestActivity;
import com.training.notification.NotificationListenActivity;
import com.training.others.screenshot.FloatViewService;
import com.training.service.activity.ServiceTestActivity;
import com.training.storage.activity.DataTestActivity;
import com.training.touch.TouchEventActivity;

import java.util.ArrayList;

/**
 * Created by chenqiuyi on 2017/12/25.
 */

public class MainAdapt extends RecyclerView.Adapter {
    private ArrayList<MainItemBean> mData;

    MainAdapt(ArrayList<MainItemBean> mData) {
        this.mData = mData;
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getTag() == null || !(v.getTag() instanceof Integer)) {
                return;
            }
            switch ((Integer) v.getTag()) {
                case R.id.btn_service:
                    JumpUtils.startActivity(v.getContext(), ServiceTestActivity.class, false);
                    break;
                case R.id.btn_data:
                    JumpUtils.startActivity(v.getContext(), DataTestActivity.class, false);
                    break;
                case R.id.btn_network:
                    JumpUtils.startActivity(v.getContext(), NetWorkTestActivity.class, false);
                    break;
                case R.id.btn_douban:
                    JumpUtils.startActivity(v.getContext(), MovieActivity.class, false);
                    break;
                case R.id.btn_md:
                    JumpUtils.startActivity(v.getContext(), MDTestActivity.class, false);
                    break;
                case R.id.btn_touch_event:
                    JumpUtils.startActivity(v.getContext(), TouchEventActivity.class, false);
                    break;
                case R.id.btn_screenshot:
                    v.getContext().startService(new Intent(v.getContext(), FloatViewService.class));
                    break;
                case R.id.btn_drag:
                    JumpUtils.startActivity(v.getContext(), MDTestActivity.class, false);
                    break;
                case R.id.btn_notification:
                    JumpUtils.startActivity(v.getContext(), NotificationListenActivity.class, false);
                    break;
            }
        }
    };

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MainViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MainViewHolder) {
            ((MainViewHolder) holder).mRoot.setTag(mData.get(position).getId());
            ((MainViewHolder) holder).mRoot.setOnClickListener(onClickListener);

            ((MainViewHolder) holder).mTitle.setText(mData.get(position).getTitle());
        }
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    private class MainViewHolder extends RecyclerView.ViewHolder {
        TextView mTitle;
        View mRoot;

        MainViewHolder(View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.tv_title);
            mRoot = itemView.findViewById(R.id.root);
        }
    }
}
