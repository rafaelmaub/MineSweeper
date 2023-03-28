import java.util.Random;
import java.util.Scanner;


public class Main {

  /*
  * 
  */

  // BOMBS
  static boolean bombsSet = false;
  static boolean endOfGame = false;
  static int amountOfBombs = 10;
  static int actualAmountOfBombs = 0;
  static int remainingFlags = amountOfBombs;
  static int matchSize = 9;
  static MapPoint[][] gameGrid = new MapPoint[matchSize][matchSize];

  public static void main(String[] args) {
    
    //DISPLAYING RULES:
    DisplayRules();
    
    // This declare a matrix of mappoints
    for (int a = 0; a < matchSize; a++) 
    {
      for (int b = 0; b < matchSize; b++) 
      {
        gameGrid[a][b] = new MapPoint(a, b);
      }
    }

    //RANDOMLY POSITION BOMBS AROUND THE MAP
    Random rand = new Random();

    while ((actualAmountOfBombs < 10)&&(!bombsSet)) {
      int x = rand.nextInt(gameGrid.length);
      int y = rand.nextInt(gameGrid[0].length);
      if (!gameGrid[x][y].IsBomb())
      {

        gameGrid[x][y].SetUpContent(true);
        actualAmountOfBombs++;
      }
      if (actualAmountOfBombs == amountOfBombs){
        bombsSet = true;
      }
    }
    // checking amount of bombs on adjacent tiles for EACH TILE
    for (int a = 0; a < matchSize; a++) 
    {
      for (int b = 0; b < matchSize; b++) 
      {
        int amountOfAdjacentBombs = 0;

        // GETTING ADJACENT TILE ON X + 1, X - 1, Y + 1, Y - 1
        // IMAGINE THIS AS A CARTESIAN PLAN, WHERE THERE ARE X AND Y COORDS

        for (int xDir = -1; xDir <= 1; xDir ++) {
          for (int yDir = -1; yDir <= 1; yDir ++) {

            int actualPosX = a + xDir;
            int actualPosY = b + yDir;

            // IF THERE IS NOT AN ADJACENT TILE
            if ((actualPosX < 0 || actualPosY < 0 || actualPosX >= matchSize || actualPosY >= matchSize)||((xDir == 0)&&(yDir == 0)))
              continue;

            // IF THERE IS AN ADJACENT TILE
            if (gameGrid[actualPosX][actualPosY] != null) 
            {
              if (gameGrid[actualPosX][actualPosY].IsBomb()) 
              {
                amountOfAdjacentBombs++; //COUNTING AMOUNT OF BOMBS AROUND THE TILE
              }
            }
          }
        }

        gameGrid[a][b].SetCloseBombs(amountOfAdjacentBombs);
      }
    }

    while (!endOfGame){
      DrawTable();
  
      //CURRENT STATISTICS:
      System.out.println("Remaining flags: " + remainingFlags);
  
      UserInteraction();
    }
    if (endOfGame){
      for (int i = 0; i < matchSize; i++){
        for (int j = 0; j < matchSize; j++){
          gameGrid[i][j].Select(i, j);
        }
      }
      DrawTable();
      if (actualAmountOfBombs == 0){
        System.out.println("YOU WON!");
      }else{
        System.out.println("YOU LOST!");        
      }
    }
  }

  public static void DisplayRules(){
    System.out.println("SYMBOLS: ");
    System.out.println("'[X]' = Bomb.");
    System.out.println("'[!]' = Flagged.");
    System.out.println("'[1, 2, 3 or 4]' = Amount of Bombs.");
    System.out.println("'[ ]' = Nothing.");
    System.out.println("'[#]' = Not discovered yet.");
  }
  
  public static void DrawTable() 
  {
    // MAKE TABLE ON CONSOLE SHOWING THE GRID (ABC...123...) FULL OF CELLS CONTAINING BOMB OR NOTHING
    System.out.print("    A   B   C   D   E   F   G   H   I  ");
    System.out.println();
    for (int x = 1; x <= matchSize; x++) {
      System.out.print(x + " ");
      if (x < 10) {
        System.out.print(" ");
      }
      for (int y = 0; y < matchSize; y++) {
        System.out.print("[" + gameGrid[x - 1][y].GetCurrentValue() + "] ");
      }
      System.out.println();
    }
  }

  public static void UserInteraction() //Basic function to read the input on console and select a bomb according to the input
  {
    Scanner cin = new Scanner(System.in);
    System.out.println("Enter 'reveal *space* *letter* *space* *number*' in order to reveal cell.");
    System.out.println("Enter 'flag *space* *letter* *space* *number*' in order to flag cell.");
    String userInputTemp = cin.nextLine();
    userInputTemp = userInputTemp.toLowerCase();
    try
      {
        String userInput[] = userInputTemp.split(" ");
        
        int xIndex = Integer.parseInt(userInput[2]) - 1;
        int yIndex = LettersToDigits(userInput[1]);
        
      if (userInput[0].contains("reveal"))
      {
        gameGrid[xIndex][yIndex].Select(xIndex, yIndex);
        if (gameGrid[xIndex][yIndex].IsBomb())
        {
          endOfGame = true;
        }
      }
      else if (userInput[0].contains("flag"))
      {
        gameGrid[xIndex][yIndex].SetFlag();
        if ((gameGrid[xIndex][yIndex].IsFlagged())&&!gameGrid[xIndex][yIndex].IsDiscovered())
        {
          remainingFlags--;
        }
        else if (!gameGrid[xIndex][yIndex].IsDiscovered())
        {
          remainingFlags++;
        }
        if (gameGrid[xIndex][yIndex].IsBomb())
        {
          actualAmountOfBombs--;
          if (actualAmountOfBombs == 0){
            endOfGame = true;
          }
        }
      }
    }
    catch(Exception e){
      System.out.println("You must enter commands in the required manner.");
    }
    
  }
  
  public static int LettersToDigits(String letter){
    return "ABCDEFGHIJKLMNOPQRSTUVWXYZ".indexOf(letter.toUpperCase());
  }
}