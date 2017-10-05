package example.com.stockapp.view.activities;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
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

import example.com.stockapp.R;

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
    private CardView llHome;

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
        this.tvadvertoralauthor1 = (TextView) findViewById(R.id.tv_advertoral_author1);
        this.tvadvertoralnum1 = (TextView) findViewById(R.id.tv_advertoral_num1);
        this.tvadvertoraltitle1 = (TextView) findViewById(R.id.tv_advertoral_title1);
        this.ivadvertoralicon1 = (ImageView) findViewById(R.id.iv_advertoral_icon1);
        this.GoodOne = (RelativeLayout) findViewById(R.id.GoodOne);
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

    @Override
    protected void initData() {
        titleRight.setOnClickListener(this);
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
                showActivityForResult(CaptureActivity.class,111);
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
                showActivity(MoreActivity.class);
                break;
            case R.id.homeKu://库存
                showActivity(InventoryActivity.class);
                break;
        }
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

}
