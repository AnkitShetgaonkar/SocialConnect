package com.ankdroid.socialconnect.lib;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

/**
 * Created by ankit on 14/09/16.
 * Helper class to abstract social login, presently of facebook, google and twitter
 * instansize this class before setContentView
 */
public class SocialLogin {


    private static SocialLogin mSocialLogin;
    private Context mContext;

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
     * logs in to google, should implement callback to receive info
     *
     * @param context
     */
    public void googleLogin(Context context) {

    }

    /**
     * logs in to twitter, should implement callback to receive info
     *
     * @param context
     */
    public static void twtLogin(Context context) {

    }


    public interface Callback {
        //reports back to the calling activity
        void onLoginFinish();
    }
}
