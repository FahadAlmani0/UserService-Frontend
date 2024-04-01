package rosol.userservice.ui;

import rosol.userservice.controllers.AddUserController;
import rosol.userservice.controllers.DeleteUserController;
import rosol.userservice.controllers.RefreshUsersTableController;
import rosol.userservice.controllers.UpdateUserController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UserProfile {
    private JPanel usersPanel;
    private JButton addButton;
    private JTable usersTable;
    private JLabel nameLabel;
    private JLabel lastnameLabel;
    private JLabel listLabel;
    private JTextField nameTextField;
    private JTextField lastnameTextField;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton refreshButton;
    private JScrollPane userTableScrollPane;
    private JTextField birthdayTextField;
    private JLabel birthdateLabel;
    private JTextField genderTextField;
    private JLabel genderLabel;
    private JLabel nationalityLabel;
    private JTextField nationalityTextField;
    private JLabel phoneLabel;
    private JTextField phoneTextField;
    private JLabel emailLabel;
    private JTextField emailTextField;
    private JLabel usernameLabel;
    private JTextField usernameTextField;
    private JLabel passwordLabel;
    private JTextField passwordTextField;
    private JLabel entityIdLabel;
    private JTextField entityIdTextField;
    private JLabel nationalIdLabel;
    private JTextField nationalIdTextField;
    private JLabel roleLabel;
    private JTextField roleTextField;
    private JLabel permissionLabel;
    private JTextField permissionTextField;
    private JLabel missionIdLabel;
    private JTextField missionIdTextField;

    /**
     * Start User Interface
     * @param args args
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("User Profile");
        frame.setContentPane(new UserProfile().usersPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Set controllers to User Interface buttons
     */
    public UserProfile() {
        RefreshUsersTableController refreshUsersTableController = new RefreshUsersTableController(usersTable);
        addButton.addActionListener(new AddUserController(nameTextField, lastnameTextField, birthdayTextField, genderTextField,
                nationalityTextField, phoneTextField, emailTextField, usernameTextField, passwordTextField, entityIdTextField, nationalIdTextField,
                roleTextField, permissionTextField, missionIdTextField, usersTable, refreshUsersTableController));
        updateButton.addActionListener(new UpdateUserController(nameTextField, lastnameTextField, birthdayTextField, genderTextField,
                nationalityTextField, phoneTextField, emailTextField, usernameTextField, passwordTextField, entityIdTextField, nationalIdTextField,
                roleTextField, permissionTextField, missionIdTextField, usersTable, refreshUsersTableController));
        deleteButton.addActionListener(new DeleteUserController(nameTextField, lastnameTextField, birthdayTextField, genderTextField,
                nationalityTextField, phoneTextField, emailTextField, usernameTextField, passwordTextField, entityIdTextField, nationalIdTextField,
                roleTextField, permissionTextField, missionIdTextField, usersTable, refreshUsersTableController));
        refreshButton.addActionListener(refreshUsersTableController);
        configureUsersTable(refreshUsersTableController);
    }

    /**
     * Configure Users table, columns and listeners
     */
    private void configureUsersTable(RefreshUsersTableController refreshUsersTableController) {
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Id");
        tableModel.addColumn("Name");
        tableModel.addColumn("Lastname");
        tableModel.addColumn("Birthdate");
        tableModel.addColumn("Gender");
        tableModel.addColumn("Nationality");
        tableModel.addColumn("Phone");
        tableModel.addColumn("Email");
        tableModel.addColumn("Username");
        tableModel.addColumn("Password");
        tableModel.addColumn("Entity ID");
        tableModel.addColumn("National ID");
        tableModel.addColumn("Role");
        tableModel.addColumn("Permission");
        tableModel.addColumn("Mission ID");
        tableModel.addColumn("Active");
        usersTable.setModel(tableModel);
        TableColumnModel columnModel = usersTable.getColumnModel();
        columnModel.removeColumn(columnModel.getColumn(0));
        columnModel.removeColumn(columnModel.getColumn(columnModel.getColumnCount() - 1));
        usersTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                usersTableMouseClicked();
            }
        });
        refreshUsersTableController.startRefreshUsersTableProcess();

    }

    /**
     * Configure Users table rows selection listener
     */
    private void usersTableMouseClicked(){
        int selectedRow = this.usersTable.getSelectedRow();
        this.nameTextField.setText(this.usersTable.getModel().getValueAt(selectedRow, 1).toString());
        this.lastnameTextField.setText(this.usersTable.getModel().getValueAt(selectedRow, 2).toString());
        this.birthdayTextField.setText(this.usersTable.getModel().getValueAt(selectedRow, 3).toString());
        this.genderTextField.setText(this.usersTable.getModel().getValueAt(selectedRow, 4).toString());
        this.nationalityTextField.setText(this.usersTable.getModel().getValueAt(selectedRow, 5).toString());
        this.phoneTextField.setText(this.usersTable.getModel().getValueAt(selectedRow, 6).toString());
        this.emailTextField.setText(this.usersTable.getModel().getValueAt(selectedRow, 7).toString());
        this.usernameTextField.setText(this.usersTable.getModel().getValueAt(selectedRow, 8).toString());
        this.passwordTextField.setText(this.usersTable.getModel().getValueAt(selectedRow, 9).toString());
        this.entityIdTextField.setText(this.usersTable.getModel().getValueAt(selectedRow, 10).toString());
        this.nationalIdTextField.setText(this.usersTable.getModel().getValueAt(selectedRow, 11).toString());
        this.roleTextField.setText(this.usersTable.getModel().getValueAt(selectedRow, 12).toString());
        this.permissionTextField.setText(this.usersTable.getModel().getValueAt(selectedRow, 13).toString());
        this.missionIdTextField.setText(this.usersTable.getModel().getValueAt(selectedRow, 14).toString());
    }
}
