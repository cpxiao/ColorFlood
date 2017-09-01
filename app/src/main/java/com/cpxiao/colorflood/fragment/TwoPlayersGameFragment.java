package com.cpxiao.colorflood.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.cpxiao.R;
import com.cpxiao.androidutils.library.utils.PreferencesUtils;
import com.cpxiao.colorflood.controller.Controller;
import com.cpxiao.colorflood.imps.OnToolViewClickListener;
import com.cpxiao.colorflood.mode.Extra;
import com.cpxiao.colorflood.views.ColorToolView;
import com.cpxiao.colorflood.views.GameView;
import com.cpxiao.gamelib.fragment.BaseFragment;
import com.cpxiao.gamelib.views.RotateTextView;

/**
 * @author cpxiao on 2017/09/01.
 */

public class TwoPlayersGameFragment extends BaseFragment {
    private int mGridCountX, mGridCountY;
    private boolean isPlayerBottomStep;
    private ColorToolView mColorToolViewTop, mColorToolViewBottom;

    private Controller mController;
    private int[] mColorArray;

    private RotateTextView mScoreView;

    private RotateTextView mTopPlayerMsgView;
    private RotateTextView mBottomPlayerMsgView;
    private int mScoreTop = 0, mScoreBottom = 0;

    public static TwoPlayersGameFragment newInstance(Bundle bundle) {
        TwoPlayersGameFragment fragment = new TwoPlayersGameFragment();
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
        mColorArray = Extra.Color._6colorArray;
        boolean needPadding = PreferencesUtils.getBoolean(context, Extra.Key.SETTING_HAS_BORDERS, Extra.Key.SETTING_HAS_BORDERS_DEFAULT);
        mController = new Controller.Builder()
                .setGridCountX(mGridCountX)
                .setGridCountY(mGridCountY)
                .setNeedPadding(needPadding)
                .setColorArray(mColorArray)
                .build();

        mScoreView = (RotateTextView) view.findViewById(R.id.score_text_view);
        setScoreView();
        mTopPlayerMsgView = (RotateTextView) view.findViewById(R.id.top_player_msg);
        mTopPlayerMsgView.setText("");
        mBottomPlayerMsgView = (RotateTextView) view.findViewById(R.id.bottom_player_msg);
        mBottomPlayerMsgView.setText("");


        isPlayerBottomStep = true;
        mColorToolViewTop = (ColorToolView) view.findViewById(R.id.color_tool_view_top);
        mColorToolViewTop.setColorClickable(false);
        mColorToolViewTop.setColorArray(false, mColorArray);
        mColorToolViewTop.setOnToolViewClickListener(new OnToolViewClickListener() {
            @Override
            public void onClickColor(int color) {
                handClick(false, color);
            }
        });

        mColorToolViewBottom = (ColorToolView) view.findViewById(R.id.color_tool_view_bottom);
        mColorToolViewBottom.setColorClickable(true);
        mColorToolViewBottom.setColorArray(true, mColorArray);
        mColorToolViewBottom.setOnToolViewClickListener(new OnToolViewClickListener() {
            @Override
            public void onClickColor(int color) {
                handClick(true, color);
            }
        });

        GameView gameView = (GameView) view.findViewById(R.id.game_view);
        gameView.setController(mController);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_game_2players;
    }

    private void handClick(boolean isPlayerBottom, int color) {
        synchronized (TAG) {
            Context context = getHoldingActivity();
            //若为轮到，则返回
            if (isPlayerBottom != isPlayerBottomStep) {
                return;
            }
            //判断能否填充
            if (mController.canBeFilled(true, isPlayerBottomStep, color)) {
                //填充
                mController.update(true, isPlayerBottomStep, color);
                //更换上下ColorToolView点击状态
                mColorToolViewTop.setColorClickable(isPlayerBottomStep);
                mColorToolViewBottom.setColorClickable(!isPlayerBottomStep);
                //获得分数
                if (isPlayerBottomStep) {
                    mScoreBottom = mController.getFillCount(true);
                } else {
                    mScoreTop = mController.getFillCount(false);
                }
                //设置分数
                setScoreView();
                if (mController.checkSuccess(mScoreTop, mScoreBottom)) {
                    //game over
                    showSuccessDialog(context);
                }
                //更换
                isPlayerBottomStep = !isPlayerBottomStep;
            }
        }
    }

    private void showSuccessDialog(final Context context) {
        //        AlertDialog dialog = new AlertDialog.Builder(context)
        //                .setTitle(context.getString(R.string.mission_completed))
        //                .setMessage(context.getString(R.string.play_again))
        //                .setPositiveButton(context.getString(R.string.ok), new DialogInterface.OnClickListener() {
        //                    @Override
        //                    public void onClick(DialogInterface dialog, int which) {
        //                        Bundle bundle = TwoPlayersGameActivity.makeBundle(mGridCountX, mGridCountY);
        //                        Intent intent = TwoPlayersGameActivity.makeIntent(context, bundle);
        //                        startActivity(intent);
        //                    }
        //                })
        //                .setNegativeButton(context.getString(R.string.cancel), new DialogInterface.OnClickListener() {
        //                    @Override
        //                    public void onClick(DialogInterface dialog, int which) {
        //                        finish();
        //                    }
        //                })
        //                .create();
        //        dialog.setCanceledOnTouchOutside(false);
        //        dialog.setCancelable(false);
        //        dialog.show();
        if (mScoreTop >= mScoreBottom) {
            mTopPlayerMsgView.setText(R.string.you_win);
            mBottomPlayerMsgView.setText(R.string.you_lose);
        } else {
            mTopPlayerMsgView.setText(R.string.you_lose);
            mBottomPlayerMsgView.setText(R.string.you_win);
        }
        mColorToolViewTop.setColorClickable(false);
        mColorToolViewBottom.setColorClickable(false);
    }

    private void setScoreView() {
        if (mScoreView != null) {
            String msg = mScoreTop + " - " + mScoreBottom;
            mScoreView.setText(msg);
        }
    }
}
