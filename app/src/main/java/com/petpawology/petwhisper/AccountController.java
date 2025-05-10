package com.petpawology.petwhisper;

public class AccountController {
    private static AccountController instance;
    private Account account;

    private boolean petsLoaded = false;
    private boolean friendsLoaded = false;

    private Runnable onReadyCallback;

    private AccountController() {}

    public static synchronized AccountController getInstance() {
        if (instance == null) {
            instance = new AccountController();
        }
        return instance;
    }

    public void init(String userId, Runnable onReadyCallback) {
        this.account = new Account(userId);
        this.onReadyCallback = onReadyCallback;
        this.petsLoaded = false;
        this.friendsLoaded = false;

        loadAccountData();
    }

    private void loadAccountData() {
        account.loadPets(() -> {
            petsLoaded = true;
            checkIfReady();
        });

        account.loadFriends(() -> {
            friendsLoaded = true;
            checkIfReady();
        });
    }

    private void checkIfReady() {
        if (petsLoaded && friendsLoaded && onReadyCallback != null) {
            onReadyCallback.run();
            onReadyCallback = null; // prevent multiple calls
        }
    }

    public Account getAccount() {
        return account;
    }

    public boolean isInitialized() {
        return account != null;
    }

    public boolean isReady() {
        return petsLoaded && friendsLoaded;
    }
}
