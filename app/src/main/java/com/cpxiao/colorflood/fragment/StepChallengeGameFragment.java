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
import com.cpxiao.colorflood.mode.extra.BlockColor;
import com.cpxiao.colorflood.mode.extra.Extra;
import com.cpxiao.colorflood.views.ColorToolView;
import com.cpxiao.colorflood.views.GameView;
import com.cpxiao.gamelib.fragment.BaseZAdsFragment;
import com.cpxiao.zads.core.ZAdPosition;

/**
 * @author cpxiao on 2017/09/01.
 */

public class StepChallengeGameFragment extends BaseZAdsFragment {

    private int mGridCountX, mGridCountY, mLimitStep;
    private int mScore;
    private TextView mScoreView;
    private Controller mController;
    private int[] mColorArray;
    private GameView gameView;

    private boolean isDialogShown = false;

    public static StepChallengeGameFragment newInstance(Bundle bundle) {
        StepChallengeGameFragment fragment = new StepChallengeGameFragment();
        if (bundle != null) {
            fragment.setArguments(bundle);
        }
        return fragment;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        loadZAds(ZAdPosition.POSITION_GAME);

        Bundle bundle = getArguments();
        if (bundle != null) {
            mGridCountX = bundle.getInt(Extra.Name.GAME_DIFFICULTY_X);
            mGridCountY = bundle.getInt(Extra.Name.GAME_DIFFICULTY_Y);
            mLimitStep = bundle.getInt(Extra.Name.LIMIT_STEP);
        }

        final Context context = getHoldingActivity();
        mColorArray = BlockColor.get6ColorArray(context);

        mScoreView = (TextView) view.findViewById(R.id.score_view);

        ColorToolView colorToolView = (ColorToolView) view.findViewById(R.id.color_tool_view);
        colorToolView.setColorArray(true, mColorArray);
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
                        showSuccessDialog(context);
                        return;
                    }
                    //判断是否达到步数上限
                    if (mScore >= mLimitStep) {
                        showFailDialog(context);
                    }

                }
            }
        });

        gameView = (GameView) view.findViewById(R.id.game_view);

        initGameView(context);
    }

    private void initGameView(Context context) {
        isDialogShown = false;
        boolean needPadding = PreferencesUtils.getBoolean(context, Extra.Key.SETTING_HAS_BORDERS, Extra.Key.SETTING_HAS_BORDERS_DEFAULT);
        mController = new Controller.Builder()
                .setGridCountX(mGridCountX)
                .setGridCountY(mGridCountY)
                .setNeedPadding(needPadding)
                .setColorArray(mColorArray)
                .build();

        mScore = 0;
        setScoreView(mScore);

        gameView.setController(mController);


    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_game_common;
    }

    public void showSuccessDialog(Context context) {
        String title = context.getString(R.string.mission_completed);
        String msg = context.getString(R.string.play_again);
        showDialog(context, title, msg);
    }

    public void showFailDialog(Context context) {
        String title =  context.getString(R.string.game_over);
        String msg = context.getString(R.string.play_again);
        showDialog(context, title, msg);
    }

    public void showDialog(final Context context, String title, String msg) {
        if (isDialogShown) {
            return;
        }
        isDialogShown = true;
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton(context.getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        initGameView(context);
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
            String msg = score + " / " + mLimitStep;
            mScoreView.setText(msg);
        }
    }

}
