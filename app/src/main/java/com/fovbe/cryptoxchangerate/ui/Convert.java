package com.fovbe.cryptoxchangerate.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;

import com.fovbe.cryptoxchangerate.R;
import com.fovbe.cryptoxchangerate.data.model.MoneyRates;
import com.fovbe.cryptoxchangerate.databinding.ActivityConvertBinding;

import java.text.DecimalFormat;

import static android.view.KeyEvent.KEYCODE_0;
import static android.view.KeyEvent.KEYCODE_1;
import static android.view.KeyEvent.KEYCODE_2;
import static android.view.KeyEvent.KEYCODE_3;
import static android.view.KeyEvent.KEYCODE_4;
import static android.view.KeyEvent.KEYCODE_5;
import static android.view.KeyEvent.KEYCODE_6;
import static android.view.KeyEvent.KEYCODE_7;
import static android.view.KeyEvent.KEYCODE_8;
import static android.view.KeyEvent.KEYCODE_9;
import static android.view.KeyEvent.KEYCODE_DEL;

public class Convert extends AppCompatActivity implements KeyEvent.Callback {

    private ActivityConvertBinding convertActivityBind;
    private MoneyRates cryptoRates;
    private static final String TAG = Convert.class.getSimpleName();
    private static Double default_eth, default_btc;
    private int baseRateSwitch = 0;
    public boolean isEnabled = false;
    private Double calculate;

    private static final String BASE_SWITCH = "Base_Switch";
    private static final String BASE_ENABLED = "Base_Enabled";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.activity_convert);
        convertActivityBind = DataBindingUtil.setContentView(this, R.layout.activity_convert);
        //Log.e(TAG, "onCreate: On create Method");

        if (savedInstanceState != null){
            baseRateSwitch = savedInstanceState.getInt(BASE_SWITCH);
            isEnabled = savedInstanceState.getBoolean(BASE_ENABLED);
            calculateBaseSwitch();
            /*editTextStatus(isEnabled);

            convertActivityBind.ethAmount.setText(R.string.defaultAmount);
            convertActivityBind.btcAmount.setText(R.string.defaultAmount);*/
        }

       Bundle cryptoCurInfo = getIntent().getExtras();

        if (cryptoCurInfo != null){
            cryptoRates = cryptoCurInfo.getParcelable("curInfo1");
            default_btc = cryptoRates.getRateBTC();
            default_eth = cryptoRates.getRateETH();
        }

        //Set the views with values from the currency rate passed.
        convertActivityBind.setCurrency(cryptoRates);

        //set the text view with information of exchange rates
        convertActivityBind.baseInfo.setText(getString(R.string.baseInfo, cryptoRates.getCurrency(),
                cryptoRates.getRateBTC(), cryptoRates.getRateETH()));
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if(     keyCode == KEYCODE_0 || keyCode == KEYCODE_1 || keyCode == KEYCODE_2 ||
                keyCode == KEYCODE_3 || keyCode == KEYCODE_4 || keyCode == KEYCODE_5 ||
                keyCode == KEYCODE_6 || keyCode == KEYCODE_7 || keyCode == KEYCODE_8 ||
                keyCode == KEYCODE_9 || keyCode == KEYCODE_DEL) {

            //calRate();
            calculateRate(baseRateSwitch);
            return true;

        }

        return super.onKeyUp(keyCode, event);
    }

    /**
     *  transferSwitchListener method to handle onclick of the switch image
     *  from calculating the rates between one of the world currency with Bitcoin or Ethereum
     *
     */
    public void transferSwitchListener (View v){

           calculateBaseSwitch();

        }

        //Determine which to calculate for either BTC, ETH or Currency
        private void calculateBaseSwitch(){
            baseRateSwitch += 1;
            if (baseRateSwitch >2) {
                baseRateSwitch = 0;
                isEnabled = true;
            }

            //isEnabled = true;
            editTextStatus(isEnabled);

            convertActivityBind.ethAmount.setText(R.string.defaultAmount);
            convertActivityBind.btcAmount.setText(R.string.defaultAmount);
        }

    /**
     * Calculate Rate method, converts the money value of a currency to the respective
     * currency or crypto-currency.
     *
     * @param currencySwitch: know what currency to calculate for either BTC, ETC or one the World currencies.
     */

    public void calculateRate(int currencySwitch){
        final DecimalFormat decFormat = new DecimalFormat("#,##0.00");

        switch (currencySwitch){
            case 0:
                //Calculate the rates for both BTC and ETH
                String  tvCur = convertActivityBind.curAmount.getText().toString();
                if (!tvCur.isEmpty()) {
                    calculate = Double.parseDouble(tvCur);
                    calculate /= default_btc;
                    convertActivityBind.btcAmount.setText(decFormat.format(calculate));
                    convertActivityBind.ethAmount.setText(decFormat.format((Double.parseDouble(tvCur))/default_eth));
                }
                break;
            case 1:
                //Calculate the rate between BTC and the Currency
                String  tvBTC = convertActivityBind.btcAmount.getText().toString();
                if (!tvBTC.isEmpty()) {
                    calculate = Double.parseDouble(tvBTC);
                    calculate *= default_btc;
                    convertActivityBind.curAmount.setText(decFormat.format(calculate));
                }
                break;
            case 2:
                //Calculate the rate between ETH and the currency
                String  tvETH = convertActivityBind.ethAmount.getText().toString();
                if (!tvETH.isEmpty()) {
                    calculate = Double.parseDouble(tvETH);
                    calculate *= default_eth;
                    convertActivityBind.curAmount.setText(decFormat.format(calculate));
                }
                break;

        }

    }

    /**
     * Disabling or Enabling the various EditText field according
     * to whatever currency is being calculated
     *
     * @param setEnable: boolean
     */
    private void editTextStatus (boolean setEnable){

        convertActivityBind.curAmount.setEnabled(setEnable);
        convertActivityBind.curAmount.setText("");
        convertActivityBind.btcAmount.setEnabled(!setEnable);
        //convertActivityBind.ethAmount.setEnabled(setEnable);
        if (baseRateSwitch==2){
            convertActivityBind.ethAmount.setEnabled(true);
            convertActivityBind.curAmount.setEnabled(false);
        }
        else{
            convertActivityBind.ethAmount.setEnabled(false);
            convertActivityBind.curAmount.setEnabled(setEnable);
        }

        isEnabled = !isEnabled;

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(BASE_SWITCH, baseRateSwitch);
        outState.putBoolean(BASE_ENABLED, isEnabled);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        getSupportActionBar().setTitle(getString(R.string.convert, cryptoRates.getCurrency()));
        super.onResume();
    }
}
