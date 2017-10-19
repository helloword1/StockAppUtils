package example.com.stockapp.view.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import cxx.utils.NotNull;
import example.com.stockapp.R;
import example.com.stockapp.entries.BaseEntity;
import example.com.stockapp.entries.DialogBean;
import example.com.stockapp.entries.EnterGoodsBean;
import example.com.stockapp.entries.GoodsDetails;
import example.com.stockapp.entries.RequestParam;
import example.com.stockapp.https.DefaultObserver;
import example.com.stockapp.https.NetWorkUtil;
import example.com.stockapp.view.adapters.DialogListViewAdapter;
import example.com.stockapp.view.adapters.EnterGoodsAdapter;
import example.com.stockapp.view.graphs.AlighRecycleView;
import example.com.stockapp.view.graphs.MyToast;
import example.com.stockapp.view.tools.LogUtils;
import example.com.stockapp.view.tools.SysInterceptor;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/10/3.
 */

public class EnterGoodsActivity extends BaseActivity {

    private RecyclerView entergoodsrv;
    private EnterGoodsAdapter adapter;
    private List<EditText> editTextList;
    private String name;
    private String editCode;
    private String barCode;
    private String standard;
    private String shopAuhtor;
    private String textMore;
    private String PrincipalID;
    private String Status = "0";
    private String Pic1 = "";
    private String Pic2 = "";
    private String Spec;
    private String ItemID;
    private List<DialogBean> mDatas = new ArrayList<>();
    private GoodsDetails data;

    @Override
    public int getContentView() {
        return R.layout.enter_goods_activity;
    }

    @Override
    protected void initView() {
        title.setText(R.string.GoodsInfo);
        this.entergoodsrv = (AlighRecycleView) findViewById(R.id.enter_goods_rv);
    }

