

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Homepage</title>
        <link rel="stylesheet" type="text/css" href="css/style.css">
        <script src="//code.jquery.com/jquery-1.10.2.js"></script>
        <script src="scripts/javascript.js"></script>
        <link rel="stylesheet" href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css">
        <script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
    </head>
    <body>
        <div id="container">
            <div id="header">Valuutakursid</div>
            <div id="form">
                <form>
                <div><select id="from" name="from" required></select></div>
                <div>&nbsp;&gt;&gt;&nbsp;</div>
                <div><select id="to" name="to" required></select></div>
                <div>Päev: <input type="text" name="date" id="datepicker" required></div>
                <div><input id="button" type="submit" value="Leia"></div>
                <!--<div>Baasvaluuta on hetkel EUR, <a href="/currency/changedefault">muuda</a></div>-->
                
                </form>
            </div>
       
      <br>

      <div id="rate"></div>
      <div id="debugInfo"></div>
      </div>
    </body>
</html>
