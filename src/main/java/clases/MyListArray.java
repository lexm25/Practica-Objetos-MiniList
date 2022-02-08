package clases;

import java.util.Iterator;

/**
 * Clase de Lista enlazada que sigue la interfaz MiniList
 * @author eserrano
 *
 * @param <T> Tipo de Objetos que maneja la lista
 */
public class MyListArray<T> implements MiniList<T> {

	/**
	 * Array que mantiene los elementos de la lista
	 */
	private T[] lista;
	
	/**
	 * Variable entera que lleva la cuenta de elementos en la lista
	 */
	private int numElementos;
	
	public MyListArray() {
		lista = (T[]) new Object[10];
		numElementos=0;
	}
	
	@Override
	public boolean add(T elemento) throws NullPointerException {
		boolean retorno = false;
		if (elemento == null) {
			throw new NullPointerException("No se admiten elementos nulos");
		}
		else {
			//cuando la lista no tenga más huecos, es decir, 
			//el numero de elementos excede la longitud de la lista
			//se crea una lista auxiliar para aumentar de tamaño la lista
			if(lista.length <= numElementos) {
				T[] aux = lista;
				lista = (T[]) new Object[numElementos*2];
					for(int i = 0; i < aux.length;i++) {
						lista[i] = aux[i];
					}
			}
			lista[numElementos] = elemento;
			numElementos++;
			retorno = true;
		}
		return retorno;
	}

	@Override
	public boolean contains(T elemento) {
		boolean retorno = false;
		if(elemento!=null) {
			for (int i = 0; i < lista.length; i++) {
				if(lista[i]==elemento) {
					return true;
				}
			}
		}
		return retorno;
	}

	@Override
	public boolean delete(T elemento) {
		boolean retorno = false;
		if(elemento == null) {
			retorno = false;
		}
		//primero recorro la lista para encontrar el elemento a eliminar
		else {
			for(int i = 0;i<lista.length;i++) {
				if(lista[i]==elemento) {
					lista[i]=null;
					//recorro la lista para mover los elementos una posicion hacia atrás
					for (int j = i; j < lista.length-1; j++) {
						lista[j]=lista[j+1];
					}
					//borro el ultimo elemento de la lista que queda duplicado
					lista[numElementos-1]=null;
					numElementos--;
					retorno = true;
					break;
				}
			}
		}
		return retorno;
	}

	@Override
	public boolean delete(int posicion) {
		boolean retorno = false;
		if(posicion<0 || posicion>=numElementos) {
			retorno = false;
		}
		//recorro la lista para mover cada elemento uno hacia atrás
		else {
			for (int i = posicion; i < lista.length-1; i++) {
				lista[i]=lista[i+1];
			}
			//borro el ultimo elemento de la lista que queda duplicado
			lista[numElementos-1]=null;
			numElementos--;
			retorno = true;
		}
		return retorno;
	}

	@Override
	public boolean set(int posicion, T elemento) throws IndexOutOfBoundsException {
		boolean retorno = false;
		if (elemento==null) {
			throw new NullPointerException();
		}
		if (posicion<0 || posicion >= numElementos){
			throw new IndexOutOfBoundsException();
		}
		//si en la posicion está el mismo elemento, no se cambia nada
		if(lista[posicion] == elemento) {
			retorno = false;
		}
		//si no es el mismo, se cambia
		else {
			lista[posicion]=elemento;
			retorno = true;
		}
		return retorno;
	}

	@Override
	public T get(int posicion) {
		T elemento = null;
		if(posicion<0 || posicion>=numElementos) {
			elemento = null;
		}
		else {
			elemento = lista[posicion];
		}
		return elemento;
	}

	@Override
	public int size() {
		return numElementos;
	}

	@Override
	public void clear() {
		numElementos=0;
		for(int i = 0;i<lista.length;i++) {
			lista[i]=null;
		}
	}
}