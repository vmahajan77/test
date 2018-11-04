import java.util.LinkedList;
import java.util.Map;
import java.util.LinkedHashMap;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONArray;


public class Test {
    
    public static void main(String[] args) {
        
        // Parse JSON file to desired data structure
            try{
                //JSONArray successArray = new JSONArray();
                LinkedList successList = new LinkedList();
                //JSONArray errorArray = new JSONArray();                
                LinkedList failList = new LinkedList();
                //JSONArray skipArray = new JSONArray();
                LinkedList skipList = new LinkedList(); 
                JSONParser parser = new JSONParser();
                
                //parse test JSON file using JSON parser
                Object obj = parser.parse(new FileReader("Internet1.json"));
                JSONObject jObject =  (JSONObject)(obj);
                //Extracted JSON array from JSON object and get all entries
                JSONArray lArray = (JSONArray) jObject.get("pages");       
        
        //Create linked hashmap to store parsed JSON data
                Map<String, LinkedList<String>> map = new LinkedHashMap<>();
                for (int i = 0; i<lArray.size(); i++){
                    JSONObject aObject = (JSONObject) lArray.get(i);
                    String a = (String) aObject.get("address");
                    JSONArray b = (JSONArray) aObject.get("links");
                    LinkedList<String> llist = new LinkedList<>();
                        for (int x = 0; x < b.size(); x++ ){
                          llist.add(b.get(x).toString());
                        }
                    //System.out.println("Linked List Content: " +llist);
                    map.put(a, llist);
                }
                
        // Write Algorithm for web crawl
                map.forEach((key, value) -> {
                    //System.out.println("\nKey: " + key);
                    for (int z = 0; z< value.size();z++){
                        //System.out.println(value.get(z));
                        if (successList.isEmpty()){
                            successList.addLast(key);
                            //successArray.add(key);
                        }
                        if ((value.get(z).contains(key)) && (!successList.contains(value.get(z)))){
                           successList.addLast(value.get(z));
                           skipList.addLast(value.get(z)); 
                           //successArray.add(value.get(z));
                           //skipArray.add(value.get(z));
                        }
                        else if ((map.containsKey(value.get(z))) && (!successList.contains(value.get(z)))){
                            //Add link to success list if address exists for link
                            successList.addLast(value.get(z));
                            //successArray.add(value.get(z));
                        }
                        else if ((successList.contains(value.get(z)) ) && !skipList.contains(value.get(z))) {
                            //System.out.println("Skipped :" + value.get(z));
                            skipList.addLast(value.get(z));
                            //skipArray.add(value.get(z));
                        }
                        else if (!map.containsKey(value.get(z)) && (!failList.contains(value.get(z)))){
                            //Add Link to fail list if it does not hava a associated Address 
                            failList.addLast(value.get(z));
                            //errorArray.add(value.get(z));
                        }
                    }
                }
                );
            
            // Print web crawl results
            System.out.println("Success: " + successList + "\n");
            System.out.println("Skipped: " + skipList + "\n");
            System.out.println("Error: " + failList + "\n");
            
                       
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            catch (ParseException e) {
                e.printStackTrace();
            }
    }
}
