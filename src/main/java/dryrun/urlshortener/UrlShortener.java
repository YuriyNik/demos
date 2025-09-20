package dryrun.urlshortener;

public interface UrlShortener {
    String generateUrlByKeyword(String longUrl, String keyword);
    String generateUrlCharRandom(String longUrl);
    String getUrlByKeyword(String shortUrl, String keyword);
    String getUrlbyCharRandom(String shortUrl);
}
