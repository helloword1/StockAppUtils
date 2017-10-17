package example.com.stockapp.view.activities;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
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
import com.yanzhenjie.recyclerview.swipe.SwipeItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import cxx.utils.NotNull;
import example.com.stockapp.R;
import example.com.stockapp.entries.BaseEntity;
import example.com.stockapp.entries.DialogBean;
import example.com.stockapp.entries.MoreAdapterModel;
import example.com.stockapp.entries.OutGoodsItems;
import example.com.stockapp.entries.RequestParam;
import example.com.stockapp.entries.SearchForCode;
import example.com.stockapp.entries.UserInfo;
import example.com.stockapp.entries.UserList;
import example.com.stockapp.https.DefaultObserver;
import example.com.stockapp.https.NetWorkUtil;
import example.com.stockapp.view.adapters.OutInventoryAdapter;
import example.com.stockapp.view.graphs.MyToast;
import example.com.stockapp.view.graphs.PopWindowUtils;
import example.com.stockapp.view.graphs.SADialogUtils;
import example.com.stockapp.view.tools.FileCache;
import example.com.stockapp.view.tools.LogUtils;
import example.com.stockapp.view.tools.SysInterceptor;
import okhttp3.RequestBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static example.com.stockapp.view.tools.Constant.CURRENT_USER;
import static example.com.stockapp.view.tools.Constant.M_USER_LIST;
import static example.com.stockapp.view.tools.Constant.USER_LIST;

/**
 * Created by Administrator on 2017/9/29.
 * 出库
 */

public class OutInventoryActivity extends BaseActivity {
    private SwipeMenuRecyclerView outmorerv;
    private android.widget.Button btnoutinventory;
    private OutInventoryAdapter adapter;
    private List<MoreAdapterModel> mData;
    int width;
    private RelativeLayout linearLayout6;
    private AlertDialog dialog6;
    private List<DialogBean> datas;
    private List<DialogBean> datas_M;
    private TextView tvStock;
    private String storeId = "";
    private String remarkStr = "";
    private String dataStr;
    private int addPotion = 3;
    private String CurrentUserId = "";
    private List<Integer> listInts = new ArrayList<>();
    private List<OutGoodsItems> outGoodsItemses = new ArrayList<>();
    private BaseEntity<UserInfo> baseEntity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getContentView() {
        return R.layout.out_activity;
    }

    @Override
    protected void initView() {
        title.setText(R.string.outInventory);
        this.btnoutinventory = (Button) findViewById(R.id.btn_out_inventory);
        this.outmorerv = (SwipeMenuRecyclerView) findViewById(R.id.out_more_rv);
        linearLayout6 = (RelativeLayout) getLayoutInflater().inflate(R.layout.out_fail, null);
        customerPageSetting(linearLayout6);


    }

