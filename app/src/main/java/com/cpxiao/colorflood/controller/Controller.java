package com.cpxiao.colorflood.controller;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.cpxiao.colorflood.mode.Block;
import com.cpxiao.AppConfig;

import java.util.Random;
import java.util.Stack;

import hugo.weaving.DebugLog;


/**
 * @author cpxiao on 2017/1/9.
 */
public class Controller {
    private static final boolean DEBUG = AppConfig.DEBUG;
    private final String TAG = getClass().getSimpleName();

    private static  final Paint mPaint = new Paint();

    private static final boolean isFitXY = false;
    private int mGridCountX, mGridCountY;
    private Block[][] mGrid;
    private Block mBaseBlockTop, mBaseBlockBottom;


    private Random mRandom = new Random();
    private int[] mColorArray;

    static {
        mPaint.setAntiAlias(true);//抗锯齿
        mPaint.setDither(true);//防抖动
        mPaint.setFilterBitmap(true);//用来对位图进行滤波处理
    }

    private Controller() {
    }

    private Controller(Builder builder) {
        if (builder == null) {
            if (DEBUG) {
                throw new NullPointerException("builder == null");
            }
            return;
        }
        mColorArray = builder.colorArray;
        initGrid(builder.mGridCountX, builder.mGridCountY, builder.mNeedPadding);
    }

    private void initGrid(int gridCountX, int gridCountY, boolean needPadding) {
        this.mGridCountX = gridCountX;
        this.mGridCountY = gridCountY;
        mGrid = new Block[gridCountY][gridCountX];
        for (int x = 0; x < gridCountX; x++) {
            for (int y = 0; y < gridCountY; y++) {
                mGrid[y][x] = new Block.Builder()
                        .setGridX(x)
                        .setGridY(y)
                        //                        .setCenterX(paddingLR + blockW / 2 + x * blockW)
                        //                        .setCenterY(paddingTB + blockH / 2 + y * blockH)
                        //                        .setWidth(blockW)
                        //                        .setHeight(blockH)
                        .setColor(getRandomColor())
                        .setNeedPadding(needPadding)
                        .build();
            }
        }
        mBaseBlockTop = mGrid[0][mGridCountX - 1];
        mBaseBlockBottom = mGrid[mGridCountY - 1][0];
    }

    private int getRandomColor() {
        if (mColorArray != null) {
            int index = mRandom.nextInt(mColorArray.length);
            return mColorArray[index];
        }
        return 0;
    }

    public void updateSize(int w, int h) {
        int blockW;
        int blockH;
        float paddingLR, paddingTB;
        if (isFitXY) {
            blockW = w / mGridCountX;
            blockH = h / mGridCountY;
            paddingLR = 0;
            paddingTB = 0;
        } else {
            blockW = blockH = Math.min(w / mGridCountX, h / mGridCountY);
            paddingLR = (w - blockW * mGridCountX) / 2;
            paddingTB = (h - blockH * mGridCountY) / 2;
        }
        float centerX, centerY, width, height;
        if (w > 0 && h > 0) {
            for (int x = 0; x < mGridCountX; x++) {
                for (int y = 0; y < mGridCountY; y++) {
                    centerX = paddingLR + blockW / 2 + x * blockW;
                    centerY = paddingTB + blockH / 2 + y * blockH;
                    width = blockW;
                    height = blockH;
                    mGrid[y][x].updateSize(centerX, centerY, width, height);
                }
            }
        }
    }

    public void draw(Canvas canvas) {
        for (int x = 0; x < mGridCountX; x++) {
            for (int y = 0; y < mGridCountY; y++) {
                mGrid[y][x].draw(canvas, mPaint);
            }
        }
    }

    public int[] getNonClickableColorArray(boolean isVsMode) {
        if (isVsMode) {
            int[] array = new int[2];
            array[0] = mBaseBlockBottom.color;
            array[1] = mBaseBlockTop.color;
            return array;
        } else {
            int[] array = new int[1];
            array[0] = mBaseBlockBottom.color;
            return array;
        }
    }

    /**
     * update
     */
    public boolean canBeFilled(boolean isVSMode, boolean isPlayerBottom, int color) {
        if (mGrid != null) {
            if (isVSMode) {
                return (color != mBaseBlockTop.color &&
                        color != mBaseBlockBottom.color);
            } else {
                if (isPlayerBottom) {
                    return color != mBaseBlockBottom.color;
                } else {
                    return color != mBaseBlockTop.color;
                }
            }
        }
        return false;
    }

    @DebugLog
    public int getFillCount(boolean isPlayerBottom) {
        int count;
        if (isPlayerBottom) {
            getFillCount(mBaseBlockBottom.gridX, mBaseBlockBottom.gridY, mBaseBlockBottom.color + 1);
            count = getFillCount(mBaseBlockBottom.gridX, mBaseBlockBottom.gridY, mBaseBlockBottom.color - 1);
            return count;
        } else {
            getFillCount(mBaseBlockTop.gridX, mBaseBlockTop.gridY, mBaseBlockTop.color + 1);
            count = getFillCount(mBaseBlockTop.gridX, mBaseBlockTop.gridY, mBaseBlockTop.color - 1);
            return count;
        }
    }

    @DebugLog
    public void update(boolean isVSMode, boolean isDown, int color) {
        synchronized (TAG) {
            if (canBeFilled(isVSMode, isDown, color)) {
                if (isDown) {
                    getFillCount(mBaseBlockBottom.gridX, mBaseBlockBottom.gridY, color);
                } else {
                    getFillCount(mBaseBlockTop.gridX, mBaseBlockTop.gridY, color);
                }
            }
        }
    }

