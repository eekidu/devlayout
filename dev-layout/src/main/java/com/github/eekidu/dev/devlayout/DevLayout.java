package com.github.eekidu.dev.devlayout;

import android.content.Context;
import android.os.Parcel;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.widget.NestedScrollView;

import com.github.eekidu.dev.devlayout.util.DevLayoutUtil;
import com.github.eekidu.dev.devlayout.util.ListenerDelegator;
import com.github.eekidu.dev.devlayout.widget.EditorTextLayout;
import com.github.eekidu.dev.devlayout.widget.LogMonitorLayout;
import com.github.eekidu.dev.devlayout.widget.RadioGroupLayout;
import com.github.eekidu.dev.devlayout.widget.SeekBarLayout;
import com.github.eekidu.dev.devlayout.widget.TitleAndDescLayout;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayout;

import java.lang.reflect.Constructor;

import github.eekidu.dev.devlayout.R;

/**
 * 使用代码的方式，快速添加常用调试控件，无需XML，简化调试页面开发过程
 *
 * @author caohk
 * @date 2021/10/12
 */
public class DevLayout extends NestedScrollView {
    public static final String TAG = "DevLayout";

    private final FlexboxLayout mFlexboxLayout;
    private boolean mIsLineStyleFlag;

    public DevLayout(Context context) {
        this(context, null);
    }

    public DevLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DevLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        int padding = DevLayoutUtil.dp2px(2);
        setPadding(padding, padding, padding, padding);

