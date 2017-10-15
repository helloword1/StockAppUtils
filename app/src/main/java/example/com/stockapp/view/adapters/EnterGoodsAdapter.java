package example.com.stockapp.view.adapters;


import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import example.com.stockapp.R;
import example.com.stockapp.entries.EnterGoodsBean;
import example.com.stockapp.view.graphs.PopWindowUtils;

/**
 * Created by Administrator on 2017/9/30.
 */

public class EnterGoodsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private boolean isAdd;
    private LayoutInflater inflate;
    private Context context;
    private List<EnterGoodsBean> datas;
    private Bitmap bitmap;
    private OnGetAdapterListener listener;

    public EnterGoodsAdapter(Context context, List<EnterGoodsBean> datas, boolean isAdd) {
        this.context = context;
        this.datas = datas;
        this.isAdd = isAdd;
        inflate = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 1) {
            return new enterGoodsViewHolder(inflate.inflate(R.layout.enter_goods_item, null));
        } else {
            return new PicViewHolder(inflate.inflate(R.layout.pic_goods_item, null));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (!isAdd) {
            if (position >= 9) {
                return 2;
            } else {
                return 1;
            }
        } else {
            if (position >= 11) {
                return 2;
            } else {
                return 1;
            }
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (isAdd && position > 10) {
            PicViewHolder picViewHolder = (PicViewHolder) holder;
            if (position == 11) {
                picViewHolder.viewBg.setVisibility(View.GONE);
                picViewHolder.btn_confir.setVisibility(View.GONE);
                picViewHolder.tvAddPic.setVisibility(View.VISIBLE);
                if (bitmap != null) {
                    picViewHolder.ivAddPic.setImageBitmap(bitmap);
                    picViewHolder.tvAddPic.setVisibility(View.GONE);
                }
                picViewHolder.ivAddPic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PopWindowUtils.getPopWindow().showImagePopwindow(context);
                    }
                });

            } else {
                picViewHolder.viewBg.setVisibility(View.VISIBLE);
                picViewHolder.btn_confir.setVisibility(View.VISIBLE);
                picViewHolder.tvAddPic.setVisibility(View.GONE);
            }
            return;
        } else if (position > 8) {
            PicViewHolder picViewHolder = (PicViewHolder) holder;
            if (position == 9) {
                picViewHolder.viewBg.setVisibility(View.GONE);
                picViewHolder.btn_confir.setVisibility(View.GONE);
                picViewHolder.tvAddPic.setVisibility(View.VISIBLE);
                if (bitmap != null) {
                    picViewHolder.ivAddPic.setImageBitmap(bitmap);
                    picViewHolder.tvAddPic.setVisibility(View.GONE);
                }
                picViewHolder.ivAddPic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PopWindowUtils.getPopWindow().showImagePopwindow(context);
                    }
                });

            } else {
                picViewHolder.viewBg.setVisibility(View.VISIBLE);
                picViewHolder.btn_confir.setVisibility(View.VISIBLE);
                picViewHolder.tvAddPic.setVisibility(View.GONE);
            }
            return;
        }
        EnterGoodsBean enterGoodsBean = datas.get(position);
        enterGoodsViewHolder goodsViewHolder = (enterGoodsViewHolder) holder;
        goodsViewHolder.itemsectioncontenttitletv.setText(enterGoodsBean.getTitle());
        if (enterGoodsBean.isHaveRight()) {
            goodsViewHolder.itemsectioniv.setVisibility(View.VISIBLE);
            goodsViewHolder.cardview0.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.itemClick(position);
                }
            });
        } else {
            goodsViewHolder.itemsectioniv.setVisibility(View.GONE);
        }

        if (enterGoodsBean.isInput()) {
            goodsViewHolder.item_section_et.setVisibility(View.VISIBLE);
            goodsViewHolder.itemsectioncontent.setVisibility(View.GONE);
            goodsViewHolder.item_section_et.setHint(enterGoodsBean.getContent());
            listener.getEitTextData(position, goodsViewHolder.item_section_et);
        } else {
            goodsViewHolder.item_section_et.setVisibility(View.GONE);
            goodsViewHolder.itemsectioncontent.setVisibility(View.VISIBLE);
            goodsViewHolder.itemsectioncontent.setText(enterGoodsBean.getContent());
        }

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    private class enterGoodsViewHolder extends RecyclerView.ViewHolder {
        private android.widget.TextView itemsectioncontenttitletv;
        private android.widget.TextView itemsectioncontent;
        private android.widget.ImageView itemsectioniv;
        private android.widget.TextView itemsectioncontentdestv;
        private android.widget.RelativeLayout cardview0;
        private android.widget.EditText item_section_et;

        public enterGoodsViewHolder(View inflate) {
            super(inflate);
            this.cardview0 = (RelativeLayout) inflate.findViewById(R.id.card_view0);
            this.itemsectioncontentdestv = (TextView) inflate.findViewById(R.id.item_section_content_des_tv);
            this.itemsectioniv = (ImageView) inflate.findViewById(R.id.item_section_iv);
            this.itemsectioncontent = (TextView) inflate.findViewById(R.id.item_section_content);
            this.itemsectioncontenttitletv = (TextView) inflate.findViewById(R.id.item_section_content_title_tv);
            this.item_section_et = (EditText) inflate.findViewById(R.id.item_section_et);

        }
    }

    private class PicViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvAddPic;
        private final ImageView ivAddPic;
        private final View viewBg;
        private final Button btn_confir;

        public PicViewHolder(View inflate) {
            super(inflate);
            tvAddPic = ((TextView) inflate.findViewById(R.id.tvAddPic));
            ivAddPic = ((ImageView) inflate.findViewById(R.id.ivAddPic));
            viewBg = inflate.findViewById(R.id.viewBg);
            btn_confir = (Button) inflate.findViewById(R.id.btn_confir);
        }
    }

    public interface OnGetAdapterListener {
        void getEitTextData(int position, EditText editText);

        void itemClick(int position);
    }

    public void setoOnGetAdapterListener(OnGetAdapterListener listener) {
        this.listener = listener;
    }
}
