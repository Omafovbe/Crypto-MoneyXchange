package com.fovbe.cryptoxchangerate.data;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import com.fovbe.cryptoxchangerate.data.model.MoneyRates;

import java.util.List;

/**
 * Created by Omafovbe Imonikosaye on 10/17/2017.
 *
 * Currency DiffUtill is a utility class that finds the difference between
 * two lists and provides the updated list as an output.
 * We can use this utility class to notify updates to a RecyclerView Adapter.
 *
 * References
 * ===========
 * https://android.jlelse.eu/smart-way-to-update-recyclerview-using-diffutil-345941a160e0
 * http://blogs.quovantis.com/how-to-use-diffutil-with-recyclerview-adapter-in-android/
 *
 */

public class CurDiffCallback extends DiffUtil.Callback {
    //fields to get the old list as well as the new list
    private List<MoneyRates> oldRateList;
    private List<MoneyRates> newRateList;

    public CurDiffCallback(List<MoneyRates> oldRateList, List<MoneyRates> newRateList) {
        this.oldRateList = oldRateList;
        this.newRateList = newRateList;
    }

    //This method will return the size of the old list.
    @Override
    public int getOldListSize() {
        return oldRateList.size();
    }

    //This method will return the size of the new list.
    @Override
    public int getNewListSize() {
        return newRateList.size();
    }

    //This callback method decides whether two objects are representing same items or not
    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldRateList.get(oldItemPosition).getCurrency() == newRateList.get(newItemPosition).getCurrency();
    }

    //This callback method decides that two items have same data or not. This method will only
    // be called if the return type is true.
    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        MoneyRates oldMoneyRate = oldRateList.get(oldItemPosition);
        MoneyRates newMoneyRate = newRateList.get(newItemPosition);
        return oldMoneyRate.getCurrency().equals(newMoneyRate.getCurrency());
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        //Return changes if areItemTheSame true and areContentsTheSame false
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
