/*
 * GNU License.
 */
package br.com.brenov.chatclient.view;

import br.com.brenov.chatclient.model.Language;
import br.com.brenov.chatclient.control.ClientHandler;
import java.awt.CardLayout;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;

/**
 * Chat window. This class is responsible for the GUI.
 *
 * @author Breno Viana
 * @version 23/07/2017
 */
public class ChatWindow extends JFrame {

    // Layouts names
    private final static String CHAT_SERVER_LOG_ON = "CHAT_SERVER_IP_ADDRESS";
    private final static String CHOOSE_CLIENT_NAME = "CHOOSE_CLENT_NAME";
    private final static String CHOOSE_CLIENT_LANGUAGE = "CHOOSE_CLENT_LANGUAGE";
    private final static String CHAT = "CHAT";

    // Languages
    private final Language[] languages = {
        Language.ENGLISH,
        Language.PORTUGUESE,
        Language.SPANISH,
        Language.FRENCH,
        Language.GERMAN,
        Language.ITALIAN,
        Language.DUTCH};
    private final String[] languagesName = {
        Language.ENGLISH.getName(),
        Language.PORTUGUESE.getName(),
        Language.SPANISH.getName(),
        Language.FRENCH.getName(),
        Language.GERMAN.getName(),
        Language.ITALIAN.getName(),
        Language.DUTCH.getName()};

    // Client handler
    private final ClientHandler handler;
    // Card layout
    private final CardLayout card;

    /**
     * Creates new form ChatWindow.
     *
     * @param handler Client handler
     */
    public ChatWindow(ClientHandler handler) {
        // Init handler
        this.handler = handler;
        initComponents();
        // Center window
        setLocationRelativeTo(this);
        // Init layout
        this.card = new CardLayout();
        this.jMainPanel.setLayout(this.card);
        this.jMainPanel.add(this.jLogOnServerPanel,
                CHAT_SERVER_LOG_ON);
        this.jMainPanel.add(this.jEnterTheNamePanel,
                CHOOSE_CLIENT_NAME);
        this.jMainPanel.add(this.jEnterTheLanguagePanel,
                CHOOSE_CLIENT_LANGUAGE);
        this.jMainPanel.add(this.jChatPanel, CHAT);
    }

    /**
     * Get the message text field.
     *
     * @return The message text field.
     */
    public JTextField getJMessageTextField() {
        return this.jMessageTextField;
    }

    /**
     * Get the messages text area.
     *
     * @return The messages text area.
     */
    public JTextPane getJConversation() {
        return this.jConversation;
    }

    /**
     * Set IP address of the server.
     */
    private void setIPAddress() {
        if (!this.jIPAddressTextField.getText().equals("")) {
            // Set server IP Address
            this.handler.setServerIPAddress(this.jIPAddressTextField.getText());
            // Change screen
            this.card.show(this.jMainPanel, CHOOSE_CLIENT_LANGUAGE);
            this.jClientLanguageComboBox.requestFocusInWindow();
        }
    }

    /**
     * Choose a language.
     */
    private void chooseLanguage() {
        // Set language
        this.handler.setLanguage(this.languages[this.jClientLanguageComboBox
                .getSelectedIndex()]);
        // Change screen
        this.card.show(this.jMainPanel, CHOOSE_CLIENT_NAME);
        this.jClientNameTextField.requestFocusInWindow();
    }

