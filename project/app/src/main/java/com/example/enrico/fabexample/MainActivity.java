package com.example.enrico.fabexample;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton fabMain, fabOne, fabTwo;
    RecyclerView recyclerView;
    private AnimatedVectorDrawableCompat plusToMinus, minusToPlus;
    private boolean isShowingPlus = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFab();
        initRecyclerView();
    }

    //method to init fab buttons
    private void initFab() {

        //get animated vector drawable
        plusToMinus = AnimatedVectorDrawableCompat.create(MainActivity.this, R.drawable.avd_pathmorph_plusminus_plus_to_minus);
        minusToPlus = AnimatedVectorDrawableCompat.create(MainActivity.this, R.drawable.avd_pathmorph_plusminus_minus_to_plus);

        //get all the fab
        fabMain = (FloatingActionButton) findViewById(R.id.fab_main);
        fabOne = (FloatingActionButton) findViewById(R.id.fab_one);
        fabTwo = (FloatingActionButton) findViewById(R.id.fab_two);

        //set click listener on main fab
        fabMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //method to start animated vector drawable
                toggleFab();
            }
        });
    }

    //method to init recycler view
    private void initRecyclerView() {

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //create an array of numbers that will populate the recycler view
        String[] numbers = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17"};

        //set the recycler view adapter and pass arguments to the adapter to it
        recyclerView.setAdapter(new RecyclerViewAdapter(this, numbers));

        //method to hide or show fab depending on recycler view scrolling
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                //scroll down and hide the fab
                if (dy > 0 && fabMain.isShown()) {
                    hideFabMain(true);

                }

                //scroll up and show again
                if (dy < 0) {

                    hideFabMain(false);
                }
            }
        });
    }

    //method to hide or show fab
    private void hideFabMain(final boolean hide) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                //if true hide fab buttons
                if (hide) {

                    //if fab one and fab two are shown, i.e. if user clicker the fab main, restore plus avd to avoid strange behaviours
                    if (fabOne.isShown() && fabTwo.isShown()) {
                        fabMain.performClick();
                    }

                    //then hide all the fab buttons
                    fabMain.hide();
                    fabOne.hide();
                    fabTwo.hide();

                    //if false show the fab
                } else {
                    fabMain.show();
                }
            }
        });
    }

    //method to start animated vector drawable
    private void toggleFab() {

        //determine which drawable is shown (plus or minus)
        AnimatedVectorDrawableCompat currentDrawable = isShowingPlus ? plusToMinus : minusToPlus;

        //then set the right avd
        fabMain.setImageDrawable(currentDrawable);

        //start avd
        currentDrawable.start();

        //if fab is showing plus avd, on click show the other two fab buttons
        if (isShowingPlus) {

            //show the first fab button
            fabOne.show(new FloatingActionButton.OnVisibilityChangedListener() {
                @Override
                public void onShown(FloatingActionButton fab) {

                    //when the show animation is finished show the other one
                    fabTwo.show();

                    //update avd state, now the fab is showing the minus avd
                    isShowingPlus = !isShowingPlus;
                }

            });
        } else {

            //if fab is showing minus avd, hide the fab two
            fabTwo.hide(new FloatingActionButton.OnVisibilityChangedListener() {
                @Override
                public void onHidden(FloatingActionButton fab) {

                    //when the hide animation is finished hide the other one
                    fabOne.hide();
                    isShowingPlus = true;
                }

            });
        }

    }
}
