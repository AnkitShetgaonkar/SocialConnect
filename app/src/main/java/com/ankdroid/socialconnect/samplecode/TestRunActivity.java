package com.ankdroid.socialconnect.samplecode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ankdroid.socialconnect.R;
import com.ankdroid.socialconnect.lib.SocialLogin;
import com.facebook.FacebookSdk;
import com.facebook.login.widget.LoginButton;

public class TestRunActivity extends AppCompatActivity implements View.OnClickListener,SocialLogin.Callback{

    private LoginButton loginButton;
    private SocialLogin mSocialLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSocialLogin = SocialLogin.getInstance(TestRunActivity.this);
        setContentView(R.layout.activity_test_run);

        Button fbBtn = (Button) findViewById(R.id.btnFb);
        Button googleBtn = (Button) findViewById(R.id.btnGoogle);
        Button twtBtn = (Button) findViewById(R.id.btnTwt);

        //fb login
        loginButton = (LoginButton) findViewById(R.id.login_button);
        mSocialLogin.fbLogin(loginButton);

        fbBtn.setOnClickListener(this);
        googleBtn.setOnClickListener(this);
        twtBtn.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnFb:
                //fb button clicked
                break;
            case R.id.btnGoogle:
                //google button clicked
                mSocialLogin.googleLogin(TestRunActivity.this);
                break;
            case R.id.btnTwt:
                //tweeter button clicked
                SocialLogin.twtLogin(TestRunActivity.this);
                break;
        }
    }

    @Override
    public void onLoginFinish() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
    }
}
