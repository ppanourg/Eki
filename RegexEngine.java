package Eki;
import java.util.HashMap;

class RegexEngine{
	private String pattern = null;
	private DFANode head   = null;

    RegexEngine(String pattern){
    	this.pattern = pattern;
    	this.head = compile( this.pattern );
	}

	public DFANode getInitialState(){ return head; }

	private DFANode compile(String pattern){
		DFANode head = new DFANode( false );
		DFANode curr = head;

		for(int state = 0; state < pattern.length(); ++state){
			char next = pattern.charAt( state );

			if(next == '*'){
				curr.loopsToSelf = true;
			}
			else{
				curr.addNext( next );
				curr = curr.traverseToState( next );
			}
		}

		curr.isAcceptState = true;
		return head;
	}
}

class DFANode
{
	public  boolean isAcceptState;
	public  boolean loopsToSelf;
	public  DFANode previousState;
	private HashMap<Character, DFANode> nextStates = null;


	public DFANode(){
		nextStates = new HashMap<>();
		this.isAcceptState = false;
		this.loopsToSelf   = false;
		this.previousState = null;
	}

	public DFANode(boolean isAcceptState){
		nextStates = new HashMap<>();
		this.isAcceptState = isAcceptState;
		this.loopsToSelf   = false;
		this.previousState = null;
	}

	public DFANode getNextState(char state){
		if(this.equals( previousState )){
			previousState = this;
			return this;
		}

		if(nextStates.containsKey( state )){
			return nextStates.get( state );
		}

		return null;
	}

	public boolean addNext(char value){
		if(!nextStates.containsKey( value )){
			nextStates.put( value, new DFANode() );
			return true;
		}

		return false;
	}

	public DFANode traverseToState(char value){
		return nextStates.get( value );
	}

	@Override
	public boolean equals(Object obj){
		if( (obj == null) || (this.getClass() != obj.getClass()) ){
			return false;
		}

		DFANode node = (DFANode)( obj );

		return ( node.isAcceptState == this.isAcceptState  ) &&
		       ( node.loopsToSelf   == this.loopsToSelf    ) &&
		       ( this.nextStates.equals( node.nextStates ) ) ;
	}
}