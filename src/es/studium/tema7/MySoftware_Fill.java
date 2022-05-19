package es.studium.tema7;

import java.awt.Button;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySoftware_Fill implements WindowListener, ActionListener {

	Frame frmAltaCliente = new Frame("Alta de Cliente");
	Label lblNombreCliente = new Label("Nombre:");
	TextField txtNombreCliente = new TextField(20);
	Label lblCifCliente = new Label("Cif:");
	TextField txtCifCliente = new TextField(20);
	Button btnAltaCliente = new Button("Alta");
	Button btnCancelarAltaCliente = new Button("Cancelar");
	
	Dialog dlgConfirmarAltaCliente = new Dialog(frmAltaCliente, "Alta Cliente", true);

	
	public MySoftware_Fill(String frame_title) {
		
		frmAltaCliente.setTitle(frame_title);
		frmAltaCliente.setLayout(new FlowLayout());
		frmAltaCliente.add(lblNombreCliente);
		frmAltaCliente.add(txtNombreCliente);
		frmAltaCliente.add(lblCifCliente);
		frmAltaCliente.add(txtCifCliente);
		btnAltaCliente.addActionListener(this);
		frmAltaCliente.add(btnAltaCliente);
		btnCancelarAltaCliente.addActionListener(this);
		frmAltaCliente.add(btnCancelarAltaCliente);

		frmAltaCliente.setSize(260, 140);
		frmAltaCliente.setResizable(false);
		frmAltaCliente.setLocationRelativeTo(null);
		frmAltaCliente.addWindowListener((WindowListener) this);
		frmAltaCliente.setVisible(true);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if (e.getSource().equals(btnAltaCliente)) {
			MySoftware.connection = MySoftware.conectar();
			try {
				//Crear una sentencia
				MySoftware.statement = MySoftware.connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				
				//Crear un objeto ResultSet para guardar lo obtenido
				//y ejecutar la sentencia SQL
				MySoftware.sentencia = "INSERT INTO clientes VALUES (null, '" + txtNombreCliente.getText() + "', '" + txtCifCliente.getText() + "', 'ejemplo@ejemplo.com')";
				
				MySoftware.statement.executeUpdate(MySoftware.sentencia);
				
				//dlgConfirmarAltaCliente.setVisible(true);
				
				
			} catch (SQLException sqle) {
				System.out.println("Error 2-" + sqle.getMessage());
			}
		}
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		frmAltaCliente.setVisible(false);
		//System.exit(0);
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

}
