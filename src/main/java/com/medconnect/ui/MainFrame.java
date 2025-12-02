
package com.medconnect.ui;

import com.medconnect.model.*;
import com.medconnect.repository.*;
import com.medconnect.service.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

// Integración final
public class MainFrame extends JFrame {
    private final PacienteService pacienteService;
    private final MedicoService medicoService;
    private final CitaService citaService;
    private final boolean soloLectura;

 
    private DefaultTableModel pacientesModel;
    private DefaultTableModel medicosModel;
    private DefaultTableModel citasModel;

    public MainFrame(boolean soloLectura) {
        this.soloLectura = soloLectura;
        
        this.pacienteService = new PacienteService(new PacienteRepositoryJDBC());
        this.medicoService = new MedicoService(new MedicoRepositoryJDBC());
        this.citaService = new CitaService(new CitaRepositoryJDBC());

        setTitle("MedConnect - Panel");
        setSize(800,600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
        loadData();
    }

    private void initUI() {
        JTabbedPane tabs = new JTabbedPane();
        tabs.add("Pacientes & Médicos", buildGestionPanel());
        tabs.add("Gestión de Citas", buildCitasPanel());

        if (soloLectura) {
            
        }

        add(tabs);
    }

    private JPanel buildGestionPanel() {
        JPanel panel = new JPanel(new GridLayout(1,2));

  
        JPanel pPac = new JPanel(new BorderLayout());
        pacientesModel = new DefaultTableModel(new Object[]{"ID","Nombre","Documento","Teléfono"},0);
        JTable tblPac = new JTable(pacientesModel);
        pPac.add(new JScrollPane(tblPac), BorderLayout.CENTER);

        JPanel pacBtns = new JPanel();
        JTextField tfNombre = new JTextField(10), tfDoc = new JTextField(8), tfTel = new JTextField(8);
        pacBtns.add(new JLabel("Nombre:")); pacBtns.add(tfNombre);
        pacBtns.add(new JLabel("Doc:")); pacBtns.add(tfDoc);
        pacBtns.add(new JLabel("Tel:")); pacBtns.add(tfTel);

        JButton btnAddPac = new JButton("Agregar Paciente");
        btnAddPac.addActionListener(e -> {
            try {
                Paciente p = new Paciente(tfNombre.getText(), tfDoc.getText(), tfTel.getText());
                pacienteService.guardarPaciente(p);
                loadPacientes();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });
        pacBtns.add(btnAddPac);
        pPac.add(pacBtns, BorderLayout.SOUTH);

    
        JPanel pMed = new JPanel(new BorderLayout());
        medicosModel = new DefaultTableModel(new Object[]{"ID","Nombre","Especialidad"},0);
        JTable tblMed = new JTable(medicosModel);
        pMed.add(new JScrollPane(tblMed), BorderLayout.CENTER);

        JPanel medBtns = new JPanel();
        JTextField tfMedNombre = new JTextField(10), tfEsp = new JTextField(8);
        medBtns.add(new JLabel("Nombre:")); medBtns.add(tfMedNombre);
        medBtns.add(new JLabel("Esp:")); medBtns.add(tfEsp);

        JButton btnAddMed = new JButton("Agregar Médico");
        btnAddMed.addActionListener(e -> {
            try {
                Medico m = new Medico(tfMedNombre.getText(), tfEsp.getText());
                medicoService.guardarMedico(m);
                loadMedicos();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });
        medBtns.add(btnAddMed);
        pMed.add(medBtns, BorderLayout.SOUTH);

        panel.add(pPac);
        panel.add(pMed);
        return panel;
    }

    private JPanel buildCitasPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel top = new JPanel();
        JComboBox<Medico> cbMedicos = new JComboBox<>();
        JComboBox<Paciente> cbPacientes = new JComboBox<>();
        JTextField tfFecha = new JTextField(8); // formato yyyy-mm-dd
        JTextField tfHora = new JTextField(6); // formato HH:mm
        JButton btnRegistrar = new JButton("Registrar Cita");
        top.add(new JLabel("Médico:")); top.add(cbMedicos);
        top.add(new JLabel("Paciente:")); top.add(cbPacientes);
        top.add(new JLabel("Fecha:")); top.add(tfFecha);
        top.add(new JLabel("Hora:")); top.add(tfHora);
        top.add(btnRegistrar);

        citasModel = new DefaultTableModel(new Object[]{"ID","Paciente","Medico","Fecha","Hora","Tipo"},0);
        JTable tblCitas = new JTable(citasModel);
        panel.add(top, BorderLayout.NORTH);
        panel.add(new JScrollPane(tblCitas), BorderLayout.CENTER);

        // acciones
        btnRegistrar.addActionListener(e -> {
            if (soloLectura) {
                JOptionPane.showMessageDialog(this, "Modo lectura: no puede registrar citas.");
                return;
            }
            try {
                Medico m = (Medico) cbMedicos.getSelectedItem();
                Paciente p = (Paciente) cbPacientes.getSelectedItem();
                Date fecha = Date.valueOf(LocalDate.parse(tfFecha.getText()));
                Time hora = Time.valueOf(LocalTime.parse(tfHora.getText() + ":00"));
                CitaPresencial cita = new CitaPresencial(p, m, fecha, hora, "");
                citaService.registrarCita(cita);
                loadCitasForMedicoAndDate(cbMedicos, fecha);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

      
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                // noop
            }
        });

        Runnable fill = () -> {
            try {
                cbMedicos.removeAllItems();
                cbPacientes.removeAllItems();
                for (Medico m : medicoService.listarMedicos()) cbMedicos.addItem(m);
                for (Paciente p : pacienteService.listarPacientes()) cbPacientes.addItem(p);
            } catch (Exception ex) { ex.printStackTrace(); }
        };
        
        fill.run();

       
        cbMedicos.addActionListener(ev -> {
            try {
                Medico sel = (Medico) cbMedicos.getSelectedItem();
                if (sel != null) {
                    Date fecha = Date.valueOf(LocalDate.now());
                    loadCitasForMedicoAndDate(cbMedicos, fecha);
                }
            } catch (Exception ex) { /* ignore */ }
        });

        return panel;
    }

    private void loadData() {
        try {
            loadPacientes();
            loadMedicos();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error cargando datos: " + e.getMessage());
        }
    }

    private void loadPacientes() throws Exception {
        pacientesModel.setRowCount(0);
        List<Paciente> lst = pacienteService.listarPacientes();
        for (Paciente p : lst) {
            pacientesModel.addRow(new Object[]{p.getId(), p.getNombre(), p.getDocumento(), p.getTelefono()});
        }
    }

    private void loadMedicos() throws Exception {
        medicosModel.setRowCount(0);
        List<Medico> lst = medicoService.listarMedicos();
        for (Medico m : lst) {
            medicosModel.addRow(new Object[]{m.getId(), m.getNombre(), m.getEspecialidad()});
        }
    }

    private void loadCitasForMedicoAndDate(JComboBox<Medico> cbMed, java.sql.Date fecha) throws Exception {
        citasModel.setRowCount(0);
        Medico m = (Medico) cbMed.getSelectedItem();
        if (m == null) return;
        List<ICita> citas = citaService.listarCitasPorMedicoYFecha(m, fecha);
        for (ICita c : citas) {
            citasModel.addRow(new Object[]{c.getId(), c.getPaciente().getNombre(), c.getMedico().getNombre(), c.getFecha(), c.getHora(), c.getTipo()});
        }
    }
}
