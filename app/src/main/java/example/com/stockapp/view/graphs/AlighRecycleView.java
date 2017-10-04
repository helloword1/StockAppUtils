package example.com.stockapp.view.graphs;

import android.content.Context;
import android.util.AttributeSet;

import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

/**
 * Created by Administrator on 2017/10/3.
 */

public class AlighRecycleView extends SwipeMenuRecyclerView {
    public AlighRecycleView(Context context) {
        super(context);
    }

    public AlighRecycleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AlighRecycleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
