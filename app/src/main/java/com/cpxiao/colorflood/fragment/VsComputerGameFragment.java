package com.cpxiao.colorflood.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.cpxiao.R;
import com.cpxiao.androidutils.library.utils.PreferencesUtils;
import com.cpxiao.colorflood.controller.Controller;
import com.cpxiao.colorflood.imps.OnToolViewClickListener;
import com.cpxiao.colorflood.mode.extra.BlockColor;
import com.cpxiao.colorflood.mode.extra.Extra;
import com.cpxiao.colorflood.views.ColorToolView;
import com.cpxiao.colorflood.views.GameView;
import com.cpxiao.gamelib.fragment.BaseFragment;

/**
 * @author cpxiao on 2017/09/01.
 */

public class VsComputerGameFragment extends BaseFragment {
    private int mGridCountX, mGridCountY;
    private Controller mController;
    private int[] mColorArray;

    public static VsComputerGameFragment newInstance(Bundle bundle) {
        VsComputerGameFragment fragment = new VsComputerGameFragment();
        if (bundle != null) {
            fragment.setArguments(bundle);
        }
        return fragment;
    }
    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mGridCountX = bundle.getInt(Extra.Name.GAME_DIFFICULTY_X);
            mGridCountY = bundle.getInt(Extra.Name.GAME_DIFFICULTY_Y);
        }
        Context context = getHoldingActivity();
        mColorArray = BlockColor._6colorArray;
        boolean needPadding = PreferencesUtils.getBoolean(context, Extra.Key.SETTING_HAS_BORDERS, Extra.Key.SETTING_HAS_BORDERS_DEFAULT);
        mController = new Controller.Builder()
                .setGridCountX(mGridCountX)
                .setGridCountY(mGridCountY)
                .setNeedPadding(needPadding)
                .setColorArray(mColorArray)
                .build();

        ColorToolView colorToolView = (ColorToolView) view.findViewById(R.id.color_tool_view);
        colorToolView.setColorArray(true, mColorArray);
        colorToolView.setOnToolViewClickListener(new OnToolViewClickListener() {
            @Override
            public void onClickColor(int color) {
            }
        });

        GameView gameView = (GameView) view.findViewById(R.id.game_view);
        gameView.setController(mController);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_game_common;
    }
}