    private void customerPageSetting(RelativeLayout linearLayout6) {
        linearLayout6.findViewById(R.id.ivOutDelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NotNull.isNotNull(dialog6)) {
                    dialog6.dismiss();
                }
            }
        });
    }

    @Override
    protected void initData() {
        mData = new ArrayList<>();
        datas = new ArrayList<>();
        datas_M = new ArrayList<>();
        outmorerv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new OutInventoryAdapter(this, mData);
        outmorerv.setAdapter(adapter);
        setData();
        width = (int) getResources().getDimension(R.dimen.x52);
        // 设置监听器。
        outmorerv.setSwipeMenuCreator(mSwipeMenuCreator);
        outmorerv.setSwipeMenuItemClickListener(mMenuItemClickListener);

        btnoutinventory.setOnClickListener(new View.OnClickListener() {//出仓
            @Override
            public void onClick(View v) {
                AddGood();

            }
        });
        outmorerv.setSwipeItemClickListener(new SwipeItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                if (position == 6) {
                    initpermission();
                    showActivityForResult(CaptureActivity.class, 111);
                }
            }
        });

        adapter.setAdapterListenerInterface(new OutInventoryAdapter.AdapterListener() {


            @Override
            public void setClick() {
                if (!NotNull.isNotNull(tvStock)) {
                    MyToast.showToastCustomerStyleText(OutInventoryActivity.this, "请选择仓库");
                } else {
                    if (TextUtils.equals(tvStock.getText().toString(), "请点击选择")) {
                        MyToast.showToastCustomerStyleText(OutInventoryActivity.this, "请选择仓库");
                    } else {
//                        dataStr = "6931037800803";
//                        setCode(dataStr);
                        initpermission();
                        showActivityForResult(CaptureActivity.class, 111);
                    }
                }
            }

            @Override
            public void setUserText(TextView user) {
//                user.setText(preferences.getStringValue(CURRENT_USER));
//                mData.get(1).setContent(preferences.getStringValue(CURRENT_USER));
            }

            @Override
            public void getMoreEditText(EditText user) {
                remarkStr = user.getText().toString();
                mData.get(2).setContent(remarkStr);
            }

            @Override
            public void setItemClick(int position, final TextView content, EditText etContent) {
                LogUtils.d("", "" + position);
                if (position == 0) {//仓库
                    tvStock = content;

                    if (!NotNull.isNotNull(baseEntity)) return;
                    List<UserInfo.StoresAuthorized> storesAuthorized = baseEntity.getData().getStoresAuthorized();
                    if (!NotNull.isNotNull(storesAuthorized)) return;
                    if (datas.size() == 0) {
                        for (int i = 0; i < storesAuthorized.size(); i++) {
                            UserInfo.StoresAuthorized authorized = storesAuthorized.get(i);
                            datas.add(new DialogBean(authorized.getStoreName(), "" + authorized.getStoreId()));
                        }
                    }

                    final SADialogUtils dialogUtils = new SADialogUtils(OutInventoryActivity.this);
                    dialogUtils.showSADialog(datas);
                    dialogUtils.setClickListenerInterface(new SADialogUtils.DialogClickListener() {


                        @Override
                        public void doClick(int position) {
                            for (int i = 0; i < datas.size(); i++) {
                                if (i == position) {
                                    datas.get(position).setSelct(true);
                                    storeId = datas.get(position).getId();
                                } else {
                                    datas.get(i).setSelct(false);
                                }
                            }
                            dialogUtils.notifyAdapter();
                            content.setText(datas.get(position).getTypeName());
                            mData.get(0).setContent(datas.get(position).getTypeName());
                            CurrentUserId = datas.get(position).getId();
                        }
                    });
                } else if (position == 1) {//操作人
                    BaseEntity<List<UserList>> baseEntity = (BaseEntity<List<UserList>>) FileCache.get(OutInventoryActivity.this).getAsObject(M_USER_LIST);
                    if (!NotNull.isNotNull(baseEntity)) return;
                    List<UserList> storesAuthorized = baseEntity.getData();
                    if (!NotNull.isNotNull(storesAuthorized)) return;
                    if (datas_M.size() == 0) {
                        for (int i = 0; i < storesAuthorized.size(); i++) {
                            UserList authorized = storesAuthorized.get(i);
                            datas_M.add(new DialogBean(authorized.getUserName(), "" + authorized.getUserCode()));
                        }
                    }
                    final SADialogUtils dialogUtils = new SADialogUtils(OutInventoryActivity.this);
                    dialogUtils.showSADialog(datas_M);
                    dialogUtils.setClickListenerInterface(new SADialogUtils.DialogClickListener() {
                        @Override
                        public void doClick(int position) {
                            for (int i = 0; i < datas_M.size(); i++) {
                                if (i == position) {
                                    datas_M.get(position).setSelct(true);
                                } else {
                                    datas_M.get(i).setSelct(false);
                                }
                            }
                            dialogUtils.notifyAdapter();
                            content.setText(datas_M.get(position).getTypeName());
                            mData.get(1).setContent(datas_M.get(position).getTypeName());
                        }
                    });
                } else {//备注

                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 111) {
                String dataStr = data.getStringExtra("DATA");
                if (TextUtils.equals(dataStr, "Fail")) {//扫描失败

                } else {//成功
//                    Toast.makeText(this,
//                            "识别结果:" + dataStr,
//                            Toast.LENGTH_SHORT).show();
                    setCode(dataStr);

                }

            }
        }
    }

    private void setCode(String dataStr) {

        RequestParam param1 = new RequestParam();
        param1.put("barcode", dataStr);
        if (!NotNull.isNotNull(storeId)) {
            MyToast.showToastCustomerStyleText(this, "请选择仓库");
            return;
        }
        param1.put("storeid", storeId);
        NetWorkUtil.getUserInfoApi(new SysInterceptor(this))
                .getOutGoods(param1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BaseEntity<SearchForCode>>(this) {

                    @Override
                    public void onCompleted() {
                        LogUtils.d("onCompleted", "------->>");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.d("onError", "------->>" + e);
                    }

                    @Override
                    public void onNext(BaseEntity<SearchForCode> baseEntity) {
                        super.onNext(baseEntity);
                        SearchForCode searchFotCode = baseEntity.getData();
                        if (!NotNull.isNotNull(searchFotCode)) {
                            if (NotNull.isNotNull(dialog6)) {
                                dialog6.show();
                                return;
                            }
                            dialog6 = getDialongView(linearLayout6);
                            setWindowCenter(dialog6);
                        }
                        final SearchForCode.ItemBean item = searchFotCode.getItem();
                        if (!NotNull.isNotNull(item)) {
                            if (NotNull.isNotNull(dialog6)) {
                                dialog6.show();
                                return;
                            }
                            dialog6 = getDialongView(linearLayout6);
                            setWindowCenter(dialog6);
                        }
                        final List<SearchForCode.BatchNosBean> batchNos = searchFotCode.getBatchNos();
                        if (!NotNull.isNotNull(batchNos) || (NotNull.isNotNull(batchNos) && batchNos.size() == 0)) {
                            if (NotNull.isNotNull(dialog6)) {
                                dialog6.show();
                                return;
                            }
                            dialog6 = getDialongView(linearLayout6);
                            setWindowCenter(dialog6);
                        } else {

                            List<String> datas = new ArrayList<>();
                            datas.add("");
                            for (int i = 0; i < batchNos.size(); i++) {
                                SearchForCode.BatchNosBean batchNosBean = batchNos.get(i);
                                String productDate = batchNosBean.getProductDate();
                                if (!NotNull.isNotNull(productDate)) productDate = "--";
                                String batchNo = batchNosBean.getBatchNo();
                                if (!NotNull.isNotNull(batchNo)) batchNo = "--";
                                datas.add(productDate + " 至 " + batchNo);
                            }
                            PopWindowUtils popWindow = PopWindowUtils.getPopWindow();
                            popWindow.showButtonPopwindow(OutInventoryActivity.this, true, datas);
                            popWindow.setClickListenerInterface(new PopWindowUtils.PopWindowClickListener() {
                                @Override
                                public void doClick(int potion) {

                                    Log.d("doClick", "------->>" + potion);
                                    SearchForCode.BatchNosBean batchNosBean = batchNos.get(potion);
                                    int stockQty = batchNosBean.getStockQty();
                                    if (stockQty != 0) {
                                        if (listInts.contains(potion)) {
                                            MyToast.showToastCustomerStyleText(OutInventoryActivity.this, "你已选择该商品");
                                            return;
                                        }
                                        listInts.add(potion);
                                        MoreAdapterModel moreAdapterModel = new MoreAdapterModel("", "", true);
                                        moreAdapterModel.setPic1(item.getPic1());
                                        moreAdapterModel.setStockQty(stockQty);
                                        moreAdapterModel.setItemName(item.getItemName());
                                        addPotion += 1;
                                        mData.add(addPotion, moreAdapterModel);
                                        Log.d("doClick", "------->>" + mData);
                                        adapter.setBatchNosBean(batchNos);
                                        OutGoodsItems outGoodsItems = new OutGoodsItems();
                                        outGoodsItems.setItemName(batchNosBean.getItemName());
                                        outGoodsItems.setBatchNo(batchNosBean.getBatchNo());
                                        outGoodsItems.setImgUrl(item.getPic1());
                                        outGoodsItems.setRemark(batchNosBean.getRemark());
                                        outGoodsItems.setItemBarcode(batchNosBean.getBarcode());
                                        outGoodsItems.setItemID(batchNosBean.getItemID());
                                        outGoodsItemses.add(outGoodsItems);
                                        adapter.notifyDataSetChanged();
                                    } else {
                                        if (NotNull.isNotNull(dialog6)) {
                                            dialog6.show();
                                            return;
                                        }
                                        dialog6 = getDialongView(linearLayout6);
                                        setWindowCenter(dialog6);
                                    }
                                }
                            });
                        }

                    }
                });

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

    // 创建菜单：
    SwipeMenuCreator mSwipeMenuCreator = new SwipeMenuCreator() {
        // 1. MATCH_PARENT 自适应高度，保持和Item一样高;
        // 2. 指定具体的高，比如80;
        // 3. WRAP_CONTENT，自身高度，不推荐;
        int height = ViewGroup.LayoutParams.MATCH_PARENT;

        @Override
        public void onCreateMenu(SwipeMenu leftMenu, SwipeMenu rightMenu, int viewType) {
            if (viewType == 1) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(OutInventoryActivity.this)
                        .setBackground(R.color.textHomered)
                        .setText("删除")
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                rightMenu.addMenuItem(deleteItem);// 添加菜单到右侧。
            }
        }
    };

    /**
     * RecyclerView的Item的Menu点击监听。
     */
    private SwipeMenuItemClickListener mMenuItemClickListener = new SwipeMenuItemClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge) {
            menuBridge.closeMenu();
            int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
            int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。
            if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
                Toast.makeText(OutInventoryActivity.this, "" + adapterPosition, Toast.LENGTH_SHORT).show();
                outGoodsItemses.remove(adapterPosition - 4);
                mData.remove(adapterPosition);
            }
        }
    };

    private void setData() {
        if (mData.size() != 0) {
            mData.clear();
        }
        List<MoreAdapterModel> list = new ArrayList<>();
        list.add(new MoreAdapterModel("仓库", "", true));
        MoreAdapterModel user = new MoreAdapterModel("操作人", "", true);
        user.setContent(preferences.getStringValue(CURRENT_USER));
        list.add(user);
        list.add(new MoreAdapterModel("备注", "", true));

        list.add(new MoreAdapterModel("出库商品", "", true));

        list.add(new MoreAdapterModel("last", "", true));

        baseEntity = (BaseEntity<UserInfo>) FileCache.get(OutInventoryActivity.this).getAsObject(USER_LIST);
        List<UserInfo.StoresAuthorized> storesAuthorized = baseEntity.getData().getStoresAuthorized();
        if (storesAuthorized.size()==1){
            mData.get(0).setContent(storesAuthorized.get(0).getStoreName());
            storeId =""+storesAuthorized.get(0).getStoreId();
        }
        mData.addAll(list);
        adapter.notifyDataSetChanged();


    }

    private void setWindowCenter(AlertDialog dialog) {
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.CENTER;
        lp.width = (int) getResources().getDimension(R.dimen.x240);
        window.setAttributes(lp);
    }

    private AlertDialog getDialongView(View view) {
        final AlertDialog.Builder builder6 = new AlertDialog.Builder(this);
        builder6.setView(view);
        builder6.create();
        return builder6.show();
    }

    private void AddGood() {
        JSONObject Bill = new JSONObject();
        JSONObject object1 = new JSONObject();
        List<EditText> editTextList = adapter.getEditTextList();
        try {
            Bill.put("OutstockType", "101");
            Bill.put("OutstockDate", getcurrentDate());
            Bill.put("PrincipalID", CurrentUserId);
            Bill.put("StoreID", storeId);
            Bill.put("Remark", remarkStr);
            JSONArray item = new JSONArray();
            for (int i = 0; i < outGoodsItemses.size(); i++) {
                JSONObject object = new JSONObject();
                OutGoodsItems outGoodsItems = outGoodsItemses.get(i);
                object.put("BatchNo", outGoodsItems.getBatchNo());
                object.put("ItemID", outGoodsItems.getItemID());
                object.put("ItemName", outGoodsItems.getItemName());
                object.put("ItemBarcode", outGoodsItems.getItemBarcode());
                object.put("ImgUrl", outGoodsItems.getImgUrl());
                String Qty = editTextList.get(i).getText().toString();
                if (Integer.valueOf(Qty)==0){
                    MyToast.showToastCustomerStyleText(OutInventoryActivity.this, "出库数量不能为0");
                    return;
                }
                object.put("Qty", Qty);
                object.put("Remark", outGoodsItems.getRemark());
                item.put(i, object);
            }
            object1.put("Bill", Bill);
            object1.put("Items", item);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), object1.toString());
        NetWorkUtil.getUserInfoApi(new SysInterceptor(this))
                .AddOutGoods(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BaseEntity<Integer>>(this) {

                    @Override
                    public void onCompleted() {
                        LogUtils.d("onCompleted", "------->>");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.d("onError", "------->>" + e);
                    }

                    @Override
                    public void onNext(BaseEntity<Integer> baseEntity) {
                        super.onNext(baseEntity);
                        if (NotNull.isNotNull(baseEntity)) {
                            if (baseEntity.getErrorcode() != 0) {
                                MyToast.showToastCustomerStyleText(OutInventoryActivity.this, baseEntity.getErrormsg());
                            } else {
                                MyToast.showToastCustomerStyleText(OutInventoryActivity.this, "出库成功");
                                finish();
                            }
                        }

                    }
                });
    }

    private String getcurrentDate() {
        return new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis());

    }
}
