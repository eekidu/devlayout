package com.github.eekidu.dev.devlayout.child;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.github.eekidu.dev.devlayout.util.DevLayoutUtil;

/**
 * @author caohk
 * @date 2022/4/18
 */
public class EditorTextLayout extends LinearLayout {
    public interface OnSureClickListener {
        void onText(Editable editable);
    }

    private final TextView mTitleTextView;
    private final EditText mEditorText;
    private final Button mSureButton;
    private ImageView mImageView;

    private OnSureClickListener mOnSureClickListener;

    public EditorTextLayout(Context context) {
        this(context, null);
    }

    public EditorTextLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EditorTextLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setGravity(Gravity.CENTER_VERTICAL);

        mTitleTextView = DevLayoutUtil.generateTitleTv(context);
        mTitleTextView.setText("标题");
        addView(mTitleTextView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setGravity(Gravity.CENTER_VERTICAL);
        mEditorText = new EditText(getContext());
        mEditorText.setHint("请输入");
        mEditorText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mImageView.setVisibility(s.length() > 0 ? VISIBLE : GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.weight = 1;
        linearLayout.addView(mEditorText, params);


        mImageView = new ImageView(getContext());
        mImageView.setOnClickListener(v -> mEditorText.setText(""));
        mImageView.setImageDrawable(getContext().getDrawable(android.R.drawable.ic_menu_close_clear_cancel));
        mImageView.setVisibility(GONE);
        linearLayout.addView(mImageView);


        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
        params1.weight = 1;
        addView(linearLayout, params1);


        mSureButton = new Button(getContext());
        mSureButton.setText("确定");
        mSureButton.setOnClickListener(v -> {
            if (mOnSureClickListener != null) {
                mOnSureClickListener.onText(mEditorText.getText());
            }

        });
        addView(mSureButton, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    public TextView getTitleTextView() {
        return mTitleTextView;
    }

    public EditText getEditorText() {
        return mEditorText;
    }


    public Button getSureButton() {
        return mSureButton;
    }

    public void setOnSureClickListener(OnSureClickListener onSureClickListener) {
        mOnSureClickListener = onSureClickListener;
    }
}
