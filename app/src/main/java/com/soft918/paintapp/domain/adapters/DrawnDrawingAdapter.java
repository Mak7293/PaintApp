package com.soft918.paintapp.domain.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.RequestManager;
import com.soft918.paintapp.R;
import com.soft918.paintapp.databinding.RvDrawnPaintItemBinding;
import com.soft918.paintapp.domain.model.PaintUriEntity;
import java.util.List;

public class DrawnDrawingAdapter extends RecyclerView.Adapter<DrawnDrawingAdapter.ViewHolder> {

    private final Context context;
    private final List<PaintUriEntity> list;
    private final RequestManager glide;
    public View btn_delete_view;
    public View btn_share_view;
    public View btn_select_view;


    public DrawnDrawingAdapter(Context context, List<PaintUriEntity> list, RequestManager glide){
        this.context = context;
        this.list = list;
        this.glide = glide;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        RvDrawnPaintItemBinding binding;
        public ViewHolder(@NonNull RvDrawnPaintItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
    // select drawn drawing paint
    public interface OnClickListenerSelect{
        void onClickSelect(PaintUriEntity paintUriEntity);
    }
    OnClickListenerSelect onClickListenerSelect;

    public void onClickListenerSelect(OnClickListenerSelect onClickListener){
        this.onClickListenerSelect = onClickListener;
    }
    // share drawn drawing paint
    public interface OnClickListenerShare {
        void onClickShare(PaintUriEntity paintUriEntity);
    }
    OnClickListenerShare onClickListenerShare;

    public void onClickListenerShare(OnClickListenerShare onClickListener){
        this.onClickListenerShare = onClickListener;
    }
    // delete drawn drawing paint
    public interface OnClickListenerDelete{
        void onClickDelete(PaintUriEntity paintUriEntity);
    }
    OnClickListenerDelete onClickListenerDelete;

    public void onClickListenerDelete(OnClickListenerDelete onClickListener){
        this.onClickListenerDelete = onClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        return new ViewHolder(RvDrawnPaintItemBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PaintUriEntity paintUriEntity = list.get(position);
        glide.load(Uri.parse(paintUriEntity.contentUri))
                .centerInside()
                .into(holder.binding.imageView);
        btn_select_view = holder.binding.imageView;
        btn_delete_view = holder.binding.btnIvDelete;
        btn_share_view = holder.binding.btnIvShare;
        holder.binding.imageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onClickListenerSelect.onClickSelect(paintUriEntity);
            }
        });

        holder.binding.btnIvDelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onClickListenerDelete.onClickDelete(paintUriEntity);
            }
        });
        holder.binding.btnIvShare.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                onClickListenerShare.onClickShare(paintUriEntity);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
