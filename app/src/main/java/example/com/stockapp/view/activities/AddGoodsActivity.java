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

import com.bigkoo.pickerview.TimePickerView;
import com.jude.swipbackhelper.SwipeBackHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cxx.utils.NotNull;
import example.com.stockapp.R;
import example.com.stockapp.entries.BaseEntity;
import example.com.stockapp.entries.DialogBean;
import example.com.stockapp.entries.EnterGoodsBean;
import example.com.stockapp.entries.GoodsDetails;
import example.com.stockapp.entries.Grassify;
import example.com.stockapp.entries.RequestParam;
import example.com.stockapp.entries.ResultItem;
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

import static example.com.stockapp.view.tools.Constant.CURRENT_USER;
import static example.com.stockapp.view.tools.Constant.M_USER_LIST;

/**
 * Created by Administrator on 2017/10/3.
 */

public class AddGoodsActivity extends BaseActivity {

    private RecyclerView entergoodsrv;
    private EnterGoodsAdapter mAdapter;
    private EditText[] editArrays;
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
    private String ProductDate = "";
    private String Indate = "";
    private String ItemType;
    private String[] inputTypeStr = {"食品", "药品", "其他"};
    private String[] inputTypeId = {"0", "1", "100"};
    private BaseEntity<List<UserList>> baseEntity;
    private AlertDialog dialog6;
    private long time1;
    private long time2;
    private boolean in_inventory;

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
        editArrays = new EditText[6];
        data = new GoodsDetails();
        data.setItemType(-1);
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
        datas.add(new EnterGoodsBean("生产日期", "点击选择", true, false));
        datas.add(new EnterGoodsBean("有效期至", "点击选择", true, false));
        datas.add(new EnterGoodsBean("负责人", "点击选择", true, false));
        datas.add(new EnterGoodsBean("主供应商", "输入商品供应商名称", false, true));
        datas.add(new EnterGoodsBean("备注", "输入其他信息", false, true));
        datas.add(new EnterGoodsBean("", "", false, false));

        datas.get(3).setContent("点击选择");
        datas.get(4).setContent("点击选择");
        datas.get(6).setContent("点击选择");
        datas.get(7).setContent("点击选择");
        String stringValue = preferences.getStringValue(CURRENT_USER);
        datas.get(8).setContent(stringValue);
        baseEntity = (BaseEntity<List<UserList>>) FileCache.get(AddGoodsActivity.this).getAsObject(M_USER_LIST);
        for (int i = 0; i < baseEntity.getData().size(); i++) {
            if (TextUtils.equals(baseEntity.getData().get(i).getUserName(), stringValue)) {
                datas.get(8).setId(String.valueOf(baseEntity.getData().get(i).getUserID()));
            }
        }
        //首页二维码
        Intent intent = getIntent();
        if (NotNull.isNotNull(intent) && NotNull.isNotNull(intent.getExtras())) {
            Bundle extras = intent.getExtras();
            String barcode = extras.getString("BARCODE");
            if (NotNull.isNotNull(barcode)) {
                barCode = barcode;
                datas.get(2).setContent(barCode);
            }
            in_inventory = extras.getBoolean("IN_INVENTORY", false);

        }

