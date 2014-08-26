  
/** 
  * @desc this class contains response data and is used to generate JSON format response
  * @var HashMap<String,String> debugInfo - info for debugging(requestDate and responseTime)
  * @var HashMap<String,String> currencyRate - requested currency rate   

*/ 

package com.web.app.data;

import java.util.HashMap;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Andu
 */
@XmlRootElement
public class QueryResponse {
    private HashMap<String,String> debugInfo;
    private HashMap<String,String> currencyRate;

    public QueryResponse(HashMap<String, String> debugInfo, HashMap<String, String> currencyRate) {
        this.debugInfo = debugInfo;
        this.currencyRate = currencyRate;
    }

    public HashMap<String, String> getDebugInfo() {
        return debugInfo;
    }

    public void setDebugInfo(HashMap<String, String> debugInfo) {
        this.debugInfo = debugInfo;
    }

    public HashMap<String, String> getCurrencyRate() {
        return currencyRate;
    }

    public void setCurrencyRate(HashMap<String, String> currencyRate) {
        this.currencyRate = currencyRate;
    }
    
    
}
