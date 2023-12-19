/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.dal.sql;

import hr.algebra.dal.Repository;
import hr.algebra.model.Actor;
import hr.algebra.model.Director;
import hr.algebra.model.Genre;
import hr.algebra.model.Movie;
import hr.algebra.model.User;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.mindrot.jbcrypt.BCrypt;

public class SqlRepository implements Repository {

    private static final String ID_USER = "IDUser";
    private static final String USERNAME = "Username";
    private static final String PASSWORD = "Password";
    private static final String ROLE_ID = "RoleID";
    private static final String CHECK_USER_EXISTS = "{ CALL checkUserExists (?,?) }";
    private static final String CREATE_USER = "{ CALL createUser (?,?,?) }";
    private static final String SELECT_USER = "{ CALL selectUser (?) }";

    private static final String ID_MOVIE = "IDMovie";
    private static final String TITLE = "Title";
    private static final String PUB_DATE = "PubDate";
    private static final String DESCRIPTION = "Description";
    private static final String ORIGINAL_TITLE = "OriginalTitle";
    private static final String DURATION = "Duration";
    private static final String POSTER_PATH = "PosterPath";
    private static final String LINK = "Link";
    private static final String EXPECTED = "Expected";

    private static final String LOGIN_USER = "{CALL loginUser (?)}";

    private static final String CREATE_MOVIE = "{ CALL createMovie (?,?,?,?,?,?,?,?,?) }";
    private static final String UPDATE_MOVIE = "{ CALL updateMovie (?,?,?,?,?,?,?,?,?) }";
    private static final String DELETE_MOVIE = "{ CALL deleteMovie (?) }";
    private static final String SELECT_MOVIE = "{ CALL selectMovie (?) }";
    private static final String SELECT_MOVIE_BY_TITLE = "{ CALL selectMovieByTitle (?) }";
    
    private static final String SELECT_MOVIES = "{ CALL selectMovies }";
    private static final String DELETE_ALL_MOVIES = "{ CALL removeAllMoviesFromDatabase }";

    private static final String ID_DIRECTOR = "IDDirector";
    private static final String DIRECTOR_NAME = "DirectorName";
    private static final String CREATE_DIRECTOR = "{ CALL createDirectors (?,?,?) }";
    private static final String CREATE_NEW_DIRECTOR = "{ CALL createNewDirector (?) }";
    private static final String UPDATE_DIRECTOR = "{ CALL updateDirector (?,?) }";
    private static final String DELETE_DIRECTOR = "{ CALL deleteDirector (?) }";
    private static final String SELECT_DIRECTOR = "{ CALL selectDirector (?) }";
    private static final String SELECT_DIRECTOR_NAME = "{ CALL selectDirectorName (?) }";
    private static final String SELECT_DIRECTORS = "{ CALL selectDirectors (?) }";
    private static final String SELECT_ALL_DIRECTORS = "{ CALL selectAllDirectors }";
    private static final String CHECK_DIRECTOR_RELATION = "{ CALL checkDirectorRelatesToAMovie (?,?) }";

    private static final String ID_ACTOR = "IDActor";
    private static final String ACTOR_NAME = "ActorName";
    private static final String CREATE_ACTOR = "{ CALL createActors (?,?,?) }";
    private static final String CREATE_NEW_ACTOR = "{ CALL createNewActor (?) }";
    private static final String UPDATE_ACTOR = "{ CALL updateActor (?,?) }";
    private static final String DELETE_ACTOR = "{ CALL deleteActor (?) }";
    private static final String SELECT_ACTOR = "{ CALL selectActor (?) }";
    private static final String SELECT_ACTOR_NAME = "{ CALL selectActorName (?) }";
    private static final String SELECT_ACTORS = "{ CALL selectActors (?) }";
    private static final String SELECT_ALL_ACTORS = "{ CALL selectAllActors  }";
    private static final String CHECK_ACTOR_RELATION = "{ CALL checkActorRelatesToAMovie (?,?) }";

