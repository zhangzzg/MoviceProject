package com.zhangwan.movieproject.app.widget;

/*
 * Copyright (C) 2013 Andreas Stuetz <andreas.stuetz@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitationscom.gaca.util.schedulecense.
 */


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.zhangwan.movieproject.app.R;

import java.util.Locale;


public class MPagerSlidingTabStrip extends HorizontalScrollView {

    private int mSelectedPosition;

    public interface IconTabProvider {
        public int getPageIconResId(int position);
    }

    // @formatter:off
    private static final int[] ATTRS = new int[]{
            android.R.attr.textSize,
            android.R.attr.textColor
    };
    // @formatter:on

    private LinearLayout.LayoutParams defaultTabLayoutParams;
    private LinearLayout.LayoutParams expandedTabLayoutParams;

    private final PageListener pageListener = new PageListener();
    public OnPageChangeListener delegatePageListener;

    private LinearLayout tabsContainer;
    private ViewPager    pager;

    private int tabCount;

    private int currentPosition = 0;
    private float currentPositionOffset = 0f;

    private Paint rectPaint;
    private Paint dividerPaint;

    private int indicatorColor = 0xFF666666;
    private int underlineColor = 0x1A000000;
    private int dividerColor = 0x1A000000;

    private boolean shouldExpand = false;
    private boolean textAllCaps = true;

    private int scrollOffset = 52;
    private int indicatorHeight = 8;
    private int underlineHeight = 6;
    private int dividerPadding = 12;
    private int tabPadding = 24;
    private int dividerWidth = 1;

    //begin 源码已经定义死了，只能自己改
    private int tabTextSize = 12;
    private int tabTextColor = 0xFF666666;
    private int tabSelectedTextSize = 12;
    private int tabSelectedTextColor = 0xFF666666;

    //end
    private Typeface tabTypeface = null;
    private int tabTypefaceStyle = Typeface.NORMAL;

    private int lastScrollX = 0;

    private int tabBackgroundResId = R.drawable.select_background_tab;

    private Locale locale;

    public MPagerSlidingTabStrip(Context context) {
        this(context, null);
    }

