/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * author antoanne
 */
@XmlAccessorType(XmlAccessType.FIELD)
public final class Movie {

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss.S");
    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @XmlElement(name = "idmovie")
    private int idMovie;
    private String title;
    @XmlJavaTypeAdapter(DataAdapter.class)
    @XmlElement(name = "pubdate")
    private LocalDateTime pubDate;
    @XmlElement(name = "description")
    private String description;
    @XmlElement(name = "originaltitle")
    private String originalTitle;

    @XmlElement(name = "director")
    private String director;
    @XmlElementWrapper

    private List<Director> directors;

    @XmlElement(name = "actor")
    private String actor;
    @XmlElementWrapper
    private List<Actor> actors;

    @XmlElement(name = "duration")
    private String duration;

    private String genre;
    @XmlElementWrapper
    @XmlElement(name = "genre")
    private List<Genre> genres;

    @XmlElement(name = "posterpath")
    private String posterPath;
    private String link;
    private LocalDate expected;

    public Movie() {
    }

    public Movie(String title, LocalDateTime pubDate, String description, String originalTitle, String duration, String posterPath, String link, LocalDate expected) {
        this.title = title;
        this.pubDate = pubDate;
        this.description = description;
        this.originalTitle = originalTitle;
        this.duration = duration;
        this.posterPath = posterPath;
        this.link = link;
        this.expected = expected;
    }

    public Movie(int idMovie, String title, LocalDateTime pubDate, String description, String originalTitle, String duration, String posterPath, String link, LocalDate expected) {
        this.idMovie = idMovie;
        this.title = title;
        this.pubDate = pubDate;
        this.description = description;
        this.originalTitle = originalTitle;
        this.duration = duration;
        this.posterPath = posterPath;
        this.link = link;
        this.expected = expected;
    }

    public Movie(int idMovie, String title, LocalDateTime pubDate, String description, String originalTitle, List<Director> directors, List<Actor> actors, String duration, List<Genre> genres, String posterPath, String link, LocalDate expected) {
        this.idMovie = idMovie;
        this.title = title;
        this.pubDate = pubDate;
        this.description = description;
        this.originalTitle = originalTitle;
        this.directors = directors;
        this.actors = actors;
        this.duration = duration;
        this.genres = genres;
        this.posterPath = posterPath;
        this.link = link;
        this.expected = expected;
    }

    public int getIdMovie() {
        return idMovie;
    }

    public void setIdMovie(int idMovie) {
        this.idMovie = idMovie;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getPubDate() {
        return pubDate;
    }

    public void setPubDate(LocalDateTime pubDate) {
        this.pubDate = pubDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public List<Director> getDirectors() {
        return directors;
    }

    public void setDirectors(List<Director> directors) {
        this.directors = directors;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public LocalDate getExpected() {
        return expected;
    }

    public void setExpected(LocalDate expected) {
        this.expected = expected;
    }

    @Override
    public String toString() {
        return idMovie + " - " + title;
    }
}
