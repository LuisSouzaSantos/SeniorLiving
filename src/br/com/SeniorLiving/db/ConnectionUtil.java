package br.com.SeniorLiving.db;

import java.math.BigDecimal;

import javax.persistence.EntityManager;

import br.com.ftt.ec6.seniorLiving.db.Database;
import br.com.ftt.ec6.seniorLiving.entities.Elderly;

//import net.sf.jasperreports.engine.JRException;
//import net.sf.jasperreports.engine.JasperFillManager;
//import net.sf.jasperreports.engine.JasperPrint;
//import net.sf.jasperreports.view.JasperViewer;

public class ConnectionUtil {
	
  
    public static void main(String[] args) {
		Elderly elderly = new Elderly();
		elderly.setName("Rodolfo");
		elderly.setNationality("Holanda");
		elderly.setRg("55556565565");
		elderly.setCpf("545544454");
		elderly.setMaritalStatus("casado");
		elderly.setMonthlyPayment(new BigDecimal(8989.45));
		
		
		EntityManager entityManager = Database.getConnection(elderly);
		
		entityManager.getTransaction().begin();
		entityManager.persist(elderly);
		entityManager.getTransaction().commit();
		entityManager.close();
	}
}
//    
//    public static Connection connectdb()
//    {
//        try
//        {
//            Class.forName("com.mysql.jdbc.Driver");
//            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/coursejdbc?autoReconnect=true&useSSL=false","root","genius");
//            return conn;
//        }
//        catch(Exception e)
//        {
//            JOptionPane.showMessageDialog(null, e);
//            return null;
//        }
//    }
    
//    public JasperPrint gerarPDF() throws JRException, SQLException, ClassNotFoundException {
//		JasperPrint rel = null;
//		try {
//			Connection con = connectdb();
//		//	HashMap map = new HashMap();
//			String arquivoJasper = "/br/com/SeniorLiving/Report/RelatorioDespesas.jasper";
//			rel = JasperFillManager.fillReport(arquivoJasper, null, con); //map
//			JasperViewer viewer = new JasperViewer(rel,false);
//			viewer.setTitle("Relatório de Despesas");
//			viewer.setVisible(true);
//		//	viewer.show();
//			
//		} catch (JRException e) {
//			JOptionPane.showMessageDialog(null,e.getMessage());
//		}
//		return rel;
//	}
//}