    public MPagerSlidingTabStrip(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MPagerSlidingTabStrip(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        setFillViewport(true);
        setWillNotDraw(false);

        tabsContainer = new LinearLayout(context);
        tabsContainer.setOrientation(LinearLayout.HORIZONTAL);
        tabsContainer.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        addView(tabsContainer);

        DisplayMetrics dm = getResources().getDisplayMetrics();

        scrollOffset = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, scrollOffset, dm);
        indicatorHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, indicatorHeight, dm);
        underlineHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, underlineHeight, dm);
        dividerPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dividerPadding, dm);
        tabPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, tabPadding, dm);
        dividerWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dividerWidth, dm);
        tabTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, tabTextSize, dm);

        // get system attrs (android:textSize and android:textColor)

        TypedArray a = context.obtainStyledAttributes(attrs, ATTRS);

        tabTextSize = a.getDimensionPixelSize(0, tabTextSize);
        tabTextColor = a.getColor(1, tabTextColor);

        a.recycle();

        // get custom attrs

        a = context.obtainStyledAttributes(attrs, R.styleable.MPagerSlidingTabStrip);

        indicatorColor = a.getColor(R.styleable.MPagerSlidingTabStrip_pstsIndicatorColor, indicatorColor);
        underlineColor = a.getColor(R.styleable.MPagerSlidingTabStrip_pstsUnderlineColor, underlineColor);
        dividerColor = a.getColor(R.styleable.MPagerSlidingTabStrip_pstsDividerColor, dividerColor);
        indicatorHeight = a.getDimensionPixelSize(R.styleable.MPagerSlidingTabStrip_pstsIndicatorHeight, indicatorHeight);
        underlineHeight = a.getDimensionPixelSize(R.styleable.MPagerSlidingTabStrip_pstsUnderlineHeight, underlineHeight);
        dividerPadding = a.getDimensionPixelSize(R.styleable.MPagerSlidingTabStrip_pstsDividerPadding, dividerPadding);
        tabPadding = a.getDimensionPixelSize(R.styleable.MPagerSlidingTabStrip_pstsTabPaddingLeftRight, tabPadding);
        tabBackgroundResId = a.getResourceId(R.styleable.MPagerSlidingTabStrip_pstsTabBackground, tabBackgroundResId);
        shouldExpand = a.getBoolean(R.styleable.MPagerSlidingTabStrip_pstsShouldExpand, shouldExpand);
        scrollOffset = a.getDimensionPixelSize(R.styleable.MPagerSlidingTabStrip_pstsScrollOffset, scrollOffset);
        textAllCaps = a.getBoolean(R.styleable.MPagerSlidingTabStrip_pstsTextAllCaps, textAllCaps);

        //custom:获取属性值
        tabTextSize = a.getDimensionPixelSize(R.styleable.MPagerSlidingTabStrip_pstsTabTextSize, tabTextSize);
        tabTextColor = a.getColor(R.styleable.MPagerSlidingTabStrip_pstsTabTextColor, tabTextColor);
        tabSelectedTextSize = a.getDimensionPixelSize(R.styleable.MPagerSlidingTabStrip_pstsTabSelectedTextSize, tabSelectedTextSize);
        tabSelectedTextColor = a.getColor(R.styleable.MPagerSlidingTabStrip_pstsTabSelectedTextColor, tabSelectedTextColor);

        a.recycle();

        rectPaint = new Paint();
        rectPaint.setAntiAlias(true);
        rectPaint.setStyle(Style.FILL);
        dividerPaint = new Paint();
        dividerPaint.setAntiAlias(true);
        dividerPaint.setStrokeWidth(dividerWidth);

        defaultTabLayoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        expandedTabLayoutParams = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1.0f);

        if (locale == null) {
            locale = getResources().getConfiguration().locale;
        }
    }

    public void setViewPager(ViewPager pager) {
        this.pager = pager;

        if (pager.getAdapter() == null) {
            throw new IllegalStateException("ViewPager does not have adapter instance.");
        }

        pager.setOnPageChangeListener(pageListener);

        notifyDataSetChanged();
    }

    public void setOnPageChangeListener(OnPageChangeListener listener) {
        this.delegatePageListener = listener;
    }

    public void notifyDataSetChanged() {

        tabsContainer.removeAllViews();

        tabCount = pager.getAdapter().getCount();

        for (int i = 0; i < tabCount; i++) {

            if (pager.getAdapter() instanceof IconTabProvider) {
                addIconTab(i, ((IconTabProvider) pager.getAdapter()).getPageIconResId(i));
            } else {
                addTextTab(i, pager.getAdapter().getPageTitle(i).toString());
            }

        }

        updateTabStyles();

        getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

            @SuppressWarnings("deprecation")
            @SuppressLint("NewApi")
            @Override
            public void onGlobalLayout() {

                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                    getViewTreeObserver().removeGlobalOnLayoutListener(this);
                } else {
                    getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }

                currentPosition = pager.getCurrentItem();
                scrollToChild(currentPosition, 0);
                //custom 默认显示第一页
                pageListener.onPageSelected(0);
            }
        });

    }

    private void addTextTab(final int position, String title) {

        TextView tab = new TextView(getContext());
        tab.setText(title);
        tab.setGravity(Gravity.CENTER);
        tab.setSingleLine();

        addTab(position, tab);
    }

    private void addIconTab(final int position, int resId) {

        ImageButton tab = new ImageButton(getContext());
        tab.setImageResource(resId);

        addTab(position, tab);

    }

    private void addTab(final int position, View tab) {
        tab.setFocusable(true);
        tab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(position);
            }
        });

        tab.setPadding(tabPadding, 0, tabPadding, 0);
        tabsContainer.addView(tab, position, shouldExpand ? expandedTabLayoutParams : defaultTabLayoutParams);
    }

    private void updateTabStyles() {

        for (int i = 0; i < tabCount; i++) {

            View v = tabsContainer.getChildAt(i);

            v.setBackgroundResource(tabBackgroundResId);

            if (v instanceof TextView) {

                TextView tab = (TextView) v;
                tab.setTextSize(TypedValue.COMPLEX_UNIT_PX, tabTextSize);
                tab.setTypeface(tabTypeface, tabTypefaceStyle);
                tab.setTextColor(tabTextColor);

                // setAllCaps() is only available from API 14, so the upper case is made manually if we are on a
                // pre-ICS-build
                if (textAllCaps) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                        tab.setAllCaps(true);
                    } else {
                        tab.setText(tab.getText().toString().toUpperCase(locale));
                    }
                }

                // custom
                if (mSelectedPosition == i) {// 选中
                    tab.setTextSize(TypedValue.COMPLEX_UNIT_PX, tabSelectedTextSize);
                    tab.setTextColor(tabSelectedTextColor);
                }
            }
        }

    }

    private void scrollToChild(int position, int offset) {

        if (tabCount == 0) {
            return;
        }

        int newScrollX = tabsContainer.getChildAt(position).getLeft() + offset;

        if (position > 0 || offset > 0) {
            newScrollX -= scrollOffset;
        }

        if (newScrollX != lastScrollX) {
            lastScrollX = newScrollX;
            scrollTo(newScrollX, 0);
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (isInEditMode() || tabCount == 0) {
            return;
        }

        final int height = getHeight();

        // draw indicator line

        rectPaint.setColor(indicatorColor);

        // default: line below current tab
        View currentTab = tabsContainer.getChildAt(currentPosition);
        float lineLeft = currentTab.getLeft() + tabPadding;
        float lineRight = currentTab.getRight() - tabPadding;
//        float lineLeft = currentTab.getLeft() ;
//        float lineRight = currentTab.getRight() ;
        // if there is an offset, start interpolating left and right coordinates between current and next tab
        if (currentPositionOffset > 0f && currentPosition < tabCount - 1) {

            View nextTab = tabsContainer.getChildAt(currentPosition + 1);
            final float nextTabLeft = nextTab.getLeft();
            final float nextTabRight = nextTab.getRight();

            lineLeft = (currentPositionOffset * nextTabLeft + (1f - currentPositionOffset) * lineLeft) + tabPadding;
            lineRight = (currentPositionOffset * nextTabRight + (1f - currentPositionOffset) * lineRight) - tabPadding;

        }

        canvas.drawRect(lineLeft, height - indicatorHeight, lineRight, height, rectPaint);

        // draw 线
//        float triangleWith = 240;
//        float x1 = (lineRight - lineLeft) / 2 + lineLeft;
//		float y1= height - indicatorHeight;
////        float y1 = height;
//        float x2 = x1 - triangleWith / 2;
//        float y2 = height;
//        float x3 = x1 + triangleWith / 2;
//        float y3 = height;
//
//        Path path = new Path();
//        path.moveTo(x1, y1);
//        path.lineTo(x2, y2);
//        path.lineTo(x3, y3);
//        path.lineTo(x1, y1);
//        canvas.drawPath(path, rectPaint);
        // draw underline

        rectPaint.setColor(underlineColor);
        canvas.drawRect(0, height - underlineHeight, tabsContainer.getWidth(), height, rectPaint);
//        canvas.drawRect(0, 2, tabsContainer.getWidth(),2, rectPaint);
        // draw divider

        dividerPaint.setColor(dividerColor);
        for (int i = 0; i < tabCount - 1; i++) {
            View tab = tabsContainer.getChildAt(i);
            canvas.drawLine(tab.getRight(), dividerPadding, tab.getRight(), height - dividerPadding, dividerPaint);
        }
    }

    private class PageListener implements OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            currentPosition = position;
            currentPositionOffset = positionOffset;

            scrollToChild(position, (int) (positionOffset * tabsContainer.getChildAt(position).getWidth()));

            invalidate();

            if (delegatePageListener != null) {
                delegatePageListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == ViewPager.SCROLL_STATE_IDLE) {
                scrollToChild(pager.getCurrentItem(), 0);
            }

            if (delegatePageListener != null) {
                delegatePageListener.onPageScrollStateChanged(state);
            }
        }

        @Override
        public void onPageSelected(int position) {
            if (delegatePageListener != null) {
                delegatePageListener.onPageSelected(position);
            }
            mSelectedPosition = position;//ctrl+alt+f 抽取成员变量
            // 更新tab style
            updateTabStyles();
        }

    }

    public void setIndicatorColor(int indicatorColor) {
        this.indicatorColor = indicatorColor;
        invalidate();
    }

    public void setIndicatorColorResource(int resId) {
        this.indicatorColor = getResources().getColor(resId);
        invalidate();
    }

    public int getIndicatorColor() {
        return this.indicatorColor;
    }

    public void setIndicatorHeight(int indicatorLineHeightPx) {
        this.indicatorHeight = indicatorLineHeightPx;
        invalidate();
    }

    public int getIndicatorHeight() {
        return indicatorHeight;
    }

    public void setUnderlineColor(int underlineColor) {
        this.underlineColor = underlineColor;
        invalidate();
    }

    public void setUnderlineColorResource(int resId) {
        this.underlineColor = getResources().getColor(resId);
        invalidate();
    }

    public int getUnderlineColor() {
        return underlineColor;
    }

    public void setDividerColor(int dividerColor) {
        this.dividerColor = dividerColor;
        invalidate();
    }

    public void setDividerColorResource(int resId) {
        this.dividerColor = getResources().getColor(resId);
        invalidate();
    }

    public int getDividerColor() {
        return dividerColor;
    }

    public void setUnderlineHeight(int underlineHeightPx) {
        this.underlineHeight = underlineHeightPx;
        invalidate();
    }

    public int getUnderlineHeight() {
        return underlineHeight;
    }

    public void setDividerPadding(int dividerPaddingPx) {
        this.dividerPadding = dividerPaddingPx;
        invalidate();
    }

    public int getDividerPadding() {
        return dividerPadding;
    }

    public void setScrollOffset(int scrollOffsetPx) {
        this.scrollOffset = scrollOffsetPx;
        invalidate();
    }

    public int getScrollOffset() {
        return scrollOffset;
    }

    public void setShouldExpand(boolean shouldExpand) {
        this.shouldExpand = shouldExpand;
        requestLayout();
    }

    public boolean getShouldExpand() {
        return shouldExpand;
    }

    public boolean isTextAllCaps() {
        return textAllCaps;
    }

    public void setAllCaps(boolean textAllCaps) {
        this.textAllCaps = textAllCaps;
    }

    public void setTextSize(int textSizePx) {
        this.tabTextSize = textSizePx;
        updateTabStyles();
    }

    public int getTextSize() {
        return tabTextSize;
    }

    public void setTextColor(int textColor) {
        this.tabTextColor = textColor;
        updateTabStyles();
    }

    public void setTextColorResource(int resId) {
        this.tabTextColor = getResources().getColor(resId);
        updateTabStyles();
    }

    public int getTextColor() {
        return tabTextColor;
    }

    public void setTypeface(Typeface typeface, int style) {
        this.tabTypeface = typeface;
        this.tabTypefaceStyle = style;
        updateTabStyles();
    }

    public void setTabBackground(int resId) {
        this.tabBackgroundResId = resId;
    }

    public int getTabBackground() {
        return tabBackgroundResId;
    }

    public void setTabPaddingLeftRight(int paddingPx) {
        this.tabPadding = paddingPx;
        updateTabStyles();
    }

    public int getTabPaddingLeftRight() {
        return tabPadding;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        currentPosition = savedState.currentPosition;
        requestLayout();
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState savedState = new SavedState(superState);
        savedState.currentPosition = currentPosition;
        return savedState;
    }

    static class SavedState extends BaseSavedState {
        int currentPosition;

        public SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            currentPosition = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(currentPosition);
        }

        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }

    public static class PagerSlidingTabStrip extends HorizontalScrollView {

        public interface IconTabProvider {
            public int getPageIconResId(int position);
        }

        // @formatter:off
        private static final int[] ATTRS = new int[]{
                android.R.attr.textSize,
                android.R.attr.textColor
        };
        // @formatter:on

        private LinearLayout.LayoutParams defaultTabLayoutParams;
        private LinearLayout.LayoutParams expandedTabLayoutParams;

        private final PageListener pageListener = new PageListener();
        public OnPageChangeListener delegatePageListener;

        private LinearLayout tabsContainer;
        private ViewPager    pager;

        private int tabCount;

        private int currentPosition = 0;
        private float currentPositionOffset = 0f;

        private Paint rectPaint;
        private Paint dividerPaint;

        private int indicatorColor = 0xFF666666;
        private int underlineColor = 0x1A000000;
        private int dividerColor = 0x1A000000;

        private boolean shouldExpand = false;
        private boolean textAllCaps = true;

        private int scrollOffset = 52;
        private int indicatorHeight = 8;
        private int underlineHeight = 2;
        private int dividerPadding = 12;
        private int tabPadding = 24;
        private int dividerWidth = 1;

        private int tabTextSize = 12;
        private int tabTextColor = 0xFF666666;
        private Typeface tabTypeface = null;
        private int tabTypefaceStyle = Typeface.NORMAL;

        private int lastScrollX = 0;

        private int tabBackgroundResId = R.drawable.select_background_tab;

        private Locale locale;

        public PagerSlidingTabStrip(Context context) {
            this(context, null);
        }

        public PagerSlidingTabStrip(Context context, AttributeSet attrs) {
            this(context, attrs, 0);
        }

        public PagerSlidingTabStrip(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);

            setFillViewport(true);
            setWillNotDraw(false);

            tabsContainer = new LinearLayout(context);
            tabsContainer.setOrientation(LinearLayout.HORIZONTAL);
            tabsContainer.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            addView(tabsContainer);

            DisplayMetrics dm = getResources().getDisplayMetrics();

            scrollOffset = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, scrollOffset, dm);
            indicatorHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, indicatorHeight, dm);
            underlineHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, underlineHeight, dm);
            dividerPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dividerPadding, dm);
            tabPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, tabPadding, dm);
            dividerWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dividerWidth, dm);
            tabTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, tabTextSize, dm);

            // get system attrs (android:textSize and android:textColor)

            TypedArray a = context.obtainStyledAttributes(attrs, ATTRS);

            tabTextSize = a.getDimensionPixelSize(0, tabTextSize);
            tabTextColor = a.getColor(1, tabTextColor);

            a.recycle();

            // get custom attrs

            a = context.obtainStyledAttributes(attrs, R.styleable.MPagerSlidingTabStrip);

            indicatorColor = a.getColor(R.styleable.MPagerSlidingTabStrip_pstsIndicatorColor, indicatorColor);
            underlineColor = a.getColor(R.styleable.MPagerSlidingTabStrip_pstsUnderlineColor, underlineColor);
            dividerColor = a.getColor(R.styleable.MPagerSlidingTabStrip_pstsDividerColor, dividerColor);
            indicatorHeight = a.getDimensionPixelSize(R.styleable.MPagerSlidingTabStrip_pstsIndicatorHeight, indicatorHeight);
            underlineHeight = a.getDimensionPixelSize(R.styleable.MPagerSlidingTabStrip_pstsUnderlineHeight, underlineHeight);
            dividerPadding = a.getDimensionPixelSize(R.styleable.MPagerSlidingTabStrip_pstsDividerPadding, dividerPadding);
            tabPadding = a.getDimensionPixelSize(R.styleable.MPagerSlidingTabStrip_pstsTabPaddingLeftRight, tabPadding);
            tabBackgroundResId = a.getResourceId(R.styleable.MPagerSlidingTabStrip_pstsTabBackground, tabBackgroundResId);
            shouldExpand = a.getBoolean(R.styleable.MPagerSlidingTabStrip_pstsShouldExpand, shouldExpand);
            scrollOffset = a.getDimensionPixelSize(R.styleable.MPagerSlidingTabStrip_pstsScrollOffset, scrollOffset);
            textAllCaps = a.getBoolean(R.styleable.MPagerSlidingTabStrip_pstsTextAllCaps, textAllCaps);

            a.recycle();

            rectPaint = new Paint();
            rectPaint.setAntiAlias(true);
            rectPaint.setStyle(Style.FILL);

            dividerPaint = new Paint();
            dividerPaint.setAntiAlias(true);
            dividerPaint.setStrokeWidth(dividerWidth);

            defaultTabLayoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
            expandedTabLayoutParams = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1.0f);

            if (locale == null) {
                locale = getResources().getConfiguration().locale;
            }
        }

        public void setViewPager(ViewPager pager) {
            this.pager = pager;

            if (pager.getAdapter() == null) {
                throw new IllegalStateException("ViewPager does not have adapter instance.");
            }

            pager.setOnPageChangeListener(pageListener);

            notifyDataSetChanged();
        }

        public void setOnPageChangeListener(OnPageChangeListener listener) {
            this.delegatePageListener = listener;
        }

        public void notifyDataSetChanged() {

            tabsContainer.removeAllViews();

            tabCount = pager.getAdapter().getCount();

            for (int i = 0; i < tabCount; i++) {

                if (pager.getAdapter() instanceof IconTabProvider) {
                    addIconTab(i, ((IconTabProvider) pager.getAdapter()).getPageIconResId(i));
                } else {
                    addTextTab(i, pager.getAdapter().getPageTitle(i).toString());
                }

            }

            updateTabStyles();

            getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

                @SuppressWarnings("deprecation")
                @SuppressLint("NewApi")
                @Override
                public void onGlobalLayout() {

                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                        getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    } else {
                        getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }

                    currentPosition = pager.getCurrentItem();
                    scrollToChild(currentPosition, 0);
                }
            });

        }

        private void addTextTab(final int position, String title) {

            TextView tab = new TextView(getContext());
            tab.setText(title);
            tab.setGravity(Gravity.CENTER);
            tab.setSingleLine();

            addTab(position, tab);
        }

        private void addIconTab(final int position, int resId) {

            ImageButton tab = new ImageButton(getContext());
            tab.setImageResource(resId);

            addTab(position, tab);

        }

        private void addTab(final int position, View tab) {
            tab.setFocusable(true);
            tab.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    pager.setCurrentItem(position);
                }
            });

            tab.setPadding(tabPadding, 0, tabPadding, 0);
            tabsContainer.addView(tab, position, shouldExpand ? expandedTabLayoutParams : defaultTabLayoutParams);
        }

        private void updateTabStyles() {

            for (int i = 0; i < tabCount; i++) {

                View v = tabsContainer.getChildAt(i);

                v.setBackgroundResource(tabBackgroundResId);

                if (v instanceof TextView) {

                    TextView tab = (TextView) v;
                    tab.setTextSize(TypedValue.COMPLEX_UNIT_PX, tabTextSize);
                    tab.setTypeface(tabTypeface, tabTypefaceStyle);
                    tab.setTextColor(tabTextColor);

                    // setAllCaps() is only available from API 14, so the upper case is made manually if we are on a
                    // pre-ICS-build
                    if (textAllCaps) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                            tab.setAllCaps(true);
                        } else {
                            tab.setText(tab.getText().toString().toUpperCase(locale));
                        }
                    }
                }
            }

        }

        private void scrollToChild(int position, int offset) {

            if (tabCount == 0) {
                return;
            }

            int newScrollX = tabsContainer.getChildAt(position).getLeft() + offset;

            if (position > 0 || offset > 0) {
                newScrollX -= scrollOffset;
            }

            if (newScrollX != lastScrollX) {
                lastScrollX = newScrollX;
                scrollTo(newScrollX, 0);
            }

        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            if (isInEditMode() || tabCount == 0) {
                return;
            }

            final int height = getHeight();

            // draw indicator line

            rectPaint.setColor(indicatorColor);

            // default: line below current tab
            View currentTab = tabsContainer.getChildAt(currentPosition);
            float lineLeft = currentTab.getLeft();
            float lineRight = currentTab.getRight();

            // if there is an offset, start interpolating left and right coordinates between current and next tab
            if (currentPositionOffset > 0f && currentPosition < tabCount - 1) {

                View nextTab = tabsContainer.getChildAt(currentPosition + 1);
                final float nextTabLeft = nextTab.getLeft();
                final float nextTabRight = nextTab.getRight();

                lineLeft = (currentPositionOffset * nextTabLeft + (1f - currentPositionOffset) * lineLeft);
                lineRight = (currentPositionOffset * nextTabRight + (1f - currentPositionOffset) * lineRight);
            }

            canvas.drawRect(lineLeft, height - indicatorHeight, lineRight, height, rectPaint);

            // draw underline

            rectPaint.setColor(underlineColor);
            canvas.drawRect(0, height - underlineHeight, tabsContainer.getWidth(), height, rectPaint);

            // draw divider

            dividerPaint.setColor(dividerColor);
            for (int i = 0; i < tabCount - 1; i++) {
                View tab = tabsContainer.getChildAt(i);
                canvas.drawLine(tab.getRight(), dividerPadding, tab.getRight(), height - dividerPadding, dividerPaint);
            }
        }

        private class PageListener implements OnPageChangeListener {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                currentPosition = position;
                currentPositionOffset = positionOffset;

                scrollToChild(position, (int) (positionOffset * tabsContainer.getChildAt(position).getWidth()));

                invalidate();

                if (delegatePageListener != null) {
                    delegatePageListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    scrollToChild(pager.getCurrentItem(), 0);
                }

                if (delegatePageListener != null) {
                    delegatePageListener.onPageScrollStateChanged(state);
                }
            }

            @Override
            public void onPageSelected(int position) {
                if (delegatePageListener != null) {
                    delegatePageListener.onPageSelected(position);
                }
            }

        }

        public void setIndicatorColor(int indicatorColor) {
            this.indicatorColor = indicatorColor;
            invalidate();
        }

        public void setIndicatorColorResource(int resId) {
            this.indicatorColor = getResources().getColor(resId);
            invalidate();
        }

        public int getIndicatorColor() {
            return this.indicatorColor;
        }

        public void setIndicatorHeight(int indicatorLineHeightPx) {
            this.indicatorHeight = indicatorLineHeightPx;
            invalidate();
        }

        public int getIndicatorHeight() {
            return indicatorHeight;
        }

        public void setUnderlineColor(int underlineColor) {
            this.underlineColor = underlineColor;
            invalidate();
        }

        public void setUnderlineColorResource(int resId) {
            this.underlineColor = getResources().getColor(resId);
            invalidate();
        }

        public int getUnderlineColor() {
            return underlineColor;
        }

        public void setDividerColor(int dividerColor) {
            this.dividerColor = dividerColor;
            invalidate();
        }

        public void setDividerColorResource(int resId) {
            this.dividerColor = getResources().getColor(resId);
            invalidate();
        }

        public int getDividerColor() {
            return dividerColor;
        }

        public void setUnderlineHeight(int underlineHeightPx) {
            this.underlineHeight = underlineHeightPx;
            invalidate();
        }

        public int getUnderlineHeight() {
            return underlineHeight;
        }

        public void setDividerPadding(int dividerPaddingPx) {
            this.dividerPadding = dividerPaddingPx;
            invalidate();
        }

        public int getDividerPadding() {
            return dividerPadding;
        }

        public void setScrollOffset(int scrollOffsetPx) {
            this.scrollOffset = scrollOffsetPx;
            invalidate();
        }

        public int getScrollOffset() {
            return scrollOffset;
        }

        public void setShouldExpand(boolean shouldExpand) {
            this.shouldExpand = shouldExpand;
            requestLayout();
        }

        public boolean getShouldExpand() {
            return shouldExpand;
        }

        public boolean isTextAllCaps() {
            return textAllCaps;
        }

        public void setAllCaps(boolean textAllCaps) {
            this.textAllCaps = textAllCaps;
        }

        public void setTextSize(int textSizePx) {
            this.tabTextSize = textSizePx;
            updateTabStyles();
        }

        public int getTextSize() {
            return tabTextSize;
        }

        public void setTextColor(int textColor) {
            this.tabTextColor = textColor;
            updateTabStyles();
        }

        public void setTextColorResource(int resId) {
            this.tabTextColor = getResources().getColor(resId);
            updateTabStyles();
        }

        public int getTextColor() {
            return tabTextColor;
        }

        public void setTypeface(Typeface typeface, int style) {
            this.tabTypeface = typeface;
            this.tabTypefaceStyle = style;
            updateTabStyles();
        }

        public void setTabBackground(int resId) {
            this.tabBackgroundResId = resId;
        }

        public int getTabBackground() {
            return tabBackgroundResId;
        }

        public void setTabPaddingLeftRight(int paddingPx) {
            this.tabPadding = paddingPx;
            updateTabStyles();
        }

        public int getTabPaddingLeftRight() {
            return tabPadding;
        }

        @Override
        public void onRestoreInstanceState(Parcelable state) {
            SavedState savedState = (SavedState) state;
            super.onRestoreInstanceState(savedState.getSuperState());
            currentPosition = savedState.currentPosition;
            requestLayout();
        }

        @Override
        public Parcelable onSaveInstanceState() {
            Parcelable superState = super.onSaveInstanceState();
            SavedState savedState = new SavedState(superState);
            savedState.currentPosition = currentPosition;
            return savedState;
        }

        static class SavedState extends BaseSavedState {
            int currentPosition;

            public SavedState(Parcelable superState) {
                super(superState);
            }

            private SavedState(Parcel in) {
                super(in);
                currentPosition = in.readInt();
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                super.writeToParcel(dest, flags);
                dest.writeInt(currentPosition);
            }

            public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
                @Override
                public SavedState createFromParcel(Parcel in) {
                    return new SavedState(in);
                }

                @Override
                public SavedState[] newArray(int size) {
                    return new SavedState[size];
                }
            };
        }

    }
}
