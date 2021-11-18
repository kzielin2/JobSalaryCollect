package jobsalarycollect;

//JobSalaryCollect
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package HelloWord;
/*
OK 1. Wykonuje przeszukanie strony zbieram do tablicy pełny wynik wyszukiwanie
= wyszukiwanie w odzielnej metoodzie jako parametr link do wyszukiwania
- wynik jaki zwraca to tablica
2. Następnie sprawdzam czy link w sytępuje już w pliku.
ok odzielna metoda wejście link tablicy z danego wiersza, wyjście true- jest, false - nie ma
    Jeśli tak:
    - przechodzę do następnego sprawdzenia
    - 
    Jeśli nie:
    - formatuje dany wiersz tablicy zgodnie z notacją csv
    odzielna metoda wejście: wiersz tablicy, wyjście gotowy strin do umieszczenia w pliu
    - dodaje wiersz do pliku, dodaje też datę na koniec
    odzielna metoda wejście string


*/

/*
A teraz zastanawiam się nad wersją rozszerzoną
2b. Jeśli nie:
- wchodzę do danego linku i pobieram dane
    nowa metoda 
                - wejście  link do konkretnego ogłoszenia
                - wyjście tablica z danymi do danego rekordu
- dodaje do pliku gotowy rekord
                - dodaniej nowej meteody  lub przerobienie metody (formafileline na bardziej uniersalną)
- zapis do pliu



*/
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author karol
 */
public class JobSalaryCollect {
    public static void main(String[] args) throws IOException {
        // test2
        //Test2PreparingToExtractFromOffer exampleExtract = new Test2PreparingToExtractFromOffer();
        //exampleExtract.uploadtest("https://nofluffjobs.com/pl/job/mid-senior-nodejs-software-engineer-westwing-remote-ybu4snsr");
        
        
        JobSalaryImport jobsimport = new JobSalaryImport();
        String [] link2 = {
                           "https://nofluffjobs.com/pl/praca-it?page=1&criteria=keyword%3D%27software%20engineer%27"
                          ,"https://nofluffjobs.com/pl/praca-it?page=1&criteria=keyword%3D%27java%20developer%27"
        };
        String [] jobsArrayOutputLine = new String [6];
        String stringLine= null;
        
//        test 
//        Test testuje = new Test();
//        testuje.uploadtest("https://nofluffjobs.com/pl/praca-it?page=1&criteria=keyword%3D%27java%20developer%27");
        
        for (String link:link2){            
            String [][] jobsArrayOutput = jobsimport.uploadDataFromHtml(link);

            for(int i=0;i < jobsArrayOutput.length; i++ ){
            // przygotowanie wiersza do wyszukiwnia dla funkcji serachinFile    
                String []arrayToSearch  = new String [6];
                for (int o=0; o<6; o++){
                    arrayToSearch [o] =jobsArrayOutput[i][o];
                }                
                boolean isFind = jobsimport.serachInFile(arrayToSearch);
                if (isFind == false){
                    for (int j=0; j <= 5; j++){
                        jobsArrayOutputLine[j] = jobsArrayOutput[i][j];
                    }
                    stringLine= jobsimport.formatFileLine(jobsArrayOutputLine);
                    jobsimport.writeOfferToFile(stringLine);                    
                }                                  
            }         
        }
    }
    
}



