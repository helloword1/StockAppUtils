package example.com.stockapp.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import cxx.utils.NotNull;
import example.com.stockapp.R;
import example.com.stockapp.entries.InventoryEntity;
import example.com.stockapp.view.activities.BaseActivity;
import example.com.stockapp.view.activities.MoreActivity;
import example.com.stockapp.view.tools.Constant;

/**
 * Created by Administrator on 2017/9/30.
 */

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final LayoutInflater inflate;
    private Context context;
    private List<InventoryEntity.DataSetBean> datas;
    private OnGetAdapterListener listener;

    public HomeAdapter(Context context, List<InventoryEntity.DataSetBean> datas) {
        this.context = context;
        this.datas = datas;
        inflate = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new IntenvoryViewHolder(inflate.inflate(R.layout.home_recycle_view_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        IntenvoryViewHolder intenvoryViewHolder = (IntenvoryViewHolder) holder;
        if (position == 0) {
            intenvoryViewHolder.rl1.setVisibility(View.VISIBLE);
        } else {
            intenvoryViewHolder.rl1.setVisibility(View.GONE);
        }
        InventoryEntity.DataSetBean dataSetBean = datas.get(position);
        intenvoryViewHolder.tvhometitle.setText(dataSetBean.getItemName());
        intenvoryViewHolder.tvhomeauthor.setText("负责人：" + dataSetBean.getPrincipalName());
        double aDouble = Double.valueOf(dataSetBean.getQty());
        intenvoryViewHolder.tvhomenum.setText("库存数量：" + (int) aDouble);
        intenvoryViewHolder.tvhomeshop.setText("所属仓库：" + dataSetBean.getStoreName());
        Glide.with(context).load(Constant.BASE_IMG_HEAD_URL + dataSetBean.getPic2()).placeholder(R.mipmap.advertol_icon).into(intenvoryViewHolder.ivhomeicon);
        intenvoryViewHolder.GoodOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NotNull.isNotNull(listener))
                listener.itemClick(position);
            }
        });
        intenvoryViewHolder.homeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//点击更多
                ((BaseActivity) context).showActivity(MoreActivity.class);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    private class IntenvoryViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivhomeicon;
        private TextView tvhometitle;
        private TextView tvhomeauthor;
        private TextView tvhomeshop;
        private TextView tvhomenum;
        private TextView homeMore;
        private android.widget.LinearLayout ll1;
        private RelativeLayout GoodOne;
        private RelativeLayout rl1;

        public IntenvoryViewHolder(View view) {
            super(view);
            this.GoodOne = (RelativeLayout) view.findViewById(R.id.GoodOne);
            this.rl1 = (RelativeLayout) view.findViewById(R.id.rl1);
            this.ll1 = (LinearLayout) view.findViewById(R.id.ll1);
            this.tvhomenum = (TextView) view.findViewById(R.id.tv_home_num);
            this.tvhomeshop = (TextView) view.findViewById(R.id.tv_home_shop);
            this.tvhomeauthor = (TextView) view.findViewById(R.id.tv_home_author);
            this.tvhometitle = (TextView) view.findViewById(R.id.tv_home_title);
            this.ivhomeicon = (ImageView) view.findViewById(R.id.iv_home_icon);
            this.homeMore = (TextView) view.findViewById(R.id.homeMore);
        }
    }

    public interface OnGetAdapterListener {
        void itemClick(int position);
    }

    public void setoOnGetAdapterListener(OnGetAdapterListener listener) {
        this.listener = listener;
    }
}
