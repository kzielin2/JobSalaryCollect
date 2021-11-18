///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package jobsalarycollect;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//import java.io.IOException;
//import java.nio.file.*;
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileReader;
//
//import javax.sound.sampled.SourceDataLine;
//
//import java.io.*;
///**
// *
// * @author karol
// */
//public class NewClass {
//    
//    
//}
// /*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
////package HelloWord;
///*
//1. Wykonuje przeszukanie strony zbieram do tablicy pełny wynik wyszukiwanie
//= wyszukiwanie w odzielnej metoodzie jako parametr link do wyszukiwania
//- wynik jaki zwaraca to tablica
//2. Następnie sprawdzam czy link w sytępuje już w pliku.
//odzielna metoda wejście link tablicy z danego wiersza, wyjście true- jest, false - nie ma
//    Jeśli tak:
//    - przechodzę do następnego sprawdzenia
//    - 
//    Jeśli nie:
//    - formatuje dany wiersz tablicy zgodnie z notacją csv
//    odzielna metoda wejście: wiersz tablicy, wyjście gotowy strin do umieszczenia w pliu
//    - dodaje wiersz do pliku, dodaje też datę na koniec
//    odzielna metoda wejście string
//
//
//*/
//
//
///**
// *
// * @author karol
// */
// class HelloWord {
//    public static void main(String[] args) throws IOException {
//     
//        int info=1;
//        int sprawdzenie=1;
//        int wynik;
//        int iloscPozycji=0;
//        boolean czyCosZnaleziono;
//      
//        // sprawdzanie ile jest pod stron
//        while (info != 0){
//            
//          //String link2 = "https://nofluffjobs.com/pl/praca-it?page="+sprawdzenie+"&criteria=keyword%3D%27software%20engineer%27";
//          String link2 = "https://nofluffjobs.com/pl/praca-it?page="+sprawdzenie+"&criteria=keyword%3D%27java%20developer%27";
//          Document doc2 = Jsoup.connect(link2).get();
//          info = doc2.select("h3.posting-title__position").toArray().length;
//          iloscPozycji= iloscPozycji + info;
//          sprawdzenie++;
//        }
//        
//        wynik = sprawdzenie -2;
//        System.out.println("Wynik prawdzenia: ilość podstron "+ wynik);
//        System.out.println("Ilość pozycji " + iloscPozycji);
//        String[][] tablicaInformacjeOWyszukanychOfertach = new String[iloscPozycji][4];
//           
//  
//        for (int podstrona=1; podstrona<= wynik; podstrona++){        
//            //String link2 = "https://nofluffjobs.com/pl/praca-it?page="+podstrona+"&criteria=keyword%3D%27software%20engineer%27";
//            String link2 = "https://nofluffjobs.com/pl/praca-it?page="+podstrona+"&criteria=keyword%3D%27java%20developer%27";
//            Document doc = Jsoup.connect(link2).get();
//            //pobranie ofert z urla
//            Elements nazwyStanowisk = doc.select("h3.posting-title__position");
//            Elements miejscaWykonywaniaPracy = doc.select("span.posting-info__location");
//            Elements wynagrodzenia = doc.select("span.salary");
//            Elements linkiOferty = doc.select("a.posting-list-item");
//            
//            
//            
//            for (int ofertaPracy=1; ofertaPracy<=nazwyStanowisk.toArray().length;ofertaPracy++){
//                int m=(podstrona-1)*20;
//                for (Element miejsceWykonywaniaPracy : miejscaWykonywaniaPracy ) {
//                    tablicaInformacjeOWyszukanychOfertach[m][2] = miejsceWykonywaniaPracy.text();
//                    m++;
//                }
//                int k=(podstrona-1)*20;
//                for (Element wynagrodzenie : wynagrodzenia ) {
//                    tablicaInformacjeOWyszukanychOfertach[k][1] = wynagrodzenie.text();
//                    k++;
//                }
//                int s=(podstrona-1)*20;
//                for (Element nazwaStanowiska : nazwyStanowisk ) {
//                    tablicaInformacjeOWyszukanychOfertach[s][0] = nazwaStanowiska.text();
//                    s++;
//                } 
//                int w=(podstrona-1)*20;
//                for (Element linkOferty : linkiOferty ) {
//                    tablicaInformacjeOWyszukanychOfertach[w][3] = linkOferty.attr("href");
//                    w++;
//                }
//            }
//        }
//       
//      
//        // załadowanie informacji do tablicy
//
//     
//        
//    for (int i=0; i < tablicaInformacjeOWyszukanychOfertach.length; i++){
//        for (int e=0; e <=3; e++){
//             System.out.print((tablicaInformacjeOWyszukanychOfertach[i][e])+" ");
//        }
//       System.out.println();
//    }
//    
//       for (String [] wyswieltanieZawartosciTabeliOOfertach : tablicaInformacjeOWyszukanychOfertach){
//            System.out.println(wyswieltanieZawartosciTabeliOOfertach);
//        }
//    FileWordSearch szukamy = new FileWordSearch();
//    szukamy.sprawdz();
//    czyCosZnaleziono = szukamy.czyWyszukano();
//    System.out.println(czyCosZnaleziono);
//}
//}
// 
//    class zapisywanie { 
//        String filePath = "C:/Users/karol_zielinski/Desktop/test.txt";
//       // FileWriter fileWriter = null;
//       
//    //     String test = tablicaInformacjeOWyszukanychOfertach [0] [0] +"," + tablicaInformacjeOWyszukanychOfertach [0] [1] + "," + tablicaInformacjeOWyszukanychOfertach [0] [2] ;
//        String test = "test";
//         final String NEW_LINE = System.lineSeparator();
//       // NEW_LINE.contains("");
//        
//    public void zapisz() {
//        try {
//            BufferedWriter out =  new BufferedWriter(new FileWriter(filePath, true));
//            out.write(test+NEW_LINE);
//            out.close();
//        }
//        
//        catch (IOException e) {
//                // Display message when exception occurs
//                System.out.println("exception occoured" + e);
//
//        }
//    } 
//} 
//
//
//
//    
//    //Testowa nowaInstnancjaTestowejKlasy = new Testowa();
//   // nowaInstnancjaTestowejKlasy.ustawKolor("Czarny");
//    //nowaInstnancjaTestowejKlasy.wyswietlKolor();
//
// 
//
//
//
//
//
//
//class Testowa {
//    private String kolor;
//    public void ustawKolor(String kolorZmieniony ) {
//       kolor = kolorZmieniony;
//    }
//    public void wyswietlKolor() {
//        System.out.println(kolor);
//    }
//}
//
//
//
//class FileWordSearch   { 
//    //String f1= "C:/Users/karol_zielinski/Desktop/test.txt";
//    private File f1= new File("C:/Users/karol_zielinski/Desktop/test.txt"); //Creation of File Descriptor for input file
//    private String[] words=null;  
//   // try {
//    private int count=0; 
//    public void sprawdz() throws IOException {
//    count =0;
//    FileReader fr = new FileReader(f1);
//
//  //Creation of File Reader object
//     BufferedReader br = new BufferedReader(fr); //Creation of BufferedReader object
//     String s;     
//     String input="wynik";   // Input word to be searched
//      //Intialize the word to zero
//     
//    //public void find()  throws FileNotFoundException 
//      while((s=br.readLine())!=null)   //Reading Content from the file
//      {
//         words=s.split(" ");  //Split the word using space
//          for (String word : words) 
//          {
//                 if (word.contains(input))   //Search for the given word
//                 {
//                   count++;    //If Present increase the count by one
//                 }
//          }
//      }
//      if(count!=0)  //Check for count not equal to zero
//      {
//         System.out.println("The given word is present for "+count+ " Times in the file");
//      }
//      else
//      {
//         System.out.println("The given word is not present in the file");
//      }
//      
//         fr.close();
//
//     
//    //} 
//    //catch (FileNotFoundException fe){
//    //        fe.printStackTrace();
//   // }
//    }
//    public boolean czyWyszukano(){
//        if (count ==0)
//        return false;
//        else
//        return true;
//    }
//}
//
//
//
//
//
//    
// 
//
