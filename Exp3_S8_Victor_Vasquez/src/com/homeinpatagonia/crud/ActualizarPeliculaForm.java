package com.homeinpatagonia.crud;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.Arrays;

import com.homeinpatagonia.conexion.DatabaseConnection;
import com.homeinpatagonia.model.Movie;

// Formulario para actualizar una película
public class ActualizarPeliculaForm extends JPanel {
    private JTextField idField, tituloField, directorField, anioField, duracionField, generoField;
    private JButton buscarButton, actualizarButton;

    public ActualizarPeliculaForm() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        idField = new JTextField(5);
        tituloField = new JTextField(20);
        directorField = new JTextField(20);
        anioField = new JTextField(4);
        duracionField = new JTextField(4);
        generoField = new JTextField(20);
        buscarButton = new JButton("Buscar");
        actualizarButton = new JButton("Actualizar");

        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("ID:"), gbc);
        gbc.gridx = 1;
        add(idField, gbc);
        gbc.gridx = 2;
        add(buscarButton, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Título:"), gbc);
        gbc.gridx = 1;
        add(tituloField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Director:"), gbc);
        gbc.gridx = 1;
        add(directorField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        add(new JLabel("Año:"), gbc);
        gbc.gridx = 1;
        add(anioField, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        add(new JLabel("Duración (min):"), gbc);
        gbc.gridx = 1;
        add(duracionField, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        add(new JLabel("Género:"), gbc);
        gbc.gridx = 1;
        add(generoField, gbc);

        gbc.gridx = 1; gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.CENTER;
        add(actualizarButton, gbc);

        buscarButton.addActionListener(e -> buscarPelicula());
        actualizarButton.addActionListener(e -> actualizarPelicula());
    }

    private void buscarPelicula() {
        try {
            int id = Integer.parseInt(idField.getText());
            Movie movie = DatabaseConnection.getInstance().getMovie(id);
            if (movie != null) {
                tituloField.setText(movie.getTitulo());
                directorField.setText(movie.getDirector());
                anioField.setText(String.valueOf(movie.getAnno()));
                duracionField.setText(String.valueOf(movie.getDuracion()));
                generoField.setText(String.join(",", movie.getGeneros()));
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró la película con ID: " + id);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al buscar la película: " + ex.getMessage());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese un ID válido");
        }
    }

    private void actualizarPelicula() {
        Movie movie = new Movie();
        movie.setId(Integer.parseInt(idField.getText()));
        movie.setTitulo(tituloField.getText());
        movie.setDirector(directorField.getText());
        movie.setAnno(Integer.parseInt(anioField.getText()));
        movie.setDuracion(Integer.parseInt(duracionField.getText()));
        movie.setGeneros(Arrays.asList(generoField.getText().split(",")));
        
        try {
            DatabaseConnection.getInstance().updateMovie(movie);
            JOptionPane.showMessageDialog(this, "Película actualizada con éxito");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al actualizar la película: " + ex.getMessage());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese valores numéricos válidos para ID, Año y Duración");
        }
    }
}