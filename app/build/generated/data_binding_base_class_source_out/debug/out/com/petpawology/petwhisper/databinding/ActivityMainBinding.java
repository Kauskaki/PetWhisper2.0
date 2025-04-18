// Generated by view binder compiler. Do not edit!
package com.petpawology.petwhisper.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.petpawology.petwhisper.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityMainBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final Toolbar AppBar;

  @NonNull
  public final FrameLayout MainFrameContainer;

  @NonNull
  public final ImageButton backButton;

  @NonNull
  public final BottomNavigationView bottomNavigation;

  @NonNull
  public final FloatingActionButton floatPetAddButton;

  @NonNull
  public final ConstraintLayout main;

  @NonNull
  public final ShapeableImageView profileImage;

  @NonNull
  public final TextView toolbarTitle;

  private ActivityMainBinding(@NonNull ConstraintLayout rootView, @NonNull Toolbar AppBar,
      @NonNull FrameLayout MainFrameContainer, @NonNull ImageButton backButton,
      @NonNull BottomNavigationView bottomNavigation,
      @NonNull FloatingActionButton floatPetAddButton, @NonNull ConstraintLayout main,
      @NonNull ShapeableImageView profileImage, @NonNull TextView toolbarTitle) {
    this.rootView = rootView;
    this.AppBar = AppBar;
    this.MainFrameContainer = MainFrameContainer;
    this.backButton = backButton;
    this.bottomNavigation = bottomNavigation;
    this.floatPetAddButton = floatPetAddButton;
    this.main = main;
    this.profileImage = profileImage;
    this.toolbarTitle = toolbarTitle;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityMainBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityMainBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_main, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityMainBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.AppBar;
      Toolbar AppBar = ViewBindings.findChildViewById(rootView, id);
      if (AppBar == null) {
        break missingId;
      }

      id = R.id.MainFrameContainer;
      FrameLayout MainFrameContainer = ViewBindings.findChildViewById(rootView, id);
      if (MainFrameContainer == null) {
        break missingId;
      }

      id = R.id.backButton;
      ImageButton backButton = ViewBindings.findChildViewById(rootView, id);
      if (backButton == null) {
        break missingId;
      }

      id = R.id.bottom_navigation;
      BottomNavigationView bottomNavigation = ViewBindings.findChildViewById(rootView, id);
      if (bottomNavigation == null) {
        break missingId;
      }

      id = R.id.floatPetAddButton;
      FloatingActionButton floatPetAddButton = ViewBindings.findChildViewById(rootView, id);
      if (floatPetAddButton == null) {
        break missingId;
      }

      ConstraintLayout main = (ConstraintLayout) rootView;

      id = R.id.profile_image;
      ShapeableImageView profileImage = ViewBindings.findChildViewById(rootView, id);
      if (profileImage == null) {
        break missingId;
      }

      id = R.id.toolbar_title;
      TextView toolbarTitle = ViewBindings.findChildViewById(rootView, id);
      if (toolbarTitle == null) {
        break missingId;
      }

      return new ActivityMainBinding((ConstraintLayout) rootView, AppBar, MainFrameContainer,
          backButton, bottomNavigation, floatPetAddButton, main, profileImage, toolbarTitle);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
