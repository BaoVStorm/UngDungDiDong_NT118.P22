package com.example.truonghop6;

import android.app.Activity;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
//
import com.bumptech.glide.Glide;

import java.time.Instant;
import java.util.ArrayList;

public class TruongHop6_HeroAdapter extends RecyclerView.Adapter<TruongHop6_HeroAdapter.ViewHolder> {
    private Activity mContext;
    private ArrayList<TruongHop6_Hero> mHeros;

    // Constructor
    public TruongHop6_HeroAdapter(Activity mContext, ArrayList<TruongHop6_Hero> mHeros) {
        this.mContext = mContext;
        this.mHeros = mHeros;
    }

    // Tạo ViewHolder mới
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View heroView = inflater.inflate(R.layout.truonghop6_row, parent, false);
        return new ViewHolder(heroView);
    }

    // Gán dữ liệu vào ViewHolder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TruongHop6_Hero hero = mHeros.get(position);
        Glide.with(mContext)
                .load(hero.getImage())
                .into(holder.mImageHero);
        holder.mTextName.setText(hero.getName());
    }

    // Trả về số lượng phần tử
    @Override
    public int getItemCount() {
        return mHeros.size();
    }

    // ViewHolder chứa các thành phần UI
    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageHero;
        private TextView mTextName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageHero = itemView.findViewById(R.id.image_hero);
            mTextName = itemView.findViewById(R.id.text_name);
        }
    }
}
