import java.io.InputStream;

import gurobi.*;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;


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
		ventanaTiempo=tiempo;
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
		System.out.println("HOLAAAAAAAAAAAAAAAAAAAAAAAAA pasaj inic: "+pasajerosIniciales.size());
		Double duracionTotal=ultimo.getHoraSolicitud();
		Integer numeroEtapas=(int) Math.ceil((duracionTotal/ventanaTiempo));
		Double horaInicio=0.0;
		Double horaFin=ventanaTiempo;
		for(int i=0;i<numeroEtapas;i++){
			Long id=(long) (i+1);
			Etapa etapa=new Etapa(ventanaTiempo,id,horaInicio,horaFin);
			int ttt=pasajerosIniciales.size();
			for(int j=0;j<ttt;j++){
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
			System.out.println("Etapa "+i+" num pas"+etapa.pasajeros.size()+"Inicio: "+etapa.getHoraInicial()+" fin: "+etapa.getHoraFinal());
		}
		
	}
	
	public void reparticionConductoresEtapas(List<Conductor>conduct,int id){
		for(Conductor c:conduct){
			
			
			for(int i=1;i<etapas.size();i++){
				if(etapas.get(i).getServiciosFinalizados()==null){
					etapas.get(i).setServiciosFinalizados(0);
				}
				
				if(c.getTiempoDisponible()>=etapas.get(i).getHoraInicial()&&c.getTiempoDisponible()<=etapas.get(i).getHoraFinal()){
					etapas.get(i).setServiciosFinalizados(1+etapas.get(i).getServiciosFinalizados());
				}
				if(c.getTiempoDisponible()>=etapas.get(i-1).getHoraInicial()&&c.getTiempoDisponible()<=etapas.get(i).getHoraInicial()){
					etapas.get(i).conductores.add(c);
					//etapas.get(i).setServiciosFinalizados(1+etapas.get(i).getServiciosFinalizados());
					i=etapas.size()+2;
				}
			}
		}
	}
	
	public static void main(String[] args) {
		SimulacionOptimizacion s=new SimulacionOptimizacion(30.0*60.0);
		try {
			s.asignarPasajerosAEtapas();
			s.etapas.get(0).conductores=s.conductoresIniciales;
			s.etapas.get(0).setServiciosFinalizados(0);
			for(int i=0;i<s.etapas.size();i++){
				s.etapas.get(i).generarModelo();
				s.reparticionConductoresEtapas(s.etapas.get(i).conductoresSinAsignar, i);
//				for(Conductor c:s.etapas.get(i).conductoresSinAsignar){
//					System.out.println("Conductor sin asignar: "+c.getId()+" tiempo disp: "+c.getTiempoDisponible());
//				}
				if(i<s.etapas.size()-1){
					if(s.etapas.get(i).pasajerosSinAsignar.size()>0){
						s.etapas.get(i+1).pasajeros.addAll(s.etapas.get(i).pasajerosSinAsignar);
					}
					
				}
				
				System.out.println("Nuevas solicitudes: "+s.etapas.get(i).getNuevasSolicitudes());
				System.out.println("Clientes perdidos: "+s.etapas.get(i).getClientesPerdidos());
				System.out.println("Servicios finalizados: "+s.etapas.get(i).getServiciosFinalizados());
				System.out.println("Solicitudes asignadas: "+s.etapas.get(i).getSolicitudesAsignadas());
				System.out.println("Solicitudes no asignadas: "+s.etapas.get(i).getSolicitudesNoAsignadas());
				System.out.println("Tiempo promedio: "+s.etapas.get(i).getTiempoEsperaPromedio());
				System.out.println("Conductores disponibles iniciales: "+s.etapas.get(i).getVehiculosDisponiblesInicio());
			}
//			Etapa e=s.etapas.get(0);
//			e.conductores=s.conductoresIniciales;
//			e.generarModelo();
//			Set<Pasajero>set=e.asignacion.keySet();
//			for(Pasajero p:set){
//				
//				System.out.println("Pasajero: "+p.getId()+"tiempo espera: "+p.getTiempoEspera()+"\t Conductor: "+e.asignacion.get(p).getId());
//			}
//			for(Conductor c:e.conductoresSinAsignar){
//				System.out.println("Conductor sin asignar: "+c.getId()+" tiempo disp: "+c.getTiempoDisponible());
//			}
//			System.out.println("Numero pasajeros sin Asignar con posibilidades: "+e.pasajerosSinAsignar.size());
//			for(Pasajero p: e.pasajerosSinAsignar){
//				System.out.println("Pasajero sin asignar: "+p.getId());
//			}
//			System.out.println(e.getNuevasSolicitudes());
//			System.out.println(e.getServiciosFinalizados());
//			System.out.println(e.getSolicitudesAsignadas());
//			System.out.println(e.getSolicitudesNoAsignadas());
//			System.out.println(e.getTiempoEsperaPromedio());
//			System.out.println(e.getVehiculosDisponiblesInicio());
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
