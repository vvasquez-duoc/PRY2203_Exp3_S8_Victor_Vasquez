package com.homeinpatagonia.crud;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.List;

import com.homeinpatagonia.conexion.DatabaseConnection;
import com.homeinpatagonia.model.Movie;
import com.homeinpatagonia.observador.MovieDatabaseObservador;

// Formulario para listar todas las películas
public class ListarPeliculasForm extends JPanel implements MovieDatabaseObservador{
    private JTable peliculasTable;
    private JButton refreshButton;

    public ListarPeliculasForm() {
        setLayout(new BorderLayout());

        // Crear la tabla
        peliculasTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(peliculasTable);
        add(scrollPane, BorderLayout.CENTER);

        // Crear el botón de actualizar
        refreshButton = new JButton("Actualizar Lista");
        add(refreshButton, BorderLayout.SOUTH);

        refreshButton.addActionListener(e -> refreshList());

        // Registrar este formulario como observador
        DatabaseConnection.getInstance().addObserver(this);

        // Cargar los datos inicialmente
        refreshList();
    }
    
    @Override
    public void onDatabaseChanged() {
        refreshList();
    }

    private void refreshList() {
        try {
            List<Movie> movies = DatabaseConnection.getInstance().getAllMovies();
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("ID");
            model.addColumn("Título");
            model.addColumn("Director");
            model.addColumn("Año");
            model.addColumn("Duración");
            model.addColumn("Géneros");

            for (Movie movie : movies) {
                model.addRow(new Object[]{
                    movie.getId(),
                    movie.getTitulo(),
                    movie.getDirector(),
                    movie.getAnno(),
                    movie.getDuracion(),
                    String.join(", ", movie.getGeneros())
                });
            }

            peliculasTable.setModel(model);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar los datos: " + ex.getMessage());
        }
    }
}