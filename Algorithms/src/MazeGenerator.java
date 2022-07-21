import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public final class MazeGenerator {
  private MazeGenerator(){}

  public static ArrayList<Point> basicBinary(int numRows, int numCols, Square[][] squareList, Point originPoint, Point targetPoint){
    ArrayList<Point> blockCollection = new ArrayList<>();
    for(int i = 0; i < numRows; i++){
      for(int j = 0; j < numCols; j++){

        Square block = squareList[i][j];

        double randomNum = Math.random();
        if ((randomNum > .90) && !Functional.coordinateInvalid(block.getNeighbors().get(0))) {
          Square squareNeighbor = squareList[(int) block.getNeighbors().get(0).getY()][(int) block.getNeighbors().get(0)
              .getX()];
          if(enclosesCriticalBlock(squareNeighbor,originPoint,targetPoint)){
            continue;
          }
          squareNeighbor.blockSquare(true);
          blockCollection.add(block.getNeighbors().get(0));

        } else if(randomNum > .70 && !Functional.coordinateInvalid(block.getNeighbors().get(1))){
          Square squareNeighbor = squareList[(int) block.getNeighbors().get(1).getY()][(int) block.getNeighbors().get(1)
              .getX()];
          if(enclosesCriticalBlock(squareNeighbor,originPoint,targetPoint)){
            continue;
          }
          squareNeighbor.blockSquare(true);
          blockCollection.add(block.getNeighbors().get(1));
        }

      }
    }
    return blockCollection;
  }

  private static boolean enclosesCriticalBlock(Square block, Point originPoint, Point targetPoint){
    return block.encloses((float) originPoint.getX(), (float) originPoint.getY()) || block.encloses(
        (float) targetPoint.getX(), (float) targetPoint.getY());
  }
}
