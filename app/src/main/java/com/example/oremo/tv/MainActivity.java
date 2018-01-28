package com.example.oremo.tv;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.print.PrintHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private FragmentTransaction transaction;

    private SpeechRecognizer mSpeechRecognizer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Slide slide = new Slide();
                slide.setSlideEdge(Gravity.RIGHT);

                ;
                Fragment2 fragment2 = new Fragment2();
                fragment2.setEnterTransition(slide);
                FragmentTransaction transaction2 = getSupportFragmentManager().beginTransaction();
                transaction2.replace(R.id.frame, fragment2);
                transaction2.addToBackStack(null);
                transaction2.commit();
            }
        });



        Fragment1 fragment1 = new Fragment1();
        Slide slide = new Slide();
        slide.setSlideEdge(Gravity.LEFT);
        fragment1.setExitTransition(slide);
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frame, fragment1);
        transaction.addToBackStack(null);
        transaction.commit();

        mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        mSpeechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {
                Log.d("aaa","ggg");
            }

            @Override
            public void onBeginningOfSpeech() {

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
                    getData += s + ",";
                }
            }

            @Override
            public void onPartialResults(Bundle partialResults) {

            }

            @Override
            public void onEvent(int eventType, Bundle params) {

            }
        });
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


    private void speech(){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
                getPackageName());
    }
}
