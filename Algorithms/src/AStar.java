import javax.swing.Timer;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class AStar {
  private AStar(){}

  public static void start(Grid grid, Square originNode, Square targetNode){

    List<Square> unvisited = new ArrayList<>();
    List<Square> visited = new ArrayList<>();
    originNode.changeDistance(0);
    originNode.setSpecialDistance(0);
    unvisited.add(originNode);


    Timer timer = new Timer(100, e -> {

      Square currentNode = performOperation(unvisited, visited, grid.getSquareList(), targetNode, originNode);

      assert currentNode != null;
      if(currentNode.getID() == targetNode.getID()){
        ((Timer)e.getSource()).stop();
        Functional.drawPath(originNode, targetNode,grid);

      }

      grid.paintSquare(new Point((int) currentNode.getX()/29, (int) currentNode.getY()/29), false);


    });
    timer.start();
  }

  public static Square performOperation(List<Square> unvisited, List<Square> visited, Square[][] squareList, Square targetNode, Square originNode){
    if(unvisited.size() != 0){
      Square currentNode = lowestDistanceNode(unvisited, targetNode);
      if(currentNode.isBlocked()){
        return null;
      }
      unvisited.remove(currentNode);
      visited.add(currentNode);

      List<Point> nodeNeighbors = currentNode.getNeighbors();

      for(Point point: nodeNeighbors){
        if(Functional.coordinateInvalid(point)){
          continue;
        }
        Square neighborNode = squareList[(int)point.getY()][(int)point.getX()];
        if(visited.contains(neighborNode)){
          continue;
        }
        if(!neighborNode.isBlocked()){
          int g = Functional.buildingDistance(currentNode, originNode) + 1;
          //System.out.println("X: " + (int)neighborNode.getX()/29 + " Y: " + (int)neighborNode.getY()/29 + " G: " + (g));
          if(g < neighborNode.getSpecialDistance()){
            neighborNode.setSpecialDistance(g);
            neighborNode.addNodePath(currentNode);
            int h = Functional.buildingDistance(neighborNode,targetNode);
            int f = h + g;
            neighborNode.changeDistance(f);

            if(!unvisited.contains(neighborNode)){
              unvisited.add(neighborNode);
            }
          }

        }
      }

      return currentNode;
    }
    return null;
  }

  public static Square lowestDistanceNode(List<Square> squareList, Square targetNode){
    Integer smallest = Integer.MAX_VALUE;
    Square smallestNode = squareList.get(0);


    for (Square square : squareList) {
      if (square.getDistance() < smallest) {

        smallest = square.getDistance();
      }
    }

    int smallestH = Integer.MAX_VALUE;
    for(Square square: squareList){
      if(square.getDistance() == smallest){
        int h = Functional.buildingDistance(square,targetNode);
        if(h < smallestH){
          smallestH = h;
          smallestNode = square;
        }
      }
    }



    return smallestNode;
  }

}
