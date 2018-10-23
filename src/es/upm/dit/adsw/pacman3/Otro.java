package es.upm.dit.adsw.pacman3;

import java.awt.Image;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.swing.ImageIcon;

public class Otro extends Movil implements Runnable {
	 private  Terreno terreno;

	    /**
	     * Casilla en la que esta.
	     */
	    private Casilla casilla;

	    /**
	     * TRUE si no ha muerto.
	     */
	    private boolean vivo;
	    
	    private Casilla move;
	    private List<Casilla> pendientes =  new ArrayList<Casilla>(); 
		private Map<Casilla, Casilla> visitadas =  new HashMap<Casilla, Casilla>(); 
		
	    
	    public Otro(Terreno terreno){
	    	  this.terreno = terreno;
	          setImage("Fran2.png");
	          vivo = true;
	    }
	
	@Override
	public void run() {
		while(vivo ){
			if(hayPresa()){
			

				Casilla c= primerPaso(getPresa(), casilla);

				terreno.move(this, casilla.getDireccion(c));


				pendientes.clear();
				visitadas.clear();
				setCasilla(casilla);
				}else{
				
				
					Random random = new Random();
					Direccion[] direcciones = Direccion.values();
					Direccion d = direcciones[random.nextInt(direcciones.length)];
					terreno.move(this, d);
				}
			
				try{ 
					Thread.sleep(500);
				}
				catch (InterruptedException e){
					return;
				}
			
		}
	}


	public Casilla primerPaso(Casilla origen, Casilla destino){
		pendientes.add(origen);
		if (bfs(destino, pendientes, visitadas)){
			return visitadas.get(destino);

		}
		return null;
	}
	
	private boolean bfs(Casilla destino, List<Casilla> pendientes,  Map<Casilla, Casilla> visitadas) { 

		while (!pendientes.isEmpty()){ //		si no hay pendientes, devuelve FALSE 

			Casilla c1 = pendientes.remove(0);

			if (c1 == destino){
				return true;
			}

			for (Direccion direccion : Direccion.values()) { 
				Casilla c2 =  c1.siguiente(direccion);


				if ( !terreno.hayPared(c1, direccion) && !visitadas.containsKey(c2) && (c2 != null) ){ 


					visitadas.put(c1.siguiente(direccion), c1);
					pendientes.add(c1.siguiente(direccion));

				}

				
				
				this.pendientes=pendientes;
				this.visitadas=visitadas;

			}
//			pendientes.remove(pendientes.get(0));

		}
		return false;

	}

	@Override
	public Casilla getCasilla() {
		
		return casilla;
	}

	@Override
	public void setCasilla(Casilla casilla) {
	    this.casilla = casilla;
	    if (casilla.isLlave()){
	    	terreno.quitaLlavesTrampas();
	    }
	   
        if (casilla.isObjetivo()) {
            terreno.setEstado(EstadoJuego.GANA_JUGADOR);
            vivo = false;
        }
		
	}

	@Override
	public void muere(boolean devorado) {
		if (terreno.getEstadoJuego()==EstadoJuego.PIERDE_JUGADOR )
            terreno.setEstado(EstadoJuego.PIERDE_JUGADOR);
        vivo = false;
		
	}

	@Override
	public int puedoMoverme(Direccion direccion) {
		 if (!vivo)
	            return 2;
	        if (terreno.hayPared(casilla, direccion))
	            return 2;
	       
	        if ((casilla.isTrampa() ))
	        	return 1;
	       
	        if (casilla.isLlave()){
	        	terreno.quitaLlavesTrampas();
	        	return 0;
	        }
	        
	        return 0;
	}
	

	private Casilla getPresa(){
		for (int i=0; i<terreno.getN(); i++){
			for (int j=0; j<terreno.getN(); j++){
				if (terreno.getCasilla(i, j).isLlave()){
					Casilla	presa= terreno.getCasilla(i, j);
					
					return presa;
				}

			}

		}
		return null;
	}

	private boolean hayPresa(){
		for (int i=0; i<terreno.getN(); i++){
			for (int j=0; j<terreno.getN(); j++){
				if (terreno.getCasilla(i, j).isLlave()){
					return true;

				}



			}
		}
		return false;
	}
}


