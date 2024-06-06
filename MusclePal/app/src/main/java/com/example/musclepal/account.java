package com.example.musclepal;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;


public class account extends Fragment {


    public account() {
        // Required empty public constructor

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_account, container, false);
        Button button = (Button) view.findViewById(R.id.gymfind);
        button.setOnClickListener(new View.OnClickListener() {//button listener on for gym finder
            @Override
            public void onClick(View view) {//leads to google maps and adds query
                Uri gmmIntentUri = Uri.parse("geo:0,0?q=gym near me");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);

            }
        });
        Button userGuidebutton = (Button) view.findViewById(R.id.userGuide);
        userGuidebutton.setOnClickListener(new View.OnClickListener() {//execute show dialog for user guide
            @Override
            public void onClick(View view) {
                show_dialog();

            }
        });

        Button logout = (Button) view.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {//logout and go to login activity
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), MainActivity.class);
                startActivity(i);

            }
        });



        return view;
    }
    public void show_dialog() {//code for executing user guide webview
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        WebView webView = new WebView(getActivity());
        WebSettings webSettings = webView.getSettings();
        webSettings.setBuiltInZoomControls(true);
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/login.html");
        webView.setWebViewClient(new WebViewClient(){
            public boolean shouldoverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        alert.setView(webView);
        alert.setNegativeButton("Close", new DialogInterface.OnClickListener() {//close button to get out of webview
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alert.show();
    }
}