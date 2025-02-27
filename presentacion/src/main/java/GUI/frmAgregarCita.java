/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

import DTO.CitaDTO;
import DTO.HorarioDTO;
import DTO.MedicoDTO;
import DTO.PacienteNDTO;
import DTO.UsuarioNDTO;
import Exception.NegocioException;
import Exception.PersistenciaException;
import configuracion.DependencyInjector;
import entidades.Cita;
import entidades.Horario;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author erika
 */
public class frmAgregarCita extends javax.swing.JFrame {

    /**
     * Creates new form frmAgregarCita
     */
    public final String nombrePaciente;
    private final Map<String, MedicoDTO> medicosMap = new HashMap<>();
    public frmAgregarCita(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
        initComponents();
    }
    public void mostrarDoctores (String especialidad) throws NegocioException{
        List<MedicoDTO> medicos = DependencyInjector.agendarCita().obtenerDoctoresDisponibles(especialidad);
        if (medicos.isEmpty()) {
            System.out.println("No se encontraron doctores para la especialidad: " + especialidad);
        } else {
            System.out.println("Doctores encontrados: " + medicos.size());
        }
        // Limpiar el comboBox antes de agregar nuevos items
        cmbMedico.removeAllItems();
        for (MedicoDTO medico : medicos) {
            
            cmbMedico.addItem(medico.getNombre());
            medicosMap.put(medico.getNombre(), medico); // guardar los doctores para poder consultar sus datos despues
        }
    }
    
    public void mostrarHorarioMedico () throws NegocioException, PersistenciaException{
        String nombreSeleccionado = (String) cmbMedico.getSelectedItem();
        MedicoDTO medicoSeleccionado = medicosMap.get(nombreSeleccionado);
        List<HorarioDTO> horarios = DependencyInjector.consultarMedico().consultarHorarioMedico(medicoSeleccionado.getId());
        for (HorarioDTO horario : horarios){
            cmbDia.addItem(horario.getDiaSemana());
        }
        
    }
    private void actualizarIntervalos() {
        try {
            String nombreSeleccionado = (String) cmbMedico.getSelectedItem();
            MedicoDTO medicoSeleccionado = medicosMap.get(nombreSeleccionado);
            int idMedico = medicoSeleccionado.getId(); 
            Map<String, List<Time>> intervalosPorDia = DependencyInjector.consultarMedico().obtenerIntervalosMedico(idMedico);
            String diaSeleccionado = (String) cmbDia.getSelectedItem();
            List<Time> intervalos = intervalosPorDia.get(diaSeleccionado);

            // Formato para convertir Time a String
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

            // Limpiar y actualizar los intervalos en el combo box
            intervalosComboBox.removeAllItems();
            if (intervalos != null) {
            for (Time intervalo : intervalos) {
                intervalosComboBox.addItem(sdf.format(intervalo));
            }
            }
        } catch (PersistenciaException ex) {
            JOptionPane.showMessageDialog(null, "Error al obtener los intervalos: " + ex.getMessage());
        }
    }
    public LocalDate obtenerProximoDiaDeLaSemana(String diaSemana) {
        DayOfWeek diaActual = LocalDate.now().getDayOfWeek();
        DayOfWeek diaSeleccionado = DayOfWeek.valueOf(diaSemana.toUpperCase());

    // Calcular los días de diferencia entre hoy y el próximo día seleccionado
    int diasDiferencia = diaSeleccionado.getValue() - diaActual.getValue();
    if (diasDiferencia <= 0) {
        diasDiferencia += 7; // Asegurarse de que sea la próxima semana
    }

    return LocalDate.now().plusDays(diasDiferencia);
}
    public LocalDateTime crearLocalDateTime() {
        String diaSeleccionado = (String) cmbDia.getSelectedItem();
        String intervaloSeleccionado = (String) intervalosComboBox.getSelectedItem();

        LocalDate fechaDia = obtenerProximoDiaDeLaSemana(diaSeleccionado);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime tiempo = LocalTime.parse(intervaloSeleccionado, formatter);

        return LocalDateTime.of(fechaDia, tiempo);
    }
public void agendarCita() throws NegocioException {
    try {
        String nombreSeleccionado = (String) cmbMedico.getSelectedItem();
        MedicoDTO medicoSeleccionado = medicosMap.get(nombreSeleccionado);

        LocalDateTime fecha = crearLocalDateTime(); // Crear LocalDateTime con los datos del frame
        
        // Estado y tipo de cita (puedes ajustarlos según tu lógica)
        String estadoCita = "Pendiente";
        String tipoCita = "Regular";
        
        UsuarioNDTO usuarioRecuperado = DependencyInjector.consultarUsuario().recuperarUsuarioPorNombre(nombrePaciente);
        int idUsuario = usuarioRecuperado.getId();
        PacienteNDTO paciente = DependencyInjector.crearPacienteBO().recuperarPacienteID(idUsuario);

        // Crear una nueva cita
        CitaDTO nuevaCita = new CitaDTO(paciente.getIdUsuario(), medicoSeleccionado.getId(), fecha, estadoCita, tipoCita);

       boolean existeCita = DependencyInjector.agendarCita().existeCita(medicoSeleccionado.getId(), fecha);

        // Verificar si ya existe una cita en la misma fecha y hora para el mismo médico
        if (existeCita==true) {
            JOptionPane.showMessageDialog(null, "El médico ya tiene una cita agendada en la misma fecha y hora.", "Error al agendar cita", JOptionPane.ERROR_MESSAGE);
        } else {
            // Guardar la cita en la base de datos
            DependencyInjector.agendarCita().agregarCitaSimple(nuevaCita);
            JOptionPane.showMessageDialog(null, "Cita agendada.");
        }

    } catch (PersistenciaException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error al agendar la cita: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}



    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        lblEspecialidad = new javax.swing.JLabel();
        cmbEspecialidad = new javax.swing.JComboBox<>();
        lblDia = new javax.swing.JLabel();
        cmbDia = new javax.swing.JComboBox<>();
        lblHora = new javax.swing.JLabel();
        lblMedico = new javax.swing.JLabel();
        cmbMedico = new javax.swing.JComboBox<>();
        btnRegresar1 = new javax.swing.JButton();
        btnAgregar = new javax.swing.JButton();
        btnEsp = new javax.swing.JButton();
        btnDia = new javax.swing.JButton();
        btnHorario = new javax.swing.JButton();
        intervalosComboBox = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(183, 213, 229));
        jPanel1.setPreferredSize(new java.awt.Dimension(859, 510));

