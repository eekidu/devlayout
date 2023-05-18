package com.github.eekidu.dev.devlayout.util;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author caohk
 * @date 2023/5/19
 */
public class DevLayoutUtil {

    /**
     * dp转px
     *
     * @param dpValue dp值
     * @return px值
     */
    public static int dp2px(final float dpValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int getScreenWidth(Context context) {
        return 0;
    }

    public static LinearLayout wrapperDesc(View view) {
        LinearLayout linearLayout = new LinearLayout(view.getContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        TextView textView = new TextView(view.getContext());
        textView.setText("说明：");
        linearLayout.addView(textView);
        linearLayout.addView(view);
        return linearLayout;
    }
}
