/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package hr.algebra.view;

import hr.algebra.dal.Repository;
import hr.algebra.dal.RepositoryFactory;
import hr.algebra.model.Actor;
import hr.algebra.model.Director;
import hr.algebra.model.Genre;
import hr.algebra.model.Movie;
import hr.algebra.parser.rss.MovieParser;
import hr.algebra.utilities.MessageUtils;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.SwingUtilities;

/**
 *
 * @author antoanne
 */
public class UploadMoviePanel extends javax.swing.JPanel {

    private Repository repository;
    private DefaultListModel<Movie> model;

    /**
     * Creates new form EditMoviePanel
     */
    public UploadMoviePanel() {
        initComponents();
        init();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu3 = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();
        jMenu5 = new javax.swing.JMenu();
        btnUpload = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        lsMovies = new javax.swing.JList<>();
        btnDelete = new javax.swing.JButton();
        lbLoadingText = new javax.swing.JLabel();

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        jMenu3.setText("File");
        jMenuBar2.add(jMenu3);

        jMenu4.setText("Edit");
        jMenuBar2.add(jMenu4);

        jMenu5.setText("jMenu5");

        setPreferredSize(new java.awt.Dimension(640, 580));

        btnUpload.setText("Upload");
        btnUpload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUploadActionPerformed(evt);
            }
        });

        jScrollPane2.setViewportView(lsMovies);

        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 638, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(122, 122, 122)
                        .addComponent(btnUpload)
                        .addGap(207, 207, 207)
                        .addComponent(btnDelete))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(256, 256, 256)
                        .addComponent(lbLoadingText, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 475, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnUpload)
                    .addComponent(btnDelete))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbLoadingText)
                .addGap(0, 36, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnUploadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUploadActionPerformed

//        try {
//            List<Movie> allMovies = MovieParser.parse();
//
//            for (Movie movie : allMovies) {
//                try {
//                    int idMovie = repository.createMovie(movie);
//
//                    addDirectors(movie, idMovie);
//                    addActors(movie, idMovie);
//                    addGenres(movie, idMovie);
//                } catch (Exception ex) {
//                    continue;
//                }
//            }
//            loadModel();
//
//            MessageUtils.showInformationMessage("Upload", "Movies inserted to database");
//        } catch (Exception ex) {
//            Logger.getLogger(UploadMoviePanel.class.getName()).log(Level.SEVERE, null, ex);
//        }

        Thread uploadThread = new Thread(() -> {
            SwingUtilities.invokeLater(() -> {
                lbLoadingText.setText("Loading data, please wait.....");
                btnUpload.setEnabled(false); // Disable the upload button while loading
                btnDelete.setEnabled(false); // Disable the delete button while loading
            });

            try {
                List<Movie> allMovies = MovieParser.parse();

                for (Movie movie : allMovies) {
                    try {
                        int idMovie = repository.createMovie(movie);

                        addDirectors(movie, idMovie);
                        addActors(movie, idMovie);
                        addGenres(movie, idMovie);
                    } catch (Exception ex) {
                        continue;
                    }
                }

                SwingUtilities.invokeLater(() -> {
                    loadModel();
                    MessageUtils.showInformationMessage("Upload", "Movies inserted into the database");
                });
            } catch (Exception ex) {
                Logger.getLogger(UploadMoviePanel.class.getName()).log(Level.SEVERE, null, ex);
                SwingUtilities.invokeLater(() -> {
                    MessageUtils.showErrorMessage("Upload", "An error occurred during the upload process");
                });
            } finally {
                SwingUtilities.invokeLater(() -> {
                    lbLoadingText.setText("");
                    btnUpload.setEnabled(true);
                    btnDelete.setEnabled(true);
                });
            }
        });

        uploadThread.start();
    }

    private void addDirectors(Movie movie, int idMovie) throws Exception {
        if (movie.getDirectors() != null) {
            for (Director director : movie.getDirectors()) {
                Optional<Director> dir = repository.selectDirectorByName(director);
                if (dir.isPresent()) {
                    Director existingDirector = dir.get();
                    repository.addMovieDirector(idMovie, existingDirector.getIdDirector());
                } else {
                    repository.createNewDirector(director.getDirectorName());
                }
            }
        }
    }

    private void addActors(Movie movie, int idMovie) throws Exception {
        if (movie.getActors() != null) {
            for (Actor actor : movie.getActors()) {
                Optional<Actor> act = repository.selectActorByName(actor);
                if (act.isPresent()) {
                    Actor existingActor = act.get();
                    repository.addMovieActor(idMovie, existingActor.getIdActor());
                } else {
                    repository.createNewActor(actor.getActorName());
                }
            }
        }
    }

    private void addGenres(Movie movie, int idMovie) throws Exception {
        if (movie.getGenres() != null) {
            for (Genre genre : movie.getGenres()) {
                Optional<Genre> g = repository.selectGenreByName(genre);
                if (g.isPresent()) {
                    Genre existingGenre = g.get();
                    repository.addMovieGenre(idMovie, existingGenre.getIdGenre());
                } else {
                    repository.createNewGenre(genre.getGenreName());
                }
            }
        }
    }//GEN-LAST:event_btnUploadActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        try {
            repository.removeAllMoviesFromDatabase();
            loadModel();
        } catch (Exception ex) {
            Logger.getLogger(UploadMoviePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnDeleteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnUpload;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lbLoadingText;
    private javax.swing.JList<Movie> lsMovies;
    // End of variables declaration//GEN-END:variables

    private void init() {

        initRepository();
        initListModel();
        loadModel();
    }

    private void initRepository() {
        repository = RepositoryFactory.getRepository();
    }

    private void initListModel() {
        model = new DefaultListModel<>();
    }

    private void loadModel() {
        List<Movie> moviesList;
        try {
            moviesList = repository.selectMovies();
            model.clear();
            moviesList.forEach(model::addElement);
            lsMovies.setModel((model));
        } catch (Exception ex) {
            Logger.getLogger(UploadMoviePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
