package com.letstravel.taskmanagerapi;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class SignUpActivity extends AppCompatActivity {
    private EditText etFirstName, etLastName, etUsername, etPassword, etCPassword;
    private Button btnSignup;
    private CircleImageView imgProfilePicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etFirstName=findViewById(R.id.etFirstName);
        etLastName=findViewById(R.id.etLastName);
        etUsername=findViewById(R.id.etUsername);
        etPassword=findViewById(R.id.etPassword);
        etCPassword=findViewById(R.id.etCPassword);

        imgProfilePicture=findViewById(R.id.imgProfilePicture);
        btnSignup=findViewById(R.id.btnSignUp);
        checkPermission();
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        imgProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
            }
        });

    }



    private void checkPermission() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]
                    {Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE},0);

        }
    }

    private void showPictureDialog() {

        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Choose Photo ");
        String[] pictureDialogItems = {
                "Select Gallery",
                "Capture Camera",
                "Cancel"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                            case 2:
                                cancel();
                                break;
                        }
                    }
                });
        pictureDialog.show();

    }

    private void cancel() {

    }

    private void takePhotoFromCamera() {
        Intent intent=new Intent((MediaStore.ACTION_IMAGE_CAPTURE));
        if(intent.resolveActivity(getPackageManager()) !=null){
            startActivityForResult(intent,0);

        }
    }
    private void choosePhotoFromGallary() {
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    // String path = saveImage(bitmap);
                    Toast.makeText(SignUpActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
                    imgProfilePicture.setImageBitmap(bitmap);


                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(SignUpActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == 0) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap= (Bitmap) extras.get("data");
            imgProfilePicture.setImageBitmap(imageBitmap);
        }
    }
}
