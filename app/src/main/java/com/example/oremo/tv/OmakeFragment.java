package com.example.oremo.tv;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.preference.Preference;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v4.app.Fragment;
import android.support.v4.print.PrintHelper;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class OmakeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private SpeechRecognizer mSpeechRecognizer;
    private SharedPreferences pre;
    String getData;


    public OmakeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment3.
     */
    // TODO: Rename and change types and number of parameters
    public static OmakeFragment newInstance(String param1, String param2) {
        OmakeFragment fragment = new OmakeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_omake, container, false);
        Button button = view.findViewById(R.id.record);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speech();
            }
        });

        Button button2 = view.findViewById(R.id.print);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                printWindow();
            }
        });
        pre = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        getData = pre.getString("speech", null);
        if(!TextUtils.isEmpty(getData)){
            ((TextView)view.findViewById(R.id.recordText)).setText(getData);
        }

        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_DOWN){
                    if(keyCode == KeyEvent.KEYCODE_VOLUME_UP){
                        getData = "";
                        saveText(getData);
                        ((TextView)v.findViewById(R.id.recordText)).setText(getData);

                    }
                }
                return this.onKey(v, keyCode, event);
            }
        });


        return view;
    }

    private void speech(){
        Log.d("aaa","開始");
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getContext().getPackageName());

        mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(getContext());
        mSpeechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {
                Toast.makeText(getActivity(), "入力します", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onBeginningOfSpeech() {

                Log.d("aaa","スタート");
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


                for (String s : recData) {
                    if(TextUtils.isEmpty(getData)){
                        getData = s;
                    }
                    else{
                        getData = getData + "\n" + s;
                    }
                }
                saveText(getData);
                ((TextView)getView().findViewById(R.id.recordText)).setText(getData);
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

    private void printWindow() {
        View view = getActivity().getWindow().getDecorView();
        Log.d("aaa",view.getWidth() + "," + view.getHeight());
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        PrintHelper printHelper = new PrintHelper(getActivity());
        printHelper.setColorMode(PrintHelper.COLOR_MODE_COLOR);
        printHelper.setScaleMode(PrintHelper.SCALE_MODE_FIT);
        printHelper.printBitmap("view", bitmap);
    }

    private void saveText(String st){
        SharedPreferences.Editor editor = pre.edit();
        editor.putString("speech", st);
        editor.commit();
    }


}
