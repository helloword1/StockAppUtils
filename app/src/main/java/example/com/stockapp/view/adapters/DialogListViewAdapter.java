package example.com.stockapp.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import example.com.stockapp.R;
import example.com.stockapp.entries.DialogBean;

/**
 * Created by Administrator on 2017/9/30.
 */

public class DialogListViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final LayoutInflater inflate;
    private Context context;
    private List<DialogBean> datas;
    private OnGetAdapterListener listener;

    public DialogListViewAdapter(Context context, List<DialogBean> datas) {
        this.context = context;
        this.datas = datas;
        inflate = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new IntenvoryViewHolder(inflate.inflate(R.layout.dialog_item, parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        IntenvoryViewHolder holder1 = (IntenvoryViewHolder) holder;
        DialogBean dialogBean = datas.get(position);
        if (dialogBean.isSelct()) {
            holder1.popuchioce.setImageResource(R.drawable.vector_ischoice);
        } else {
            holder1.popuchioce.setImageResource(R.drawable.vector_unchoice);
        }
        holder1.tvDialogType.setText(dialogBean.getTypeName());
        holder1.item_click.setOnClickListener(new View.OnClickListener() {
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


        private android.widget.TextView tvDialogType;
        private android.widget.ImageView popuchioce;
        private android.widget.LinearLayout item_click;

        public IntenvoryViewHolder(View view) {
            super(view);
            this.popuchioce = (ImageView) view.findViewById(R.id.popu_chioce);
            this.tvDialogType = (TextView) view.findViewById(R.id.tvDialogType);
            this.item_click = (LinearLayout) view.findViewById(R.id.item_click);
        }
    }

    public interface OnGetAdapterListener {
        void itemClick(int position);
    }

    public void setoOnGetAdapterListener(OnGetAdapterListener listener) {
        this.listener = listener;
    }
}
