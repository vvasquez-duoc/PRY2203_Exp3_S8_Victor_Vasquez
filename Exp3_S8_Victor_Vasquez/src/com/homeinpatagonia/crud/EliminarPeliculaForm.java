package com.homeinpatagonia.crud;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

import com.homeinpatagonia.conexion.DatabaseConnection;

// Formulario para eliminar una película
public class EliminarPeliculaForm extends JPanel {
    private JTextField idField;
    private JButton eliminarButton;

    public EliminarPeliculaForm() {
        setLayout(new FlowLayout());

        idField = new JTextField(10);
        eliminarButton = new JButton("Eliminar");

        add(new JLabel("ID de la película:"));
        add(idField);
        add(eliminarButton);

        eliminarButton.addActionListener(e -> eliminarPelicula());
    }

    private void eliminarPelicula() {
        try {
            int id = Integer.parseInt(idField.getText());
            
            int confirmar = JOptionPane.showConfirmDialog(this,
                    "¿Está seguro de que desea eliminar la película con ID " + id + "?",
                    "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
            
            if (confirmar == JOptionPane.YES_OPTION) {
                DatabaseConnection.getInstance().deleteMovie(id);
                JOptionPane.showMessageDialog(this, "Película eliminada con éxito");
                idField.setText("");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al eliminar la película: " + ex.getMessage());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese un ID válido");
        }
    }
}
