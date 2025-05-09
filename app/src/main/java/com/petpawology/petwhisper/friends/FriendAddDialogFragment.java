package com.petpawology.petwhisper.friends;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.petpawology.petwhisper.R;

public class FriendAddDialogFragment extends DialogFragment {

    private EditText inputFriendId;
    private ProgressBar loadingSpinner;
    private ImageView profileImage;
    private TextView profileName;
    private Button addFriendButton;
    private Button searchButton;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        View view = requireActivity().getLayoutInflater().inflate(R.layout.dialog_add_friend, null);

        inputFriendId = view.findViewById(R.id.inputFriendId);
        loadingSpinner = view.findViewById(R.id.loadingSpinner);
        profileImage = view.findViewById(R.id.profileImage);
        profileName = view.findViewById(R.id.profileName);
        addFriendButton = view.findViewById(R.id.addFriendButton);
        searchButton = view.findViewById(R.id.searchFriendButton);

        loadingSpinner.setVisibility(View.GONE);
        profileImage.setVisibility(View.GONE);
        profileName.setVisibility(View.GONE);
        addFriendButton.setVisibility(View.GONE);

        searchButton.setOnClickListener(v -> {
            String friendId = inputFriendId.getText().toString().trim();
            if (!friendId.isEmpty()) {
                searchFriend(friendId);
            }
        });

        builder.setView(view);
        return builder.create();
    }

    private void searchFriend(String friendId) {
        loadingSpinner.setVisibility(View.VISIBLE);
        profileImage.setVisibility(View.GONE);
        profileName.setVisibility(View.GONE);
        addFriendButton.setVisibility(View.GONE);

        // Simulate delay for loading (replace with actual DB call)
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            loadingSpinner.setVisibility(View.GONE);
            // Fake condition - replace with real search logic
            if (friendId.equals("12345")) {
                profileName.setText("John Doe");
                profileImage.setImageResource(R.drawable.user_ic); // Load real image in real app
                profileImage.setVisibility(View.VISIBLE);
                profileName.setVisibility(View.VISIBLE);
                addFriendButton.setVisibility(View.VISIBLE);
            } else {
                Toast.makeText(getContext(), "User not found", Toast.LENGTH_SHORT).show();
            }
        }, 2000); // simulate 2-second loading
    }
}
