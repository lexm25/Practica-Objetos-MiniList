package clases;

/**
 * Clase de Lista enlazada que sigue la interfaz MiniList
 * 
 * @author eserrano
 *
 * @param <T> Tipo de Objetos que maneja la lista
 */

public class MyList<T> implements MiniList<T> {

	private Container<T> lista = null;
	private int numElementos = 0;

	@Override
	public boolean add(T elemento) throws NullPointerException {
		if (elemento == null) {
			throw new NullPointerException("No se permiten elementos nulos");
		}
		// primero creamos el container
		Container<T> nuevo = new Container<>();
		nuevo.elemento = elemento;
		// si la lista está vacía, el primer elemento se iguala a nuevo
		if (lista == null) {
			lista = nuevo;
		} else {
			Container<T> aux = lista;

			while (aux.next != null) {
				aux = aux.next;
			}
			aux.next = nuevo;
		}
		numElementos++;

		return true;
	}

	@Override
	public boolean contains(T elemento) {
		Container<T> aux = lista;
		if (lista.elemento.equals(elemento)) {
			return true;
		} else {
			for (int i = 0; i < numElementos; i++) {
				while(aux.next!=null) {
					aux = aux.next;
					if ((aux.elemento).equals(elemento)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public boolean delete(T elemento) {
		boolean retorno = false;
		if (elemento == null || lista == null) {
			return false;
		}
		Container<T> aux = lista;
		Container<T> anterior = aux.next;
		
		if (lista.elemento.equals(elemento)) {
			lista = lista.next;
			numElementos--;
			retorno = true;
		} else {
			for (int i = 0; i < numElementos; i++) {
				while(aux.next!=null) {
					anterior = aux;
					aux = aux.next;
					if ((aux.elemento).equals(elemento)) {
						anterior.next = aux.next;
						numElementos--;
						retorno = true;
					}
				}
			}
		}
		return retorno;
	}

	@Override
	public boolean delete(int posicion) {
		boolean retorno = false;
		if (posicion >= numElementos || posicion < 0 || lista == null) {
			return false;
		}
		if (posicion == 0) {
			lista = lista.next;
			retorno = true;
		} 
		else {
			Container<T> aux = lista;
			for (int i = 0; i < posicion - 1; i++) {
				aux = aux.next;
			}
			aux.next = aux.next.next;
			retorno = true;
		}
		numElementos--;
		return retorno;
	}

	@Override
	public boolean set(int posicion, T elemento) throws IndexOutOfBoundsException {
		boolean retorno = false;
		if (posicion < 0 || posicion >= numElementos) {
			throw new IndexOutOfBoundsException("Posicion inválida");
		}
		if (elemento == null) {
			throw new NullPointerException("No se admite valores nulos");
		}
		Container<T> aux = lista;

		for (int i = 0; i < posicion; i++, aux = aux.next)
			;

		retorno = !aux.elemento.equals(elemento);
		aux.elemento = elemento;

		return retorno;
	}

	@Override
	public T get(int posicion) {
		T elemento = null;
		if (lista != null && posicion >= 0 && posicion < numElementos) {
			Container<T> aux = lista;

			int contador = 0;
			while (contador < posicion) {
				aux = aux.next;
				contador++;
			}
			elemento = aux.elemento;
		}
		return elemento;
	}

	@Override
	public int size() {
		return numElementos;
	}

	@Override
	public void clear() {
		lista = null;
		numElementos = 0;
	}

	private class Container<E> {
		E elemento;
		Container<E> next;

		Container() {
			elemento = null;
			next = null;
		}
	}
}