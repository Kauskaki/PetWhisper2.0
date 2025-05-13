package com.petpawology.petwhisper;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class FriendPetsFragment extends Fragment {

    private static final String ARG_FRIEND_NAME = "friendName";
    private static final String ARG_PETS = "pets";

    private String friendName;
    private List<Pet> pets;

    public static FriendPetsFragment newInstance(String friendName, List<Pet> pets) {
        FriendPetsFragment fragment = new FriendPetsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_FRIEND_NAME, friendName);
        args.putSerializable(ARG_PETS, new ArrayList<>(pets));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            friendName = getArguments().getString(ARG_FRIEND_NAME);
            pets = (List<Pet>) getArguments().getSerializable(ARG_PETS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friend_pets, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.petRecyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new FriendPetAdapter(pets));

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Enable back button in ActionBar (if using ActionBar)
        if (getActivity() instanceof AppCompatActivity) {
            AppCompatActivity activity = (AppCompatActivity) getActivity();
            Toolbar toolbar = activity.findViewById(R.id.AppBar);
            toolbar.setTitle("");
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            activity.getSupportActionBar().setTitle(friendName + "'s Pets");
            TextView toolbarTitle = activity.findViewById(R.id.toolbar_title);
            toolbarTitle.setText("");



        }

        // Enable options menu to handle back button
        setHasOptionsMenu(true);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Pops the fragment and returns to FriendFragment
            AppCompatActivity activity = (AppCompatActivity) getActivity();
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            activity.getSupportActionBar().setTitle("");
            TextView toolbarTitle = activity.findViewById(R.id.toolbar_title);
            toolbarTitle.setText("Friends");
            requireActivity().getSupportFragmentManager().popBackStack();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}