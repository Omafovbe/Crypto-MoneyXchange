package com.fovbe.cryptoxchangerate.data.model;

import android.annotation.SuppressLint;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;

import com.fovbe.cryptoxchangerate.BR;

import java.util.Objects;

/**
 * Created by Omafovbe Imonikosaye on 10/7/2017.
 */

public class MoneyRates extends BaseObservable implements Parcelable {

    private int countryFlag;
    private String currency;
    private String curName;
    private String curSym;
    private Double rateBTC;
    private Double rateETH;

    //Empty Constructor
    public MoneyRates() {
    }


    public MoneyRates(int countryFlag, String currency, String curName, String curSym, Double rateBTC, Double rateETH) {

        this.countryFlag = countryFlag;
        this.currency = currency;
        this.curName = curName;
        this.curSym = curSym;

        this.rateBTC = rateBTC;
        this.rateETH = rateETH;
    }


    @Bindable
    public String getCurName() {
        return curName;
    }

    public void setCurName(String curName) {
        this.curName = curName;
        notifyPropertyChanged(BR.curName);
    }

    @Bindable
    public int getCountryFlag() {
        return countryFlag;
    }

    public void setCountryFlag(int countryFlag) {
        this.countryFlag = countryFlag;
        notifyPropertyChanged(BR.countryFlag);
    }

    @Bindable
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
        notifyPropertyChanged(BR.currency);
    }

    @Bindable
    public String getCurSym() {
        return curSym;
    }

    public void setCurSym(String curSym) {
        this.curSym = curSym;
        notifyPropertyChanged(BR.curSym);
    }

    @Bindable
    public Double getRateBTC() {
        return rateBTC;
    }

    public void setRateBTC(Double rateBTC) {
        this.rateBTC = rateBTC;
        notifyPropertyChanged(BR.rateBTC);
    }

    @Bindable
    public Double getRateETH() {
        return rateETH;
    }

    public void setRateETH(Double rateETH) {
        this.rateETH = rateETH;
        notifyPropertyChanged(BR.rateETH);
    }


    @SuppressLint("NewApi")
    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof MoneyRates)) {
            return false;
        }
        MoneyRates newCurrency = (MoneyRates) obj;

        return Objects.equals(currency, newCurrency.currency) &&
                Objects.equals(curName, newCurrency.curName) &&
                Objects.equals(curSym, newCurrency.curSym);
    }

    @SuppressLint("NewApi")
    @Override
    public int hashCode() {
      return Objects.hash(curName,currency,curSym);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.countryFlag);
        dest.writeString(this.currency);
        dest.writeString(this.curName);
        dest.writeString(this.curSym);
        dest.writeValue(this.rateBTC);
        dest.writeValue(this.rateETH);
    }

    protected MoneyRates(Parcel in) {
        this.countryFlag = in.readInt();
        this.currency = in.readString();
        this.curName = in.readString();
        this.curSym = in.readString();
        this.rateBTC = (Double) in.readValue(Double.class.getClassLoader());
        this.rateETH = (Double) in.readValue(Double.class.getClassLoader());
    }

    public static final Parcelable.Creator<MoneyRates> CREATOR = new Parcelable.Creator<MoneyRates>() {
        @Override
        public MoneyRates createFromParcel(Parcel source) {
            return new MoneyRates(source);
        }

        @Override
        public MoneyRates[] newArray(int size) {
            return new MoneyRates[size];
        }
    };
}
