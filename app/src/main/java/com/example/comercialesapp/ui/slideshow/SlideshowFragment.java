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

        generarXml();

        return root;
    }

    public void generarXml(){
        ArchivoXML xmlPartner = new ArchivoXML("chargenetic", "Jon",  "Bergerandi", "Loidi",  "424115141L",
                 "Zarautz", "trinidade",  "cebanc", "c", "4");
        xmlPartner.generarDOM();
        xmlPartner.generarDocument();
        xmlPartner.generarXml();
    }
}