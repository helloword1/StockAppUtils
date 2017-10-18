package example.com.stockapp.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import cxx.utils.NotNull;
import example.com.stockapp.R;
import example.com.stockapp.entries.MoreAdapterModel;
import example.com.stockapp.entries.SearchForCode;
import example.com.stockapp.view.tools.Constant;

/**
 * Created by Administrator on 2017/9/30.
 */

public class OutInventoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<MoreAdapterModel> data;
    private int sum;
    private AdapterListener listener;
    private List<SearchForCode.BatchNosBean> batchNosBean;
    private int index = 0;
    private List<EditText> editTextList = new ArrayList<>();

    public OutInventoryAdapter(Context context, List<MoreAdapterModel> data) {
        this.context = context;
        this.data = data;

    }

    public void setBatchNosBean(List<SearchForCode.BatchNosBean> batchNosBean) {
        this.batchNosBean = batchNosBean;
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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
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
            final TopViewHolder topViewHolder = (TopViewHolder) holder;
            topViewHolder.title.setText(mySection.getTitle());
            if (position == 2) {
                topViewHolder.ivRight.setVisibility(View.GONE);
                topViewHolder.etContent.setVisibility(View.VISIBLE);
                topViewHolder.content.setVisibility(View.GONE);

                topViewHolder.etContent.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        listener.getMoreEditText(topViewHolder.etContent);
                    }
                });
                String content = mySection.getContent();
                if (NotNull.isNotNull(content))
                    topViewHolder.etContent.setText(content);
            } else {
                topViewHolder.etContent.setVisibility(View.GONE);
                topViewHolder.content.setVisibility(View.VISIBLE);
                topViewHolder.ivRight.setVisibility(View.VISIBLE);
                String content = mySection.getContent();
                if (NotNull.isNotNull(content))
                    topViewHolder.content.setText(content);
            }
            if (position == 1) {
                listener.setUserText(topViewHolder.content);
            }

            topViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.setItemClick(position, topViewHolder.content, topViewHolder.etContent);
                }
            });
        } else {
            final BottomViewHolder bottomViewHolder = (BottomViewHolder) holder;
            bottomViewHolder.title.setText(mySection.getItemName());
            Glide.with(context).load(Constant.BASE_IMG_HEAD_URL + mySection.getPic1()).placeholder(R.mipmap.advertol_icon).into(bottomViewHolder.ivIcon);
            bottomViewHolder.date.setText("库存量：" + mySection.getStockQty());
            bottomViewHolder.num.setText("有效期：" + mySection.getBatchNos());
            if (!editTextList.contains(bottomViewHolder.tvNumCount))
                editTextList.add(bottomViewHolder.tvNumCount);
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
        } else if (position == data.size() - 1) {
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
        EditText etContent;
        ImageView ivRight;
        RelativeLayout cardView;

        public TopViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.item_section_content_title_tv);
            cardView = (RelativeLayout) view.findViewById(R.id.card_view0);
            content = (TextView) view.findViewById(R.id.item_section_content);
            ivRight = (ImageView) view.findViewById(R.id.item_section_iv);
            etContent = (EditText) view.findViewById(R.id.item_et_content);
        }
    }

    public List<EditText> getEditTextList() {
        return editTextList;
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

    public interface AdapterListener {
        void setClick();

        void setUserText(TextView user);

        void getMoreEditText(EditText etContent);

        void setItemClick(int position, TextView content, EditText etContent);
    }

}
