 
$(document).ready(function(){
    $( "#datepicker" ).datepicker({ dateFormat: 'yy-mm-dd' });
    //load currency codes from server and generate <select> options
    var getCurrencyCodes = function(){
        $.getJSON("/rest/currency/currencies")
            .done(function( json ) {
                $.each(json, function(i, value){
                     $('#from').append($('<option>').text(value).attr('value', value));
                     $('#to').append($('<option>').text(value).attr('value', value));
                });
            })
            .fail(function( xhr, textStatus, error ) {
                var err = xhr.status + " " + error; 
                $( "#rate" ).text( "Valuutakoodide laadimisel tekkis viga: "+err ).show();
            });
    };
    getCurrencyCodes();

  $( "form" ).on( "submit", function( event ) {
    var str = $( "form" ).serialize();
    event.preventDefault();
    $('#debugInfo').text("");
    $('#rate').text("");
    //load currency rate from server in JSON format
    var getCurrencyRate = function(){
        $.getJSON("/rest/currency?"+str)
            .done(function( json ) {
                $('#rate').text("Kurss on: "+json.currencyRate.rate); 
                $('#debugInfo').text("Server sai päringu "+ json.debugInfo.requestReceived);
                $('#debugInfo').append(" ja töötles seda "+json.debugInfo.requestTime+" sekundit");
            })
            .fail(function( xhr, textStatus, error ) {
                var err = xhr.status + " " + error;
                $( "#rate" ).text( "Valuutakursi arvutamisel tekkis viga: "+err ).show();
                 $('#debugInfo').text("");
            });
            
    };
    getCurrencyRate();
  });
});


