package top.qvisa.baidumapdmeo;


import android.Manifest;
import android.os.Bundle;


public class MainActivity extends BaseActivity {
    private  String[] permissions={
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        request_Permission(permissions);
    }
}