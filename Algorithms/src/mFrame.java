import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;



public class mFrame extends JFrame{

  //JPanel menuPanel = new JPanel();
  Grid grid = new Grid();
  final int buttonBorder = 5;
  Color backgroundColor = new Color(237, 219, 190);


  mFrame(){
    //basic frame setup
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setSize(1240,784);
    this.setLocationRelativeTo(null);
    this.setResizable(false);
    this.setTitle("Algorithms");
    this.getContentPane().setBackground(backgroundColor);
    this.setLayout(new FlowLayout(FlowLayout.LEADING,0,0));
    grid.setBackground(backgroundColor);
    this.add(grid, BorderLayout.CENTER);




    //change icon
    ImageIcon image = new ImageIcon("startingNode.png");
    this.setIconImage(image.getImage());

    ImageIcon algorithmsImage = new ImageIcon("algorithms.png");
    ImageIcon algorithmsImagePressed = new ImageIcon("algorithmsPressed.png");
    JMenuBar menuBar = new JMenuBar();
    menuBar.setBackground(backgroundColor);
    JMenu algMenu = new JMenu();
    algMenu.setIcon(algorithmsImage);
    algMenu.setRolloverSelectedIcon(algorithmsImagePressed);
    algMenu.setFocusPainted(false);
    algMenu.setBorder(new RoundBtn(buttonBorder));


    JMenuItem dijk = new JMenuItem("Dijkstra's");
    JMenuItem A = new JMenuItem("A*");
    JMenuItem depth = new JMenuItem("Depth-first Search");

    JLabel algLabel = new JLabel();
    algLabel.setText(" ");

    //BUTTON
    ImageIcon startImage = new ImageIcon("start.png");
    ImageIcon startPressedImage = new ImageIcon("startPressed.png");
    JButton startButton = new JButton(startImage);
    startButton.setPressedIcon(startPressedImage);
    startButton.setFocusPainted(false);
    startButton.setBorder(new RoundBtn(buttonBorder));
    startButton.setBackground(Color.WHITE);
    //startButton.setMargin(new Insets(0,0,0,0));
    startButton.addActionListener((e) -> {
      if(algLabel.getText().equals("Dijkstra's")){
        //Dijkstra test = new Dijkstra(grid.getG(),grid.getSquareList());
          Dijkstra.start(grid, grid.getOriginNode(),grid.getTargetNode());
      }else if(algLabel.getText().equals("A*")){
        AStar.start(grid, grid.getOriginNode(),grid.getTargetNode());
      }else if(algLabel.getText().equals("Depth")){
        DepthFirst.start(grid,grid.getOriginNode(), grid.getTargetNode());
      }
    });

    ImageIcon generateImage = new ImageIcon("Generate.png");
    ImageIcon generatePressedImage = new ImageIcon("GeneratePressed.png");
    JButton generateButton = new JButton(generateImage);
    generateButton.setPressedIcon(generatePressedImage);
    generateButton.setFocusPainted(false);
    generateButton.setBorder(new RoundBtn(buttonBorder));
    generateButton.setBackground(backgroundColor);
    generateButton.addActionListener((e) ->{
      grid.generateMaze();
    });


    ImageIcon resetImage = new ImageIcon("reset.png");
    ImageIcon resetImagePressed = new ImageIcon("resetPressed.png");
    JButton resetButton = new JButton(resetImage);
    resetButton.setPressedIcon(resetImagePressed);
    resetButton.setFocusPainted(false);
    resetButton.setBorder(new RoundBtn(buttonBorder));

    resetButton.addActionListener((e) ->{
      grid.reset();
    });


    //BUTTON
    ImageIcon blockedImage = new ImageIcon("blocked.png");
    ImageIcon blockedImagePressed = new ImageIcon("blockedPressed.png");
    ImageIcon unblockImage = new ImageIcon("unblock.png");
    ImageIcon unblockImagePressed = new ImageIcon("unblockPressed.png");
    JButton blockButton = new JButton(blockedImage);
    blockButton.setPressedIcon(blockedImagePressed);
    //blockButton.setText("Block Squares");
    blockButton.setFocusPainted(false);
    //blockButton.setFont(new Font("Times New Roman",Font.PLAIN,15));


    blockButton.setBorder(new RoundBtn(buttonBorder));
    blockButton.addActionListener((e) ->{
        if(grid.getBlockMode()){
          blockButton.setIcon(blockedImage);
          blockButton.setPressedIcon(blockedImagePressed);
          System.out.println("Deactivated");
          grid.switchBlockMode(false);
        }else{
          blockButton.setIcon(unblockImage);
          blockButton.setPressedIcon(unblockImagePressed);
          System.out.println("Activated");
          grid.switchBlockMode(true);
        }
    });


    dijk.addActionListener((e) -> algLabel.setText("Dijkstra's"));
    A.addActionListener((e) -> algLabel.setText("A*"));
    depth.addActionListener((e) -> algLabel.setText("Depth"));


    algMenu.add(dijk);
    algMenu.add(A);
    algMenu.add(depth);

    menuBar.add(algMenu);
    //menuBar.add(algLabel);
    menuBar.add(startButton);
    menuBar.add(blockButton);
    menuBar.add(resetButton);
    menuBar.add(generateButton);

    this.setJMenuBar(menuBar);


    //END

    this.setVisible(true);




  }










}
