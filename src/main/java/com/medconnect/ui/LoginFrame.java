package com.medconnect.ui;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginFrame extends JFrame {
    public LoginFrame() {
        setTitle("MedConnect - Login");
        setSize(350,180);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
    }

    private void initUI() {
        JPanel p = new JPanel();
        p.setLayout(new GridLayout(4,2,5,5));
        p.add(new JLabel("Usuario:"));
        JTextField userField = new JTextField("recepcion");
        p.add(userField);
        p.add(new JLabel("Contraseña:"));
        JPasswordField passField = new JPasswordField();
        p.add(passField);

        JButton loginBtn = new JButton("Iniciar sesión");
        loginBtn.addActionListener((ActionEvent e) -> {
            // En este prototipo no se valida contra BD: usuario fijo
            String user = userField.getText();
            // si es "medico" abrimos modo médico (solo lectura); sino modo recepcionista (gestión)
            boolean esMedico = "medico".equalsIgnoreCase(user);
            MainFrame main = new MainFrame(esMedico);
            main.setVisible(true);
            this.dispose();
        });

        getContentPane().add(p, BorderLayout.CENTER);
        JPanel south = new JPanel();
        south.add(loginBtn);
        getContentPane().add(south, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginFrame().setVisible(true);
        });
    }
}
