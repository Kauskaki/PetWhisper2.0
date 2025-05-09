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
import com.petpawology.petwhisper.petinfo.FragmentEnterPetInfoContainer;

import android.view.LayoutInflater;
import android.view.ViewGroup;

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
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_pet_selection_linear_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PetInfo pet = petList.get(position);
        holder.shapeablePet_ic.setImageResource(pet.getImageResId());
        holder.itemText.setText(pet.getSpeciesName());


        //Make the item clickable
        holder.itemView.setOnClickListener(v -> {
            Log.d("DebugCheck", "Pet species name before bundle: " + pet.getSpeciesName());

            // Create a bundle and put the selected species
            Bundle bundle = new Bundle();
            bundle.putString("selected_species", pet.getSpeciesName());

            Log.d("DebugCheck", "Replacing fragment with selected species: " + bundle.getString("selected_species"));

            FragmentEnterPetInfoContainer fragmentContainer = new FragmentEnterPetInfoContainer();
            fragmentContainer.setArguments(bundle);

            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.MainFrameContainer, fragmentContainer);
            transaction.addToBackStack(null);
            transaction.commit();

            dialog.dismiss();
        });

    }

    @Override
    public int getItemCount() {
        return petList.size();
    }


}