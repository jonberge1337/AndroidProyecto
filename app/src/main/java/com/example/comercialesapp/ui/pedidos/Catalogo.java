package com.example.comercialesapp.ui.pedidos;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.comercialesapp.R;

import java.util.ArrayList;
import java.util.Objects;

public class Catalogo extends Fragment implements View.OnClickListener {

    private ListView listaPedidos;
    private View vista;
    private ImageButton generarPedido;
    private ArrayList<String> pedidoGenerado = new ArrayList<>();
    private ArrayList<Float> precios = new ArrayList<>();
    private ArrayList<String> idArticulos = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        int imagenes[];
        View root = inflater.inflate(R.layout.fragment_articulo, container, false);

        this.vista = root;
        final ArrayList<Pedido> pedidos;

        PedidoXML xml  = new PedidoXML();
        xml.generarDOM();
        pedidos = xml.leerPedido();
        imagenes = new int[pedidos.size()];

        Resources resources = getContext().getResources();

        for (int i = 0; i < pedidos.size(); i++){
            int resourceId = resources.getIdentifier(pedidos.get(i).getImagen(), "drawable",
                    getContext().getPackageName());
            imagenes[i] = resourceId;
        }

        AdaptadorPedido adaptador = new AdaptadorPedido(getActivity(), pedidos, imagenes);
        listaPedidos = root.findViewById(R.id.lstArticulos);
        listaPedidos.setAdapter(adaptador);
        listaPedidos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                EditText cantidad = view.findViewById(R.id.txtCantidad);
                TextView articuloID = view.findViewById(R.id.articuloID);
                int cuantia = Integer.parseInt(cantidad.getText().toString());
                boolean existeArticulo = false;
                int posicionArray = 0;
                for (String i : idArticulos){
                    if (i.equalsIgnoreCase(articuloID.getText().toString())){
                        existeArticulo = true;
                        break;
                    }
                    posicionArray++;
                }
                if (cuantia > 0){
                    TextView articulo = view.findViewById(R.id.txtArticuloCatalogo);
                    TextView precio = view.findViewById(R.id.txtPrecio);
                    TextView descuento = view.findViewById(R.id.txtDescuento);
                    int desc = Integer.parseInt(descuento.getText().toString());
                    int cant = Integer.parseInt(cantidad.getText().toString());
                    float pre = Float.parseFloat(precio.getText().toString());
                    if (!existeArticulo){
                        idArticulos.add(articuloID.getText().toString());
                        pedidoGenerado.add(articulo.getText().toString() + " " + cantidad.getText().toString() + " " + precio.getText().toString());
                        try {
                            precios.add(pre * cant * (1 - desc / 100));
                        } catch (Exception ex){
                            precios.add(pre * cant);
                        }
                    } else {
                        pedidoGenerado.set(posicionArray, articulo.getText().toString() + " " + cantidad.getText().toString() + " " + precio.getText().toString());
                        try {
                            precios.set(posicionArray, pre * cant * (1 - desc / 100));
                        } catch (Exception ex){
                            precios.set(posicionArray, pre * cant);
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        generarPedido = root.findViewById(R.id.btnPedidoNuevo);
        generarPedido.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View v) {

        PedidoXML pedido = new PedidoXML();
        pedido.generarDOM();
        pedido.generarDocument();
        pedido.generarXml();

        Fragment newFragment = new ResumenPedido();
        FragmentTransaction transaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();

// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack if needed
        transaction.replace(R.id.layoutCatalogoID, newFragment);
        transaction.addToBackStack(null);

// Commit the transaction
        transaction.commit();
        LinearLayout layout = this.vista.findViewById(R.id.layoutCatalogoID);
        layout.setVisibility(View.GONE);
        Fragment fragment = new Fragment();
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("pedidoGenerado", pedidoGenerado);
        float total = 0;
        for (float i : precios){
            total += i;
        }
        bundle.putFloat("precios", total);
        bundle.putStringArrayList("idArticulos", idArticulos);
        fragment.setArguments(bundle);
    }
}