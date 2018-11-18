package com.example.xu.app;

import android.app.Application;
import android.os.Environment;
import android.util.Log;

import com.example.xu.bean.City;
import com.example.xu.db.CityDB;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/10/17.
 */
public class MyApplication extends Application {          //  一般处理全局数据
    private static final String TAG = "MYAPP";

    private static MyApplication mApplication;
    private CityDB mCityDB;

    private List<City> mCityList;
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "MyApplication->Oncreate");

        mApplication = this;
        mCityDB = openCityDB();
        initCityList();
    }
    //  构造函数，单例模式
    public static MyApplication getInstance() {
        return mApplication;
    }

    //  开启线程读取数据库
    private void initCityList() {
        mCityList = new ArrayList<City>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                prepareCityList();
            }
        }).start();
    }
    //  读取数据库中数据并赋值List
    private boolean prepareCityList() {
        mCityList = mCityDB.getAllCity();
        int i = 0;
        for (City city : mCityList) {
            i++;
            String cityName = city.getCity();
            String cityCode = city.getNumber();
            //Log.d(TAG, cityCode + ":" + cityName);
        }
        Log.d(TAG, "i=" + i);
        return true;
    }
    //  获取城市列表，对外提供接口
    public List<City> getmCityList() {
        return mCityList;
    }
    //  获取数据库路径并初始化CityDB，找不到路径则新建数据库并读取db
    private CityDB openCityDB() {
        String path = "/data"
                      + Environment.getDataDirectory().getAbsolutePath()
                      + File.separator + getPackageName()
                      + File.separator + "databases1"
                      + File.separator
                      + CityDB.CITY_DB_NAME;
        File db = new File(path);
        Log.d(TAG, path);
        if (!db.exists()) {
            //  先创建目录
            String pathfolder = "/data"
                                + Environment.getDataDirectory().getAbsolutePath()
                                + File.separator + getPackageName()
                                + File.separator + "databases1"
                                + File.separator;
            File dirFirstFolder = new File(pathfolder);
            if (!dirFirstFolder.exists()) {
                dirFirstFolder.mkdirs();
                Log.i("MyApp", "mkdirs");
            }
            Log.i("MyApp", "db is not exists");
            //  读取写入数据
            try {
                InputStream is = getAssets().open("city.db");
                FileOutputStream fos = new FileOutputStream(db);
                int len = -1;
                byte[] buffer = new byte[1024];
                while ((len = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                    fos.flush();
                }
                fos.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(0);
            }
        }
        return new CityDB(this, path);
    }
}
