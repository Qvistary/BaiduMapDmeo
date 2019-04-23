package top.qvisa.baidumapdmeo;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class BaseActivity extends AppCompatActivity {

    public void request_Permission(String[] permissions) {
        List<String> permissionList = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(BaseActivity.this, permissions[i])
                    != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(permissions[i]);
            }
        }
        if (!permissionList.isEmpty()) {
            String[] permission_list = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(this, permission_list, 1);
        }

    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0) {
                    for (int result : grantResults)
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            new AlertDialog
                                    .Builder(this)
                                    .setCancelable(false)
                                    .setTitle("错误信息：")
                                    .setMessage("需要授权全部的访问权限，否则将无法使用！")
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Intent intent = new Intent();
                                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                            //设置去向意图
                                            Uri uri = Uri.fromParts("package", BaseActivity.this.getPackageName(), null);
                                            intent.setData(uri);
                                            //发起跳转
                                            startActivity(intent);
                                        }
                                    })
                                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            finish();
                                        }
                                    }).show();
                            break;
                        }
                }
                break;
            }

        }
    }
}
