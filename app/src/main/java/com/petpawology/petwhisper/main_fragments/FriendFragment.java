package com.petpawology.petwhisper;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.petpawology.petwhisper.friends.Friend;
import com.petpawology.petwhisper.friends.FriendAdapter;
import com.petpawology.petwhisper.friends.FriendAddDialogFragment;
import com.petpawology.petwhisper.friends.FriendPetsFragment;
import com.petpawology.petwhisper.friends.Pet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FriendFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FloatingActionButton fab;

    public FriendFragment() {
        // Required empty public constructor
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fab = view.findViewById(R.id.floatAddFriendButton);
        fab.setVisibility(View.VISIBLE);

        fab.setOnClickListener(v -> {
            new FriendAddDialogFragment().show(getParentFragmentManager(), "AddFriendDialog");
        });
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FriendFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FriendFragment newInstance(String param1, String param2) {
        FriendFragment fragment = new FriendFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        if (fab != null) {
            fab.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public void onStop() {

        super.onStop();
        View fab = getView().findViewById(R.id.floatAddFriendButton);
        fab.setVisibility(View.GONE);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (fab != null) {
            fab.setVisibility(View.GONE); // This works better than onStop
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friend, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.friendsRecyclerView);

        fab = view.findViewById(R.id.floatAddFriendButton);
        fab.setVisibility(View.VISIBLE);
        fab.setOnClickListener(v -> {
            new FriendAddDialogFragment().show(getParentFragmentManager(), "AddFriendDialog");
        });
        // Sample data
        List<Friend> friends = new ArrayList<>();
        friends.add(new Friend("Alice", Arrays.asList(new Pet("Buddy"), new Pet("Mittens"))));
        friends.add(new Friend("Bob", Arrays.asList(new Pet("Rex"), new Pet("Whiskers"))));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new FriendAdapter(friends, friend -> {
            // Navigate to PetFragment and pass pet list
            Fragment petFragment = FriendPetsFragment.newInstance(friend.getName(), friend.getPets());
            fab.setVisibility(View.GONE);
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.MainFrameContainer, petFragment)
                    .addToBackStack(null)
                    .commit();
        }));

        return view;
    }
}