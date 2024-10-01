package com.homeinpatagonia.gui;

import javax.swing.*;
import java.awt.*;

import com.homeinpatagonia.crud.CrearPeliculaForm;
import com.homeinpatagonia.crud.BuscarPeliculaForm;
import com.homeinpatagonia.crud.ActualizarPeliculaForm;
import com.homeinpatagonia.crud.EliminarPeliculaForm;
import com.homeinpatagonia.crud.ListarPeliculasForm;
import java.awt.event.ActionListener;

public class MovieManagementGUI extends JFrame {
    private JToolBar toolBar;
    private JPanel mainPanel;
    private CardLayout cardLayout;

    public MovieManagementGUI() {
        setTitle("Gestión de Películas");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        createToolBar();
        createMainPanel();
        
        add(toolBar, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
    }

    private void createToolBar() {
        toolBar = new JToolBar();
        toolBar.setFloatable(false);

        addToolBarButton("Crear", e -> showForm("crear"));
        addToolBarButton("Buscar", e -> showForm("buscar"));
        addToolBarButton("Actualizar", e -> showForm("actualizar"));
        addToolBarButton("Eliminar", e -> showForm("eliminar"));
        addToolBarButton("Listar Todas", e -> showForm("listar"));
    }

    private void addToolBarButton(String text, ActionListener listener) {
        JButton button = new JButton(text);
        button.addActionListener(listener);
        toolBar.add(button);
    }

    private void createMainPanel() {
        mainPanel = new JPanel();
        cardLayout = new CardLayout();
        mainPanel.setLayout(cardLayout);

        // Añadir los formularios al panel principal
        mainPanel.add(new CrearPeliculaForm(), "crear");
        mainPanel.add(new BuscarPeliculaForm(), "buscar");
        mainPanel.add(new ActualizarPeliculaForm(), "actualizar");
        mainPanel.add(new EliminarPeliculaForm(), "eliminar");
        mainPanel.add(new ListarPeliculasForm(), "listar");

        // Pantalla de bienvenida
        JPanel welcomePanel = new JPanel();
        welcomePanel.setLayout(new BorderLayout());
        JLabel welcomeLabel = new JLabel("Bienvenido al Sistema de Gestión de Películas", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomePanel.add(welcomeLabel, BorderLayout.CENTER);
        mainPanel.add(welcomePanel, "welcome");

        // Mostrar la pantalla de bienvenida inicialmente
        cardLayout.show(mainPanel, "welcome");
    }

    private void showForm(String formName) {
        cardLayout.show(mainPanel, formName);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MovieManagementGUI gui = new MovieManagementGUI();
            gui.setVisible(true);
        });
    }
}

