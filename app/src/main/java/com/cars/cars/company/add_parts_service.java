package com.cars.cars.company;


import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;

import com.cars.cars.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


/**
 * A simple {@link Fragment} subclass.
 */
public class add_parts_service extends Fragment {
    private EditText part_name,part_model,part_price,details,part_number;
    private String type_view = "",imgLoad="",imgURL="";
    private ImageButton upload_img;
    private Button add;
    FirebaseDatabase database;
    DatabaseReference ref;
    StorageReference storageReference;
    String Storage_Path = "Image_Uploads_part/";
    Uri FilePathUri;
    public static final String Database_Path = "All_Image_Uploads_Database";
    int Image_Request_Code = 7;
    ProgressDialog progressDialog ;


    public add_parts_service() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_add_parts_service, container, false);
        part_name = (EditText)view.findViewById(R.id.part_name);
        part_model = (EditText)view.findViewById(R.id.part_model);
        part_price = (EditText)view.findViewById(R.id.part_price);
        part_number= (EditText)view.findViewById(R.id.part_number);
        details= (EditText)view.findViewById(R.id.part_details);
        upload_img=(ImageButton)view.findViewById(R.id.img);
        add = (Button)view.findViewById(R.id.add_part);
        database = FirebaseDatabase.getInstance();
        ref = database.getReference();
        storageReference = FirebaseStorage.getInstance().getReference();
        progressDialog = new ProgressDialog(getActivity());



        return view;
    }

}
