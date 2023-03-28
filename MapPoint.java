public class MapPoint {
  /*
   * This class represents a cube/spot in the whole grid in MineSweeper
   * It can have a bomb, nothing, or a number indicating near bombs
   * "[X]" = Bomb.
   * "[1, 2, 3 or 4]" = Amount of Bombs.
   * "[ ]" = Nothing.
   * "[#]" = Not discovered yet.
   */
  boolean discovered;
  boolean bomb;
  boolean flagged;
  char amountOfBombs;
  
  public MapPoint(int row, int column) 
  {
    // store my position in the map
  }

  public boolean IsBomb()
  {
    return bomb;
  }

  public boolean IsDiscovered()
  {
    return discovered;
  }
  
  public boolean IsFlagged()
  {
    return flagged;
  }

  public void SetFlag (){
    if (!discovered){
      flagged = !flagged;
    }
  }
  
  public void SetCloseBombs(int amountClose) 
  {
    amountOfBombs = Character.forDigit(amountClose, 10);
  }

  public void SetUpContent(boolean isBomb) {
    // Detect adjacent tiles
    // set content acording to "isBomb" bool
    bomb = isBomb;
    if (!isBomb) {

    }
  }

  public void Select(int x, int y) 
  {
    // show adjacent tiles values
    if (!flagged && !discovered){
      for (int i = -1; i < 2; i ++){
        for (int j = -1; j < 2; j++){
          if ((i == 0 && j == 0)||(x + i < 0 || y + j < 0 || x + i >= Main.matchSize || y + j >= Main.matchSize)){
            continue;
          }
          discovered = true;
          if (amountOfBombs == '0'){
            Main.gameGrid[x + i][y + j].Select(x + i, y + j);
          }
        }
      }
    }
    
  }

  
  public char GetCurrentValue() {
    // RETURN ACTUAL VALUE
    // "[X]" = Bomb.
    // "[!]" = Flagged.
    // "[1, 2, 3 or 4]" = Amount of Bombs.
    // "[ ]" = Nothing.
    // "[#]" = Not discovered yet.
    
    if (discovered) 
    {
      if (bomb) 
      {
        return 'X';
      } 
      else if (amountOfBombs != '0')
      {
        return amountOfBombs;
      }
      else
      {
        return ' ';
      }
    } 
    else if (flagged){
      return '!';
    }
    else
    {
      return '#';
    }

  }


     

}