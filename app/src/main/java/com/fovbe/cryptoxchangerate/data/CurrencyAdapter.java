package com.fovbe.cryptoxchangerate.data;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fovbe.cryptoxchangerate.BR;
import com.fovbe.cryptoxchangerate.ui.Convert;
import com.fovbe.cryptoxchangerate.R;
import com.fovbe.cryptoxchangerate.data.model.MoneyRates;
import com.fovbe.cryptoxchangerate.databinding.ItemCardBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Omafovbe Imonikosaye on 10/17/2017.
 */

public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.ViewHolder> {
    private List<MoneyRates> mRates = new ArrayList<>();

    public CurrencyAdapter(List<MoneyRates> mRates){
        this.mRates.addAll(mRates);
    }


    @Override
    public CurrencyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
        return new CurrencyAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.getBinding().setVariable(BR.curRates, mRates.get(position));
        holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mRates.size();
    }

    public void updateList(ArrayList<MoneyRates> newRates){
        final CurDiffCallback diffCallback = new CurDiffCallback(mRates, newRates);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        mRates.clear();
        mRates.addAll(newRates);
        diffResult.dispatchUpdatesTo(this);
    }

    public List<MoneyRates> getCurRates(){
        if (mRates == null){
            return new ArrayList<>(0);
        }
        return mRates;
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        public ItemCardBinding cardBinding;
        public ViewHolder(View v){
            super(v);
            cardBinding = DataBindingUtil.bind(v);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION) {
                        Context context = view.getContext();

                        context.startActivity(new Intent(context,Convert.class).putExtra("curInfo1", mRates.get(pos))
                                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    }
                }
            });
        }

        public ViewDataBinding getBinding(){
            return cardBinding;
        }
    }
}
