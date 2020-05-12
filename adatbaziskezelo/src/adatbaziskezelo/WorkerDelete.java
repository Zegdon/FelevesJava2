package adatbaziskezelo;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;
import javax.swing.JScrollPane;
import java.awt.Color;

public class WorkerDelete extends JDialog {
	
	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private WorkerTableModel tableModel;
	private DataVerify verify = new DataVerify();
	private DatabaseMethods databaseMethod = new DatabaseMethods();

	
	public WorkerDelete(JFrame frame, WorkerTableModel tableModelParam) {
		super(frame, "Dolgozók Törlése", true);
		tableModel = tableModelParam;
		
		setBounds(100, 100, 731, 460);
		getContentPane().setLayout(null);
		
		JButton btnBezar = new JButton("Bez\u00E1r");
		btnBezar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose(); 
			}
		});
		btnBezar.setBounds(309, 387, 89, 23);
		getContentPane().add(btnBezar);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 630, 330);
		getContentPane().add(scrollPane);
		
		table = new JTable(tableModel);
		table.setForeground(Color.CYAN);
		table.setBackground(Color.DARK_GRAY);
		scrollPane.setViewportView(table);
		
		JButton btnAdatokTrlse = new JButton("Adatok t\u00F6rl\u00E9se");
		btnAdatokTrlse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int value=0, Jel=0, x=0;
				for(x = 0; x<tableModel.getRowCount();x++)
					if ((Boolean)tableModel.getValueAt(x,0)) {value++; Jel=x;}
					if (value==0) verify.SystemMessage("Nincs kijelölve törlendõ Munkást!", 0);
					if (value>1) verify.SystemMessage("Több Munkás van kijelölve! (Egyszerre csak egy törölhetõ)", 0); 
					if (value==1) {
						String id= tableModel.getValueAt(Jel, 1).toString();
						databaseMethod.connect();
						databaseMethod.deletedata(id);
						databaseMethod.disconnect();
						tableModel.removeRow(Jel);
						verify.SystemMessage("A Munkás törölve",1);
					}
			}
		});
		
		TableColumn column = null;
		for (int i = 0; i < 5; i++) {
			column = table.getColumnModel().getColumn(i);
			if (i==0 || i==1 || i==4) column.setPreferredWidth(30);
			else {column.setPreferredWidth(100);}
		}
		table.setAutoCreateRowSorter(true);
		
		btnAdatokTrlse.setBounds(46, 387, 117, 23);
		getContentPane().add(btnAdatokTrlse);
		TableRowSorter<WorkerTableModel> rowSorter = (TableRowSorter<WorkerTableModel>)table.getRowSorter();
		rowSorter.setSortable(0, false);
		

	}
}
