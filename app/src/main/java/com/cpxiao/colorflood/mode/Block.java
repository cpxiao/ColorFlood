package com.cpxiao.colorflood.mode;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.cpxiao.AppConfig;


/**
 * @author cpxiao on 2017/1/9.
 */

public class Block {
    private static final boolean DEBUG = AppConfig.DEBUG;
    private final String TAG = getClass().getSimpleName();
    public int gridX, gridY;
    /**
     * 方块中心坐标
     */
    private float centerX, centerY;

    /**
     * 方块宽高
     */
    private float width, height;
    public float percent;//占比，用于绘制动画

    private boolean needPadding;//方块之间是否有边界
    public int color;

    private RectF mBgRectF = new RectF();
    private float mBgPadding;

    private RectF mDrawRectF = new RectF();

    private Block() {
    }

    private Block(Builder builder) {
        gridX = builder.mGridX;
        gridY = builder.mGridY;
        color = builder.color;
        needPadding = builder.needPadding;

        updateSize(builder.centerX, builder.centerY, builder.width, builder.height);
    }

    public void updateSize(float centerX, float centerY, float width, float height) {
        if (width <= 0 || height <= 0) {
            if (DEBUG) {
//                throw new IllegalArgumentException("width <= 0 || height <= 0");
            }
            return;
        }
        this.centerX = centerX;
        this.centerY = centerY;
        this.width = width;
        this.height = height;
        if (needPadding) {
            mBgPadding = Math.max(Math.min(width, height) / 20, 2);
        } else {
            mBgPadding = 0;
        }
        mBgRectF.left = centerX - width / 2 + mBgPadding;
        mBgRectF.top = centerY - height / 2 + mBgPadding;
        mBgRectF.right = centerX + width / 2 - mBgPadding;
        mBgRectF.bottom = centerY + height / 2 - mBgPadding;


    }

    public void draw(Canvas canvas, Paint paint) {
        drawBg(canvas, paint);

        drawBlock(canvas, paint);
    }


    private void drawBg(Canvas canvas, Paint paint) {
        paint.setColor(Extra.Color.colorBg);
        canvas.drawRoundRect(mBgRectF, mBgPadding, mBgPadding, paint);
    }


    private void drawBlock(Canvas canvas, Paint paint) {
        if (percent < 1) {
            percent += 0.2f;
        }
        if (percent > 1) {
            percent = 1;
        }
        paint.setColor(color);
        setDrawRectF(percent, width - mBgPadding * 2, height - mBgPadding * 2);
        canvas.drawRect(mDrawRectF, paint);
    }


    private void setDrawRectF(float percent, float width, float height) {
        float rectFW = percent * width;
        float rectFH = percent * height;
        mDrawRectF.left = centerX - 0.5f * rectFW;
        mDrawRectF.right = mDrawRectF.left + rectFW;
        mDrawRectF.top = centerY - 0.5f * rectFH;
        mDrawRectF.bottom = mDrawRectF.top + rectFH;
    }

    public static class Builder {
        public int mGridX, mGridY;

        public float centerX, centerY;

        public float width, height;

        public int color;
        public boolean needPadding;

        public Builder() {
        }

        public Builder setGridX(int gridX) {
            mGridX = gridX;
            return this;
        }

        public Builder setGridY(int gridY) {
            mGridY = gridY;
            return this;
        }

        public Builder setCenterX(float centerX) {
            this.centerX = centerX;
            return this;
        }

        public Builder setCenterY(float centerY) {
            this.centerY = centerY;
            return this;
        }

        public Builder setWidth(float width) {
            this.width = width;
            return this;
        }

        public Builder setHeight(float height) {
            this.height = height;
            return this;
        }

        public Builder setColor(int color) {
            this.color = color;
            return this;
        }

        public Builder setNeedPadding(boolean needPadding) {
            this.needPadding = needPadding;
            return this;
        }

        public Block build() {
            return new Block(this);
        }
    }

}
