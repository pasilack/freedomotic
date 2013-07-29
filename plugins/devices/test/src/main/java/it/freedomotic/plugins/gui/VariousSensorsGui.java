/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * VariousSensorsForm.java
 *
 * Created on 4-lug-2010, 10.40.19
 */
package it.freedomotic.plugins.gui;

import it.freedomotic.app.Freedomotic;

import it.freedomotic.environment.EnvironmentPersistence;

import it.freedomotic.events.LuminosityEvent;
import it.freedomotic.events.ProtocolRead;
import it.freedomotic.events.TemperatureEvent;

import it.freedomotic.plugins.VariousSensors;

import it.freedomotic.reactions.Command;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Enrico
 */
public class VariousSensorsGui
        extends javax.swing.JFrame {

    VariousSensors sensor;

    /**
     * Creates new form VariousSensorsForm
     */
    public VariousSensorsGui(VariousSensors sensors) {
        this.sensor = sensors;
        initComponents();
        sldLuminosity.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (!sldLuminosity.getValueIsAdjusting()) {
                    notifyLuminosityEvent(sldLuminosity.getValue());
                    updateDescription("Selected temperature is " + sldTemperature.getValue()
                            + "°C. Selected Luminosity is " + sldLuminosity.getValue() + "%.");
                }
            }
        });

        sldTemperature.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (!sldTemperature.getValueIsAdjusting()) {
                    notifyTemperatureEvent(sldTemperature.getValue());
                    updateDescription("Selected temperature is " + sldTemperature.getValue()
                            + "°C. Selected Luminosity is " + sldLuminosity.getValue() + "%.");
                }
            }
        });
    }

    public void updateDescription(String text) {
        txtResult.setText(text);
    }

    private void notifyLuminosityEvent(int luminosity) {
        LuminosityEvent luminosityEvent =
                new LuminosityEvent(this, luminosity,
                EnvironmentPersistence.getEnvironments().get(0).getPojo().getZone(0));
        sensor.notifyEvent(luminosityEvent);
    }

    private void notifyTemperatureEvent(int temperature) {
        TemperatureEvent temperatureEvent =
                new TemperatureEvent(this, temperature,
                EnvironmentPersistence.getEnvironments().get(0).getPojo().getZone(0));
        sensor.notifyEvent(temperatureEvent);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings( "unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents(  )
    {
        jLabel1 = new javax.swing.JLabel(  );
        sldLuminosity = new javax.swing.JSlider(  );
        jLabel2 = new javax.swing.JLabel(  );
        sldTemperature = new javax.swing.JSlider(  );
        txtResult = new javax.swing.JLabel(  );
        btnCancel = new javax.swing.JButton(  );
        jButton1 = new javax.swing.JButton(  );
        lblAsk = new javax.swing.JLabel(  );
        jButton2 = new javax.swing.JButton(  );
        jLabel3 = new javax.swing.JLabel(  );
        btnJoinDevice = new javax.swing.JButton(  );
        jLabel4 = new javax.swing.JLabel(  );
        btnBypassTriggerSystem = new javax.swing.JButton(  );
        jLabel5 = new javax.swing.JLabel(  );

        setDefaultCloseOperation( javax.swing.WindowConstants.DISPOSE_ON_CLOSE );
        setTitle( "Simulate Sensors Variables" );

        jLabel1.setText( "Luminosity (0% to 100%):" );

        sldLuminosity.setMajorTickSpacing( 5 );
        sldLuminosity.setMinorTickSpacing( 1 );
        sldLuminosity.setPaintTicks( true );
        sldLuminosity.setSnapToTicks( true );
        sldLuminosity.setValue( 70 );
        sldLuminosity.addChangeListener( new javax.swing.event.ChangeListener(  )
            {
                public void stateChanged( javax.swing.event.ChangeEvent evt )
                {
                    sldLuminosityStateChanged( evt );
                }
            } );

        jLabel2.setText( "Temperature (-10°C to +40°C)" );

        sldTemperature.setMajorTickSpacing( 5 );
        sldTemperature.setMaximum( 40 );
        sldTemperature.setMinimum( -10 );
        sldTemperature.setMinorTickSpacing( 1 );
        sldTemperature.setPaintTicks( true );
        sldTemperature.setSnapToTicks( true );
        sldTemperature.setValue( 22 );

        txtResult.setFont( new java.awt.Font( "Tahoma", 1, 14 ) ); // NOI18N

        btnCancel.setText( "Cancel" );
        btnCancel.addActionListener( new java.awt.event.ActionListener(  )
            {
                public void actionPerformed( java.awt.event.ActionEvent evt )
                {
                    btnCancelActionPerformed( evt );
                }
            } );

        jButton1.setText( "Do it" );
        jButton1.addActionListener( new java.awt.event.ActionListener(  )
            {
                public void actionPerformed( java.awt.event.ActionEvent evt )
                {
                    jButton1ActionPerformed( evt );
                }
            } );

        lblAsk.setText( "Use 'User Input' plugin to ask someting to the user" );

        jButton2.setText( "Do it" );
        jButton2.addActionListener( new java.awt.event.ActionListener(  )
            {
                public void actionPerformed( java.awt.event.ActionEvent evt )
                {
                    jButton2ActionPerformed( evt );
                }
            } );

        jLabel3.setText( "Use 'Plugin remote controller' to show or hide a plugin gui" );

        btnJoinDevice.setText( "Do it" );
        btnJoinDevice.addActionListener( new java.awt.event.ActionListener(  )
            {
                public void actionPerformed( java.awt.event.ActionEvent evt )
                {
                    btnJoinDeviceActionPerformed( evt );
                }
            } );

        jLabel4.setText( "Add a new object using 'JoinDevice' command" );

        btnBypassTriggerSystem.setText( "Do it" );
        btnBypassTriggerSystem.addActionListener( new java.awt.event.ActionListener(  )
            {
                public void actionPerformed( java.awt.event.ActionEvent evt )
                {
                    btnBypassTriggerSystemActionPerformed( evt );
                }
            } );

        jLabel5.setText( "Change an object behavior bypassing trigger system" );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout( getContentPane(  ) );
        getContentPane(  ).setLayout( layout );
        layout.setHorizontalGroup( layout.createParallelGroup( javax.swing.GroupLayout.Alignment.LEADING ).addGroup( layout.createSequentialGroup(  )
                                                                                                                           .addContainerGap(  )
                                                                                                                           .addGroup( layout.createParallelGroup( javax.swing.GroupLayout.Alignment.LEADING )
                                                                                                                                            .addGroup( layout.createParallelGroup( javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                                                                                                   false )
                                                                                                                                                             .addGroup( layout.createSequentialGroup(  )
                                                                                                                                                                              .addGroup( layout.createParallelGroup( javax.swing.GroupLayout.Alignment.LEADING )
                                                                                                                                                                                               .addComponent( jLabel1 )
                                                                                                                                                                                               .addComponent( jLabel2 ) )
                                                                                                                                                                              .addPreferredGap( javax.swing.LayoutStyle.ComponentPlacement.RELATED )
                                                                                                                                                                              .addGroup( layout.createParallelGroup( javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                                                                                                                                     false )
                                                                                                                                                                                               .addComponent( sldTemperature,
                                                                                                                                                                                                              javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                                                              javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                                                              Short.MAX_VALUE )
                                                                                                                                                                                               .addComponent( sldLuminosity,
                                                                                                                                                                                                              javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                                                              462,
                                                                                                                                                                                                              Short.MAX_VALUE ) ) )
                                                                                                                                                             .addGroup( layout.createSequentialGroup(  )
                                                                                                                                                                              .addComponent( btnCancel )
                                                                                                                                                                              .addPreferredGap( javax.swing.LayoutStyle.ComponentPlacement.RELATED )
                                                                                                                                                                              .addComponent( txtResult,
                                                                                                                                                                                             javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                                             javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                                             Short.MAX_VALUE ) )
                                                                                                                                                             .addGroup( layout.createSequentialGroup(  )
                                                                                                                                                                              .addGroup( layout.createParallelGroup( javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                                                                                                                                     false )
                                                                                                                                                                                               .addComponent( jButton2,
                                                                                                                                                                                                              javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                                                              javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                                                              Short.MAX_VALUE )
                                                                                                                                                                                               .addComponent( jButton1,
                                                                                                                                                                                                              javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                                                              javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                                                              Short.MAX_VALUE ) )
                                                                                                                                                                              .addGap( 18,
                                                                                                                                                                                       18,
                                                                                                                                                                                       18 )
                                                                                                                                                                              .addGroup( layout.createParallelGroup( javax.swing.GroupLayout.Alignment.LEADING )
                                                                                                                                                                                               .addComponent( lblAsk,
                                                                                                                                                                                                              javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                                                              javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                                                              Short.MAX_VALUE )
                                                                                                                                                                                               .addComponent( jLabel3,
                                                                                                                                                                                                              javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                                                              615,
                                                                                                                                                                                                              Short.MAX_VALUE ) ) ) )
                                                                                                                                            .addGroup( layout.createParallelGroup( javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                                                                                                   false )
                                                                                                                                                             .addGroup( 
                                                                                                                                                                        javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                                                                                        layout.createSequentialGroup(  )
                                                                                                                                                                              .addComponent( btnBypassTriggerSystem,
                                                                                                                                                                                             javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                                             44,
                                                                                                                                                                                             javax.swing.GroupLayout.PREFERRED_SIZE )
                                                                                                                                                                              .addGap( 18,
                                                                                                                                                                                       18,
                                                                                                                                                                                       18 )
                                                                                                                                                                              .addComponent( jLabel5,
                                                                                                                                                                                             javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                                             javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                                             Short.MAX_VALUE ) )
                                                                                                                                                             .addGroup( 
                                                                                                                                                                        javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                                                                                        layout.createSequentialGroup(  )
                                                                                                                                                                              .addComponent( btnJoinDevice )
                                                                                                                                                                              .addGap( 18,
                                                                                                                                                                                       18,
                                                                                                                                                                                       18 )
                                                                                                                                                                              .addComponent( jLabel4,
                                                                                                                                                                                             javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                                             406,
                                                                                                                                                                                             javax.swing.GroupLayout.PREFERRED_SIZE ) ) ) )
                                                                                                                           .addContainerGap( 43,
                                                                                                                                             Short.MAX_VALUE ) ) );
        layout.setVerticalGroup( layout.createParallelGroup( javax.swing.GroupLayout.Alignment.LEADING )
                                       .addGroup( layout.createSequentialGroup(  ).addContainerGap(  )
                                                        .addGroup( layout.createParallelGroup( javax.swing.GroupLayout.Alignment.TRAILING )
                                                                         .addComponent( jLabel1 )
                                                                         .addComponent( sldLuminosity,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE ) )
                                                        .addGap( 16, 16, 16 )
                                                        .addGroup( layout.createParallelGroup( javax.swing.GroupLayout.Alignment.TRAILING )
                                                                         .addComponent( jLabel2 )
                                                                         .addComponent( sldTemperature,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE ) )
                                                        .addGap( 18, 18, 18 )
                                                        .addGroup( layout.createParallelGroup( javax.swing.GroupLayout.Alignment.BASELINE )
                                                                         .addComponent( jButton1 ).addComponent( lblAsk ) )
                                                        .addPreferredGap( javax.swing.LayoutStyle.ComponentPlacement.RELATED )
                                                        .addGroup( layout.createParallelGroup( javax.swing.GroupLayout.Alignment.BASELINE )
                                                                         .addComponent( jButton2 ).addComponent( jLabel3 ) )
                                                        .addPreferredGap( javax.swing.LayoutStyle.ComponentPlacement.RELATED )
                                                        .addGroup( layout.createParallelGroup( javax.swing.GroupLayout.Alignment.BASELINE )
                                                                         .addComponent( btnJoinDevice )
                                                                         .addComponent( jLabel4 ) )
                                                        .addPreferredGap( javax.swing.LayoutStyle.ComponentPlacement.RELATED )
                                                        .addGroup( layout.createParallelGroup( javax.swing.GroupLayout.Alignment.BASELINE )
                                                                         .addComponent( btnBypassTriggerSystem )
                                                                         .addComponent( jLabel5 ) )
                                                        .addPreferredGap( javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                          112, Short.MAX_VALUE )
                                                        .addGroup( layout.createParallelGroup( javax.swing.GroupLayout.Alignment.TRAILING )
                                                                         .addComponent( btnCancel )
                                                                         .addComponent( txtResult,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                        24,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE ) )
                                                        .addContainerGap(  ) ) );

        pack(  );
    } // </editor-fold>//GEN-END:initComponents

    private void sldLuminosityStateChanged(javax.swing.event.ChangeEvent evt) {
//GEN-FIRST:event_sldLuminosityStateChanged
    }//GEN-LAST:event_sldLuminosityStateChanged

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt)    {//GEN-FIRST:event_btnCancelActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt)    {//GEN-FIRST:event_jButton1ActionPerformed
        sensor.askSomething();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt)    {//GEN-FIRST:event_jButton2ActionPerformed

        Command c = new Command();
        c.setName("Ask user test");
        c.setReceiver("app.actuators.plugins.controller.in");
        c.setProperty("plugin", "Automations Editor");
        c.setProperty("action", "show"); //the default choice

        Freedomotic.sendCommand(c);
        txtResult.setText("Command sent, the GUI should be visible now.");
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btnJoinDeviceActionPerformed(java.awt.event.ActionEvent evt)    {//GEN-FIRST:event_btnJoinDeviceActionPerformed

        Command c = new Command();
        c.setName("Join Custom Light Object");
        c.setReceiver("app.objects.create");
        c.setProperty("object.class", "Light");
        c.setProperty("object.name", "Created with JoinDevice");
        c.setProperty("object.protocol", "test");
        c.setProperty("object.address", "test");

        Freedomotic.sendCommand(c);
        txtResult.setText("Command sent, the new object should be on the map");
    }//GEN-LAST:event_btnJoinDeviceActionPerformed

    private void btnBypassTriggerSystemActionPerformed(java.awt.event.ActionEvent evt)    {//GEN-FIRST:event_btnBypassTriggerSystemActionPerformed

        ProtocolRead event = new ProtocolRead(this, "test", "test");
        event.addProperty("behavior.name", "powered");
        event.addProperty("behaviorValue", "true");
        Freedomotic.sendEvent(event);
    }//GEN-LAST:event_btnBypassTriggerSystemActionPerformed
      // Variables declaration - do not modify//GEN-BEGIN:variables

    private javax.swing.JButton btnBypassTriggerSystem;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnJoinDevice;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel lblAsk;
    private javax.swing.JSlider sldLuminosity;
    private javax.swing.JSlider sldTemperature;
    private javax.swing.JLabel txtResult;

    // End of variables declaration//GEN-END:variables
}
