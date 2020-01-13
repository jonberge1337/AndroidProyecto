package com.example.comercialesapp.ui.agenda;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.comercialesapp.R;

import java.util.Calendar;

public class ShareFragment extends Fragment {

    private ShareViewModel shareViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        shareViewModel =
//                ViewModelProviders.of(this).get(ShareViewModel.class);
        View root = inflater.inflate(R.layout.fragment_agenda, container, false);
//        final TextView textView = root.findViewById(R.id.text_share);
//        shareViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        crearCalendario(root);
        return root;
    }

    public void crearCalendario(View vista){

        ImageButton agenda = vista.findViewById(R.id.btnAgenda);


        agenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendarEvent = Calendar.getInstance();
                Intent intent = new Intent(Intent.ACTION_EDIT);
                intent.setType("vnd.android.cursor.item/event");
                intent.putExtra("Empieza", calendarEvent.getTimeInMillis());
                intent.putExtra("Acaba", calendarEvent.getTimeInMillis() + 60 * 60 * 1000);
                intent.putExtra("titulo", "Sample Event");
                intent.putExtra("todo el dia", true);
                intent.putExtra("regla", "FREQ=YEARLY");
                startActivity(intent);

            }
        });
    }
}