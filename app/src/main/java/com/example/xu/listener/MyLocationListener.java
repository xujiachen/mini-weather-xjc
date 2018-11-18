package com.example.xu.listener;

import android.util.Log;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.example.xu.app.MyApplication;
import com.example.xu.bean.City;

import java.util.List;
import java.util.logging.LogRecord;

/**
 * Created by Administrator on 2018/11/17.
 */
public class MyLocationListener extends BDAbstractLocationListener {
    public String recity;
    public String cityCode;
    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        String city = bdLocation.getCity();
        Log.d("location_city", city);
        recity = city.replace("市", "");
        List<City> mCityList;
        MyApplication myApplication = MyApplication.getInstance();

        mCityList = myApplication.getmCityList();

        for (City temp : mCityList) {
            if (temp.getCity().equals(recity)) {
                cityCode = temp.getNumber();
                Log.d("location_code", cityCode);
            }
        }
    }
}
