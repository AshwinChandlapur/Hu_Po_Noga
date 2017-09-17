package com.stickercamera.app.ui;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.skykai.stickercamera.R;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StreamCorruptedException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;


import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;


public class VoterID extends Fragment {
    static final int PICK_IMAGE_REQUEST = 1;


    public VoterID() {
        // Required empty public constructor
    }

    public static VoterID newInstance(String param1, String param2) {
        VoterID fragment = new VoterID();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    EditText name,fatherName;
    TextView cardNameText,cardFatherNameText;
    Button choosePhoto,getId;
    LinearLayout box;
    ConstraintLayout ll;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //final LinearLayout parent = (LinearLayout)getActivity().findViewById(R.id.parent);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_voter_id, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();




        box = (LinearLayout)rootView.findViewById(R.id.box);
        name = (EditText)rootView.findViewById(R.id.name);
        fatherName = (EditText)rootView.findViewById(R.id.fathername);
        choosePhoto = (Button)rootView.findViewById(R.id.choosePhoto);
        getId=(Button) rootView.findViewById(R.id.getID);
        cardNameText = (TextView)rootView.findViewById(R.id.cardNameText);
        cardFatherNameText=(TextView)rootView.findViewById(R.id.cardFatherNameText);






        choosePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, PICK_IMAGE_REQUEST);
            }
        });

        getId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                box.setVisibility(View.GONE);

                String cardName = name.getText().toString();
                cardNameText.setText(cardName);


                String cardFatherName = fatherName.getText().toString();
                cardFatherNameText.setText(cardFatherName);



                getScreenShot();

            }


        });


        return rootView;
    }


    private void getScreenShot() {
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        try {
            // image naming and path  to include sd card  appending name you choose for file
            String mPath = Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpg";

            // create bitmap screen capture
           // View v1 = getActivity().getWindow().getDecorView().getRootView();
            View v1 = getView().findViewById(R.id.constraint);
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);

            File imageFile = new File(mPath);

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();
            shareImage(imageFile);
           // openScreenshot(imageFile);
        } catch (Throwable e) {
            // Several error may come out with file handling or OOM
            e.printStackTrace();
        }
    }


    private void openScreenshot(File imageFile) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(imageFile);
        intent.setDataAndType(uri, "image/*");
        startActivity(intent);
    }




    private void shareImage(File file){
        Uri uri = Uri.fromFile(file);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");

        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
        intent.putExtra(android.content.Intent.EXTRA_TEXT, "");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        try {
            startActivity(Intent.createChooser(intent, "Share Screenshot"));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getActivity(), "No App Available", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        ImageView image_view = (ImageView) getView().findViewById(R.id.photo);

        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                //image_view.setImageBitmap(getScaledBitmap(imageUri,800,800));
                final InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                image_view.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(getActivity(), "You haven't picked Image", Toast.LENGTH_LONG).show();
        }
    }



}
