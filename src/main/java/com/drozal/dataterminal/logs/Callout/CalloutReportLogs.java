package com.drozal.dataterminal.logs.Callout;

import com.drozal.dataterminal.util.stringUtil;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import jakarta.xml.bind.annotation.XmlRootElement;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class CalloutReportLogs {
    private List<CalloutLogEntry> logs;

    public CalloutReportLogs() {
    }

    public static List<CalloutLogEntry> extractLogEntries(String filePath) {
        List<CalloutLogEntry> logEntries = new ArrayList<>();

        try {
            File file = new File(filePath);
            if (!file.exists()) {
                return logEntries; // Return an empty list if the file doesn't exist
            }

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();

            NodeList logsList = doc.getElementsByTagName("logs");

            for (int i = 0; i < logsList.getLength(); i++) {
                Node logsNode = logsList.item(i);
                if (logsNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element logsElement = (Element) logsNode;
                    CalloutLogEntry logEntry = new CalloutLogEntry();
                    logEntry.CalloutNumber = getTagValue(logsElement, "CalloutNumber");
                    logEntry.Date = getTagValue(logsElement, "Date");
                    logEntry.Time = getTagValue(logsElement, "Time");
                    logEntry.ResponeType = getTagValue(logsElement, "ResponeType");
                    logEntry.ResponseGrade = getTagValue(logsElement, "ResponseGrade");
                    logEntry.Address = getTagValue(logsElement, "Address");
                    logEntry.County = getTagValue(logsElement, "County");
                    logEntry.Area = getTagValue(logsElement, "Area");
                    logEntry.Rank = getTagValue(logsElement, "Rank");
                    logEntry.Name = getTagValue(logsElement, "Name");
                    logEntry.Number = getTagValue(logsElement, "Number");
                    logEntry.Agency = getTagValue(logsElement, "Agency");
                    logEntry.Division = getTagValue(logsElement, "Division");
                    logEntry.NotesTextArea = getTagValue(logsElement, "NotesTextArea");
                    logEntries.add(logEntry);
                }
            }
        } catch (Exception e) {
            System.err.println("Error extracting callout log entries: " + e.getMessage());
        }

        return logEntries;
    }

    public static String getTagValue(Element element, String tagName) {
        NodeList nodeList = element.getElementsByTagName(tagName);
        if (nodeList != null && nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        }
        return "";
    }

    public static List<CalloutLogEntry> loadLogsFromXML() {
        try {
            Path filePath = Paths.get(stringUtil.calloutLogURL);
            if (!Files.exists(filePath)) {
                return new ArrayList<>(); // Return an empty list if file doesn't exist
            }

            JAXBContext jaxbContext = JAXBContext.newInstance(CalloutReportLogs.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            CalloutReportLogs logList = (CalloutReportLogs) unmarshaller.unmarshal(filePath.toFile());
            return logList.getLogs();
        } catch (JAXBException e) {
            e.printStackTrace();
            return new ArrayList<>(); // Return an empty list if loading fails
        }
    }

    public static void saveLogsToXML(List<CalloutLogEntry> logs) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(CalloutReportLogs.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            CalloutReportLogs logList = new CalloutReportLogs();
            logList.setLogs(logs);

            // Use try-with-resources to ensure FileOutputStream is closed properly
            try (FileOutputStream fos = new FileOutputStream(stringUtil.calloutLogURL)) {
                marshaller.marshal(logList, fos);
            }
        } catch (JAXBException | IOException e) {
            e.printStackTrace();
        }
    }

    public List<CalloutLogEntry> getLogs() {
        return logs;
    }

    public void setLogs(List<CalloutLogEntry> logs) {
        this.logs = logs;
    }
}