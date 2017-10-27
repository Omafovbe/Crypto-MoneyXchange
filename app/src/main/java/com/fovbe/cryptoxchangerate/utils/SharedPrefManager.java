package com.fovbe.cryptoxchangerate.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.fovbe.cryptoxchangerate.data.model.MoneyRates;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by OWNER1 on 10/17/2017.
 */

public class SharedPrefManager {
    //Shared Preferences
    SharedPreferences mSharedPref;

    //Context
    Context mContext;

    //Preference Mode
    int PRIVATE_MODE = 0;

    //Editor for Shared preferences
    Editor editor;

    //Shared Pref file name
    private static final String PREF_NAME = "CryptoXchange";
    public static final String ALL_CARDS = "All_Cards";


    public SharedPrefManager (Context _mContext){
        mContext = _mContext;
        mSharedPref = mContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = mSharedPref.edit();
    }

    /*
    **  Takes the application context as well as the list of cards which the user has
    *   saved or added to application screen
     */
    public void saveUserCards(Context context, List<MoneyRates> uCards){
        //Create variables for Shared Preferences.
        SharedPreferences settings;
        Editor mEditor;

        //Initialize the Shared Preferences variables
        settings = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        mEditor = settings.edit();

        //Use Gson to convert the ArrayList to JSON String
        Gson gson = new Gson();
        String jsonUserCards = gson.toJson(uCards);

        //Save all the currency cards.
        mEditor.putString(ALL_CARDS,jsonUserCards);
        mEditor.commit();
    }

    //Save a currency card at a time;
    public void addUserCard(Context context, MoneyRates curCard){
        List<MoneyRates> allCards = getCurCards(context);

        if (allCards == null)
            allCards = new ArrayList<MoneyRates>();
        allCards.add(curCard);
        saveUserCards(context,allCards);
    }

    //Remove a currency card from the saved list
    public void removeUserCard(Context context, MoneyRates curCard){
        ArrayList<MoneyRates> curCards = getCurCards(context);
        if (curCards != null){
            curCards.remove(curCard);
            saveUserCards(context, curCards);
        }
    }

    //Method to convert JSON string to ArrayList
    public ArrayList<MoneyRates> getCurCards(Context context){
        SharedPreferences settings;
        List<MoneyRates> allCards;

        settings = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);

        if(settings.contains(ALL_CARDS)){
            String jsonCards = settings.getString(ALL_CARDS, null);
            Gson gson = new Gson();
            MoneyRates[] curItems = gson.fromJson(jsonCards,MoneyRates[].class);
            allCards = Arrays.asList(curItems);
            allCards = new ArrayList<MoneyRates>(allCards);
        } else
            return null;

        //Return all currency cards as array list
        return (ArrayList<MoneyRates>) allCards;
    }

}
