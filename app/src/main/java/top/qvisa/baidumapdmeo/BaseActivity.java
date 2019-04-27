package top.qvisa.baidumapdmeo;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class BaseActivity extends AppCompatActivity {

    private View layoutRoot;
    private String permission_warning;


    public void request_Permission(String[] permissions, View view, String text) {
        this.permission_warning = text;
        this.layoutRoot = view;
        List<String> permissionList = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(this, permissions[i])
                    != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(permissions[i]);
            }
        }
        if (!permissionList.isEmpty()) {
            String[] permission_list = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(this, permission_list, 1);
        }


    }

    public void onRequestPermissionsResult(int requestCode, final String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0) {
                    for (int result : grantResults)
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            if (ActivityCompat.shouldShowRequestPermissionRationale(BaseActivity.this,
                                    permissions[0])) {
                                Snackbar.make(layoutRoot, permission_warning, Snackbar.LENGTH_LONG).setAction(
                                        "去请求", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                request_Permission(permissions, layoutRoot, permission_warning);
                                            }
                                        }
                                ).show();
                            } else {
                                Snackbar.make(layoutRoot, permission_warning, Snackbar.LENGTH_LONG).setAction(
                                        "去设置", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Intent intent = new Intent();
                                                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                                //设置去向意图
                                                Uri uri = Uri.fromParts("package", BaseActivity.this.getPackageName(),
                                                        null);
                                                intent.setData(uri);
                                                //发起跳转
                                                startActivity(intent);
                                            }
                                        }
                                ).show();
                            }
                            break;
                        }
                }
                break;
            }
        }
    }
}
