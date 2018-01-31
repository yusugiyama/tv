package com.example.oremo.tv;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.print.PrintHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private FragmentTransaction transaction;

    private Slide leftSlide;
    private Slide rightSlide;

    private int page = 1;

    private FragmentEnum[] fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextSlide();
            }
        });



        fragments = FragmentEnum.values();

        FragmentEnum.hyoushi.getFragment();
        Fragment1 fragment1 = new Fragment1();
        leftSlide = new Slide();
        leftSlide.setSlideEdge(Gravity.LEFT);
        rightSlide = new Slide();
        rightSlide.setSlideEdge(Gravity.RIGHT);
        //fragment1.setExitTransition(leftSlide);
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frame, fragments[0].getFragment());
        //transaction.addToBackStack(null);
        transaction.commit();


    }

    private void printWindow() {
        View view = this.getWindow().getDecorView();
        Log.d("aaa",view.getWidth() + "," + view.getHeight());
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        /*PrintHelper printHelper = new PrintHelper(this);
        printHelper.setColorMode(PrintHelper.COLOR_MODE_COLOR);
        printHelper.setScaleMode(PrintHelper.SCALE_MODE_FIT);
        printHelper.printBitmap("view", bitmap);*/
    }

    private void nextSlide(){
        if(page < fragments.length){
            page++;
            switch (page){
                case 2: Fragment2 fragment2 = new Fragment2();
                    fragment2.setEnterTransition(rightSlide);
                    fragment2.setExitTransition(leftSlide);

                    FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
                    transaction2.replace(R.id.frame, fragment2);
                    transaction2.addToBackStack(null);
                    transaction2.commit();
                    break;
                case 3: Fragment3 fragment3 = new Fragment3();
                    fragment3.setEnterTransition(rightSlide);
                    fragment3.setExitTransition(leftSlide);

                    FragmentTransaction transaction3 = getSupportFragmentManager().beginTransaction();
                    transaction3.replace(R.id.frame, fragment3);
                    transaction3.addToBackStack(null);
                    transaction3.commit();
                    break;
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.d("keycode", keyCode+"");
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(page > 1) {
                page--;
            }
            else if (page == 1){
                finish();
            }
        }
        else if(keyCode == KeyEvent.KEYCODE_MEDIA_NEXT){
            nextSlide();
        }
        return super.onKeyDown(keyCode,event);
    }

}
