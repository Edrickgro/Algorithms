
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import java.util.ArrayList;

public class Grid extends JPanel {
  Point currNode_Point = new Point(7,7);
  Point prevNode_Point;

  Point currTNode_Point = new Point(35,6);
  Point prevTNode_Point;


  final int oNODE_SIZE = 22;

  float tNodeXCenter = (float) (currTNode_Point.getX() - (oNODE_SIZE/2));
  float tNodeYCenter = (float) (currTNode_Point.getY() - (oNODE_SIZE/2));
  float tNodeX = (float) currTNode_Point.getX();
  float tNodeY = (float) currTNode_Point.getY();

  float nodeXCenter = (float) (currNode_Point.getX() - (oNODE_SIZE/2));
  float nodeYCenter = (float) (currNode_Point.getY() - (oNODE_SIZE/2));
  float nodeX = (float) currNode_Point.getX();
  float nodeY = (float) currNode_Point.getY();
  final int gridRows = 24;
  final int gridCols = 42;
  Square[][] squareList = new Square[gridRows][gridCols];

  //fill lists for all types of squares
  ArrayList<Point> fillList = new ArrayList<>();
  ArrayList<Point> pathList = new ArrayList<>();
  ArrayList<Point> blockList = new ArrayList<>();

  boolean active = false;
  InitiateGrid grid;
  int OFFSET = 3;
  int size = 29;


  Shape oNode;
  Shape targetNode;

  Image oNodeImage = new ImageIcon("startingNode.png").getImage();
  Image targetNodeImage = new ImageIcon("targetNode.png").getImage();

  boolean isBlockMode = false;

  Grid(){
    setLayout(new BorderLayout());
    this.setPreferredSize(new Dimension(1400,1010));
    grid = new InitiateGrid();
    //grid.setBackground(new Color(230, 211, 179));
    this.add(grid,BorderLayout.CENTER);


    ClickListener clickListener = new ClickListener();
    DragListener dragListener = new DragListener();
    this.addMouseListener(clickListener);
    this.addMouseMotionListener(dragListener);


    this.setVisible(true);

    for(int i = 0; i < gridRows; i++){
      for(int j = 0; j < gridCols; j++){
        Shape rootRect = new Rectangle2D.Float((j * size + OFFSET),((size * i) + OFFSET), size, size);
        Square block = new Square(rootRect,size,(j * size + OFFSET),((size * i) + OFFSET));
        squareList[i][j] = block;
      }
    }



  }

  private class InitiateGrid extends JComponent{
    Graphics2D graph2;

    @Override
    protected void paintComponent(Graphics g){
      super.paintComponent(g);
      graph2 = (Graphics2D) g;
      //graph2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      graph2.setColor(Color.BLACK);

      for(int i = 0; i < gridRows; i++){
        for(int j = 0; j < gridCols; j++){
          Square block = squareList[i][j];

          graph2.draw(block.getShape());
        }
      }


      //** BLOCK LIST RED **//

      for(Point square: blockList){
        Square tempSquare = squareList[(int)square.getY()][(int)square.getX()];
        tempSquare.changeImage("redPixil.png");
        graph2.drawImage(tempSquare.getImage(), (int) tempSquare.getX(), (int) tempSquare.getY(),null);

      }

      targetNode = new Ellipse2D.Float(tNodeX,tNodeY, oNODE_SIZE, oNODE_SIZE);
      graph2.setColor(Color.PINK);
      graph2.draw(targetNode);
      graph2.drawImage(targetNodeImage,(int)tNodeX -1 ,(int) tNodeY -1, null);

      oNode = new Ellipse2D.Float(nodeX,nodeY,oNODE_SIZE,oNODE_SIZE);
      graph2.setColor(Color.BLACK);
      graph2.draw(oNode);
      graph2.drawImage(oNodeImage, (int) (nodeX) -1 , (int) (nodeY) - 1,null);

      if(active){
        //** FILL LIST BLUE **//
        for (Point square : fillList) {
          int tempX = (int) square.getX();
          int tempY = (int) square.getY();
          Square tempSquare = squareList[tempY][tempX];
          tempSquare.changeImage("bluePixil.png");
          //graph2.fill(tempSquare.getShape());
          graph2.drawImage(tempSquare.getImage(), (int) tempSquare.getX(), (int) tempSquare.getY(),null);
        }

        //** PATH LIST GREEN **//
        graph2.setColor(Color.PINK);
        for(Point square: pathList){
          Square tempSquare = squareList[(int)square.getY()][(int)square.getX()];
          tempSquare.changeImage("greenPixil.png");
          graph2.drawImage(tempSquare.getImage(), (int) tempSquare.getX(), (int) tempSquare.getY(),null);
        }

      }

    }

  }
  private class ClickListener extends MouseAdapter{
    public void mousePressed(MouseEvent e){
      int x = (int) e.getPoint().getX()/29;
      int y = (int) e.getPoint().getY()/29;
      if(oNode.contains(e.getPoint().getX(),e.getPoint().getY())){
        prevNode_Point = e.getPoint();
      }else if(targetNode.contains(e.getPoint().getX(),e.getPoint().getY())){
        prevTNode_Point = e.getPoint();
      }else if(isBlockMode){

        blockList.add(new Point(x,y));
        squareList[y][x].blockSquare(true);
        repaint();
      }else if(!isBlockMode){
        squareList[y][x].blockSquare(false);
        blockList.remove(new Point(x,y));
        repaint();
      }
    }
    public void mouseReleased(MouseEvent e){
      if(oNode.contains(e.getPoint().getX(),e.getPoint().getY())){
        //System.out.println("MOUSE yx: " + (int)e.getPoint().getY()/29 + " " + (int)e.getPoint().getX()/29);
        snapNode(nodeXCenter,nodeYCenter,true);
      }else if(targetNode.contains(e.getPoint().getX(), e.getPoint().getY())){
        snapNode(tNodeXCenter,tNodeYCenter,false);
      }


    }

