package app.test.com.washtestapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import app.test.com.washtestapp.R;
import app.test.com.washtestapp.models.Wash;

public class WashAdapter extends RecyclerView.Adapter<WashAdapter.WashViewHolder> {
    private Wash mData[];
    private OnWashClickListener onWashClickListener;

    public WashAdapter(Wash[] mData, OnWashClickListener onWashClickListener) {
        super();
        this.mData = mData;
        this.onWashClickListener = onWashClickListener;
    }

    @NonNull
    @Override
    public WashViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final WashViewHolder viewHolder=new WashViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_wash, parent, false));
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onWashClickListener.onWashClick(viewHolder.getAdapterPosition());
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WashViewHolder holder, int position) {
        holder.tvTitle.setText(mData[position].name);
    }

    @Override
    public int getItemCount() {
        return mData.length;
    }

    static class WashViewHolder extends RecyclerView.ViewHolder
    {
        public TextView tvTitle;
        public WashViewHolder(View itemView) {
            super(itemView);
            tvTitle=itemView.findViewById(R.id.tvTitle);
        }
    }
    public interface OnWashClickListener
    {
        void onWashClick(int washPos);
    }
}
