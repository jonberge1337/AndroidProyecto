package com.example.comercialesapp.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.comercialesapp.R;

public class Partners extends Fragment {

    private SlideshowViewModel slideshowViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_partners, container, false);

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