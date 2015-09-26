import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;


public class Moviplus {
	
	//-------------------------------------------------------------------------------
	//Metodos------------------------------------------------------------------------
	//-------------------------------------------------------------------------------
	public void simulacionVecinos(){
		SimulacionVecinos simulacion=new SimulacionVecinos();
		Double r=simulacion.simulacionVecinosCercanos();
		System.out.println("Tiempo espera total: "+r);
		int co=0;
		for(int i=0;i<simulacion.pasajerosFinales.size();i++){
			Pasajero pas=simulacion.pasajerosFinales.get(i);
			double p=pas.getHoraSolicitud();
			Conductor c=simulacion.asignacion.get(simulacion.pasajerosFinales.get(i));
			if(c!=null){
				System.out.println("El pasajero: "+pas.getId()+" tiene tiempo: "+p+"\t el conductor: "+c.getId()+" tiene tiempo: "+c.getTiempoDisponible());
			}
			else{
				co++;
				System.out.println("El pasajero: "+pas.getId()+" tiene tiempo: "+p+" - NO ENTROOOO");
			}
		}
		System.out.println(co);
	}
	
	public void simulacionOptimizacion(Double tiempo,int id){
		try {
			JFrame parentFrame = new JFrame();
			 
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setDialogTitle("Especifique el nombre del archivo a guardar con extencion xls");   
			 
			int userSelection = fileChooser.showSaveDialog(parentFrame);
			 
			if (userSelection == JFileChooser.APPROVE_OPTION) {
			    File fileToSave = fileChooser.getSelectedFile();
				
			    WorkbookSettings wbSettings = new WorkbookSettings();

			    wbSettings.setLocale(new Locale("en", "EN"));

			    WritableWorkbook workbook = Workbook.createWorkbook(fileToSave, wbSettings);
			    workbook.createSheet("Simulacion"+id, id);
			    WritableSheet excelSheet = workbook.getSheet(id);
		SimulacionOptimizacion s=new SimulacionOptimizacion(tiempo);
		s.simulacionOptimizacion(excelSheet);

	    workbook.write();
	    workbook.close();
			}else{
				JOptionPane.showMessageDialog (null, "No se llevó a cabo la simulación.", "Error", JOptionPane.ERROR_MESSAGE);

			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog (null, "Uppss! no se pudo guardar bien tu archivo, vuelve a intentarlo.", "Error", JOptionPane.ERROR_MESSAGE);

		}
	}
	
	
	public void simulacionOptimizacionM(Double tiempo,WritableSheet excelSheet ){
		
			
		SimulacionOptimizacion s=new SimulacionOptimizacion(tiempo);
		s.simulacionOptimizacion(excelSheet);

			

		
	}
	
	
	public void simulacionMultiple(double superior, double inferior, double veces){
		try{
		JFrame parentFrame = new JFrame();
		 
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Especifique el nombre del archivo a guardar con extencion xls");   
		 
		int userSelection = fileChooser.showSaveDialog(parentFrame);
		 
		if (userSelection == JFileChooser.APPROVE_OPTION) {
		    File fileToSave = fileChooser.getSelectedFile();
			
		    WorkbookSettings wbSettings = new WorkbookSettings();

		    wbSettings.setLocale(new Locale("en", "EN"));

		    WritableWorkbook workbook = Workbook.createWorkbook(fileToSave, wbSettings);

		
		double tamVentanas=(superior-inferior)/veces;
		Double[]ventanas=new Double[(int)veces];
		for(int i=0;i<veces;i++){
		    workbook.createSheet("Simulacion"+i, i);
		    WritableSheet excelSheet = workbook.getSheet(i);
		    if(i==0){
			ventanas[i]=inferior+tamVentanas;
		    }
		    else{
		    	ventanas[i]=inferior+tamVentanas+ventanas[i-1];
		    }
			simulacionOptimizacionM(ventanas[i],excelSheet);
		}

	    workbook.write();
	    workbook.close();
		}else{
			JOptionPane.showMessageDialog (null, "No se llevó a cabo la simulación.", "Error", JOptionPane.ERROR_MESSAGE);

		}

	} catch (Exception e) {
		JOptionPane.showMessageDialog (null, "Uppss! no se pudo guardar bien tu archivo, vuelve a intentarlo.", "Error", JOptionPane.ERROR_MESSAGE);

	}
	}

}
