package br.com.SeniorLiving.db;

import java.sql.*;

import javax.swing.*;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class ConnectionUtil {
	
    Connection conn = null;
    
    public static Connection connectdb()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/coursejdbc?autoReconnect=true&useSSL=false","root","genius");
            return conn;
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e);
            return null;
        }
    }
    
    public JasperPrint gerarPDF() throws JRException, SQLException, ClassNotFoundException {
		JasperPrint rel = null;
		try {
			Connection con = connectdb();
		//	HashMap map = new HashMap();
			String arquivoJasper = "/br/com/SeniorLiving/Report/RelatorioDespesas.jasper";
			rel = JasperFillManager.fillReport(arquivoJasper, null, con); //map
			JasperViewer viewer = new JasperViewer(rel,false);
			viewer.setTitle("Relatório de Despesas");
			viewer.setVisible(true);
		//	viewer.show();
			
		} catch (JRException e) {
			JOptionPane.showMessageDialog(null,e.getMessage());
		}
		return rel;
	}
}