package com.example.comercialesapp.ui.slideshow;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.comercialesapp.R;

import java.io.File;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);

        Resources res = getResources();
        TabHost tabs = root.findViewById(R.id.TabHost);
        tabs.setup();

        TabHost.TabSpec spec = tabs.newTabSpec("mitab1");
        spec.setContent(R.id.ACTUALES);
        spec.setIndicator("TAB1", res.getDrawable(android.R.drawable.ic_btn_speak_now));
        tabs.addTab(spec);

        spec = tabs.newTabSpec("mitab2");
        spec.setContent(R.id.NUEVO);
        spec.setIndicator("TAB2", res.getDrawable(android.R.drawable.ic_dialog_map));
        tabs.addTab(spec);


        generarXml();

        return root;
    }

    public void generarXml(){
        ArchivoXML xmlPartner = new ArchivoXML("chargenetic", "Jo",  "Bergerandi", "Loidi",  "424115141L",
                 "Zarautz", "trinidade",  "cebanc", "c", "4");
        xmlPartner.generarDOM();
        xmlPartner.generarDocument();
        xmlPartner.generarXml();
    }
}