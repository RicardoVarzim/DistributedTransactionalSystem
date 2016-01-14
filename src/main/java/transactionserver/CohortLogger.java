/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transactionserver;

import java.util.List;

/**
 *
 * @author ricardo
 */
public class CohortLogger{
    private String fileName;
    //private FileWriter fileWriter;

    public CohortLogger(String dbName) {
        this.fileName = dbName + ".json";
    }

    public void log(CohortLog logItem) {
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.enable(SerializationFeature.INDENT_OUTPUT);
//        try {
//            if(logItem.getStatus().toString().equals("INIT")){
//                this.fileWriter = new FileWriter(fileName, false);
//                fileWriter.write('[');
//
//            }else {
//                this.fileWriter = new FileWriter(fileName, true);
//                fileWriter.write(',');
//            }
//            mapper.writeValue(fileWriter, logItem);
//            fileWriter.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public static List<CohortLog> getLogItems(String fileName) {
//        List<CohortLog> logItems = null;
//        try {
//            byte[] encoded = Files.readAllBytes(Paths.get(fileName));
//            String jsonAsString = new String(encoded, Charset.defaultCharset());
//            jsonAsString+="]";
//            Collection<CohortLog> items = new ObjectMapper().readValue(jsonAsString, new TypeReference<Collection<CohortLog>>() { });
//            logItems=new ArrayList<>(items);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return logItems;
        return null;
    }
}
