package com.cpxiao.colorflood.views;

import android.content.Context;
import android.util.AttributeSet;

import com.cpxiao.colorflood.controller.Controller;
import com.cpxiao.gamelib.views.BaseSurfaceViewFPS;

/**
 * ColorClickView
 *
 * @author cpxiao on 2017/1/9.
 */
public class GameView extends BaseSurfaceViewFPS {
    /**
     * 控制器
     */
    private Controller mController;

    public GameView(Context context) {
        super(context);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initWidget() {
        if (mController != null) {
            mController.updateSize(mViewWidth, mViewHeight);
        }
    }

    @Override
    public void drawCache() {
        if (mController != null) {
            mController.draw(mCanvasCache);
        }
    }

    @Override
    protected void timingLogic() {

    }

    public void setController(Controller controller) {
        mController = controller;
        if (mController != null) {
            mController.updateSize(mViewWidth, mViewHeight);
        }
    }


}
