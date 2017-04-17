import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;
import java.lang.StringBuilder;

/**
 * A puzzle game of flipping adjacent dots to make them all match.
 * @author Orion Guan
 * @version February 28th, 2017
 */
public class DotFlipGame
{
	Scanner in;
	private static final char STATE_ONE = '+';
	private static final char STATE_TWO = '-';
	private static final int NUMBER_OF_DOTS = 7;
	
	String gameSetup;
	String gameState;
	int moveCounter;
	
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);
		long epoch = System.currentTimeMillis()/1000;
		Random rn = new Random(epoch);
		boolean playAgain = true;
		
		System.out.println("Welcome to DotFlip!");
		
		while(playAgain == true)
		{
			System.out.println("Generating game . . .");
			String gameSetup = generateGameSetup(rn);
			DotFlipGame currentGame = new DotFlipGame(gameSetup, in);
			System.out.println("Game generated.");
			currentGame.play();
			
			System.out.print("Play again? Y/N: ");
			in.nextLine();
			String yesOrNo = in.nextLine();
			if (yesOrNo.equals("y")|| yesOrNo.equals("Y")) {playAgain = true;}
			else if (yesOrNo.equals("n")|| yesOrNo.equals("N")) {playAgain = false;}
			else
			{
				System.out.println("Invalid input. I assume you don't want to play.");
				playAgain = false;
			}
		}
		
		System.out.println("See ya!");
	}
	
	/**
	 * Creates an initial state for a game of DotFlip.
	 * @param rn A random number generator.
	 * @return A String of characters representing the initial state of a game generated by this method.
	 */
	private static String generateGameSetup(Random rn)
	{
		StringBuilder gameSetupBuilt = new StringBuilder();
		while (gameSetupBuilt.indexOf("-") == -1)
		{
			gameSetupBuilt.setLength(0);
			for (int i = 0; i < (NUMBER_OF_DOTS); i++)
			{
				if (rn.nextInt(2) == 0)
				{
					gameSetupBuilt.append('-');
				}
				else
				{
					gameSetupBuilt.append('+');
				}
			}
		}
		return gameSetupBuilt.toString();
	}
	
	/**
	 * Creates a DotFlipGame object.
	 * @param gameSetup The initial state of the dots.
	 * @param in A scanner object.
	 */
	private DotFlipGame(String gameSetup, Scanner in)
	{
		this.gameSetup = gameSetup;
		this.in = in;
		moveCounter = 0;
		gameState = gameSetup;
	}
	
	/**
	 * Carries out the game of DotFlip.
	 */
	private void play()
	{
		StringBuilder idealStateBuilder = new StringBuilder();
		for (int i = 0; i < NUMBER_OF_DOTS; i++) {idealStateBuilder.append(STATE_ONE);}
		String idealState = idealStateBuilder.toString();
		StringBuilder numberGuide = new StringBuilder();
		for (int j = 0; j < NUMBER_OF_DOTS; j++)
		{
			numberGuide.append((char) ((j % 10) + 49));
		}
		System.out.println("Enter a number to flip a dot and surrounding dots. Get all +'s!");

		while (!gameState.equals(idealState))
		{
			System.out.println(numberGuide);
			System.out.println(gameState);
			int input = in.nextInt();
			if (input > 0 && input < (NUMBER_OF_DOTS + 1))
			{
				gameState = flip(gameState, input);
				moveCounter++;
			}
			else
			{
				System.out.println("Enter a number that is shown please.");
			}
		}
		System.out.println(gameState);
		System.out.printf("You won, and it took you %d moves.\n", moveCounter);
	}
	
	/**
	 * Flips a dot as well as the dots adjacent to it.
	 * @param gameState The previous state of the dots.
	 * @param input The number entered by the player representing the dot to be flipped.
	 * @return The new state of the dots.
	 */
	private String flip(String gameState, int input)
	{
		StringBuilder gameStateBuilder = new StringBuilder(gameState);
		ArrayList<Integer> threeIndices = new ArrayList<Integer>();
		threeIndices.add(input - 1);
		int previousIndex = (input != 1) ? (input - 1) : (NUMBER_OF_DOTS);
		threeIndices.add(previousIndex - 1);
		int nextIndex = (input != NUMBER_OF_DOTS) ? (input + 1) : 1;
		threeIndices.add(nextIndex - 1);
		
		for (Integer i : threeIndices)
		{
			if (gameStateBuilder.charAt(i) == STATE_ONE)
			{
				gameStateBuilder.setCharAt(i, STATE_TWO);
			}
			else if (gameStateBuilder.charAt(i) == STATE_TWO)
			{
				gameStateBuilder.setCharAt(i, STATE_ONE);
			}
		}
		return gameStateBuilder.toString();
	}
}
