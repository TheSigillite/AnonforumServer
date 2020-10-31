package com.tijo.anonforum.domain.utilities;

import com.tijo.anonforum.domain.dto.movie.MovieDTO;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class URLregexp {
    public static boolean VerifyURL(MovieDTO newMovie) {
        try{
            Pattern regex = Pattern.compile("((http|https|ftp)://)?((\\w)*|([0-9]*)|([-|_])*)+([\\.|/]((\\w)*|([0-9]*)|([-|_])*))+");
            Matcher matcher = regex.matcher(newMovie.getCover());
            if(!matcher.find()) {
                throw new URISyntaxException(newMovie.getCover(), "Not a valid url");
            }
            URL u = new URL(newMovie.getCover());
            u.toURI();
        } catch (MalformedURLException | URISyntaxException e) {
            return false;
        }
        return true;
    }
}
