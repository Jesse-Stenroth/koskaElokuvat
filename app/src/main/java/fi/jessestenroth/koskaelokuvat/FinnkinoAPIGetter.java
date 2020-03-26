package fi.jessestenroth.koskaelokuvat;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class FinnkinoAPIGetter {

    public List<area> getAreas(){
        try{
            List<area> lista = new LinkedList<>();
            URL url = new URL("https://www.finnkino.fi/xml/TheatreAreas/");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new InputSource(url.openStream()));
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("TheatreArea");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                Element fstElmnt = (Element) node;
                NodeList idList = fstElmnt.getElementsByTagName("ID");
                Element idElement = (Element) idList.item(0);
                idList = idElement.getChildNodes();
                String id = ((Node) idList.item(0)).getNodeValue();
                NodeList nameList = fstElmnt.getElementsByTagName("website");
                Element nameElement = (Element) nameList.item(0);
                nameList = nameElement.getChildNodes();
                String name = ((Node) nameList.item(0)).getNodeValue();

                lista.add(new area(id, name));
            }
            return lista;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
