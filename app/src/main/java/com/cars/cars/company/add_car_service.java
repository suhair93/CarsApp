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
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

import static android.Manifest.permission_group.CAMERA;
import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class add_car_service extends Fragment {
    private CheckBox checkBox_leasing, checkBox_saleing;
    private EditText car_type,car_model,car_price,details;
    private String type_view = "",imgLoad="",imgURL="";
    private ImageButton upload_img;
    private Button add;
    private ImageView car;
    //firebase objects
    FirebaseDatabase database;
    DatabaseReference ref;
    StorageReference storageReference;
    public static final String STORAGE_PATH_UPLOADS = "uploads_car/";
    public static final String DATABASE_PATH_UPLOADS = "uploads";
    //uri to store file
    private Uri filePath;
    String Token="";
    //constant to track image chooser intent
    private static final int PICK_IMAGE_REQUEST = 234;
    ProgressDialog progressDialog ;
    public add_car_service() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_add_car_service, container, false);
        car_type = (EditText)view.findViewById(R.id.car_type);
        car_model = (EditText)view.findViewById(R.id.car_model);
        car_price = (EditText)view.findViewById(R.id.car_price);
        details = (EditText)view.findViewById(R.id.car_details);
        upload_img=(ImageButton)view.findViewById(R.id.img);
        add = (Button)view.findViewById(R.id.add_car);
        car = (ImageView)view.findViewById(R.id.showImage);
        checkBox_leasing = (CheckBox) view.findViewById(R.id.leasing);
        database = FirebaseDatabase.getInstance();
        ref = database.getReference();
        storageReference = FirebaseStorage.getInstance().getReference();
        progressDialog = new ProgressDialog(getActivity());

        SharedPreferences prefs = getActivity().getSharedPreferences("company", MODE_PRIVATE);
        Token = prefs.getString(Keys.KEY_COMPANY, "");

        //choose laesing or saleing check box
        checkBox_leasing.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //is chkIos checked?
                if (((CheckBox) v).isChecked()) {
                 type_view = R.string.Leasing+"";
                }

            }
        });
        checkBox_saleing = (CheckBox) view.findViewById(R.id.saleing);

        checkBox_saleing.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //is chkIos checked?
                if (((CheckBox) v).isChecked()) {
                  type_view = R.string.saleing+"";
                }

            }
        });
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
                car.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
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

                            //displaying success toast
                            Toast.makeText(getActivity(), "image Uploaded ", Toast.LENGTH_LONG).show();

                            Car car = new Car();
                            car.setType(car_type.getText().toString());
                            car.setModel(car_model.getText().toString());
                            car.setPrice(car_price.getText().toString());
                            car.setDetails(details.getText().toString());
                            car.setTypeView(type_view);
                            car.setUserid(Token);
                            car.setImageURL(taskSnapshot.getDownloadUrl().toString());
                            ref.child("car").push().setValue(car);


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
