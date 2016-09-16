package com.ankdroid.socialconnect.samplecode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.ankdroid.socialconnect.R;
import com.ankdroid.socialconnect.lib.SocialLogin;
import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

public class TestRunActivity extends AppCompatActivity implements View.OnClickListener, SocialLogin.Callback {


    private SocialLogin mSocialLogin;

    private CallbackManager mFbCallbackManager;
    private TwitterLoginButton mTwitterLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //should be initialized before setContentView
        mSocialLogin = SocialLogin.getInstance(TestRunActivity.this);
        setContentView(R.layout.activity_test_run);

        //FB
        //fb login, find the view and pass it on to Social Connect class
        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        mFbCallbackManager = mSocialLogin.fbLogin(loginButton);

        //GOOGLE
        //set onclick event for google sign in button
        findViewById(R.id.sign_in_button).setOnClickListener(this);

        //TWITTER
        //twitter login button
        mTwitterLoginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);
        mSocialLogin.twtLogin(mTwitterLoginButton);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                //google button clicked
                mSocialLogin.googleLogin();
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
        mFbCallbackManager.onActivityResult(requestCode, resultCode, data);

        //google code
        mSocialLogin.handleGoogleSignInResult(requestCode, data);

        //twitter code
        mTwitterLoginButton.onActivityResult(requestCode, resultCode, data);
    }


}
