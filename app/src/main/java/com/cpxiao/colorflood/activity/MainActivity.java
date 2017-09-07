package com.cpxiao.colorflood.activity;

import android.os.Bundle;

import com.cpxiao.colorflood.fragment.HomeFragment;
import com.cpxiao.gamelib.activity.BaseZAdsActivity;
import com.cpxiao.gamelib.fragment.BaseFragment;
import com.cpxiao.zads.ZAdManager;
import com.cpxiao.zads.core.ZAdPosition;

public class MainActivity extends BaseZAdsActivity {

    @Override
    protected BaseFragment getFirstFragment() {
        return HomeFragment.newInstance(null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ZAdManager.getInstance().init(getApplicationContext());
        loadAds();
    }

    private void loadAds() {
        initAdMobAds100("ca-app-pub-4157365005379790/7658792277");
        initFbAds90("1292081760878860_1425254130894955");
        loadZAds(ZAdPosition.POSITION_MAIN);
    }

    @Override
    protected void onDestroy() {
        ZAdManager.getInstance().destroyAllPosition(this);
        super.onDestroy();
    }
}
