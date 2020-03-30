package com.example.ac_instagramclone;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;


/**
 * A simple {@link Fragment} subclass.
 */
public class SharePictureTab extends Fragment implements View.OnClickListener {

    private ImageView imgShare;
    private Button btnShare;
    private TextView txtDiscrip;
    private Bitmap receivedImageBitmap;
    private Uri selecteImage;

    public SharePictureTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_share_picture_tab, container, false);
        imgShare=view.findViewById(R.id.imgShare);
        btnShare=view.findViewById(R.id.btnShare);
        txtDiscrip=view.findViewById(R.id.txtDiscrip);
        //imageView3=view.findViewById(R.id.imageView3);

        imgShare.setOnClickListener(SharePictureTab.this);
        btnShare.setOnClickListener(SharePictureTab.this);

        return view;
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){

            case R.id.imgShare:
                if(Build.VERSION.SDK_INT >= 23 && Manifest.permission.READ_EXTERNAL_STORAGE != PackageManager.PERMISSION_GRANTED+""){
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1000);
                }else{
                    getChosenImage();
                }

                break;
            case R.id.btnShare:
                if(selecteImage !=null){
                    if(txtDiscrip.getText().toString().equals("")){
                        Toast.makeText(getContext(),"Fill the Discription",Toast.LENGTH_SHORT).show();
                    }else{

                        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
                        receivedImageBitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
                        byte[] bytes=byteArrayOutputStream.toByteArray();
                        ParseFile parseFile=new ParseFile("img.png",bytes);
                        ParseObject parseObject = new ParseObject("Photo");
                        parseObject.put("picture",parseFile);
                        parseObject.put("image_des",txtDiscrip.getText().toString());
                        parseObject.put("username", ParseUser.getCurrentUser().getUsername());
//                        final ContentLoadingProgressBar dialog = new ContentLoadingProgressBar(getContext());
//                        dialog.show();
                        parseObject.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if(e== null){
                                    Toast.makeText(getContext(),"Done....!",Toast.LENGTH_SHORT).show();

                                }else{
                                    Toast.makeText(getContext(),"Unknown Error",Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                        Toast.makeText(getContext(), "Uploading", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getContext(), "Choose an Image", Toast.LENGTH_SHORT).show();
                }
                break;

        }

    }

    private void getChosenImage() {

        //Toast.makeText(getContext(), "Acess Sucess", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,2000);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==1000){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getChosenImage();

            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==2000){
            if(resultCode == Activity.RESULT_OK && null !=data){

                try{
                    selecteImage=data.getData();

                    //Folowing Commented lines Did not worked

//                    String[] filePathColumn={MediaStore.Images.Media.DATA};
//                    Cursor cursor= getContext().getContentResolver()
//                            .query(selecteImage,filePathColumn,
//                                    null,null,null);
//                    cursor.moveToFirst();
//                    int columnIndex=cursor.getColumnIndex(filePathColumn[0]);
//                    String picturePath =cursor.getString(columnIndex);
//                    cursor.close();
                    String s=getRealPathFromURI(getContext(),selecteImage);
                    receivedImageBitmap=  MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), selecteImage);
                    imgShare.setImageBitmap(receivedImageBitmap);
                    //imageView3.setImageBitmap(receivedImageBitmap);

                    Toast.makeText(getContext(),"sucess"+s,Toast.LENGTH_LONG).show();

                }catch(Exception e){
                    e.printStackTrace();
                }
            }else {
                Toast.makeText(getContext(),"Error occuerd",Toast.LENGTH_SHORT).show();
            }

        }
    }

    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}
