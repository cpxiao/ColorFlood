package com.cpxiao.colorflood.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.cpxiao.AppConfig;
import com.cpxiao.androidutils.library.utils.PreferencesUtils;
import com.cpxiao.colorflood.imps.OnToolViewClickListener;
import com.cpxiao.colorflood.mode.extra.BlockColor;
import com.cpxiao.colorflood.mode.extra.Extra;

/**
 * @author cpxiao on 2017/01/14.
 */
public class ColorToolView extends View implements View.OnTouchListener {
    private static final boolean DEBUG = AppConfig.DEBUG;
    private static final String TAG = ColorToolView.class.getSimpleName();

    private int viewW, viewH;
    private int paddingLR, paddingTB;

    private int blockWH;
    private int blockPadding;
    private Paint mPaint = new Paint();
    private RectF mBlockRectF = new RectF();

    private int mSelectedIndex = -1;

    private int[] mColorArray;
    private boolean mColorClickable = true;
    private OnToolViewClickListener mOnToolViewClickListener;

    public ColorToolView(Context context) {
        super(context);
    }

    public ColorToolView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ColorToolView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        setOnTouchListener(this);
        viewW = w;
        viewH = h;
        updateSize();
    }

    private void updateSize() {
        if (mColorArray != null && mColorArray.length > 0) {
            int count = mColorArray.length;
            blockWH = Math.min(viewW / count, viewH);
            blockPadding = blockWH / 20;
            paddingTB = (viewH - blockWH) / 2;
            paddingLR = (viewW - blockWH * count) / 2;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        boolean isRound = PreferencesUtils.getBoolean(getContext(), Extra.Key.IS_ROUND, Extra.Key.IS_ROUND_DEFAULT);
        if (mColorArray != null) {
            for (int i = 0; i < mColorArray.length; i++) {
                mBlockRectF.left = paddingLR + blockWH * i + blockPadding;
                mBlockRectF.top = paddingTB + blockPadding;
                mBlockRectF.right = mBlockRectF.left + blockWH - blockPadding * 2;
                mBlockRectF.bottom = mBlockRectF.top + blockWH - blockPadding * 2;
                if (mColorClickable) {
                    mPaint.setColor(mColorArray[i]);
                    if (mSelectedIndex == i) {
                        mPaint.setAlpha(192);
                    } else {
                        mPaint.setAlpha(255);
                    }
                    //判断是否在不可点击数组里，若在，则设置灰色
                    if (mNonClickableColorArray != null) {
                        for (int nonClickableColor : mNonClickableColorArray) {
                            if (nonClickableColor == mColorArray[i]) {
                                mPaint.setColor(BlockColor.colorGray);
                            }
                        }
                    }
                } else {
                    mPaint.setColor(BlockColor.colorGray);
                    mPaint.setAlpha(255);
                }
                if (isRound) {
                    canvas.drawRoundRect(mBlockRectF, blockPadding, blockPadding, mPaint);
                } else {
                    canvas.drawRect(mBlockRectF, mPaint);
                }
            }
        }

    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (DEBUG) {
            Log.d(TAG, "onTouch: ");
        }
        if (event.getX() >= paddingLR && event.getX() <= viewW - paddingLR
                && event.getY() > 0 && event.getY() < viewH) {
            mSelectedIndex = (int) ((event.getX() - paddingLR) / blockWH);
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (mColorArray != null) {
                if (mSelectedIndex >= 0 && mSelectedIndex < mColorArray.length) {
                    if (mOnToolViewClickListener != null) {
                        mOnToolViewClickListener.onClickColor(mColorArray[mSelectedIndex]);
                    }
                }
            }
            mSelectedIndex = -1;
        }
        postInvalidate();
        return true;

    }

    /**
     * 设置颜色，若不是底部玩家，则颜色转置
     *
     * @param isBottomPlayer 是否是底部玩家
     * @param colorArray     默认值
     */
    public void setColorArray(boolean isBottomPlayer, int[] colorArray) {
        if (colorArray == null) {
            if (DEBUG) {
                throw new IllegalArgumentException("colorArray == null");
            }
            return;
        }
        if (isBottomPlayer) {
            mColorArray = colorArray;
        } else {
            int[] array = new int[colorArray.length];
            for (int i = 0; i < colorArray.length; i++) {
                array[i] = colorArray[colorArray.length - i - 1];
            }
            mColorArray = array;
        }
        updateSize();
    }

    private int[] mNonClickableColorArray;

    public void setNonClickableColorArray(int[] nonClickableColorArray) {
        mNonClickableColorArray = nonClickableColorArray;
        postInvalidate();
    }

    public void setColorClickable(boolean clickable) {
        mColorClickable = clickable;
        postInvalidate();
    }


    public void setOnToolViewClickListener(OnToolViewClickListener listener) {
        mOnToolViewClickListener = listener;
    }
}
