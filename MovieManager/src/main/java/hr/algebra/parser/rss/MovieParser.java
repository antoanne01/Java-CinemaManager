/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.parser.rss;
import hr.algebra.factory.ParserFactory;
import hr.algebra.factory.UrlConnectionFactory;
import hr.algebra.model.Actor;
import hr.algebra.model.Director;
import hr.algebra.model.Genre;
import hr.algebra.model.Movie;
import hr.algebra.utilities.FileUtils;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 *
 * @author antoanne
 */
public class MovieParser {
    
    private MovieParser(){
    } 
    
    private static final String RSS_URL = "https://www.blitz-cinestar-bh.ba/rss.aspx?id=2682";
    private static final String EXT = ".jpg";
    private static final String DIR = "assets";
    private static final String DEL = ", ";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss zzz");

 

    public static List<Movie> parse() throws IOException, XMLStreamException {
        List<Movie> movies = new ArrayList<>();
        HttpURLConnection con = UrlConnectionFactory.getHttpUrlConnection(RSS_URL);
        try (InputStream is = con.getInputStream()) {
            XMLEventReader reader = ParserFactory.createStaxParser(is);


            Optional<TagType> tagType = Optional.empty();
            Movie movie = null;
            StartElement startElement = null;
            while (reader.hasNext()) {
                XMLEvent event = reader.nextEvent();
                switch (event.getEventType()) {
                    case XMLStreamConstants.START_ELEMENT -> {
                        startElement = event.asStartElement();
                        String qName = startElement.getName().getLocalPart();
                        tagType = TagType.from(qName);
                        if (tagType.isPresent() && tagType.get().equals(TagType.ITEM)) {
                            movie = new Movie();
                            movies.add(movie);
                        }
                    }
                    case XMLStreamConstants.CHARACTERS -> {
                        if (tagType.isPresent() && movie != null) {
                            Characters characters = event.asCharacters();
                            String data = characters.getData().trim();
                            switch (tagType.get()) {
                                case TITLE -> {
                                    if (!data.isEmpty()) {
                                        movie.setTitle(data);
                                    }
                                }
                                case LINK -> {
                                    if (!data.isEmpty()) {
                                        movie.setLink(data);
                                    }
                                }
                                case DESCRIPTION -> {
                                    if (!data.isEmpty()) {
                                        
                                        Document docs = Jsoup.parse(data);
                                        String desc = docs.text();
                                        movie.setDescription(desc);
                                    }
                                }
                                case ORIGINALTITLE ->{
                                    if(!data.isEmpty()){
                                        movie.setOriginalTitle(data);
                                    }
                                }
                                case DIRECTOR -> {
                                    if (!data.isEmpty()) {
                                        movie.setDirectors(handleData(data, Director::new));
                                    }
                                }
                                case ACTORS -> {
                                    if (!data.isEmpty()) {
                                        movie.setActors(handleData(data, Actor::new));
                                    }
                                }
                                case LENGTH -> {
                                    if (!data.isEmpty()) {
                                        movie.setDuration(data);
                                    }
                                }
                                case GENRE -> {
                                    if (!data.isEmpty()) {
                                        movie.setGenres(handleData(data, Genre::new));
                                    }
                                }
                                case PUBLISHED -> {
                                    if (!data.isEmpty()) {
                                        movie.setPubDate(LocalDateTime.parse(data, formatter));
                                    }
                                }
                                case EXPECTED -> {
                                    if (!data.isEmpty()) {
                                        movie.setExpected(LocalDate.parse(data, movie.DATE_FORMAT));
                                    }
                                }
                                case POSTER -> {
                                    if (!data.isEmpty() && movie.getPosterPath()== null) {
                                        handlePicture(movie, data);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return movies;
        }
    }
    
    private static <T> List<T> handleData(String data, Function<String, T> constructor) {
    List<T> objects = new ArrayList<>();
    String[] dataItems = data.split(", ");
    for (String item : dataItems) {
        T object = constructor.apply(item);
        objects.add(object);
    }
    return objects;
}
 

    private static void handlePicture(Movie movie, String pictureUrl) {
        try {
            String ext = pictureUrl.substring(pictureUrl.lastIndexOf("."));
            if (ext.length() > 4) {
                ext = EXT;
            }
            String pictureName = UUID.randomUUID() + ext;
            String localPicturePath = DIR + File.separator + pictureName;

 

            FileUtils.copyFromUrl(pictureUrl, localPicturePath);
            movie.setPosterPath(localPicturePath);
        } catch (IOException ex) {
            Logger.getLogger(MovieParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

 

    private enum TagType {
        ITEM("item"),
        TITLE("title"),
        LINK("link"),
        DESCRIPTION("description"),
        ORIGINALTITLE("orignaziv"),
        DIRECTOR("redatelj"),
        ACTORS("glumci"),
        LENGTH("trajanje"),
        GENRE("zanr"),
        PUBLISHED("pubDate"),
        EXPECTED("datumprikazivanja"),
        POSTER("plakat");

 

        private final String name;

 

        TagType(String name) {
            this.name = name;
        }

 

        private static Optional<TagType> from(String name) {
            for (TagType value : values()) {
                if (value.name.equals(name)) {
                    return Optional.of(value);
                }
            }
            return Optional.empty();
        }
    }
}
    
