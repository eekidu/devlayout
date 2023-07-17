package com.github.eekidu.dev.devlayout.child;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.github.eekidu.dev.devlayout.util.DevLayoutUtil;

/**
 * @author caohk
 * @date 2022/4/18
 */
public class SeekBarLayout extends LinearLayout {

    private Button mMinusBt;
    private Button mPlusBt;

    public interface OnProgressChangeListener {
        void onProgressChanged(int progress);
    }

    private TextView mTitleTv;
    private TextView mValueTv;
    private SeekBar mSeekBar;
    private SeekBar.OnSeekBarChangeListener mOnSeekBarChangeListener;

    private OnProgressChangeListener mProgressListener;

    public SeekBarLayout(Context context) {
        this(context, null);
    }

    public SeekBarLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SeekBarLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setGravity(Gravity.CENTER_VERTICAL);

        mTitleTv = DevLayoutUtil.generateTitleTv(getContext());
        addView(mTitleTv);

        mValueTv = new TextView(getContext());
        mValueTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.END);
        mValueTv.setMaxLines(2);


        mSeekBar = new SeekBar(getContext());
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mValueTv.setText(String.valueOf(progress) + "/" + seekBar.getMax());
                if (mProgressListener != null) {
                    mProgressListener.onProgressChanged(progress);
                }
                if (mOnSeekBarChangeListener != null) {
                    mOnSeekBarChangeListener.onProgressChanged(seekBar, progress, fromUser);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                if (mOnSeekBarChangeListener != null) {
                    mOnSeekBarChangeListener.onStartTrackingTouch(seekBar);
                }
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (mOnSeekBarChangeListener != null) {
                    mOnSeekBarChangeListener.onStopTrackingTouch(seekBar);
                }
            }
        });
        LayoutParams params = new LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.weight = 1;
        addView(mSeekBar, params);
        mValueTv.setMinHeight(DevLayoutUtil.dp2px(30));
        addView(mValueTv, new LayoutParams(DevLayoutUtil.dp2px(60), ViewGroup.LayoutParams.WRAP_CONTENT));

        mMinusBt = new Button(getContext());
        mMinusBt.setText("-");
        addView(mMinusBt, new LayoutParams(DevLayoutUtil.dp2px(30), ViewGroup.LayoutParams.WRAP_CONTENT));
        mMinusBt.setOnClickListener(v -> {
            int min = 0;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                min = mSeekBar.getMin();
            }
            mSeekBar.setProgress(Math.max(mSeekBar.getProgress() - 1, min));
        });


//        mPlusBt = new Button(new ContextThemeWrapper(getContext(), android.R.style.Widget_Material_ButtonBar));
        mPlusBt = new Button(getContext());
        mPlusBt.setText("+");
        mPlusBt.setLayoutParams(new LinearLayout.LayoutParams(DevLayoutUtil.dp2px(30), ViewGroup.LayoutParams.WRAP_CONTENT));
        addView(mPlusBt);
        mPlusBt.setOnClickListener(v -> {
            mSeekBar.setProgress(Math.min(mSeekBar.getProgress() + 1, mSeekBar.getMax()));
        });

        setEnableStep(false);

        mValueTv.setText(mSeekBar.getProgress() + "/" + mSeekBar.getMax());

        if (isInEditMode()) {
            setBackgroundColor(Color.WHITE);
            mTitleTv.setText("标题");
        }
    }

    /**
     * 设置启用步进步减按钮
     *
     * @param b
     */
    public SeekBarLayout setEnableStep(boolean b) {
        if (b) {
            mMinusBt.setVisibility(VISIBLE);
            mPlusBt.setVisibility(VISIBLE);
        } else {
            mMinusBt.setVisibility(GONE);
            mPlusBt.setVisibility(GONE);
        }
        return this;
    }


    public SeekBarLayout setMax(int max) {
        mSeekBar.setMax(max);
        return this;
    }

    public SeekBarLayout setProgress(int progress) {
        mSeekBar.setProgress(progress);
        return this;
    }

    /**
     * 设置进度回调
     */
    public SeekBarLayout setOnProgressChangeListener(OnProgressChangeListener progressListener) {
        mProgressListener = progressListener;
        return this;
    }

    /**
     * 设置原生全回调
     * 仅监听进度, 可以使用{@link #setOnProgressChangeListener(OnProgressChangeListener)}简化
     *
     * @param onSeekBarChangeListener
     */
    public SeekBarLayout setOnSeekBarChangeListener(SeekBar.OnSeekBarChangeListener onSeekBarChangeListener) {
        mOnSeekBarChangeListener = onSeekBarChangeListener;
        return this;
    }


    public TextView getTitleTv() {
        return mTitleTv;
    }

    public SeekBar getSeekBar() {
        return mSeekBar;
    }

    public TextView getValueTv() {
        return mValueTv;
    }


}
