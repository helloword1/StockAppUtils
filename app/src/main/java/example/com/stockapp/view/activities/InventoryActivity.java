package example.com.stockapp.view.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

import cxx.utils.NotNull;
import example.com.stockapp.R;
import example.com.stockapp.entries.BaseEntity;
import example.com.stockapp.entries.GoodsDetails;
import example.com.stockapp.entries.InventoryEntity;
import example.com.stockapp.entries.RequestParam;
import example.com.stockapp.https.DefaultObserver;
import example.com.stockapp.https.NetWorkUtil;
import example.com.stockapp.view.adapters.InventoryAdapter;
import example.com.stockapp.view.graphs.PopWindowUtils;
import example.com.stockapp.view.tools.SysInterceptor;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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
    private List<InventoryEntity.DataSetBean> lists;
    private String barcode = "";
    private String ItemType = "";
    private String storeid = "";
    private int PageIndex = 1;
    private InventoryAdapter adapter;
    private boolean isHaveMore;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Override
    public int getContentView() {
        return R.layout.inventory_activity;
    }

    @Override
    protected void initView() {
        title.setText(R.string.inventory);
        baseLine.setVisibility(View.GONE);
        titleRight.setVisibility(View.GONE);
        titleRight.setImageResource(R.drawable.search_right);
        this.outinventoryrv = (SwipeMenuRecyclerView) findViewById(R.id.out_inventory_rv);
        this.tvInvenSum = (TextView) findViewById(R.id.tvInvenSum);
        this.tvDeaLine = (TextView) findViewById(R.id.tvDeaLine);
        lists = new ArrayList<>();
        outinventoryrv.useDefaultLoadMore(); // 使用默认的加载更多的View。
        LinearLayoutManager manager = new LinearLayoutManager(this);
        outinventoryrv.setLayoutManager(manager);
        adapter = new InventoryAdapter(this, lists);
        outinventoryrv.setAdapter(adapter);
        outinventoryrv.setLoadMoreListener(mLoadMoreListener); // 加载更多的监听。
        adapter.setoOnGetAdapterListener(new InventoryAdapter.OnGetAdapterListener() {
            @Override
            public void itemClick(int position) {
                getPopuData(lists.get(position).getItemID());

            }


        });
    }

    private void getPopuData(final String id) {
        showProgressDialog();
        RequestParam param = new RequestParam();
        param.put("id", id);
        NetWorkUtil.getInventoryApi(new SysInterceptor(this))
                .getGoodDetail(param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BaseEntity<GoodsDetails>>(InventoryActivity.this) {

                    @Override
                    public void onCompleted() {
                        dismissDialog();
                        Log.d("onCompleted", "------->>");
                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissDialog();
                        Log.d("onError", "------->>" + e);
                    }

                    @Override
                    public void onNext(BaseEntity<GoodsDetails> objectBaseEntity) {
                        super.onNext(objectBaseEntity);
                        GoodsDetails data = objectBaseEntity.getData();
                        if (NotNull.isNotNull(data)) {
                            Log.d("onNext", "------->>" + objectBaseEntity);
                            PopWindowUtils.getPopWindow().showGoodsPopwindow(InventoryActivity.this, data);
                        }
                    }
                });


    }

    protected void initData() {
        showProgressDialog();
        RequestParam param = new RequestParam();
        param.put("barcode", barcode);
        param.put("ItemType", ItemType);
        param.put("storeid", storeid);
        param.put("PageIndex", PageIndex);
        NetWorkUtil.getInventoryApi(new SysInterceptor(this))
                .getUserInfo(param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BaseEntity<InventoryEntity>>(InventoryActivity.this) {

                    @Override
                    public void onCompleted() {
                        dismissDialog();
                        Log.d("onCompleted", "------->>");
                    }

                    @Override
                    public void onError(Throwable e) {
                        dismissDialog();
                        Log.d("onError", "------->>" + e);
                    }

                    @Override
                    public void onNext(BaseEntity<InventoryEntity> objectBaseEntity) {
                        super.onNext(objectBaseEntity);
                        Log.d("onNext", "------->>" + objectBaseEntity);
                        setData(objectBaseEntity);
                    }
                });


    }

    SwipeMenuRecyclerView.LoadMoreListener mLoadMoreListener = new SwipeMenuRecyclerView.LoadMoreListener() {
        @Override
        public void onLoadMore() {
            // 该加载更多啦。
            PageIndex++;
            initData();

        }
    };

    private void setData(BaseEntity<InventoryEntity> objectBaseEntity) {
        if (objectBaseEntity.getErrorcode() != 0) return;
        InventoryEntity inventoryEntity = objectBaseEntity.getData();
        isHaveMore = true;
        if (inventoryEntity.getPageCount() == inventoryEntity.getPageIndex()) {
            isHaveMore = false;
        }
        List<InventoryEntity.DataSetBean> dataSet = inventoryEntity.getDataSet();
        if (NotNull.isNotNull(dataSet)) {
            lists.addAll(dataSet);
            adapter.notifyDataSetChanged();
            // 数据完更多数据，一定要调用这个方法。
            // 第一个参数：表示此次数据是否为空。
            // 第二个参数：表示是否还有更多数据。
            outinventoryrv.loadMoreFinish(false, isHaveMore);
        }

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

    }
}
