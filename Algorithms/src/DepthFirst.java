import javax.swing.Timer;
import java.awt.Point;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class DepthFirst {
  private DepthFirst(){
  }
  public static void start(Grid grid, Square originNode, Square targetNode){

    Deque<Square> stack = new ArrayDeque<>();
    ArrayList<Square> visited = new ArrayList<>();

    stack.add(originNode);


    Timer timer = new Timer(100, e -> {

      Square currentNode = performOperation(visited, stack, grid.getSquareList());

      assert currentNode != null;
      if(currentNode.getID() == targetNode.getID()){
        ((Timer)e.getSource()).stop();
        Functional.drawPath(originNode, targetNode,grid);

      }

      grid.paintSquare(new Point((int) currentNode.getX()/29, (int) currentNode.getY()/29), false);

    });

    timer.start();
  }

  public static Square performOperation(ArrayList<Square> visited, Deque<Square> stack,Square[][] squareList){
    if(!stack.isEmpty()){
      Square currentNode = stack.removeLast();

      visited.add(currentNode);

      List<Point> nodeNeighbors = currentNode.getNeighbors();
      for(Point point: nodeNeighbors){
        if(Functional.coordinateInvalid(point)){
          continue;
        }
        Square neighborNode = squareList[(int)point.getY()][(int)point.getX()];
        if(!visited.contains(neighborNode) && !stack.contains(neighborNode) && !neighborNode.isBlocked()){
          stack.addLast(neighborNode);
        }
      }

      stack.getLast().addNodePath(currentNode);
      return currentNode;
    }
    return null;
  }
}
