import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.Point;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.List;


public class Square{
  int size;
  int x;
  int y;
  Shape shape;
  boolean blocked = false;
  Square connectingSquare;
  Integer distance = Integer.MAX_VALUE;
  Integer specialDistance = Integer.MAX_VALUE;
  List<Point> neighbors =  new ArrayList<>();
  int mazeCount = 0;
  static List<Square> unvisited = new ArrayList<>();
  static int counter = 0;

  int ID;
  Image pixelImage;



  Square(Shape shape,int size, int x, int y){
    this.pixelImage = new ImageIcon("bluePixil.png").getImage();
    this.size = size;
    this.x = x;
    this.y = y;
    this.shape = shape;
    counter++;
    if(counter > 1008){
      counter = 1;
    }
    this.ID = counter;

    calculateNeighbors();

  }
  public boolean encloses(float x, float y){
    return shape.contains(x,y);
  }
  public float getX(){
    return x;
  }
  public float getY(){
    return y;
  }
  public float getSize(){
    return size;
  }
  public Shape getShape(){
    return shape;
  }
  public void blockSquare(boolean statement){
    this.blocked = statement;
  }
  public boolean isBlocked(){
    return blocked;
  }
  public void changeDistance(int dis){
    this.distance = dis;
  }

  public int getDistance(){
    return this.distance;
  }

  public int getID(){
    return ID;
  }

  public void calculateNeighbors(){
    int currX = this.x/29;
    int currY = this.y/29;
    neighbors.add(new Point(currX + 1,currY));
    neighbors.add(new Point(currX - 1,currY));
    neighbors.add(new Point(currX,currY + 1));
    neighbors.add(new Point(currX,currY - 1));
  }
  public List<Point> getNeighbors(){
    return neighbors;
  }

  public void addNodePath(Square node){
    connectingSquare = node;
  }

  public Square getNodePath(){
    return connectingSquare;
  }

  public Image getImage(){
    return pixelImage;
  }

  public void changeImage(String image){
    this.pixelImage =  new ImageIcon(image).getImage();
  }

  public void reset(){
      blocked = false;
      this.changeImage("bluePixel.png");
      distance = Integer.MAX_VALUE;
      unvisited = new ArrayList<>();
      connectingSquare = null;
      mazeCount = 0;
  }

  public int getSpecialDistance(){
    return specialDistance;
  }

  public void setSpecialDistance(int newDistance){
    specialDistance = newDistance;
  }

  public int getMazeCount(){
    return mazeCount;
  }

  public void setMazeCount(int mazeCount){
    this.mazeCount = mazeCount;
  }








}
