package com.example.waltex.trusthospital;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class EditCard extends AppCompatActivity {

    ImageView ImgUserPhoto;
    static int PRegCode = 1;
    static int REQUESTCODE = 1;
    Uri pickedImgUri;


    private EditText userEmail, userPsw, confirmPsw,userName;
    private ProgressBar loadingProgressBar;
    private Button btnLogin;

    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //login element
        userEmail = findViewById(R.id.regMail);
        userName = findViewById(R.id.regName);
        userPsw = findViewById(R.id.regPsw);
        confirmPsw = findViewById(R.id.regChgPws);
        loadingProgressBar = findViewById(R.id.progressBar);
        btnLogin = findViewById(R.id.regBtn);


        mAuth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btnLogin.setVisibility(View.INVISIBLE);
                loadingProgressBar.setVisibility(View.VISIBLE);

                final String email = userEmail.getText().toString();
                final String password = userPsw.getText().toString();
                final String password2 = confirmPsw.getText().toString();
                final String name = userName.getText().toString();

                if (email.isEmpty() || name.isEmpty() || password.isEmpty() || !password.equals(password2)){

                    showMessage("Please verify all fields");
                    btnLogin.setVisibility(View.VISIBLE);
                    loadingProgressBar.setVisibility(View.INVISIBLE);

                }else{
                    //if feilds are ok then create user account

                    CreateUserAccount(email,name,password);
                }


            }
        });

        loadingProgressBar.setVisibility(View.INVISIBLE);


        //igm view

        ImgUserPhoto = findViewById(R.id.regUserPic);

        ImgUserPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Build.VERSION.SDK_INT >= 22){

                    checkAndRequestForPermission();


                }else {
                    openGallery();
                }
            }
        });
    }

    private void CreateUserAccount(String email, final String name, String password) {

        //account create methods

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            //user acount success
                            showMessage("Account created");

                            //update account with name and profile picture
                            updateUserInfo( name, pickedImgUri, mAuth.getCurrentUser() );

                        }else{
                            showMessage("account created failed" + task.getException().getMessage());
                            btnLogin.setVisibility(View.VISIBLE);
                            loadingProgressBar.setVisibility(View.INVISIBLE);
                        }

                    }
                });


    }


    //update user info method
    private void updateUserInfo(final String name, Uri pickedImgUri, final FirebaseUser currentUser) {
//upload user profile pic to firebase

        StorageReference mStorage = FirebaseStorage.getInstance().getReference().child("users_photos");
        final StorageReference imageFilePath = mStorage.child(pickedImgUri.getLastPathSegment());
        imageFilePath.putFile(pickedImgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                //IMAGE UPLOAD SUCCESSFULLY

                imageFilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        // uri contains user image uri

                        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                                .setDisplayName(name)
                                .setPhotoUri(uri)
                                .build();


                        currentUser.updateProfile(profileUpdate)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if (task.isSuccessful()) {
                                            //user info updated successfully
                                            showMessage("Register Complete");
                                            updateUI();
                                        }


                                    }
                                });
                    }
                });


            }
        });


    }



    private void updateUI() {

        Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(mainActivity);
        finish();

    }

    //simple message show method
    private void showMessage(String message) {

        Toast.makeText(getApplicationContext(),message, Toast.LENGTH_LONG).show();
    }

    private void openGallery() {

        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,REQUESTCODE);
    }



    private void checkAndRequestForPermission() {

        if (ContextCompat.checkSelfPermission(EditCard.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(EditCard.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

                Toast.makeText(EditCard.this, "please accept for required permission", Toast.LENGTH_LONG).show();
            } else {

                ActivityCompat.requestPermissions(EditCard.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PRegCode);
            }

        }
        else
        {
            openGallery();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUESTCODE && data != null ) {
            // user has successful picked image

            //saving image to a Uri
            pickedImgUri = data.getData();
            ImgUserPhoto.setImageURI(pickedImgUri);
        }
    }
}

