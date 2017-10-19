package example.com.stockapp.view.graphs;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.List;

import cxx.utils.NotNull;
import cxx.utils.TimeUtils;
import example.com.stockapp.R;
import example.com.stockapp.entries.InventoryEntity;
import example.com.stockapp.view.activities.BaseActivity;
import example.com.stockapp.view.activities.EnterGoodsActivity;
import example.com.stockapp.view.adapters.DataAdapter;
import example.com.stockapp.view.adapters.DataInAdapter;
import example.com.stockapp.view.tools.Constant;

import static cxx.utils.FileUtils.isHaveSdcard;

public class PopWindowUtils {

    private static PopWindowUtils popWindowUtils = null;
    private BaseActivity base = null;

    private static boolean isShow = false;
    public PopupWindow popWindow;
    public static final int TAKE_PHOTO = 1; //拍照
    public static final int IMAGE_CHIOCE = 2; //图片选择
    public static String takePhotoPath = "";

    private PopWindowClickListener clickListenerInterface;

    public interface PopWindowClickListener {
        void doClick(int position);
    }

    private PopWindowInClickListener clickInListener;

    public interface PopWindowInClickListener {
        void doClick(int position, TextView tvDateContent);
        void clickOther(List<String> datas);
        void clickCommit();
    }

    public void setClickInListener(
            PopWindowInClickListener clickInListener) {
        this.clickInListener = clickInListener;
    }

    public static PopWindowUtils getPopWindow() {
        if (popWindowUtils == null) {
            popWindowUtils = new PopWindowUtils();
        }
        return popWindowUtils;

    }

