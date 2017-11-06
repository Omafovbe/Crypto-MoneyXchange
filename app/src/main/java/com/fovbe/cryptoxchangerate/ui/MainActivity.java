package com.fovbe.cryptoxchangerate.ui;

import android.content.Context;
import android.content.res.ColorStateList;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.fovbe.cryptoxchangerate.R;
import com.fovbe.cryptoxchangerate.data.CurrencyAdapter;
import com.fovbe.cryptoxchangerate.data.CustomItemTouchHelper;
import com.fovbe.cryptoxchangerate.data.model.Crytocoin;
import com.fovbe.cryptoxchangerate.data.model.MoneyRates;
import com.fovbe.cryptoxchangerate.databinding.ActivityMainBinding;
import com.fovbe.cryptoxchangerate.network.CryptoAPI;
import com.fovbe.cryptoxchangerate.network.CryptoInterface;
import com.fovbe.cryptoxchangerate.utils.SharedPrefManager;
import com.fovbe.cryptoxchangerate.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements CustomItemTouchHelper.CustomItemTouchHelperListener, AdapterView.OnItemSelectedListener{

    private ActivityMainBinding mBind;
    private ArrayList<MoneyRates> rates = new ArrayList<>();
    public CurrencyAdapter mCurAdapter;
    public JSONObject currencyRateBTC;
    public JSONObject currencyRateETH;
    public String btcJSON;
    public String ethJSON;

    private static final String TAG = MainActivity.class.getSimpleName();

    public String[] currencyNames;
    public String[] curCode;
    public String[] curSymbol;
    public SharedPrefManager sharedPref;
    private final Context mContext = this;
    private Disposable compositeDisposable;
    private CryptoInterface apiService;

    private static final String CURRENCY_CARDS_KEY = "currency_cards_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Enable data binding class for main activity and
        //Binding the layout.
        mBind = DataBindingUtil.setContentView(this, R.layout.activity_main);

        //Shared Preference Manager Initialization
        sharedPref = new SharedPrefManager(mContext);

        // Get previously saved or currency cards from Shared Preference Manager
        rates = sharedPref.getCurCards(mContext);

        // if no cards have been saved re-initialize the the ArrayList
        if (rates == null) rates = new ArrayList<>();

        //Initialize all necessary variables, views classes.
        initialization();

        //There anything on our savedInstanceState? If yes, display rather than creating afresh
        if(savedInstanceState != null){
            rates = savedInstanceState.getParcelableArrayList(CURRENCY_CARDS_KEY);
            // update the recyclerView
            if (rates != null && !rates.isEmpty())
                mCurAdapter.updateList(rates);
            else    rates = new ArrayList<>();
        }

        //Set the background
        backgroundVIew();

        //Set the actionbar
        setSupportActionBar(mBind.mytoolbar);

        //Fetch from crypto-compare api with retrofit
        getRates();
    }


    public void initialization(){

        // Initialize the currency names, symbol and code-name into their various arrays
        // from string resources
        currencyNames = getResources().getStringArray(R.array.currency);
        curSymbol = getResources().getStringArray(R.array.curSymbol);
        curCode = getResources().getStringArray(R.array.curCode);

        //Initializing the recyclerVIew
        mBind.cardRV.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));
        mBind.cardRV.setHasFixedSize(true);
        mCurAdapter = new CurrencyAdapter(rates);
        mBind.cardRV.setItemAnimator(new DefaultItemAnimator());
        mBind.cardRV.setAdapter(mCurAdapter);
        mBind.cardRV.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));

        //Initializing the spinner with values using the ArrayAdapter
        mBind.curSelect.setOnItemSelectedListener(this);
        ArrayAdapter<String> adapter =  new ArrayAdapter<>(mContext, android.R.layout.simple_dropdown_item_1line, currencyNames );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mBind.curSelect.setAdapter(adapter);
        mBind.curSelect.setEnabled(false); //Disable the spinner

        // Initializing the itemTouchHelper callback.
        // To listen for touch event like drag and swipe and act on it.
        ItemTouchHelper.Callback customItemTouchHelper = new CustomItemTouchHelper(this);

        // attaching the touch helper to recycler view
        new ItemTouchHelper(customItemTouchHelper).attachToRecyclerView(mBind.cardRV);

        mBind.colToolBar.setExpandedTitleTextColor(ColorStateList.valueOf(Color.TRANSPARENT));

    }

    /**
     *    Spinner OnItemSelected Listener method.
     *    Gets the selected item from the spinner every time the user selects a currency
     *    The item selected is either added or disregarded if on the list.
     *
     *    i= selected item position on the adapterview array
     */
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        // First item on the spinner is an instruction to "select a currency"
        // and should be ignored. Therefore value should start from position 1 not 0

        if(i>=1) {

            // Accessing values from other arrays, such as currency code (NGN, USD)
            // currency symbol(₦, $, €) and other we need decrease the position by 1
            int pos = i - 1;

            // Create a temporary array list to hold the new currency card to add to screen
            List<MoneyRates> tempCards = new ArrayList<>();

            //Use try and catch for the JSON Object
            try {
                Log.e(TAG, ""+ currencyRateBTC);

            // Add the new currency to the temporary array list
                tempCards.add(new MoneyRates(pos, curCode[pos], currencyNames[i], curSymbol[pos],
                        currencyRateBTC.getDouble(curCode[pos]), currencyRateETH.getDouble(curCode[pos])));

            } catch (JSONException e) {
                e.printStackTrace();
                Log.e(TAG, "JSONException: " + e.getMessage());
            }

            // With a for loop, iterate the newly added currency
            // as against list on Recycler View on the application screen to know
            // if the currency have been added before.
            // If it has using, a ArrayList .contain function, do not add to the screen,
            // but if not include to the screen and display a success message.
            for(MoneyRates mTempCard: tempCards){
                if(!rates.contains(mTempCard)){

                    //Add the card to the list from the temp list
                    rates.add(mTempCard);

                    // Update the recycler view
                    mCurAdapter.updateList(rates);
                    backgroundVIew();

                    // Save the currency card to shared preference.
                    // Which would always be the last item
                    int len = rates.size();
                    sharedPref.addUserCard(mContext,rates.get(len-1));

                    // reset the spinner.
                    mBind.curSelect.setSelection(0);

                    // Display the added information on a snackbar
                    Snackbar.make(mBind.coLay,"Added " + adapterView.getItemAtPosition(i).toString() + " successfully", Snackbar.LENGTH_LONG).show();

                } else
                    // Let the user know the card is on his/her list.
                    Snackbar.make(mBind.coLay,"" + adapterView.getItemAtPosition(i).toString() + " added already", Snackbar.LENGTH_LONG).show();
            }

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof CurrencyAdapter.ViewHolder) {
            // get the removed item name to display it in snack bar
            String name = rates.get(viewHolder.getAdapterPosition()).getCurName();

            // backup of removed item for undo purpose
            final MoneyRates deletedItem = rates.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();

            // remove the item from recycler view and delete from shared Preferences
            sharedPref.removeUserCard(mContext, deletedItem);
            rates.remove(viewHolder.getAdapterPosition());

            // Update the recycler view
            mCurAdapter.updateList(rates);
            backgroundVIew();

            // showing snack bar with Undo option
            Snackbar snackbar = Snackbar
                    .make(mBind.coLay, name + " removed from list", Snackbar.LENGTH_LONG);


            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // undo is clicked, restore the deleted item
                    rates.add(deletedIndex, deletedItem);
                    Log.e(TAG, "restored: "+ deletedIndex);

                    // update the recyclerView and save the ArrayList
                    mCurAdapter.updateList(rates);
                    backgroundVIew();
                    //sharedPref.saveUserCards(mContext, rates);
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }
    }

    @Override
    public void onMove(RecyclerView.ViewHolder viewHolder, int fromPosition, int toPosition) {
        // Start position is less than target position
        // swapping an item down the list with another
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                // Do a swap of the item respective to the new position and update recycler view
                Collections.swap(rates, i, i + 1);

                // Update the recycler view
                mCurAdapter.updateList(rates);
            }
        } else {
            // otherwise user is moving the card up the list
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(rates, i, i - 1);
                // Update the recycler view
                mCurAdapter.updateList(rates);
            }
        }

    }

    //Change the background view to world map or plain gray depending on ArrayList
    public void backgroundVIew(){
        if(rates.isEmpty()){
            mBind.coLay.setBackgroundResource(R.drawable.bg);
        } else {mBind.coLay.setBackgroundColor(getResources().getColor(R.color.light_gray));}
    }

    //Network call to fetch rates using observables and retrofit
    public void getRates(){
        //Check if device is connected online
        boolean isOnline = new Utils(this).isConnectedOnline();
        if(!isOnline){
            //SnackBar to notify offline
            Snackbar.make(mBind.coLay,"Oops! You're offline", Snackbar.LENGTH_INDEFINITE)
                    .setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            //Recursive: Call self on RETRY btn clicked if the device is online
                            getRates();
                        }
                    })
                    .setActionTextColor(Color.YELLOW)
                    .show();

            return;
        }

        //Info user we're fetching the latest currency rate
        mBind.updateInfo.setText(R.string.loading);

        //RxObservable for network calling
        apiService = CryptoAPI.getClient().create(CryptoInterface.class);
        Observable<Crytocoin> coinResponse = apiService.getCompareRates();
        coinResponse.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(@NonNull Observable<Throwable> throwableObservable) throws Exception {
                        Log.e(TAG, "Wait 5s before retry on timeout");
                        return throwableObservable.delay(5, TimeUnit.SECONDS);

                    }
                })
                .subscribe(new Observer<Crytocoin>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.e(TAG, "Subscription Started ");
                        compositeDisposable = d;

                    }

                    @Override
                    public void onNext(@NonNull Crytocoin crytocoin) {
                        //
                        btcJSON = crytocoin.getBTC().toString();
                        ethJSON = crytocoin.getETH().toString();

                        try {
                            currencyRateBTC = new JSONObject(btcJSON);
                            currencyRateETH = new JSONObject(ethJSON);

                            mBind.curSelect.setEnabled(true); //Enable the spinner after fetching values
                            Log.e(TAG, "Fetched BTC "+crytocoin.getBTC().toString()+"\n\rFetched ETH "+
                                    crytocoin.getETH().toString());

                            if(rates != null) {
                                for (MoneyRates updateRates : rates) {
                                    //Get the lastest crypto-currency rates
                                    Double setBTC = currencyRateBTC.getDouble(updateRates.getCurrency());
                                    Double setETH = currencyRateETH.getDouble(updateRates.getCurrency());

                                    //update the ArrayList with the recent rate values
                                    updateRates.setRateBTC(setBTC);
                                    updateRates.setRateETH(setETH);

                                }
                                //Display update message.
                                Snackbar.make(mBind.coLay, R.string.update_msg, Snackbar.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, "onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        setUpdateTime();
                        Log.d(TAG, "Fetched and updated the rates.");
                    }
                });
    }

    //Method to notify the user on the last time update was carried out.
    private void setUpdateTime(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E MMM dd, HH:mm:ss");
        String formattedDate = simpleDateFormat.format(c.getTime());
        mBind.updateInfo.setText(getString(R.string.last_update, formattedDate));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.opt_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Utils alertMsgs = new Utils(this);
        switch (item.getItemId()){
            case R.id.mnUpdate:
                //if we have at least a currency card to update its value
                if(!rates.isEmpty() && rates != null)
                    getRates();
                else //No cards to update the rates
                    Snackbar.make(mBind.coLay, "No cards on your screen", Snackbar.LENGTH_LONG).show();

                break;
            case R.id.mnHelp:
                //Displays features on how to use the app
                alertMsgs.displayAlertDialog(R.string.helpTitle, R.string.helpMsg);
                break;
            case R.id.mnAbout:
                //Gives information about the developer
                alertMsgs.displayAlertDialog(R.string.aboutTitle, R.string.aboutMsg);

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // save the current state of the application screen
        // on orientation change
        if (mCurAdapter.getItemCount() != 0) {

            outState.putParcelableArrayList(CURRENCY_CARDS_KEY, (ArrayList<MoneyRates>) mCurAdapter.getCurRates());
        }
        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onStop() {
        // Save the user currency cards on stopping the activity
        if(rates != null)
            sharedPref.saveUserCards(mContext,rates);

        super.onStop();
    }

    @Override
    protected void onDestroy() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose(); //dispose the observable
            Log.e(TAG, "onDestroy: disposable cleared" );
        }

        super.onDestroy();
    }
}
