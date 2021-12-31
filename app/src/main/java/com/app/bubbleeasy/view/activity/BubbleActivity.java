package com.app.bubbleeasy.view.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.*;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import com.app.bubbleeasy.R;
import com.app.bubbleeasy.Utills.Constant;
import com.app.bubbleeasy.databinding.ActivityBubbleLayoutBinding;
import com.app.bubbleeasy.model.Item;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class BubbleActivity extends AppCompatActivity implements View.OnLongClickListener, View.OnClickListener, BetterGestureDetector.BetterGestureListener {

    private FirebaseAnalytics mFirebaseAnalytics;
    SharedPreferences sharedpreferences;
    ActivityBubbleLayoutBinding binding;
    private ImageView imageView;
    List<Item> itemList = new ArrayList<>();
    int[] bubble_active = {R.mipmap.bubble_1, R.mipmap.bubble_2, R.mipmap.bubble_3,
            R.mipmap.bubble_4, R.mipmap.bubble_5};
    int[] bubble_inactive = {R.mipmap.bubble_pitch_1, R.mipmap.bubble_pitch_2, R.mipmap.bubble_pitch_3,
            R.mipmap.bubble_pitch_4, R.mipmap.bubble_pitch_5};
    int[] resID = {R.raw.tap1, R.raw.tap2, R.raw.tap3, R.raw.tap4};
    private Toolbar toolbar;
    MediaPlayer mp;
    long mLastStopTime = 0;
    private boolean isPause = false;
    private boolean isRestart = false;
    InterstitialAd mInterstitialAd;
    int unselectedCount = 0;
    long time;
    private BetterGestureDetector gestureDetector;
    private String rows, columns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_bubble_layout);
        sharedpreferences = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        gestureDetector = new BetterGestureDetector(this);

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        rows = sharedpreferences.getString(Constant.numberOfRows, "");
        columns = sharedpreferences.getString(Constant.numberOfColumns, "");

        binding.chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer cArg) {
                time = SystemClock.elapsedRealtime() - cArg.getBase();
                int h = (int) (time / 3600000);
                int m = (int) (time - h * 3600000) / 60000;
                int s = (int) (time - h * 3600000 - m * 60000) / 1000;
                String hh = h < 10 ? "0" + h : h + "";
                String mm = m < 10 ? "0" + m : m + "";
                String ss = s < 10 ? "0" + s : s + "";
                cArg.setText(hh + ":" + mm + ":" + ss);
            }
        });
        binding.chronometer.setBase(SystemClock.elapsedRealtime());
        binding.chronometer.start();

        binding.imgBack.setOnClickListener(this);
        binding.chronometer.setOnClickListener(this);
        binding.btnRestart.setOnClickListener(this);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.ad_id_interstitial));

        loadRequest();

        init();

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        gestureDetector.onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

    private void loadRequest() {

        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("358960061547934")
                .build();

        mInterstitialAd.loadAd(adRequest);
        binding.bannerAdView.loadAd(adRequest);
        binding.bannerAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                binding.bannerAdView.setVisibility(View.VISIBLE);
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdClicked() {

                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                binding.bannerAdView.setVisibility(View.GONE);
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        });

    }


    @Override
    public void onPause() {
        if (binding.bannerAdView != null) {
            binding.bannerAdView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (binding.bannerAdView != null) {
            binding.bannerAdView.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (binding.bannerAdView != null) {
            binding.bannerAdView.destroy();
        }
        super.onDestroy();
    }


    public void init() {

        for (int i = 0; i < Integer.parseInt(rows); i++) {
            final TableRow row = new TableRow(this);
            if (i % 2 == 0) {
                for (int j = 0; j < Integer.parseInt(columns) - 1; j++) {

                    TableLayout.LayoutParams tableRowParams =
                            new TableLayout.LayoutParams
                                    (TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT);

                    int leftMargin = 70;
                    int topMargin = 0;
                    int rightMargin = 0;
                    int bottomMargin = 0;

                    tableRowParams.setMargins(leftMargin, topMargin, rightMargin, bottomMargin);
                    row.setLayoutParams(tableRowParams);

                    imageView = new ImageView(this);
                    Random r = new Random();
                    int n = r.nextInt(5);
                    imageView.setBackgroundResource(bubble_active[n]);
                    //imageView.setText("" + i + "," + j);
                    Item item = new Item();
                    item.setSelected(false);
                    item.setRow(i);
                    item.setColumn(j);
                    itemList.add(item);
                    imageView.setTag(new Item(i, j, false));
                    row.addView(imageView);
                    imageView.setOnLongClickListener(this);
                }
            } else {
                for (int j = 0; j < Integer.parseInt(columns); j++) {

                    TableLayout.LayoutParams tableRowParams =
                            new TableLayout.LayoutParams
                                    (TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT);

                    int leftMargin = 0;
                    int topMargin = 0;
                    int rightMargin = 0;
                    int bottomMargin = 0;

                    tableRowParams.setMargins(leftMargin, topMargin, rightMargin, bottomMargin);
                    row.setLayoutParams(tableRowParams);


                    imageView = new ImageView(this);
                    Random r = new Random();
                    int n = r.nextInt(5);
                    imageView.setBackgroundResource(bubble_active[n]);
                    // imageView.setText("" + i + "," + j);
                    Item item = new Item();
                    item.setSelected(false);
                    item.setRow(i);
                    item.setColumn(j);
                    itemList.add(item);
                    imageView.setTag(new Item(i, j, false));
                    row.addView(imageView);
                    imageView.setOnLongClickListener(this);
                }
            }


            binding.tableLayout.addView(row, i);
        }
    }


    public boolean onLongClick(View view) {
        switch (view.getId()) {

            case -1:

                Item item = (Item) view.getTag();
                int row = item.row;
                int column = item.column;
                boolean selected = item.isSelected();

                if (selected) {
                    // do nothing
                } else {
                    item.setSelected(true);

                    if (sharedpreferences.getString(Constant.Vibrate, "").equals("1")) {
                        view.setHapticFeedbackEnabled(true);
                    } else {
                        view.setHapticFeedbackEnabled(false);
                    }

                    if (sharedpreferences.getString(Constant.Sound, "").equals("1")) {
                        Random r = new Random();
                        int n = r.nextInt(4);
                        mp = MediaPlayer.create(getApplicationContext(), resID[n]);
                        mp.start();
                    } /*else {
                        mp.reset();
                    }
*/
                    Random r = new Random();
                    int n = r.nextInt(5);
                    view.setBackgroundResource(bubble_inactive[n]);
                    unselectedCount = unselectedCount + 1;
                }

                int value1 = Integer.parseInt(rows) / 2 * (Integer.parseInt(columns) - 1);
                int value2 = Integer.parseInt(rows) / 2 * Integer.parseInt(columns);

                if (unselectedCount == (value1 + value2)) {
                    isPause = true;
                    mLastStopTime = binding.chronometer.getBase() - SystemClock.elapsedRealtime();
                    binding.chronometer.stop();
                    int h = (int) (time / 3600000);
                    int m = (int) (time - h * 3600000) / 60000;
                    int s = (int) (time - h * 3600000 - m * 60000) / 1000;
                    String hh = h < 10 ? "0" + h : h + "";
                    String mm = m < 10 ? "0" + m : m + "";
                    String ss = s < 10 ? "0" + s : s + "";
                    //    cArg.setText(hh + ":" + mm + ":" + ss);
                    showDialog(hh + ":" + mm + ":" + ss);
                }


//                System.out.println("row===" + row);
//                System.out.println("column===" + column);


                break;

            default:
                break;
        }

        return true;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:

                finish();
                break;

            case R.id.chronometer:

                if (isPause) {
                    isPause = false;
                    binding.chronometer.setBase(SystemClock.elapsedRealtime() + mLastStopTime);
                    binding.chronometer.start();
                    enable(binding.bubbleLayout);

                } else {
                    if (isRestart) {
                        isRestart = false;
                        binding.chronometer.setBase(SystemClock.elapsedRealtime());
                        binding.chronometer.start();
                        //enable(binding.bubbleLayout);

                    } else {
                        isPause = true;
                        mLastStopTime = binding.chronometer.getBase() - SystemClock.elapsedRealtime();
                        binding.chronometer.stop();
                        binding.chronometer.setText("Resume");

                        disable(binding.bubbleLayout);


                    }
                }


                break;

            case R.id.btn_restart:
                // Load ads into Interstitial Ads
                binding.chronometer.setVisibility(View.VISIBLE);
                loadRequest();
                showInterstitial();
                //isRestart = true;
                binding.chronometer.setBase(SystemClock.elapsedRealtime());
                mLastStopTime = 0;
                binding.chronometer.stop();
                int count = binding.tableLayout.getChildCount();
                for (int i = 0; i < count; i++) {
                    View child = binding.tableLayout.getChildAt(i);
                    if (child instanceof TableRow) ((ViewGroup) child).removeAllViews();
                }
                unselectedCount = 0;
                init();
                if (isPause) {
                    isPause = true;
                    disable(binding.bubbleLayout);
                    binding.chronometer.setText("Resume");
                } else {
                    isPause = false;
                    isRestart = false;
                    enable(binding.bubbleLayout);
                    binding.chronometer.setBase(SystemClock.elapsedRealtime());
                    binding.chronometer.start();
                    // binding.chronometer.setText("Resume");
                }

                break;
            default:
                break;
        }
    }


    private static void disable(ViewGroup layout) {
        layout.setEnabled(false);
        for (int i = 0; i < layout.getChildCount(); i++) {
            View child = layout.getChildAt(i);
            if (child instanceof ViewGroup) {
                disable((ViewGroup) child);
            } else {
                child.setEnabled(false);
            }
        }
    }

    private static void enable(ViewGroup layout) {
        layout.setEnabled(true);
        for (int i = 0; i < layout.getChildCount(); i++) {
            View child = layout.getChildAt(i);
            if (child instanceof ViewGroup) {
                enable((ViewGroup) child);
            } else {
                child.setEnabled(true);
            }
        }
    }


    private void showInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }


    public void showDialog(String time) {

        final Dialog dialog = new Dialog(this, R.style.translucentDialog);
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.round_corner);

        TextView tv_time = (TextView) dialog.findViewById(R.id.tv_time);
        tv_time.setText(String.valueOf(time));

        TextView dialogButton1 = (TextView) dialog.findViewById(R.id.tv_ok);
        dialogButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                //  unselectedCount = 10;
                // mLastStopTime = 0;
                binding.chronometer.setBase(SystemClock.elapsedRealtime());
                binding.chronometer.stop();
                isPause = true;
                binding.chronometer.setText("Resume");
            }
        });

        TextView dialogButton2 = (TextView) dialog.findViewById(R.id.tv_restart);
        dialogButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                /*Firebase Analytics event*/
                Bundle params = new Bundle();
                params.putString("TotalBubleCount", String.valueOf(unselectedCount));
                mFirebaseAnalytics.logEvent("GameOver", params);

                unselectedCount = 0;
                int count = binding.tableLayout.getChildCount();
                for (int i = 0; i < count; i++) {
                    View child = binding.tableLayout.getChildAt(i);
                    if (child instanceof TableRow) ((ViewGroup) child).removeAllViews();
                }
                mLastStopTime = 0;
                init();
                binding.chronometer.setBase(SystemClock.elapsedRealtime());
                binding.chronometer.start();

            }
        });

        dialog.show();

    }


    /**
     * On show press.
     *
     * @param motionEvent the motion event
     */
    @Override
    public void onPress(MotionEvent motionEvent) {
        System.out.println(" press============");
    }

    /**
     * On single tap up.
     *
     * @param motionEvent the motion event
     */
    @Override
    public void onTap(MotionEvent motionEvent) {
        System.out.println(" tap ============");
    }

    /**
     * On drag.
     *
     * @param motionEvent the motion event
     */
    @Override
    public void onDrag(MotionEvent motionEvent) {
        System.out.println("drag============");
    }

    /**
     * On move.
     *
     * @param motionEvent the motion event
     */
    @Override
    public void onMove(MotionEvent motionEvent) {
        System.out.println("move ============");
    }

    /**
     * On release.
     *
     * @param motionEvent the motion event
     */
    @Override
    public void onRelease(MotionEvent motionEvent) {
        if (mp != null) {
            mp.release();
        }
        System.out.println("release============");
    }

    /**
     * On long press.
     *
     * @param motionEvent the motion event
     */
    @Override
    public void onLongPress(MotionEvent motionEvent) {
        System.out.println("long press============");
    }

    /**
     * On double tap.
     *
     * @param motionEvent the motion event
     * @param clicks
     */
    @Override
    public void onMultiTap(MotionEvent motionEvent, int clicks) {
        System.out.println("multi tap============");
    }



    /* GameOver(TotalBubleCount);*/
}
