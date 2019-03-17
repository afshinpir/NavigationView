package com.github.afshinpir.navigationview;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.DimenRes;
import androidx.annotation.Dimension;
import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.MenuRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.annotation.StyleRes;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.view.ViewCompat;
import androidx.core.widget.NestedScrollView;

public class NavigationView extends LinearLayout {
    private LayoutInflater mInflater;
    private InternalNavigationView mInternalNavigationView;
    private LinearLayout mScrollingContainer;
    private NestedScrollView mNestedScrollView;
    private int mSeparator = 0;
    private int mMaxWidth = 0;

    public NavigationView(Context context) {
        this(context, null);
    }

    public NavigationView(Context context, AttributeSet attrs) {
        this(context, attrs, com.google.android.material.R.attr.navigationViewStyle);
    }

    public NavigationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    @RestrictTo(RestrictTo.Scope.LIBRARY)
    @Override
    public void setOrientation(int orientation) {
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        mInflater = LayoutInflater.from(getContext());
        mInflater.inflate(R.layout.extended_navigation_view_layout, this, true);

        mNestedScrollView = findViewById(R.id.navigation_scrolling_view);
        mScrollingContainer = findViewById(R.id.navigation_scrolling_container);
        mInternalNavigationView = findViewById(R.id.main_navigation_view);

        mSeparator = getResources().getDimensionPixelSize(R.dimen.navigation_separator_vertical_padding);
        super.setOrientation(VERTICAL);
        setWillNotDraw(true);

        initNavigationViewStyle(context, attrs, defStyleAttr);
    }

    private void initNavigationViewStyle(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.NavigationViewWithFooter,
                defStyleAttr,
                com.google.android.material.R.style.Widget_Design_NavigationView);

        if (a.hasValue(R.styleable.NavigationViewWithFooter_android_background)) {
            final int resourceId = a.getResourceId(
                    R.styleable.NavigationViewWithFooter_android_background,
                    0);
            if (resourceId != 0) {
                ViewCompat.setBackground(this, AppCompatResources.getDrawable(context, resourceId));
            }
        } else {
            TypedValue attr = new TypedValue();
            getContext().getTheme().resolveAttribute(android.R.attr.windowBackground, attr, true);
            if (attr.type >= TypedValue.TYPE_FIRST_COLOR_INT && attr.type <= TypedValue.TYPE_LAST_COLOR_INT) {
                setBackgroundColor(attr.data);
            } else {
                ViewCompat.setBackground(this, AppCompatResources.getDrawable(context, attr.resourceId));
            }
        }

        if (a.hasValue(R.styleable.NavigationViewWithFooter_elevation)) {
            ViewCompat.setElevation(this, a.getDimensionPixelSize(R.styleable.NavigationViewWithFooter_elevation, 0));
        }

        mMaxWidth = a.getDimensionPixelSize(R.styleable.NavigationViewWithFooter_android_maxWidth, 0);

        setFitsSystemWindows(a.getBoolean(R.styleable.NavigationViewWithFooter_android_fitsSystemWindows, false));

        if (a.hasValue(R.styleable.NavigationViewWithFooter_itemIconTint)) {
            final int resourceId = a.getResourceId(
                    R.styleable.NavigationViewWithFooter_itemIconTint,
                    0);
            if (resourceId != 0) {
                final ColorStateList colorStateList = AppCompatResources.getColorStateList(context, resourceId);

                if (colorStateList != null) {
                    setItemIconTintList(colorStateList);
                }
            }
        }

        if (a.hasValue(R.styleable.NavigationViewWithFooter_itemTextAppearance)) {
            setItemTextAppearance(a.getResourceId(R.styleable.NavigationViewWithFooter_itemTextAppearance, 0));
        }

        if (a.hasValue(R.styleable.NavigationViewWithFooter_itemTextColor)) {
            final int resourceId = a.getResourceId(
                    R.styleable.NavigationViewWithFooter_itemTextColor,
                    0);
            if (resourceId != 0) {
                final ColorStateList colorStateList = AppCompatResources.getColorStateList(context, resourceId);

                if (colorStateList != null) {
                    setItemTextColor(colorStateList);
                }
            }
        }

