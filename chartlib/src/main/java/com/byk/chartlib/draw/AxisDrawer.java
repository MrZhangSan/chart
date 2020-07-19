package com.byk.chartlib.draw;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;

import com.byk.chartlib.data.DataProvider;
import com.byk.chartlib.format.ValueFormatter;

/**
 * 绘制网格线
 */
public abstract class AxisDrawer extends BaseDrawer {

    protected ValueFormatter mValueFormatter;

    protected Paint mGridLinePaint;
    protected TextPaint mTextPaint;

    /**
     * 网格线数量
     */
    protected int mGridLinesCount ;

    /**
     * 网格线宽度
     */
    protected float mGridLinesWidth;

    /**
     * 网格线颜色
     */
    protected int mGridLinesColor;

    /**
     * 文字颜色
     */
    protected int mTextColor = 0xffff0000;

    /**
     * 字体大小
     */
    protected int mTextSize = 30;

    /**
     * 是否绘制
     */
    private boolean mGridLinesEnable = true;


    private boolean mTextEnable = true;

    /**
     * 是否显示
     */
    protected OutLine mOutLine = OutLine.NONE;


    public enum OutLine{
        NONE,LEFT,RIGHT,LEFT_RIGHT,TOP,BOTTOM,TOP_BOTTOM
    }

    protected TextPosition mTextPosition = TextPosition.BOTH_INSIDE;

    public enum TextPosition{
        NONE,LEFT_OUTSIDE,LEFT_INSIDE,BOTH_OUTSIDE,BOTH_INSIDE,RIGHT_OUTSIDE,RIGHT_INSID
    }

    public AxisDrawer(DataProvider mDataProvider) {
        super(mDataProvider);
        mGridLinePaint = new Paint();
        mTextPaint = new TextPaint();
        mValueFormatter = new ValueFormatter();
    }

    public int getGridLinesCount() {
        return mGridLinesCount;
    }

    public void setGridLinesCount(int mGridLinesCount) {
        this.mGridLinesCount = mGridLinesCount;
    }

    public float getGridLinesWidth() {
        return mGridLinesWidth;
    }

    public void setGridLinesWidth(float mGridLinesWidth) {
        this.mGridLinesWidth = mGridLinesWidth;
    }

    public int getGridLinesColor() {
        return mGridLinesColor;
    }

    public void setGridLinesColor(int mGridLinesColor) {
        this.mGridLinesColor = mGridLinesColor;
    }

    public boolean isGridLinesEnable() {
        return mGridLinesEnable;
    }

    public void setGridLinesEnable(boolean mGridLinesEnable) {
        this.mGridLinesEnable = mGridLinesEnable;
    }

    public int getTextColor() {
        return mTextColor;
    }

    public void setTextColor(int mTextColor) {
        this.mTextColor = mTextColor;
    }

    public int getTextSize() {
        return mTextSize;
    }

    public void setTextSize(int mTextSize) {
        this.mTextSize = mTextSize;
    }

    public boolean isTextEnable() {
        return mTextEnable;
    }

    public void setTextEnable(boolean mTextEnable) {
        this.mTextEnable = mTextEnable;
    }

    public TextPosition getTextPosition() {
        return mTextPosition;
    }

    public void setTextPosition(TextPosition mTextPosition) {
        this.mTextPosition = mTextPosition;
    }

    public OutLine getOutLine() {
        return mOutLine;
    }

    public void setOutLine(OutLine mOutLine) {
        this.mOutLine = mOutLine;
    }

    public ValueFormatter getValueFormatter() {
        return mValueFormatter;
    }

    /**
     * 绘制网格
     * @param canvas
     */
    public abstract void drawGridLines(Canvas canvas);

    /**
     * 绘制文字
     * @param canvas
     */
    public abstract void drawTexts(Canvas canvas);
}
