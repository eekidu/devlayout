package com.github.eekidu.dev.devlayout.child;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.eekidu.dev.devlayout.util.DevLayoutUtil;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 支持左右的RadioGroup指示器
 *
 * @author caohk
 * @date 2021/7/23
 */
public class RadioGroupLayout extends HorizontalScrollView {
    public interface OnItemCheckListener {
        void onSelect();
    }


    public static class RadioItem {
        private String title;
        private OnItemCheckListener mOnClickListener;

        public RadioItem(String title) {
            this.title = title;
        }

        public void setOnCheckListener(OnItemCheckListener onClickListener) {
            mOnClickListener = onClickListener;
        }

        public RadioItem(String title, OnItemCheckListener onClickListener) {
            this.title = title;
            mOnClickListener = onClickListener;
        }

        @NonNull
        @Override
        public String toString() {
            return title;
        }
    }

    private List<?> mBts;
    private int mSelectPosition = -1;

    //额外追加的滑动偏移，使边界的item点击后，能露出后面的一个。
    private int extraScrollX;
    private int itemGaps;


    public interface OnCheckedChangeListener {
        void onCheckedChange(int index, Object tabName);
    }

    private RadioGroup mRadioGroup;
    private OnCheckedChangeListener mListener;

    public RadioGroupLayout(Context context) {
        this(context, null);
    }

    public RadioGroupLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RadioGroupLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        itemGaps = DisplayUtils.dip2px(10);
        mRadioGroup = new RadioGroup(context);
        mRadioGroup.setOrientation(LinearLayout.HORIZONTAL);
        mRadioGroup.setGravity(Gravity.CENTER_VERTICAL);
        addView(mRadioGroup, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        extraScrollX = DisplayUtils.dip2px(50);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View radioItem = group.findViewById(checkedId);
                if (radioItem != null) {
                    int left = radioItem.getLeft();
                    int right = radioItem.getRight();
                    int scrollX = getScrollX();
                    if (left < scrollX) {
                        int scrollByX = scrollX - left + extraScrollX;
                        smoothScrollBy(-scrollByX, 0);
                    } else if (right > scrollX + getMeasuredWidth()) {
                        int scrollByX = right - scrollX - getMeasuredWidth() + getPaddingRight() * 2 + extraScrollX;
                        smoothScrollBy(scrollByX, 0);
                    }
                }

                if (mListener != null || !mRadioItems.isEmpty()) {
                    for (int i = 0; i < group.getChildCount(); i++) {
                        if (group.getChildAt(i).getId() == checkedId) {
                            if (i != mSelectPosition) {
                                if (mListener != null) {
                                    mListener.onCheckedChange(i, mBts.get(i));
                                }
                                if (mRadioItems.get(i).mOnClickListener != null) {
                                    mRadioItems.get(i).mOnClickListener.onSelect();
                                }
                                mSelectPosition = i;
                            }
                            break;
                        }
                    }
                }

            }
        });
        if (isInEditMode()) {
            List<Object> list = new ArrayList<>();
            list.add("Radio0");
            list.add("Radio1");
            list.add("Radio2");
            list.add("Radio3");
            list.add("Radio4");
            list.add("Radio5");
            list.add("Radio6");
            list.add("Radio7");
            bindData(list);
        }
    }

    public void setListener(OnCheckedChangeListener listener) {
        mListener = listener;
    }

    List<RadioItem> mRadioItems = new LinkedList<>();

    public void addItem(RadioItem radioItem) {
        if (radioItem != null) {
            mRadioItems.add(radioItem);
        }
        bindData(mRadioItems);
    }

    public RadioItem addItem(String item) {
        RadioItem e = new RadioItem(item, null);
        mRadioItems.add(e);
        bindData(mRadioItems);
        return e;
    }

    public void setRadioGroupGravity() {
        if (mRadioGroup != null) {
            ViewGroup.LayoutParams layoutParams = mRadioGroup.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            if (layoutParams instanceof LayoutParams) {
                ((LayoutParams) layoutParams).gravity = Gravity.CENTER;
            }
            mRadioGroup.setGravity(Gravity.CENTER);
            mRadioGroup.setLayoutParams(layoutParams);
        }
    }


    //    @StyleRes int radioBtStyle
    public void bindData(List<?> bts) {
        mBts = bts;
        mRadioGroup.removeAllViews();
        if (bts != null)
            for (int i = 0; i < bts.size(); i++) {
                RadioButton radioButton = new RadioButton(getContext());
                radioButton.setClickable(true);
                radioButton.setText(bts.get(i).toString());
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                if (i != bts.size() - 1) {
                    layoutParams.rightMargin = itemGaps;
                }
                layoutParams.weight = 1;
                mRadioGroup.addView(radioButton, layoutParams);
            }
    }


    public void setChecked(int position) {
        if (position >= 0 && position < mRadioGroup.getChildCount()) {
            int id = mRadioGroup.getChildAt(position).getId();
            mRadioGroup.check(id);
        }
    }

    public int getCheckedPosition() {
        return mSelectPosition;
    }
}