        if (a.hasValue(R.styleable.NavigationViewWithFooter_itemBackground)) {
            final int resourceId = a.getResourceId(
                    R.styleable.NavigationViewWithFooter_itemBackground,
                    0);
            if (resourceId != 0) {
                setItemBackground(AppCompatResources.getDrawable(context, resourceId));
            }
        }

        if (a.hasValue(R.styleable.NavigationViewWithFooter_itemHorizontalPadding)) {
            setItemHorizontalPadding(
                    a.getDimensionPixelSize(R.styleable.NavigationViewWithFooter_itemHorizontalPadding, 0)
            );
        }

        if (a.hasValue(R.styleable.NavigationViewWithFooter_itemIconPadding)) {
            setItemIconPadding(
                    a.getDimensionPixelSize(R.styleable.NavigationViewWithFooter_itemIconPadding, 0)
            );
        }

        if (a.hasValue(R.styleable.NavigationViewWithFooter_menu)) {
            inflateMenu(a.getResourceId(
                    R.styleable.NavigationViewWithFooter_menu,
                    0));
        }

        if (a.hasValue(R.styleable.NavigationViewWithFooter_scrollableHeaderLayout)) {
            inflateScrollableHeaderView(a.getResourceId(
                    R.styleable.NavigationViewWithFooter_scrollableHeaderLayout,
                    0));
        }

        if (a.hasValue(R.styleable.NavigationViewWithFooter_fixedHeaderLayout)) {
            inflateFixedHeaderView(a.getResourceId(
                    R.styleable.NavigationViewWithFooter_fixedHeaderLayout,
                    0));
        }

        if (a.hasValue(R.styleable.NavigationViewWithFooter_scrollableFooterLayout)) {
            inflateScrollableFooterView(a.getResourceId(
                    R.styleable.NavigationViewWithFooter_scrollableFooterLayout,
                    0));
        }

