package com.example.comercialesapp.ui.pedidos;

import android.util.Log;

import com.example.comercialesapp.BuildConfig;

import org.w3c.dom.Attr;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class PedidoXML {
    private Document documento;
    private File fichero = new File("/storage/emulated/legacy/Download/Articulos.xml");
    private ArrayList<Integer> imagenes = new ArrayList<>();
    private String id;
    private String descripcion;
    private float precio;
    private int cantidad;
    private int descuento;



    public PedidoXML(){

    }

    public PedidoXML(String id, String descripcion, float precio, int cantidad, int descuento, String ruta){
        this.id = id;
        this.descripcion = descripcion;
        this.precio = precio;
        this.cantidad = cantidad;
        this.descuento = descuento;
        this.fichero.renameTo(new File(ruta));
    }


    public void generarDOM() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            if (!this.fichero.exists()){
                DOMImplementation dom = builder.getDOMImplementation();
                documento = dom.createDocument(null, "articulos", null);
            } else {
                documento = builder.parse(this.fichero);
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    public void generarDocument(){

        Element articulo = documento.createElement("articulo");
        Attr atributo = documento.createAttribute("id");
        atributo.setValue(this.id);
        articulo.setAttributeNode(atributo);
        documento.getDocumentElement().appendChild(articulo);

        Element descripcion = documento.createElement("descripcion");
        descripcion.setTextContent(this.descripcion);
        articulo.appendChild(descripcion);

        Element precio = documento.createElement("precio");
        precio.setTextContent(String.valueOf(this.precio));
        articulo.appendChild(precio);

        Element cantidad = documento.createElement("cantidad");
        cantidad.setTextContent(String.valueOf(this.cantidad));
        articulo.appendChild(cantidad);

        Element descuento = documento.createElement("descuento");
        descuento.setTextContent(String.valueOf(this.descuento));
        articulo.appendChild(descuento);

    }

    public ArrayList<Pedido> leerPedido(){
        String articuloId;
        String articuloDescripcion;
        float precio;
        int stock;
        int descuento;
        String imagen;
        ArrayList<Pedido> xml = new ArrayList<>();

        NodeList partners = documento.getElementsByTagName("articulo");

        for (int i = 0; i < partners.getLength(); i++) {
            Node nodo = partners.item(i);
            if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                Element e = (Element) nodo;

                articuloId = e.getAttribute("id");
                articuloDescripcion = e.getElementsByTagName("descripcion").item(0).getTextContent();
                try{
                    precio = Float.parseFloat(e.getElementsByTagName("pr_vent").item(0).getTextContent());
                } catch (Exception ex){
                    precio = 0;
                }

                try{
                    Log.e("Stock", e.getElementsByTagName("stock").item(0).getTextContent());
                    stock = Integer.parseInt(e.getElementsByTagName("stock").item(0).getTextContent());
                } catch (Exception ex){
                    stock = 0;
                }

                try{
                    Log.e("Descuento", e.getElementsByTagName("descuento").item(0).getTextContent());
                    descuento = Integer.parseInt(e.getElementsByTagName("descuento").item(0).getTextContent());
                } catch (Exception ex){
                    Log.e("Salta", "try");
                    descuento = 0;
                }
                imagen = e.getElementsByTagName("foto").item(0).getTextContent();

                Pedido pedido = new Pedido(articuloId, articuloDescripcion, precio, imagen, descuento, 0, stock);
                xml.add(pedido);

            }
        }

        return xml;
    }

    public void generarXml(){

        try {
            TransformerFactory transFactory = TransformerFactory.newInstance();
            Transformer transformador = transFactory.newTransformer();
            Source source = new DOMSource(documento);
            Result resultado  = new StreamResult(this.fichero);
            transformador.setOutputProperty(OutputKeys.INDENT, "yes");
            transformador.transform(source, resultado);

            Log.e("ha creado", "ha creado");
        } catch (TransformerConfigurationException e) {
            Log.e("Ha saltado", "1");
            e.printStackTrace();
        } catch (TransformerException e) {
            Log.e("Ha saltado", "2");
            e.printStackTrace();
        }
    }
}
