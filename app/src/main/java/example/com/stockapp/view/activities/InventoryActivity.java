package example.com.stockapp.view.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.yanzhenjie.recyclerview.swipe.SwipeItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

import example.com.stockapp.R;
import example.com.stockapp.view.adapters.InventoryAdapter;
import example.com.stockapp.view.graphs.PopWindowUtils;

/**
 * Created by Administrator on 2017/9/29.
 * 库存
 */

public class InventoryActivity extends BaseActivity {
    private android.widget.TextView tvDeaLine;
    private android.widget.TextView tvInvenSum;
    private com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView outinventoryrv;
    private boolean isDealine = true;
    private boolean isInvenSum = true;
    private List<String> lists;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getContentView() {
        return R.layout.inventory_activity;
    }

    @Override
    protected void initView() {
        title.setText(R.string.inventory);
        baseLine.setVisibility(View.GONE);
        titleRight.setVisibility(View.VISIBLE);
        titleRight.setImageResource(R.drawable.search_right);
        this.outinventoryrv = (SwipeMenuRecyclerView) findViewById(R.id.out_inventory_rv);
        this.tvInvenSum = (TextView) findViewById(R.id.tvInvenSum);
        this.tvDeaLine = (TextView) findViewById(R.id.tvDeaLine);
    }

    @Override
    protected void initData() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        outinventoryrv.setLayoutManager(manager);
        lists = new ArrayList<>();
        lists.add("");
        lists.add("");
        lists.add("");
        outinventoryrv.setAdapter(new InventoryAdapter(this,lists));
        tvDeaLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvDeaLine.setTextColor(getResources().getColor(R.color.colorAccent));
                if (isDealine) {
                    isDealine = false;
                    tvDeaLine.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.choise_up, 0);
                } else {
                    isDealine = true;
                    tvDeaLine.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.choise_down, 0);
                }
            }
        });
        tvInvenSum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvInvenSum.setTextColor(getResources().getColor(R.color.colorAccent));
                if (isInvenSum) {
                    isInvenSum = false;
                    tvInvenSum.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.choise_up, 0);
                } else {
                    isInvenSum = true;
                    tvInvenSum.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.choise_down, 0);
                }
            }
        });
        outinventoryrv.setSwipeItemClickListener(new SwipeItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                PopWindowUtils.getPopWindow().showGoodsPopwindow(InventoryActivity.this);
            }
        });
    }
}
