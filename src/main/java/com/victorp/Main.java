package com.victorp;

import java.util.*;

public class Main
{
    public static void main( String[] args ) throws Exception {

        Crawler spider = new Crawler();
        List<String> words = new ArrayList<>();
        words.add("Elon");
        words.add("Musk");
        words.add("Tesla");
        words.add("Elon Musk");
        spider.search("https://en.wikipedia.org/wiki/Elon_Musk", words);
   }
}
