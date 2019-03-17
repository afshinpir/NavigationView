package com.github.afshinpir.navigationview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

class ScrimInsetsLinearLayout extends LinearLayout {
    public ScrimInsetsLinearLayout(Context context) {
        this(context, null);
    }

    public ScrimInsetsLinearLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrimInsetsLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setWillNotDraw(true);
        ViewCompat.setOnApplyWindowInsetsListener(this, new androidx.core.view.OnApplyWindowInsetsListener() {
            @Override
            public WindowInsetsCompat onApplyWindowInsets(View v, WindowInsetsCompat insets) {
                onInsetsChanged(insets);
                ViewCompat.postInvalidateOnAnimation(ScrimInsetsLinearLayout.this);
                return insets.consumeSystemWindowInsets();
            }
        });
    }

    protected void onInsetsChanged(WindowInsetsCompat insets) {
    }
}
