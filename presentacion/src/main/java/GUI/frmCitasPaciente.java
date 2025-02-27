/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

import DTO.CitaDTO;
import DTO.PacienteNDTO;
import DTO.UsuarioNDTO;
import Exception.NegocioException;
import Exception.PersistenciaException;
import configuracion.DependencyInjector;
import entidades.Horario;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author erika
 */
public final class frmCitasPaciente extends javax.swing.JFrame {

    /**
     * Creates new form frmCitasPaciente
     */
     public final String nombrePaciente;
    public frmCitasPaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
        
        initComponents();
        mostrarCitas();
    }
    public void mostrarCitas (){
         try {
             UsuarioNDTO usuarioRecuperado = DependencyInjector.consultarUsuario().recuperarUsuarioPorNombre(nombrePaciente);
             int idUsuario = usuarioRecuperado.getId();
             PacienteNDTO paciente = DependencyInjector.crearPacienteBO().recuperarPacienteID(idUsuario);
              List<CitaDTO> Citas = DependencyInjector.actualizarPaciente().obtenerCitasPendientes(idUsuario);
              if(Citas.isEmpty()){
                  txtCitas.append("No tiene citas proximas");
              }else{
                  txtCitas.setText("");
                  for (CitaDTO cita : Citas) {
                    txtCitas.append(cita.getFecha()+" "+cita.getTipoCita()+" "+cita.getIdMedico()+"\n");
            }
              }
             
         } catch (NegocioException | PersistenciaException | SQLException ex) {
             Logger.getLogger(frmCitasPaciente.class.getName()).log(Level.SEVERE, null, ex);
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
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtCitas = new javax.swing.JTextArea();
        btnAgregarCita = new javax.swing.JButton();
        btnEliminarCita = new javax.swing.JButton();
        btnRegresar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(183, 213, 229));
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(183, 213, 229));

        lblTitulo.setFont(new java.awt.Font("Century Gothic", 0, 48)); // NOI18N
        lblTitulo.setText("Mis Citas");

        jLabel1.setFont(new java.awt.Font("Century Gothic", 0, 18)); // NOI18N
        jLabel1.setText("proximas citas: ");

        txtCitas.setEditable(false);
        txtCitas.setColumns(20);
        txtCitas.setRows(5);
        jScrollPane1.setViewportView(txtCitas);

        btnAgregarCita.setBackground(new java.awt.Color(0, 0, 0));
        btnAgregarCita.setForeground(new java.awt.Color(255, 255, 255));
        btnAgregarCita.setText("Agregar");
        btnAgregarCita.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAgregarCitaMouseClicked(evt);
            }
        });

        btnEliminarCita.setBackground(new java.awt.Color(0, 0, 0));
        btnEliminarCita.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminarCita.setText("Eliminar");
        btnEliminarCita.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEliminarCitaMouseClicked(evt);
            }
        });

        btnRegresar.setBackground(new java.awt.Color(0, 0, 0));
        btnRegresar.setForeground(new java.awt.Color(255, 255, 255));
        btnRegresar.setText("Regresar");
        btnRegresar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnRegresarMouseClicked(evt);
            }
        });
        btnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(315, 315, 315)
                        .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(128, 128, 128)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 564, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(310, 310, 310)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnAgregarCita, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnEliminarCita, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(btnRegresar, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(162, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitulo)
                .addGap(46, 46, 46)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addComponent(btnEliminarCita)
                .addGap(18, 18, 18)
                .addComponent(btnAgregarCita)
                .addGap(12, 12, 12)
                .addComponent(btnRegresar)
                .addGap(42, 42, 42))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnRegresarActionPerformed

    private void btnRegresarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRegresarMouseClicked
        frmPantallaPrinicipalPaciente pantallaCliente = new frmPantallaPrinicipalPaciente();
        pantallaCliente.setVisible(true);
        pantallaCliente.setNombrePaciente(nombrePaciente);
        this.setVisible(false);
    }//GEN-LAST:event_btnRegresarMouseClicked

    private void btnEliminarCitaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarCitaMouseClicked
       frmEliminarCita eliminarCita = new frmEliminarCita(nombrePaciente);
       eliminarCita.setVisible(true);
       this.setVisible(false);
    }//GEN-LAST:event_btnEliminarCitaMouseClicked

    private void btnAgregarCitaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAgregarCitaMouseClicked
        frmAgregarCita agregarCita = new frmAgregarCita(nombrePaciente);
        agregarCita.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnAgregarCitaMouseClicked

 

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarCita;
    private javax.swing.JButton btnEliminarCita;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JTextArea txtCitas;
    // End of variables declaration//GEN-END:variables
}