    public void showButtonPopwindow(Context context, boolean isOutInv, final List<String> datas) {
        isShow = true;
        base = (BaseActivity) context;
        View popView = View.inflate(context, R.layout.pop_list_item, null);
        ImageView popuDelete = ((ImageView) popView.findViewById(R.id.popu_delete));
        popuDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindow.dismiss();
            }
        });
        final RecyclerView alRView = (RecyclerView) popView.findViewById(R.id.alRView);

        LinearLayoutManager manager = new LinearLayoutManager(context);
        alRView.setLayoutManager(manager);

        final DataAdapter adapter = new DataAdapter(context, datas);
        adapter.setOutInventor(isOutInv);
        alRView.setAdapter(adapter);
        if (datas.size() > 7) {
            alRView.getLayoutParams().height = (int) context.getResources().getDimension(R.dimen.y240);
        }
        adapter.notifyDataSetChanged();
        adapter.setOnpopuOnClickLIstener(new DataAdapter.popuOnClickListener() {
            @Override
            public void setMoreTime() {
                datas.add("自定义时间");
                datas.add("生产日期");
                datas.add("有效日期");
                adapter.setOtherTime(true);
                adapter.setCommitContet("确认入库");
                adapter.notifyDataSetChanged();
            }

            @Override
            public void setChoice(int position) {
                clickListenerInterface.doClick(position);
            }

            @Override
            public void setCommit() {

            }
        });
        popWindow = new PopupWindow(popView, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        popWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popWindow.setOutsideTouchable(false);// 设置允许在外点击消失
        popWindow.showAtLocation(new View(context), Gravity.TOP, 0, 0);
    }

    public void showButtonInPopwindow(Context context, boolean isOutInv, final List<String> datas) {
        isShow = true;
        base = (BaseActivity) context;
        View popView = View.inflate(context, R.layout.pop_list_item, null);
        ImageView popuDelete = ((ImageView) popView.findViewById(R.id.popu_delete));
        popuDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindow.dismiss();
            }
        });
        final RecyclerView alRView = (RecyclerView) popView.findViewById(R.id.alRView);

        LinearLayoutManager manager = new LinearLayoutManager(context);
        alRView.setLayoutManager(manager);

        final DataInAdapter adapter = new DataInAdapter(context, datas);
        adapter.setOutInventor(isOutInv);
        alRView.setAdapter(adapter);
        if (datas.size() > 7) {
            alRView.getLayoutParams().height = (int) context.getResources().getDimension(R.dimen.y240);
        }
        adapter.notifyDataSetChanged();
        adapter.setOnpopuOnClickLIstener(new DataInAdapter.popuOnClickListener() {
            @Override
            public void setMoreTime() {
                datas.add("自定义时间");
                datas.add("生产日期");
                datas.add("有效日期");
                adapter.setOtherTime(true);
                adapter.setCommitContet("确认");
                adapter.notifyDataSetChanged();
                clickInListener.clickOther(datas);
            }

            @Override
            public void setChoice(int position, TextView tvDateContent) {
                clickInListener.doClick(position,tvDateContent);
            }

            @Override
            public void setCommit() {
                clickInListener.clickCommit();
            }
        });
        popWindow = new PopupWindow(popView, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        popWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popWindow.setOutsideTouchable(false);// 设置允许在外点击消失
        popWindow.showAtLocation(new View(context), Gravity.TOP, 0, 0);
    }

    public void showButtonPanPopwindow(Context context, boolean isOutInv, final List<String> datas) {
        isShow = true;
        base = (BaseActivity) context;
        View popView = View.inflate(context, R.layout.pop_list_item, null);
        ImageView popuDelete = ((ImageView) popView.findViewById(R.id.popu_delete));
        popuDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindow.dismiss();
            }
        });
        final RecyclerView alRView = (RecyclerView) popView.findViewById(R.id.alRView);

        LinearLayoutManager manager = new LinearLayoutManager(context);
        alRView.setLayoutManager(manager);

        final DataAdapter adapter = new DataAdapter(context, datas);
        adapter.setOutInventor(isOutInv);
        alRView.setAdapter(adapter);
        if (datas.size() > 7) {
            alRView.getLayoutParams().height = (int) context.getResources().getDimension(R.dimen.y240);
        }
        adapter.notifyDataSetChanged();
        adapter.setOnpopuOnClickLIstener(new DataAdapter.popuOnClickListener() {
            @Override
            public void setMoreTime() {
                datas.add("自定义时间");
                datas.add("生产日期");
                datas.add("有效日期");
                adapter.setOtherTime(true);
                adapter.setCommitContet("确认盘点");
                adapter.notifyDataSetChanged();
            }

            @Override
            public void setChoice(int position) {
                clickListenerInterface.doClick(position);
            }

            @Override
            public void setCommit() {
            }
        });
        popWindow = new PopupWindow(popView, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        popWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popWindow.setOutsideTouchable(false);// 设置允许在外点击消失
        popWindow.showAtLocation(new View(context), Gravity.TOP, 0, 0);
    }

    public void showGoodsPopwindow(Context context, final InventoryEntity.DataSetBean data) {

        base = (BaseActivity) context;
        final View popView = View.inflate(context, R.layout.goods_details_item, null);
        Button tvChangeBtn = (Button) popView.findViewById(R.id.tvChangeBtn);
        TextView tvChangeRight = (TextView) popView.findViewById(R.id.tvChangeRight);
        TextView tvChangeLeft = (TextView) popView.findViewById(R.id.tvChangeLeft);
        TextView tvChangeName = (TextView) popView.findViewById(R.id.tvChangeName);
        ImageView ivChangeName = (ImageView) popView.findViewById(R.id.ivChangeName);

        if (NotNull.isNotNull(data.getPic1()))
            Glide.with(context).load(Constant.BASE_IMG_HEAD_URL + data.getPic1())
                    .placeholder(R.mipmap.good_details).into(ivChangeName);

        tvChangeName.setText(data.getItemName());

        String productDate = data.getProductDate();
        if (!NotNull.isNotNull(productDate))productDate="--";
        String storeName = data.getStoreName();
        if (!NotNull.isNotNull(storeName))storeName="";
        String principalName = data.getPrincipalName();
        if (!NotNull.isNotNull(principalName))principalName="";
        tvChangeLeft.setText
                (String.format("商品编号：%s\n仓库：%s\n生产日期：%s\n负责人：%s", "" + data.getItemCode()
                        , storeName, productDate, principalName));


        String qty = data.getQty();
        if (!NotNull.isNotNull(qty))qty="";
        String keepTime = data.getKeepTime();
        if (!NotNull.isNotNull(keepTime))keepTime="--";
        String barcode = data.getBarcode();
        if (!NotNull.isNotNull(barcode))barcode="";
        tvChangeRight.setText
                (String.format("条形码：%s\n仓库量：%s\n有效日期：%s", barcode
                        , qty, keepTime));
        ImageView tvChangeDelete = (ImageView) popView.findViewById(R.id.tvChangeDelete);


        tvChangeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("ItemID", data.getItemID());
                base.showActivity(EnterGoodsActivity.class, bundle);
            }
        });
        tvChangeDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popView.setAnimation(AnimationUtils.loadAnimation(base, R.anim.adujst_close));
                popWindow.dismiss();
            }
        });
        popWindow = new PopupWindow(popView, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        popWindow.setOutsideTouchable(true);// 设置允许在外点击消失
        popView.setAnimation(AnimationUtils.loadAnimation(base, R.anim.adujst_open));
        popWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popWindow.showAtLocation(new View(context), Gravity.TOP, 0, 0);
    }

    public PopWindowClickListener getClickListenerInterface() {
        return clickListenerInterface;
    }

    public void setClickListenerInterface(
            PopWindowClickListener clickListenerInterface) {
        this.clickListenerInterface = clickListenerInterface;
    }

    public void showImagePopwindow(Context context) {
        base = (BaseActivity) context;
        View parent = ((ViewGroup) base.findViewById(android.R.id.content)).getChildAt(0);
        View popView = View.inflate(context, R.layout.pop_menu_camara, null);
        Button btnCamera = (Button) popView.findViewById(R.id.btn_camera_pop_camera);
        Button btnAlbum = (Button) popView.findViewById(R.id.btn_camera_pop_album);
        Button btnCancel = (Button) popView.findViewById(R.id.btn_camera_pop_cancel);

        int width = context.getResources().getDisplayMetrics().widthPixels;
        int height = context.getResources().getDisplayMetrics().heightPixels;


        final PopupWindow popWindow = new PopupWindow(popView, width, height);
        popWindow.setAnimationStyle(R.style.AnimBottom);
        popWindow.setTouchable(true);
        popWindow.setFocusable(true);
        popWindow.setOutsideTouchable(true);// 设置允许在外点击消失
        View.OnClickListener listener = new View.OnClickListener() {
            public void onClick(View v) {
                int id = v.getId();
                Intent intent = null;
                switch (id) {
                    case R.id.btn_camera_pop_camera: //拍照
                        intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                        takePhotoPath = getCamaraPath();
                        File file = new File(takePhotoPath);
                        Uri uri = Uri.fromFile(file);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                        base.startActivityForResult(intent, TAKE_PHOTO);
                        popWindow.dismiss();
                        break;
                    case R.id.btn_camera_pop_album:// 相册获取图片
                        intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        base.startActivityForResult(intent, IMAGE_CHIOCE);
                        popWindow.dismiss();
                        break;
                    case R.id.btn_camera_pop_cancel:
                        popWindow.dismiss();
                        break;
                }

            }
        };

        btnCamera.setOnClickListener(listener);
        btnAlbum.setOnClickListener(listener);
        btnCancel.setOnClickListener(listener);
        ColorDrawable dw = new ColorDrawable(0x30000000);
        popWindow.setBackgroundDrawable(dw);
        popWindow.showAtLocation(parent, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    private String getCamaraPath() {
        if (!isHaveSdcard()) {
            return "";
        }
        String imageFilePath = Environment.getExternalStorageDirectory() + "/DCIM/Camera/";

        String imageName = TimeUtils.getYyyymmddHHmmssFormat() + ".jpg";
        File out = new File(imageFilePath);
        if (!out.exists()) {
            out.mkdirs();
        }
        imageFilePath = imageFilePath + imageName;
        return imageFilePath;
    }
}