        lblTitulo.setFont(new java.awt.Font("Century Gothic", 0, 48)); // NOI18N
        lblTitulo.setText("Agregar Cita");

        lblEspecialidad.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        lblEspecialidad.setText("Especialidad:");

        cmbEspecialidad.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Medicina General", "Cirujia", "Cardiología", "Neurologia", "Ginecologia" }));

        lblDia.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        lblDia.setText("Dia:");

        lblHora.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        lblHora.setText("Hora:");

        lblMedico.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        lblMedico.setText("Medico:");

        btnRegresar1.setBackground(new java.awt.Color(0, 0, 0));
        btnRegresar1.setForeground(new java.awt.Color(255, 255, 255));
        btnRegresar1.setText("Regresar");
        btnRegresar1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnRegresar1MouseClicked(evt);
            }
        });
        btnRegresar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresar1ActionPerformed(evt);
            }
        });

        btnAgregar.setText("Agendar");
        btnAgregar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAgregarMouseClicked(evt);
            }
        });

        btnEsp.setText("buscar medicos");
        btnEsp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEspMouseClicked(evt);
            }
        });

        btnDia.setText("buscar dia");
        btnDia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDiaActionPerformed(evt);
            }
        });

        btnHorario.setText("buscar horario");
        btnHorario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnHorarioMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 193, Short.MAX_VALUE)
                .addComponent(btnRegresar1, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48)
                .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(282, 282, 282))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(348, 348, 348)
                        .addComponent(lblMedico))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(365, 365, 365)
                        .addComponent(lblDia))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(359, 359, 359)
                        .addComponent(lblHora))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(205, 205, 205)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(cmbEspecialidad, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblEspecialidad)
                            .addComponent(cmbMedico, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbDia, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(intervalosComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnEsp, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
                            .addComponent(btnDia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnHorario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(lblTitulo)
                .addGap(11, 11, 11)
                .addComponent(lblEspecialidad)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbEspecialidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEsp))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblMedico)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbMedico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDia))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblDia)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbDia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHorario))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblHora)
                .addGap(14, 14, 14)
                .addComponent(intervalosComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(104, 104, 104)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRegresar1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(53, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegresar1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRegresar1MouseClicked
        frmPantallaPrinicipalPaciente pantallaprincipal = new frmPantallaPrinicipalPaciente();
        pantallaprincipal.setNombrePaciente(nombrePaciente);
        pantallaprincipal.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnRegresar1MouseClicked

    private void btnRegresar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresar1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnRegresar1ActionPerformed

    private void btnEspMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEspMouseClicked
        try {
            String seleccion =(String) cmbEspecialidad.getSelectedItem();
            mostrarDoctores(seleccion);
        } catch (NegocioException ex) {
            Logger.getLogger(frmAgregarCita.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnEspMouseClicked

    private void btnDiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDiaActionPerformed
        try {
            mostrarHorarioMedico();
        } catch (NegocioException | PersistenciaException ex) {
            Logger.getLogger(frmAgregarCita.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnDiaActionPerformed

    private void btnHorarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHorarioMouseClicked
        actualizarIntervalos();
    }//GEN-LAST:event_btnHorarioMouseClicked

    private void btnAgregarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAgregarMouseClicked
        try {
            agendarCita();
            
        } catch (NegocioException ex) {
            Logger.getLogger(frmAgregarCita.class.getName()).log(Level.SEVERE, null, ex);
             JOptionPane.showMessageDialog(null, "Error agendar la cita: " + ex.getMessage());
        }
    }//GEN-LAST:event_btnAgregarMouseClicked

    /**
     * @param args the command line arguments
     */


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnDia;
    private javax.swing.JButton btnEsp;
    private javax.swing.JButton btnHorario;
    private javax.swing.JButton btnRegresar1;
    private javax.swing.JComboBox<String> cmbDia;
    private javax.swing.JComboBox<String> cmbEspecialidad;
    private javax.swing.JComboBox<String> cmbMedico;
    private javax.swing.JComboBox<String> intervalosComboBox;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblDia;
    private javax.swing.JLabel lblEspecialidad;
    private javax.swing.JLabel lblHora;
    private javax.swing.JLabel lblMedico;
    private javax.swing.JLabel lblTitulo;
    // End of variables declaration//GEN-END:variables
}
