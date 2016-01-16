/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transactionserver;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 *
 * @author ricardo
 */
public class Logger{

    private String fileName;
    private FileWriter fileWriter;

    public Logger(String dbName) {
        this.fileName = dbName + ".json";
    }

    public void log(Log logItem) {
        //ObjectMapper mapper = new ObjectMapper();
        //mapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            if(logItem.getStatus().toString().equals("INIT")){
                this.fileWriter = new FileWriter(fileName, false);
                fileWriter.write('[');
            }else{
                this.fileWriter = new FileWriter(fileName, true);
                fileWriter.write(',');
            }
            //mapper.writeValue(fileWriter, logItem);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Log> getLogItems(String id) {
        ArrayList<Log>logItems = null;

        try {
            byte[] encoded = Files.readAllBytes(Paths.get("errorlogs/" + id + ".json"));
            String jsonAsString = new String(encoded, Charset.defaultCharset());
            jsonAsString+="]";
            //<<Collection<CoordinatorLog> items = new ObjectMapper().readValue(jsonAsString, new TypeReference<Collection<CoordinatorLog>>() { });
            //logItems=new ArrayList<>(items);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return logItems;
    }

    public List<Log> getLogItems() {
        ArrayList<Log>logItems = null;

        try {
            byte[] encoded = Files.readAllBytes(Paths.get(fileName));
            String jsonAsString = new String(encoded, Charset.defaultCharset());
            jsonAsString+="]";
            //Collection<CoordinatorLog> items = new ObjectMapper().readValue(jsonAsString, new TypeReference<Collection<CoordinatorLog>>() { });
            //logItems=new ArrayList<>(items);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return logItems;
    }

}
