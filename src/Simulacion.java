import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;


public class Simulacion {
	//-------------------------------------------------------------------------------
	//Metodos------------------------------------------------------------------------
	//-------------------------------------------------------------------------------
	
	public static void cargarPasajeros(InputStream archivoDatos1,PriorityQueue<Pasajero>pasajerosIniciales) throws Exception{
		Workbook workbook = Workbook.getWorkbook(archivoDatos1);
		Sheet sheet = workbook.getSheet("Servicios");
		Cell[]horas=sheet.getColumn(1);
		Cell[]callesIniciales=sheet.getColumn(2);
		Cell[]carrerasIniciales=sheet.getColumn(3);
		Cell[]callesFinales=sheet.getColumn(4);
		Cell[]carrerasFinales=sheet.getColumn(5);
		for(int i =2;i<horas.length-1;i++){
			Long id=(long) i-1;
			Pasajero p=new Pasajero(Double.parseDouble(horas[i].getContents().replace(',', '.')),Integer.parseInt(callesIniciales[i].getContents()),Integer.parseInt(carrerasIniciales[i].getContents()),Integer.parseInt(callesFinales[i].getContents()),Integer.parseInt(carrerasFinales[i].getContents()),id);
			pasajerosIniciales.add(p);
		}
	}
	
	public static void cargarConductores(InputStream archivoDatos2, List<Conductor>conductoresIniciales) throws Exception{
		Workbook workbook = Workbook.getWorkbook(archivoDatos2);
		Sheet sheet = workbook.getSheet("Conductores");
		Cell[]calles=sheet.getColumn(1);	
		Cell[]carreras=sheet.getColumn(2);
		for(int i =1;i<calles.length;i++){
			Long id=(long) i;
			Conductor c=new Conductor(Integer.parseInt(calles[i].getContents()),Integer.parseInt(carreras[i].getContents()),id);
			conductoresIniciales.add(c);
			//System.out.println(c.getCalle()+" --- "+c.getCarrera()+" -- "+c.getId());
		}
	}
	public static void main(String[] args) {
		try {
			PriorityQueue<Pasajero>in=new PriorityQueue<Pasajero>();
			InputStream archivoDatos1=Moviplus.class.getResourceAsStream("datos.xls");
			Simulacion.cargarPasajeros(archivoDatos1,in);
			for(int i=0;i<in.size();i++){
				Pasajero p=in.poll();
				System.out.println(p.getHoraSolicitud());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
