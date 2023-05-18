package github.eekidu.dev.devlayout.child;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

/**
 * @author caohk
 * @date 2022/4/18
 */
public class EditorTextLayout extends LinearLayout {

    private final TextView mTitleTextView;
    private final EditText mEditorText;
    private final Button mClearButton;

    public EditorTextLayout(Context context) {
        this(context, null);
    }

    public EditorTextLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EditorTextLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setGravity(Gravity.CENTER_VERTICAL);

        mTitleTextView = new TextView(getContext());
        addView(mTitleTextView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));


        mEditorText = new EditText(getContext());
        mEditorText.setHint("请输入");
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.weight = 1;
        addView(mEditorText, params);


        mClearButton = new Button(getContext());
        mClearButton.setText("清除");
        mClearButton.setOnClickListener(v -> mEditorText.setText(""));
        addView(mClearButton, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    public TextView getTitleTextView() {
        return mTitleTextView;
    }

    public EditText getEditorText() {
        return mEditorText;
    }


    public Button getClearButton() {
        return mClearButton;
    }
}
