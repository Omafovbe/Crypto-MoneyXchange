package com.fovbe.cryptoxchangerate.network;

import com.fovbe.cryptoxchangerate.data.model.Crytocoin;

import io.reactivex.Observable;
import retrofit2.http.GET;

import static com.fovbe.cryptoxchangerate.utils.Constants.FROM_SYSM;
import static com.fovbe.cryptoxchangerate.utils.Constants.TO_SYSM;

/**
 * Created by OWNER1 on 10/24/2017.
 */

public interface CryptoInterface {
    @GET("pricemulti?fsyms="+FROM_SYSM+"&tsyms="+TO_SYSM)
    Observable<Crytocoin> getCompareRates();
}
