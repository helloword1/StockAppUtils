package example.com.stockapp.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.jude.swipbackhelper.SwipeBackHelper;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

import cxx.utils.NotNull;
import example.com.stockapp.R;
import example.com.stockapp.entries.BaseEntity;
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
    private int isDealine = 1;
    private int isInvenSum = 1;
    private List<InventoryEntity.DataSetBean> lists;
    private String barcode = "";
    private String ItemType = "";
    private String storeid = "";
    private String Sort = "";
    private String Indate = "";
    private String Qty = "";
    private int PageIndex = 1;
    private InventoryAdapter adapter;
    private boolean isHaveMore;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置右滑不finsh界面
        SwipeBackHelper.getCurrentPage(this)
                .setSwipeBackEnable(true);
        SwipeBackHelper.getCurrentPage(this).setDisallowInterceptTouchEvent(true);

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
                PopWindowUtils.getPopWindow().showGoodsPopwindow(InventoryActivity.this, lists.get(position));
            }


        });

        Intent intent = getIntent();
        if (NotNull.isNotNull(intent)&&NotNull.isNotNull(intent.getExtras())){
            Bundle extras = intent.getExtras();
            String barcode = extras.getString("BARCODE");
            if (NotNull.isNotNull(barcode)){
                this.barcode = barcode;
            }
        }
    }
    @Override
    protected void initData() {
        RequestParam param = new RequestParam();
        param.put("barcode", barcode);
        param.put("ItemType", ItemType);
        param.put("storeid", storeid);
        param.put("PageIndex", PageIndex);
        param.put("Sort", Sort);
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
                if (isDealine == 0) {//有效期排序
                    isDealine = 1;
                    tvDeaLine.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.choise_normal, 0);
                    tvDeaLine.setTextColor(getResources().getColor(R.color.choice_text));
                    Indate="";

                } else if (isDealine == 1) {
                    isDealine = 2;
                    tvDeaLine.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.choise_up, 0);
                    tvDeaLine.setTextColor(getResources().getColor(R.color.colorAccent));
                    Indate="BatchNo:1";
                } else if (isDealine == 2) {
                    isDealine = 0;
                    tvDeaLine.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.choise_down, 0);
                    tvDeaLine.setTextColor(getResources().getColor(R.color.colorAccent));
                    Indate="BatchNo:2";
                }
                getSort();
            }
        });
        tvInvenSum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isInvenSum == 0) {//库存量排序
                    isInvenSum = 1;
                    tvInvenSum.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.choise_normal, 0);
                    tvInvenSum.setTextColor(getResources().getColor(R.color.choice_text));
                    Qty="";
                } else if (isInvenSum == 1) {
                    isInvenSum = 2;
                    tvInvenSum.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.choise_up, 0);
                    tvInvenSum.setTextColor(getResources().getColor(R.color.colorAccent));
                    Qty="Qty:1";
                } else if (isInvenSum == 2) {
                    isInvenSum = 0;
                    tvInvenSum.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.choise_down, 0);
                    tvInvenSum.setTextColor(getResources().getColor(R.color.colorAccent));
                    Qty="Qty:2";
                }
                getSort();
            }
        });

    }
    private void getSort(){
        if (TextUtils.equals(Qty,"")&&TextUtils.equals(Indate,"")){
            Sort="";
        }else if (TextUtils.equals(Qty,"")){
            Sort=Indate;
        }else if (TextUtils.equals(Indate,"")){
            Sort=Qty;
        }else {
            Sort=Indate+","+Qty;
        }
        lists.clear();
        initData();

    }
}
