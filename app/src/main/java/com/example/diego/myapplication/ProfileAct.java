package com.example.diego.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;

import java.io.IOException;
import com.facebook.Profile;

public class ProfileAct extends AppCompatActivity {
    private static SimpleDraweeView draweeView;
    private LoginButton logoutButton;
    private TextView lblnome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setContentView(R.layout.activity_profile);

        draweeView=(SimpleDraweeView)findViewById(R.id.fotoPerfil);

        lblnome = (TextView) findViewById(R.id.lblNome);

        Profile profile=Profile.getCurrentProfile();

        lblnome.setText(profile.getFirstName() + " " + profile.getLastName());

        logoutButton = (LoginButton) findViewById(R.id.logoutbutton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logOut();
                goLoginAct();
            }
        });

        if (AccessToken.getCurrentAccessToken() != null) {
            String userID = AccessToken.getCurrentAccessToken().getUserId();
            try {
                getFacebookProfilePicture(userID);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.e("LOG", userID);
        }


    }

    private void goLoginAct() {
        Intent i = new Intent(this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }


    public static void getFacebookProfilePicture(String userID) throws IOException {

        Uri uri = Uri.parse("https://graph.facebook.com/" + userID + "/picture?type=large");

        draweeView.setImageURI(uri);

    }

}
