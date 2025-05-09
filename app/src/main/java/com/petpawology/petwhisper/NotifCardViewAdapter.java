package com.petpawology.petwhisper;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.petpawology.petwhisper.petinfo.CreateNotifcationBottomsheet;

import java.util.List;

public class NotifCardViewAdapter extends RecyclerView.Adapter<NotifCardViewAdapter.ViewHolder> {
    private List<NotificationType> itemList;
    private Context context;

    public NotifCardViewAdapter(Context context, List<NotificationType> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;
        androidx.cardview.widget.CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.notif_title);
            imageView = itemView.findViewById(R.id.notif_image);
            cardView = itemView.findViewById(R.id.cardView_notif_btn);
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.enter_pet_info_notifs_btn_cardview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        NotificationType item = itemList.get(position);

        if (holder.textView == null) {
            Log.e("DEBUG", "TextView is null!");
        }

        if (holder.imageView == null) {
            Log.e("DEBUG", "ImageView is null!");
        }

        Log.d("DEBUG", "Binding item at position: " + position + " - " + item.getTitle());

        holder.textView.setText(item.getTitle());
        holder.imageView.setImageResource(item.getImageResId());

        holder.cardView.setOnClickListener(v -> {
            CreateNotifcationBottomsheet bottomSheetFragment = new CreateNotifcationBottomsheet();
            bottomSheetFragment.show(((AppCompatActivity) v.getContext()).getSupportFragmentManager(), "NotificationBottomSheet");
        });

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    private void openDialog(String itemTitle) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Notification Type Selected")
                .setMessage("You selected: " + itemTitle)
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}