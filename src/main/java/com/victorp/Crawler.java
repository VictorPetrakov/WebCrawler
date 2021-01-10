package com.victorp;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;


public class Crawler {


    private int MAX_PAGES_TO_SEARCH;
    private Set<String> pagesVisited = new HashSet<String>();
    private List<String> pagesToVisit = new LinkedList<String>();
    private Map<String, Integer> resultList = new HashMap();
    private Map<String, Integer> top10ResultList = new HashMap<>();

    public Crawler(){
    }

    public void search(String url, List<String> searchWords) throws IOException {
        CSVWriter writer = new CSVWriter();
        InputStream in = new FileInputStream("src/main/resources/config.properties");
        Properties properties = new Properties();
        properties.load(in);
        MAX_PAGES_TO_SEARCH = Integer.parseInt(properties.getProperty("maxPageToSearch"));
        while (this.pagesVisited.size() < MAX_PAGES_TO_SEARCH) {
            String currentUrl;
            CrawlerUtil crawlerUtil = new CrawlerUtil();
            if (this.pagesToVisit.isEmpty()) {
                currentUrl = url;
                this.pagesVisited.add(url);
            } else {
                currentUrl = this.nextUrl();
            }

            if (crawlerUtil.crawl(currentUrl)) {
                List<Integer> countWords = crawlerUtil.searchForWord(searchWords);
                int sum = countWords.stream()
                        .mapToInt(a -> a)
                        .sum();
                String result;
                result = currentUrl + " " + countWords.toString().replaceAll("[,.]", "").replaceAll("\\[", "").replaceAll("\\]", "") + " " + sum;
                resultList.put(result, sum);
                this.pagesToVisit.addAll(crawlerUtil.getLinks());
            }

        }

        writer.writeAllResultToList(resultList);

        top10ResultList = resultList.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue()
                        .reversed()
                        .thenComparing(Map.Entry.comparingByKey()))
                .limit(10).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> b, LinkedHashMap::new));

        writer.writeTop10ResultToList(top10ResultList);
        top10ResultList.entrySet().stream().forEach(System.out::println);

        System.out.println("\nDone! Visited " + this.pagesVisited.size() + " web page(s)");
    }

    private String nextUrl() {
        String nextUrl;
        do {
            nextUrl = this.pagesToVisit.remove(0);
        } while (this.pagesVisited.contains(nextUrl));
        this.pagesVisited.add(nextUrl);
        return nextUrl;
    }

}
