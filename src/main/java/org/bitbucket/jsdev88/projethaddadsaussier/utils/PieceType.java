package org.bitbucket.jsdev88.projethaddadsaussier.utils;

import java.util.LinkedList;

/**
 * 
 * Type of the piece enum
 * 
 */
public enum PieceType {
	
	VOID(0) {
		@Override
		public LinkedList<Orientation> getConectorsList(Orientation orientation) {
			 this.getConnectors().add(orientation);
			 return this.getConnectors();
		}

		@Override
		public Orientation getOrientation(Orientation orientation) {
			return Orientation.NORTH;
		}
	}, ONECONN(1) {
		@Override
		public LinkedList<Orientation> getConectorsList(Orientation orientation) {
			this.getConnectors().add(orientation);
			return this.getConnectors();
			
		}

		@Override
		public Orientation getOrientation(Orientation orientation) {
			return orientation;
		}
	}, BAR(2) {
		@Override
		public LinkedList<Orientation> getConectorsList(Orientation orientation) {
			this.getConnectors().add(orientation);
			this.getConnectors().add(Orientation.getValueFromOrdinal((orientation.ordinal()+2) % 4));	
			return this.getConnectors();
		}

		@Override
		public Orientation getOrientation(Orientation orientation) {
			if(orientation == Orientation.SOUTH)
				return Orientation.NORTH;
			if(orientation == Orientation.WEST)
				return Orientation.EAST;
			return orientation;
		}
	}, TTYPE(3) {
		@Override
		public LinkedList<Orientation> getConectorsList(Orientation orientation) {
			this.getConnectors().add(Orientation.getValueFromOrdinal((orientation.ordinal()+3) % 4));
			this.getConnectors().add(orientation);
			this.getConnectors().add(Orientation.getValueFromOrdinal((orientation.ordinal()+1) % 4));	
			return this.getConnectors();
			
		}

		@Override
		public Orientation getOrientation(Orientation orientation) {
			return orientation;
		}
	}, FOURCONN(4) {
		@Override
		public LinkedList<Orientation> getConectorsList(Orientation orientation) {
			this.getConnectors().add(orientation);
			this.getConnectors().add(Orientation.getValueFromOrdinal((orientation.ordinal()+1) % 4));
			this.getConnectors().add(Orientation.getValueFromOrdinal((orientation.ordinal()+1) % 4));	
			this.getConnectors().add(Orientation.getValueFromOrdinal((orientation.ordinal()+1) % 4));	
			return this.getConnectors();
			
		}

		@Override
		public Orientation getOrientation(Orientation orientation) {
			return Orientation.NORTH;
		}
	}, LTYPE(2) {
		@Override
		public LinkedList<Orientation> getConectorsList(Orientation orientation) {
			this.getConnectors().add(orientation);
			this.getConnectors().add(Orientation.getValueFromOrdinal((orientation.ordinal()+1) % 4));
			return this.getConnectors();
			
		}

		@Override
		public Orientation getOrientation(Orientation orientation) {
			return orientation;
		}
	};

	private final int nbConnectors;
	private LinkedList<Orientation> connectors;


	private PieceType(int nbConnectors) {
		this.nbConnectors = nbConnectors;
		this.connectors = new LinkedList<>();
	}
	/**
	 * 
	 * @return the piece's number of connectors
	 */
	public int getNbConnectors() {
		return nbConnectors;
	}
	
	public LinkedList<Orientation> getConnectors() {
		return connectors;
	}
	
	/**
	 * 
	 * @param orientation 
	 * @return Linked List of the piece's connectors
	 */
	public abstract LinkedList<Orientation> getConectorsList(Orientation orientation);
	/**
	 * get the orientation available for the type
	 * @param orientation 
	 * @return
	 */
	public abstract Orientation getOrientation(Orientation orientation);

	/**
	 * Standardize the access of the value from the ordinal of the enum
	 * 
	 * @param ordinal
	 *            value of the orientation
	 * @return the corresponding value
	 */
	public static PieceType getValueFromOrdinal(Integer ordinal) throws IllegalArgumentException {
		if (ordinal < 0 || ordinal > 5) {
			throw new IllegalArgumentException("Ordinal of Orientation is out of bound");
		}
		return values()[ordinal];

	}
	
	

}
