package com.github.eekidu.dev.devlayout.widget;

import android.content.Context;
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

import com.github.eekidu.dev.devlayout.DevLayout;
import com.github.eekidu.dev.devlayout.util.DevLayoutUtil;
import com.github.eekidu.dev.devlayout.util.ProxyListener;

import java.util.LinkedList;
import java.util.List;

/**
 * 支持左右的RadioGroup指示器
 *
 * @author caohk
 * @date 2021/7/23
 */
public class RadioGroupLayout extends HorizontalScrollView {

    private DevLayout mDevLayout;
    private String mTitle = "";


    public interface OnItemCheckListener {
        void onSelect();
    }

    public interface OnCheckedChangeListener {
        void onCheckedChange(int index, String tabName);
    }


    public static class RadioItem {
        private String title;
        private OnItemCheckListener mOnClickListener;

        public RadioItem(String title) {
            this.title = title;
        }

        public RadioItem(String title, OnItemCheckListener onClickListener) {
            this.title = title;
            mOnClickListener = onClickListener;
        }

        public void setOnCheckListener(OnItemCheckListener onClickListener) {
            mOnClickListener = onClickListener;
        }


        @NonNull
        @Override
        public String toString() {
            return title;
        }
    }

    private List<RadioItem> mBts;
    private int mSelectPosition = -1;

    //额外追加的滑动偏移，使边界的item点击后，能露出后面的一个。
    private int extraScrollX;
    private int itemGaps;


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
                                    mListener.onCheckedChange(i, mBts.get(i).title);
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
            addItem("Radio0");
            addItem("Radio1");
            addItem("Radio2");
            addItem("Radio3");
        }
    }

    public RadioGroupLayout setListener(OnCheckedChangeListener listener) {
        return setOnCheckedChangeListener(listener);
    }

    public RadioGroupLayout setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        DevLayout devLayout = DevLayoutUtil.getParentDevLayout(this);
        if (devLayout != null) {//代理点击事件
            mListener = ProxyListener.getProxy(devLayout, mTitle, OnCheckedChangeListener.class, listener);
        } else {
            mListener = listener;
        }
        return this;
    }


    List<RadioItem> mRadioItems = new LinkedList<>();

    public RadioGroupLayout addItem(String item) {
        return addItem(item, null);
    }

    public RadioGroupLayout addItem(String title, OnItemCheckListener onClickListener) {
        return addItem(new RadioItem(title, onClickListener));
    }

    public RadioGroupLayout addItem(RadioItem radioItem) {
        if (radioItem != null) {
            DevLayout devLayout = DevLayoutUtil.getParentDevLayout(this);
            if (devLayout != null && radioItem.mOnClickListener != null) {//代理点击事件
                radioItem.mOnClickListener = ProxyListener.getProxy(devLayout, radioItem.title, OnItemCheckListener.class, radioItem.mOnClickListener);
            }
            mRadioItems.add(radioItem);
        }
        bindData(mRadioItems);
        return this;
    }

    public void bindDevLayout(DevLayout devLayout, String title) {
        mDevLayout = devLayout;
        mTitle = title;
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
    public void bindData(List<RadioItem> bts) {
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


    public RadioGroupLayout setChecked(int position) {
        if (position >= 0 && position < mRadioGroup.getChildCount()) {
            int id = mRadioGroup.getChildAt(position).getId();
            mRadioGroup.check(id);
        }
        return this;
    }

    public int getCheckedPosition() {
        return mSelectPosition;
    }
}
