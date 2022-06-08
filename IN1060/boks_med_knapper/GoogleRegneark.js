function doGet(e){
    Logger.log("--- doGet ---");
   
   var vaxType = "", vaxNumber = 0;
   
    try {
      
      // lagrer verdiene fra ESP32-requesten
      vaxType = e.parameters.vaxType;
      vaxNumber = e.parameters.vaxNumber;
   
      // save the data to spreadsheet
      saveData(vaxType, vaxNumber);
   
      return ContentService.createTextOutput("Wrote:\n  vaxType: " + vaxType + "\n  vaxNumber: " + vaxNumber);
   
    } catch(error) { 
      Logger.log(error);    
      return ContentService.createTextOutput("error occured...." + error.message 
                                              + "\n" + new Date() 
                                              + "\nvaxType: " + vaxType +
                                              + "\nvaxNumber: " + vaxNumber);
    }  
  }
   
  
  function saveData(vaxType, vaxNumber){
    Logger.log("--- save_data ---"); 
   
   
    try {
  
      // oppretter et nytt DateTime-objekt for aa lagre dato og klokkeslett for requesten
      var dateTime = new Date();
      var formattedDate = Utilities.formatDate(dateTime, SpreadsheetApp.getActive().getSpreadsheetTimeZone(), 'dd/mm/yyyy');
      var formattedTime = Utilities.formatDate(dateTime, SpreadsheetApp.getActive().getSpreadsheetTimeZone(), 'HH:mm')
   
      // URLen til Google Spreadsheet-et der resultatene skal loggfoeres
      var ss = SpreadsheetApp.openByUrl("https://docs.google.com/spreadsheets/d/1XnFOaI3ALkc4_oeiKNlrGAXcazPyHHj6WqELLz3wmyA/edit#");
      var dataLoggerSheet = ss.getSheetByName("Hovedark");
   
      // hent forrige rad som ble endret og gaa 1 videre
      var row = dataLoggerSheet.getLastRow() + 1;
  
      // hopper over header-raden
      if (row == 1) {return;}
   
      // vi er naa kommet til neste ledige rad som ikke er headerne, saa vi kan sette inn dataen
      dataLoggerSheet.getRange("A" + row).setValue(formattedDate); // DATO
      dataLoggerSheet.getRange("B" + row).setValue(formattedTime); // KLOKKESLETT
      dataLoggerSheet.getRange("C" + row).setValue(vaxType); // Vaksinetype
      dataLoggerSheet.getRange("D" + row).setValue(vaxNumber); // Vaksinenummer (vaksine nummer N i dag)
    }
   
    catch(error) {
      Logger.log(JSON.stringify(error));
    }
   
    Logger.log("--- saveData end---"); 
  }