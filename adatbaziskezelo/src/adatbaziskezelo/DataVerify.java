package adatbaziskezelo;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class DataVerify {
	
	public boolean filled (JTextField field, String data) {
		String verify =Fieldcheck (field);
		if (verify.length()>0) return true;
		else {
			SystemMessage("Hiba: a(z) "+data+" mez� �res",0);
			return false;
		}
	}
	
	public boolean validInteger(JTextField field ,String data) {
		String verify =Fieldcheck (field);
		boolean gooddata = filled(field,data);
		if (gooddata) try {
			Integer.parseInt(verify);
		} catch(NumberFormatException e){
			SystemMessage("A(z) "+data+" mez�ben hib�s sz�madat!",0);
			gooddata = false;
		}
		return gooddata;
	}
	
	public String Fieldcheck(JTextField gooddata) {
		return gooddata.getText();
	}
	
	public void SystemMessage(String msg, int tipus) {
		JOptionPane.showMessageDialog(null, msg, "Program �zenet", tipus);
	}
	
	
	public boolean datechecK(String SDate) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
		try {
			java.util.Date pdate = dateFormat.parse (SDate);
			if (!dateFormat.format(pdate).equals(SDate)) {return false;}
			return true;
		} catch(ParseException ef) {return false;}
	}
	
	public boolean validDate(JTextField field, String data) {
		String verify=Fieldcheck(field);
		boolean b = filled (field,data);
		if (b && datechecK(verify))return true;
		else {
			SystemMessage ("A(z) "+data+" Mez�ben hib�s a d�tum",0);
			return false;	
		}
		
	}
	
	
	
	public boolean filled (JTextField field) {
		String verify =Fieldcheck (field);
		if (verify.length()>0) return true;
		else return false;
	}
	
	public int stringToInt(String data) {
		int number=-1;
		try { number=Integer.valueOf(data);}
		catch (NumberFormatException nfe) {
			SystemMessage("strinToInt: "+nfe.getMessage(),0);
		}
		return number;
	
	}
	
	
}
