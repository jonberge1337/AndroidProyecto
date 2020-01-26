package com.example.comercialesapp.ui.partners;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.comercialesapp.BuildConfig;
import com.example.comercialesapp.TablaSQL;

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

public class PartnersXML {
    private String empresa;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String dni;
    private String ciudad;
    private String direccion1;
    private String direccion2;
    private String formapagoID;
    private String comercial;
    private String correo;
    private String telefono;
    private Document documento = null;
    private File fichero = new File("/data/data/" + BuildConfig.APPLICATION_ID + "/partner.xml");

    public PartnersXML(String empresa, String nombre, String apellido1, String apellido2, String dni,
                       String ciudad, String direccion1, String direccion2,
                       String formapagoID, String correo, String telefono, String comercial) {
        this.empresa = empresa;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.dni = dni;
        this.ciudad = ciudad;
        this.direccion1 = direccion1;
        this.direccion2 = direccion2;
        this.formapagoID = formapagoID;
        this.comercial = comercial;
        this.telefono = telefono;
        this.correo = correo;
    }

    public PartnersXML(){

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

    public void generarDocument(){

            Element partner = documento.createElement("partner");
            documento.getDocumentElement().appendChild(partner);

            Element empresa = documento.createElement("empresa");
            empresa.setTextContent(this.empresa);
            partner.appendChild(empresa);

            Element nombre = documento.createElement("nombre");
            nombre.setTextContent(this.nombre);
            partner.appendChild(nombre);

            Element apellido1 = documento.createElement("apellido1");
            apellido1.setTextContent(this.apellido1);
            partner.appendChild(apellido1);

            Element apellido2 = documento.createElement("apellido2");
            apellido2.setTextContent(this.apellido2);
            partner.appendChild(apellido2);

            Element dni = documento.createElement("dni");
            dni.setTextContent(this.dni);
            partner.appendChild(dni);

            Element ciudad = documento.createElement("ciudad");
            ciudad.setTextContent(this.ciudad);
            partner.appendChild(ciudad);

            Element direccion1 = documento.createElement("direccion1");
            direccion1.setTextContent(this.direccion1);
            partner.appendChild(direccion1);

            Element direccion2 = documento.createElement("direccion2");
            direccion2.setTextContent(this.direccion2);
            partner.appendChild(direccion2);

            Element formapago = documento.createElement("formapagoID");
            formapago.setTextContent(this.formapagoID);
            partner.appendChild(formapago);

            Element correo = documento.createElement("correo");
            correo.setTextContent(this.correo);
            partner.appendChild(correo);

            Element telefono = documento.createElement("telefono");
            telefono.setTextContent(this.telefono);
            partner.appendChild(telefono);

            Element comercial = documento.createElement("comercial");
            comercial.setTextContent(this.comercial);
            partner.appendChild(comercial);


    }

    public ArrayList<Partner> leerPartner(){
        String nombre;
        String apellido1;
        String apellido2;
        String correo;
        String telefono;
        ArrayList<Partner> xml = new ArrayList<>();

//        TablaSQL tabla = new TablaSQL(this, "DBUsuarios", null, 1);
//        final SQLiteDatabase db = tabla.getWritableDatabase();

        NodeList partners = documento.getElementsByTagName("partner");

        for (int i = 0; i < partners.getLength(); i++) {
            Node nodo = partners.item(i);
            if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                Element e = (Element) nodo;

                nombre = e.getElementsByTagName("nombre").item(0).getTextContent();
                apellido1 = e.getElementsByTagName("apellido1").item(0).getTextContent();
                apellido2 = e.getElementsByTagName("apellido2").item(0).getTextContent();
                correo = e.getElementsByTagName("correo").item(0).getTextContent();
                telefono = e.getElementsByTagName("telefono").item(0).getTextContent();

                Partner partner = new Partner(nombre + " " + apellido1 + " " + apellido2, correo, telefono);
                xml.add(partner);

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
