/**  
  * @desc this class will hold currency data 
  * Every currency object has:
  * base - name of the currency base
  * target - basically the currency name
  * rate - currency rate according to base
  * @author Andres Kiik   andresk777@gmail.com
*/

package com.web.app.data;

import java.math.BigDecimal;


public class Currency {
    private String base;
    private String target;
    private BigDecimal rate;
    public Currency(String base, String target, BigDecimal rate){
        this.base=base;
        this.target=target;
        this.rate=rate;
    }

    public String getTarget() {
        return target;
    }

    public String getBase() {
        return base;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }
    
    
 
    
    
    
    
    
 
    
}
