package com.example.ac_instagramclone;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;

//import android.support.v7.widget.Toolbar;

public class SocialMediaActivity extends AppCompatActivity {

    private  androidx.appcompat.widget.Toolbar toolbar;
    private ViewPager viewPager;
    private TabAdapter tabAdapter;
    private TabLayout tabLayout;
    private Uri capturedImage;
    private Bitmap receivedImageBitmap;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_media);

        setTitle("SocialMediaApp!!!");;
        toolbar=findViewById(R.id.myToolBar);
        setSupportActionBar(toolbar);

        viewPager=findViewById(R.id.viewPager);
        tabAdapter= new TabAdapter(getSupportFragmentManager(),0);
        viewPager.setAdapter(tabAdapter);

        tabLayout=findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager,true);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_list,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.postImageItem) {
            if (Build.VERSION.SDK_INT >= 23 && Manifest.permission.READ_EXTERNAL_STORAGE != PackageManager.PERMISSION_GRANTED + "") {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 3000);
            } else {
                captureImage();
            }
        }else if(item.getItemId()==R.id.logoutUserItem){
            ParseUser.logOut();
            finish();
            Intent intent=new Intent(SocialMediaActivity.this,MainActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==3000){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                captureImage();

            }
        }
    }
    private void captureImage() {
        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,4000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 4000 && resultCode == RESULT_OK && data != null) {
            try {
                capturedImage = data.getData();
                receivedImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), capturedImage);

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                receivedImageBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] bytes = byteArrayOutputStream.toByteArray();

                ParseFile parseFile = new ParseFile("img.png", bytes);
                ParseObject parseObject = new ParseObject("Photo");
                parseObject.put("picture", parseFile);
                parseObject.put("username", ParseUser.getCurrentUser().getUsername());
                parseObject.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Toast.makeText(SocialMediaActivity.this, "Done....!", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(SocialMediaActivity.this, "Unknown Error", Toast.LENGTH_LONG).show();
                        }
                    }
                });

                Toast.makeText(this, "Uploading", Toast.LENGTH_SHORT).show();


            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            Toast.makeText(this, "Choose an Image", Toast.LENGTH_SHORT).show();
        }
    }


}
