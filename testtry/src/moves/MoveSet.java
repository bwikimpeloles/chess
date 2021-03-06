package moves;

import java.util.ArrayList;

import boards.Board;

/**
 * Specialized List for the purpose of chess board additions
 * and checking to see if possible moves are allowed.
 * @author Drew Grubb
 */
public class MoveSet
{
	private Board board;
	private ArrayList<Move> moves;
	
	/**
	 * Initiates new Set of moves 
	 */
	public MoveSet(Board board)
	{
		this.board = board;
		moves = new ArrayList<Move>();
	}
	
	/**
	 * Checks to see if possibleMove is legal, and adds it to the Moveset if it is. 
	 * 
	 * There are several steps to a legal move:
	 * - Is the move on the board
	 * - Is the move on an empty space or onto an enemy piece
	 * - Does performing this move put a friendly King in check
	 * 
	 * Returns true if an unavailable piece was hit for loop breaking
	 * @param move
	 */
	public boolean tryMove(Move move, boolean needsVerification)
	{
		if(!board.isAvailableSpace(move.getNewPosition(), board.getPiece(move.getPreviousPosition()).getPieceColor()))
			return true;
		
		//If this move needs to be checked, check it
		if(needsVerification)
		{
			int preMoveScore = board.getCurrentScore();
			
			board.performMove(move);
			
			//If it does not put the player's king in check
			if(board.isInCheck(board.getPiece(move.getNewPosition()).getPieceColor()) == false)
			{
				moves.add(move);
				moves.get(moves.size() - 1).setScore(Math.abs(preMoveScore - board.getCurrentScore()));
			}
			
			board.undoLastMove();
		}
		else
		{
			moves.add(move);
		}
		
		//Is position enemy piece
		//If the piece is available it is either empty or an enemy piece.
		if(board.isEmptySpace(move.getNewPosition()) == false)
			return true;
		
		return false;
	}
	
	/**
	 * Checks to see if a move exists in the moveset.
	 * @param move
	 * @return
	 */
	public boolean containsMove(Move move)
	{
		return moves.contains(move);
	}
	
	/**
	 * Removes the last move added to the list
	 * Used only for castling
	 */
	public void removeLastMove()
	{
		moves.remove(moves.size() - 1);
	}
	
	/**
	 * Checks to see if an origin position exists in the move set.
	 * @param pos being searched for.
	 * @return does position exist in move set.
	 */
	public boolean containsPrev(Position pos)
	{
		for(Move move : moves)
			if(move.getNewPosition().equals(pos))
				return true;
		return false;
	}
	
	/**
	 * Checks to see if a destination position exists in the current moveset.
	 * @param pos destination being searched for
	 * @return if the destination exists in the move set.
	 */
	public boolean containsDest(Position pos)
	{
		for(Move move : moves)
			if(move.getNewPosition().equals(pos))
				return true;
		return false;
	}
	
	/**
	 * Returns the move at the specific index in the move set.
	 * Used for Artificial Intelligence move calculations.
	 * @param index
	 * @return the move at the index
	 */
	public Move getMove(int index)
	{
		if(index >= moves.size() || index < 0)
			return null;
		
		return moves.get(index);
	}

	/**
	 * @return
	 */
	public int size()
	{
		return moves.size();
	}
	
	@Override
	public String toString()
	{
		String string = "Moveset: ";
		for(Move move : moves)
			string += move + " : ";
		return string;
	}
}
