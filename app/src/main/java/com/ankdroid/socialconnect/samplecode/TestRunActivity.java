package com.ankdroid.socialconnect.samplecode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ankdroid.socialconnect.R;
import com.ankdroid.socialconnect.lib.SocialLogin;
import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;

public class TestRunActivity extends AppCompatActivity implements View.OnClickListener,SocialLogin.Callback{

    private SocialLogin mSocialLogin;
    private CallbackManager mCallbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSocialLogin = SocialLogin.getInstance(TestRunActivity.this);
        setContentView(R.layout.activity_test_run);

        Button googleBtn = (Button) findViewById(R.id.btnGoogle);
        Button twtBtn = (Button) findViewById(R.id.btnTwt);

        //fb login, find the view and pass it on to Social Connect class
        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        mCallbackManager = mSocialLogin.fbLogin(loginButton);

        googleBtn.setOnClickListener(this);
        twtBtn.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
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

        //facebook code
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        
    }
}
