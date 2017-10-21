package example.com.stockapp.view.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

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
import example.com.stockapp.view.tools.LogUtils;
import example.com.stockapp.view.tools.SysInterceptor;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/9/29.
 * 更多
 */

public class MoreActivity extends BaseActivity {

    private SwipeMenuRecyclerView outinventoryrv;
    private InventoryAdapter adapter;
    private List<InventoryEntity.DataSetBean> lists;
    private int PageIndex= 1;
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
        return R.layout.more_activity;
    }

    @Override
    protected void initView() {
        title.setText(R.string.more);
        outinventoryrv = (SwipeMenuRecyclerView) findViewById(R.id.out_inventory_rv);
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
                PopWindowUtils.getPopWindow().showGoodsPopwindow(MoreActivity.this, lists.get(position));

            }
        });
    }
    @Override
    protected void initData() {
        RequestParam param = new RequestParam();
        param.put("PageIndex", PageIndex);
        param.put("PageSize", 20);
        NetWorkUtil.getUserInfoApi(new SysInterceptor(this))
                .getOutDateGods(param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BaseEntity<InventoryEntity>>(this) {

                    @Override
                    public void onCompleted() {
                        Log.d("onCompleted", "------->>");
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        LogUtils.d("onError", "------->>" + e);
                    }

                    @Override
                    public void onNext(BaseEntity<InventoryEntity> baseEntity) {
                        super.onNext(baseEntity);
                        LogUtils.d("onNext", "------->>" + baseEntity);
                        if (baseEntity.getErrorcode() != 0) return;
                        InventoryEntity inventoryEntity = baseEntity.getData();
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
}
