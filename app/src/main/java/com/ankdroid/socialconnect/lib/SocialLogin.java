package com.ankdroid.socialconnect.lib;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.ankdroid.socialconnect.R;
import com.ankdroid.socialconnect.samplecode.TestRunActivity;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import io.fabric.sdk.android.Fabric;

/**
 * Created by ankit on 14/09/16.
 * Helper class to abstract social login, presently of facebook, google and twitter
 * instansize this class before setContentView
 */
public class SocialLogin implements GoogleApiClient.OnConnectionFailedListener {


    public static final int RC_SIGN_IN = 1000;
    private static SocialLogin mSocialLogin;
    private GoogleApiClient mGoogleApiClient;
    private Context mContext;

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "AOpablsuYxv2dDh2mVHjBZbjP";
    private static final String TWITTER_SECRET = "BxXjXLkPvMTSLhx4O4SS8OwCgYR2c1TXz3C7yhFkwBzOyyttqs";


    private static final String TAG = SocialLogin.class.getSimpleName();

    /**
     * singleton reference
     *
     * @param context
     * @return
     */
    public static SocialLogin getInstance(Context context) {
        if (mSocialLogin == null) {
            mSocialLogin = new SocialLogin(context);
        }
        return mSocialLogin;
    }


    /**
     * constructor
     *
     * @param context
     */
    private SocialLogin(Context context) {

        this.mContext = context;
        //initialize fb
        FacebookSdk.sdkInitialize(mContext.getApplicationContext());
        //initialize google plus
        initializeGoogleLogin();
        //initialize twt login
        initializeTwtLogin();
    }


    private void initializeGoogleLogin(){
        //initialize google plus
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                .enableAutoManage((AppCompatActivity)mContext, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    private void initializeTwtLogin(){
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(mContext, new Twitter(authConfig));
    }

    /**
     * logs in to facebook, should implement callback to receive info, returns the @callbackManager
     * reference to be used in onActivityResult of the calling Activity
     *
     * @param fbBtn
     * @return callbackManager
     */
    public CallbackManager fbLogin(LoginButton fbBtn) {
        CallbackManager callbackManager = CallbackManager.Factory.create();
        fbBtn.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "Successfully logged in");
                Toast.makeText(mContext, "Success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "Canceled logged in");
                Toast.makeText(mContext, "cancel", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException e) {
                Log.d(TAG, "There was some issue with fb login");
                Toast.makeText(mContext, "error", Toast.LENGTH_SHORT).show();
            }

        });

        return callbackManager;
    }


    /**
     * google login call
     */
    public void googleLogin() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        ((AppCompatActivity)mContext).startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    /**
     * logs in to twitter, should implement callback to receive info
     *
     * @param loginButton
     */
    public void twtLogin(TwitterLoginButton loginButton) {
        loginButton.setCallback(new com.twitter.sdk.android.core.Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                // The TwitterSession is also available through:
                // Twitter.getInstance().core.getSessionManager().getActiveSession()
                TwitterSession session = result.data;
                // TODO: Remove toast and use the TwitterSession's userID
                // with your app's user model
                String msg = "@" + session.getUserName() + " logged in! (#" + session.getUserId() + ")";
                Toast.makeText(mContext.getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            }
            @Override
            public void failure(TwitterException exception) {
                Log.d("TwitterKit", "Login with Twitter failure", exception);
            }
        });

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    /**
     * handle google sign in result
     * @param requestCode
     * @param data
     */
    public void handleGoogleSignInResult(int requestCode,Intent data) {
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode != SocialLogin.RC_SIGN_IN) {
            return;
        }

        GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            Toast.makeText(mContext, "google login success "+acct.getDisplayName(), Toast.LENGTH_SHORT).show();
        } else {
            // Signed out, show unauthenticated UI.
            Toast.makeText(mContext, "Failed google login", Toast.LENGTH_SHORT).show();
        }
    }



    public interface Callback {
        //reports back to the calling activity
        void onLoginFinish();
    }
}
