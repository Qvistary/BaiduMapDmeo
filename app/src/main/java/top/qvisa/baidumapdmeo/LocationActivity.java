package top.qvisa.baidumapdmeo;

import android.Manifest;
import android.util.Log;
import android.view.View;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

public class LocationActivity extends BaseActivity {
    private String[] permissions = {
          //  Manifest.permission.READ_PHONE_STATE,//电话
          //  Manifest.permission.WRITE_EXTERNAL_STORAGE,//存储
            Manifest.permission.ACCESS_FINE_LOCATION//定位权限
    };

    private final static String TAG = "MainActivity";
    public LocationClient mLocationClient;
    private BaiduMap mBaiduMap;
    public boolean isFirst_Location = true;

    public void request_Location(View view, BaiduMap baiduMap) {
        this.mBaiduMap = baiduMap;
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new MyLocationListener());
        //声明LocationClient类实例并配置定位参数
        LocationClientOption locationOption = new LocationClientOption();
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        locationOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认gcj02，设置返回的定位结果坐标系，如果配合百度地图使用，建议设置为bd09ll;
        locationOption.setCoorType("bd09ll");
        mLocationClient.setLocOption(locationOption);
        requestPermission(view);
    }
    private void requestPermission(View view){
        request_Permission(permissions, view, "注意：你需要同意软件获取位置权限！", new PermissionListen() {
            @Override
            public void onGranted() {
                mLocationClient.start();
            }
        });
    }

    private void navigateTo(BDLocation bdLocation) {
        if (isFirst_Location) {
            LatLng latLng = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
            MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(latLng);
            mBaiduMap.animateMapStatus(update);
            update = MapStatusUpdateFactory.zoomTo(18f);
            mBaiduMap.animateMapStatus(update);
            isFirst_Location = false;
        }
        MyLocationData.Builder builder = new MyLocationData.Builder();
        builder.latitude(bdLocation.getLatitude());
        builder.longitude(bdLocation.getLongitude());
        MyLocationData locationData = builder.build();
        mBaiduMap.setMyLocationData(locationData);
    }


    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //获取纬度信息
            double latitude = location.getLatitude();
            //获取经度信息
            double longitude = location.getLongitude();
            Log.d(TAG, "纬度信息>>>" + latitude);
            Log.d(TAG, "经度信息>>>" + longitude);
            //获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明
            if (location.getLocType() == BDLocation.TypeGpsLocation) {
                Log.d(TAG, "GPS");
            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
                Log.d(TAG, "NETWORK");
            }

            if (location.getLocType() == BDLocation.TypeNetWorkLocation || location.getLocType() == BDLocation.TypeGpsLocation) {
                navigateTo(location);
            }
        }
    }
}