    /**
     * Log in to chat.
     */
    private void login() {
        try {
            // Set name
            this.handler.login(this.jClientNameTextField.getText());
            // Check if the client is ready
            if (this.handler.isReady()) {
                // Update screen
                setTitle("Understand Me: " + this.handler.getName());
                this.card.show(this.jMainPanel, CHAT);
                this.jMessageTextField.requestFocusInWindow();
            } else {
                // Name already chosen error
                JOptionPane.showMessageDialog(null, "This name has already "
                        + "been chosen, please choose a new name.");
            }
        } catch (InterruptedException ex) {
            System.err.println("Error in running Understand Me. "
                    + "Waiting time error.");
            Logger.getLogger(ChatWindow.class.getName())
                    .log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            System.err.println("Error in running Understand Me. "
                    + "The socket could not be created.");
            Logger.getLogger(ChatWindow.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Send message.
     */
    private void send() {
        if (!this.jMessageTextField.getText().equals("")) {
            // Send message
            this.handler.send(this.jMessageTextField.getText());
            this.jMessageTextField.setText("");
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

        jLogOnServerPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jIPAddressTextField = new javax.swing.JTextField();
        jConnectButton = new javax.swing.JButton();
        jEnterTheNamePanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jClientNameTextField = new javax.swing.JTextField();
        jConfirmNameButton = new javax.swing.JButton();
        jEnterTheLanguagePanel = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jConfirmLanguageButton = new javax.swing.JButton();
        jClientLanguageComboBox = new javax.swing.JComboBox<>(languagesName);
        jChatPanel = new javax.swing.JPanel();
        jMessageTextField = new javax.swing.JTextField();
        jSendButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jConversation = new javax.swing.JTextPane();
        jMainPanel = new javax.swing.JPanel();

        jLogOnServerPanel.setPreferredSize(new java.awt.Dimension(400, 300));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("<html>Enter IP Address of the <b>Understand Me</b> Server:</html>");

        jIPAddressTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jIPAddressTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jIPAddressTextFieldActionPerformed(evt);
            }
        });

        jConnectButton.setText("Connect");
        jConnectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jConnectButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jLogOnServerPanelLayout = new javax.swing.GroupLayout(jLogOnServerPanel);
        jLogOnServerPanel.setLayout(jLogOnServerPanelLayout);
        jLogOnServerPanelLayout.setHorizontalGroup(
            jLogOnServerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLogOnServerPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jLogOnServerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel1)
                    .addComponent(jIPAddressTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jConnectButton, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jLogOnServerPanelLayout.setVerticalGroup(
            jLogOnServerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLogOnServerPanelLayout.createSequentialGroup()
                .addGap(106, 106, 106)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jIPAddressTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jConnectButton)
                .addContainerGap(107, Short.MAX_VALUE))
        );

        jEnterTheNamePanel.setPreferredSize(new java.awt.Dimension(400, 300));

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Enter the your name or what do you want to be called:");

        jClientNameTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jClientNameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jClientNameTextFieldActionPerformed(evt);
            }
        });

        jConfirmNameButton.setText("Ok");
        jConfirmNameButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jConfirmNameButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jEnterTheNamePanelLayout = new javax.swing.GroupLayout(jEnterTheNamePanel);
        jEnterTheNamePanel.setLayout(jEnterTheNamePanelLayout);
        jEnterTheNamePanelLayout.setHorizontalGroup(
            jEnterTheNamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jEnterTheNamePanelLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jEnterTheNamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jClientNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jConfirmNameButton, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 376, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jEnterTheNamePanelLayout.setVerticalGroup(
            jEnterTheNamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jEnterTheNamePanelLayout.createSequentialGroup()
                .addGap(106, 106, 106)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jClientNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jConfirmNameButton)
                .addGap(108, 108, 108))
        );

        jEnterTheLanguagePanel.setPreferredSize(new java.awt.Dimension(400, 300));

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Choose a language:");

        jConfirmLanguageButton.setText("Enter");
        jConfirmLanguageButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jConfirmLanguageButtonActionPerformed(evt);
            }
        });

        jClientLanguageComboBox.setMaximumRowCount(10);

        javax.swing.GroupLayout jEnterTheLanguagePanelLayout = new javax.swing.GroupLayout(jEnterTheLanguagePanel);
        jEnterTheLanguagePanel.setLayout(jEnterTheLanguagePanelLayout);
        jEnterTheLanguagePanelLayout.setHorizontalGroup(
            jEnterTheLanguagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jEnterTheLanguagePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jEnterTheLanguagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jClientLanguageComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jConfirmLanguageButton, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jEnterTheLanguagePanelLayout.setVerticalGroup(
            jEnterTheLanguagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jEnterTheLanguagePanelLayout.createSequentialGroup()
                .addGap(106, 106, 106)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jClientLanguageComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jConfirmLanguageButton)
                .addGap(108, 108, 108))
        );

        jChatPanel.setPreferredSize(new java.awt.Dimension(400, 300));

        jMessageTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMessageTextFieldActionPerformed(evt);
            }
        });

        jSendButton.setText("Send");
        jSendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jSendButtonActionPerformed(evt);
            }
        });

        jConversation.setEditable(false);
        jConversation.setContentType("text/html"); // NOI18N
        jScrollPane2.setViewportView(jConversation);

        javax.swing.GroupLayout jChatPanelLayout = new javax.swing.GroupLayout(jChatPanel);
        jChatPanel.setLayout(jChatPanelLayout);
        jChatPanelLayout.setHorizontalGroup(
            jChatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jChatPanelLayout.createSequentialGroup()
                .addComponent(jMessageTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 345, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jSendButton, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jScrollPane2)
        );
        jChatPanelLayout.setVerticalGroup(
            jChatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jChatPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addGroup(jChatPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jMessageTextField)
                    .addComponent(jSendButton)))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Understand Me");
        setResizable(false);

        javax.swing.GroupLayout jMainPanelLayout = new javax.swing.GroupLayout(jMainPanel);
        jMainPanel.setLayout(jMainPanelLayout);
        jMainPanelLayout.setHorizontalGroup(
            jMainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jMainPanelLayout.setVerticalGroup(
            jMainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jMainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jMainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jConnectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jConnectButtonActionPerformed
        setIPAddress();
    }//GEN-LAST:event_jConnectButtonActionPerformed

    private void jIPAddressTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jIPAddressTextFieldActionPerformed
        setIPAddress();
    }//GEN-LAST:event_jIPAddressTextFieldActionPerformed

    private void jClientNameTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jClientNameTextFieldActionPerformed
        login();
    }//GEN-LAST:event_jClientNameTextFieldActionPerformed

    private void jConfirmNameButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jConfirmNameButtonActionPerformed
        login();
    }//GEN-LAST:event_jConfirmNameButtonActionPerformed

    private void jConfirmLanguageButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jConfirmLanguageButtonActionPerformed
        chooseLanguage();
    }//GEN-LAST:event_jConfirmLanguageButtonActionPerformed

    private void jSendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jSendButtonActionPerformed
        send();
    }//GEN-LAST:event_jSendButtonActionPerformed

    private void jMessageTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMessageTextFieldActionPerformed
        send();
    }//GEN-LAST:event_jMessageTextFieldActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jChatPanel;
    private javax.swing.JComboBox<String> jClientLanguageComboBox;
    private javax.swing.JTextField jClientNameTextField;
    private javax.swing.JButton jConfirmLanguageButton;
    private javax.swing.JButton jConfirmNameButton;
    private javax.swing.JButton jConnectButton;
    private javax.swing.JTextPane jConversation;
    private javax.swing.JPanel jEnterTheLanguagePanel;
    private javax.swing.JPanel jEnterTheNamePanel;
    private javax.swing.JTextField jIPAddressTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jLogOnServerPanel;
    private javax.swing.JPanel jMainPanel;
    private javax.swing.JTextField jMessageTextField;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton jSendButton;
    // End of variables declaration//GEN-END:variables
}
