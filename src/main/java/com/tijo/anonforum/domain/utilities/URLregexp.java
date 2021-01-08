package com.tijo.anonforum.domain.utilities;

import com.tijo.anonforum.domain.dto.movie.MovieDTO;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class URLregexp {
    public static boolean VerifyURL(String toverify) {
        try{
            Pattern regex = Pattern.compile("(?:([^:/?#]+):)?(?://([^/?#]*))?([^?#]*\\.(?:jpg|gif|png))(?:\\?([^#]*))?(?:#(.*))?");
            Matcher matcher = regex.matcher(toverify);
            if(!matcher.find()) {
                throw new URISyntaxException(toverify, "Not a valid image url");
            }
            URL u = new URL(toverify);
            u.toURI();
        } catch (MalformedURLException | URISyntaxException e) {
            return false;
        }
        return true;
    }
}
