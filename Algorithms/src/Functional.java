import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public final class Functional {
  private Functional(){

  }

  public static List<Square> toList(Square[][] arrayList){
    List<Square> list = new ArrayList<>();
    for(Square[] array: arrayList){
      list.addAll(Arrays.asList(array));
    }
    return list;
  }

  public static Square lowestDistanceNode(List<Square> squareList){
    Integer smallest = Integer.MAX_VALUE;
    Square smallestNode = squareList.get(0);



    for (Square square : squareList) {

      if (square.getDistance() < smallest) {

        smallest = square.getDistance();
        smallestNode = square;
      }
    }

    //System.out.println(smallestNode.getDistance());
    return smallestNode;
  }

  public static void drawPath(Square originNode, Square targetNode, Grid grid){

    Square prevNode = targetNode;
    while(prevNode != null){
      grid.paintSquare(new Point((int)prevNode.getX()/29,(int)prevNode.getY()/29),true);
      prevNode = prevNode.getNodePath();
    }

  }

  public static boolean coordinateInvalid(Point point){
    return (int)point.getY() < 0 || (int)point.getX() < 0 || (int)point.getY() > 23 || (int)point.getX() > 41;
  }
  public static int buildingDistance(Square currentPoint, Square focusedPoint){
    return (Math.abs( (int)  (currentPoint.getX()/29) - (int) (focusedPoint.getX()/29)) + Math.abs((int) (currentPoint.getY()/29) -  (int) (focusedPoint.getY()/29)));
  }

}
