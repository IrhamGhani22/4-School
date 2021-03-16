package com.application.a4_school.ui.profile;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.application.a4_school.LocalStorage.UserInfoStorage;
import com.application.a4_school.R;
import com.application.a4_school.RestAPI.APIClient;
import com.application.a4_school.RestAPI.APIService;
import com.application.a4_school.RestAPI.ResponseData;
import com.application.a4_school.ui.help.HelpViewModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment{
    FloatingActionButton chooseImage;
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 99 ;
    public static final int REQUEST_GALLERY = 9544;
    public static final int CAPTURE_REQUEST_CODE = 700;
    private UserInfoStorage userInfoStorage;
    private Bitmap bitmap;
    private CircleImageView userImage;
    String part_image = "";
    Context context;

    @SuppressLint("ResourceAsColor")
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        initialize(root);

//        final Toolbar toolbar = (Toolbar)root.findViewById(R.id.toolbarpf);
//        toolbar.setBackgroundColor(R.color.BlueishPurple);
////        final Toolbar tb = (Toolbar)root.findViewById(R.id.toolbar);
////
//
//        AppCompatActivity app = (AppCompatActivity) getActivity();
//        app.setSupportActionBar(toolbar);
//        app.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        CollapsingToolbarLayout collapsingToolbar =
//                (CollapsingToolbarLayout)root.findViewById(R.id.collaps);
//        collapsingToolbar.setTitle("APP");
//        int bgColor = ContextCompat.getColor(context, R.color.BluePurple);
//        collapsingToolbar.setExpandedTitleColor( ContextCompat.getColor(context, bgColor));



        SharedPreferences getUserInfo = getActivity().getSharedPreferences("userInfo", 0);
        String url_image = getUserInfo.getString("image", "");
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.empty_profile);

        Glide.with(userImage.getContext()).load(url_image).apply(options).into(userImage);

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
        userImage = root.findViewById(R.id.userImage);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case CAPTURE_REQUEST_CODE: {
                if (resultCode == RESULT_OK && data != null){
                    bitmap = (Bitmap) data.getExtras().get("data");
                    imageDecodedUpload(bitmap);
                }
            }
            break;
            case REQUEST_GALLERY:{
                if (requestCode == REQUEST_GALLERY && resultCode == RESULT_OK && data != null) {
                    Uri selectedImage = data.getData();
                    String[] imageprojection = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContext().getContentResolver().query(selectedImage, imageprojection, null, null, null);
                    if (cursor != null){
                        cursor.moveToFirst();
                        int indexImage = cursor.getColumnIndex(imageprojection[0]);
                        part_image = cursor.getString(indexImage);
                        if (part_image != null){
                            try {
                                bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), selectedImage);
                                imageDecodedUpload(bitmap);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
            break;
        }
    }

    private void imageDecodedUpload(final Bitmap bitmap) {
        String image = imageToString();
        SharedPreferences getId_user = getActivity().getSharedPreferences("userInfo", 0);
        int id_user = getId_user.getInt("id", 0);
        APIService api = APIClient.getClient().create(APIService.class);
        Call<ResponseBody> upload = api.uploadBase64Pict(id_user, image);
        upload.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        String JSONResponse = response.body().string();
                        Gson objGson = new Gson();
                        ResponseData objResp = objGson.fromJson(JSONResponse, ResponseData.class);
                        if (objResp.getImage_url() != null){
                            userImage.setImageBitmap(bitmap);
                            userInfoStorage.addPict(objResp.getImage_url());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private String imageToString(){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,75,byteArrayOutputStream);
        byte[] imgByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgByte, Base64.DEFAULT);
    }


}
