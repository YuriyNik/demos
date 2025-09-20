package dryrun.urlshortener;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class UrlShortenerServiceTest {

    @Test
    void urlShortenerService_Test(){
        UrlShortener urlShortener = new UrlShortenerService();
        List<String> shortUrls = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
//            shortUrls.add(
//                    urlShortener.generateUrlByKeyword("https://leetcode.com/problems/count-submatrices-with-all-ones/submissions/1743684871"+i,"yuriy"));
            shortUrls.add(urlShortener.generateUrlCharRandom("https://leetcode.com/problems/count-submatrices-with-all-ones/submissions/1743684871"+i));
        }
//        shortUrls.add(
//                urlShortener.generateUrlByKeyword("https://leetcode.com/problems/count-submatrices-with-all-ones/submissions/1743684871"+1,"yuriy"));
        shortUrls.add(urlShortener.generateUrlCharRandom("https://leetcode.com/problems/count-submatrices-with-all-ones/submissions/1743684871"+1));
        System.out.println(shortUrls);
        for (String shortUrt:shortUrls){
//            System.out.println(urlShortener.getUrlByKeyword(shortUrt,"yuriy"));
            System.out.println(urlShortener.getUrlbyCharRandom(shortUrt));
        }




    }
}
