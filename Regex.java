package Eki;

import java.util.Scanner;

public class Regex
{
	private RegexEngine re = null;

	public Regex(String pattern){
		this.re = new RegexEngine(pattern);
	}

	public boolean find(String str){
		DFANode curr = re.getInitialState();

		for(char c : str.toCharArray()){
			curr = curr.getNextState( c );

			if(curr == null){
				return false;
			}
		}

		return curr.isAcceptState;
	}

	/**
	 * Test Code
	 **/
	public static void main(String[] args){
		Regex reg = null;

		Scanner in = new Scanner( System.in );

		String uin = "";

		while(uin != "EXIT"){
			uin = in.nextLine();
			reg = new Regex(uin);
			System.out.println( reg.find("baccca") );
		}
	}
}