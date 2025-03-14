package org.example.spring_recuperacion.repository;

import org.example.spring_recuperacion.dto.ProductoDTO;
import org.springframework.stereotype.Repository;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
@Repository
public class DAOFicherosXML {
    private final String archivoProductos = "src/main/resources/data/productos.xml";

    public List<ProductoDTO> leerFicheroXML() {
        List<ProductoDTO> productos = new ArrayList<>();
        try {
            File file = new File(archivoProductos);
            if (!file.exists()) return productos;

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();

            NodeList listaProductos = doc.getElementsByTagName("Producto");
            for (int i = 0; i < listaProductos.getLength(); i++) {
                Node nodo = listaProductos.item(i);
                if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                    Element elemento = (Element) nodo;
                    String idText = getTextFromElement(elemento, "ID");
                    String nombre = getTextFromElement(elemento, "Nombre");
                    String descripcion = getTextFromElement(elemento, "Descripcion");
                    String precioText = getTextFromElement(elemento, "Precio");
                    String stockText = getTextFromElement(elemento, "Stock");

                    if (idText != null && precioText != null && stockText != null) {
                        ProductoDTO producto = new ProductoDTO();
                                producto.setId(Integer.parseInt(idText));
                                producto.setNombre(nombre);
                                producto.setDescripcion(descripcion);
                                producto.setPrecio(Float.parseFloat(precioText));
                                producto.setStock(Integer.parseInt(stockText));
                                productos.add(producto);
                    } else {
                        System.err.println("Error: Uno de los valores es null en el XML");
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productos;
    }

    public void escribirFicheroXML(List<ProductoDTO> productos) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            Element rootElement = doc.createElement("Productos");
            doc.appendChild(rootElement);

            for (ProductoDTO prod : productos) {
                Element producto = doc.createElement("Producto");
                rootElement.appendChild(producto);

                Element id = doc.createElement("ID");
                id.appendChild(doc.createTextNode(String.valueOf(prod.getId())));
                producto.appendChild(id);

                Element nombre = doc.createElement("Nombre");
                nombre.appendChild(doc.createTextNode(prod.getNombre()));
                producto.appendChild(nombre);

                Element descripcion = doc.createElement("Descripcion");
                descripcion.appendChild(doc.createTextNode(prod.getDescripcion()));
                producto.appendChild(descripcion);

                Element precio = doc.createElement("Precio");
                precio.appendChild(doc.createTextNode(String.valueOf(prod.getPrecio())));
                producto.appendChild(precio);

                Element stock = doc.createElement("Stock");
                stock.appendChild(doc.createTextNode(String.valueOf(prod.getStock())));
                producto.appendChild(stock);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(archivoProductos));
            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private String getTextFromElement(Element element, String tagName) {
        NodeList nodeList = element.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            Node node = nodeList.item(0);
            return node.getTextContent().trim();
        }
        return null;
    }

}
