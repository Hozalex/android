package com.example.ahozyainov.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ahozyainov.activities.R;
import com.example.ahozyainov.models.Cities;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityViewHolder> {

    private Cities citiesData[];
    private OnCityClickListener onCityClickListener;

    public CityAdapter(Cities[] citiesData, OnCityClickListener onCityClickListener) {
        this.citiesData = citiesData;
        this.onCityClickListener = onCityClickListener;

    }

    @NonNull
    @Override
    public CityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final CityViewHolder viewHolder = new CityViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_city, parent, false));
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCityClickListener.onCityClick(viewHolder.getAdapterPosition());
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CityViewHolder holder, int position) {
        holder.tvTitle.setText(citiesData[position].name);
    }


    @Override
    public int getItemCount() {
        return citiesData.length;
    }

    static class CityViewHolder extends RecyclerView.ViewHolder {

        public TextView tvTitle;

        public CityViewHolder(final View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvTitle.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
                @Override
                public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
                    MenuInflater menuInflater = new MenuInflater(itemView.getContext());
                    menuInflater.inflate(R.menu.context_menu, contextMenu);

                }

            });

        }

    }

    public interface OnCityClickListener {
        void onCityClick(int cityPosition);
    }
}


