package com.byk.chartlib.data;

import android.view.MotionEvent;

import com.byk.chartlib.bean.ChartPoint;
import com.byk.chartlib.bean.Entry;
import com.byk.chartlib.bean.IEntry;
import com.byk.chartlib.utils.Tranformer;
import com.byk.chartlib.utils.ViewHandler;

public class DataProvider<T extends IEntry> implements IDataProvider<T> {

    /**
     * 开始位置
     */
    private int mStartPosition = 0;

    /**
     * 结束位置
     */
    private int mEndPosition = 19;

    /**
     * 最小显示数量
     */
    private int mMinDisplayCount = 5;

    /**
     * 最大显示数量
     */
    private int mMaxDisplayCount = 100;


    /**
     * 现实最大值
     */
    private float mMaxValue;

    /**
     * 显示数据最小值
     */
    private float mMinValue;

    private float mValueFactor = 0.02f;

    /**
     * 数据集合
     */
    private DataSet<T>[] mDataSets;


    private ScaleType mScaleType = ScaleType.RIGHT;


    private Tranformer mTranformer;

    /**
     * 手指触摸坐标标点
     */
    private ChartPoint mChartPoint;

    /**
     * 是否在长按状态
     */
    private boolean mIsLongPress = false;

    /**
     * 是否是正在
     */
    private boolean mIsTouchView = false;

    /**
     * x轴十字是否跟随手指
     */
    private boolean mIsFollowFinger = true;

    /**
     * 是否为主图
     */
    private boolean mIsMaster = false;

    private boolean mIsDisplayYMarker = false;


    @Override
    public DataSet<T>[] getDataSet() {
        return mDataSets;
    }

    @Override
    public void setDataSet(DataSet<T>... dataSets) {
        this.mDataSets = dataSets;
    }

    @Override
    public int getCurrentDisplayCount() {
        return (mEndPosition - mStartPosition + 1);
    }

    @Override
    public int getStartPosition() {
        return mStartPosition;
    }

    @Override
    public void setStartPosition(int startPosition) {
        this.mStartPosition = startPosition;
    }

    @Override
    public int getEndPosition() {
        return mEndPosition;
    }

    @Override
    public void setEndPosition(int endPosition) {
        this.mEndPosition = endPosition;
    }

    public float getMaxValue() {
        return mMaxValue;
    }

    public float getMinValue() {
        return mMinValue;
    }

    public Tranformer getTranformer() {
        return mTranformer;
    }

    public void setTranformer(Tranformer mTranformer) {
        this.mTranformer = mTranformer;
    }

    public ChartPoint getChartPoint() {
        return mChartPoint;
    }

    public void setChartPoint(float x,float y) {
        if (mChartPoint == null){
            mChartPoint = new ChartPoint();
        }
        mChartPoint.setxPx(x);
        mChartPoint.setyPx(y);

        float[] pts = new float[]{x,y};
        mTranformer.convertPixelToValues(pts);

        ///将像素点转换为值点
        x = Math.min(Math.max(pts[0],getStartPosition()),getEndPosition());
        y = Math.min(Math.max(pts[1],getMinValue()),getMaxValue());

        x = (float) Math.floor(x);

        if (mChartPoint == null){
            mChartPoint = new ChartPoint(x,y);
        }else {
            mChartPoint.set(x,y);
        }
    }

    public boolean isLongPress() {
        return mIsLongPress;
    }

    public void setLongPress(boolean isLongPress) {
        this.mIsLongPress = isLongPress;
    }

    public boolean isFollowFinger() {
        return mIsFollowFinger;
    }

    public void setFollowFinger(boolean followFinger) {
        this.mIsFollowFinger = followFinger;
    }


    public boolean isTouchView() {
        return mIsTouchView;
    }

    public void setTouchView(boolean mIsTouchView) {
        this.mIsTouchView = mIsTouchView;
    }

    public boolean isMaster() {
        return mIsMaster;
    }

    public void setMaster(boolean mIsMaster) {
        this.mIsMaster = mIsMaster;
    }

    public float getItemWidth(){
        ViewHandler viewHandler = mTranformer.getViewHandler();

        float contentWidth = viewHandler.getContentWidth();
        return contentWidth/getCurrentDisplayCount();
    }


    /**
     * 同步数据
     */
    public void snycData(DataProvider provider){

        setStartPosition(provider.getStartPosition());
        setEndPosition(provider.getEndPosition());
        setLongPress(provider.isLongPress());

        if (provider.isLongPress()){
            ChartPoint chartPoint = provider.getChartPoint();
            setTouchView(false);
            setChartPoint(chartPoint.xPx,chartPoint.yPx);
        }

    }

