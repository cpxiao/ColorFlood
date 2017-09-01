package com.cpxiao.colorflood.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.cpxiao.R;
import com.cpxiao.androidutils.library.utils.PreferencesUtils;
import com.cpxiao.colorflood.controller.Controller;
import com.cpxiao.colorflood.imps.OnToolViewClickListener;
import com.cpxiao.colorflood.mode.Extra;
import com.cpxiao.colorflood.views.ColorToolView;
import com.cpxiao.colorflood.views.GameView;
import com.cpxiao.gamelib.fragment.BaseFragment;

/**
 * @author cpxiao on 2017/09/01.
 */

public class CasualGameFragment extends BaseFragment {
    private int mGridCountX, mGridCountY;
    private int mScore, mBestScore;
    private TextView mScoreView, mBestScoreView;
    private Controller mController;
    private int[] mColorArray;

    public static CasualGameFragment newInstance(Bundle bundle) {
        CasualGameFragment fragment = new CasualGameFragment();
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

        mColorArray = Extra.Color._6colorArray;

        final Context context = getHoldingActivity();
        boolean needPadding = PreferencesUtils.getBoolean(context, Extra.Key.SETTING_HAS_BORDERS, Extra.Key.SETTING_HAS_BORDERS_DEFAULT);
        mController = new Controller.Builder()
                .setGridCountX(mGridCountX)
                .setGridCountY(mGridCountY)
                .setNeedPadding(needPadding)
                .setColorArray(mColorArray)
                .build();

        mScoreView = (TextView) view.findViewById(R.id.score_view);
        mScore = 0;
        setScoreView(mScore);

        mBestScoreView = (TextView) view.findViewById(R.id.best_score_view);
        String key = Extra.Key.CASUAL_GAME_BEST_SCORE + PreferencesUtils.getString(context, Extra.GridSize.SIZE_KEY, Extra.GridSize.SIZE_DEFAULT);
        mBestScore = PreferencesUtils.getInt(context, key, Extra.Key.CASUAL_GAME_BEST_SCORE_DEFAULT);
        setBestScoreView(mBestScore);

        final ColorToolView colorToolView = (ColorToolView) view.findViewById(R.id.color_tool_view);
        colorToolView.setColorArray(true, mColorArray);
        //        colorToolView.setNonClickableColorArray(mController.getNonClickableColorArray(false));
        colorToolView.setOnToolViewClickListener(new OnToolViewClickListener() {
            @Override
            public void onClickColor(int color) {
                if (mController.canBeFilled(false, true, color)) {
                    //更新
                    mController.update(false, true, color);
                    //步数加一
                    mScore++;
                    setScoreView(mScore);
                    //判断是否完成
                    if (mController.checkSuccess()) {
                        if (mScore <= mBestScore) {
                            String key = Extra.Key.CASUAL_GAME_BEST_SCORE + PreferencesUtils.getString(context, Extra.GridSize.SIZE_KEY, Extra.GridSize.SIZE_DEFAULT);
                            PreferencesUtils.putInt(context, key, mScore);
                        }
                        showSuccessDialog();
                    }
                    //                    //设置colorToolView的不可点击颜色
                    //                    colorToolView.setNonClickableColorArray(mController.getNonClickableColorArray(false));
                }
            }
        });

        GameView gameView = (GameView) view.findViewById(R.id.game_view);
        gameView.setController(mController);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_game_common;
    }


    private void showSuccessDialog() {
        Context context = getHoldingActivity();
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.mission_completed))
                .setMessage(context.getString(R.string.play_again))
                .setPositiveButton(context.getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Bundle bundle = new Bundle();
                        bundle.putInt(Extra.Name.GAME_DIFFICULTY_X, mGridCountX);
                        bundle.putInt(Extra.Name.GAME_DIFFICULTY_Y, mGridCountY);
                        addFragment(CasualGameFragment.newInstance(bundle));
                    }
                })
                .setNegativeButton(context.getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        removeFragment();
                    }
                })
                .create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
    }

    private void setScoreView(int score) {
        if (mScoreView != null) {
            String msg = String.valueOf(score);
            mScoreView.setText(msg);
        }
    }

    private void setBestScoreView(int score) {
        if (mBestScoreView != null) {
            String msg = getResources().getString(R.string.best_score) + " : " + String.valueOf(score);
            mBestScoreView.setText(msg);
        }
    }

}
