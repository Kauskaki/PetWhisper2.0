package com.petpawology.petwhisper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.FriendViewHolder> {

    public interface OnFriendClickListener {
        void onFriendClick(Friend friend);
    }

    private List<Friend> friends;
    private OnFriendClickListener listener;

    public FriendAdapter(List<Friend> friends, OnFriendClickListener listener) {
        this.friends = friends;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.friend_list_item, parent, false);
        return new FriendViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendViewHolder holder, int position) {
        Friend friend = friends.get(position);
        holder.nameTextView.setText(friend.getName());
        holder.imageView.setImageResource(R.drawable.user_ic);
        // Load the image (if you use a library like Glide or Picasso, replace this)
        //holder.imageView.setImageResource(friend.getImageResId());

        holder.itemView.setOnClickListener(v -> listener.onFriendClick(friend));
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }

    static class FriendViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView nameTextView;

        public FriendViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.friend_image);
            nameTextView = itemView.findViewById(R.id.friend_name);
        }
    }
}
