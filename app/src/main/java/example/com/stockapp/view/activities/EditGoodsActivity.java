package example.com.stockapp.view.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.jude.swipbackhelper.SwipeBackHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cxx.utils.NotNull;
import example.com.stockapp.R;
import example.com.stockapp.entries.BaseEntity;
import example.com.stockapp.entries.DialogBean;
import example.com.stockapp.entries.EnterGoodsBean;
import example.com.stockapp.entries.GoodsDetails;
import example.com.stockapp.entries.Grassify;
import example.com.stockapp.entries.RequestParam;
import example.com.stockapp.entries.UserList;
import example.com.stockapp.https.DefaultObserver;
import example.com.stockapp.https.NetWorkUtil;
import example.com.stockapp.view.adapters.DialogListViewAdapter;
import example.com.stockapp.view.adapters.EnterGoodsAdapter;
import example.com.stockapp.view.graphs.MyToast;
import example.com.stockapp.view.tools.FileCache;
import example.com.stockapp.view.tools.LogUtils;
import example.com.stockapp.view.tools.SysInterceptor;
import okhttp3.RequestBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static example.com.stockapp.view.tools.Constant.M_USER_LIST;

/**
 * Created by Administrator on 2017/10/3.
 */

public class EditGoodsActivity extends BaseActivity {
    private RecyclerView entergoodsrv;
    private EnterGoodsAdapter mAdapter;
    private String name;
    private String editCode;
    private String barCode;
    private String standard;
    private String shopAuhtor = "";
    private String textMore = "";
    private String PrincipalID;
    private String Status = "0";
    private String Pic1 = "";
    private String Pic2 = "";
    private String Spec;
    private String ItemID = "0";
    private String CreateUserID;
    private String CategoryID;
    private List<DialogBean> mDatas = new ArrayList<>();
    private GoodsDetails data;
    private ArrayList<EnterGoodsBean> datas;
    private EditText[] editArrays;
    private String ProductDate = "";
    private String Indate = "";
    private String ItemType;
    private String[] inputTypeStr = {"食品", "药品", "其他"};
    private String[] inputTypeId = {"0", "1", "100"};
    private BaseEntity<List<UserList>> baseEntity;
    private AlertDialog dialog6;

    @Override
    public int getContentView() {
        return R.layout.enter_goods_activity;
    }

    @Override
    protected void initView() {
        //设置右滑不finsh界面
        SwipeBackHelper.getCurrentPage(this)
                .setSwipeBackEnable(true);
        SwipeBackHelper.getCurrentPage(this).setDisallowInterceptTouchEvent(true);

        title.setText(R.string.GoodsInfo);
        this.entergoodsrv = (RecyclerView) findViewById(R.id.enter_goods_rv);
    }