    private static final String ID_GENRE = "IDGenre";
    private static final String GENRE_NAME = "GenreName";
    private static final String CREATE_GERNE = "{ CALL createGenres (?,?,?) }";
    private static final String CREATE_NEW_GENRE = "{ CALL createNewGenre (?) }";
    private static final String UPDATE_GERNE = "{ CALL updateGenre (?,?) }";
    private static final String DELETE_GERNE = "{ CALL deleteGenre (?) }";
    private static final String SELECT_GERNE = "{ CALL selectGerne (?) }";
    private static final String SELECT_GENRE_NAME = "{ CALL selectGenreName (?) }";
    private static final String SELECT_GENRES = "{ CALL selectGernes (?) }";
    private static final String SELECT_ALL_GENRES = "{ CALL selectAllGernes }";
    private static final String CHECK_GENRE_RELATION = "{ CALL checkGenreRelatesToAMovie (?,?) }";

    private static final String ADD_MOVIE_DIRECTOR = "{ CALL addMovieDirector (?,?) }";
    private static final String ADD_MOVIE_ACTOR = "{ CALL addMovieActor (?,?) }";
    private static final String ADD_MOVIE_GENRE = "{ CALL addMovieGenre (?,?) }";

    private static final String UPDATE_MOVIE_DIRECTOR = "{ CALL updateMovieDirector (?,?) }";
    private static final String UPDATE_MOVIE_ACTOR = "{ CALL updateMovieActor (?,?) }";
    private static final String UPDATE_MOVIE_GENRE = "{ CALL updateMovieGenre (?,?) }";

    private static final String REMOVE_DIRECTORS_MOVIE_DIRECTOR = "{ CALL removeDirectorsFromMovieDirector (?) }";
    private static final String REMOVE_ACTORS_MOVIE_ACTOR = "{ CALL removeActorsFromMovieActor (?) }";
    private static final String REMOVE_GENRES_MOVIE_GENRE = "{ CALL removeGenresFromMovieGenre (?) }";

    private static final String DELETE_SELECTED_MOVIE = "{ CALL deleteSelectedMovie (?) }";

