package com.demo.suctionview.widgets;

import android.content.Context;
import android.graphics.Point;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.widget.ViewDragHelper;
import android.support.v4.widget.ViewDragHelper.Callback;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DragViewLayout extends LinearLayout {
    private Point mAutoBackOriginPos;
    private ViewDragHelper mDragger;

    public TextView mTestSuction;
    public TextView mTestPosition;

    public DragViewLayout(Context context) {
        this(context, null);
    }

    public DragViewLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragViewLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mAutoBackOriginPos = new Point();
        init();
    }

    private void init() {
        this.mDragger = ViewDragHelper.create(this, 1.0f, new Callback() {
            public boolean tryCaptureView(View child, int pointerId) {
                if (child == DragViewLayout.this.mTestPosition) {
                    child.setAlpha(1.0f);
                }
                return true;
            }

            public int clampViewPositionHorizontal(View child, int left, int dx) {
                int leftBound = DragViewLayout.this.getPaddingLeft();
                return Math.min(Math.max(left, leftBound), (DragViewLayout.this.getWidth() - child.getWidth()) - leftBound);
            }

            public int clampViewPositionVertical(View child, int top, int dy) {
                int topBound = DragViewLayout.this.getPaddingTop();
                return Math.min(Math.max(top, topBound), (DragViewLayout.this.getHeight() - child.getHeight()) - topBound);
            }

            public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
                super.onViewPositionChanged(changedView, left, top, dx, dy);
            }

            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                if (releasedChild == DragViewLayout.this.mTestPosition) {
                    DragViewLayout.this.mDragger.settleCapturedViewAt(
                            DragViewLayout.this.mAutoBackOriginPos.x, DragViewLayout.this.mAutoBackOriginPos.y);
                }
                if (releasedChild == DragViewLayout.this.mTestSuction) {
                    int layoutWidth = DragViewLayout.this.getMeasuredWidth();//布局测量宽度
                    int layoutHeight = DragViewLayout.this.getMeasuredHeight();//布局测量高度
                    int viewWidth = releasedChild.getMeasuredWidth();//小球测量宽度
                    int viewHeight = releasedChild.getMeasuredHeight();//小球测量宽度

                    int centerX = layoutWidth / 2 - viewWidth / 2;//小球中心与布局中心x距离
                    int centerY = layoutHeight / 2 - viewHeight / 2;//小球中心与布局中心y距离

                    int viewX = releasedChild.getLeft();
                    int viewY = releasedChild.getTop();

                    int paddingLeft = DragViewLayout.this.getPaddingLeft();
                    int paddingRight = DragViewLayout.this.getPaddingRight();
                    int paddingTop = DragViewLayout.this.getPaddingTop();
                    int paddingBottom = DragViewLayout.this.getPaddingBottom();

                    int left = paddingLeft + viewX;
                    int right = ((layoutWidth - viewX) - viewWidth) - paddingRight;
                    int top = paddingTop + viewY;
                    int bottom = ((layoutHeight - viewY) - viewHeight) - paddingBottom;
                    releasedChild.setAlpha(0.6f);

                    if (viewX < centerX) {
                        if (viewY < centerY) {
                            if (left < viewY - paddingTop) {
                                DragViewLayout.this.mDragger.smoothSlideViewTo(releasedChild, paddingLeft, viewY);
                            } else {
                                DragViewLayout.this.mDragger.smoothSlideViewTo(releasedChild, left, paddingTop);
                            }
                        } else if (left < bottom) {
                            DragViewLayout.this.mDragger.smoothSlideViewTo(releasedChild, paddingLeft, viewY);
                        } else {
                            DragViewLayout.this.mDragger.smoothSlideViewTo(releasedChild, left, layoutHeight - viewHeight);
                        }
                    } else if (viewY < centerY) {
                        if (right < viewY - DragViewLayout.this.getPaddingTop()) {
                            DragViewLayout.this.mDragger.smoothSlideViewTo(releasedChild, layoutWidth - viewWidth, viewY);
                        } else {
                            DragViewLayout.this.mDragger.smoothSlideViewTo(releasedChild, left, paddingTop);
                        }
                    } else if (right < bottom) {
                        DragViewLayout.this.mDragger.smoothSlideViewTo(releasedChild, layoutWidth - viewWidth, viewY);
                    } else {
                        DragViewLayout.this.mDragger.smoothSlideViewTo(releasedChild, left, layoutHeight - viewHeight);
                    }
                }
                DragViewLayout.this.invalidate();
            }

            public int getViewHorizontalDragRange(View child) {
                return DragViewLayout.this.getMeasuredWidth() - child.getMeasuredWidth();
            }

            public int getViewVerticalDragRange(View child) {
                return DragViewLayout.this.getMeasuredHeight() - child.getMeasuredHeight();
            }
        });
        this.mDragger.setEdgeTrackingEnabled(1);
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = MotionEventCompat.getActionMasked(ev);
        if (action != 3 && action != 1) {
            return this.mDragger.shouldInterceptTouchEvent(ev);
        }
        this.mDragger.cancel();
        return false;
    }

    public boolean onTouchEvent(MotionEvent ev) {
        this.mDragger.processTouchEvent(ev);
        return true;
    }

    public void computeScroll() {
        if (this.mDragger.continueSettling(true)) {
            invalidate();
        }
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        this.mAutoBackOriginPos.x = this.mTestPosition.getLeft();
        this.mAutoBackOriginPos.y = this.mTestPosition.getTop();
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mTestSuction = (TextView) getChildAt(0);
        this.mTestPosition = (TextView) getChildAt(1);
    }
}
