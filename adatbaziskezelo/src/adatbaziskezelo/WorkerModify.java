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
import javax.swing.JTextField;
import javax.swing.JLabel;

public class WorkerModify extends JDialog {
	
	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private WorkerTableModel tableModel;
	private DataVerify verify = new DataVerify();
	private DatabaseMethods databaseModel = new DatabaseMethods();
	private JTextField id;
	private JTextField nev;
	private JTextField szid;
	private JTextField munk;
	private JTextField fiz;

	
	public WorkerModify(JFrame frame, WorkerTableModel betm) {
		super(frame, "Munkatársak adatainak módositása", true);
		tableModel = betm;
		
		setBounds(100, 100, 882, 555);
		getContentPane().setLayout(null);
		
		JButton btnBezar = new JButton("Bez\u00E1r");
		btnBezar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose(); 
			}
		});
		btnBezar.setBounds(753, 482, 89, 23);
		getContentPane().add(btnBezar);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 815, 276);
		getContentPane().add(scrollPane);
		
		table = new JTable(tableModel);
		table.setForeground(Color.CYAN);
		table.setBackground(Color.DARK_GRAY);
		scrollPane.setViewportView(table);
		
		JButton btnmodosítas = new JButton("M\u00F3dos\u00EDt\u00E1s");
		btnmodosítas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int counter=0, index=0, x=0;
				for(x = 0; x<tableModel.getRowCount();x++)
					if ((Boolean)tableModel.getValueAt(x,0)) {counter++; index=x;}
					if (counter==0) verify.SystemMessage("Nincs kijelölve módositandó Munkás!", 0);
					if (counter>1) verify.SystemMessage("Több Munkás van kijelölve! (Egyszerre csak egy modositando)", 0); 
					if (counter==1) {
						if (Datamod() >0) {
							boolean ok= true;
							if (verify.filled(id)) ok= verify.validInteger(id,"Azonosító");
							if (ok && verify.filled(fiz)) ok = verify.validInteger(fiz, "Fizetés");
							if(ok) {
								String mkod = tableModel.getValueAt(index, 1).toString();
								databaseModel.connect();
								if(verify.filled(id)) databaseModel.UpdateData(mkod, "azonosito", verify.Fieldcheck(id));
								if(verify.filled(nev)) databaseModel.UpdateData(mkod, "nev", verify.Fieldcheck(nev));
								if(verify.filled(szid)) databaseModel.UpdateData(mkod, "szulido", verify.Fieldcheck(szid));
								if(verify.filled(munk)) databaseModel.UpdateData(mkod, "munkakor", verify.Fieldcheck(munk));
								if(verify.filled(fiz)) databaseModel.UpdateData(mkod, "fizetes", verify.Fieldcheck(fiz));
								databaseModel.disconnect();
								
								if (verify.filled(id)) tableModel.setValueAt(verify.stringToInt(verify.Fieldcheck(id)), index, 1);
								if (verify.filled(nev)) tableModel.setValueAt(verify.Fieldcheck(nev), index, 2);
								if (verify.filled(szid)) tableModel.setValueAt(verify.Fieldcheck(szid), index, 3);
								if (verify.filled(munk)) tableModel.setValueAt(verify.Fieldcheck(munk), index, 4);
								if (verify.filled(fiz)) tableModel.setValueAt(verify.stringToInt(verify.Fieldcheck(fiz)), index, 5);
								verify.SystemMessage("A munkatárs Módosítva",1);
								
								
							
							}
							else {
						
								verify.SystemMessage("Nincs kitöltve egy módosító mezezõ sem!",0);
				
							}
						}
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
		
		btnmodosítas.setBounds(34, 482, 117, 23);
		getContentPane().add(btnmodosítas);
		
		id = new JTextField();
		id.setBounds(34, 313, 75, 20);
		getContentPane().add(id);
		id.setColumns(10);
		
		nev = new JTextField();
		nev.setBounds(188, 313, 150, 20);
		getContentPane().add(nev);
		nev.setColumns(10);
		
		szid = new JTextField();
		szid.setBounds(439, 313, 86, 20);
		getContentPane().add(szid);
		szid.setColumns(10);
		
		munk = new JTextField();
		munk.setBounds(630, 313, 117, 20);
		getContentPane().add(munk);
		munk.setColumns(10);
		
		fiz = new JTextField();
		fiz.setBounds(253, 384, 292, 20);
		getContentPane().add(fiz);
		fiz.setColumns(10);
		
		JLabel lblId = new JLabel("ID:");
		lblId.setBounds(10, 316, 46, 14);
		getContentPane().add(lblId);
		
		JLabel lblNv = new JLabel("N\u00E9v:");
		lblNv.setBounds(162, 316, 46, 14);
		getContentPane().add(lblNv);
		
		JLabel lblSzletsiId = new JLabel("Sz\u00FClet\u00E9si id\u0151:");
		lblSzletsiId.setBounds(377, 316, 63, 14);
		getContentPane().add(lblSzletsiId);
		
		JLabel lblMunkakr = new JLabel("Munkak\u00F6r:");
		lblMunkakr.setBounds(579, 316, 50, 14);
		getContentPane().add(lblMunkakr);
		
		JLabel lblFizets = new JLabel("Fizet\u00E9s:");
		lblFizets.setBounds(211, 387, 46, 14);
		getContentPane().add(lblFizets);
		
		
		TableRowSorter<WorkerTableModel> rowSorter = (TableRowSorter<WorkerTableModel>)table.getRowSorter();
		rowSorter.setSortable(0, false);
		

	}
	public int Datamod() {
		int counter=0;
		if (verify.filled(id)) counter++;
		if (verify.filled(nev)) counter++;
		if (verify.filled(szid)) counter++;
		if (verify.filled(munk)) counter++;
		if (verify.filled(fiz)) counter++;
		return counter;
	}
}