    public void snapNode(float x, float y, boolean isOrigin){
      Square tempSquare = squareList[(int)y/29][(int)x/29];
      if(!tempSquare.isBlocked()){
        if(isOrigin){
          nodeX = tempSquare.getX() + tempSquare.getSize()/2 - (float)oNODE_SIZE/2;
          nodeY = tempSquare.getY() + tempSquare.getSize()/2 - (float)oNODE_SIZE/2;
        }else{
          tNodeX = tempSquare.getX() + tempSquare.getSize()/2 - (float)oNODE_SIZE/2;
          tNodeY = tempSquare.getY() + tempSquare.getSize()/2 - (float)oNODE_SIZE/2;

        }
      }else{
        if(isOrigin){
          nodeX = 7;
          nodeY = 7;
        }else{
          tNodeX = 35;
          tNodeY = 6;
        }
      }
      repaint();
    }

  }
  private class DragListener extends MouseMotionAdapter{
    public void mouseDragged(MouseEvent e){
      if(prevNode_Point != null && oNode.contains(e.getPoint().getX(),e.getPoint().getY())){
        currNode_Point.translate(
            (int)(e.getPoint().getX() - prevNode_Point.getX()),
            (int)(e.getPoint().getY() - prevNode_Point.getX())

        );
        currNode_Point = e.getPoint();
        nodeXCenter = (float) (currNode_Point.getX() - (oNODE_SIZE/2));
        nodeYCenter = (float) (currNode_Point.getY() - (oNODE_SIZE/2));
        nodeX = nodeXCenter;
        nodeY = nodeYCenter;

        repaint();
      }else if(prevTNode_Point != null && targetNode.contains(e.getPoint().getX(),e.getPoint().getY())){
        currTNode_Point.translate(
            (int)(e.getPoint().getX() - prevTNode_Point.getX()),
            (int)(e.getPoint().getY() - prevTNode_Point.getX())
        );

        currTNode_Point = e.getPoint();
        tNodeXCenter = (float) (currTNode_Point.getX() - (oNODE_SIZE/2));
        tNodeYCenter = (float) (currTNode_Point.getY() - (oNODE_SIZE/2));
        tNodeX = tNodeXCenter;
        tNodeY = tNodeYCenter;

        repaint();

      }

    }


  }

  public Square[][] getSquareList(){
    return squareList;
  }


  public void paintSquare(Point newPoint, Boolean isPath){
    active = true;
    if(isPath){
      pathList.add(newPoint);
    }else{
      fillList.add(newPoint);

    }
    repaint();
  }

  public Square getOriginNode(){
    return squareList[(int)currNode_Point.getY()/29][(int)currNode_Point.getX()/29];
  }

  public Square getTargetNode(){
    //return squareList[(int)290/29][(int)290/29];
    return squareList[(int)currTNode_Point.getY()/29][(int)currTNode_Point.getX()/29];
  }

  public void switchBlockMode(boolean change){
    isBlockMode = change;
  }

  public boolean getBlockMode(){
    return isBlockMode;
  }

  public void reset(){
    nodeX = 7;
    nodeY = 7;
    tNodeX = 35;
    tNodeY = 6;
    fillList = new ArrayList<>();
    pathList = new ArrayList<>();
    blockList = new ArrayList<>();
    for(Square[] squares: squareList){
      for(Square square: squares){
        square.reset();
      }
    }

    repaint();


  }

  public void generateMaze(){
    ArrayList<Point> blockCollection =  MazeGenerator.basicBinary(gridRows,gridCols,squareList, currNode_Point, currTNode_Point);
    blockList.addAll(blockCollection);
    repaint();
  }


}