        mAdapter = new EnterGoodsAdapter(this, datas, true);
        entergoodsrv.setAdapter(mAdapter);
        mAdapter.setoOnGetAdapterListener(new EnterGoodsAdapter.OnGetAdapterListener() {


            @Override
            public void getEitTextData(int position, EditText editText) {
                Log.d("getEitTextData", "------->>" + position);

                switch (position) {
                    case 0:
                        editArrays[0] = editText;
                        break;
                    case 1:
                        editArrays[1] = editText;
                        break;
                    case 2:
                        editArrays[2] = editText;
                        break;
                    case 5:
                        editArrays[3] = editText;
                        break;
                    case 9:
                        editArrays[4] = editText;
                        break;
                    case 10:
                        editArrays[5] = editText;
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
                    recyclerView.setLayoutManager(new LinearLayoutManager(AddGoodsActivity.this));
                    final DialogListViewAdapter adapter = new DialogListViewAdapter(AddGoodsActivity.this, mDatas);
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

                } else if (mPosition == 6) {//生产日期
                    TimePickerView pvTime = new TimePickerView.Builder(AddGoodsActivity.this, new TimePickerView.OnTimeSelectListener() {
                        @Override
                        public void onTimeSelect(Date date, View v) {//选中事件回调
                            ProductDate = getTime(date);//生产日期
                            time1 = date.getTime();
                            datas.get(mPosition).setContent(ProductDate);
                            notifyEditContent();
                            mAdapter.notifyDataSetChanged();
                        }
                    }).setLabel("", "", "", "", "", "").setSubCalSize(17).setSubmitColor(getResources().getColor(R.color.colorAccent)).setCancelColor(R.color.colorAccent).setType(new boolean[]{true, true, true, false, false, false}).isDialog(true).build();
                    pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
                    pvTime.show();

                } else if (mPosition == 7) {//有效日期

                    TimePickerView pvTime = new TimePickerView.Builder(AddGoodsActivity.this, new TimePickerView.OnTimeSelectListener() {
                        @Override
                        public void onTimeSelect(Date date, View v) {//选中事件回调
                            time2 = date.getTime();
                            if (time2 < time1) {
                                MyToast.showToastCustomerStyleText(AddGoodsActivity.this, "有效日期不能少于生产日期");
                                return;
                            }
                            Indate = getTime(date);//生产日期
                            datas.get(mPosition).setContent(Indate);
                            notifyEditContent();
                            mAdapter.notifyDataSetChanged();
                        }
                    }).setLabel("", "", "", "", "", "").setSubCalSize(17).setSubmitColor(getResources().getColor(R.color.colorAccent)).setCancelColor(R.color.colorAccent).setType(new boolean[]{true, true, true, false, false, false}).isDialog(true).build();
                    pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
                    pvTime.show();
                } else if (mPosition == 8) {//负责人
                    mDatas.clear();
                    if (!NotNull.isNotNull(baseEntity)) return;
                    List<UserList> storesAuthorized = baseEntity.getData();
                    if (!NotNull.isNotNull(storesAuthorized)) return;
                    for (int i = 0; i < storesAuthorized.size(); i++) {
                        UserList authorized = storesAuthorized.get(i);
                        DialogBean dialogBean = new DialogBean(authorized.getUserName(), "" + authorized.getUserID());
                        mDatas.add(dialogBean);
                        if (NotNull.isNotNull(datas.get(8).getId()) && Integer.valueOf(datas.get(8).getId()) == authorized.getUserID()) {
                            dialogBean.setSelct(true);
                        }
                    }
                    recyclerView.setLayoutManager(new LinearLayoutManager(AddGoodsActivity.this));
                    final DialogListViewAdapter adapter = new DialogListViewAdapter(AddGoodsActivity.this, mDatas);
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
    }

    private void getGlassify(final RecyclerView recyclerView, final int mPosition, final View view) {
        RequestParam param = new RequestParam();
        NetWorkUtil.getInventoryApi(new SysInterceptor(this))
                .getGlassify(param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BaseEntity<List<Grassify>>>(AddGoodsActivity.this) {

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

                        recyclerView.setLayoutManager(new LinearLayoutManager(AddGoodsActivity.this));
                        final DialogListViewAdapter adapter = new DialogListViewAdapter(AddGoodsActivity.this, mDatas);
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

    @Override
    protected void onBackForImage(List<String> data, Bitmap bitmap) {
        if (!NotNull.isNotNull(data)) {
            return;
        }
        Pic1 = data.get(0);
        Pic2 = data.get(1);
        datas.get(11).setPic2Url(Pic2);
        datas.get(11).setPic1Url(Pic1);
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
        NetWorkUtil.addGoodsApi(new SysInterceptor(AddGoodsActivity.this))
                .addGoods(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BaseEntity<Integer>>(AddGoodsActivity.this) {

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
                            if (in_inventory) {//表示入库进来，返回要新增一条
                                Intent intent = new Intent();
                                ResultItem resultItem = new ResultItem();
                                resultItem.setPic1(Pic1);
                                resultItem.setItemName(name);
                                resultItem.setBatchNo(Indate);
                                resultItem.setRemark(textMore);
                                resultItem.setBarcode(barCode);
                                resultItem.setItemID(response.getData());
                                intent.putExtra("RESULT_ITEM", resultItem);
                                setResult(RESULT_OK, intent);
                            }
                            MyToast.showToastCustomerStyleText(AddGoodsActivity.this, "新增成功");
                            finish();
                        }
                    }
                });

    }

    private boolean isVail() {
        ItemType = datas.get(3).getId();
        CategoryID = datas.get(4).getId();
        PrincipalID = datas.get(8).getId();

        name = getEditTextStr(editArrays[0]);
        editCode = getEditTextStr(editArrays[1]);
        barCode = getEditTextStr(editArrays[2]);
        Spec = getEditTextStr(editArrays[3]);
        shopAuhtor = getEditTextStr(editArrays[4]);
        textMore = getEditTextStr(editArrays[5]);
        Pic2 = datas.get(11).getPic2Url();
        Pic1 = datas.get(11).getPic1Url();
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
        if (!NotNull.isNotNull(ProductDate)) {
            MyToast.showToastCustomerStyleText(this, "请选择生产日期");
            return false;
        }
        if (!NotNull.isNotNull(Indate)) {
            MyToast.showToastCustomerStyleText(this, "请选择有效");
            return false;
        }
        if (!NotNull.isNotNull(PrincipalID)) {
            MyToast.showToastCustomerStyleText(this, "请选择负责人");
            return false;
        }
        if (!NotNull.isNotNull(Pic1) || Pic1 == "") {
            MyToast.showToastCustomerStyleText(this, "请选择负商品图片");
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

    private String getTime(Date date) {
        SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd");
        return myFmt.format(date);
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
        datas.get(9).setContent(getEditTextStr(editArrays[4]));
        datas.get(10).setContent(getEditTextStr(editArrays[5]));
    }
}
