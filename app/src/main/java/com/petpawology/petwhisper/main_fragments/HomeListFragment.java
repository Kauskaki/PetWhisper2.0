package com.petpawology.petwhisper.main_fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.snackbar.Snackbar;
import com.petpawology.petwhisper.PetInfo;
import com.petpawology.petwhisper.R;
import com.petpawology.petwhisper.petinfo.FragmentEnterPetInfoContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomeListFragment extends Fragment {
    private RecyclerView recyclerViewHomeList;
    private HomeListAdapter adapter;
    private List<PetInfo> petList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.homelist_fragment, container, false);
        recyclerViewHomeList = view.findViewById(R.id.pet_listRecyclerView);
        recyclerViewHomeList.setLayoutManager(new LinearLayoutManager(requireContext()));

        petList = new ArrayList<>(); // Initialize list
        // Sample data
        petList.add(new PetInfo("Bennett", "American ShortHair", "Male", 4, R.drawable.cat_ic, false));
        petList.add(new PetInfo("Buddy", "Golden Retriever", "Male", 4, R.drawable.dog_ic, true));

        adapter = new HomeListAdapter(requireContext(), petList, getParentFragmentManager());
        recyclerViewHomeList.setAdapter(adapter);

        //Slide to delete
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false; // We don't need move functionality
            }

            @SuppressLint("ShowToast")
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getLayoutPosition();
                View itemView = viewHolder.itemView;
                PetInfo deletedItem = petList.get(position);
                itemView.animate().alpha(0).setDuration(300).withEndAction(() -> {
                    petList.remove(position);
                    Objects.requireNonNull(recyclerViewHomeList.getAdapter()).notifyItemRemoved(position);
                }).start();

                // Show Snackbar for undo

                Snackbar snackbar = Snackbar.make(recyclerViewHomeList, "Oops! A pet ran away! 🐾", Snackbar.LENGTH_LONG)
                        .setAction("Bring them back!", v -> {
                            petList.add(position, deletedItem);
                            Objects.requireNonNull(recyclerViewHomeList.getAdapter()).notifyItemInserted(position);
                        })
                        .setActionTextColor(ContextCompat.getColor(requireContext(), R.color.softYellow))
                        .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.lavenderBlue))
                        .setDuration(5000);

                View snackbarView = snackbar.getView();
                snackbarView.setAlpha(0f); // Start fully transparent
                snackbarView.animate().alpha(1f).setDuration(300).start(); // Fade in effect
                snackbarView.setBackgroundResource(R.drawable.edit_text_rounded2);
                TextView snackbarTextView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
                Typeface typeFace = ResourcesCompat.getFont(requireContext(), R.font.baloo_bhai);
                snackbarTextView.setTypeface(typeFace);
                snackbar.show();
            }

            @Override
            public void onChildDraw(Canvas canvas, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                    float dX, float dY, int actionState, boolean isCurrentlyActive) {

                super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerViewHomeList);

        //Undo Deletion






        return view;
    }

    public static class HomeListAdapter extends RecyclerView.Adapter<HomeListAdapter.ViewHolder> {
        private final Context context;
        private final List<PetInfo> petList;
        private final FragmentManager fragmentManager;

        public HomeListAdapter(Context context, List<PetInfo> petList, FragmentManager fragmentManager) {
            this.context = context;
            this.petList = petList;
            this.fragmentManager = fragmentManager;
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            ShapeableImageView pet_pfp;
            TextView petName;
            TextView petAge;

            TextView visitorStatusTitle;

            public ViewHolder(View itemView) {
                super(itemView);
                petName = itemView.findViewById(R.id.FriendName);
                pet_pfp = itemView.findViewById(R.id.userPetpfpHome);
                petAge = itemView.findViewById(R.id.petAgeTextView);
                visitorStatusTitle = itemView.findViewById(R.id.VisitorTitle);
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.homelist_item_cardview, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            PetInfo pet = petList.get(position);
            holder.petName.setText(pet.getPetName());
            holder.pet_pfp.setImageResource(pet.getImageResId());
            holder.petAge.setText(String.valueOf(pet.getPetAge()));

            Boolean checkVisitor = pet.getVisitorStatus();

            if (checkVisitor) {
                holder.visitorStatusTitle.setVisibility(View.VISIBLE);
            } else{
                holder.visitorStatusTitle.setVisibility(View.GONE);
            }



            // Handle item click to transition to pet details fragment
            holder.itemView.setOnClickListener(v -> {
                Bundle bundle = new Bundle();

                FragmentEnterPetInfoContainer fragmentContainer = new FragmentEnterPetInfoContainer();
                fragmentContainer.setArguments(bundle);

                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.MainFrameContainer, fragmentContainer);
                transaction.addToBackStack(null);
                transaction.commit();
            });
        }

        @Override
        public int getItemCount() {
            return petList.size();
        }

        public void removeItem(int position) {
            petList.remove(position);
            notifyItemRemoved(position);
        }

    }

}