package com.petpawology.petwhisper.friends;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.petpawology.petwhisper.R;

import java.util.List;

public class FriendPetAdapter extends RecyclerView.Adapter<FriendPetAdapter.PetViewHolder> {

    private List<Pet> pets;
    private OnPetClickListener listener;

    public interface OnPetClickListener {
        void onPetClick(Pet pet);
    }

    public FriendPetAdapter(List<Pet> pets, OnPetClickListener listener) {
        this.pets = pets;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_pet_list_item, parent, false);
        return new PetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PetViewHolder holder, int position) {
        Pet pet = pets.get(position);
        holder.nameTextView.setText(pet.getName());
        holder.imageView.setImageResource(R.drawable.catwalking);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onPetClick(pet);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pets.size();
    }

    static class PetViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        ImageView imageView;

        public PetViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.pet_name);
            imageView = itemView.findViewById(R.id.pet_image);
        }
    }
}



