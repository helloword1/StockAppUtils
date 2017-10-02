package example.com.stockapp.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import example.com.stockapp.R;

/**
 * Created by Administrator on 2017/9/30.
 */

public class InventoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final LayoutInflater inflate;
    private Context context;
    private List<String> datas;

    public InventoryAdapter(Context context, List<String> datas) {
        this.context = context;
        this.datas = datas;
        inflate = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new IntenvoryViewHolder(inflate.inflate(R.layout.inventory_shop, null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


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
        private android.widget.RelativeLayout GoodOne;

        public IntenvoryViewHolder(View view) {
            super(view);
            this.GoodOne = (RelativeLayout) view.findViewById(R.id.GoodOne);
            this.tvadvertoralauthor = (TextView) view.findViewById(R.id.tv_advertoral_author);
            this.tvadvertoralnum = (TextView) view.findViewById(R.id.tv_advertoral_num);
            this.tvadvertoraltitle = (TextView) view.findViewById(R.id.tv_advertoral_title);
            this.ivadvertoralicon = (ImageView) view.findViewById(R.id.iv_advertoral_icon);
        }
    }
}