        mFlexboxLayout = new FlexboxLayout(context);
        mFlexboxLayout.setFlexWrap(FlexWrap.WRAP);
        addView(mFlexboxLayout);
        if (isInEditMode()) {
            addTitleAndDesc("DevLayout", "使用代码的方式，快速添加常用调试控件，无需XML，简化调试页面开发过程");
            addLine();
            addAction("测试", v -> {
            });
        }
    }

    public DevLayout addAction(String actionName, OnClickListener onClickListener) {
        addButton(actionName, onClickListener);
        return this;
    }


    public Button addButton(String title, OnClickListener onClickListener) {
        Button button = new Button(getContext());
        button.setAllCaps(false);
        button.setText(title);
        button.setOnClickListener(ListenerDelegator.getDelegator(this, title, OnClickListener.class, onClickListener));
        mFlexboxLayout.addView(button, new LayoutParamOr(getWidthParam(), ViewGroup.LayoutParams.WRAP_CONTENT));
        return button;
    }

    public Button addFullButton(String title, OnClickListener onClickListener) {
        Button button = new Button(getContext());
        button.setAllCaps(false);
        button.setText(title);
        button.setOnClickListener(ListenerDelegator.getDelegator(this, title, OnClickListener.class, onClickListener));
        mFlexboxLayout.addView(button, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return button;
    }


    /**
     * @param desc
     * @param btTitle
     * @param onClickListener
     */
    public LinearLayout addDescribeAndButton(String desc, String btTitle, OnClickListener onClickListener) {
        LinearLayout textviewAndButton = new LinearLayout(getContext());

        TextView titleTextView = new TextView(getContext());
        titleTextView.setText(desc);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.weight = 1;
        textviewAndButton.addView(titleTextView, params);

        Button button = new Button(getContext());
        button.setAllCaps(false);
        button.setText(btTitle);
        button.setOnClickListener(ListenerDelegator.getDelegator(this, btTitle, OnClickListener.class, onClickListener));
        textviewAndButton.addView(button, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        mFlexboxLayout.addView(textviewAndButton, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return textviewAndButton;
    }

    /**
     * 添加标题与描述
     *
     * @param title
     * @param desc
     * @return
     */
    public TitleAndDescLayout addTitleAndDesc(@Nullable String title, @Nullable String desc) {
        TitleAndDescLayout titleAndDescLayout = new TitleAndDescLayout(getContext());
        titleAndDescLayout.setTitle(title);
        titleAndDescLayout.setDesc(desc);
        mFlexboxLayout.addView(titleAndDescLayout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return titleAndDescLayout;
    }

    public TitleAndDescLayout addTitleBar(@Nullable String title) {
        return addTitleAndDesc(title, null);
    }


    public SwitchCompat addSwitch(String actionName, CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
        return addSwitch(actionName, onCheckedChangeListener, false);
    }

    public SwitchCompat addSwitch(String title, CompoundButton.OnCheckedChangeListener onCheckedChangeListener, boolean defaultCheck) {
        SwitchCompat aSwitch = new SwitchCompat(getContext());
        aSwitch.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
        aSwitch.setText(title);
        int padding = DevLayoutUtil.dp2px(10);
        aSwitch.setPadding(padding, 0, padding, 0);
        aSwitch.setOnCheckedChangeListener(ListenerDelegator.getDelegator(this, title, CompoundButton.OnCheckedChangeListener.class, onCheckedChangeListener));
        if (defaultCheck) {
            aSwitch.setChecked(true);
        }
        mFlexboxLayout.addView(aSwitch, new LayoutParamOr(getWidthParam(), ViewGroup.LayoutParams.WRAP_CONTENT));
        return aSwitch;
    }

    public CheckBox addCheckBox(String title, CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
        CheckBox checkBox = new CheckBox(getContext());
        checkBox.setText(title);
        checkBox.setOnCheckedChangeListener(ListenerDelegator.getDelegator(this, title, CompoundButton.OnCheckedChangeListener.class, onCheckedChangeListener));
        checkBox.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
        mFlexboxLayout.addView(checkBox, new LayoutParamOr(getWidthParam(), ViewGroup.LayoutParams.WRAP_CONTENT));
        return checkBox;
    }


    public SeekBarLayout addSeekBar(String title) {
        return addSeekBar(title, null);
    }

    public SeekBarLayout addSeekBar(String title, SeekBarLayout.OnProgressChangeListener onProgressChangeListener) {
        SeekBarLayout seekBarLayout = new SeekBarLayout(getContext());
        mFlexboxLayout.addView(seekBarLayout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        seekBarLayout.getTitleTv().setText(title);
        seekBarLayout.setOnProgressChangeListener(onProgressChangeListener);
        return seekBarLayout;
    }


    /**
     * 用于固定展示信息
     *
     * @param title
     * @return Value TextView
     */
    public TextView addTextView(String title) {
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setGravity(Gravity.CENTER_VERTICAL);

        TextView titleTextView = DevLayoutUtil.generateTitleTv(getContext());
        titleTextView.setText(title + ": ");
        linearLayout.addView(titleTextView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        TextView valueTextView = new TextView(getContext());
        valueTextView.setHint("待展示文本");
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params1.weight = 1;
        linearLayout.addView(valueTextView, params1);

        FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mFlexboxLayout.addView(linearLayout, params);
        return valueTextView;
    }


    /**
     * 添加编辑框
     *
     * @param title
     * @return
     */
    public EditorTextLayout addEditor(String title) {
        return addEditor(title, null);
    }

    public EditorTextLayout addEditor(String title, EditorTextLayout.OnSureClickListener onSureClickListener) {
        EditorTextLayout editorTextLayout = new EditorTextLayout(getContext());
        editorTextLayout.setOnSureClickListener(onSureClickListener);
        editorTextLayout.setOnSureClickListener(ListenerDelegator.getDelegator(this, title, EditorTextLayout.OnSureClickListener.class, onSureClickListener));

        editorTextLayout.getTitleTextView().setText(title);
        mFlexboxLayout.addView(editorTextLayout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return editorTextLayout;
    }

    /**
     * 添加单选框
     *
     * @return
     */
    public RadioGroupLayout addRadioGroup() {
        return addRadioGroup(null);
    }

    /**
     * 添加单选框
     *
     * @return
     */
    public RadioGroupLayout addRadioGroup(String title) {
        RadioGroupLayout radioGroup = new RadioGroupLayout(getContext());
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setGravity(Gravity.CENTER_VERTICAL);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        if (title != null) {
            TextView textView = DevLayoutUtil.generateTitleTv(getContext());
            textView.setText(title + ":");
            linearLayout.addView(textView);
        }
        radioGroup.bindDevLayout(this, title);
        linearLayout.addView(radioGroup, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mFlexboxLayout.addView(linearLayout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return radioGroup;
    }

    public <T extends View> T addViewByClass(Class<T> viewClass) {
        return this.addViewByClass(viewClass, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    public <T extends View> T addViewByClass(Class<T> viewClass, ViewGroup.LayoutParams layoutParams) {
        try {
            Constructor<? extends View> declaredConstructor = viewClass.getDeclaredConstructor(Context.class);
            View view = declaredConstructor.newInstance(getContext());
            mFlexboxLayout.addView(view, layoutParams);
            return (T) view;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    //<editor-folder desc="日志相关">
    private LogMonitorLayout mLogMonitorLayout;

    /**
     * 添加日志框
     *
     * @return
     */
    public LogMonitorLayout addLogMonitor() {
        if (mLogMonitorLayout == null) {
            mLogMonitorLayout = new LogMonitorLayout(getContext());
            mFlexboxLayout.addView(mLogMonitorLayout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        } else {
            Log.w(TAG, "Only one can be add!");
        }
        return mLogMonitorLayout;
    }

    /**
     * 添加日志框(小尺寸)
     *
     * @return
     */
    public LogMonitorLayout addLogMonitorSmall() {
        LogMonitorLayout logMonitorLayout = addLogMonitor();
        logMonitorLayout.changeSizeTo(DevLayoutUtil.dp2px(120));
        return logMonitorLayout;
    }

    /**
     * 添加日志框(大尺寸)
     *
     * @return
     */
    public LogMonitorLayout addLogMonitorLarge() {
        LogMonitorLayout logMonitorLayout = addLogMonitor();
        logMonitorLayout.changeSizeBy(DevLayoutUtil.dp2px(100));
        return logMonitorLayout;
    }

    public DevLayout log(@Nullable CharSequence log) {
        if (mLogMonitorLayout != null) {
            mLogMonitorLayout.log(log);
        }
        return this;
    }

    public DevLayout logI(@Nullable CharSequence log) {
        if (mLogMonitorLayout != null) {
            mLogMonitorLayout.i(log);
        }
        return this;
    }

    public DevLayout logD(@Nullable CharSequence log) {
        if (mLogMonitorLayout != null) {
            mLogMonitorLayout.d(log);
        }
        return this;
    }


    public DevLayout logW(CharSequence log) {
        if (mLogMonitorLayout != null) {
            mLogMonitorLayout.w(log);
        }
        return this;
    }

    public DevLayout logE(@NonNull CharSequence log) {
        return logE(log, null);
    }

    public DevLayout logE(@NonNull CharSequence log, @Nullable Throwable throwable) {
        if (mLogMonitorLayout != null) {
            mLogMonitorLayout.e(log, throwable);
        }
        return this;
    }

    public boolean hasLogMonitor() {
        return mLogMonitorLayout != null && mLogMonitorLayout.getParent() != null;
    }

    @Nullable
    public LogMonitorLayout getLogMonitorLayout() {
        return mLogMonitorLayout;
    }

    //</editor-folder>

    /**
     * 添加换行分割线
     *
     * @return
     */
    public DevLayout addLine() {
        View line = new View(getContext());
        line.setBackgroundResource(R.color.dev_layout_divider_color);
        MarginLayoutParams params = new MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
        int margin = DevLayoutUtil.dp2px(5);
        params.setMargins(margin / 2, margin, margin / 2, margin);
        mFlexboxLayout.addView(line, params);
        return this;
    }

    /**
     * 添加换行分割线
     *
     * @return
     */
    public DevLayout hr() {
        return addLine();
    }

    /**
     * 换行
     *
     * @return
     */
    public DevLayout br() {
        Space space = new Space(getContext());
        mFlexboxLayout.addView(space, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return this;
    }

    /**
     * 增加距上的间距
     *
     * @return
     */
    public DevLayout p() {
        Space space = new Space(getContext());
        mFlexboxLayout.addView(space, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DevLayoutUtil.dp2px(10)));
        return this;
    }


    /**
     * 设置是否是行模式,
     * false：流式布局，true：线性布局
     * 控制小的控件是否要占一整行
     *
     * @param isLineStyle true：线性布局，false：流式布局
     */
    public void setIsLineStyle(boolean isLineStyle) {
        mIsLineStyleFlag = isLineStyle;

        //修改已经添加的控件的宽度模式
        for (int i = 0; i < mFlexboxLayout.getChildCount(); i++) {
            View childAt = mFlexboxLayout.getChildAt(i);
            ViewGroup.LayoutParams layoutParams = childAt.getLayoutParams();
            if (layoutParams instanceof LayoutParamOr) {
                layoutParams.width = getWidthParam();
                childAt.setLayoutParams(layoutParams);
            }
        }
    }


    public FlexboxLayout getFlexboxLayout() {
        return mFlexboxLayout;
    }

    private int getWidthParam() {
        return mIsLineStyleFlag ? ViewGroup.LayoutParams.MATCH_PARENT : ViewGroup.LayoutParams.WRAP_CONTENT;
    }


    public static class LayoutParamOr extends FlexboxLayout.LayoutParams {

        public LayoutParamOr(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public LayoutParamOr(FlexboxLayout.LayoutParams source) {
            super(source);
        }

        public LayoutParamOr(ViewGroup.LayoutParams source) {
            super(source);
        }

        public LayoutParamOr(int width, int height) {
            super(width, height);
        }

        public LayoutParamOr(MarginLayoutParams source) {
            super(source);
        }

        protected LayoutParamOr(Parcel in) {
            super(in);
        }
    }

}
