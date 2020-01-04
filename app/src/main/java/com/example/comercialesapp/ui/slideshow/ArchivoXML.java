package com.example.comercialesapp.ui.slideshow;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class ArchivoXML {
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

    public ArchivoXML(String empresa, String nombre, String apellido1, String apellido2, String dni,
                      String ciudad, String direccion1, String direccion2,
                      String formapagoID, String comercial) {
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
    }

    public boolean existeFichero(){
        boolean existe = false;

        return existe;
    }

    public void generarXMl(){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            DOMImplementation dom = builder.getDOMImplementation();
            Document documento = dom.createDocument(null, "partners", null);

            Element partner = documento.createElement("partner");
            documento.appendChild(partner);

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

            Element comercial = documento.createElement("comercial");
            comercial.setTextContent(this.comercial);
            comercial.appendChild(comercial);

            TransformerFactory transFactory = TransformerFactory.newInstance();
            Transformer transformador = transFactory.newTransformer();

            Source source = new DOMSource(documento);
            Result resultado  = new StreamResult(new File("/home/jb/Documentos/android.xml"));

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }
    }

}
