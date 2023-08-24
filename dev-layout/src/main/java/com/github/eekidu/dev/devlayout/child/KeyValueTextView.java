package com.github.eekidu.dev.devlayout.child;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * @author caohk
 * @date 2023/6/15
 */
public class KeyValueTextView extends AppCompatTextView {

    private StringBuffer mStringBuffer;

    public KeyValueTextView(Context context) {
        this(context, null);
    }

    public KeyValueTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KeyValueTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mStringBuffer = new StringBuffer();
    }

    public KeyValueTextView clear() {
        mStringBuffer.delete(0, mStringBuffer.length());
        return this;
    }

    public KeyValueTextView addKV(String key, Object value) {
        if (mStringBuffer.length() > 0 && mStringBuffer.charAt(mStringBuffer.length() - 1) != '\n') {
            mStringBuffer.append("，");
        }
        mStringBuffer.append("[").append(key).append(": ").append(value).append("]");
        setText(mStringBuffer);
        return this;
    }

    public KeyValueTextView ln() {
        mStringBuffer.append("\n");
        return this;
    }
}
