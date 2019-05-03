package top.qvisa.baidumapdmeo;

import android.Manifest;
import android.graphics.Point;
import android.util.Log;
import android.view.View;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MyLocationConfiguration;
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
    private double mLatitude,mLongitude;




    public void request_Location(View view, BaiduMap baiduMap) {
        this.mBaiduMap = baiduMap;
        //开启地图的定位图层
        mBaiduMap.setMyLocationEnabled(true);
        //设置指南针位置
        Point pointCompass = new Point();
        pointCompass.set(100, 300);
        mBaiduMap.setCompassPosition(pointCompass);
        //默认打开路况
        mBaiduMap.setTrafficEnabled(true);


        mLocationClient = new LocationClient(getApplicationContext());

        MyLocationListener mMyLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(mMyLocationListener);
        //声明LocationClient类实例并配置定位参数
        LocationClientOption locationOption = new LocationClientOption();
        //设置定位模式，高精度，低功耗，仅设备
        locationOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //获得周边POI信息
        locationOption.setIsNeedLocationPoiList(true);
        //设置返回的定位结果坐标系，配合百度地图使用设置为bd09ll;
        locationOption.setCoorType("bd09ll");
        mLocationClient.setLocOption(locationOption);
        requestPermission(view);
    }

    private void requestPermission(View view) {
        request_Permission(permissions, view, "注意：你需要同意软件获取位置权限！", new PermissionListen() {
            @Override
            public void onGranted() {
                mLocationClient.start();
            }
        });
    }

    private void navigateTo() {
        if (isFirst_Location) {
            LatLng latLng = new LatLng(mLatitude, mLongitude);
            MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(latLng);
            mBaiduMap.animateMapStatus(update);
            update = MapStatusUpdateFactory.zoomTo(18f);
            mBaiduMap.animateMapStatus(update);
            isFirst_Location = false;
        }
        MyLocationData.Builder builder = new MyLocationData.Builder();
        builder.latitude(mLatitude)
                .longitude(mLongitude);
        MyLocationData locationData = builder.build();
        mBaiduMap.setMyLocationData(locationData);
    }

    private class MyLocationListener  extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            mLatitude = location.getLatitude();
            mLongitude = location.getLongitude();
            Log.d(TAG,"MyLocationListener>>"+"  维度>>"+mLatitude+"  经度>>"+mLongitude);
            if (location.getLocType() == BDLocation.TypeNetWorkLocation || location.getLocType() == BDLocation.TypeGpsLocation) {
                navigateTo();
            }
        }
    }
}
