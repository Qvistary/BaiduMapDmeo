package top.qvisa.baidumapdmeo;

import android.Manifest;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;

public class MainActivity extends BaseActivity {
    private String[] permissions = {
            //  Manifest.permission.READ_PHONE_STATE,//电话
            // Manifest.permission.WRITE_EXTERNAL_STORAGE,//存储
            Manifest.permission.ACCESS_FINE_LOCATION//定位权限
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CoordinatorLayout coordinatorLayout = findViewById(R.id.layout_coordinator);
        request_Permission(permissions, coordinatorLayout);

    }
}