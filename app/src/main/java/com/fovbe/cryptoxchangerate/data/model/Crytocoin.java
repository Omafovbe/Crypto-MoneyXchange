package com.fovbe.cryptoxchangerate.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Omafovbe Imonikosaye on 10/7/2017.
 *
 * Class for both BTC and Ethereum info
 */

public class Crytocoin {
    @SerializedName("ETH")
    @Expose
    private ETH eTH;

    @SerializedName("BTC")
    @Expose
    private BTC bTC;

    public ETH getETH() {
        return eTH;
    }

    public void setETH(ETH eTH) {
        this.eTH = eTH;
    }

    public BTC getBTC() {
        return bTC;
    }

    public void setBTC(BTC bTC) {
        this.bTC = bTC;
    }
}
