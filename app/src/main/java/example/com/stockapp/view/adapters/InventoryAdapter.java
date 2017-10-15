package example.com.stockapp.view.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import cxx.utils.NotNull;
import example.com.stockapp.R;
import example.com.stockapp.entries.InventoryEntity;
import example.com.stockapp.view.tools.Constant;

/**
 * Created by Administrator on 2017/9/30.
 */

public class InventoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final LayoutInflater inflate;
    private Context context;
    private List<InventoryEntity.DataSetBean> datas;
    private OnGetAdapterListener listener;

    public InventoryAdapter(Context context, List<InventoryEntity.DataSetBean> datas) {
        this.context = context;
        this.datas = datas;
        inflate = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new IntenvoryViewHolder(inflate.inflate(R.layout.inventory_shop, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        IntenvoryViewHolder intenvoryViewHolder = (IntenvoryViewHolder) holder;
        if (position == 0) {
            intenvoryViewHolder.viewSpace.setVisibility(View.VISIBLE);
        } else {
            intenvoryViewHolder.viewSpace.setVisibility(View.GONE);
        }
        InventoryEntity.DataSetBean dataSetBean = datas.get(position);
        intenvoryViewHolder.tvadvertoraltitle.setText(dataSetBean.getItemName());
        intenvoryViewHolder.tvadvertoralauthor.setText("负责人：" + dataSetBean.getPrincipalName());
        double aDouble = Double.valueOf(dataSetBean.getQty());
        intenvoryViewHolder.tvadvertoralnum.setText("库存量：" + (int) aDouble);
        intenvoryViewHolder.tvAdvertoralShop.setText("所属仓库：" + dataSetBean.getStoreName());
        intenvoryViewHolder.tvAdvertoralShop.setText("所属仓库：" + dataSetBean.getStoreName());
        String productDate = dataSetBean.getProductDate();
        if (NotNull.isNotNull(productDate)) {
            intenvoryViewHolder.proDate.setText("生产日期：" + productDate);
        } else {
            intenvoryViewHolder.proDate.setText("生产日期：");
        }
        String batchNo = dataSetBean.getBatchNo();
        if (NotNull.isNotNull(batchNo)) {
            intenvoryViewHolder.endDate.setText("有效日期：" + batchNo);
        } else {
            intenvoryViewHolder.endDate.setText("有效日期：");
        }
        Glide.with(context).load(Constant.BASE_IMG_HEAD_URL + dataSetBean.getPic1).placeholder(R.mipmap.advertol_icon).into(intenvoryViewHolder.ivadvertoralicon);
        intenvoryViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.itemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    private class IntenvoryViewHolder extends RecyclerView.ViewHolder {
        private android.widget.ImageView ivadvertoralicon;
        private TextView tvadvertoraltitle;
        private TextView tvadvertoralnum;
        private TextView tvadvertoralauthor;
        private TextView tvAdvertoralShop;
        private TextView proDate;
        private TextView endDate;
        private android.widget.RelativeLayout GoodOne;
        private View viewSpace;
        private CardView cardView;

        public IntenvoryViewHolder(View view) {
            super(view);
            this.GoodOne = (RelativeLayout) view.findViewById(R.id.GoodOne);
            this.tvadvertoralauthor = (TextView) view.findViewById(R.id.tv_advertoral_author);
            this.tvadvertoralnum = (TextView) view.findViewById(R.id.tv_advertoral_num);
            this.tvAdvertoralShop = (TextView) view.findViewById(R.id.tv_advertoral_shop);
            this.tvadvertoraltitle = (TextView) view.findViewById(R.id.tv_advertoral_title);
            this.proDate = (TextView) view.findViewById(R.id.pro_date);
            this.endDate = (TextView) view.findViewById(R.id.end_date);
            this.ivadvertoralicon = (ImageView) view.findViewById(R.id.iv_advertoral_icon);
            this.cardView = (CardView) view.findViewById(R.id.cardView);
            this.viewSpace = view.findViewById(R.id.view_space);
        }
    }

    public interface OnGetAdapterListener {
        void itemClick(int position);
    }

    public void setoOnGetAdapterListener(OnGetAdapterListener listener) {
        this.listener = listener;
    }
}
