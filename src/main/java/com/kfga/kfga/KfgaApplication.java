package com.kfga.kfga;

import org.codehaus.jettison.json.JSONArray;
import org.jsoup.Jsoup;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//@SpringBootApplication
public class KfgaApplication {
    public static void main(String[] args) {
        //SpringApplication.run(KfgaApplication.class, args);
        try {

            Document doc1 = Jsoup.connect("http://portal.kleague.com/common/result/result0051popup.do")
                    .data("workingTag", "L")
                    .data("iptMeetYear", "2015")
                    .data("iptMeetSeq", "1")
                    .data("iptGameid", "1")
                    .data("iptMeetName", "현대오일뱅크 K리그 클래식 2015")
                    .post();
            for (Element scripts : doc1.getElementsByTag("script")) {

                Pattern p = Pattern.compile("(?s)(jsonResultData)\\s??=\\s??\\[\\[(.*?)\\]\\]");
                Matcher m = p.matcher(scripts.html());

                while( m.find() )
                {
                    String jsonResultData = m.group().replace("jsonResultData = ","");
                    //System.out.println(jsonResultData.toString());
                    //System.out.println("-----");
                    JSONArray arr = new JSONArray();
                    //System.out.println(jsonResultData); // value only
                    String [] data = jsonResultData.split("\\{\"H_Change_In_Time" );

                    Pattern p1 = Pattern.compile("(?s)\\[\\{(.*?)\\}\\]");
                    Matcher m1 = p1.matcher(jsonResultData);

                    while( m1.find() ){
                        //System.out.println(m1.group());
                        //System.out.println(m1.group(1));


                        Pattern p2 = Pattern.compile("(?s)\\{(.*?)\\}");
                        Matcher m2 = p2.matcher(m1.group());
                        while( m2.find() ){
                            System.out.println(m2.group());
                            System.out.println("======================");
                        }
                    }
                    /*
                    for(String d : data){
                        System.out.println("\\{\"H_Change_In_Time"+d);
                        System.out.println("======================");
                    }
                    */
                }
            }

            Document doc2 = Jsoup.connect("http://portal.kleague.com/main/schedule/calendar.do")
                    .header("Referer", "http://portal.kleague.com/mainFrame.do")
                    .data("selectYear", "2015")
                    .data("selectMonth", "4")
                    .post();

            //System.out.println("fn_detailPopup('2015','1','현대오일뱅크 K리그 클래식 2015','1','2','1')");
            //System.out.println("fn_detailPopup('2015','1','현대오일뱅크 K리그 클래식 2015','19','2','1');\"");
            Elements k1 = doc2.getElementsByClass("full-calendar-data-k1");
            System.out.println("#######################################################");
            for (Element k : k1) {
                Element a = k.getElementsByTag("a").get(0);
                //System.out.println("k1 : " + a);
            }


            System.out.println("#######################################################");
            // 1. URL 선언
            String connUrl = "http://www.wisetoto.com/util/gameinfo/get_proto_list.htm?game_category=pt1&game_year=2020&game_round=9&game_month=&game_day=&game_info_master_seq=21849&sports=&sort=&tab_type=proto";
            // 2. HTML 가져오기
            Document doc = Jsoup.connect(connUrl).ignoreContentType(true)
                                .header("Referer", "http://www.wisetoto.com/index.htm?tab_type=proto")
                                .get();
            // 3. 가져온 HTML Document 를 확인하기
            Elements element = doc.select("div.gameInfo");
            Iterator<Element> a1 = element.select("li.a1").iterator();
            Iterator<Element> a2 = element.select("li.a2").iterator();
            Iterator<Element> a4 = element.select("li.a4").iterator();
            Iterator<Element> a6 = element.select("li.a6").iterator();
            Iterator<Element> a8 = element.select("li.a8").iterator();

            //System.out.println(doc.toString());

            while (a1.hasNext()) {
                /*
                System.out.println(a1.next().text()
                        +"\t"+a2.next().text()
                        +"\t"+a4.next().text()
                        +"\t"+a6.next().text()
                        +"\t"+a8.next().text());
                */
            }
        } catch (IOException e) {
            // Exp : Connection Fail
            e.printStackTrace();
        }

    }

}
