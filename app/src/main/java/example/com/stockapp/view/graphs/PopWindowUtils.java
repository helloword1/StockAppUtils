package example.com.stockapp.view.graphs;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import example.com.stockapp.R;
import example.com.stockapp.view.activities.BaseActivity;

public class PopWindowUtils {

    private static PopWindowUtils popWindowUtils = null;
    private BaseActivity base = null;

    private static boolean isShow = false;
    public PopupWindow popWindow;

    private PopWindowClickListener clickListenerInterface;

    public interface PopWindowClickListener {
        public void doClick(View v);
    }

    public static PopWindowUtils getPopWindow() {
        if (popWindowUtils == null) {
            popWindowUtils = new PopWindowUtils();
        }
        return popWindowUtils;

    }

    public void showButtonPopwindow(Context context, View button) {
        isShow = true;
        base = (BaseActivity) context;
        View popView = View.inflate(context, R.layout.pop_list_item, null);

        ImageView btnBlow = (ImageView) popView.findViewById(R.id.popu_delete);
        final TextView otherTime = (TextView) popView.findViewById(R.id.otherTime);
        final LinearLayout llBottom1 = (LinearLayout) popView.findViewById(R.id.llBottom1);
        final LinearLayout llBottom2 = (LinearLayout) popView.findViewById(R.id.llBottom2);
        final LinearLayout llBottom3 = (LinearLayout) popView.findViewById(R.id.llBottom3);
        final LinearLayout BntBottom = (LinearLayout) popView.findViewById(R.id.BntBottom);
        otherTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (llBottom1.getVisibility() == View.GONE) {
                    llBottom1.setVisibility(View.VISIBLE);
                    llBottom2.setVisibility(View.VISIBLE);
                    llBottom3.setVisibility(View.VISIBLE);
                    BntBottom.setVisibility(View.VISIBLE);
                    otherTime.setVisibility(View.GONE);
                }
            }
        });
//		Button btnShare = (Button) popView.findViewById(R.id.btnShare);
        popWindow = new PopupWindow(popView, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        btnBlow.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {// 发布动态
                popWindow.dismiss();
            }
        });
//
//		btnShare.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {// 分享
//				((ComunityActivity) base).showShare();
//				isShow = false;
//				popWindow.dismiss();
//
//			}
//		});
        popWindow.setOutsideTouchable(false);// 设置允许在外点击消失
//		popView.setOnTouchListener(new OnTouchListener() {
//
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				popWindow.dismiss();
//				return false;
//			}
//		});
        popWindow.showAtLocation(button, Gravity.TOP, 0, 0);
    }

    public void showGoodsPopwindow(Context context) {
        base = (BaseActivity) context;
        final View popView = View.inflate(context, R.layout.goods_details_item, null);
        Button tvChangeBtn = (Button) popView.findViewById(R.id.tvChangeBtn);
        TextView tvChangeRight = (TextView) popView.findViewById(R.id.tvChangeRight);
        TextView tvChangeLeft = (TextView) popView.findViewById(R.id.tvChangeLeft);
        TextView tvChangeName = (TextView) popView.findViewById(R.id.tvChangeName);
        ImageView tvChangeDelete = (ImageView) popView.findViewById(R.id.tvChangeDelete);
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
        popWindow.showAtLocation(new View(context), Gravity.TOP, 0, 0);
    }

    public PopWindowClickListener getClickListenerInterface() {
        return clickListenerInterface;
    }

    public void setClickListenerInterface(
            PopWindowClickListener clickListenerInterface) {
        this.clickListenerInterface = clickListenerInterface;
    }

}
