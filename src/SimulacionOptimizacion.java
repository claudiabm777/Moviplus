import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;


public class SimulacionOptimizacion extends Simulacion {
	public final static InputStream archivoDatos1=Moviplus.class.getResourceAsStream("datos.xls");
	public final static InputStream archivoDatos2=Moviplus.class.getResourceAsStream("datos.xls");
	
	//-------------------------------------------------------------------------------
	//Atributos----------------------------------------------------------------------
	//-------------------------------------------------------------------------------
	private List<Conductor>conductoresIniciales;
	private PriorityQueue<Pasajero>pasajerosIniciales;
	private Double tiempo;
	
	
	//-------------------------------------------------------------------------------
	//Constructor--------------------------------------------------------------------
	//-------------------------------------------------------------------------------
	public SimulacionOptimizacion(Double tiempo){
		this.tiempo=tiempo;
		conductoresIniciales=new ArrayList<Conductor>();
		pasajerosIniciales=new PriorityQueue<Pasajero>();
	}
	
	//-------------------------------------------------------------------------------
	//Getters and Setters------------------------------------------------------------
	//-------------------------------------------------------------------------------
	
	
	//-------------------------------------------------------------------------------
	//Metodos------------------------------------------------------------------------
	//-------------------------------------------------------------------------------
	
	public void cargarInformacionInicial() throws Exception{
		Simulacion.cargarPasajeros(archivoDatos1, pasajerosIniciales);
		Simulacion.cargarConductores(archivoDatos2, conductoresIniciales);
	}

}
