package com.petpawology.petwhisper;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import com.google.android.material.imageview.ShapeableImageView;
import com.petpawology.petwhisper.main_fragments.FragmentEnterPetInfo;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

public class PetAdapter extends RecyclerView.Adapter<PetAdapter.ViewHolder> {
    private Context context;
    private List<PetInfo> petList;
    private FragmentManager fragmentManager;
    private Dialog dialog;

    private String SelectedSpecies;

    Bundle bundle = new Bundle();

    public PetAdapter(Context context, List<PetInfo> petList, FragmentManager fragmentManager, Dialog dialog) {
        this.context = context;
        this.petList = petList;
        this.fragmentManager = fragmentManager;
        this.dialog = dialog;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        ShapeableImageView shapeablePet_ic;
        TextView itemText;

        public ViewHolder(View itemView) {
            super(itemView);
            shapeablePet_ic = itemView.findViewById(R.id.shapeablePetic);
            itemText = itemView.findViewById(R.id.itemText);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_species_linear_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PetInfo pet = petList.get(position);
        holder.shapeablePet_ic.setImageResource(pet.getImageResId());
        holder.itemText.setText(pet.getSpeciesName());


        //Make the item clickable
        holder.itemView.setOnClickListener(v -> {
            Toast.makeText(context, "Selected: " + pet.getSpeciesName(), Toast.LENGTH_SHORT).show();

            // Create a bundle and put the selected species
            Bundle bundle = new Bundle();
            bundle.putString("selected_species", pet.getSpeciesName());
            Log.d("DebugCheck", "Replacing fragment with selected species: " + pet.getSpeciesName());

            // Create fragment and set arguments
            FragmentEnterPetInfo fragment = new FragmentEnterPetInfo();
            fragment.setArguments(bundle);

            // Perform fragment transaction
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.MainFrameContainer, fragment);
            transaction.addToBackStack(null);
            transaction.commit();

            dialog.dismiss();
        });

    }

    @Override
    public int getItemCount() {
        return petList.size();
    }

    public interface OnPetClickListener {
        void onPetClick(PetInfo pet);
    }

    public String getSelectedSpecies() {
        if(SelectedSpecies != null) {
            String a = "None Selected";
            return a;
        }
            return SelectedSpecies;
    }



}