package org.bitbucket.jsdev88.projethaddadsaussier.utils;

import java.util.ArrayList;

/**
 * 
 * Type of the piece enum
 * 
 */
public enum PieceType {
	
	VOID(0) {
		@Override
		public ArrayList<Connector> createConnectors(int posXPiece, int posYPiece, Orientation orientation, ArrayList<Connector> connectorsList) {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public ArrayList<Connector> setConnectors(Orientation orientation, ArrayList<Connector> connectorsList) {
			return null;
		}

		
	}, 
	ONECONN(1) {
		@Override
		public ArrayList<Connector> createConnectors(int posXPiece, int posYPiece, Orientation orientation, ArrayList<Connector> connectorsList) {
			connectorsList.add(new Connector(posXPiece,posYPiece));
			setConnectors(orientation,connectorsList);
			return connectorsList;
		}
		@Override
		public ArrayList<Connector> setConnectors(Orientation orientation, ArrayList<Connector> connectorsList) {
			switch(orientation){
			case NORTH: connectorsList.get(0).setCoordinates(0, -1);
			case EAST:	connectorsList.get(0).setCoordinates(1, 0);
			case SOUTH: connectorsList.get(0).setCoordinates(0, 1);
			case WEST: 	connectorsList.get(0).setCoordinates(0, 1);
			}
			return null;
		}

		
	}, 
	BAR(2) {
		@Override
		public ArrayList<Connector> setConnectors(Orientation orientation, ArrayList<Connector> connectorsList) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public ArrayList<Connector> createConnectors(int posXPiece, int posYPiece, Orientation orientation, ArrayList<Connector> connectorsList) {
			// TODO Auto-generated method stub
			return null;
		}
	}, 
	TTYPE(3) {
		@Override
		public ArrayList<Connector> setConnectors(Orientation orientation, ArrayList<Connector> connectorsList) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public ArrayList<Connector> createConnectors(int posXPiece, int posYPiece, Orientation orientation, ArrayList<Connector> connectorsList) {
			// TODO Auto-generated method stub
			return null;
		}
	}, 
	FOURCONN(4) {
		@Override
		public ArrayList<Connector> setConnectors(Orientation orientation, ArrayList<Connector> connectorsList) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public ArrayList<Connector> createConnectors(int posXPiece, int posYPiece, Orientation orientation, ArrayList<Connector> connectorsList) {
			// TODO Auto-generated method stub
			return null;
		}
	}, 
	LTYPE(2) {
		@Override
		public ArrayList<Connector> setConnectors(Orientation orientation, ArrayList<Connector> connectorsList) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public ArrayList<Connector> createConnectors(int posXPiece, int posYPiece, Orientation orientation, ArrayList<Connector> connectorsList) {
			// TODO Auto-generated method stub
			return null;
		}
	};

	private final int nbConnectors;


	private PieceType(int nbConnectors) {
		this.nbConnectors = nbConnectors;
	}
	/**
	 * 
	 * @return the piece's number of connectors
	 */
	public int getNbConnectors() {
		return nbConnectors;
	}
	
	/**
	 * Instantiate the connectors with the orientation enum
	 * @param orientation
	 * @return the list of connectors with the right coordinates
	 */
	public abstract ArrayList<Connector> setConnectors(Orientation orientation, ArrayList<Connector> connectorsList );
	public abstract ArrayList<Connector> createConnectors(int posXPiece, int posYPiece, Orientation orientation, ArrayList<Connector> connectorsList);

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
