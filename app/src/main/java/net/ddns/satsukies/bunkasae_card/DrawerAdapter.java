package net.ddns.satsukies.bunkasae_card;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.ddns.satsukies.bunkasae_card.card.AccountStorage;

import java.util.ArrayList;
import java.util.HashMap;

public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.ViewHolder> {

    Context mContext;
    boolean flg = false;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        static TextView mTextView;
        static ImageView mImageView;
        static RelativeLayout mLayout;

        public ViewHolder(View itemView, int viewType) {
            super(itemView);
            switch (viewType) {
                case R.array.type_menu:
                    // menu 用レイアウトに含まれる、各Viewを取得
                    mTextView = (TextView) itemView.findViewById(R.id.menu_text);
                    mImageView = (ImageView) itemView.findViewById(R.id.menu_icon);
                    mLayout = (RelativeLayout) itemView.findViewById(R.id.menu_layout);
                    break;
                case R.array.type_menu_hide:
                    mTextView = (TextView) itemView.findViewById(R.id.menu_text);
                    mImageView = (ImageView) itemView.findViewById(R.id.menu_icon);
                    break;
                case R.array.type_header:
                    //for header
                    mImageView = (ImageView) itemView.findViewById(R.id.header_image);
                    break;
                case R.array.type_category:
                    mTextView = (TextView) itemView.findViewById(R.id.category_text);
                    break;
                case R.array.type_card_id:
                    mTextView = (TextView) itemView.findViewById(R.id.card_id_text);
                    mImageView = (ImageView) itemView.findViewById(R.id.text_icon);
                    break;
                case R.array.type_transparent:
                    break;
                default:
                    break;
            }
        }
    }

    private ArrayList<HashMap<String, Object>> mDrawerMenuArr;

//    public DrawerAdapter(ArrayList<HashMap<String, Object>> arrayList) {
//        mDrawerMenuArr = arrayList;
//    }

    public DrawerAdapter(ArrayList<HashMap<String, Object>> arrayList, Context context) {
        mDrawerMenuArr = arrayList;
        mContext = context;
    }


    @Override
    public DrawerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        switch (viewType) {
            case R.array.type_menu:
                // menu 用のレイアウトを利用する
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_item_menu, parent, false);
                itemView.setClickable(true);
                break;

            case R.array.type_menu_hide:
                if (flg) {
                    itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_item_menu, parent, false);
                    itemView.setClickable(true);
                } else {
                    itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_item_transparent_zero, parent, false);
                    itemView.setClickable(false);
                }
                break;

            case R.array.type_header:
                // header 用のレイアウトを利用する
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_item_header, parent, false);
                itemView.setClickable(false);
                break;

            case R.array.type_category:
                //for category
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_category, parent, false);
                itemView.setClickable(true);
                break;

            case R.array.type_transparent:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_item_transparent, parent, false);
                itemView.setClickable(false);
                break;

            case R.array.type_card_id:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_item_card_id, parent, false);
                itemView.setClickable(false);
                break;

            default:
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_item_transparent, parent, false);
                itemView.setClickable(false);
                break;
        }
        ViewHolder vh = new ViewHolder(itemView, viewType);
        return vh;
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        HashMap<String, Object> menu = mDrawerMenuArr.get(position);
        switch (holder.getItemViewType()) {
            case R.array.type_menu:
                // ViewHolder で取得したViewに表示するデータをバインド
                holder.mTextView.setText(menu.get("text").toString());
                holder.mImageView.setImageDrawable((Drawable) menu.get("icon"));
                break;
            case R.array.type_menu_hide:
                if (flg) {
                    holder.mTextView.setText(menu.get("text").toString());
                    holder.mImageView.setImageDrawable((Drawable) menu.get("icon"));
                }
                break;
            case R.array.type_header:
                // ViewHolder で取得したViewに表示するデータをバインド
                holder.mImageView.setImageDrawable((Drawable) menu.get("icon"));
                break;
            case R.array.type_category:
                holder.mTextView.setText(menu.get("text").toString());
                break;
            case R.array.type_card_id:
                if (mContext != null && !(AccountStorage.GetAccount(mContext).equals("00000000"))) {
                    holder.mTextView.setText(AccountStorage.GetAccount(mContext));
                    holder.mImageView.setImageDrawable((Drawable) menu.get("icon"));
                    flg = false;
                } else if (mContext != null) {
                    holder.mTextView.setText("Card is not Available");
                    holder.mImageView.setImageDrawable((Drawable) menu.get("icon"));
                    flg = true;
                }
                break;
            case R.array.type_transparent:
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
//        return mDrawerMenuArr.length;
        return mDrawerMenuArr.size();
    }

    public int getItemViewType(int position) {
        HashMap<String, Object> menu = mDrawerMenuArr.get(position);
        switch (menu.get("type").toString()) {
            case "menu":
                return R.array.type_menu;
            case "menu_hide":
                return R.array.type_menu_hide;
            case "header":
                return R.array.type_header;
            case "category":
                return R.array.type_category;
            case "transparent":
                return R.array.type_transparent;
            case "card_id":
                return R.array.type_card_id;
            default:
                return R.array.type_transparent;
        }
    }


}
