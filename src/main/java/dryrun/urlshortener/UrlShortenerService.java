package dryrun.urlshortener;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UrlShortenerService implements UrlShortener{

    Map<String, String> urlsByKeywords = new HashMap<>();

    Map<String, String> urlsByCode = new HashMap<>();

    private String getCharRandomCode(){
        return UUID.randomUUID().toString().replace("-","").substring(0,4);
    }

    @Override
    public String generateUrlByKeyword(String longUrl, String keyword){
        int shortUrl = longUrl.hashCode();
        String key = keyword+":"+shortUrl;
        urlsByKeywords.put(key,longUrl);
        return Integer.toString(shortUrl);
    }
    @Override
    public String generateUrlCharRandom(String longUrl){

        int MAX_ATTEMPTS = 3;
        for (int i = 0; i < MAX_ATTEMPTS; i++) {
            String key = getCharRandomCode();
            if (urlsByCode.putIfAbsent(key,longUrl)==null){
                return key;
            }
            MAX_ATTEMPTS++;
        }
        throw new IllegalStateException("max attempts reached for random code generation MAX_ATTEMPTS"+MAX_ATTEMPTS);
    }
    @Override
    public String getUrlByKeyword(String shortUrl, String keyword){
        return urlsByKeywords.get(keyword+":"+shortUrl);
    }
    @Override
    public String getUrlbyCharRandom(String shortUrl){
        return urlsByCode.get(shortUrl);
    }
}