    @Override
    protected void initData() {
        editTextList = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(this);
        entergoodsrv.setLayoutManager(manager);
        ArrayList<EnterGoodsBean> datas = new ArrayList<>();
        datas.add(new EnterGoodsBean("商品名称", "输入完整名称", false, true));
        datas.add(new EnterGoodsBean("商品编码", "输入商品编码", false, true));
        datas.add(new EnterGoodsBean("商品条码", "输入商品条码", false, true));
        datas.add(new EnterGoodsBean("商品类型", "点击选择", true, false));
        datas.add(new EnterGoodsBean("分类", "点击选择", true, false));
        datas.add(new EnterGoodsBean("规格", "输入商品规格", false, true));
//        datas.add(new EnterGoodsBean("生产日期","点击选择",true,false));
//        datas.add(new EnterGoodsBean("有效期至","点击选择",true,false));
        datas.add(new EnterGoodsBean("负责人", "点击选择", true, false));
        datas.add(new EnterGoodsBean("主供应商", "输入商品供应商名称", false, true));
        datas.add(new EnterGoodsBean("备注", "输入其他信息", false, true));
        datas.add(new EnterGoodsBean("", "", false, false));
        adapter = new EnterGoodsAdapter(this, datas, false);
        entergoodsrv.setAdapter(adapter);
        adapter.setoOnGetAdapterListener(new EnterGoodsAdapter.OnGetAdapterListener() {
            @Override
            public void getEitTextData(int position, EditText editText) {
                editTextList.add(editText);
            }

            @Override
            public void itemClick(int position) {
                View view = getLayoutInflater().inflate(R.layout.dialog_listview, null);
                RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.dialogRecycleView);
                final AlertDialog dialog6 = getDialongView(view);
                if (position == 3) {//商品类型
                    mDatas.clear();
                    DialogBean dialogBean = new DialogBean("药品", "1");
                    DialogBean dialogBean1 = new DialogBean("食品", "0");
                    DialogBean dialogBean2 = new DialogBean("其他", "100");
                    if (data.getItemType() == 0) {
                        dialogBean1.setSelct(true);
                    } else if (data.getItemType() == 1) {
                        dialogBean.setSelct(true);
                    } else {
                        dialogBean2.setSelct(true);
                    }
                    mDatas.add(dialogBean);
                    mDatas.add(dialogBean1);
                    mDatas.add(dialogBean2);
                } else if (position == 4) {//分类
                    mDatas.clear();
                } else if (position == 6) {//负责人
                    mDatas.clear();
                }
                recyclerView.setLayoutManager(new LinearLayoutManager(EnterGoodsActivity.this));
                final DialogListViewAdapter adapter = new DialogListViewAdapter(EnterGoodsActivity.this, mDatas);
                recyclerView.setAdapter(adapter);
                adapter.setoOnGetAdapterListener(new DialogListViewAdapter.OnGetAdapterListener() {
                    @Override
                    public void itemClick(int position) {

                        if (position == 0) {
                            data.setItemType(1);
                        } else if (position == 1) {
                            data.setItemType(0);
                        } else {
                            data.setItemType(100);
                        }
                        adapter.notifyDataSetChanged();
                        dialog6.dismiss();
                    }
                });
                setWindowCenter(dialog6);
            }
        });
        Intent intent = getIntent();
        if (NotNull.isNotNull(intent)) {
            Bundle extras = intent.getExtras();
            if (NotNull.isNotNull(extras.getString("ItemID"))){
                getPopuData(extras.getString("ItemID"));
            }


        }
    }
    private void getPopuData(final String id) {
        showProgressDialog();
        RequestParam param = new RequestParam();
        param.put("id", id);
        NetWorkUtil.getInventoryApi(new SysInterceptor(this))
                .getGoodDetail(param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BaseEntity<GoodsDetails>>(EnterGoodsActivity.this) {

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
                        data = objectBaseEntity.getData();
                        if (NotNull.isNotNull(data)) {
                            Log.d("onNext", "------->>" + objectBaseEntity);
                            getData();
                        }
                    }
                });


    }

    private void getData() {
        showProgressDialog();
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                dismissDialog();
                if (editTextList.size() != 0) {
                    editTextList.get(0).setText(data.getItemName());
                    editTextList.get(1).setText(data.getItemCode());
                    editTextList.get(2).setText(data.getBarcode());
                    editTextList.get(3).setText(data.getSpec());
                    editTextList.get(4).setText(data.getTraderName());
                    editTextList.get(5).setText(data.getRemark());
                }
            }
        }, 1000);
    }

    @Override
    protected void onBackForImage(List<String> data, Bitmap bitmap) {
        adapter.setBitmap(bitmap);
        editTextList.clear();
        adapter.notifyDataSetChanged();
    }

    //添加商品
    public void onSaveGoods(View view) {
        if (isVail()) {
            addGoods();
        }

    }

    private void addGoods() {
        showProgressDialog();
        RequestParam param = new RequestParam();
        param.put("CategoryID", ItemID);
        param.put("ItemName", name);
        param.put("ItemCode", editCode);
        param.put("Barcode", barCode);
        param.put("Spec", Spec);
        param.put("Pic1", Pic1);
        param.put("Pic2", Pic2);
        param.put("URL", "");
        param.put("Status", Status);
//        param.put("KeepTime", psdStr);
        param.put("PrincipalID", PrincipalID);
        param.put("TraderName", shopAuhtor);
        param.put("Remark", textMore);
        NetWorkUtil.addGoodsApi(new SysInterceptor(EnterGoodsActivity.this))
                .addGoods(param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<Object>(EnterGoodsActivity.this) {

                    @Override
                    public void onCompleted() {
                        dismissDialog();
                        LogUtils.d("onCompleted", "------->>");
                    }

                    @Override
                    public void onError(Throwable e) {

                        LogUtils.d("onError", "------->>" + e);
                    }

                    @Override
                    public void onNext(Object response) {
                        LogUtils.d("onNext", "------->>" + response);
                    }
                });

    }

    private boolean isVail() {
        name = editTextList.get(0).getText().toString();
        editCode = editTextList.get(1).getText().toString();
        barCode = editTextList.get(2).getText().toString();
        standard = editTextList.get(3).getText().toString();
        shopAuhtor = editTextList.get(4).getText().toString();
        textMore = editTextList.get(5).getText().toString();
        if (!NotNull.isNotNull(name)) {
            MyToast.showToastCustomerStyleText(this, "请输入商品名称");
            return false;
        }
        if (!NotNull.isNotNull(editCode)) {
            MyToast.showToastCustomerStyleText(this, "请输入商品编码");
            return false;
        }
        if (!NotNull.isNotNull(barCode)) {
            MyToast.showToastCustomerStyleText(this, "请输入商品条码");
            return false;
        }
        return true;
    }

    private void setWindowCenter(AlertDialog dialog) {
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.CENTER;
        window.setAttributes(lp);
    }

    private AlertDialog getDialongView(View view) {
        final AlertDialog.Builder builder6 = new AlertDialog.Builder(this);
        builder6.setView(view);
        builder6.create();
        return builder6.show();
    }
}
