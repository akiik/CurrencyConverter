/**  
  * @desc this class will accept the request, then processes it and sends it back to the client in JSON format.
  * @var List<FinancialDay> financialDays - keeps data alive: holds objects of loaded days
  * @var String[] availableCurrencies - holds the codes of available currencies we are working with.
  * @var String filesLocation - the path of the files where currency rates are located
  * @var Logger logger - just for logging
  * @author Andres Kiik andresk777@gmail.com 
  *
*/
package com.web.app.rest;

import com.web.app.data.FinancialDay;
import com.web.app.data.QueryResponse;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;



@Path("/currency")
public class CurrencyService {
    
  private static final List<FinancialDay> financialDays = new ArrayList<FinancialDay>();
  private static final String[] availableCurrencies = {"ALL","AMD","ARS","AZN","AUD","BGN","BND","BRL","CAD","CHF","CLP","CNY","COP","CZK","DDD","DKK","DOP","EUR","GBP","GEL","HKD","HNL","HRK","HUF","IDR","ILS","INR","ISK","JOM","JPY","KGS","KRW","KZT","LTL","LVL","MAD","MDL","MNT","MOP","MXN","MYR","NGN","NIO","NOK","NZD","PEN","PHP","PLN","PYG","RON","RSD","RUB","RUP","SEK","SGD","ZAR","THB","TJS","TRY","TZS","TWD","UAH","USD","UYU","VDO","VEB","VND","XOF","XXX"};
  private static final String filesLocation = "F:\\currency_rates\\";
  private static final Logger logger = Logger.getLogger(CurrencyService.class.getName());

  
/** 
  * @desc processes the request: 
  * checks if the query is valid, 
  * checks if the requested day is in memory (list)(if not, makes a new day object and saves it)
  * builds a new response object
  * @param String from - the currency where the rate is calculated from
  * @param String to - the currency the rate is calculated to
  * @param String date - the date of currency rates
  * @return QueryResponse - object that contains response data and will be parsed to JSON format
*/ 

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public QueryResponse getJSON(
    @QueryParam("from") String from,
    @QueryParam("to") String to,
    @QueryParam("date") String date) {
    /** 
        * @var long nanoStart  - saves start time in nanoseconds, needed to measure execution time of request 
        * @var Date dNow  - saves the date and time when request arrived
        * @var SimpleDateFormat ft - response date format
        * @var Date queryDate - query date in Date format
      */ 
        long nanoStart = System.nanoTime(); 
        Date dNow = new Date();
        SimpleDateFormat ft =  new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss:SSS");
        Date queryDate;
        
        //query parameters check        
        if ((from==null || from.isEmpty()) || 
             (to==null || to.isEmpty()) ||
             (date==null || date.isEmpty()))
        {   
            logger.log(Level.INFO, "Query parameters were incorrect (null or empty)");
            throw new WebApplicationException(400);
        }
        //check if requested currencies are in array
        if (!Arrays.asList(availableCurrencies).contains(from) ||
            !Arrays.asList(availableCurrencies).contains(to)   )
        {
            logger.log(Level.INFO, "Query parameters were incorrect (code(s) were not available)");
            throw new WebApplicationException(400);
        }
        //check if requested date is in correct format (yyyy-MM-dd)
        try{
            queryDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        }
        catch(ParseException ex) {
            logger.log(Level.INFO, "Query parameters were incorrect (date parameter was incorrect)");
            throw new WebApplicationException(400);
        }
        
        // next we will see if the day is already in memory (list)
                
        FinancialDay day;
        int index = -1;
        for (int i=0;i<financialDays.size();i++) {
               if (financialDays.get(i).getDate().equals(queryDate)) {
                   index = i;
                   break;
               }
           }
        /**
         * If the day is in memory, we use that day, 
         * otherwise load a new data and create a new day object 
         */
        if (index > -1){
            logger.log(Level.INFO, "Day was found");
            day = financialDays.get(index);
        }
        else {
            logger.log(Level.INFO, "Day was not found, loading a new day");
            day = new FinancialDay(queryDate,FinancialDay.loadNewDay(date,filesLocation));
            financialDays.add(day);
        }

        if (day.getCurrencies().isEmpty()){
            logger.log(Level.INFO, "Day (file) was found, but there was no data ");
            throw new WebApplicationException(204);
        }

        for (FinancialDay i : financialDays ){
            System.out.println(i.toString()+"HASH::"+i.hashCode());
        }
        
        //making a response object needs some data
          HashMap<String, String> debug = new LinkedHashMap<String,String>();
          HashMap<String, String> rate = new LinkedHashMap<String,String>();
          BigDecimal currencyRate = day.findCurrencyRate(from, to);
          
          long nanoEnd = System.nanoTime(); // saves the execution end time
          double elapsedTime = (double)Math.round((nanoEnd - nanoStart)/1000000.0)/1000; // execution time in seconds
          debug.put("requestReceived",ft.format(dNow));
          debug.put("requestTime",String.valueOf(elapsedTime)); 
          rate.put("rate",currencyRate.toString());

          QueryResponse response = new QueryResponse(debug,rate);

          return response;
  }
    /** 
    * @desc a simple method that returns available currency codes in JSON format.
    * @return String[] - currency codes
    */
    @Path("/currencies")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String[] getavailableCurrencies() {
        return availableCurrencies;
  }


} 
