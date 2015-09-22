import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;


public class SimulacionVecinos {
	
	//-------------------------------------------------------------------------------
	//Constantes----------------------------------------------------------------------
	//-------------------------------------------------------------------------------
	public final static InputStream archivoDatos1=Moviplus.class.getResourceAsStream("datos.xls");
	public final static InputStream archivoDatos2=Moviplus.class.getResourceAsStream("datos.xls");
	
	//-------------------------------------------------------------------------------
	//Atributos----------------------------------------------------------------------
	//-------------------------------------------------------------------------------
	private List<Conductor>conductoresIniciales;
	private PriorityQueue<Pasajero>pasajerosIniciales;
	private HashMap<Pasajero,Conductor>asignacion;
	private List<Pasajero>pasajerosFinales;
	
	//-------------------------------------------------------------------------------
	//Constructor--------------------------------------------------------------------
	//-------------------------------------------------------------------------------
	public SimulacionVecinos(){
		conductoresIniciales=new ArrayList<Conductor>();
		pasajerosIniciales=new PriorityQueue<Pasajero>();
		pasajerosFinales=new ArrayList<Pasajero>();
		asignacion= new HashMap<Pasajero,Conductor>();
	}
	
	//-------------------------------------------------------------------------------
	//Getters and Setters------------------------------------------------------------
	//-------------------------------------------------------------------------------
	
	
	//-------------------------------------------------------------------------------
	//Metodos------------------------------------------------------------------------
	//-------------------------------------------------------------------------------
	public Double simulacionVecinosCercanos(){
		Double respuesta=null;
		try{
			cargarInformacionInicial();
			asignarConductoresAPasajeros();
			respuesta=tiempoEsperaTotal();
		}catch(Exception e){
			e.printStackTrace();
		}
		return respuesta;
	}
	private void cargarInformacionInicial() throws Exception{
		Simulacion.cargarPasajeros(archivoDatos1, pasajerosIniciales);
		Simulacion.cargarConductores(archivoDatos2, conductoresIniciales);
	}
	
	private Double tiempoEsperaTotal(){
		Double tiempoTotal=0.0;
		for(int i=0;i<pasajerosIniciales.size();i++){
			tiempoTotal+=pasajerosFinales.get(i).getTiempoEspera();
		}
		System.out.println(asignacion);
		return tiempoTotal;
	}
	
	private void asignarConductoresAPasajeros(){
		int tamanio=pasajerosIniciales.size();
		for(int i=0;i<tamanio;i++){
			Pasajero pasajero=pasajerosIniciales.poll();
			Conductor conductor=conductorMasCercano(pasajero);
			if(Math.abs(pasajero.getHoraSolicitud()-conductor.getTiempoDisponible())>=Simulacion.TIEMPO_IMPACIENCIA){
				pasajero.setTiempoEspera(Simulacion.TIEMPO_IMPACIENCIA);
				pasajerosFinales.add(pasajero);
			}
			else if(pasajero.getHoraSolicitud()>=conductor.getTiempoDisponible()){
				pasajero.setTiempoEspera(0.0);
				conductoresIniciales.remove(conductor);
				double distanciaX1=Math.abs(pasajero.getCalleInicial()-conductoresIniciales.get(i).getCalle());
				double distanciaY1=Math.abs(pasajero.getCarreraInicial()-conductoresIniciales.get(i).getCarrera());
				double distanciaPasajero=distanciaX1+distanciaY1;
				double distanciaX2=Math.abs(pasajero.getCalleInicial()-pasajero.getCalleFinal());
				double distanciaY2=Math.abs(pasajero.getCarreraInicial()-pasajero.getCarreraFinal());
				double distanciaDestino=distanciaX2+distanciaY2;
				double tiempo=(distanciaPasajero+distanciaDestino)*Simulacion.DISTANCIA_CUADRAS/Simulacion.VELOCIDAD_CONDUCTORES;
				Conductor c=new Conductor(pasajero.getCalleFinal(),pasajero.getCarreraFinal(),-2L);
				c.setTiempoDisponible(tiempo+conductor.getTiempoDisponible());
				conductoresIniciales.add(c);
				asignacion.put(pasajero, conductor);
				pasajerosFinales.add(pasajero);
			}
			else if(pasajero.getHoraSolicitud()<conductor.getTiempoDisponible()){
				pasajero.setTiempoEspera(conductor.getTiempoDisponible()-pasajero.getHoraSolicitud());
				conductoresIniciales.remove(conductor);
				double distanciaX1=Math.abs(pasajero.getCalleInicial()-conductoresIniciales.get(i).getCalle());
				double distanciaY1=Math.abs(pasajero.getCarreraInicial()-conductoresIniciales.get(i).getCarrera());
				double distanciaPasajero=distanciaX1+distanciaY1;
				double distanciaX2=Math.abs(pasajero.getCalleInicial()-pasajero.getCalleFinal());
				double distanciaY2=Math.abs(pasajero.getCarreraInicial()-pasajero.getCarreraFinal());
				double distanciaDestino=distanciaX2+distanciaY2;
				double tiempo=(distanciaPasajero+distanciaDestino)*Simulacion.DISTANCIA_CUADRAS/Simulacion.VELOCIDAD_CONDUCTORES;
				Conductor c=new Conductor(pasajero.getCalleFinal(),pasajero.getCarreraFinal(),-2L);
				c.setTiempoDisponible(tiempo+conductor.getTiempoDisponible());
				conductoresIniciales.add(c);
				asignacion.put(pasajero, conductor);
				pasajerosFinales.add(pasajero);
			}
		}	
	}
	
	
	private Conductor conductorMasCercano(Pasajero pasajero){
		Conductor respuesta=null;
		boolean hayConductoresDisponibles=false;
		for(int i=0;i<conductoresIniciales.size();i++){
			if(pasajero.getHoraSolicitud()<=conductoresIniciales.get(i).getTiempoDisponible()){
				hayConductoresDisponibles=true;
			}
		}
		if(hayConductoresDisponibles){
			double minimo=999999999.0;
			Conductor conductorMinimo=null;
			for(int i=0;i<conductoresIniciales.size();i++){
				double distanciaX=Math.abs(pasajero.getCalleInicial()-conductoresIniciales.get(i).getCalle());
				double distanciaY=Math.abs(pasajero.getCarreraInicial()-conductoresIniciales.get(i).getCarrera());
				double distancia=distanciaX+distanciaY;
				if(distancia<minimo){
					minimo=distancia;
					conductorMinimo=conductoresIniciales.get(i);
				}
			}
			respuesta=conductorMinimo;
		}
		else{
			double minimo=999999999.0;
			Conductor conductorMinimo=null;
			for(int i=0;i<conductoresIniciales.size();i++){
				if(conductoresIniciales.get(i).getTiempoDisponible()<minimo){
					minimo=conductoresIniciales.get(i).getTiempoDisponible();
					conductorMinimo=conductoresIniciales.get(i);
				}
			}
			respuesta=conductorMinimo;
		}
		return respuesta;
	}
	
	public static void main(String[] args) {
		SimulacionVecinos simulacion=new SimulacionVecinos();
		Double r=simulacion.simulacionVecinosCercanos();
		System.out.println("Tiempo espera total: "+r);
	}

}
