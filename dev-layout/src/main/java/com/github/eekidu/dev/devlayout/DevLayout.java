package com.github.eekidu.dev.devlayout;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;

import com.github.eekidu.dev.devlayout.child.EditorTextLayout;
import com.github.eekidu.dev.devlayout.child.KeyValueTextView;
import com.github.eekidu.dev.devlayout.child.RadioGroupLayout;
import com.github.eekidu.dev.devlayout.child.SeekBarLayout;
import com.github.eekidu.dev.devlayout.child.TitleAndDescLayout;
import com.github.eekidu.dev.devlayout.util.DevLayoutUtil;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayout;

/**
 * 快速添加测试控件的布局
 *
 * @author caohk
 * @date 2021/10/12
 */
public class DevLayout extends LinearLayout {


    private FlexboxLayout mFlexboxLayout;
    private boolean mIsLineStyleFlag;

    public DevLayout(Context context) {
        this(context, null);
    }

    public DevLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DevLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);
        int padding = DevLayoutUtil.dp2px(2);
        setPadding(padding, padding, padding, padding);

        mFlexboxLayout = new FlexboxLayout(context);
        mFlexboxLayout.setFlexWrap(FlexWrap.WRAP);
        addView(mFlexboxLayout);
    }

    public DevLayout addAction(String actionName, OnClickListener onClickListener) {
        addButton(actionName, onClickListener);
        return this;
    }

    public Button addButton(String title, OnClickListener onClickListener) {
        Button button = new Button(getContext());
        button.setAllCaps(false);
        button.setText(title);
        button.setOnClickListener(onClickListener);
        mFlexboxLayout.addView(button, new ViewGroup.LayoutParams(getWidthParam(), ViewGroup.LayoutParams.WRAP_CONTENT));
        return button;
    }

    /**
     * @param desc
     * @param btTitle
     * @param onClickListener
     */
    public void addTextViewAndButton(String desc, String btTitle, OnClickListener onClickListener) {
        LinearLayout linearLayout = new LinearLayout(getContext());

        TextView titleTextView = new TextView(getContext());
        titleTextView.setText(desc);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.weight = 1;
        linearLayout.addView(titleTextView, params);

        Button button = new Button(getContext());
        button.setAllCaps(false);
        button.setText(btTitle);
        button.setOnClickListener(onClickListener);
        linearLayout.addView(button, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        mFlexboxLayout.addView(linearLayout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
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

    public SwitchCompat addSwitch(String actionName, CompoundButton.OnCheckedChangeListener onCheckedChangeListener, boolean defaultCheck) {
        SwitchCompat aSwitch = new SwitchCompat(getContext());
        aSwitch.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
        aSwitch.setText(actionName);
        int padding = DevLayoutUtil.dp2px(10);
        aSwitch.setPadding(padding, 0, padding, 0);
        aSwitch.setOnCheckedChangeListener(onCheckedChangeListener);
        if (defaultCheck) {
            aSwitch.setChecked(true);
        }
        mFlexboxLayout.addView(aSwitch, new ViewGroup.LayoutParams(getWidthParam(), ViewGroup.LayoutParams.WRAP_CONTENT));
        return aSwitch;
    }

    public CheckBox addCheckBox(String title, CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
        CheckBox checkBox = new CheckBox(getContext());
        checkBox.setText(title);
        checkBox.setOnCheckedChangeListener(onCheckedChangeListener);
        checkBox.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
        mFlexboxLayout.addView(checkBox, new ViewGroup.LayoutParams(getWidthParam(), ViewGroup.LayoutParams.WRAP_CONTENT));
        return checkBox;
    }


    public SeekBarLayout addSeekBar(String title) {
        return addSeekBar(title, null);
    }

    public SeekBarLayout addSeekBar(String title, SeekBarLayout.OnProgressChangeListener onProgressChangeListener) {
        SeekBarLayout seekBarLayout = new SeekBarLayout(getContext());
        seekBarLayout.getTitleTv().setText(title);
        seekBarLayout.setOnProgressChangeListener(onProgressChangeListener);

        mFlexboxLayout.addView(seekBarLayout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return seekBarLayout;
    }


    /**
     * @param title
     * @return Value TextView
     */
    public TextView addTextView(String title) {
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setGravity(Gravity.CENTER_VERTICAL);

        TextView titleTextView = new TextView(getContext());
        titleTextView.setText(title + ": ");
        linearLayout.addView(titleTextView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        TextView valueTextView = new TextView(getContext());
        valueTextView.setText("--");
        valueTextView.setMinWidth(DevLayoutUtil.dp2px(100));
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayout.addView(valueTextView, params1);

        FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(getWidthParam(), ViewGroup.LayoutParams.WRAP_CONTENT);
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
        RadioGroupLayout radioGroupIndicatorView = new RadioGroupLayout(getContext());
        mFlexboxLayout.addView(radioGroupIndicatorView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return radioGroupIndicatorView;
    }


    /**
     * 添加单选框
     *
     * @return
     */
    public KeyValueTextView addKeyValueTextView() {
        KeyValueTextView keyValueLayout = new KeyValueTextView(getContext());
        keyValueLayout.setMinHeight(DevLayoutUtil.dp2px(40));
        mFlexboxLayout.addView(keyValueLayout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return keyValueLayout;
    }

    /**
     * 添加换行分割线
     *
     * @return
     */
    public DevLayout addLine() {
        View line = new View(getContext());
        line.setBackgroundColor(Color.GRAY);
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
     * 设置是否是行模式,是否是线性布局，小的控件是否要占一行。
     *
     * @param isLineStyle true：线性布局，false：流式布局
     */
    public void setIsLineStyle(boolean isLineStyle) {
        mIsLineStyleFlag = isLineStyle;
    }


    public FlexboxLayout getFlexboxLayout() {
        return mFlexboxLayout;
    }

    private int getWidthParam() {
        return mIsLineStyleFlag ? ViewGroup.LayoutParams.MATCH_PARENT : ViewGroup.LayoutParams.WRAP_CONTENT;
    }

}
