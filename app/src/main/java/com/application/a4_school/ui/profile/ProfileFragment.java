package com.application.a4_school.ui.profile;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.application.a4_school.LocalStorage.UserInfoStorage;
import com.application.a4_school.R;
import com.application.a4_school.ui.help.HelpViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ProfileFragment extends Fragment{
    FloatingActionButton chooseImage;
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 99 ;
    public static final int REQUEST_GALLERY = 9544;
    public static final int CAPTURE_REQUEST_CODE = 700;
    private Bitmap bitmap;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        initialize(root);

        chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseMenu();
            }
        });

        return root;
    }

    private void initialize(View root){
        chooseImage = root.findViewById(R.id.chooseUserImage);
    }

    private void chooseMenu(){
        final Dialog popUpdialog = new Dialog(getContext(), R.style.AppBottomSheetDialogTheme);
        popUpdialog.setContentView(R.layout.dialog_select_picture);
        Button btnOpenCamera = popUpdialog.findViewById(R.id.btn_opencamera);
        Button btnOpenGallery = popUpdialog.findViewById(R.id.btn_open_gallery);
        btnOpenCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent capture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(capture, CAPTURE_REQUEST_CODE);
                popUpdialog.dismiss();
            }
        });

        btnOpenGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, REQUEST_GALLERY);
                popUpdialog.dismiss();
            }
        });

        popUpdialog.show();
    }
}
