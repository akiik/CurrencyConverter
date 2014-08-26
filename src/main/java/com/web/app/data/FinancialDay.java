/**  
  * @desc this class contains financial day data
  * FinancialDay object has:
  * @var Date date - the date of an object
  * @var List<Currency> currencies - list of currency rates 
  * @author Andres Kiik   andresk777@gmail.com
*/

package com.web.app.data;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 *
 * @author Andu
 */
public class FinancialDay {
    
    private Date date;
    private List<Currency> currencies;
    private static final Logger logger = Logger.getLogger(FinancialDay.class.getName());
    
    public FinancialDay(Date date, List<Currency> currencies){
        this.date=date;
        this.currencies=currencies;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
    public List<Currency> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<Currency> currencies) {
        this.currencies = currencies;
    }
    
     /** 
      * @desc loads the financial day currency rates from file 
      * @param String date - the date we are looking for
      * @param String filesLocation - the path where the currency rate files are located
      * @return List<Currency> - list of currency rates
    */   
    public static List<Currency> loadNewDay(String date,String filesLocation){
        
        String fileName= date+".txt"; 
        logger.log(Level.INFO, "Try to open the file "  + filesLocation+fileName);
        
        List<Currency> data = new ArrayList<Currency>();         
        try (BufferedReader br = new BufferedReader(new FileReader(filesLocation+fileName)))
        {
                String sCurrentLine;
                br.readLine();//we don't care about the first line
                while ((sCurrentLine = br.readLine()) != null) {
                        String[] str = sCurrentLine.split(",");
                        data.add(new Currency(str[0],str[1],new BigDecimal(str[3])));

                }
        } catch (IOException e) {
                logger.log(Level.INFO, "Error opening file: "  + fileName);
                throw new WebApplicationException(Response.Status.NOT_FOUND);
        } 
        return data;
    }
    
     /** 
      * @desc finds the currency rate from x currency to y currency
      * @param String from - x currecy
      * @param String to - y currency
      * @return BigDecimal - rate (comparison result)
    */
    public BigDecimal findCurrencyRate(String from, String to){
        BigDecimal rate;
        BigDecimal fromx = new BigDecimal("0");
        BigDecimal tox = new BigDecimal("0");
        for (Currency currency : currencies) {
            if (currency.getTarget().equals(from)) {
                fromx = currency.getRate();
            }
            if (currency.getTarget().equals(to)) {            
                tox = currency.getRate();
            }
        }
        try {
            rate = fromx.divide(tox, 4, RoundingMode.CEILING);
        }
        catch (ArithmeticException ex) {
            logger.log(Level.INFO, "Arithmetic exception occoured: "+ex);
            rate = new BigDecimal("0");
        }
        return rate;
        
    }
    
}
