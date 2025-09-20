package dryrun.urlshortener;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class UrlShortenerServiceV2 implements UrlShortenerV2{
    private Map<String,String> shortCodetoUrl = new ConcurrentHashMap<>();
    private Map<String,String> urlToShortCode = new ConcurrentHashMap<>();
    private static final int MAX_RETRIES =3;
    @Override
    public synchronized String shorten(String url){
        if (url==null || url.isEmpty()) {
            throw new IllegalArgumentException("Long url is null or empty");
        }
        if (urlToShortCode.get(url)!=null)
        {
            return urlToShortCode.get(url);
        }

            for (int i = 0; i <MAX_RETRIES ; i++) {
                String newCode = generateCode();
               if (shortCodetoUrl.putIfAbsent(newCode, url)==null){
                   urlToShortCode.putIfAbsent(url, newCode);
                   return newCode;
               }
            }
        throw new IllegalStateException("Failed to generate shortcode after "+MAX_RETRIES+" attempts");
    }
    private String generateCode(){
        //it is better to replace this with Random generation from A-Z chars.
        return UUID.randomUUID().toString().replaceAll("-","").substring(0,6);
    }

    @Override
    public String restore(String shortCode){
        String url = shortCodetoUrl.get(shortCode);
        if (url!=null){
            return url;
        }
        throw new NoSuchElementException("Url for "+shortCode+" not found");
    }

}
