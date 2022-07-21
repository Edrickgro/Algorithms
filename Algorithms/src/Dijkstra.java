import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Timer;

public final class Dijkstra {


  private Dijkstra(){
  }

  public static void start(Grid grid, Square originNode, Square targetNode){
    List<Square> unvisited = Square.unvisited;
    unvisited.add(originNode);
    List<Square> visited = new ArrayList<>();
    List<Integer> inspected = new ArrayList<>();
    inspected.add(originNode.getID());



    //List<Square> unvisited = Functional.toList(squareList);

    originNode.changeDistance(0);


    Timer timer = new Timer(100, e -> {

        Square currentNode = performOperation(visited,unvisited, grid.getSquareList(), inspected, targetNode);

      assert currentNode != null;
      if(currentNode.getID() == targetNode.getID()){
          ((Timer)e.getSource()).stop();
          Functional.drawPath(originNode, targetNode,grid);

        }

      grid.paintSquare(new Point((int) currentNode.getX()/29, (int) currentNode.getY()/29), false);


    });

    timer.start();



  }

  public static Square performOperation(List<Square> visited, List<Square> unvisited, Square[][] squareList, List<Integer> inspected, Square targetNode){
    if(unvisited.size() != 0){

      Square currentNode = Functional.lowestDistanceNode(unvisited);

      if(currentNode.isBlocked()){
        return null;
      }

      unvisited.remove(currentNode);
      visited.add(currentNode);



      List<Point> nodeNeighbors = currentNode.getNeighbors();

      //System.out.println("CurrentX: " + (int)currentNode.getX()/29 + ", CurrentY: " + (int)currentNode.getY()/29);

      for(Point point : nodeNeighbors)
        {
          //System.out.println("X: " + point.getX() + ", Y: " +  point.getY());
          if(Functional.coordinateInvalid(point)){
            continue;
          }
          Square neighborNode = squareList[(int)point.getY()][(int)point.getX()];

          if(!neighborNode.isBlocked()){
            int calculatedDistance = currentNode.getDistance() + 1;
            if(calculatedDistance < neighborNode.getDistance()){

              neighborNode.changeDistance(calculatedDistance);
              neighborNode.addNodePath(currentNode);

            }
            if(!inspected.contains(neighborNode.getID())){
              unvisited.add(neighborNode);
              inspected.add(neighborNode.getID());
            }

          }

        }

      //System.out.println("Thinking..." + currentNode.getID());



      if(currentNode.getID() == targetNode.getID()){
        return targetNode;
      }
      return currentNode;
    }

    return null;
  }






}
