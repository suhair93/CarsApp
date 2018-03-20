package com.cars.cars.company;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.cars.cars.Keys;
import com.cars.cars.R;
import com.cars.cars.models.Car;
import com.cars.cars.models.Parts;
import com.cars.cars.models.service;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class add_parts_service extends Fragment {
    private EditText part_name,part_model,part_price,details,part_number;
    private String city = "",Token="",companyname="";
    private ImageButton upload_img;
    private Button add;
    FirebaseDatabase database;
    DatabaseReference ref;
    StorageReference storageReference;
    public static final String STORAGE_PATH_UPLOADS = "uploads_car/";
    public static final String DATABASE_PATH_UPLOADS = "uploads";
    //uri to store file
    private Uri filePath;
    private ImageView part;
    //constant to track image chooser intent
    private static final int PICK_IMAGE_REQUEST = 234;
    ProgressDialog progressDialog ;


    public add_parts_service() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_add_parts_service, container, false);
        part_name = (EditText)view.findViewById(R.id.name_part);
        part_model = (EditText)view.findViewById(R.id.part_model);
        part_price = (EditText)view.findViewById(R.id.part_price);
        part_number= (EditText)view.findViewById(R.id.number_part);
        part =(ImageView)view.findViewById(R.id.showImage);
        details= (EditText)view.findViewById(R.id.part_details);
        upload_img=(ImageButton)view.findViewById(R.id.img);
        add = (Button)view.findViewById(R.id.add_part);
        database = FirebaseDatabase.getInstance();
        ref = database.getReference();
        storageReference = FirebaseStorage.getInstance().getReference();
        progressDialog = new ProgressDialog(getActivity());

        SharedPreferences prefs = getActivity().getSharedPreferences("company", MODE_PRIVATE);
        Token = prefs.getString(Keys.KEY_COMPANY, "");
        city = prefs.getString(Keys.KEY_CITY,"");
        companyname = prefs.getString(Keys.KEY_NAME,"");
        // method to access image from mobile
        getPIC();
        //button to upload image
        upload_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFileChooser();
            }
        });

        // button add car

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                uploadFile();

            }
        });

        return view;
    }
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                part.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            Toast.makeText(getActivity(),"enter image",Toast.LENGTH_LONG).show();
        }
    }
    public String getFileExtension(Uri uri) {
        ContentResolver cR = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
    private void uploadFile() {
        //checking if file is available
        if (filePath != null) {
            //displaying progress dialog while image is uploading
            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle("Uploading");
            progressDialog.show();

            //getting the storage reference
            StorageReference sRef = storageReference.child(STORAGE_PATH_UPLOADS + System.currentTimeMillis() + "." + getFileExtension(filePath));

            //adding the file to reference
            sRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //dismissing the progress dialog
                            progressDialog.dismiss();



                            service parts = new service();
                            parts.setType_service("part");
                            parts.setName_or_type(part_name.getText().toString());
                            parts.setModel(part_model.getText().toString());
                            parts.setPrice(part_price.getText().toString());
                            parts.setDetails(details.getText().toString());
                            parts.setNumber(part_number.getText().toString());
                            parts.setUserid(Token);
                            parts.setCity(city);
                            parts.setCompany(companyname);
                            parts.setTypeView("saleing");
                            parts.setImageURL(taskSnapshot.getDownloadUrl().toString());
                            ref.child("part").push().setValue(parts);
                            ref.child("service").push().setValue(parts);
                            //displaying success toast
                            Toast.makeText(getActivity(), "image Uploaded ", Toast.LENGTH_LONG).show();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //displaying the upload progress
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                            Toast.makeText(getActivity(),"Done",Toast.LENGTH_LONG).show();
                        }
                    });
        } else {
            //display an error if no file is selected
        }
    }
    private void getPIC() {
        //for greater than lolipop versions we need the permissions asked on runtime
        //so if the permission is not available user will go to the screen to allow storage permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:" + getActivity().getPackageName()));
            startActivity(intent);
            return;
        }
    }

    @Override
    public void onRequestPermissionsResult(int RC, String per[], int[] Result) {

        switch (RC) {

            case 1:

                if (Result.length > 0 && Result[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(getActivity(),"Permission Granted", Toast.LENGTH_LONG).show();

                } else {

                    Toast.makeText(getActivity() ,"Permission Canceled", Toast.LENGTH_LONG).show();

                }
                break;
        }
    }
}
