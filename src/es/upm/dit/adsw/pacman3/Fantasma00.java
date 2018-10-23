package es.upm.dit.adsw.pacman3;

import java.awt.Image;
import java.net.URL;
import java.util.Random;

import javax.swing.ImageIcon;

public class Fantasma00 extends Movil implements Runnable {
	 private  Terreno terreno;

	    /**
	     * Casilla en la que esta.
	     */
	    private Casilla casilla;

	    /**
	     * TRUE si no ha muerto.
	     */
	    private boolean vivo;
	    public Fantasma00(Terreno terreno){
	    	  this.terreno = terreno;
	    	  Random color = new Random();
	    	  String [] fantasmas = {"FantasmaRojo.png", "FantasmaAmarillo.png", "FantasmaVerde.png", "FantasmaRosa.png"};
	          setImage(fantasmas[color.nextInt(fantasmas.length)]);
	          vivo = true;
	    }
	@Override
	public void run() {
		while(vivo){
			Random random = new Random();
			Direccion[] direcciones = Direccion.values();
			Direccion d = direcciones[random.nextInt(direcciones.length)];
			terreno.move(this, d);
			try{ 
				Thread.sleep(500);
			}
			catch (InterruptedException e){
				return;
			}
		}

		
	}

	@Override
	public Casilla getCasilla() {
		
		return casilla;
	}

	@Override
	public void setCasilla(Casilla casilla) {
	    this.casilla = casilla;
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
	        Casilla micasilla = casilla.siguiente(direccion);
	        Movil fantasma = micasilla.getMovil();
	        if (fantasma instanceof Fantasma00 || (casilla.isTrampa() ))
	        	return 1;
	        
	        return 0;
	}
	
	 
	
}

		