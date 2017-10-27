package com.fovbe.cryptoxchangerate.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.animation.AnimationUtils;

import com.fovbe.cryptoxchangerate.R;

/**
 * Created by OWNER1 on 10/26/2017.
 */

public class Utils extends ContextWrapper {

    public Utils(Context base) {
        super(base);
    }

    /**
     * isConnectedOnline
     * ===================
     * Method to check for users' connectivity; whether the device is online or not.
     *
     * @return
     */

    public boolean isConnectedOnline (){

        ConnectivityManager connManager = (ConnectivityManager) this.getSystemService(CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = connManager.getActiveNetworkInfo();

        return activeNetwork !=null && activeNetwork.isConnected();

    }

    /**
     *  Slide Animation for displaying content.
     * @param view: The view to attach the animation effect
     * @param ctx: Context
     */
    public static void slideAnim(View view, Context ctx){
        view.startAnimation(AnimationUtils.loadAnimation(ctx, R.anim.slide_anim));
    }

    /**
     * Dialog to display on the user screen to pass information
     * @param sTitle: Title of the alert Dialog.
     * @param sMsg: The message to display.
     */
    public void displayAlertDialog(int sTitle, int sMsg){
        AlertDialog.Builder msgDialog = new AlertDialog.Builder(this);
        msgDialog.setTitle(getString(sTitle));
        msgDialog.setMessage(getString(sMsg));
        msgDialog.show();
    }

}
