/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.view.model;

import hr.algebra.model.Movie;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author antoanne
 */
public class MovieTableModel extends AbstractTableModel{
    
    private static final String[] COLUMNS = {
        "Id",
        "Title",
        "PubDate",
        "Description",
        "OriginalTitle",
        "Duration",
        "PosterPath",
        "Link",
        "Expected"
    };
    
    private List<Movie> movies;

    public MovieTableModel(List<Movie> movies) {
        this.movies = movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return movies.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMNS.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return movies.get(rowIndex).getIdMovie();
            case 1:
                return movies.get(rowIndex).getTitle();
            case 2:
                return movies.get(rowIndex).getPubDate().format(Movie.DATE_FORMATTER);
            case  3:
                return movies.get(rowIndex).getDescription();
            case 4:
                return movies.get(rowIndex).getOriginalTitle();
            case 5:
                return movies.get(rowIndex).getDuration();
            case 6:
                return movies.get(rowIndex).getPosterPath();
            case 7:
                return movies.get(rowIndex).getLink();
            case 8:
                return movies.get(rowIndex).getExpected().format(Movie.DATE_FORMAT);
            default:
                throw new RuntimeException("Column does not exists");
        }
    }
    
    @Override
    public String getColumnName(int column) {
        return COLUMNS[column];
    }
    
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return Integer.class;
        }
        return super.getColumnClass(columnIndex);
    }
}
