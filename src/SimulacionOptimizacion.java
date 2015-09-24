import java.io.InputStream;

import gurobi.*;

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
	public List<Etapa>etapas;
	private Double ventanaTiempo;
	
	//-------------------------------------------------------------------------------
	//Constructor--------------------------------------------------------------------
	//-------------------------------------------------------------------------------
	public SimulacionOptimizacion(Double tiempo) {
		
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
	
	public void asignarPasajerosAEtapas() throws Exception{
		cargarInformacionInicial();
		//System.out.println("HOLAAAAAAAAAAAAAAAAAAAAAAAAA pasaj inic: "+pasajerosIniciales.size());
		PriorityQueue<Pasajero> q=new PriorityQueue(pasajerosIniciales);
		Pasajero ultimo=null;
		int tam=q.size();
		for(int i=0;i<tam;i++){
			ultimo=q.poll();
		}
		//System.out.println("HOLAAAAAAAAAAAAAAAAAAAAAAAAA pasaj inic: "+pasajerosIniciales.size());
		Double duracionTotal=ultimo.getHoraSolicitud();
		Integer numeroEtapas=(int) Math.ceil((duracionTotal/ventanaTiempo));
		Double horaInicio=0.0;
		Double horaFin=ventanaTiempo;
		for(int i=0;i<numeroEtapas;i++){
			Long id=(long) (i+1);
			Etapa etapa=new Etapa(ventanaTiempo,id,horaInicio,horaFin);
			
			for(int j=0;j<pasajerosIniciales.size();j++){
				Pasajero pa=pasajerosIniciales.poll();
				if(pa.getHoraSolicitud()<=etapa.getHoraFinal()){
					etapa.pasajeros.add(pa);
				}
				else{
					pasajerosIniciales.add(pa);
					break;
				}
			}
			etapas.add(etapa);
			horaInicio=horaFin;
			horaFin+=ventanaTiempo;
		}
		for(int i=0;i<numeroEtapas;i++){
			Etapa etapa=etapas.get(i);
			etapa.setNuevasSolicitudes(etapa.pasajeros.size());
		}
	}
	
	public static void main(String[] args) {
		SimulacionOptimizacion s=new SimulacionOptimizacion(5.0*60.0);
		try {
			s.asignarPasajerosAEtapas();
			
			Etapa e=s.etapas.get(0);
			e.conductores=s.conductoresIniciales;
			e.generarModelo();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
//	public static void main(String[] args) {
//		int a=9;
//		int b=a;
//		b=b-2;
//		System.out.println(""+a+" b: "+b);
//		System.out.println(Math.ceil(101.0/50.0));
//		SimulacionOptimizacion sim=new SimulacionOptimizacion(1.0);
//		try {
//			sim.asignarPasajerosAEtapas();
//			for(int i=0;i<sim.etapas.size();i++){
//				Etapa e=sim.etapas.get(i);
//				System.out.println("Hora final: "+e.getHoraFinal()+" Hora inicial "+e.getHoraInicial());
//				for(int j=0;j<e.pasajeros.size();j++){
//					System.out.println(e.pasajeros.size());
//					System.out.println("Pasajero: "+e.pasajeros.get(j).getHoraSolicitud());
//				}
//			}
//			System.out.println(sim.etapas);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}

	 
}
