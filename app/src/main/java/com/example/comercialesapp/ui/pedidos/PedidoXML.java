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
import java.util.Date;

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
//    private File ficheroPedido = new File("/data/data/" + BuildConfig.APPLICATION_ID + "/pedidos.xml");
    private String nombreFicheroPedido;
    private File ficheroPedido;
    private ArrayList<Pedido> pedidos;
    private int pedidoID;
    private int comercialID;
    private int partnerID;
    private String fecha;



    public PedidoXML(){

    }

    public PedidoXML(ArrayList<Pedido> pedidos, int comercialID, int partnerID, String fecha, int pedidoID){
        this.pedidos = pedidos;
        this.comercialID = comercialID;
        this.partnerID = partnerID;
        this.fecha = fecha;
        this.pedidoID = pedidoID;
        this.nombreFicheroPedido = "/data/data/" + BuildConfig.APPLICATION_ID + "/pedidos_" + fecha + ".xml";
        this.ficheroPedido = new File(this.nombreFicheroPedido);
    }


    public void generarDOM() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            documento = builder.parse(this.fichero);
        } catch (ParserConfigurationException | SAXException | IOException e) { e.printStackTrace();
        }
    }

    public void generarDomPedido(){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            if (!ficheroPedido.exists()){
                Log.e("existe", "no");
                Log.e("nombre", this.nombreFicheroPedido);
                DOMImplementation dom = builder.getDOMImplementation();
                documento = dom.createDocument(null, "pedidos", null);
            } else {
                Log.e("existe", "si");
                Log.e("nombre", this.nombreFicheroPedido);
                documento = builder.parse(this.ficheroPedido);
            }
        } catch (IOException | ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }
    }

    public void generarDocument(){

        Element albaran = documento.createElement("albaran");
        Attr atributo = documento.createAttribute("id");
        atributo.setValue(String.valueOf(this.pedidoID));
        albaran.setAttributeNode(atributo);
        documento.getDocumentElement().appendChild(albaran);

        Element fechaPedido = documento.createElement("fechaAlbaran");
        fechaPedido.setTextContent(this.fecha);
        albaran.appendChild(fechaPedido);

        Element fechaEnvio = documento.createElement("fechaEnvio");
        albaran.appendChild(fechaEnvio);

        Element fechaEntrega = documento.createElement("fechaEntrega");
        albaran.appendChild(fechaEntrega);

        Element formapagoID = documento.createElement("formapagoID");
        albaran.appendChild(formapagoID);

        Element transportistaID = documento.createElement("transportistaID");
        albaran.appendChild(transportistaID);

        Element partnerID = documento.createElement("partnerID");
        partnerID.setTextContent(String.valueOf(this.partnerID));
        albaran.appendChild(partnerID);

        Element comercialID = documento.createElement("comercialID");
        comercialID.setTextContent(String.valueOf(this.comercialID));
        albaran.appendChild(comercialID);

        Element lineas = documento.createElement("lineas");
        albaran.appendChild(lineas);

        for (Pedido pedido : this.pedidos){
            Element linea = documento.createElement("linea");
            lineas.appendChild(linea);

            Element articuloID = documento.createElement("articuloID");
            articuloID.setTextContent(pedido.getArticuloID());
            linea.appendChild(articuloID);

            Element precio = documento.createElement("precio");
            precio.setTextContent(String.valueOf(pedido.getPrecio()));
            linea.appendChild(precio);

            Element cantidad = documento.createElement("cantidad");
            cantidad.setTextContent(String.valueOf(pedido.getCantidad()));
            linea.appendChild(cantidad);

            Element descuento = documento.createElement("descuento");
            descuento.setTextContent(String.valueOf(pedido.getDescuento()));
            linea.appendChild(descuento);
        }

    }

    public ArrayList<Pedido> leerCatalogo(){
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
            Result resultado  = new StreamResult(this.ficheroPedido);
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
