package com.example.eventmanager.model;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import javafx.stage.FileChooser;

import java.io.File;

public class FileIOXML<T> implements FileIO<T>
{

    @Override
    public void writeToFile(T events, File file) throws Exception {
        JAXBContext context = JAXBContext.newInstance(Events.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(events, file);
    }

    @Override
    public void writeToFile(T events) throws Exception {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Wybierz XML plik do importu");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Plik XML","*.xml","*.XML"));
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Wszystkie pliki","*.*"));
        File file = chooser.showSaveDialog(null);
        if(file.canWrite() || (!file.exists() && file.createNewFile()))
            writeToFile(events, file);
    }

    @Override
    public T readFromFile(File file) throws Exception{
        JAXBContext context = JAXBContext.newInstance(Events.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (T) unmarshaller.unmarshal(file);
    }

    @Override
    public T readFromFile() throws Exception {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Wybierz XML plik do importu");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Plik XML","*.XML","*.xml"));
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Wszystkie pliki","*.*"));
        File file = chooser.showOpenDialog(null);
        if(file.canRead())
            return readFromFile(file);
        return null;
    }
}
