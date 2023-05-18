package github.eekidu.dev.devlayout.child;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSeekBar;

/**
 * @author caohk
 * @date 2022/4/18
 */
public class SeekBarLayout extends LinearLayout {

    private TextView mTitleTv;
    private TextView mValueTv;
    private AppCompatSeekBar mSeekBar;
    private SeekBar.OnSeekBarChangeListener mOnSeekBarChangeListener;

    public SeekBarLayout(Context context) {
        this(context, null);
    }

    public SeekBarLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SeekBarLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setGravity(Gravity.CENTER_VERTICAL);

        mTitleTv = new TextView(getContext());
        addView(mTitleTv);

        mValueTv = new TextView(getContext());
        mValueTv.setGravity(Gravity.CENTER_VERTICAL | Gravity.END);


        mSeekBar = new AppCompatSeekBar(getContext());
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mValueTv.setText(String.valueOf(progress) + "/" + seekBar.getMax());
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
        addView(mValueTv, new LayoutParams(200, 100));


        mValueTv.setText(mSeekBar.getProgress() + "/" + mSeekBar.getMax());
    }

    /**
     * 设置回调
     *
     * @param onSeekBarChangeListener
     */
    public void setOnSeekBarChangeListener(SeekBar.OnSeekBarChangeListener onSeekBarChangeListener) {
        mOnSeekBarChangeListener = onSeekBarChangeListener;
    }

    public TextView getTitleTv() {
        return mTitleTv;
    }

    public AppCompatSeekBar getSeekBar() {
        return mSeekBar;
    }

    public TextView getValueTv() {
        return mValueTv;
    }
}
