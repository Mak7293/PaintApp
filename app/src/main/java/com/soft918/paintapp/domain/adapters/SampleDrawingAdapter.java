package com.soft918.paintapp.domain.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.soft918.paintapp.databinding.RvSampleDrawingItemBinding;


import java.util.List;

public class SampleDrawingAdapter extends RecyclerView.Adapter<SampleDrawingAdapter.ViewHolder> {

    private final Context context;
    private final List<Integer> list;

    public SampleDrawingAdapter(Context context, List<Integer> list){
        this.context = context;
        this.list = list;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        RvSampleDrawingItemBinding binding;
        public ViewHolder(@NonNull RvSampleDrawingItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
    public interface OnClickListenerSelect{
        void onClickSelect(int resId);
    }
    OnClickListenerSelect onClickListenerSelect;

    public void onClickListenerSelect(OnClickListenerSelect onClickListener){
        this.onClickListenerSelect = onClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return new ViewHolder(RvSampleDrawingItemBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int resId = list.get(position);
        holder.binding.imageView.setImageDrawable(ContextCompat.getDrawable(context,resId));
        holder.binding.imageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onClickListenerSelect.onClickSelect(resId);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
