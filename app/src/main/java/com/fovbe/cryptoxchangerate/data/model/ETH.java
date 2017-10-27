package com.fovbe.cryptoxchangerate.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Omafovbe Imonikosaye on 10/7/2017.
 *
 * Method class for Ethereum
 */

public class ETH {
    @SerializedName("AUD")
    @Expose
    private Double aUD;
    @SerializedName("BRL")
    @Expose
    private Double bRL;
    @SerializedName("CAD")
    @Expose
    private Double cAD;
    @SerializedName("CNY")
    @Expose
    private Double cNY;
    @SerializedName("EUR")
    @Expose
    private Double eUR;
    @SerializedName("GBP")
    @Expose
    private Double gBP;
    @SerializedName("HKD")
    @Expose
    private Double hKD;
    @SerializedName("INR")
    @Expose
    private Double iNR;
    @SerializedName("ILS")
    @Expose
    private Double iLS;
    @SerializedName("JPY")
    @Expose
    private Double jPY;
    @SerializedName("MYR")
    @Expose
    private Double mYR;
    @SerializedName("MXN")
    @Expose
    private Double mXN;
    @SerializedName("NGN")
    @Expose
    private Double nGN;
    @SerializedName("NOK")
    @Expose
    private Double nOK;
    @SerializedName("PKR")
    @Expose
    private Double pKR;
    @SerializedName("RUB")
    @Expose
    private Double rUB;
    @SerializedName("SGD")
    @Expose
    private Double sGD;
    @SerializedName("ZAR")
    @Expose
    private Double zAR;
    @SerializedName("SEK")
    @Expose
    private Double sEK;
    @SerializedName("CHF")
    @Expose
    private Double cHF;
    @SerializedName("THB")
    @Expose
    private Double tHB;
    @SerializedName("TRY")
    @Expose
    private Double tRY;
    @SerializedName("USD")
    @Expose
    private Double uSD;

    public Double getAUD() {
        return aUD;
    }

    public void setAUD(Double aUD) {
        this.aUD = aUD;
    }

    public Double getBRL() {
        return bRL;
    }

    public void setBRL(Double bRL) {
        this.bRL = bRL;
    }

    public Double getCAD() {
        return cAD;
    }

    public void setCAD(Double cAD) {
        this.cAD = cAD;
    }

    public Double getCNY() {
        return cNY;
    }

    public void setCNY(Double cNY) {
        this.cNY = cNY;
    }

    public Double getEUR() {
        return eUR;
    }

    public void setEUR(Double eUR) {
        this.eUR = eUR;
    }

    public Double getGBP() {
        return gBP;
    }

    public void setGBP(Double gBP) {
        this.gBP = gBP;
    }

    public Double getHKD() {
        return hKD;
    }

    public void setHKD(Double hKD) {
        this.hKD = hKD;
    }

    public Double getINR() {
        return iNR;
    }

    public void setINR(Double iNR) {
        this.iNR = iNR;
    }

    public Double getILS() {
        return iLS;
    }

    public void setILS(Double iLS) {
        this.iLS = iLS;
    }

    public Double getJPY() {
        return jPY;
    }

    public void setJPY(Double jPY) {
        this.jPY = jPY;
    }

    public Double getMYR() {
        return mYR;
    }

    public void setMYR(Double mYR) {
        this.mYR = mYR;
    }

    public Double getMXN() {
        return mXN;
    }

    public void setMXN(Double mXN) {
        this.mXN = mXN;
    }

    public Double getNGN() {
        return nGN;
    }

    public void setNGN(Double nGN) {
        this.nGN = nGN;
    }

    public Double getNOK() {
        return nOK;
    }

    public void setNOK(Double nOK) {
        this.nOK = nOK;
    }

    public Double getPKR() {
        return pKR;
    }

    public void setPKR(Double pKR) {
        this.pKR = pKR;
    }

    public Double getRUB() {
        return rUB;
    }

    public void setRUB(Double rUB) {
        this.rUB = rUB;
    }

    public Double getSGD() {
        return sGD;
    }

    public void setSGD(Double sGD) {
        this.sGD = sGD;
    }

    public Double getZAR() {
        return zAR;
    }

    public void setZAR(Double zAR) {
        this.zAR = zAR;
    }

    public Double getSEK() {
        return sEK;
    }

    public void setSEK(Double sEK) {
        this.sEK = sEK;
    }

    public Double getCHF() {
        return cHF;
    }

    public void setCHF(Double cHF) {
        this.cHF = cHF;
    }

    public Double getTHB() {
        return tHB;
    }

    public void setTHB(Double tHB) {
        this.tHB = tHB;
    }

    public Double getTRY() {
        return tRY;
    }

    public void setTRY(Double tRY) {
        this.tRY = tRY;
    }

    public Double getUSD() {
        return uSD;
    }

    public void setUSD(Double uSD) {
        this.uSD = uSD;
    }

    @Override
    public String toString() {
        return "{" +
                "AUD:" + aUD +
                ", BRL:" + bRL +
                ", CAD:" + cAD +
                ", CNY:" + cNY +
                ", EUR:" + eUR +
                ", GBP:" + gBP +
                ", HKD:" + hKD +
                ", INR:" + iNR +
                ", ILS:" + iLS +
                ", JPY:" + jPY +
                ", MYR:" + mYR +
                ", MXN:" + mXN +
                ", NGN:" + nGN +
                ", NOK:" + nOK +
                ", PKR:" + pKR +
                ", RUB:" + rUB +
                ", SGD:" + sGD +
                ", ZAR:" + zAR +
                ", SEK:" + sEK +
                ", CHF:" + cHF +
                ", THB:" + tHB +
                ", TRY:" + tRY +
                ", USD:" + uSD +
                '}';
    }
}
