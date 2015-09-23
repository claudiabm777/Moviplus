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
	private List<Etapa>etapas;
	private Double ventanaTiempo;
	
	//-------------------------------------------------------------------------------
	//Constructor--------------------------------------------------------------------
	//-------------------------------------------------------------------------------
	public SimulacionOptimizacion(Double tiempo){
		this.tiempo=tiempo;
		conductoresIniciales=new ArrayList<Conductor>();
		pasajerosIniciales=new PriorityQueue<Pasajero>();
		etapas=new ArrayList<Etapa>();
		ventanaTiempo=60.0*4;
	}
	
	//-------------------------------------------------------------------------------
	//Getters and Setters------------------------------------------------------------
	//-------------------------------------------------------------------------------
	
	
	//-------------------------------------------------------------------------------
	//Metodos------------------------------------------------------------------------
	//-------------------------------------------------------------------------------
	
	private void cargarInformacionInicial() throws Exception{
		Simulacion.cargarPasajeros(archivoDatos1, pasajerosIniciales);
		Simulacion.cargarConductores(archivoDatos2, conductoresIniciales);
	}
	
	private void asignarPasajerosAEtapas(){
		PriorityQueue<Pasajero> q=pasajerosIniciales;
		Pasajero ultimo=null;
		while(!q.isEmpty()){
			ultimo=q.poll();
		}
		Double duracionTotal=ultimo.getHoraSolicitud();
		Integer numeroEtapas=(int) Math.ceil((duracionTotal/ventanaTiempo));
		for(int i=0;)
	}
	public static void main(String[] args) {
		System.out.println(Math.ceil(101.0/50.0));
	}

}
