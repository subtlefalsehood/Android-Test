package com.training.notification;

import com.training.BaseActivity;

//public class NotificationListenActivity extends BaseActivity implements View.OnClickListener {
public class NotificationListenActivity extends BaseActivity {
//    private static final int EVENT_SHOW_CREATE_NOS = 0;
//    private static final int EVENT_LIST_CURRENT_NOS = 1;
//    private static final String ENABLED_NOTIFICATION_LISTENERS = "enabled_notification_listeners";
//    private static final String ACTION_NOTIFICATION_LISTENER_SETTINGS = "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS";
//    private boolean isEnabledNLS = false;
//
//    @BindView(R.id.activity_main)
//    LinearLayout layout;
//    private int[][] buttonInfo = {
//            {R.id.btn_start, R.string.start},
//            {R.id.btn_show, R.string.show},
//            {R.id.btn_authorize, R.string.authorize},
//            {R.id.btn_list, R.string.list},
//            {R.id.btn_message, R.string.message},
//            {R.id.btn_open, R.string.open},
//            {R.id.btn_close, R.string.close}
//    };
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        mKnife = ButterKnife.bind(this);
//        setupView();
////        toggleNotificationListenerService(getApplicationContext());
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//    }
//
//    private void setupView() {
//        Button[] buttons = new Button[buttonInfo.length];
//        for (int i = 0; i < buttonInfo.length; i++) {
//            buttons[i] = new Button(this);
//            buttons[i].setId(buttonInfo[i][0]);
//            buttons[i].setText(buttonInfo[i][1]);
//            buttons[i].setGravity(Gravity.CENTER);
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 0, 1);
//            layout.addView(buttons[i], params);
//            JumpUtils.setClick(this, buttons[i]);
//        }
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.btn_start:
//                toggleNotificationListenerService(this);
//                break;
//            case R.id.btn_show:
//                break;
//            case R.id.btn_authorize:
//                jumpAuthorization();
//                break;
//            case R.id.btn_list:
//                if (isEnabled()) {
//                    JumpUtils.startActivity(this, NotificationListActivity.class, false);
//                } else {
//                    jumpAuthorization();
//                }
//                break;
//            case R.id.btn_message:
//                JumpUtils.startActivity(this, MessageListActivity.class, false);
//                break;
//            case R.id.btn_open:
//                sendListen(true);
//                break;
//            case R.id.btn_close:
//                sendListen(false);
//                break;
//            default:
//                break;
//        }
//    }
//
//    private void sendListen(boolean needListen) {
//        Intent intent = new Intent(NotificationMonitor.ACTION_LISTEN);
//        intent.putExtra(NotificationMonitor.ACTION_LISTEN, needListen);
//        sendBroadcast(intent);
//    }
//
//    private void jumpAuthorization() {
//        Intent intent = new Intent(ACTION_NOTIFICATION_LISTENER_SETTINGS);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);
//    }
//
//    private boolean isEnabled() {
//        String pkgName = getPackageName();
//        final String flat = Settings.Secure.getString(getContentResolver(),
//                ENABLED_NOTIFICATION_LISTENERS);
//        if (!TextUtils.isEmpty(flat)) {
//            final String[] names = flat.split(":");
//            for (int i = 0; i < names.length; i++) {
//                final ComponentName cn = ComponentName.unflattenFromString(names[i]);
//                if (cn != null) {
//                    if (TextUtils.equals(pkgName, cn.getPackageName())) {
//                        return true;
//                    }
//                }
//            }
//        }
//        return false;
//    }
//
//    public static void toggleNotificationListenerService(Context context) {
//        Logger.e("toggleNotificationListenerService");
//        PackageManager pm = context.getPackageManager();
//        pm.setComponentEnabledSetting(new ComponentName(context, NotificationMonitor.class),
//                PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
//
//        pm.setComponentEnabledSetting(new ComponentName(context, NotificationMonitor.class),
//                PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
//    }
}
