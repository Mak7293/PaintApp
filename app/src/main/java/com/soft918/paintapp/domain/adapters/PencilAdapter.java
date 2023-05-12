package com.soft918.paintapp.domain.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnticipateOvershootInterpolator;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.TransitionManager;

import com.soft918.paintapp.databinding.RvPencilItemBinding;
import com.soft918.paintapp.domain.component.Pencil;
import com.soft918.paintapp.domain.model.ColorSet;

import java.util.List;

public class PencilAdapter extends RecyclerView.Adapter<PencilAdapter.ViewHolder> {

    private final Context context;
    private final List<ColorSet> colorList;

    public PencilAdapter(Context context, List<ColorSet> colorList){
        this.context = context;
        this.colorList = colorList;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        RvPencilItemBinding binding;
        public ViewHolder(@NonNull RvPencilItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
    public interface OnClickListenerSelect{
        void onClickSelect(int id);
    }
    OnClickListenerSelect onClickListenerSelect;

    public void onClickListenerSelect(OnClickListenerSelect onClickListener){
        this.onClickListenerSelect = onClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return new ViewHolder(RvPencilItemBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int color = colorList.get(position).color;
        boolean isPicked = colorList.get(position).isPicked;

        if (isPicked){
            setWidthAndHeightOfItem(holder,235);
            holder.binding.flRvPencil.removeAllViews();
            holder.binding.flRvPencil.addView(new Pencil(context, color,160f));
        }else {
            setWidthAndHeightOfItem(holder,175);
            holder.binding.flRvPencil.removeAllViews();
            holder.binding.flRvPencil.addView(new Pencil(context, color,100f));
        }
        holder.binding.flRvPencil.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onClickListenerSelect.onClickSelect(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return colorList.size();
    }
    private void setWidthAndHeightOfItem(ViewHolder holder,int height){
        ViewGroup.LayoutParams ivLayoutParams = holder.binding.flRvPencil.getLayoutParams();
        ivLayoutParams.height = height;
        ivLayoutParams.width = (int) (34*2+3);
        holder.binding.flRvPencil.setLayoutParams(ivLayoutParams);
        holder.binding.flRvPencil.requestLayout();
    }

}