    @Override
    protected void initData() {
        editArrays=new EditText[6];
        LinearLayoutManager manager = new LinearLayoutManager(this);
        entergoodsrv.setLayoutManager(manager);
        entergoodsrv.setNestedScrollingEnabled(false);
        datas = new ArrayList<>();
        datas.add(new EnterGoodsBean("商品名称", "输入完整名称", false, true));
        datas.add(new EnterGoodsBean("商品编码", "输入商品编码", false, true));
        datas.add(new EnterGoodsBean("商品条码", "输入商品条码", false, true));
        datas.add(new EnterGoodsBean("商品类型", "点击选择", true, false));
        datas.add(new EnterGoodsBean("商品分类", "点击选择", true, false));
        datas.add(new EnterGoodsBean("规格", "输入商品规格", false, true));
//        datas.add(new EnterGoodsBean("生产日期","点击选择",true,false));
//        datas.add(new EnterGoodsBean("有效期至","点击选择",true,false));
        datas.add(new EnterGoodsBean("负责人", "点击选择", true, false));
        datas.add(new EnterGoodsBean("主供应商", "输入商品供应商名称", false, true));
        datas.add(new EnterGoodsBean("备注", "输入其他信息", false, true));
        datas.add(new EnterGoodsBean("", "", false, false));
        mAdapter = new EnterGoodsAdapter(this, datas, false);
        baseEntity = (BaseEntity<List<UserList>>) FileCache.get(EditGoodsActivity.this).getAsObject(M_USER_LIST);
        entergoodsrv.setAdapter(mAdapter);
        mAdapter.setoOnGetAdapterListener(new EnterGoodsAdapter.OnGetAdapterListener() {


            @Override
            public void getEitTextData(int position, EditText editText) {
                switch (position){
                    case 0:
                        editArrays[0]=editText;
                        break;
                    case 1:
                        editArrays[1]=editText;
                        break;
                    case 2:
                        editArrays[2]=editText;
                        break;
                    case 5:
                        editArrays[3]=editText;
                        break;
                    case 7:
                        editArrays[4]=editText;
                        break;
                    case 8:
                        editArrays[5]=editText;
                        break;
                }
            }

            @Override
            public void itemClick(final int mPosition) {
                View view = getLayoutInflater().inflate(R.layout.dialog_listview, null);
                RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.dialogRecycleView);

                if (mPosition == 3) {//商品类型
                    mDatas.clear();
                    DialogBean dialogBean = new DialogBean("食品", "0");
                    DialogBean dialogBean1 = new DialogBean("药品", "1");
                    DialogBean dialogBean2 = new DialogBean("其他", "100");
                    if (data.getItemType() == 0) {
                        dialogBean.setSelct(true);
                    } else if (data.getItemType() == 1) {
                        dialogBean1.setSelct(true);
                    } else if (data.getItemType() == 100) {
                        dialogBean2.setSelct(true);
                    }
                    mDatas.add(dialogBean);
                    mDatas.add(dialogBean1);
                    mDatas.add(dialogBean2);
                    recyclerView.setLayoutManager(new LinearLayoutManager(EditGoodsActivity.this));
                    final DialogListViewAdapter adapter = new DialogListViewAdapter(EditGoodsActivity.this, mDatas);
                    recyclerView.setAdapter(adapter);

                    adapter.setoOnGetAdapterListener(new DialogListViewAdapter.OnGetAdapterListener() {
                        @Override
                        public void itemClick(int position) {

                            if (position == 0) {
                                data.setItemType(0);
                            } else if (position == 1) {
                                data.setItemType(1);
                            } else {
                                data.setItemType(100);
                            }
                            datas.get(mPosition).setContent(mDatas.get(position).getTypeName());
                            datas.get(mPosition).setId(mDatas.get(position).getId());
                            notifyEditContent();
                            mAdapter.notifyDataSetChanged();
                            adapter.notifyDataSetChanged();
                            dialog6.dismiss();
                        }
                    });
                    dialog6 = getDialongView(view);
                    setWindowCenter(dialog6);
                } else if (mPosition == 4) {//分类
                    mDatas.clear();
                    getGlassify(recyclerView, mPosition, view);

                } else if (mPosition == 6) {//负责人
                    mDatas.clear();

                    if (!NotNull.isNotNull(baseEntity)) return;
                    List<UserList> storesAuthorized = baseEntity.getData();
                    if (!NotNull.isNotNull(storesAuthorized)) return;
                    for (int i = 0; i < storesAuthorized.size(); i++) {
                        UserList authorized = storesAuthorized.get(i);
                        DialogBean dialogBean = new DialogBean(authorized.getUserName(), "" + authorized.getUserID());
                        mDatas.add(dialogBean);
                        if (NotNull.isNotNull(datas.get(6).getId()) && Integer.valueOf(datas.get(6).getId()) == authorized.getUserID()) {
                            dialogBean.setSelct(true);
                        }
                    }
                    recyclerView.setLayoutManager(new LinearLayoutManager(EditGoodsActivity.this));
                    final DialogListViewAdapter adapter = new DialogListViewAdapter(EditGoodsActivity.this, mDatas);
                    recyclerView.setAdapter(adapter);
                    adapter.setoOnGetAdapterListener(new DialogListViewAdapter.OnGetAdapterListener() {
                        @Override
                        public void itemClick(int position) {
                            datas.get(mPosition).setContent(mDatas.get(position).getTypeName());
                            datas.get(mPosition).setId(mDatas.get(position).getId());
                            notifyEditContent();
                            mAdapter.notifyDataSetChanged();
                            dialog6.dismiss();
                        }
                    });
                    dialog6 = getDialongView(view);
                    setWindowCenter(dialog6);
                }


            }
        });
        Intent intent = getIntent();
        if (NotNull.isNotNull(intent)) {
            Bundle extras = intent.getExtras();
            ItemID = extras.getString("ItemID");
            if (NotNull.isNotNull(ItemID)) {
                getPopuData(ItemID);
            }

        }
    }

    private void getGlassify(final RecyclerView recyclerView, final int mPosition, final View view) {
        RequestParam param = new RequestParam();
        NetWorkUtil.getInventoryApi(new SysInterceptor(this))
                .getGlassify(param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BaseEntity<List<Grassify>>>(EditGoodsActivity.this) {

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
                    public void onNext(BaseEntity<List<Grassify>> objectBaseEntity) {
                        super.onNext(objectBaseEntity);
                        final List<Grassify> data = objectBaseEntity.getData();
                        if (!NotNull.isNotNull(data)) return;
                        for (int i = 0; i < data.size(); i++) {
                            DialogBean dialogBean = new DialogBean(data.get(i).getCategoryName(), "" + data.get(i).getCategoryID());
                            mDatas.add(dialogBean);
                            if (NotNull.isNotNull(datas.get(4).getId()) && Integer.valueOf(datas.get(4).getId()) == data.get(i).getCategoryID()) {
                                dialogBean.setSelct(true);
                            }
                        }

                        recyclerView.setLayoutManager(new LinearLayoutManager(EditGoodsActivity.this));
                        final DialogListViewAdapter adapter = new DialogListViewAdapter(EditGoodsActivity.this, mDatas);
                        recyclerView.setAdapter(adapter);

                        adapter.setoOnGetAdapterListener(new DialogListViewAdapter.OnGetAdapterListener() {
                            @Override
                            public void itemClick(int position) {
                                datas.get(mPosition).setContent(mDatas.get(position).getTypeName());
                                datas.get(mPosition).setId(mDatas.get(position).getId());
                                notifyEditContent();
                                mAdapter.notifyDataSetChanged();
                                adapter.notifyDataSetChanged();
                                dialog6.dismiss();
                            }
                        });
                        dialog6 = getDialongView(view);
                        setWindowCenter(dialog6);
                        LogUtils.i("", "" + objectBaseEntity);
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
                .subscribe(new DefaultObserver<BaseEntity<GoodsDetails>>(EditGoodsActivity.this) {

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
        datas.get(0).setContent(data.getItemName());
        datas.get(1).setContent(data.getItemCode());
        datas.get(2).setContent(data.getBarcode());
        datas.get(3).setContent(inputTypeStr[data.getItemType()]);
        datas.get(3).setId(String.valueOf(data.getItemType()));
        datas.get(4).setContent(data.getCategoryName());
        datas.get(4).setId(String.valueOf(data.getCategoryID()));
        datas.get(5).setContent(data.getSpec());
        String principalID = data.getPrincipalID();
        datas.get(6).setId(principalID);
        for (int i = 0; i < baseEntity.getData().size(); i++) {
            if (baseEntity.getData().get(i).getUserID() == Integer.valueOf(principalID)) {
                datas.get(6).setContent(baseEntity.getData().get(i).getUserName());
            }

        }
        String traderName = data.getTraderName();
        if (!NotNull.isNotNull(traderName)) traderName = "";
        datas.get(7).setContent(traderName);
        String remark = data.getRemark();
        if (!NotNull.isNotNull(remark)) remark = "";
        datas.get(8).setContent(remark);
        datas.get(9).setPic2Url(data.getPic2());
        datas.get(9).setPic1Url(data.getPic1());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onBackForImage(List<String> data, Bitmap bitmap) {
        if (!NotNull.isNotNull(data)) {
            return;
        }
        Pic1 = data.get(0);
        Pic2 = data.get(1);
        datas.get(9).setPic2Url(Pic2);
        datas.get(9).setPic1Url(Pic1);
//        mAdapter.setBitmap(bitmap);
        notifyEditContent();
        mAdapter.notifyDataSetChanged();
    }

    //添加商品
    public void onSaveGoods(View view) {
        if (isVail()) {
            addGoods();
        }

    }

    private void addGoods() {
        showProgressDialog();
        JSONObject object = new JSONObject();
        try {
            object.put("ProductDate", ProductDate);
            object.put("Indate", Indate);
            object.put("ItemID", ItemID);
            object.put("ItemType", ItemType);
            object.put("CategoryID", CategoryID);
            object.put("ItemName", name);
            object.put("ItemCode", editCode);
            object.put("Barcode", barCode);
            object.put("Spec", Spec);
            object.put("Pic1", Pic1);
            object.put("Pic2", Pic2);
            object.put("KeepTime", "30");
            object.put("Status", "1");
            object.put("PrincipalID", PrincipalID);
            object.put("TraderName", shopAuhtor);
            object.put("Remark", textMore);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), object.toString());
        NetWorkUtil.addGoodsApi(new SysInterceptor(EditGoodsActivity.this))
                .addGoods(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BaseEntity<Integer>>(EditGoodsActivity.this) {

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
                    public void onNext(BaseEntity<Integer> response) {
                        LogUtils.d("onNext", "------->>" + response);
                        if (NotNull.isNotNull(response) && response.getErrorcode() == 0) {
                            MyToast.showToastCustomerStyleText(EditGoodsActivity.this, "编辑成功");
                            finish();
                        }
                    }
                });

    }

    private boolean isVail() {
        ItemType = datas.get(3).getId();
        CategoryID = datas.get(4).getId();
        PrincipalID = datas.get(6).getId();
        name = getEditTextStr(editArrays[0]);
        editCode = getEditTextStr(editArrays[1]);
        barCode = getEditTextStr(editArrays[2]);
        Spec = getEditTextStr(editArrays[3]);
        shopAuhtor = getEditTextStr(editArrays[4]);
        textMore = getEditTextStr(editArrays[5]);
        Pic2 = datas.get(9).getPic2Url();
        Pic1 = datas.get(9).getPic1Url();
        if (!NotNull.isNotNull(name) || TextUtils.equals(name, "")) {
            MyToast.showToastCustomerStyleText(this, "请输入商品名称");
            return false;
        }
        if (!NotNull.isNotNull(editCode) || TextUtils.equals(editCode, "")) {
            MyToast.showToastCustomerStyleText(this, "请输入商品编码");
            return false;
        }
        if (!NotNull.isNotNull(barCode) || TextUtils.equals(barCode, "")) {
            MyToast.showToastCustomerStyleText(this, "请输入商品条码");
            return false;
        }
        if (!NotNull.isNotNull(ItemType)) {
            MyToast.showToastCustomerStyleText(this, "请选择商品类型");
            return false;
        }
        if (!NotNull.isNotNull(CategoryID)) {
            MyToast.showToastCustomerStyleText(this, "请选择商品分类");
            return false;
        }
        if (!NotNull.isNotNull(PrincipalID)) {
            MyToast.showToastCustomerStyleText(this, "请选择负责人");
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

    private String getEditTextStr(EditText et) {
        if (NotNull.isNotNull(et) && NotNull.isNotNull(et.getText().toString())) {
            return et.getText().toString();
        } else {
            return "";
        }
    }

    private void notifyEditContent() {
        datas.get(0).setContent(getEditTextStr(editArrays[0]));
        datas.get(1).setContent(getEditTextStr(editArrays[1]));
        datas.get(2).setContent(getEditTextStr(editArrays[2]));
        datas.get(5).setContent(getEditTextStr(editArrays[3]));
        datas.get(7).setContent(getEditTextStr(editArrays[4]));
        datas.get(8).setContent(getEditTextStr(editArrays[5]));
    }
}