    /**
     * 获取数据长度
     *
     * @return
     */
    public int getLength() {
        int count = 0;

        int length = mDataSets.length;

        for (int i = 0; i < length; i++) {
            DataSet dataSet = mDataSets[i];

            //查找最大的项作为数据长度
            int size = dataSet.getEntry().size();
            if (size > count) {
                count = size;
            }
        }

        return count;
    }

    /**
     * 计算显示位置的最大值最小值
     */
    public void calculate(){
        int length = mDataSets.length;
        mMaxValue = Float.MIN_VALUE;
        mMinValue = Float.MAX_VALUE;

        for (int i = 0; i < length; i++) {
            DataSet dataSet = mDataSets[i];

            if (dataSet.isCalcuLimit()){
                float max = dataSet.getCalculateMax(mStartPosition,mEndPosition);
                float min = dataSet.getCalculateMin(mStartPosition,mEndPosition);

                if (mMaxValue < max){
                    mMaxValue = max;
                }

                if (mMinValue > min){
                    mMinValue = min;
                }
            }
        }

        //系数放大
        mMaxValue = mMaxValue* (1f + mValueFactor);
        mMinValue = mMinValue*(1f - mValueFactor);
    }

    /**
     * 平移
     *
     * @param count
     */
    public int translation(int count) {
        int mCurrentDisplayCount = getCurrentDisplayCount();

        int old = mStartPosition;
        mStartPosition = mStartPosition + count;

        if (mStartPosition < 0) {
            mStartPosition = 0;
        }

        int length = getLength();
        int temp = length - mCurrentDisplayCount;

        if (mStartPosition > temp){
            mStartPosition = Math.max(temp, 0);
        }

        mEndPosition = mStartPosition + mCurrentDisplayCount - 1;

        System.out.println("-------------mStartPosition:"+mStartPosition+"|"+mEndPosition+"---------|"+count);

        return old - mStartPosition;
    }

    /**
     * 放缩
     *
     * @param count
     */
    public int scale(int count) {
        int unconsume = 0;
        switch (mScaleType) {
            case LEFT: {//以左侧为基准
                unconsume = rightScale(count);

                //未消费数量大于0,说明已经是最小显示数量，小于零说明为最大显示数量，为0表示已全部消费
                unconsume = leftScale(unconsume);

            }
            break;
            case RIGHT: { //以右侧为基准
                unconsume = leftScale(count);

                //未消费数量大于0,说明已经是最小显示数量，小于零说明为最大显示数量，为0表示已全部消费
                unconsume = rightScale(unconsume);
            }
            break;
            case CENTER: {
                int half = count / 2;

                int unconsumeLeft = leftScale(half);
                int unconsumeRight = rightScale(count - half);

                if (Math.abs(unconsumeLeft) > 0 && unconsumeRight == 0) {
                    unconsumeLeft = rightScale(unconsumeLeft);
                }

                if (Math.abs(unconsumeRight) > 0 && unconsumeLeft == 0) {
                    unconsumeRight = leftScale(unconsumeRight);
                }
                unconsume = unconsumeLeft + unconsumeRight;
            }
            break;
        }

        System.out.println("----------------" + getCurrentDisplayCount() +"|"+mStartPosition + "|"+mEndPosition);

        return count - unconsume;
    }

    /**
     * 向左放缩消费
     *
     * @param count 最多消费数量
     * @return 未消费数量
     */
    private int leftScale(int count) {
        int old = mStartPosition;

        int willDisplapCount = getCurrentDisplayCount() + count;

        if (willDisplapCount < mMinDisplayCount) {
            willDisplapCount = mMinDisplayCount;
        }

        if (willDisplapCount > mMaxDisplayCount) {
            willDisplapCount = mMaxDisplayCount;
        }

        //数组越界后，向左增加显示
        mStartPosition = mEndPosition - willDisplapCount + 1;

        if (mStartPosition < 0) {
            mStartPosition = 0;
        }

        int remain = count - (old - mStartPosition);

        return remain;
    }

    /**
     * 向右放缩消费
     *
     * @param count 最多消费数量
     * @return 未消费数量
     */
    private int rightScale(int count) {
        int old = mEndPosition;
        int willDisplapCount = getCurrentDisplayCount() + count;

        if (willDisplapCount < mMinDisplayCount) {
            willDisplapCount = mMinDisplayCount;
        }

        if (willDisplapCount > mMaxDisplayCount) {
            willDisplapCount = mMaxDisplayCount;
        }

        int length = getLength();
        //数组越界后，向左增加显示
        mEndPosition = mStartPosition + willDisplapCount - 1;

        if (mEndPosition >= length) {
            mEndPosition = length - 1;
        }

        int remain = count - (mEndPosition - old);

        return remain;
    }
}
