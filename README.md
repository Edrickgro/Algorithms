# Algorithms

Languages & Libraries: Java Swing

Description: 

Algorithms is a program that can visually showcase any algorithm and show how they work to find a target node in a grid. As of now, Dijkstra's, A*, and Depth-First
Search have been implemented into the program. There is an origin node (teal circle) and a target node (rose circle). Once an algorithm is selected with the desired grid layout,
the program begins searching for the target node. Blue squares that pop up on the grid are squares that the algorithm has visited. Red squares are squares that are manually blocked
by the user and act as walls. After the target node is found, green squares will pop up demonstrating the shortest path from the origin node to the target node according to the 
algorithm's findings. 

Controls: 

Clicking on the "Algorithms" button displays a drop down menu to select the desired algorithm. Clicking on the 'block' button activates block mode which blocks and creates 
red squares wherever the user clicks on the grid. 'Unblock' activates unblock mode which deletes red squares. The user can drag and drop the origin and target node to their desired 
locations on the grid. The 'Generate' button generates a maze with randomly assorted blocked tiles. Pressing the 'Generate' button multiple times creates a more and more dense grid.
'Start' begins executing the selected algorithm according to the current state of the layout. 'Reset' resets the whole board and the internal data. 

Known Bugs: 

The user can still move the origin and target node while an algorithm is being executed. The 'Depth First Search' algorithm sometimes doesn't showcase the shortest path
it has found. If dragged too quickly, the program will only register the previous coordinates of the nodes, creating a mismatch between the visual and interal locations.

