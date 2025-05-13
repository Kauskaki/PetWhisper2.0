package com.petpawology.petwhisper;

import android.net.Uri;
import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Account {

    private List<Friend> friends = new ArrayList<>();
    private ArrayList<Pet> pets = new ArrayList<>(); // My PETS
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final StorageReference storageRef = FirebaseStorage.getInstance().getReference();
    private final String userId;

    public Account(String userId) {
        this.userId = userId;
    }
    public String getUserId(){
        return this.userId;
    }
    // Upload pet data to Firestore
    public void uploadPet(Pet pet, Uri imageUri, Runnable onSuccess) {
        PetInfo info = pet.getPetInfo();
        String petId = info.getPetName(); // Consider using UUID.randomUUID().toString() for safety

        if (imageUri == null) {
            Log.e("Account", "uploadPet called with null imageUri");
            return;
        }

        StorageReference imgRef = storageRef.child("pet_images/" + petId + ".jpg");

        imgRef.putFile(imageUri).addOnSuccessListener(taskSnapshot ->
                imgRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    Map<String, Object> petData = PetInfoMapper.toMap(info);
                    petData.put("imageUrl", uri.toString());
                    db.collection("users").document(userId).collection("pets").document(petId)
                            .set(petData).addOnSuccessListener(unused -> {

                                onSuccess.run();
                                loadPets(()->{

                                });
                            });
                })
        );

    }
    // Load user's pets
    public void loadPets(Runnable onLoaded) {
        db.collection("users").document(userId).collection("pets")
                .get()
                .addOnSuccessListener(snapshot -> {
                    pets.clear();
                    for (QueryDocumentSnapshot doc : snapshot) {
                        PetInfo info = PetInfoMapper.fromMap(doc.getData());
                        Pet pet = new Pet(info.getPetName());
                        pet.imageUrl =(String) doc.getData().get("imageUrl");
                        //System.out.println(pet.imageUrl);

                        pet.setPetInfo(info);
                        pets.add(pet);
                    }
                    onLoaded.run();
                });
    }

    // Upload friend's user ID (not full friend data)
    public void uploadFriendId(String friendUserId, Runnable onSuccess) {
        Map<String, Object> friendData = Map.of("friendUserId", friendUserId);
        db.collection("users").document(userId).collection("friends")
                .document(friendUserId).set(friendData)
                .addOnSuccessListener(unused -> onSuccess.run());
    }

    // Check if a user with the given userId exists
    public void checkAndAddFriend(String friendUserId, Runnable onSuccess, Runnable onNotFound) {
        db.collection("users").document(friendUserId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        uploadFriendId(friendUserId, onSuccess);
                    } else {
                        onNotFound.run();
                    }
                });
    }

    // Load friends' pets using stored friend user IDs
    public void loadFriends(Runnable onLoaded) {
        friends.clear();
        db.collection("users").document(userId).collection("friends")
                .get()
                .addOnSuccessListener(friendSnapshots -> {
                    for (QueryDocumentSnapshot friendDoc : friendSnapshots) {
                        String friendUserId = friendDoc.getString("userId");
                        String name = friendDoc.getString("name");


                        List<Pet> friendPets = new ArrayList<>();

                        db.collection("users").document(friendUserId).collection("pets")
                                .get()
                                .addOnSuccessListener(petDocs -> {
                                    for (QueryDocumentSnapshot petDoc : petDocs) {
                                        PetInfo petInfo = PetInfoMapper.fromMap(petDoc.getData());
                                        Pet friendPet = new Pet(petInfo.getPetName());
                                        friendPet.setPetInfo(petInfo);
                                        friendPets.add(friendPet);
                                    }
                                    friends.add(new Friend(friendUserId, name,friendPets));
                                });
                    }
                    onLoaded.run();
                });
    }
    public void deletePet(String petName, Runnable onSuccess, Runnable onFailure) {
        // Reference to Firestore document
        db.collection("users").document(userId).collection("pets").document(petName)
                .delete()
                .addOnSuccessListener(unused -> {
                    // After deleting document, also remove image from Storage
                    StorageReference imgRef = storageRef.child("pet_images/" + petName + ".jpg");
                    imgRef.delete().addOnSuccessListener(unused2 -> {
                        Log.d("Account", "Pet and image deleted successfully: " + petName);
                        loadPets(onSuccess); // Refresh local pet list
                    }).addOnFailureListener(e -> {
                        Log.e("Account", "Failed to delete pet image", e);
                        onSuccess.run(); // Still consider success if Firestore deletion worked
                    });
                })
                .addOnFailureListener(e -> {
                    Log.e("Account", "Failed to delete pet from Firestore", e);
                    onFailure.run();
                });
    }


    public List<Friend> getFriends() {
        return friends;
    }

    public List<Pet> getPets() {
        return pets;
    }
}
