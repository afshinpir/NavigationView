package com.github.afshinpir.navigationview;

import android.content.Context;
import android.util.AttributeSet;

import com.google.android.material.navigation.NavigationView;

import androidx.recyclerview.widget.RecyclerView;

final class InternalNavigationView extends NavigationView {
    private RecyclerView mMenu;

    public InternalNavigationView(Context context) {
        super(context);
        init();
    }

    public InternalNavigationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public InternalNavigationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mMenu = findViewById(com.google.android.material.R.id.design_navigation_view);
        mMenu.setNestedScrollingEnabled(false);
    }

    void setMenuPaddingBottom(int bottom) {
        mMenu.setPadding(0, 0, 0, bottom);
    }
}

