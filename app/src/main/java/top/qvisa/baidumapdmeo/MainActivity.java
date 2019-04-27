package top.qvisa.baidumapdmeo;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;

public class MainActivity extends LocationActivity {
    private MapView mapView;
    private Toolbar toolbar;
    private BaiduMap mBaiduMap;
    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        init_View();
        init_Toolbar();
        mBaiduMap = mapView.getMap();
        mBaiduMap.setMyLocationEnabled(true);
        request_Location(coordinatorLayout,mBaiduMap);

    }

    public void init_View() {
        toolbar = findViewById(R.id.toolbar);
        mapView = findViewById(R.id.mv_MapView);
        coordinatorLayout = findViewById(R.id.layout_coordinator);
    }

    private void init_Toolbar() {
        toolbar.setTitle("在此出搜索");
        toolbar.setTitleTextAppearance(this, R.style.ToolBarTittle);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.outline_menu_24);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "你点击了搜索", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Change_mapView:
                if (mBaiduMap.getMapType()==BaiduMap.MAP_TYPE_SATELLITE){
                    mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                }else {
                    mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                }
                break;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        mLocationClient.stop();
        mBaiduMap.setMyLocationEnabled(false);
    }
}