class JobSalaryImport {
    File inputFile= new File("C:/Users/karol/desktop/job_offers.txt");
    
    
    public String [][] uploadDataFromHtml(String urlLink) {
        int info=1;
        int pageNumber=1;
        int truePageNumber;
        int findJobsOffer=0;
        String [] linkSplit= urlLink.split("page=1");

        Document doc2 = null;
        while (info != 0){            
            String link2 = linkSplit[0] + "page=" + pageNumber + linkSplit[1];
            try {
                doc2 = Jsoup.connect(link2).get();
                
            } catch (IOException ex) {
                Logger.getLogger(JobSalaryImport.class.getName()).log(Level.SEVERE, null, ex);
            }
          info = doc2.select("h3.posting-title__position").toArray().length;
          findJobsOffer= findJobsOffer + info;
          pageNumber++;
        }
        truePageNumber = pageNumber -2;
        String[][] arrayOfFindJobsOffer = new String[findJobsOffer][6]; //tablicaInformacjeOWyszukanychOfertach

        
        
        for (int page=1; page<= truePageNumber; page++){        
            String link2 = linkSplit[0] + "page=" + page + linkSplit[1];
            try {
                Document doc = Jsoup.connect(link2).get();
                Elements nazwyStanowisk = doc.select("h3.posting-title__position");
                Elements miejscaWykonywaniaPracy = doc.select("span.posting-info__location");
                Elements wynagrodzenia = doc.select("span.salary");
                Elements linkiOferty = doc.select("a.posting-list-item");
                Elements jobCompanies = doc.select("span.posting-title__company");
                Elements jobTechnologies = doc.select("a[nfjstopevent]");

            
                for (int jobOffer=1; jobOffer<=nazwyStanowisk.toArray().length;jobOffer++){

                    int s=(page-1)*20;
                    for (Element nazwaStanowiska : nazwyStanowisk ) {
                        arrayOfFindJobsOffer[s][0] = nazwaStanowiska.text();
                        s++;
                    }
                    int k=(page-1)*20;
                    for (Element wynagrodzenie : wynagrodzenia ) {
                        arrayOfFindJobsOffer[k][5] = wynagrodzenie.text();
                        k++;
                    }
                    int m=(page-1)*20;
                    for (Element miejsceWykonywaniaPracy : miejscaWykonywaniaPracy ) {
                        arrayOfFindJobsOffer[m][2] = miejsceWykonywaniaPracy.text();
                        m++;
                    }
                    int w=(page-1)*20;
                    for (Element linkOferty : linkiOferty ) {
                        arrayOfFindJobsOffer[w][3] = linkOferty.attr("href");
                        w++;
                    }
                    int z=(page-1)*20;
                    for (Element jobComapny : jobCompanies ) {
                        arrayOfFindJobsOffer[z][4] = jobComapny.text().substring(1);
                        z++;
                    }
                    int y=(page-1)*20;
                    for (Element jobTechnology : jobTechnologies ) {
                            arrayOfFindJobsOffer[y][1] = jobTechnology.text();
                            y++;
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(JobSalaryImport.class.getName()).log(Level.SEVERE, null, ex);
            }

            

        }
//        for (int i=0; i < arrayOfFindJobsOffer.length; i++){
//            for (int e=0; e <=5; e++){
//                 System.out.print((arrayOfFindJobsOffer[i][e])+" ");
//            }
//            System.out.println();
//        }
    return arrayOfFindJobsOffer;
    }
    
    public boolean serachInFile(String [] jobLinkToFind) throws IOException{
            String [] jobToFind = jobLinkToFind;
            String currentLine = formatFileLine(jobToFind);
           
            
            FileReader fileRead = null;
        try {
            fileRead = new FileReader(inputFile);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(JobSalaryImport.class.getName()).log(Level.SEVERE, null, ex);
        }
            BufferedReader buforRead = new BufferedReader(fileRead); //Creation of BufferedReader object
            String fileLine;
            String salaryInLineFile = formatSalary(jobLinkToFind);
            
            while((fileLine=buforRead.readLine())!=null){   //Reading Content from the file
                  if ((fileLine.contains(jobToFind[3])) && fileLine.contains(salaryInLineFile) )
                    return true;                  
            }
            return false;      
    }
    public String formatFileLine(String [] inputLine){
         String [] input = inputLine;
         String outputLine = "";
        
        for (int s=0; s < input.length; s++){        
            if ( s == 5 )
                outputLine+=formatSalary(input);
            else 
                outputLine += input[s] + ";";
        }
        return outputLine;
    }
    
    public String formatSalary (String [] inputLine){
        String input = inputLine[5];
        String outputLine = "";
        String jobOfferSalary = input.replace(" ","");
        String jobOfferSalaryCurrency = jobOfferSalary.substring(jobOfferSalary.length()-3);
        String jobOfferSalaryValue =jobOfferSalary.substring(0,jobOfferSalary.length()-3);
        if (jobOfferSalaryValue.contains("-")){
            String [] jobOffeSalryParts = jobOfferSalaryValue.split("-");
            outputLine +=  jobOffeSalryParts[0] + ";" + jobOffeSalryParts[1] + ";" + jobOfferSalaryCurrency;
            } else
            outputLine += jobOfferSalaryValue + ";" + " " + ";" + jobOfferSalaryCurrency;
        return outputLine;              
    }
            
    
    public void writeOfferToFile(String offerToWrite) {
        String inputData = offerToWrite;
        final String NEW_LINE = System.lineSeparator();
        BufferedWriter out;
        try {
            out = new BufferedWriter(new FileWriter(inputFile, true));
            out.write(inputData + NEW_LINE);
            out.close();
        } catch (IOException ex) {
            Logger.getLogger(JobSalaryImport.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
}

class Test {
        public String [][] uploadtest (String urlLink) {
     
        int info=0;
        int pageNumber=1;
        int truePageNumber;
        int findJobsOffer=0;
        String [] linkSplit= urlLink.split("page=1");
        Document doc2 = null;
        
           
            String link2 = urlLink;
            try {
                doc2 = Jsoup.connect(link2).get();
            } catch (IOException ex) {
                Logger.getLogger(JobSalaryImport.class.getName()).log(Level.SEVERE, null, ex);
            }
          info = doc2.select("h3.posting-title__position").toArray().length;
          findJobsOffer= findJobsOffer + info;


        truePageNumber = pageNumber -2;
        System.out.println("Wynik prawdzenia: ilość podstron: "+ truePageNumber);
        System.out.println("Ilość pozycji: " + findJobsOffer);
        String[][] arrayOfFindJobsOffer = new String[findJobsOffer][6]; //tablicaInformacjeOWyszukanychOfertach
        
        
        for (int page=1; page<= 1; page++){        

            try {
                Document doc = Jsoup.connect(link2).get();
                Elements nazwyStanowisk = doc.select("h3.posting-title__position");
                Elements miejscaWykonywaniaPracy = doc.select("span.posting-info__location");
                Elements wynagrodzenia = doc.select("span.salary");
                Elements linkiOferty = doc.select("a.posting-list-item");
                Elements jobCompanies = doc.select("span.posting-title__company");
                Elements jobTechnologies = doc.select("a[nfjstopevent]");
                
                        //_ngcontent-sc302
            
                for (int jobOffer=1; jobOffer<=nazwyStanowisk.toArray().length;jobOffer++){

                    int s=(page-1)*20;
                    for (Element nazwaStanowiska : nazwyStanowisk ) {
                        arrayOfFindJobsOffer[s][0] = nazwaStanowiska.text();
                        s++;
                    }
                    int k=(page-1)*20;
                    for (Element wynagrodzenie : wynagrodzenia ) {
                        arrayOfFindJobsOffer[k][5] = wynagrodzenie.text();
                        k++;
                    }
                    int m=(page-1)*20;
                    for (Element miejsceWykonywaniaPracy : miejscaWykonywaniaPracy ) {
                        arrayOfFindJobsOffer[m][2] = miejsceWykonywaniaPracy.text();
                        m++;
                    }
                    int w=(page-1)*20;
                    for (Element linkOferty : linkiOferty ) {
                        arrayOfFindJobsOffer[w][3] = linkOferty.attr("href");
                        w++;
                    }
                    int z=(page-1)*20;
                    for (Element jobComapny : jobCompanies ) {
                        arrayOfFindJobsOffer[z][4] = jobComapny.text().substring(1);
                        z++;
                    }
                    int y=(page-1)*20;
                    for (Element jobTechnology : jobTechnologies ) {
                            arrayOfFindJobsOffer[y][1] = jobTechnology.text();
                            y++;
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(JobSalaryImport.class.getName()).log(Level.SEVERE, null, ex);
            }

            

        }
        for (int i=0; i < arrayOfFindJobsOffer.length; i++){
            for (int e=0; e <=5; e++){
                 System.out.print((arrayOfFindJobsOffer[i][e])+" ");
            }
            System.out.println();
        }
    return arrayOfFindJobsOffer;
    }
}

class Test2PreparingToExtractFromOffer {
            public String [] uploadtest (String urlLink) {
                String link2= urlLink;
     
//        int info=0;
//        int pageNumber=1;
//        int truePageNumber;
//        int findJobsOffer=0;
  //      String [] linkSplit= urlLink.split("page=1");
        
           
//            String link2 = urlLink;
//            try {
//                doc2 = Jsoup.connect(link2).get();
//            } catch (IOException ex) {
//                Logger.getLogger(JobSalaryImport.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            info = doc2.select("h3.posting-title__position").toArray().length;
//            findJobsOffer= findJobsOffer + info;


//        truePageNumber = pageNumber -2;
//        System.out.println("Wynik prawdzenia: ilość podstron: "+ truePageNumber);
//        System.out.println("Ilość pozycji: " + findJobsOffer);
        String[] arrayOfFindJobsOffer = new String [6]; //tablicaInformacjeOWyszukanychOfertach
        String skills = "";
        
        
     //   for (int page=1; page<= 1; page++){        

            try {
                Document doc = Jsoup.connect(link2).get();
                Elements mandatoryRequirements1 = doc.select("a[nfjstopevent]");
                //Elements Requirements2 = doc.select("button[_ngcontent-sc306]");
                Elements Requirements = doc.select("h3[_ngcontent-sc307]");
                Elements skillsWelcome = doc.select("span.posting-info__location");
                Elements wynagrodzenia = doc.select("span.salary");
                Elements linkiOferty = doc.select("a.posting-list-item");
                Elements jobCompanies = doc.select("span.posting-title__company");
                Elements jobTechnologies = doc.select("a[nfjstopevent]");
                
                String mandatorySkills="";
                String welcomeSkills="";
                
                        //_ngcontent-sc302
            
                //for (int jobOffer=1; jobOffer<=nazwyStanowisk.toArray().length;jobOffer++){

                   // int s=(page-1)*20;
                    for (Element mandatoryRequirement : mandatoryRequirements1 ) {
                        //System.out.println(mandatoryRequirement.text());
                        //arrayOfFindJobsOffer[s][0] = nazwaStanowiska.text();
                       // s++;
                    }
                    for (Element Requirement : Requirements ) {
                        if (!Requirement.text().contains(" ")){
                             skills+=Requirement.text();
                        
                    } else {
                             skills+= Requirement.text().concat("\n");
                    }
                    }
                    String [] skillsarray = skills.split("\\n");
                    mandatorySkills = skillsarray[0];
                    //welcomeSkills = skillsarray[1];
                    System.out.println(mandatorySkills);
                    //System.out.println(welcomeSkills);
                    //System.out.println(skillsarray[1]);
                    //System.out.println("Java Git English OOP Communication skills Design patterns Spring Hibernate Team player GOSU Guidewire Gerrit Maven Clean code JUnit SQL");
//                    int k=(page-1)*20;
//                    for (Element wynagrodzenie : wynagrodzenia ) {
//                        arrayOfFindJobsOffer[k][5] = wynagrodzenie.text();
//                        k++;
//                    }
//                    int m=(page-1)*20;
//                    for (Element miejsceWykonywaniaPracy : miejscaWykonywaniaPracy ) {
//                        arrayOfFindJobsOffer[m][2] = miejsceWykonywaniaPracy.text();
//                        m++;
//                    }
//                    int w=(page-1)*20;
//                    for (Element linkOferty : linkiOferty ) {
//                        arrayOfFindJobsOffer[w][3] = linkOferty.attr("href");
//                        w++;
//                    }
//                    int z=(page-1)*20;
//                    for (Element jobComapny : jobCompanies ) {
//                        arrayOfFindJobsOffer[z][4] = jobComapny.text().substring(1);
//                        z++;
//                    }
//                    int y=(page-1)*20;
//                    for (Element jobTechnology : jobTechnologies ) {
//                            arrayOfFindJobsOffer[y][1] = jobTechnology.text();
//                            y++;
//                    }
                //}
            } catch (IOException ex) {
                Logger.getLogger(JobSalaryImport.class.getName()).log(Level.SEVERE, null, ex);
            }

            

       // }
//        for (int i=0; i < arrayOfFindJobsOffer.length; i++){
//            for (int e=0; e <=5; e++){
//                 System.out.print((arrayOfFindJobsOffer[i][e])+" ");
//            }
//            System.out.println();
//        }
    return arrayOfFindJobsOffer;
    }
}