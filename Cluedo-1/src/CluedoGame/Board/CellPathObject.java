package CluedoGame.Board;

public class CellPathObject implements Comparable<CellPathObject>{

	private CorridorCell currCell;
	private CorridorCell prevCell;
	private int costToHere;
	private int estTotal;
	
	
	
	public CellPathObject(CorridorCell currCell, CorridorCell prevCell, int costToHere, int estTotal){
		this.currCell = currCell;
		this.prevCell = prevCell;
		this.costToHere = costToHere;
		this.estTotal = estTotal;
	}
	
	
	public int getCostToHere(){
		return this.costToHere;
	}
	
	public CorridorCell getCell(){
		return currCell;
	}
	
	public CorridorCell getFrom(){
		return prevCell;
	}
	
	
	@Override
	public int compareTo(CellPathObject other) {
		if (this.estTotal < other.estTotal){return -1;}
		else if (this.estTotal > other.estTotal){return 1;}
		else {return 0;}	
	}
}
