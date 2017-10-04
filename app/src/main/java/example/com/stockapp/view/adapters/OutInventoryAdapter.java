package example.com.stockapp.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import example.com.stockapp.R;
import example.com.stockapp.entries.MoreAdapterModel;

/**
 * Created by Administrator on 2017/9/30.
 */

public class OutInventoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<MoreAdapterModel> data;
    private int sum;
    private AdapterListener listener;

    public OutInventoryAdapter(Context context, List<MoreAdapterModel> data) {
        this.context = context;
        this.data = data;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 3) {
            TextView textView = new TextView(context);
            textView.setTextSize(15);
            textView.setPadding((int) context.getResources().getDimension(R.dimen.x12),
                    (int) context.getResources().getDimension(R.dimen.x15),
                    (int) context.getResources().getDimension(R.dimen.x12),
                    (int) context.getResources().getDimension(R.dimen.x5));
            textView.setText(data.get(3).getTitle());
            textView.setTextColor(context.getResources().getColor(R.color.textHome));
            return new TextViewHolder(textView);
        } else if (viewType == 2) {
            return new TopViewHolder(LayoutInflater.from(context).inflate(R.layout.item_section_content, null));
        } else if (viewType == 4) {
            return new AddViewHolder(LayoutInflater.from(context).inflate(R.layout.item_section_add, null));
        } else {
            return new BottomViewHolder(LayoutInflater.from(context).inflate(R.layout.def_section_center, null));
        }
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MoreAdapterModel mySection = data.get(position);
        if (position == 3) {

        } else if (position == data.size() - 1) {//添加商品
            AddViewHolder addViewHolder = (AddViewHolder) holder;
            addViewHolder.addGoods.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.setClick();
//                        initpermission();
//                        showActivityForResult(CaptureActivity.class,111);
                }
            });
        } else if (position < 3) {
            TopViewHolder topViewHolder = (TopViewHolder) holder;
            topViewHolder.title.setText(mySection.getTitle());
            if (position == 2) {
                topViewHolder.ivRight.setVisibility(View.GONE);
            } else {
                topViewHolder.ivRight.setVisibility(View.VISIBLE);
            }
        } else {
            final BottomViewHolder bottomViewHolder = (BottomViewHolder) holder;
            bottomViewHolder.tvNumCount.setText("0");
            bottomViewHolder.ivDelive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (sum <= 0) {
                        sum = 0;
                    } else {
                        sum--;
                    }
                    bottomViewHolder.tvNumCount.setText(String.valueOf(sum));
                }
            });
            bottomViewHolder.ivAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (sum >= 999) {
                        sum = 999;
                    } else {
                        sum++;
                    }
                    bottomViewHolder.tvNumCount.setText(String.valueOf(sum));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 3) {
            return 3;
        } else if (position == data.size()-1) {
            return 4;
        } else if (position < 3) {
            return 2;
        } else {
            return 1;
        }
    }

    private class TopViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView content;
        ImageView ivRight;

        public TopViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.item_section_content_title_tv);
            content = (TextView) view.findViewById(R.id.item_section_content);
            ivRight = (ImageView) view.findViewById(R.id.item_section_iv);
        }
    }

    private class BottomViewHolder extends RecyclerView.ViewHolder {
        ImageView ivIcon;
        TextView title;
        TextView num;
        TextView date;
        EditText tvNumCount;
        ImageView ivDelive;
        ImageView ivAdd;

        public BottomViewHolder(View inflate) {
            super(inflate);
            ivIcon = ((ImageView) inflate.findViewById(R.id.iv_icon));
            title = ((TextView) inflate.findViewById(R.id.tv_title));
            num = ((TextView) inflate.findViewById(R.id.tv_num));
            date = ((TextView) inflate.findViewById(R.id.tv_date));
            tvNumCount = (EditText) inflate.findViewById(R.id.tvNumCount);
            ivDelive = (ImageView) inflate.findViewById(R.id.ivDelive);
            ivAdd = (ImageView) inflate.findViewById(R.id.ivAdd);
        }

    }

    private class TextViewHolder extends RecyclerView.ViewHolder {
        public TextViewHolder(TextView textView) {
            super(textView);
        }
    }

    private class AddViewHolder extends RecyclerView.ViewHolder {
        TextView addGoods;

        public AddViewHolder(View inflate) {
            super(inflate);
            addGoods = (TextView) inflate.findViewById(R.id.addGoods);
        }
    }

    public void setAdapterListenerInterface(
            AdapterListener listener) {
        this.listener = listener;
    }
    public interface AdapterListener{
        void setClick();
    }

}