    //User
    @Override
    public int checkUserExists(String userName) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CHECK_USER_EXISTS)) {
            stmt.setString(1, userName);
            stmt.registerOutParameter(2, Types.INTEGER);
            stmt.executeUpdate();
            return stmt.getInt(2);
        }
    }

    @Override
    public int createUser(String username, String password, int role) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_USER)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setInt(3, role);
            stmt.registerOutParameter(4, Types.INTEGER);
            stmt.executeUpdate();
            return stmt.getInt(4);
        }
    }

    @Override
    public int createUser(User user) throws Exception {

        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_USER)) {
            stmt.setString(USERNAME, user.getUserName());
            stmt.setString(PASSWORD, user.getPassword());

            stmt.registerOutParameter(ID_USER, Types.INTEGER);
            stmt.executeUpdate();

            return stmt.getInt(ID_USER);
        }
    }

    @Override
    public Optional<User> selectUser(String userName) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection conn = dataSource.getConnection(); CallableStatement stmt = conn.prepareCall(SELECT_USER)) {

            stmt.setString(1, userName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new User(
                            rs.getInt(ID_USER),
                            rs.getString(USERNAME),
                            rs.getString(PASSWORD),
                            rs.getInt(ROLE_ID)
                    ));
                }
            }
        }
        return Optional.empty();
    }
    
    ///////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public List<Movie> selectMovies() throws Exception {
        List<Movie> movies = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_MOVIES); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                movies.add(new Movie(
                        rs.getInt(ID_MOVIE),
                        rs.getString(TITLE),
                        LocalDateTime.parse(rs.getString(PUB_DATE), Movie.DATE_FORMATTER),
                        rs.getString(DESCRIPTION),
                        rs.getString(ORIGINAL_TITLE),
                        rs.getString(DURATION),
                        rs.getString(POSTER_PATH),
                        rs.getString(LINK),
                        LocalDate.parse(rs.getString(EXPECTED), Movie.DATE_FORMAT)
                ));
            }
            return movies;
        }
    }

    // Movie from RSS 
    @Override
    public int createMovie(Movie movie) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_MOVIE)) {
            stmt.setString(1, movie.getTitle());
            stmt.setString(2, movie.getPubDate().format(Movie.DATE_FORMATTER));
            stmt.setString(3, movie.getDescription());
            stmt.setString(4, movie.getOriginalTitle());
            stmt.setString(5, movie.getDuration());
            stmt.setString(6, movie.getPosterPath());
            stmt.setString(7, movie.getLink());
            stmt.setString(8, movie.getExpected().format(Movie.DATE_FORMAT));
            stmt.registerOutParameter(9, Types.INTEGER);
            stmt.executeUpdate();
            return stmt.getInt(9);
        }
    }

    @Override
    public void createMovies(List<Movie> movies) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_MOVIE)) {
            for (Movie movie : movies) {
                stmt.setString(1, movie.getTitle());
                stmt.setString(2, movie.getPubDate().format(Movie.DATE_FORMATTER));
                stmt.setString(3, movie.getDescription());
                stmt.setString(4, movie.getOriginalTitle());
                stmt.setString(5, movie.getDuration());
                stmt.setString(6, movie.getPosterPath());
                stmt.setString(7, movie.getLink());
                stmt.setString(8, movie.getExpected().format(Movie.DATE_FORMAT));
                stmt.registerOutParameter(9, Types.INTEGER);
                stmt.executeUpdate();
                movie.setIdMovie(stmt.getInt(ID_MOVIE));

//                if(movie.getDirectors() != null){
//                    addMovieDirector(movie.getIdMovie(), movie.getDirectors());
//                }
            }
        }
    }

    //Checking if Director, Actor and Genre exists
    
    @Override
    public Optional<Director> selectDirectorByName(Director director) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection conn = dataSource.getConnection(); CallableStatement stmt = conn.prepareCall(SELECT_DIRECTOR_NAME)) {

            stmt.setString(1, director.getDirectorName());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Director(
                            rs.getInt(ID_DIRECTOR),
                            rs.getString(DIRECTOR_NAME)
                    ));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Actor> selectActorByName(Actor actor) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection conn = dataSource.getConnection(); CallableStatement stmt = conn.prepareCall(SELECT_ACTOR_NAME)) {
            stmt.setString(1, actor.getActorName());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Actor(
                            rs.getInt(ID_ACTOR),
                            rs.getString(ACTOR_NAME)
                    ));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Genre> selectGenreByName(Genre genre) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection conn = dataSource.getConnection(); CallableStatement stmt = conn.prepareCall(SELECT_GENRE_NAME)) {
            stmt.setString(1, genre.getGenreName());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Genre(
                            rs.getInt(ID_GENRE),
                            rs.getString(GENRE_NAME)
                    ));
                }
            }
        }
        return Optional.empty();
    }

    // Connected Director, Actor and Genre with Movie
    @Override
    public void addMovieDirector(int idMovie, int idDirector) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(ADD_MOVIE_DIRECTOR)) {
            stmt.setInt(1, idMovie);
            stmt.setInt(2, idDirector);
            stmt.executeUpdate();
        }
    }

    @Override
    public void addMovieActor(int idMovie, int idActor) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(ADD_MOVIE_ACTOR)) {
            stmt.setInt(1, idMovie);
            stmt.setInt(2, idActor);
            stmt.executeUpdate();
        }
    }

    @Override
    public void addMovieGenre(int idMovie, int idGenre) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(ADD_MOVIE_GENRE)) {
            stmt.setInt(1, idMovie);
            stmt.setInt(2, idGenre);
            stmt.executeUpdate();
        }
    }

    // Select Movie
    @Override
    public Optional<Movie> selectMovie(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection conn = dataSource.getConnection(); CallableStatement stmt = conn.prepareCall(SELECT_MOVIE)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Movie(
                            rs.getInt(ID_MOVIE),
                            rs.getString(TITLE),
                            LocalDateTime.parse(rs.getString(PUB_DATE), Movie.DATE_FORMATTER),
                            rs.getString(DESCRIPTION),
                            rs.getString(ORIGINAL_TITLE),
                            rs.getString(DURATION),
                            rs.getString(POSTER_PATH),
                            rs.getString(LINK),
                            LocalDate.parse(rs.getString(EXPECTED), Movie.DATE_FORMAT)
                    ));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Movie> selectMovie(String title) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection conn = dataSource.getConnection(); CallableStatement stmt = conn.prepareCall(SELECT_MOVIE_BY_TITLE)) {

            stmt.setString(1, title);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Movie(
                            rs.getInt(ID_MOVIE),
                            rs.getString(TITLE),
                            LocalDateTime.parse(rs.getString(PUB_DATE), Movie.DATE_FORMATTER),
                            rs.getString(DESCRIPTION),
                            rs.getString(ORIGINAL_TITLE),
                            rs.getString(DURATION),
                            rs.getString(POSTER_PATH),
                            rs.getString(LINK),
                            LocalDate.parse(rs.getString(EXPECTED), Movie.DATE_FORMAT)
                    ));
                }
            }
        }
        return Optional.empty();
    }

    //Select all Directors, Actors and Genres for Movie   
    @Override
    public List<Actor> selectActors(int selectedMovieId) throws Exception {
        List<Actor> actorsForSelectedMovie = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection conn = dataSource.getConnection(); CallableStatement stmt = conn.prepareCall(SELECT_ACTORS)) {
            stmt.setInt(1, selectedMovieId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    actorsForSelectedMovie.add(new Actor(
                            rs.getInt(ID_ACTOR),
                            rs.getString(ACTOR_NAME)
                    ));
                }
            }
        }
        return actorsForSelectedMovie;
    }

    @Override
    public List<Director> selectDirectors(int selectedMovieId) throws Exception {
        List<Director> directorsForSelectedMovie = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection conn = dataSource.getConnection(); CallableStatement stmt = conn.prepareCall(SELECT_DIRECTORS)) {
            stmt.setInt(1, selectedMovieId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    directorsForSelectedMovie.add(new Director(
                            rs.getInt(ID_DIRECTOR),
                            rs.getString(DIRECTOR_NAME)
                    ));
                }
            }
        }
        return directorsForSelectedMovie;
    }

    @Override
    public List<Genre> selectGernes(int selectedMovieId) throws Exception {
        List<Genre> genresForSelectedMovie = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection conn = dataSource.getConnection(); CallableStatement stmt = conn.prepareCall(SELECT_GENRES)) {
            stmt.setInt(1, selectedMovieId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    genresForSelectedMovie.add(new Genre(
                            rs.getInt(ID_GENRE),
                            rs.getString(GENRE_NAME)
                    ));
                }
            }
        }
        return genresForSelectedMovie;
    }

    // New Movie 
    @Override
    public List<Actor> selectAllActors() throws Exception {
        List<Actor> actors = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_ALL_ACTORS); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                actors.add(new Actor(
                        rs.getInt(ID_ACTOR),
                        rs.getString(ACTOR_NAME)
                ));
            }
            return actors;
        }
    }

    @Override
    public List<Director> selectAllDirectors() throws Exception {
        List<Director> directors = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_ALL_DIRECTORS); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                directors.add(new Director(
                        rs.getInt(ID_DIRECTOR),
                        rs.getString(DIRECTOR_NAME)
                ));
            }
            return directors;
        }
    }

    @Override
    public List<Genre> selectAllGernes() throws Exception {
        List<Genre> genres = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(SELECT_ALL_GENRES); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                genres.add(new Genre(
                        rs.getInt(ID_GENRE),
                        rs.getString(GENRE_NAME)
                ));
            }
            return genres;
        }
    }

    //Create new Actor for New Movie
    @Override
    public void createNewActor(String actorName) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_NEW_ACTOR)) {
            stmt.setString(1, actorName);
            stmt.executeUpdate();
        }
    }

    //Create new Director for New Movie
    @Override
    public void createNewDirector(String directorName) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_NEW_DIRECTOR)) {
            stmt.setString(1, directorName);
            stmt.executeUpdate();
        }
    }

    ///Create new Genre for New Movie
    @Override
    public void createNewGenre(String genreName) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_NEW_GENRE)) {
            stmt.setString(1, genreName);
            stmt.executeUpdate();
        }
    }

    //Edit - Update movie
    @Override
    public void updateMovie(int id, Movie data) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(UPDATE_MOVIE)) {
            stmt.setString(1, data.getTitle());
            stmt.setString(2, data.getPubDate().format(Movie.DATE_FORMATTER));
            stmt.setString(3, data.getDescription());
            stmt.setString(4, data.getOriginalTitle());
            stmt.setString(5, data.getDuration());
            stmt.setString(6, data.getPosterPath());
            stmt.setString(7, data.getLink());
            stmt.setString(8, data.getExpected().format(Movie.DATE_FORMAT));
            stmt.setInt(9, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public void updateMovieDirector(int idMovie, int idDirector) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(UPDATE_MOVIE_DIRECTOR)) {
            stmt.setInt(1, idMovie);
            stmt.setInt(2, idDirector);
            stmt.executeUpdate();
        }
    }

    @Override
    public void updateMovieActor(int idMovie, int idActor) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(UPDATE_MOVIE_ACTOR)) {
            stmt.setInt(1, idMovie);
            stmt.setInt(2, idActor);
            stmt.executeUpdate();
        }
    }

    @Override
    public void updateMovieGenre(int idMovie, int idGenre) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(UPDATE_MOVIE_GENRE)) {
            stmt.setInt(1, idMovie);
            stmt.setInt(2, idGenre);
            stmt.executeUpdate();
        }
    }

    // Edit Movie - Remove actors, directors, genres 
    @Override
    public void removeDirectorsFromMovieDirector(int idMovie) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(REMOVE_DIRECTORS_MOVIE_DIRECTOR)) {
            stmt.setInt(1, idMovie);
            stmt.executeUpdate();
        }
    }

    @Override
    public void removeActorsFromMovieActor(int idMovie) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(REMOVE_ACTORS_MOVIE_ACTOR)) {
            stmt.setInt(1, idMovie);
            stmt.executeUpdate();
        }
    }

    @Override
    public void removeGenresFromMovieGenre(int idMovie) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(REMOVE_GENRES_MOVIE_GENRE)) {
            stmt.setInt(1, idMovie);
            stmt.executeUpdate();
        }
    }

    // Delete selected Movie 
    @Override
    public void deleteSelectedMovie(int idMovie) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(DELETE_SELECTED_MOVIE)) {
            stmt.setInt(1, idMovie);
            stmt.executeUpdate();
        }
    }

    // EDIT PANEL - Update actor, director and genre
    @Override
    public void updateActor(int idActor, String actorName) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(UPDATE_ACTOR)) {
            stmt.setInt(1, idActor);
            stmt.setString(2, actorName);
            stmt.executeUpdate();
        }
    }

    @Override
    public void updateDirector(int idDirector, String directorName) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(UPDATE_DIRECTOR)) {
            stmt.setInt(1, idDirector);
            stmt.setString(2, directorName);
            stmt.executeUpdate();
        }
    }

    @Override
    public void updateGenre(int idGenre, String genreName) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(UPDATE_GERNE)) {
            stmt.setInt(1, idGenre);
            stmt.setString(2, genreName);
            stmt.executeUpdate();
        }
    }

    // Checking if actor, director and genre exists in MovieActor table 
    @Override
    public int checkActorRelatesToAMovie(int idActor) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CHECK_ACTOR_RELATION)) {
            stmt.setInt(1, idActor);
            stmt.registerOutParameter(2, Types.INTEGER);
            stmt.executeUpdate();
            return stmt.getInt(2);
        }
    }

    @Override
    public int checkDirectorRelatesToAMovie(int idDirector) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CHECK_DIRECTOR_RELATION)) {
            stmt.setInt(1, idDirector);
            stmt.registerOutParameter(2, Types.INTEGER);
            stmt.executeUpdate();
            return stmt.getInt(2);
        }
    }

    @Override
    public int checkGenreRelatesToAMovie(int idGenre) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CHECK_GENRE_RELATION)) {
            stmt.setInt(1, idGenre);
            stmt.registerOutParameter(2, Types.INTEGER);
            stmt.executeUpdate();
            return stmt.getInt(2);
        }
    }

    // Deleting actor, director and genre if is not related to a movie
    @Override
    public void deleteActor(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(DELETE_ACTOR)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteDirector(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(DELETE_DIRECTOR)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteGenre(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(DELETE_GERNE)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    @Override 
    public void deleteMovie(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(DELETE_MOVIE)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public void removeAllMoviesFromDatabase() throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(DELETE_ALL_MOVIES)) {
            stmt.executeUpdate();
        }
    }

    // Genre 
    @Override
    public void createGenres(List<Genre> genres) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_GERNE)) {

            for (Genre genre : genres) {
                stmt.setString(1, genre.getGenreName());
                stmt.registerOutParameter(2, Types.INTEGER);
                stmt.executeUpdate();
            }
        }
    }

    @Override
    public Optional<Genre> selectGenreName(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection conn = dataSource.getConnection(); CallableStatement stmt = conn.prepareCall(SELECT_GERNE)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Genre(
                            rs.getInt(ID_GENRE),
                            rs.getString(GENRE_NAME)
                    ));
                }
            }
        }

        return Optional.empty();
    }

    // Actor 
    @Override
    public void createActors(List<Actor> actors) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_ACTOR)) {

            for (Actor actor : actors) {
                stmt.setString(1, actor.getActorName());
                stmt.registerOutParameter(2, Types.INTEGER);
                stmt.executeUpdate();
            }
        }
    }

    @Override
    public Optional<Actor> selectActor(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection conn = dataSource.getConnection(); CallableStatement stmt = conn.prepareCall(SELECT_ACTOR)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Actor(
                            rs.getInt(ID_DIRECTOR),
                            rs.getString(DIRECTOR_NAME)
                    ));
                }
            }
        }

        return Optional.empty();
    }

    // Director
    @Override
    public void createDirectors(List<Director> directors) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection(); CallableStatement stmt = con.prepareCall(CREATE_DIRECTOR)) {

            for (Director director : directors) {
                stmt.setString(1, director.getDirectorName());
                stmt.registerOutParameter(2, Types.INTEGER);
                stmt.executeUpdate();
            }
        }
    }

    @Override
    public Optional<Director> selectDirector(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection conn = dataSource.getConnection(); CallableStatement stmt = conn.prepareCall(SELECT_DIRECTOR)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Director(
                            rs.getInt(ID_DIRECTOR),
                            rs.getString(DIRECTOR_NAME)
                    ));
                }
            }
        }

        return Optional.empty();
    }

    public Optional<User> loginUser(String Username, String Password) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection conn = dataSource.getConnection(); CallableStatement stmt = conn.prepareCall(LOGIN_USER)) {
            stmt.setString(USERNAME, Username);
            try (ResultSet rs = stmt.executeQuery();) {
                if (rs.next()) {
                    String hPassword = rs.getString(PASSWORD);
                    if (BCrypt.checkpw(Password, hPassword)) {
                        User user = new User(
                                rs.getInt(ID_USER),
                                rs.getString(USERNAME),
                                hPassword
                        );
                        return Optional.of(user);
                    }
                }
            }
        }
        return Optional.empty();
    }

}
