package example.com.stockapp.view.activities;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
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

import java.util.ArrayList;
import java.util.List;

import cxx.utils.NotNull;
import example.com.stockapp.R;
import example.com.stockapp.entries.MoreAdapterModel;
import example.com.stockapp.view.adapters.OutInventoryAdapter;
import example.com.stockapp.view.graphs.PopWindowUtils;
import example.com.stockapp.view.tools.LogUtils;

/**
 * Created by Administrator on 2017/9/29.
 * 入库
 */

public class InInventoryActivity extends BaseActivity {
    private SwipeMenuRecyclerView outmorerv;
    private android.widget.Button btnoutinventory;
    private OutInventoryAdapter adapter;
    private List<MoreAdapterModel> mData;
    int width;
    private RelativeLayout linearLayout6;
    private AlertDialog dialog6;

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
        title.setText(R.string.inInventory);
        this.btnoutinventory = (Button) findViewById(R.id.btn_out_inventory);
        btnoutinventory.setText("确认入库");
        this.outmorerv = (SwipeMenuRecyclerView) findViewById(R.id.out_more_rv);
        linearLayout6 = (RelativeLayout) getLayoutInflater().inflate(R.layout.out_fail, null);
        customerPageSetting(linearLayout6);

    }

    private void customerPageSetting(RelativeLayout linearLayout6) {
        linearLayout6.findViewById(R.id.ivOutDelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NotNull.isNotNull(dialog6)){
                    dialog6.dismiss();
                }
            }
        });
    }

    @Override
    protected void initData() {
        mData = new ArrayList<>();
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
                if (NotNull.isNotNull(dialog6)){
                    dialog6.show();
                    return;
                }
                dialog6 = getDialongView(linearLayout6);
                setWindowCenter(dialog6);
            }
        });
        outmorerv.setSwipeItemClickListener(new SwipeItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                if (position==6){
                    initpermission();
                    showActivityForResult(CaptureActivity.class,111);
                }
            }
        });
        adapter.setAdapterListenerInterface(new OutInventoryAdapter.AdapterListener() {
            @Override
            public void setClick() {
                initpermission();
                showActivityForResult(CaptureActivity.class,111);
            }

            @Override
            public void setUserText(TextView user) {

            }

            @Override
            public void getMoreText(TextView user) {

            }

            @Override
            public void setItemClick(int position, TextView content, EditText etContent) {
                LogUtils.d("",""+position);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode==RESULT_OK){
            if (requestCode==111){
                String dataStr = data.getStringExtra("DATA");
                if (TextUtils.equals(dataStr,"Fail")){//扫描失败

                }else{//成功
                    Toast.makeText(this,
                            "识别结果:" + dataStr,
                            Toast.LENGTH_SHORT).show();
                }

            }
        }
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
                SwipeMenuItem deleteItem = new SwipeMenuItem(InInventoryActivity.this)
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
                Toast.makeText(InInventoryActivity.this, ""+adapterPosition, Toast.LENGTH_SHORT).show();
                PopWindowUtils.getPopWindow().showButtonPopwindow(InInventoryActivity.this,false,new ArrayList<String>());
            }
        }
    };

    private void setData() {
        if (mData.size() != 0) {
            mData.clear();
        }
        List<MoreAdapterModel> list = new ArrayList<>();
        list.add(new MoreAdapterModel("仓库", "", true));
        list.add(new MoreAdapterModel("操作人", "", true));
        list.add(new MoreAdapterModel("备注", "", true));

        list.add(new MoreAdapterModel("入库商品", "", true));

        list.add(new MoreAdapterModel("感冒灵", "", true));
        list.add(new MoreAdapterModel("感冒灵", "", true));
        list.add(new MoreAdapterModel("last", "", true));

        mData.addAll(list);
        adapter.notifyDataSetChanged();


    }
    private void setWindowCenter(AlertDialog dialog) {
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.CENTER;
        lp.width= (int) getResources().getDimension(R.dimen.x240);
        window.setAttributes(lp);
    }
    private AlertDialog getDialongView(View view) {
        final AlertDialog.Builder builder6 = new AlertDialog.Builder(this);
        builder6.setView(view);
        builder6.create();
        return builder6.show();
    }
}
