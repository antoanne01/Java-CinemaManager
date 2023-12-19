/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.dal;

import hr.algebra.model.Actor;
import hr.algebra.model.Director;
import hr.algebra.model.Genre;
import hr.algebra.model.Movie;
import hr.algebra.model.User;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author antoanne
 */

public interface Repository{
    
    int checkUserExists(String userName) throws Exception;
    int createUser(String username, String password, int role) throws Exception;
    int createUser(User user) throws Exception;
    
    Optional<User> selectUser(String userName) throws Exception;
    
    int createMovie(Movie movie) throws Exception;
    void createMovies(List<Movie> movies) throws Exception;
    void updateMovie(int id, Movie data) throws Exception;
    void deleteMovie(int id) throws Exception;
    Optional<Movie> selectMovie(int id) throws Exception;
    Optional<Movie> selectMovie(String title) throws Exception;
    List<Movie> selectMovies() throws Exception;
    void removeAllMoviesFromDatabase() throws Exception;
    
    void createNewGenre(String genreName) throws Exception;
    void createGenres(List<Genre> genres) throws Exception;
    void updateGenre(int idGenre, String genreName) throws Exception;
    void deleteGenre(int id) throws Exception;
    Optional<Genre> selectGenreName(int id) throws Exception;
    Optional<Genre> selectGenreByName(Genre genre) throws Exception;
    List<Genre> selectGernes(int selectedMovieId) throws Exception;
    List<Genre> selectAllGernes() throws Exception;
    
    Optional<User> loginUser(String uName, String pwd) throws Exception;
    
    void createNewActor(String actorName) throws Exception;
    void createActors(List<Actor> actors) throws Exception;
    void updateActor(int idActor, String ActorName) throws Exception;
    void deleteActor(int id) throws Exception;
    Optional<Actor> selectActor(int id) throws Exception;
    Optional<Actor> selectActorByName(Actor actor) throws Exception;
    List<Actor> selectActors(int selectedMovieId) throws Exception;
    List<Actor> selectAllActors() throws Exception;
    
    
    void createNewDirector(String directorName) throws Exception;
    void createDirectors(List<Director> directors) throws Exception;
    void updateDirector(int idDirector, String directorName) throws Exception;
    void deleteDirector(int id) throws Exception;
    Optional<Director> selectDirector(int id) throws Exception;
    Optional<Director> selectDirectorByName(Director director) throws Exception;
    List<Director> selectDirectors(int selectedMovieId) throws Exception;
    List<Director> selectAllDirectors() throws Exception;

    void addMovieDirector(int idMovie, int idDirector) throws Exception;
    void addMovieActor(int idMovie, int idActor) throws Exception;
    void addMovieGenre(int idMovie, int idGenre) throws Exception;

    void updateMovieDirector(int idMovie, int idDirector) throws Exception;
    void updateMovieActor(int idMovie, int idActor) throws Exception;
    void updateMovieGenre(int idMovie, int idGenre) throws Exception;

    void removeActorsFromMovieActor(int idMovie) throws Exception;
    void removeDirectorsFromMovieDirector(int idMovie) throws Exception;
    void removeGenresFromMovieGenre(int idMovie) throws Exception;

    void deleteSelectedMovie(int idMovie) throws Exception;

    int checkActorRelatesToAMovie(int idActor) throws Exception;
    int checkDirectorRelatesToAMovie(int idDirector) throws Exception;
    int checkGenreRelatesToAMovie(int idGenre) throws Exception;

}