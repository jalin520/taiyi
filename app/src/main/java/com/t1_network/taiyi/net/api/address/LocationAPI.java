package com.t1_network.taiyi.net.api.address;

import android.content.Context;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

/**
 * Created by Chenyu on 2015/12/30.
 */
public class LocationAPI {

    private static AMapLocationClient aMapLocationClient;
    private static AMapLocationClientOption aMapLocationClientOption;
    private static AMapLocationListener aMapLocationListener;
    private LocationAPIListener listener;

    public LocationAPI(Context context,LocationAPIListener locationAPIListener) {
        this.listener = locationAPIListener;
        aMapLocationClient = new AMapLocationClient(context);
        aMapLocationClientOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        aMapLocationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        aMapLocationClientOption.setNeedAddress(true);
        aMapLocationClientOption.setOnceLocation(true);
        aMapLocationClientOption.setWifiActiveScan(true);
        aMapLocationClientOption.setMockEnable(false);
        aMapLocationClient.setLocationOption(aMapLocationClientOption);
        //启动定位
        aMapLocationClient.startLocation();
        aMapLocationListener = new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocationListener != null && aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {
                        listener.apiLocateSuccess(aMapLocation);
                    } else {
                        listener.apiLocateFailure(aMapLocation);
                    }
                }
            }
        };
        aMapLocationClient.setLocationListener(aMapLocationListener);
    }
    public interface LocationAPIListener{
        public void apiLocateSuccess(AMapLocation location);
        public void apiLocateFailure(AMapLocation location);
    }
}