        if (a.hasValue(R.styleable.NavigationViewWithFooter_fixedFooterLayout)) {
            inflateFixedFooterView(a.getResourceId(
                    R.styleable.NavigationViewWithFooter_fixedFooterLayout,
                    0));
        }

        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        switch (MeasureSpec.getMode(widthMeasureSpec)) {
            case MeasureSpec.EXACTLY:
                break;

            case MeasureSpec.AT_MOST:
                widthMeasureSpec = MeasureSpec.makeMeasureSpec(
                        Math.min(MeasureSpec.getSize(widthMeasureSpec), mMaxWidth),
                        MeasureSpec.EXACTLY);
                break;

            case MeasureSpec.UNSPECIFIED:
                widthMeasureSpec = MeasureSpec.makeMeasureSpec(mMaxWidth,
                        MeasureSpec.EXACTLY);
                break;
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public View inflateFixedHeaderView(@LayoutRes int res) {
        View v = mInflater.inflate(res, this, false);
        addFixedHeaderView(v);
        return v;
    }

    public void addFixedHeaderView(@NonNull View header) {
        addView(header, getFixedHeaderCount());
    }

    public int getFixedHeaderCount() {
        return indexOfChild(mNestedScrollView);
    }

    @Nullable
    public View getFixedHeader(int index) {
        if (index >= 0 && index < getFixedHeaderCount()) {
            return getChildAt(index);
        }

        return null;
    }

    public void removeFixedHeader(@NonNull View header) {
        int idx = indexOfChild(header);
        if (idx >= 0 && idx < getFixedHeaderCount()) {
            removeViewAt(idx);
        }
    }

    public View inflateScrollableHeaderView(@LayoutRes int res) {
        return mInternalNavigationView.inflateHeaderView(res);
    }

    public void addScrollableHeaderView(@NonNull View header) {
        mInternalNavigationView.addHeaderView(header);
    }

    public int getScrollableHeaderCount() {
        return mInternalNavigationView.getHeaderCount();
    }

    @Nullable
    public View getScrollableHeader(int index) {
        return mInternalNavigationView.getHeaderView(index);
    }

    public void removeScrollableHeader(@NonNull View header) {
        mInternalNavigationView.removeHeaderView(header);
    }

    public View inflateFixedFooterView(@LayoutRes int res) {
        View v = mInflater.inflate(res, this, false);
        addFixedFooterView(v);
        return v;
    }

    public void addFixedFooterView(@NonNull View header) {
        addView(header);
    }

    public int getFixedFooterCount() {
        return getChildCount() - indexOfChild(mNestedScrollView) - 1;
    }

    @Nullable
    public View getFixedFooter(int index) {
        if (index >= 0 && index < getFixedHeaderCount()) {
            return getChildAt(indexOfChild(mNestedScrollView) + index + 1);
        }

        return null;
    }

    public void removeFixedFooter(@NonNull View header) {
        int idx = indexOfChild(header);
        if (idx > indexOfChild(mNestedScrollView) && idx < getChildCount()) {
            removeViewAt(idx);
        }
    }

    public View inflateScrollableFooterView(@LayoutRes int res) {
        View v = mInflater.inflate(res, mScrollingContainer, false);
        addScrollableFooterView(v);
        return v;
    }

    public void addScrollableFooterView(@NonNull View header) {
        mScrollingContainer.addView(header);
        mInternalNavigationView.setMenuPaddingBottom(mSeparator * 2);
    }

    public int getScrollableFooterCount() {
        return mScrollingContainer.getChildCount() - 1;
    }

    @Nullable
    public View getScrollableFooter(int index) {
        if (index >= 0 && index < getScrollableFooterCount()) {
            return mScrollingContainer.getChildAt(index + 1);
        }

        return null;
    }

    public void removeScrollableFooter(@NonNull View header) {
        int idx = mScrollingContainer.indexOfChild(header);
        if (idx >= 1 && idx < getChildCount()) {
            mScrollingContainer.removeViewAt(idx);

            if (getScrollableFooterCount() == 0) {
                mInternalNavigationView.setMenuPaddingBottom(0);
            }
        }
    }

    public void setNavigationItemSelectedListener(@Nullable NavigationView.OnNavigationItemSelectedListener listener) {
        mInternalNavigationView.setNavigationItemSelectedListener(listener);
    }

    public Menu getMenu() {
        return mInternalNavigationView.getMenu();
    }

    public void inflateMenu(@MenuRes int resId) {
        mInternalNavigationView.inflateMenu(resId);
    }

    @Nullable
    public ColorStateList getItemIconTintList() {
        return mInternalNavigationView.getItemIconTintList();
    }

    public void setItemIconTintList(@Nullable ColorStateList tint) {
        mInternalNavigationView.setItemIconTintList(tint);
    }

    @Nullable
    public ColorStateList getItemTextColor() {
        return mInternalNavigationView.getItemTextColor();
    }

    public void setItemTextColor(@Nullable ColorStateList textColor) {
        mInternalNavigationView.setItemTextColor(textColor);
    }

    @Nullable
    public Drawable getItemBackground() {
        return mInternalNavigationView.getItemBackground();
    }

    public void setItemBackgroundResource(@DrawableRes int resId) {
        mInternalNavigationView.setItemBackgroundResource(resId);
    }

    public void setItemBackground(@Nullable Drawable itemBackground) {
        mInternalNavigationView.setItemBackground(itemBackground);
    }

    @Dimension
    public int getItemHorizontalPadding() {
        return mInternalNavigationView.getItemHorizontalPadding();
    }

    public void setItemHorizontalPadding(@Dimension int padding) {
        mInternalNavigationView.setItemHorizontalPadding(padding);
    }

    public void setItemHorizontalPaddingResource(@DimenRes int paddingResource) {
        mInternalNavigationView.setItemHorizontalPaddingResource(paddingResource);
    }

    @Dimension
    public int getItemIconPadding() {
        return mInternalNavigationView.getItemIconPadding();
    }

    public void setItemIconPadding(@Dimension int padding) {
        mInternalNavigationView.setItemIconPadding(padding);
    }

    public void setItemIconPaddingResource(int paddingResource) {
        mInternalNavigationView.setItemIconPaddingResource(paddingResource);
    }

    public void setCheckedItem(@IdRes int id) {
        mInternalNavigationView.setCheckedItem(id);
    }

    public void setCheckedItem(@NonNull MenuItem checkedItem) {
        mInternalNavigationView.setCheckedItem(checkedItem);
    }

    @Nullable
    public MenuItem getCheckedItem() {
        return mInternalNavigationView.getCheckedItem();
    }

    public void setItemTextAppearance(@StyleRes int resId) {
        mInternalNavigationView.setItemTextAppearance(resId);
    }

    public interface OnNavigationItemSelectedListener extends
            com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener {}
}
