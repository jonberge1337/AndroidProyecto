package com.example.comercialesapp.ui.pedidos;

import com.example.comercialesapp.BuildConfig;

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

public class PedidoXML {
    private Document documento;
    private File fichero = new File("/storage/emulated/legacy/Download");
    private ArrayList<Integer> imagenes = new ArrayList<>();


    public PedidoXML(){

    }


    public void generarDOM() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            if (!this.fichero.exists()){
                DOMImplementation dom = builder.getDOMImplementation();
                documento = dom.createDocument(null, "partners", null);
            } else {
                documento = builder.parse(this.fichero);
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
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
                    stock = Integer.parseInt(e.getElementsByTagName("stock").item(0).getTextContent());
                } catch (Exception ex){
                    stock = 0;
                }

                try{
                    descuento = Integer.parseInt(e.getElementsByTagName("descuento").item(0).getTextContent());
                } catch (Exception ex){
                    descuento = 0;
                }
                imagen = e.getElementsByTagName("foto").item(0).getTextContent();

                Pedido pedido = new Pedido(articuloId, articuloDescripcion, precio, imagen, descuento, 0, stock);
                xml.add(pedido);

            }
        }

        return xml;
    }

}
