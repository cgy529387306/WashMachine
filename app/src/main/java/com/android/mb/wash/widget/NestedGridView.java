package com.android.mb.wash.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;
/**
 * 内嵌GridView
 * @author cgy
 *
 */
public class NestedGridView extends GridView { 

    public NestedGridView(Context context, AttributeSet attrs) { 
        super(context, attrs); 
    } 

    public NestedGridView(Context context) { 
        super(context); 
    } 

    public NestedGridView(Context context, AttributeSet attrs, int defStyle) { 
        super(context, attrs, defStyle); 
    } 

    @Override 
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) { 

        int expandSpec = MeasureSpec.makeMeasureSpec( 
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST); 
        super.onMeasure(widthMeasureSpec, expandSpec); 
    } 
} 