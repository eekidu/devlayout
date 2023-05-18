package github.eekidu.dev.devlayout;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;

import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayout;

import github.eekidu.dev.devlayout.child.EditorTextLayout;
import github.eekidu.dev.devlayout.child.SeekBarLayout;

/**
 * 快速添加测试按钮的布局
 *
 * @author caohk
 * @date 2021/10/12
 */
public class DevLayout extends LinearLayout {
    private FlexboxLayout mFlexboxLayout;

    public DevLayout(Context context) {
        this(context, null);
    }

    public DevLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public DevLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);
        mFlexboxLayout = new FlexboxLayout(context);
        mFlexboxLayout.setFlexWrap(FlexWrap.WRAP);
        addView(mFlexboxLayout);
    }

    public DevLayout addAction(String actionName, OnClickListener onClickListener) {
        Button button = new Button(getContext());
        button.setMinHeight(0);
        button.setMinWidth(0);
        button.setText(actionName);
        button.setOnClickListener(onClickListener);
        mFlexboxLayout.addView(button, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return this;
    }

    public DevLayout addSwitch(String actionName, CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
        return addSwitch(actionName, onCheckedChangeListener, false);
    }

    public DevLayout addSwitch(String actionName, CompoundButton.OnCheckedChangeListener onCheckedChangeListener, boolean defaultCheck) {
        SwitchCompat button = new SwitchCompat(getContext());
        button.setMinHeight(0);
        button.setMinWidth(0);
        button.setGravity(Gravity.CENTER);
        button.setText(actionName);
        int padding = (int) Resources.getSystem().getDisplayMetrics().density * 10;
        button.setPadding(padding, 0, padding, 0);
        button.setOnCheckedChangeListener(onCheckedChangeListener);
        if (defaultCheck) {
            button.setChecked(true);
        }
        mFlexboxLayout.addView(button, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return this;
    }

    public SeekBarLayout addSeekBar(String title) {
        return addSeekBar(title, null);
    }

    public SeekBarLayout addSeekBar(String title, SeekBar.OnSeekBarChangeListener onSeekBarChangeListener) {
        SeekBarLayout seekBarWrapper = new SeekBarLayout(getContext());
        seekBarWrapper.getTitleTv().setText(title);
        seekBarWrapper.setOnSeekBarChangeListener(onSeekBarChangeListener);

        mFlexboxLayout.addView(seekBarWrapper, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return seekBarWrapper;
    }


    public TextView addTextView(String title) {
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setGravity(Gravity.CENTER_VERTICAL);

        TextView titleTextView = new TextView(getContext());
        titleTextView.setText(title + ": ");
        linearLayout.addView(titleTextView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));


        TextView valueTextView = new TextView(getContext());
        valueTextView.setText("--");
        linearLayout.addView(valueTextView, new ViewGroup.LayoutParams(200, ViewGroup.LayoutParams.WRAP_CONTENT));
        mFlexboxLayout.addView(linearLayout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return valueTextView;
    }


    public EditorTextLayout addEditor(String title) {
        EditorTextLayout editorTextLayout = new EditorTextLayout(getContext());
        editorTextLayout.getTitleTextView().setText(title);
        mFlexboxLayout.addView(editorTextLayout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return editorTextLayout;
    }

}
