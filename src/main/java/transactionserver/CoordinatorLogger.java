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
class CoordinatorLogger {

    private String fileName;
    //private FileWriter fileWriter;

    public CoordinatorLogger(String dbName) {
        this.fileName = dbName + ".json";
    }

    public void log(CoordinatorLog logItem) {
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.enable(SerializationFeature.INDENT_OUTPUT);
//        try {
//            if(logItem.getStatus().toString().equals("INIT")){
//                this.fileWriter = new FileWriter(fileName, false);
//                fileWriter.write('[');
//            }else{
//                this.fileWriter = new FileWriter(fileName, true);
//                fileWriter.write(',');
//            }
//            mapper.writeValue(fileWriter, logItem);
//            fileWriter.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public List<CoordinatorLog> getLogItems(String id) {
//        ArrayList<CoordinatorLog>logItems = null;
//
//        try {
//            byte[] encoded = Files.readAllBytes(Paths.get("errorlogs/" + id + ".json"));
//            String jsonAsString = new String(encoded, Charset.defaultCharset());
//            jsonAsString+="]";
//            Collection<CoordinatorLog> items = new ObjectMapper().readValue(jsonAsString, new TypeReference<Collection<CoordinatorLog>>() { });
//            logItems=new ArrayList<>(items);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return logItems;
        return null;
    }

    public List<CoordinatorLog> getLogItems() {
//        ArrayList<CoordinatorLog>logItems = null;
//
//        try {
//            byte[] encoded = Files.readAllBytes(Paths.get(fileName));
//            String jsonAsString = new String(encoded, Charset.defaultCharset());
//            jsonAsString+="]";
//            Collection<CoordinatorLog> items = new ObjectMapper().readValue(jsonAsString, new TypeReference<Collection<CoordinatorLog>>() { });
//            logItems=new ArrayList<>(items);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return logItems;º
        return null;
    }

    public void errorLog(Long id){
//        try{
//            Files.createDirectory(Paths.get("errorlogs"));
//        } catch(IOException e){
//        }
//        Path from = Paths.get("Coordinatorlog.json");
//        Path to = Paths.get("errorlogs/" + id + ".json");
//        try {
//            Files.copy(from, to);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
    
}
