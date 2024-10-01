package com.homeinpatagonia.crud;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.homeinpatagonia.conexion.DatabaseConnection;
import com.homeinpatagonia.model.Movie;

// Formulario para buscar una película
public class BuscarPeliculaForm extends JPanel {
    private JTextField buscarField;
    private JButton buscarButton;
    private JTextArea resultadoArea;

    public BuscarPeliculaForm() {
        setLayout(new BorderLayout());

        JPanel busquedaPanel = new JPanel();
        buscarField = new JTextField(20);
        buscarButton = new JButton("Buscar");
        busquedaPanel.add(new JLabel("Buscar:"));
        busquedaPanel.add(buscarField);
        busquedaPanel.add(buscarButton);

        resultadoArea = new JTextArea(10, 30);
        resultadoArea.setEditable(false);

        add(busquedaPanel, BorderLayout.NORTH);
        add(new JScrollPane(resultadoArea), BorderLayout.CENTER);

        buscarButton.addActionListener(e -> buscarPeliculas());
    }

    private void buscarPeliculas() {
        String searchTerm = buscarField.getText().trim();
        if (searchTerm.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese un término de búsqueda.");
            return;
        }

        try {
            List<Movie> movies = searchMovies(searchTerm);
            displayResults(movies);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al buscar películas: " + ex.getMessage());
        }
    }

    private List<Movie> searchMovies(String searchTerm) throws SQLException {
        List<Movie> movies = new ArrayList<>();
        Connection conn = DatabaseConnection.getInstance().getConnection();
        String sql = "{CALL search_movies(?)}";
        
        try (CallableStatement cstmt = conn.prepareCall(sql)) {
            cstmt.setString(1, searchTerm);
            try (ResultSet rs = cstmt.executeQuery()) {
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
            }
        }
        return movies;
    }

    private void displayResults(List<Movie> movies) {
        StringBuilder sb = new StringBuilder();
        if (movies.isEmpty()) {
            sb.append("No se encontraron películas.");
        } else {
            for (Movie movie : movies) {
                sb.append("ID: ").append(movie.getId())
                  .append(", Título: ").append(movie.getTitulo())
                  .append(", Director: ").append(movie.getDirector())
                  .append(", Año: ").append(movie.getAnno())
                  .append(", Duración: ").append(movie.getDuracion())
                  .append(", Géneros: ").append(String.join(", ", movie.getGeneros()))
                  .append("\n\n");
            }
        }
        resultadoArea.setText(sb.toString());
    }
}