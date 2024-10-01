package com.homeinpatagonia.crud;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.List;

import com.homeinpatagonia.conexion.DatabaseConnection;
import com.homeinpatagonia.model.Movie;

// Formulario para crear una nueva película
public class CrearPeliculaForm extends JPanel {
    private JTextField tituloField, directorField, anioField, duracionField, generoField;
    private JButton crearButton;

    public CrearPeliculaForm() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        tituloField = new JTextField(20);
        directorField = new JTextField(20);
        anioField = new JTextField(4);
        duracionField = new JTextField(4);
        generoField = new JTextField(20);
        crearButton = new JButton("Crear Película");

        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Título:"), gbc);
        gbc.gridx = 1;
        add(tituloField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Director:"), gbc);
        gbc.gridx = 1;
        add(directorField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Año:"), gbc);
        gbc.gridx = 1;
        add(anioField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        add(new JLabel("Duración (min):"), gbc);
        gbc.gridx = 1;
        add(duracionField, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        add(new JLabel("Género:"), gbc);
        gbc.gridx = 1;
        add(generoField, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(crearButton, gbc);

        crearButton.addActionListener(e -> crearPelicula());
    }

    private void crearPelicula() {
        Movie movie = new Movie();
        movie.setTitulo(tituloField.getText());
        movie.setDirector(directorField.getText());
        movie.setAnno(Integer.parseInt(anioField.getText()));
        movie.setDuracion(Integer.parseInt(duracionField.getText()));
        movie.setGeneros(List.of(generoField.getText().split(",")));

        try {
            DatabaseConnection.getInstance().insertMovie(movie);
            JOptionPane.showMessageDialog(this, "Película creada con éxito");
            limpiarCampos();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al crear la película: " + ex.getMessage());
        }
    }

    private void limpiarCampos() {
        tituloField.setText("");
        directorField.setText("");
        anioField.setText("");
        duracionField.setText("");
        generoField.setText("");
    }
}