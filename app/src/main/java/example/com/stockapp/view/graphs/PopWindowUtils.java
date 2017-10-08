package example.com.stockapp.view.graphs;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import example.com.stockapp.R;
import example.com.stockapp.view.activities.BaseActivity;
import example.com.stockapp.view.activities.EnterGoodsActivity;
import example.com.stockapp.view.adapters.DataAdapter;

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
        ImageView popuDelete = ((ImageView) popView.findViewById(R.id.popu_delete));
        popuDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindow.dismiss();
            }
        });
        final RecyclerView alRView = (RecyclerView) popView.findViewById(R.id.alRView);

        LinearLayoutManager manager=new LinearLayoutManager(context);
        alRView.setLayoutManager(manager);
        final List<String> datas=new ArrayList<>();
        final DataAdapter adapter = new DataAdapter(context, datas);
        alRView.setAdapter(adapter);
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        datas.add("");
        if (datas.size()>7){
            alRView.getLayoutParams().height= (int) context.getResources().getDimension(R.dimen.y240);
        }
        adapter.notifyDataSetChanged();
        adapter.setOnpopuOnClickLIstener(new DataAdapter.popuOnClickListener() {
            @Override
            public void setMoreTime() {
                datas.add("");
                datas.add("");
                datas.add("");
                adapter.setOtherTime(true);
                adapter.notifyDataSetChanged();
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
        tvChangeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                base.showActivity(EnterGoodsActivity.class);
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

}
