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
import android.text.TextUtils;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private FragmentTransaction transaction;

    private Slide leftSlide;
    private Slide rightSlide;

    private int page = 0;

    private FragmentEnum[] fragments;
    private SpeechRecognizer mSpeechRecognizer;

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

            for(int i=0;i <fragments.length ;i++){
                if(page == i){
                    Fragment fragment = fragments[i].getFragment();
                    fragment.setEnterTransition(rightSlide);
                    fragment.setExitTransition(leftSlide);
                    FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
                    transaction2.replace(R.id.frame, fragment);
                    transaction2.addToBackStack(null);
                    transaction2.commit();
                }
            }

        }
    }


    private void toFirstSlide(){
        Fragment fragment = fragments[0].getFragment();
        fragment.setEnterTransition(leftSlide);
        fragment.setExitTransition(leftSlide);
        FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
        transaction2.replace(R.id.frame, fragment);
        transaction2.addToBackStack(null);
        transaction2.commit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.d("keycode", keyCode+"");
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(page > 0) {
                page--;
            }
            else if (page == 0){
                finish();
            }
        }
        else if(keyCode == KeyEvent.KEYCODE_MEDIA_NEXT){
            nextSlide();
        }
        return super.onKeyDown(keyCode,event);
    }

    private void speech() {
        Log.d("aaa", "開始");
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());

        mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        mSpeechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {
                Log.d("aaa", "ggg");
            }

            @Override
            public void onBeginningOfSpeech() {

                Log.d("aaa", "スタート");
            }

            @Override
            public void onRmsChanged(float rmsdB) {

            }

            @Override
            public void onBufferReceived(byte[] buffer) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int error) {

            }

            @Override
            public void onResults(Bundle results) {
                ArrayList<String> recData = results
                        .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

                String getData = new String();

                for (String s : recData) {
                    if (TextUtils.isEmpty(getData)) {
                        getData = s;
                    } else {
                        getData = getData + "\n" + s;
                    }
                }

                for (FragmentEnum fragmentEnum : fragments) {
                    if (fragmentEnum.getName().equals(getData)) {
                        FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
                        transaction2.replace(R.id.frame, fragmentEnum.getFragment());
                        transaction2.addToBackStack(fragmentEnum.getName());
                        transaction2.commit();
                    }


                }
            }

            @Override
            public void onPartialResults(Bundle partialResults) {

            }

            @Override
            public void onEvent(int eventType, Bundle params) {

            }
        });

        mSpeechRecognizer.startListening(intent);
    }

}
