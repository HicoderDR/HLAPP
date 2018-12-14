package com.example.jsrgjhl.hlapp.View;

import android.content.Context;
import android.content.res.ColorStateList;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jsrgjhl.hlapp.R;

public class SegmentView extends LinearLayout {
    private TextView allTextView;
    private TextView leftTextView;
    private TextView midTextView;
    private TextView rightTextView;
    private onSegmentViewClickListener segmentListener;

    // 这是代码加载ui必须重写的方法
    public SegmentView(Context context) {
        super(context);
        initView();
    }

    // 这是在xml布局使用必须重写的方法
    public SegmentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        allTextView= new TextView(getContext());
        leftTextView = new TextView(getContext());
        midTextView= new TextView(getContext());
        rightTextView = new TextView(getContext());

        // 设置textview的布局宽高并设置为weight属性都为1
        allTextView.setLayoutParams(new LayoutParams(100, LayoutParams.WRAP_CONTENT, 1));
        leftTextView.setLayoutParams(new LayoutParams(100, LayoutParams.WRAP_CONTENT, 1));
        midTextView.setLayoutParams(new LayoutParams(100, LayoutParams.WRAP_CONTENT, 1));
        rightTextView.setLayoutParams(new LayoutParams(200, LayoutParams.WRAP_CONTENT, 2));

        // 初始化的默认文字
        allTextView.setText("所有");
        leftTextView.setText("监控");
        midTextView.setText("雷达");
        rightTextView.setText("振动传感");

        // 实现不同的按钮状态，不同的颜色
        ColorStateList csl = getResources().getColorStateList(R.color.segment_text_color_selector);
        allTextView.setTextColor(csl);
        leftTextView.setTextColor(csl);
        midTextView.setTextColor(csl);
        rightTextView.setTextColor(csl);

        // 设置textview的内容位置居中
        allTextView.setGravity(Gravity.CENTER);
        leftTextView.setGravity(Gravity.CENTER);
        midTextView.setGravity(Gravity.CENTER);
        rightTextView.setGravity(Gravity.CENTER);

        // 设置textview的内边距
        allTextView.setPadding(5, 6, 5, 6);
        leftTextView.setPadding(5, 6, 5, 6);
        midTextView.setPadding(5, 6, 5, 6);
        rightTextView.setPadding(5, 6, 5, 6);

        // 设置文字大小
        setSegmentTextSize(15);

        // 设置背景资源
        allTextView.setBackgroundResource(R.drawable.segment_all_background);
        leftTextView.setBackgroundResource(R.drawable.segment_left_background);
        midTextView.setBackgroundResource(R.drawable.segment_mid_background);
        rightTextView.setBackgroundResource(R.drawable.segment_right_background);

        // 默认左侧textview为选中状态
        allTextView.setSelected(true);

        // 加入textview
        this.removeAllViews();
        this.addView(allTextView);
        this.addView(leftTextView);
        this.addView(midTextView);
        this.addView(rightTextView);
        this.invalidate();//重新draw()

        allTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (allTextView.isSelected()) {
                    return;
                }
                allTextView.setSelected(true);
                leftTextView.setSelected(false);
                midTextView.setSelected(false);
                rightTextView.setSelected(false);
                if (segmentListener != null) {
                    segmentListener.onSegmentViewClick(allTextView, 0);
                }
            }
        });
        leftTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (leftTextView.isSelected()) {
                    return;
                }
                allTextView.setSelected(false);
                leftTextView.setSelected(true);
                midTextView.setSelected(false);
                rightTextView.setSelected(false);
                if (segmentListener != null) {
                    segmentListener.onSegmentViewClick(leftTextView, 1);
                }
            }
        });
        midTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (midTextView.isSelected()) {
                    return;
                }
                allTextView.setSelected(false);
                leftTextView.setSelected(false);
                midTextView.setSelected(true);
                rightTextView.setSelected(false);
                if (segmentListener != null) {
                    segmentListener.onSegmentViewClick(midTextView, 2);
                }
            }
        });

        rightTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rightTextView.isSelected()) {
                    return;
                }
                allTextView.setSelected(false);
                leftTextView.setSelected(false);
                midTextView.setSelected(false);
                rightTextView.setSelected(true);
                if (segmentListener != null) {
                    segmentListener.onSegmentViewClick(rightTextView, 3);
                }
            }
        });

    }

    /**
     * 设置字体大小
     *
     * @param dp
     */
    private void setSegmentTextSize(int dp) {
        allTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, dp);
        leftTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, dp);
        midTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, dp);
        rightTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, dp);
    }

    /**
     * 手动设置选中的状态
     *
     * @param i
     */
    public void setSelect(int i) {
        if (i == 0) {
            allTextView.setSelected(true);
            leftTextView.setSelected(false);
            midTextView.setSelected(false);
            rightTextView.setSelected(false);
        } else if(i==1){
            allTextView.setSelected(false);
            leftTextView.setSelected(true);
            midTextView.setSelected(false);
            rightTextView.setSelected(false);
        }
        else if(i==2){
            allTextView.setSelected(false);
            leftTextView.setSelected(false);
            midTextView.setSelected(true);
            rightTextView.setSelected(false);
        }
        else{
            allTextView.setSelected(false);
            leftTextView.setSelected(false);
            midTextView.setSelected(false);
            rightTextView.setSelected(true);
        }
    }

    /**
     * 设置控件显示的文字
     *
     * @param text
     * @param position
     */
    public void setSegmentText(CharSequence text, int position) {
        if (position == 0) {
            allTextView.setText(text);
        }
        if (position == 1) {
            leftTextView.setText(text);
        }
        if (position == 2) {
            midTextView.setText(text);
        }
        if (position == 3) {
            rightTextView.setText(text);
        }
    }

    // 定义一个接口接收点击事件
    public interface onSegmentViewClickListener {
        public void onSegmentViewClick(View view, int postion);
    }

    public void setOnSegmentViewClickListener(onSegmentViewClickListener segmentListener) {
        this.segmentListener = segmentListener;
    }
}