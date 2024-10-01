package com.homeinpatagonia.app;

import com.homeinpatagonia.gui.MovieManagementGUI;
import javax.swing.SwingUtilities;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    // Cargar el driver JDBC
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    
                    // Crear y mostrar la GUI
                    MovieManagementGUI gui = new MovieManagementGUI();
                    gui.setVisible(true);
                } catch (ClassNotFoundException e) {
                    System.err.println("Error al cargar el driver JDBC: " + e.getMessage());
                    e.printStackTrace();
                } catch (Exception e) {
                    System.err.println("Error al iniciar la aplicación: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }
}