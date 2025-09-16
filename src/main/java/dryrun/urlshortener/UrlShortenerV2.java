package dryrun.urlshortener;

public interface UrlShortenerV2 {
    /**
     *
     * @param url long url link
     * @return short code (6 chars alphanumeric)
     * @throws IllegalArgumentException if url is null or empty
     */
    String shorten(String url);
    /**
     *
     * @param shortCode short code
     * @return original url
     * @throws java.util.NoSuchElementException if short not found
     */
    String restore(String shortCode);
    //Покрыть тестами:
//один и тот же url → один и тот же short
//разные url → разные short
//restore работает правильно
//параллельные вызовы не ломают консистентность
}
