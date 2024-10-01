package com.homeinpatagonia.conexion;

import com.homeinpatagonia.model.Movie;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.homeinpatagonia.observador.MovieDatabaseObservador;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/moviesdb";
    private static final String USER = "admin";
    private static final String PASSWORD = "jy{Sf*%$]C:0v1U}";

    private static DatabaseConnection instance;
    private Connection connection;
    
    private List<MovieDatabaseObservador> observers = new ArrayList<>();
    
    private DatabaseConnection() {
        createConnection();
    }

    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    private void createConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            }
        } catch (SQLException e) {
            System.out.println("ERROR EN LA CONEXION: " + e.getMessage());
        }
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                createConnection();
            }
        } catch (SQLException e) {
            System.out.println("ERROR AL VERIFICAR LA CONEXION: " + e.getMessage());
        }
        return connection;
    }

    public void addObserver(MovieDatabaseObservador observer) {
        observers.add(observer);
    }

    public void removeObserver(MovieDatabaseObservador observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        for (MovieDatabaseObservador observer : observers) {
            observer.onDatabaseChanged();
        }
    }

    // Este método se debe llamar cuando la aplicación se cierra
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
    
    public void insertMovie(Movie movie) throws SQLException {
        Connection conn = null;
        CallableStatement cstmt = null;
        try {
            conn = getConnection();
            conn.setAutoCommit(false);
            
            String sql = "{CALL insert_movie(?, ?, ?, ?, ?)}";
            cstmt = conn.prepareCall(sql);
            cstmt.setString(1, movie.getTitulo());
            cstmt.setString(2, movie.getDirector());
            cstmt.setInt(3, movie.getAnno());
            cstmt.setInt(4, movie.getDuracion());
            cstmt.setString(5, String.join(",", movie.getGeneros()));
            
            cstmt.execute();
            
            conn.commit();
            notifyObservers();
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            throw e;
        } finally {
            if (cstmt != null) {
                cstmt.close();
            }
            if (conn != null) {
                conn.setAutoCommit(true);
            }
        }
    }

    public Movie getMovie(int movieId) throws SQLException {
        Connection conn = null;
        CallableStatement cstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            String sql = "{CALL get_movie(?)}";
            cstmt = conn.prepareCall(sql);
            cstmt.setInt(1, movieId);
            
            rs = cstmt.executeQuery();
            if (rs.next()) {
                Movie movie = new Movie();
                movie.setId(rs.getInt("id"));
                movie.setTitulo(rs.getString("titulo"));
                movie.setDirector(rs.getString("director"));
                movie.setAnno(rs.getInt("anno"));
                movie.setDuracion(rs.getInt("duracion"));
                String generos = rs.getString("genero");
                if (generos != null) {
                    movie.setGeneros(Arrays.asList(generos.split(",")));
                }
                return movie;
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (cstmt != null) {
                cstmt.close();
            }
        }
        return null;
    }

    public void updateMovie(Movie movie) throws SQLException {
        Connection conn = null;
        CallableStatement cstmt = null;
        try {
            conn = getConnection();
            conn.setAutoCommit(false);
            
            String sql = "{CALL update_movie(?, ?, ?, ?, ?, ?)}";
            cstmt = conn.prepareCall(sql);
            cstmt.setInt(1, movie.getId());
            cstmt.setString(2, movie.getTitulo());
            cstmt.setString(3, movie.getDirector());
            cstmt.setInt(4, movie.getAnno());
            cstmt.setInt(5, movie.getDuracion());
            cstmt.setString(6, String.join(",", movie.getGeneros()));
            
            cstmt.execute();
            
            conn.commit();
            notifyObservers();
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            throw e;
        } finally {
            if (cstmt != null) {
                cstmt.close();
            }
            if (conn != null) {
                conn.setAutoCommit(true);
            }
        }
    }

    public void deleteMovie(int movieId) throws SQLException {
        Connection conn = null;
        CallableStatement cstmt = null;
        try {
            conn = getConnection();
            conn.setAutoCommit(false);
            
            String sql = "{CALL delete_movie(?)}";
            cstmt = conn.prepareCall(sql);
            cstmt.setInt(1, movieId);
            
            cstmt.execute();
            
            conn.commit();
            notifyObservers();
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            throw e;
        } finally {
            if (cstmt != null) {
                cstmt.close();
            }
            if (conn != null) {
                conn.setAutoCommit(true);
            }
        }
    }

    public List<Movie> getAllMovies() throws SQLException {
        List<Movie> movies = new ArrayList<>();
        Connection conn = null;
        CallableStatement cstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            String sql = "{CALL get_all_movies()}";
            cstmt = conn.prepareCall(sql);
            rs = cstmt.executeQuery();
            
            while (rs.next()) {
                Movie movie = new Movie();
                movie.setId(rs.getInt("id"));
                movie.setTitulo(rs.getString("titulo"));
                movie.setDirector(rs.getString("director"));
                movie.setAnno(rs.getInt("anno"));
                movie.setDuracion(rs.getInt("duracion"));
                String generos = rs.getString("genero");
                if (generos != null) {
                    movie.setGeneros(Arrays.asList(generos.split(",")));
                }
                movies.add(movie);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (cstmt != null) {
                cstmt.close();
            }
        }
        return movies;
    }
}