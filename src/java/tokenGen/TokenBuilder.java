/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tokenGen;

import java.util.HashMap;
import javax.json.Json;
import javax.json.JsonObject;
;
/**
 * Class using 3'rd part software to build tailored Firebase security tokens
 * @author thor
 */
public class TokenBuilder {
    
    
    
    public static String buildToken(String uidValue, String arg1, String arg2, String arg3 ){
    
        
       TokenGenerator tg = new TokenGenerator("4Y6DIqj0QU7pnn4ws5xfvqUBJvhZC9tYNNHinp8L");
            HashMap authPayload = new HashMap<String, Object>();
            authPayload.put("uid", "1");
            authPayload.put("some", "arbitrary");
            authPayload.put("data", "here");
            
            return tg.createToken(authPayload);
            
        
    }
    
    
}
