package com.example.ac_instagramclone;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileTab extends Fragment {

    private EditText txtProfile,txtBio,txtHobies,txtFav;
    private Button btnUpdate;

    public ProfileTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile_tab, container, false);

        txtProfile=view.findViewById(R.id.txtProfileName);
        txtBio=view.findViewById(R.id.txtBio);
        txtHobies=view.findViewById(R.id.txtHobies);
        txtFav=view.findViewById(R.id.txtFav);
        btnUpdate=view.findViewById(R.id.btnUpdate);

        final ParseUser parseUser= ParseUser.getCurrentUser();

        if (parseUser.get("profileName") != null){
            txtProfile.setText(parseUser.get("profileName").toString());
        }

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parseUser.put("profileName",txtProfile.getText().toString());
                parseUser.put("profileBio",txtBio.getText().toString());
                parseUser.put("profileHobies",txtHobies.getText().toString());
                parseUser.put("profileFavSport",txtFav.getText().toString());

                parseUser.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {

                        if(e ==null){
                            Toast.makeText(getContext(),"Updated",Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getContext(),"Not Updated "+e+"\n"+"Try again",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        return view;

    }
}
