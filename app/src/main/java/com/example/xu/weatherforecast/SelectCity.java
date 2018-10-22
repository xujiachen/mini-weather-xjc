package com.example.xu.weatherforecast;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.xu.adapter.CityAdapter;
import com.example.xu.app.MyApplication;
import com.example.xu.bean.City;

import java.util.List;

/**
 * Created by Administrator on 2018/10/16.
 */
public class SelectCity extends Activity implements View.OnClickListener {
    private ImageView mBackBtn;
    private TextView mTodayCity;
    private ListView mList;
    private List<City> cityList;
    private CityAdapter mAdapter;
    private String cityCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_city);

        initViews();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_back:
                Intent i = new Intent();
                if (cityCode == null) {
                    i.putExtra("cityCode", "101010100");
                } else {
                    i.putExtra("cityCode", cityCode);
                }
                setResult(RESULT_OK, i);
                finish();
                break;
            default:
                break;
        }
    }

    private void initViews() {
        Intent intent = getIntent();

        mTodayCity = (TextView)findViewById(R.id.title_name);
        mTodayCity.setText("当前城市：" + intent.getStringExtra("City"));
        //  为返回键声明监听事件
        mBackBtn = (ImageView)findViewById(R.id.title_back);
        mBackBtn.setOnClickListener(this);

        mList = (ListView)findViewById(R.id.title_list);
        MyApplication myApplication = (MyApplication)getApplication();
        cityList = myApplication.getmCityList();

        //for (City city : cityList) {
            //filterDataList
        //}

        mAdapter = new CityAdapter(SelectCity.this, cityList);
        mList.setAdapter(mAdapter);

        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                City city = cityList.get(i);
                mTodayCity.setText("当前城市：" + city.getCity());
                cityCode = city.getNumber();
            }
        });
    }
}
