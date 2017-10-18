package example.com.stockapp.view.activities;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jinlin.zxing.CaptureActivity;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;
import java.util.List;

import cxx.utils.NotNull;
import example.com.stockapp.R;
import example.com.stockapp.entries.BaseEntity;
import example.com.stockapp.entries.InventoryEntity;
import example.com.stockapp.entries.RequestParam;
import example.com.stockapp.entries.UserInfo;
import example.com.stockapp.entries.UserList;
import example.com.stockapp.https.DefaultObserver;
import example.com.stockapp.https.NetWorkUtil;
import example.com.stockapp.view.adapters.HomeAdapter;
import example.com.stockapp.view.tools.FileCache;
import example.com.stockapp.view.tools.LogUtils;
import example.com.stockapp.view.tools.SAApplication;
import example.com.stockapp.view.tools.SysInterceptor;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static example.com.stockapp.view.tools.Constant.M_USER_LIST;
import static example.com.stockapp.view.tools.Constant.USER_LIST;

/**
 * Created by Administrator on 2017/9/29.
 * 主页
 */

public class HomeActivity extends BaseActivity implements View.OnClickListener {
    private android.widget.TextView outInventory;
    private android.widget.TextView InInventory;
    private android.widget.TextView homePan;
    private android.widget.TextView homeKu;
    private android.widget.TextView homeMore;
    private android.widget.ImageView ivadvertoralicon;
    private android.widget.TextView tvadvertoraltitle;
    private android.widget.TextView tvadvertoralnum;
    private android.widget.TextView tvadvertoralauthor;
    private android.widget.RelativeLayout GoodOne;
    private android.widget.ImageView ivadvertoralicon1;
    private android.widget.TextView tvadvertoraltitle1;
    private android.widget.TextView tvadvertoralnum1;
    private android.widget.TextView tvadvertoralauthor1;
    private android.widget.RelativeLayout GoodTwo;
    private android.widget.LinearLayout llHomeb;
    private RecyclerView recyclerView;
    private CardView llHome;
    private long exitTime;
    private Subscription sb;
    private HomeAdapter adapter;
    private List<InventoryEntity.DataSetBean> datas;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    protected void initView() {
        //初始化titlebar
        titleBack.setVisibility(View.GONE);
        title.setText(R.string.inventorySystem);
        title.setTextColor(getResources().getColor(R.color.dddddd));
        ll.setBackgroundResource(R.color.colorPrimary);
        titleRight.setVisibility(View.VISIBLE);
        titleRight.setImageResource(R.mipmap.home_scan);
        this.GoodTwo = (RelativeLayout) findViewById(R.id.GoodTwo);
        this.recyclerView = (RecyclerView) findViewById(R.id.home_recycle_view);

        this.tvadvertoralauthor1 = (TextView) findViewById(R.id.tv_advertoral_author1);
        this.tvadvertoralnum1 = (TextView) findViewById(R.id.tv_advertoral_num1);
        this.tvadvertoraltitle1 = (TextView) findViewById(R.id.tv_advertoral_title1);
        this.ivadvertoralicon1 = (ImageView) findViewById(R.id.iv_advertoral_icon1);
        this.GoodOne = (RelativeLayout) findViewById(R.id.GoodOne);
        this.llHomeb = (LinearLayout) findViewById(R.id.llHomeb);
        this.tvadvertoralauthor = (TextView) findViewById(R.id.tv_advertoral_author);
        this.tvadvertoralnum = (TextView) findViewById(R.id.tv_advertoral_num);
        this.tvadvertoraltitle = (TextView) findViewById(R.id.tv_advertoral_title);
        this.ivadvertoralicon = (ImageView) findViewById(R.id.iv_advertoral_icon);
        this.homeMore = (TextView) findViewById(R.id.homeMore);
        llHome = (CardView) findViewById(R.id.llHome);
        this.homeKu = (TextView) findViewById(R.id.homeKu);
        this.homePan = (TextView) findViewById(R.id.homePan);
        this.InInventory = (TextView) findViewById(R.id.InInventory);
        this.outInventory = (TextView) findViewById(R.id.outInventory);
        GoodOne.setOnClickListener(this);
        homePan.setOnClickListener(this);
        homeKu.setOnClickListener(this);
        InInventory.setOnClickListener(this);
        outInventory.setOnClickListener(this);
        GoodTwo.setOnClickListener(this);
        homeMore.setOnClickListener(this);
    }
    private void getUserList() {
        RequestParam param = new RequestParam();
        NetWorkUtil.getUserInfoApi(new SysInterceptor(this))
                .getUserInfo(param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BaseEntity<UserInfo>>(this) {

                    @Override
                    public void onCompleted() {
                        LogUtils.d("onCompleted", "------->>");
                    }
                    @Override
                    public void onError(Throwable e) {
                        LogUtils.d("onError", "------->>" + e);
                    }

                    @Override
                    public void onNext(BaseEntity<UserInfo> baseEntity) {
                        super.onNext(baseEntity);
                        if (NotNull.isNotNull(baseEntity)) {
                            FileCache.get(HomeActivity.this).put(USER_LIST,baseEntity);
                        }

                    }
                });
        RequestParam param1 = new RequestParam();
        NetWorkUtil.getUserInfoApi(new SysInterceptor(this))
                .getUserList(param1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BaseEntity<List<UserList>>>(this) {

                    @Override
                    public void onCompleted() {
                        LogUtils.d("onCompleted", "------->>");
                    }
                    @Override
                    public void onError(Throwable e) {
                        LogUtils.d("onError", "------->>" + e);
                    }

                    @Override
                    public void onNext(BaseEntity<List<UserList>> baseEntity) {
                        super.onNext(baseEntity);
                        if (NotNull.isNotNull(baseEntity)) {
                            FileCache.get(HomeActivity.this).put(M_USER_LIST,baseEntity);
                        }

                    }
                });

    }
    @Override
    protected void initData() {
        titleRight.setOnClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        datas = new ArrayList<>();
        adapter = new HomeAdapter(this, datas);
        recyclerView.setAdapter(adapter);
        getUserList();
        RequestParam param = new RequestParam();
        param.put("PageIndex", 1);
        param.put("PageSize", 5);
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
                        datas.addAll(baseEntity.getData().getDataSet());
                        if (datas.size() == 0) {
                            llHomeb.setVisibility(View.VISIBLE);
                        } else {
                            llHomeb.setVisibility(View.GONE);
                            adapter.notifyDataSetChanged();
                        }

                    }
                });
    }



    @Override
    public int getContentView() {
        return R.layout.home_activity;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.titleRight://二维码
                initpermission();
                showActivityForResult(CaptureActivity.class, 111);
                break;
            case R.id.GoodOne://
                break;
            case R.id.GoodTwo:
                break;
            case R.id.homePan://盘点
                showActivity(CheckInventoryActivity.class);

                break;
            case R.id.InInventory://入库
                showActivity(InInventoryActivity.class);
                break;
            case R.id.outInventory://出库
                showActivity(OutInventoryActivity.class);
                break;
            case R.id.homeMore://更多

                break;
            case R.id.homeKu://库存
                showActivity(InventoryActivity.class);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 111) {
                String dataStr = data.getStringExtra("DATA");
                if (TextUtils.equals(dataStr, "Fail")) {//扫描失败

                } else {//成功
                    Toast.makeText(this,
                            "识别结果:" + dataStr,
                            Toast.LENGTH_SHORT).show();
                }

            }
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {

        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                // TODO 退出客户端
                // 退出
                ((SAApplication) getApplication()).exit();
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    private void initpermission() {
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {/* ... */}
                }).check();
    }

}
