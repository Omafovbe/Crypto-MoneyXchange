package com.fovbe.cryptoxchangerate.utils;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.fovbe.cryptoxchangerate.R;

/**
 * Created by Omafovbe Imonikosaye on 10/17/2017.
 *
 * BindingAdapter class for country flags of various
 * currencies
 */

public class LoadImage {
    public static int[] curFlags = new int[]{
            R.drawable.australia, R.drawable.brazil, R.drawable.canada, R.drawable.china,
            R.drawable.european_union, R.drawable.united_kingdom, R.drawable.hong_kong,R.drawable.india,
            R.drawable.israel, R.drawable.japan, R.drawable.malaysia, R.drawable.mexico,
            R.drawable.nigeria, R.drawable.norway, R.drawable.pakistan, R.drawable.russia,
            R.drawable.singapore, R.drawable.south_africa, R.drawable.sweden, R.drawable.switzerland,
            R.drawable.thailand, R.drawable.turkey, R.drawable.usa
    };

    // binding adapter for the image view on the on the screen.
    // Gets the position of the flag and match with the corresponding
    // array, so as to display/load into the image view.

    @BindingAdapter("loadImage")
    public static void loadImage(ImageView view, int res){
        //Log.e("Image Resource", "setImageResource: "+ res);
        view.setImageResource(curFlags[res]);
    }
}
