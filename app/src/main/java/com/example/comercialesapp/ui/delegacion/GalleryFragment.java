package com.example.comercialesapp.ui.delegacion;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.comercialesapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

public class GalleryFragment extends Fragment implements OnMapReadyCallback {

    private GalleryViewModel galleryViewModel;
    private GoogleMap mapa;
    private TextView correo;
    private TextView telefono;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_delegacion, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        correo = root.findViewById(R.id.lblCorreoDelegacion);
        telefono = root.findViewById(R.id.lblTelefonoDelegacion);

        correo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                activarCorreo();

            }
        });

        telefono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                activarTelefono();

            }
        });

        return root;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mapa = googleMap;
        mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(43.3045627, -2.0191253), 15.0f));

    }

    public void activarCorreo() {

        Intent intent = new Intent(Intent.ACTION_SEND);
        //intent.setType("text/xml");
        intent.putExtra(Intent.EXTRA_EMAIL, "adchargenetic@gmail.com");
        intent.putExtra(Intent.EXTRA_SUBJECT, "");
        intent.putExtra(Intent.EXTRA_STREAM, "");

        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void activarTelefono() {

/*
        if (ContextCompat.checkSelfPermission(getActivity(),
                android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.CALL_PHONE}, REQUEST_CALL);
        } else {
*/
        String dial = "tel:" + "943 123456789";
        startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
//        }

    }

/*    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                activarTelefono();
            } else {
                Toast.makeText(getActivity(), "Permiso Denegado", Toast.LENGTH_SHORT).show();
            }
        }
    }*/

}