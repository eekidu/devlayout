package com.github.eekidu.dev.devlayout.widget;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.github.eekidu.dev.devlayout.util.DevLayoutUtil;

/**
 * @author caohk
 * @date 2023/6/7
 */
public class TitleAndDescLayout extends LinearLayout {

    private TextView mTitleTv;
    private TextView mDescTv;

    public TitleAndDescLayout(Context context) {
        this(context, null);
    }

    public TitleAndDescLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleAndDescLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);

        mTitleTv = DevLayoutUtil.generateTitleTv(getContext());
        mTitleTv.setTextSize(18);
        mTitleTv.setVisibility(GONE);
        addView(mTitleTv, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        mDescTv = new AppCompatTextView(getContext());
        mDescTv.setTextColor(Color.GRAY);
        mDescTv.setVisibility(GONE);
        addView(mDescTv, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    public TitleAndDescLayout setTitle(CharSequence title) {
        mTitleTv.setText(title);
        mTitleTv.setVisibility(TextUtils.isEmpty(title) ? GONE : VISIBLE);
        return this;
    }

    public TitleAndDescLayout setDesc(CharSequence value) {
        mDescTv.setText(value);
        mDescTv.setVisibility(TextUtils.isEmpty(value) ? GONE : VISIBLE);
        return this;
    }


    public TextView getTitleTv() {
        return mTitleTv;
    }

    public TextView getDescTv() {
        return mDescTv;
    }


}
