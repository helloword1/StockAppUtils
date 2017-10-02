package example.com.stockapp.view.graphs;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import example.com.stockapp.R;

/**
 * Created by Administrator on 2017/10/1.
 */

public class NumCountLinear extends LinearLayout {
    private int sum = 0;
    private TextView tvNumCount;

    public NumCountLinear(Context context) {
        super(context);
        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.num_count_linear, null);
        ImageView ivDelive = (ImageView) view.findViewById(R.id.ivDelive);
        tvNumCount = (TextView) view.findViewById(R.id.tvNumCount);
        tvNumCount.setText("0");
        ImageView ivAdd = (ImageView) view.findViewById(R.id.ivAdd);
        ivDelive.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sum <= 0) {
                    sum = 0;
                } else {
                    sum--;
                }
                tvNumCount.setText(String.valueOf(sum));
            }
        });
        ivAdd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sum >= 999) {
                    sum = 999;
                } else {
                    sum++;
                }
                tvNumCount.setText(String.valueOf(sum));
            }
        });
        this.addView(view);
    }

    public NumCountLinear(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

}