    /**
     * 判断是否成功
     *
     * @return boolean
     */
    public boolean checkSuccess() {
        int baseColor = mGrid[0][0].color;
        for (int y = 0; y < mGridCountY; y++) {
            for (int x = 0; x < mGridCountX; x++) {
                if (mGrid[y][x].color != baseColor) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean checkSuccess(int top, int bottom) {
        return top + bottom >= mGridCountX * mGridCountY;
    }

    /**
     *
     */
    private Stack<Block> mStacks = new Stack<>();

    /**
     * @param gridX    坐标x
     * @param gridY    坐标y
     * @param newColor 新颜色
     * @return 填充数量
     */
    @DebugLog
    private int getFillCount(int gridX, int gridY, int newColor) {
        int fillCount = 0;
        int baseColor = mGrid[gridY][gridX].color;

        if (newColor == baseColor) {
            return 0;
        }
        mStacks.clear();
        //步骤1：将种子点(x, y)入栈；
        mStacks.push(mGrid[gridY][gridX]);
        //步骤2：判断栈是否为空，如果栈为空则结束算法，否则取出栈顶元素作为当前扫描线的种子点(x, y)， y是当前的扫描线；
        while (!mStacks.isEmpty()) {
            /**
             * 步骤3：从种子点(x, y)出发，沿当前扫描线向左、右两个方向填充，直到边界。分别标记区段的左、右端点坐标为xLeft和xRight；
             */
            Block seed = mStacks.pop();
            int countL = fillLineLeft(mGrid, baseColor, newColor, seed.gridX, seed.gridY);
            int left = seed.gridX - countL + 1;
            fillCount += countL;

            int countR = fillLineRight(mGrid, baseColor, mGridCountX, newColor, seed.gridX + 1, seed.gridY);
            int right = seed.gridX + countR;
            fillCount += countR;

            /**
             * 步骤4：
             * 分别检查与当前扫描线相邻的y - 1和y + 1两条扫描线在区间[xLeft, xRight]中的像素，
             * 从xRight开始向xLeft方向搜索，假设扫描的区间为AAABAAC（A为种子点颜色），
             * 那么将B和C前面的A作为种子点压入栈中，然后返回第（2）步；
             */
            //从y-1找种子
            if (seed.gridY - 1 >= 0) {
                findSeedInNewLine(mGrid, baseColor, mGridCountX, seed.gridY - 1, left, right);
            }
            //从y+1找种子
            if (seed.gridY + 1 < mGridCountY) {
                findSeedInNewLine(mGrid, baseColor, mGridCountX, seed.gridY + 1, left, right);
            }
        }
        return fillCount;
    }

    /**
     * 往右填色，返回填充的个数
     *
     * @return int
     */
    private int fillLineRight(Block[][] grid, int baseColor, int gridCountX, int newColor, int gridX, int gridY) {
        int count = 0;

        while (gridX < gridCountX) {
            if (needFillPixel(grid, baseColor, gridX, gridY)) {
                grid[gridY][gridX].percent = 0;
                grid[gridY][gridX].color = newColor;
                count++;
                gridX++;
            } else {
                break;
            }

        }

        return count;
    }


    /**
     * 往左填色，返回填色的数量值
     *
     * @return int
     */
    private int fillLineLeft(Block[][] grid, int baseColor, int newColor, int gridX, int gridY) {
        int count = 0;
        while (gridX >= 0) {
            if (needFillPixel(grid, baseColor, gridX, gridY)) {
                grid[gridY][gridX].percent = 0;
                grid[gridY][gridX].color = newColor;
                count++;
                gridX--;
            } else {
                break;
            }

        }
        return count;
    }

    private boolean needFillPixel(Block[][] grid, int baseColor, int x, int y) {
        return grid[y][x].color == baseColor;
    }

    /**
     * 在新行找种子节点
     */
    private void findSeedInNewLine(Block[][] grid, int baseColor, int gridCountX, int indexY, int left, int right) {
        /**
         * 获得该行的开始索引
         */
        /**
         * 获得该行的结束索引
         */
        int end = right;

        boolean hasSeed = false;

        int rx;
        /**
         * 从end到begin，找到种子节点入栈（AAABAAAB，则B前的A为种子节点）
         */
        while (end >= left) {
            if (grid[indexY][end].color == baseColor) {
                if (!hasSeed) {
                    rx = end % gridCountX;
                    mStacks.push(this.mGrid[indexY][rx]);
                    hasSeed = true;
                }
            } else {
                hasSeed = false;
            }
            end--;
        }
    }

    public static class Builder {
        private int mGridCountX;
        private int mGridCountY;
        private int mWidth;
        private int mHeight;
        private boolean mNeedPadding;
        private int[] colorArray;

        public Builder() {
        }

        public Builder setGridCountX(int gridCountX) {
            mGridCountX = gridCountX;
            return this;
        }

        public Builder setGridCountY(int gridCountY) {
            mGridCountY = gridCountY;
            return this;
        }

        public Builder setWidth(int width) {
            mWidth = width;
            return this;
        }

        public Builder setHeight(int height) {
            mHeight = height;
            return this;
        }

        public Builder setNeedPadding(boolean needPadding) {
            this.mNeedPadding = needPadding;
            return this;
        }

        public Builder setColorArray(int[] colorArray) {
            this.colorArray = colorArray;
            return this;
        }

        public Controller build() {
            return new Controller(this);
        }
    }
}
