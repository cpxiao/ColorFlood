package com.cpxiao.colorflood.fragment;

import android.os.Bundle;
import android.view.View;

import com.cpxiao.gamelib.fragment.BaseFragment;

/**
 * @author cpxiao on 2017/09/01.
 */

public class BestScoreFragment extends BaseFragment {
    public static BestScoreFragment newInstance(Bundle bundle) {
        BestScoreFragment fragment = new BestScoreFragment();
        if (bundle != null) {
            fragment.setArguments(bundle);
        }
        return fragment;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {

    }

    @Override
    protected int getLayoutId() {
        return 0;
    }
}
