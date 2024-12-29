/*
 June 18th, 2023
 Created by Derek Wang
 This program is a programmed version of a board game called "the original iq test", in which the user must remove as many pins as possible
 */

//Imports for arrays, XML, etc
import java.io.File;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author dw933
 */
public class Board extends javax.swing.JFrame {
    //Declare boolean for if game has started yet
    boolean gameStart = false;
    
    //Declare game running boolean
    boolean startingHole = false;  //At the beginning of the game the user gets to decide which hole to start empty
    //Declare and initialize enabled buttons boolean
    boolean buttonEnabled = false;
    
    //Declare and initialize boolean to hold whether or not a button is highlighted
    boolean currentHighlight = false;
    //Declare array to hold the highlighted button's dimensions
    int[] btnHighlight = new int[3];
    //Declare ints to hold the dimensions and list number, of the hole between the selected point and highlighted point
    int betweenX,
        betweenY,
        betweenNum;
    //Declare and initialize int to hold how many pins are left
    int pinsLeft = 15;
    
    //Declare array to hold the original empty hole's dimensions
    int[] originalHole = new int[2];
    //Declare int for score
    int score;
    

    //Create button array with all buttons
    JButton[] buttons = new JButton[15];
    
    //Create a 2-d array with buttons that represent each hole. Makes it easier to check possible moves
    JButton[ ] [ ] holes = new JButton [5][5];
    //Create a 2-d array with integers to hold the state of each button(0 empty, 1 filled)
    int[][] state = new int[5][5];
    //Create image icon arrays for the the 3 states of each hole filled, empty, selected/highlighted
    
    //Declare username string to hold name of user
    String username;
    //Declare boolean for invalid username
    Boolean invalidUsername = false;
    
// <editor-fold defaultstate="collapsed" desc="Creating imageIcon arrays">
    //Declare image icons for filled images
    ImageIcon filled1 = new ImageIcon("src\\filled\\3x1filled.png");
    ImageIcon filled2 = new ImageIcon("src\\filled\\2x2filled.png");
    ImageIcon filled3 = new ImageIcon("src\\filled\\3x2filled.png");
    ImageIcon filled4 = new ImageIcon("src\\filled\\2x3filled.png");
    ImageIcon filled5 = new ImageIcon("src\\filled\\3x3filled.png");
    ImageIcon filled6 = new ImageIcon("src\\filled\\4x3filled.png");
    ImageIcon filled7 = new ImageIcon("src\\filled\\1x4filled.png");
    ImageIcon filled8 = new ImageIcon("src\\filled\\2x4filled.png");
    ImageIcon filled9 = new ImageIcon("src\\filled\\3x4filled.png");
    ImageIcon filled10 = new ImageIcon("src\\filled\\4x4filled.png");
    ImageIcon filled11 = new ImageIcon("src\\filled\\1x5filled.png");
    ImageIcon filled12 = new ImageIcon("src\\filled\\2x5filled.png");
    ImageIcon filled13 = new ImageIcon("src\\filled\\3x5filled.png");
    ImageIcon filled14 = new ImageIcon("src\\filled\\4x5filled.png");
    ImageIcon filled15 = new ImageIcon("src\\filled\\5x5filled.png");

    //Store filled imageIcons into filled array
    ImageIcon[] filledArray = {filled1, filled2, filled3, filled4, filled5, filled6, filled7, filled8, filled9, filled10, filled11, filled12, filled13, filled14, filled15};


    //Declare image icons for empty images
    ImageIcon empty1= new ImageIcon("src\\empty\\3x1empty.png");
    ImageIcon empty2 = new ImageIcon("src\\empty\\2x2empty.png");
    ImageIcon empty3 = new ImageIcon("src\\empty\\3x2empty.png");
    ImageIcon empty4 = new ImageIcon("src\\empty\\2x3empty.png");
    ImageIcon empty5 = new ImageIcon("src\\empty\\3x3empty.png");
    ImageIcon empty6 = new ImageIcon("src\\empty\\4x3empty.png");
    ImageIcon empty7 = new ImageIcon("src\\empty\\1x4empty.png");
    ImageIcon empty8 = new ImageIcon("src\\empty\\2x4empty.png");
    ImageIcon empty9 = new ImageIcon("src\\empty\\3x4empty.png");
    ImageIcon empty10 = new ImageIcon("src\\empty\\4x4empty.png");
    ImageIcon empty11 = new ImageIcon("src\\empty\\1x5empty.png");
    ImageIcon empty12 = new ImageIcon("src\\empty\\2x5empty.png");
    ImageIcon empty13 = new ImageIcon("src\\empty\\3x5empty.png");
    ImageIcon empty14 = new ImageIcon("src\\empty\\4x5empty.png");
    ImageIcon empty15 = new ImageIcon("src\\empty\\5x5empty.png");

    //Store filled imageIcons into filled array
    ImageIcon[] emptyArray = {empty1, empty2, empty3, empty4, empty5, empty6, empty7, empty8, empty9, empty10, empty11, empty12, empty13, empty14, empty15};
    
    //Declare image icons for empty images
    ImageIcon high1= new ImageIcon("src\\highlight\\3x1high.png");
    ImageIcon high2 = new ImageIcon("src\\highlight\\2x2high.png");
    ImageIcon high3 = new ImageIcon("src\\highlight\\3x2high.png");
    ImageIcon high4 = new ImageIcon("src\\highlight\\2x3high.png");
    ImageIcon high5 = new ImageIcon("src\\highlight\\3x3high.png");
    ImageIcon high6 = new ImageIcon("src\\highlight\\4x3high.png");
    ImageIcon high7 = new ImageIcon("src\\highlight\\1x4high.png");
    ImageIcon high8 = new ImageIcon("src\\highlight\\2x4high.png");
    ImageIcon high9 = new ImageIcon("src\\highlight\\3x4high.png");
    ImageIcon high10 = new ImageIcon("src\\highlight\\4x4high.png");
    ImageIcon high11 = new ImageIcon("src\\highlight\\1x5high.png");
    ImageIcon high12 = new ImageIcon("src\\highlight\\2x5high.png");
    ImageIcon high13 = new ImageIcon("src\\highlight\\3x5high.png");
    ImageIcon high14 = new ImageIcon("src\\highlight\\4x5high.png");
    ImageIcon high15 = new ImageIcon("src\\highlight\\5x5high.png");

    //Store filled imageIcons into filled array
    ImageIcon[] highArray = {high1, high2, high3, high4, high5, high6, high7, high8, high9, high10, high11, high12, high13, high14, high15};
// </editor-fold>
    
    
    public Board() {
        initComponents();
        
        //Iniitalize buttons using initBtnBoard procedure
        initBtnBoard();
        
        //Show intro screen
        showIntroScreen();
    }

    /*
    showIntroScreen
    
    This procedure shows the intro frame to the user by creating an instant of the introscreen class
    
    Parameters: none
    
    Returns: none 
    */
    public void showIntroScreen()
    {
        //Create introscreen object
        IntroScreen intro = new IntroScreen();
        intro.setVisible(true);  //Set intro screen to visible
        intro.setAlwaysOnTop(true);  //Place intro over main frm
        intro.setDefaultCloseOperation(DISPOSE_ON_CLOSE);  //Change exit button to dispose to avoid closing program
    }
    
    /*
    initBtnBoard
    
    This procedure initializes the 2d arrays for the grid of buttons and the corrosponding state array.
    The triangle of buttons is initalized as a right angle triangle, to allow each button to have a corrosponding item in a grid
    
    Parameters: none
    
    Returns: none 
    */
    public void initBtnBoard()
    {
        //Initialize buttons array. Each of these buttons will also be placed into the 2d holes array
        buttons = new JButton[]{btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10, btn11, btn12, btn13, btn14, btn15};

        //Declare and initialize variable to be running count of which button we're on
        int count = 0;

        
        //Declare and initialize amount variable that will how many items are in each row
        int amnt = 1;

        //Run for loop for each row
        for (int row = 0; row < 5; row++)
        {
            //Run another for loop for each column
            for (int column = 0; column < amnt; column++)
            {
                //Set specific row and column of hole button to the count button
                holes[row][column] = buttons[count];
                //Set corrosponding state array to 1 for filled
                state[row][column] = 1;
                
                //Disable highlight on button
                holes[row][column].setFocusPainted(false);
                
                //Increment count
                count++;
            }

            //Increase value of amnt as each row has 1 more button
            amnt++;
        }
        
        //Remove highlight from leaderboard button
        btnLeaderboard.setFocusPainted(false);
        btnLeaderboard.setBorder(null);
        
        //Set btnHighlight values to -1 as no button has been highlighted yet
        btnHighlight[0] = -1;
        btnHighlight[1] = -1;
        btnHighlight[2] = -1;
    }
    
    /*
    resetGame
    
    This procedure resets the game for another play through
    
    Parameters: none
    
    Returns: none 
    */
    public void resetGame()
    {
        /*First reset each game button
        Declare amnt int which will increase every row */
        int amnt = 1;
        //Declare count variable which will count amount of buttons that have been iterated through
        int count = 0;
        
        //Run for loop to reset each button
        for (int row = 0; row < 5; row++)
        {
            for (int column = 0; column < amnt; column++)
            {
                //Set specifc button to filled icon
                holes[row][column].setIcon(filledArray[count]);
                //Set corrosponding state array to 1 for filled
                state[row][column] = 1;
                
                //Increment count
                count++;
            }

            //Increase value of amnt as each row has 1 more button
            amnt++;
        }
        
        //Reset pinsLeft variable
        pinsLeft = 15;
        //Reset pins left label
        lblPinsLeft.setText("  Pins Left: " + pinsLeft);
        
        //Reset startingHole boolean
        startingHole = false;
        
        //Set current highlight to false
        currentHighlight = false;
    }
        
    /*
    btnPress
    
    This procedure handles all the functions for when a game button is pressed. I.e. checking if move is valid, if game is over, if game is starting etc.
    
    Parameters: int, int, int
    
    Returns: none 
    */
    public void btnPress(int row, int column, int btnNum)
    {
        //Check if buttons are disabled
        if (buttonEnabled)
        {
            //Check if starting empty hole has been selected
            if (!startingHole)
            //Run code to select the starting hole
            {
                //Set specific button's status to empty
                state[row][column] = 0;
                //Change button icon to empty
                holes[row][column].setIcon(emptyArray[btnNum]);

                //Set startingHole to these dimensions
                originalHole[0] = row;
                originalHole[1] = column;
                
                //Decrement pins left
                pinsLeft--;
                //Update pinsLeft label
                lblPinsLeft.setText("  Pins Left: " + pinsLeft);

                //Update startingHole to true as it has been selected
                startingHole = true;
            }

            //If a starting hole has already been selected
            else
            {
                //Check if button that was pressed is filled
                if (state[row][column] == 1)
                //Then set specific button to highlighted stage
                {
                    //Check if there was already a highlighted button
                    if (currentHighlight)
                    {
                        //Use btnHighlight array to reset the previous buttons image
                        holes[btnHighlight[0]][btnHighlight[1]].setIcon(filledArray[btnHighlight[2]]);
                    }
                   //Update btnHighlight values
                   btnHighlight[0] = row;
                   btnHighlight[1] = column;
                   btnHighlight[2] = btnNum;
                   //Set currentHighlight to true
                   currentHighlight = true;

                   //Update button icon to highlighted
                   holes[row][column].setIcon(highArray[btnNum]);
                }

                //If selected button is empty
                else
                {
                    //Make sure a button has been selected
                    if (currentHighlight)
                    {   
                        //Determine if move was valid with validMoveCheck
                        if (validMoveCheck(row, column))
                        //If move was valid run code to swap
                        {
                            //Swap pins and remove between pin using pinSwap procedure
                            pinSwap(row, column, btnNum);

                            /*Use gameOverCheck function to see ifuser has any possible moves
                            Check what boolean is returned by gameOverCheck */
                            if (gameOverCheck())
                            {
                                //Run game over procedure
                                gameOver();
                            }
                        }
                    }
                }
            }
        }
    }
    
    /*
    validMoveCheck
    
    This procedure checks if it is possible to move from the inputted hole/btn to the highlighted hole/btn
    
    Parameters: int, int
    
    Returns: none 
    */
    public boolean validMoveCheck(int row, int column)
    {
        //Declare boolean to hold whether or not move was valid. Set to false as default
        boolean status = false;
        
        //Declare ints to hold difference in x and y values of highlighted and selected boxes
        int xDiff,
            yDiff;
        
        //Calculate xDiff and yDiff
        xDiff = column - btnHighlight[1];
        yDiff = row - btnHighlight[0];
        
        //Check if the difference between the two points is 2 spaces diagonally
        if (Math.abs(xDiff) == 2 && Math.abs(yDiff) == 2)
        {
            /*Now we need to check that the hole between the two points is filled
            So we calculate the between x and y dimensions*/
            if (xDiff > 0)
            {
                //If xDiff is positive subtract one from column to get betweenX value
                betweenX =  column - 1;
                
            }
            //If xDiff is negative
            else
            {
                //Add positive one to get betweenX value
                betweenX = column + 1;
            }
            
            //Do again for y dimension
            if (yDiff > 0)
            {
                betweenY = row - 1;
            }
            else
            {
                betweenY = row + 1;
            }
            //Now check if state of between value is filled
            if (state[betweenY][betweenX] == 1)
            {
                //Set status to true
                status = true;
            }
        }
        
        //Check if distance between two points is 2 spots horizontally
        if (Math.abs(xDiff) == 2 && Math.abs(yDiff) == 0 )
        {
            /*Now we need to check that the hole horizontally between the two points is filled
            Calculate between x dimension(between y will be the equal)*/
            if (xDiff > 0)
            {
                //If xDiff is positive subtract one from column to get betweenX value
                betweenX =  column - 1;
                
            }
            //If xDiff is negative
            else
            {
                //Add positive one to get betweenX value
                betweenX = column + 1;
            }
            
            //Set between y to y
            betweenY = row;
            
            //Now check if state of between value is filled
            if (state[betweenY][betweenX] == 1)
            {
                //Set status to true
                status = true;
            } 
        }
        
        //Check if distance between two points is 2 spots vertically
        if (Math.abs(xDiff) == 0 && Math.abs(yDiff) == 2 )
        {
            //Repeat same process above except for y
            if (yDiff > 0)
            {
                //If yDiff is positive subtract one from 'y' to get betweenY value
                betweenY = row - 1;
                
            }
            //If yDiff is negative
            else
            {
                //Add positive one to get betweenY value
                betweenY = row + 1;
            }
            
            //Set between x to x
            betweenX = column;
            
            //Now check if state of between value is filled
            if (state[betweenY][betweenX] == 1)
            {
                //Set status to true
                status = true;
            }
        }
        
        //Return status
        return (status);
    }
    
    /*
    sumFunc
    
    This method takes the inputted integer and calculates the sum of all previous integers
    
    Paramters: int
    
    Returns: int
    */
    public static int sumFunc(int n)
    {
        //Declare and initialize sum long as 0
        int sum = 0;
        
        //Run for loop that iterates through every successive positive integer in n
        for (int z = n; z > 0; z--)
        {
            //Add value z to sum
            sum = sum + z;  //Z is the specific integer for loop is on and sum is the running sum
        }
        
        //Return the sum value
        return (sum);
    }
    
    /*
    dimensionConverter
    
    This procedure converts the 2d dimensions of a button and returns its linear order
    
    Parameters: int, int
    
    Returns: none 
    */
    public static int dimensionConverter(int x, int y)
    {
        //Declare int to hold list number equivalent(i.e. 1,2,3,4 etc)
        int listNum;
        
        //The conversion of list num is the (factorial of x) - x + y
        listNum = sumFunc(x + 1) - x + y;
        
        //Return listNum
        return (listNum);
    }
    
    /*
    pinSwap
    
    This procedure swaps the pin that was highlighted and the one that was selected
    
    Parameters: int, int, int
    
    Returns: none 
    */
    public void pinSwap(int x, int y, int num)
    {
        //Set highlighted hole to empty
        holes[btnHighlight[0]][btnHighlight[1]].setIcon(emptyArray[btnHighlight[2]]);
        //Update state of hole
        state[btnHighlight[0]][btnHighlight[1]] = 0;

        //Set selected hole to filled
        holes[x][y].setIcon(filledArray[num]);
        //Update state
        state[x][y] = 1;

        //Calculate value of betweenNum using dimensionCoverter
        betweenNum = dimensionConverter(betweenY, betweenX);

        //Set between hole to empty
        holes[betweenY][betweenX].setIcon(emptyArray[betweenNum-1]);
        //Update state
        state[betweenY][betweenX] = 0;

        //Decrement pins left
        pinsLeft--;
        //Update pinsLeft label
        lblPinsLeft.setText("  Pins Left: " + pinsLeft);

        //Reset current highlight button
        currentHighlight = false;
    }
    
    /*
    gameOverCheck
    
    This function checks if there are possible moves left in the game. It does this by systemically going through
    the buttons and stopping once it's found a possible move.
    
    Parameters: none
    
    Returns: boolean 
    */
    public boolean gameOverCheck()
    {
        //Declare boolean to hold whether or not game is over. Set default to true
        boolean gameOver = true;

        //Declare amount variable that will change depending on the row
        int amnt = 1;
        int listCount = 1;

        //Run for loop to iterate through each item in grid
        for (int row = 0; row < 5; row++)
        {
            //Run another for loop for each column
            for (int column = 0; column < amnt; column++)
            {
                //Check if specific hole/btn can be played/moved(meaning it is filled)
                if (state[row][column] == 1)
                {
                    /* When checking if moves are possible, the entire board can be split into four groups. The top area of holes can only make moves directly down
                    or diagonally right and down. The bottom left area of holes can only make moves directly up or right. The bottom right group can only make moves
                    directly left or diagonally left and up. Finally, the last group of holes which are along the sides of the triangle in the middle, can only make
                    moves to the other holes in the group and the corners. Knowing this we can use if statements to determine how we should check if a move is possible
                    */
    
// <editor-fold defaultstate="collapsed" desc="Group 1-3 checks">
                    //Group 1
                    if (listCount == 1 || listCount == 2 || listCount == 3 || listCount == 5)
                    {
                        //Set btnHighlight values to hole/btn 2 below original point
                        btnHighlight[0] = row + 2;
                        btnHighlight[1] = column;
                        
                        //Check if new point is empty. If its filled the original point cannot move there
                        if (state[btnHighlight[0]][btnHighlight[1]] == 0)
                        {
                            /*Run validMoveCheck with btnHighlight values and original point
                            Check if validMoveCheck returns true*/
                            if (validMoveCheck(row, column))
                            //There is a possible move game is not over
                            {
                                //Set gameOver to false and return value
                                gameOver = false;
                                return(gameOver);
                            }
                        }
                        
                        //Now set btnHighlight values to hole/btn 2 below and 2 to the right(btnHighlight[0] is already the correct value)
                        btnHighlight[1] = column + 2;
                        
                        //Check if new point is empty. If its filled the original point cannot move there
                        if (state[btnHighlight[0]][btnHighlight[1]] == 0)
                        {
                            //Run valid check again to see if move is possible
                            if (validMoveCheck(row, column))
                            //Game is not over
                            {
                                //Set gameOver to false and return value
                                gameOver = false;
                                return(gameOver);
                            }
                        }
                    }
                    
                    //Group 2
                    else if (listCount == 7 || listCount == 8 || listCount == 11 || listCount == 12)
                    {
                        //Set btnHighlight values to hole/btn 2 above original point
                        btnHighlight[0] = row - 2;
                        btnHighlight[1] = column;
                        
                        //Check if new point is empty. If its filled the original point cannot move there
                        if (state[btnHighlight[0]][btnHighlight[1]] == 0)
                        {
                            /*Run validMoveCheck with btnHighlight values and original point
                            Check if validMoveCheck returns true*/
                            if (validMoveCheck(row, column))
                            //There is a possible move game is not over
                            {
                                //Set gameOver to false and return value
                                gameOver = false;
                                return(gameOver);
                            }
                        }
                        
                        //Now set btnHighlight values to hole/btn 2 to the right of original point
                        btnHighlight[0] = row;
                        btnHighlight[1] = column + 2;
                        
                        //Check if new point is empty. If its filled the original point cannot move there
                        if (state[btnHighlight[0]][btnHighlight[1]] == 0)
                        {
                            /*Run validMoveCheck with btnHighlight values and original point
                            Check if validMoveCheck returns true*/
                            if (validMoveCheck(row, column))
                            //There is a possible move game is not over
                            {
                                //Set gameOver to false and return value
                                gameOver = false;
                                return(gameOver);
                            }
                        }
                    }
                    
                    //Group 3
                    else if (listCount == 9 || listCount == 10 || listCount == 14 || listCount == 15)
                    {
                        //Set btnHighlight values to hole/btn 2 to the left of original point
                        btnHighlight[0] = row;
                        btnHighlight[1] = column - 2;
                        
                        //Check if new point is empty. If its filled the original point cannot move there
                        if (state[btnHighlight[0]][btnHighlight[1]] == 0)
                        {
                            /*Run validMoveCheck with btnHighlight values and original point
                            Check if validMoveCheck returns true*/
                            if (validMoveCheck(row, column))
                            //There is a possible move game is not over
                            {
                                //Set gameOver to false and return value
                                gameOver = false;
                                return(gameOver);
                            }
                        }
                        
                        //Now set btnHighlight values to hole/btn 2 spaces left and up of original point(btnHighlight[1] is already correct)
                        btnHighlight[0] = row - 2;
                        
                        //Check if new point is empty. If its filled the original point cannot move there
                        if (state[btnHighlight[0]][btnHighlight[1]] == 0)
                        {
                            /*Run validMoveCheck with btnHighlight values and original point
                            Check if validMoveCheck returns true*/
                            if (validMoveCheck(row, column))
                            //There is a possible move game is not over
                            {
                                //Set gameOver to false and return value
                                gameOver = false;
                                return(gameOver);
                            }
                        }
                    }
// </editor-fold>
                    
// <editor-fold defaultstate="collapsed" desc="Group 4 check">
                    
                    //Group 4
                    else if (listCount == 4 || listCount == 6 || listCount == 13)
                    {
                        //Set buttonHighlight to top point
                        btnHighlight[0] = 0;
                        btnHighlight[1] = 0;
                        
                        //Check if new point is empty. If its filled the original point cannot move there
                        if (state[btnHighlight[0]][btnHighlight[1]] == 0)
                        {
                            /*Run validMoveCheck with btnHighlight values and original point
                            Check if validMoveCheck returns true*/
                            if (validMoveCheck(row, column))
                            //There is a possible move game is not over
                            {
                                //Set gameOver to false and return value
                                gameOver = false;
                                return(gameOver);
                            }
                        }
                        
                        //Set buttonHighlight to right corner point
                        btnHighlight[0] = 4;
                        btnHighlight[1] = 4;
                        
                        //Check if new point is empty. If its filled the original point cannot move there
                        if (state[btnHighlight[0]][btnHighlight[1]] == 0)
                        {
                            /*Run validMoveCheck with btnHighlight values and original point
                            Check if validMoveCheck returns true*/
                            if (validMoveCheck(row, column))
                            //There is a possible move game is not over
                            {
                                //Set gameOver to false and return value
                                gameOver = false;
                                return(gameOver);
                            }
                        }
                        
                        //Set buttonHighlight to left corner point
                        btnHighlight[0] = 4;
                        btnHighlight[1] = 0;
                        
                        //Check if new point is empty. If its filled the original point cannot move there
                        if (state[btnHighlight[0]][btnHighlight[1]] == 0)
                        {
                            /*Run validMoveCheck with btnHighlight values and original point
                            Check if validMoveCheck returns true*/
                            if (validMoveCheck(row, column))
                            //There is a possible move game is not over
                            {
                                //Set gameOver to false and return value
                                gameOver = false;
                                return(gameOver);
                            }
                        }
                        
                        //Set buttonHighlight to left middle point
                        btnHighlight[0] = 2;
                        btnHighlight[1] = 0;
                        
                        //Check if new point is empty. If its filled the original point cannot move there
                        if (state[btnHighlight[0]][btnHighlight[1]] == 0)
                        {
                            /*Run validMoveCheck with btnHighlight values and original point
                            Check if validMoveCheck returns true*/
                            if (validMoveCheck(row, column))
                            //There is a possible move game is not over
                            {
                                //Set gameOver to false and return value
                                gameOver = false;
                                return(gameOver);
                            }
                        }
                        
                        //Set buttonHighlight to right middle point
                        btnHighlight[0] = 2;
                        btnHighlight[1] = 2;
                        
                        //Check if new point is empty. If its filled the original point cannot move there
                        if (state[btnHighlight[0]][btnHighlight[1]] == 0)
                        {
                            /*Run validMoveCheck with btnHighlight values and original point
                            Check if validMoveCheck returns true*/
                            if (validMoveCheck(row, column))
                            //There is a possible move game is not over
                            {
                                //Set gameOver to false and return value
                                gameOver = false;
                                return(gameOver);
                            }
                        }
                        
                        //Set buttonHighlight to bottom middle point
                        btnHighlight[0] = 4;
                        btnHighlight[1] = 2;
                        
                        //Check if new point is empty. If its filled the original point cannot move there
                        if (state[btnHighlight[0]][btnHighlight[1]] == 0)
                        {
                            /*Run validMoveCheck with btnHighlight values and original point
                            Check if validMoveCheck returns true*/
                            if (validMoveCheck(row, column))
                            //There is a possible move game is not over
                            {
                                //Set gameOver to false and return value
                                gameOver = false;
                                return(gameOver);
                            }
                        }
                    }
// </editor-fold>
                }
                
                //Increment listCount
                listCount++;
            }

            //Increase value of amnt as each row has 1 more button
            amnt++;
        }
        
        //Return gameOver boolean
        return (gameOver);
    }
    
    /*
    gameOver
    
    This procedure calculates the users score once the game is over, and generates the gameOver screen
    
    Parameters: none
    
    Returns: none 
    */
    public void gameOver()
    {
        //Disable buttons
        buttonEnabled = false;
        
        /*Calculate the score with a series of if statements
        Check if 3 pins are left*/
        if (pinsLeft == 3)
        {
            //Set score to 10 points
            score = 10;
        }
        //If 2 pins are left
        else if (pinsLeft == 2)
        {
            //Set score to 15 points
            score = 25;
        }
        //If 1 pin is left
        else if (pinsLeft == 1)
        {
            //Check if original pin is filled still
            if (state[originalHole[0]][originalHole[1]] == 1)
            //Last pin is in original empty hole
            {
                //Set score to 100
                score = 100;
            }
            //If last pin isn't in same spot as original
            else
            {
                //Set score to 50 points
                score = 50;
            }
        }
        
        //If 8 pins are left
        else if (pinsLeft == 8)
        {
            //Set score to 200
            score = 200;
        }
        
        //Otherwise score is 0
        else
        {
            //Set score to 0
            score = 0;
        }
        
        //Update XML document with new data from this attempt
        addXML();
        
        //Show gameOver screen to user
        outputGameOver();
        
        //Enable play button again
        btnPlay.setEnabled(true);
    }
    
    /*
    addXML
    
    This procedure adds the new data from this run of the game to the XML file
    
    Parameters: none
    
    Returns: none 
    */
    public void addXML()
    {
        //Check to make sure a valid username was inputted
        if (!invalidUsername)
        {
            //Declare ints 
            int index,  //Holds index of player name in database
            highscore,  //Holds high score of player
           totalscore;  //Holds total score of player


            //Use xmlLinearSearch to find index of name in xml file
            index = xmlLinearSearch(username);

            //If index is not -1 then we use index to edit players information
            if(index != -1)
            {
                //Edit xml data in try catch statement
                try
                {
                    //Create string for file name
                    String fileName = "leaderboard.xml";
                    //Declare and initialize docFactory object to create doc
                    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                    //Declare and initalize docBuilder
                    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                    //Create a document called file. Assign information xml to this file
                    Document file = docBuilder.parse(fileName);
                    
                    //Create node for players highScore
                    Node highScoreNode = file.getElementsByTagName("highScore").item(index);
                    highscore = Integer.parseInt(highScoreNode.getTextContent());
                    
                    //If current score is higher than highscore change high score value
                    if (score > highscore)
                    {
                        //Change high score value to score
                        highScoreNode.setTextContent(String.valueOf(score));
                    }
                    
                    //Create node for players total score
                    Node totalScoreNode = file.getElementsByTagName("totalScore").item(index);
                    totalscore = Integer.parseInt(totalScoreNode.getTextContent());
                    
                    //Add score to totalscore
                    totalscore += score;
                    
                    //Update totalScore to new totalScore
                    totalScoreNode.setTextContent(String.valueOf(totalscore));
                    
                    //write the content into XML file
                    TransformerFactory transformerFactory = TransformerFactory.newInstance();
                    Transformer transformer = transformerFactory.newTransformer();
                    DOMSource source = new DOMSource(file);
                    StreamResult result =  new StreamResult(new File(fileName));
                    transformer.transform(source, result);
                }
                
                //Catch statements for numerous errors
                catch(ParserConfigurationException | TransformerException | IOException | SAXException pce){
                    pce.printStackTrace();
                }
            }
            
            //Otherwise if index is -1 then we need to add a new player element
            else if (index == -1)
            {
                //Edit xml file in try catch statement
                try
                {
                    //Create string for file name
                    String fileName = "leaderboard.xml";
                    //Declare and initialize docFactory object to create doc
                    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                    //Declare and initalize docBuilder
                    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                    //Create a document called file. Assign information xml to this file
                    Document file = docBuilder.parse(fileName);

                    /* Add another player element to XML file
                    Get info element*/
                    Node info = file.getElementsByTagName("info").item(0);
                    //Create a new player element
                    Node newPlayer = file.createElement("player");
                    
                    //Create information about new player
                    Node name = file.createElement("name");  //Add player name
                    name.setTextContent(username);  //Change textcontent
                    Node highScore = file.createElement("highScore");  //Add high score
                    highScore.setTextContent(String.valueOf(score));  //Change high score
                    Node totalScore = file.createElement("totalScore");  //Add total score
                    totalScore.setTextContent(String.valueOf(score));  //Add score as total score as this is a new player entry
                    
                    //Append name high score and total score to newPlayer
                    newPlayer.appendChild(name);
                    newPlayer.appendChild(highScore);
                    newPlayer.appendChild(totalScore);
                    
                    //Add new player element under info
                    info.appendChild(newPlayer);
                    
                    //write the content into xml file
                    TransformerFactory transformerFactory = TransformerFactory.newInstance();
                    Transformer transformer = transformerFactory.newTransformer();
                    DOMSource source = new DOMSource(file);
                    StreamResult result =  new StreamResult(new File(fileName));
                    transformer.transform(source, result);
                }
                
                //Catch statements for numerous errors
                catch(ParserConfigurationException pce){
                    pce.printStackTrace();
                }
                catch(TransformerException tfe){
                    tfe.printStackTrace();
                }
                catch(IOException ioe){
                    ioe.printStackTrace();
                }
                catch(SAXException sae){
                    sae.printStackTrace();
                }
            }
        }
    }
    
    /*
    xmlLinearSearch
    
    This program searches through the xml document looking if the inputted name is present
    
    Parameters: String
    
    Returns: int
    */
    public int xmlLinearSearch(String name)
    {
        //Declare and initialize integer to hold index of reference number. (-1 means not found)
        int index = -1;
        
        //Take xml data in try catch statement
        try
        {
            //Create string for file name
            String fileName = "leaderboard.xml";
            //Declare and initialize docFactory object to create doc
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            //Declare and initalize docBuilder
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            //Create a document called file. Assign information xml to this file
            Document file = docBuilder.parse(fileName);
        
            
            NodeList names = file.getElementsByTagName("name");
            
            //Iterate through each item in names list
            for (int i = 0; i < names.getLength(); i++)
            {
                //Create tempName string that will hold specific name
                String tempName = names.item(i).getTextContent();
                
                //Check if string in names is equal to the inputted name
                if (name.equals(tempName))
                {
                    //Return index(i) of value
                    return (i);
                }
            }
        }
        
        //Catch numerous possible errors
        catch(ParserConfigurationException pce)
        {
            pce.printStackTrace();
        }
        catch(IOException ioe)
        {
            ioe.printStackTrace();
        }
        catch(SAXException sae)
        {
            sae.printStackTrace();
        }
        
        //Return index which will be equal to -1 meaning not in xml
        return (index);
    }
    
    /*
    outputGameOver
    
    This procedure opens the gameOver screen by creating an instance of the class gameOver
    
    Parameters: none
    
    Returns: none 
    */
    public void outputGameOver()
    {
        //Create game over object from GameOver class
        GameOver gameOverScreen = new GameOver(score, pinsLeft);
        gameOverScreen.setVisible(true);  //Set gameOverScreen to visible
        gameOverScreen.setAlwaysOnTop(true);   //Put gameOverScreen on top
        gameOverScreen.setDefaultCloseOperation(DISPOSE_ON_CLOSE); //Change exit button to dispose instead of exiting from program
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelBackground = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        panelBoard = new javax.swing.JPanel();
        btn1 = new javax.swing.JButton();
        btn2 = new javax.swing.JButton();
        btn3 = new javax.swing.JButton();
        btn4 = new javax.swing.JButton();
        btn5 = new javax.swing.JButton();
        btn6 = new javax.swing.JButton();
        btn7 = new javax.swing.JButton();
        btn8 = new javax.swing.JButton();
        btn9 = new javax.swing.JButton();
        btn10 = new javax.swing.JButton();
        btn11 = new javax.swing.JButton();
        btn12 = new javax.swing.JButton();
        btn13 = new javax.swing.JButton();
        btn14 = new javax.swing.JButton();
        btn15 = new javax.swing.JButton();
        lblCatchPhrase = new javax.swing.JLabel();
        panelTextInst = new javax.swing.JPanel();
        lblPinsLeft = new javax.swing.JLabel();
        lblInst1 = new javax.swing.JLabel();
        lblInst2 = new javax.swing.JLabel();
        lblInst3 = new javax.swing.JLabel();
        lblInst4 = new javax.swing.JLabel();
        lblInst5 = new javax.swing.JLabel();
        panelTextInst2 = new javax.swing.JPanel();
        lblScoring = new javax.swing.JLabel();
        lblScoring1 = new javax.swing.JLabel();
        lblScoring2 = new javax.swing.JLabel();
        lblScoring3 = new javax.swing.JLabel();
        lblScoring4 = new javax.swing.JLabel();
        lblScoring5 = new javax.swing.JLabel();
        lblScoring6 = new javax.swing.JLabel();
        lblScoring7 = new javax.swing.JLabel();
        lblScoring8 = new javax.swing.JLabel();
        lblScoring9 = new javax.swing.JLabel();
        lblScoring10 = new javax.swing.JLabel();
        lblInst30 = new javax.swing.JLabel();
        btnPlay = new javax.swing.JButton();
        btnLeaderboard = new javax.swing.JButton();
        lblCopyright = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panelBackground.setBackground(new java.awt.Color(255, 255, 255));

        lblTitle.setFont(new java.awt.Font("Lucida Calligraphy", 1, 40)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(102, 102, 255));
        lblTitle.setText("The Original IQ Tester");

        panelBoard.setBackground(new java.awt.Color(176, 138, 93));

        btn1.setBackground(new java.awt.Color(176, 138, 93));
        btn1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/3x1filled.png"))); // NOI18N
        btn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn1ActionPerformed(evt);
            }
        });

        btn2.setBackground(new java.awt.Color(176, 138, 93));
        btn2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/2x2filled.png"))); // NOI18N
        btn2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn2ActionPerformed(evt);
            }
        });

        btn3.setBackground(new java.awt.Color(176, 138, 93));
        btn3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/3x2filled.png"))); // NOI18N
        btn3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn3ActionPerformed(evt);
            }
        });

        btn4.setBackground(new java.awt.Color(176, 138, 93));
        btn4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/2x3filled.png"))); // NOI18N
        btn4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn4ActionPerformed(evt);
            }
        });

        btn5.setBackground(new java.awt.Color(176, 138, 93));
        btn5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/3x3filled.png"))); // NOI18N
        btn5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn5ActionPerformed(evt);
            }
        });

        btn6.setBackground(new java.awt.Color(176, 138, 93));
        btn6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/4x3filled.png"))); // NOI18N
        btn6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn6ActionPerformed(evt);
            }
        });

        btn7.setBackground(new java.awt.Color(176, 138, 93));
        btn7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/1x4filled.png"))); // NOI18N
        btn7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn7ActionPerformed(evt);
            }
        });

        btn8.setBackground(new java.awt.Color(176, 138, 93));
        btn8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/2x4filled.png"))); // NOI18N
        btn8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn8ActionPerformed(evt);
            }
        });

        btn9.setBackground(new java.awt.Color(176, 138, 93));
        btn9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/3x4filled.png"))); // NOI18N
        btn9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn9ActionPerformed(evt);
            }
        });

        btn10.setBackground(new java.awt.Color(176, 138, 93));
        btn10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/4x4filled.png"))); // NOI18N
        btn10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn10ActionPerformed(evt);
            }
        });

        btn11.setBackground(new java.awt.Color(176, 138, 93));
        btn11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/1x5filled.png"))); // NOI18N
        btn11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn11ActionPerformed(evt);
            }
        });

        btn12.setBackground(new java.awt.Color(176, 138, 93));
        btn12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/2x5filled.png"))); // NOI18N
        btn12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn12ActionPerformed(evt);
            }
        });

        btn13.setBackground(new java.awt.Color(176, 138, 93));
        btn13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/3x5filled.png"))); // NOI18N
        btn13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn13ActionPerformed(evt);
            }
        });

        btn14.setBackground(new java.awt.Color(176, 138, 93));
        btn14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/4x5filled.png"))); // NOI18N
        btn14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn14ActionPerformed(evt);
            }
        });

        btn15.setBackground(new java.awt.Color(176, 138, 93));
        btn15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/5x5filled.png"))); // NOI18N
        btn15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn15ActionPerformed(evt);
            }
        });

        lblCatchPhrase.setFont(new java.awt.Font("Lucida Calligraphy", 1, 26)); // NOI18N
        lblCatchPhrase.setText("Each Game a Solitary Adventure");

        panelTextInst.setBackground(new java.awt.Color(176, 138, 93));

        lblPinsLeft.setFont(new java.awt.Font("Sylfaen", 0, 27)); // NOI18N
        lblPinsLeft.setText("  Pins Left: 15");

        lblInst1.setFont(new java.awt.Font("Sylfaen", 0, 16)); // NOI18N
        lblInst1.setText("Start with any one hole empty");

        lblInst2.setFont(new java.awt.Font("Sylfaen", 0, 16)); // NOI18N
        lblInst2.setText("As you jump the pegs remove");

        lblInst3.setFont(new java.awt.Font("Sylfaen", 0, 16)); // NOI18N
        lblInst3.setText("them from the board.");

        lblInst4.setFont(new java.awt.Font("Sylfaen", 0, 16)); // NOI18N
        lblInst4.setText("Try to leave only one peg");

        lblInst5.setFont(new java.awt.Font("Sylfaen", 0, 18)); // NOI18N
        lblInst5.setText("RULES OF THE GAME:");

        javax.swing.GroupLayout panelTextInstLayout = new javax.swing.GroupLayout(panelTextInst);
        panelTextInst.setLayout(panelTextInstLayout);
        panelTextInstLayout.setHorizontalGroup(
            panelTextInstLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblPinsLeft, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblInst1, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
            .addComponent(lblInst2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblInst3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblInst4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panelTextInstLayout.createSequentialGroup()
                .addComponent(lblInst5)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panelTextInstLayout.setVerticalGroup(
            panelTextInstLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTextInstLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(lblPinsLeft)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addComponent(lblInst5)
                .addGap(0, 0, 0)
                .addComponent(lblInst1)
                .addGap(1, 1, 1)
                .addComponent(lblInst2)
                .addGap(0, 0, 0)
                .addComponent(lblInst3)
                .addGap(0, 0, 0)
                .addComponent(lblInst4)
                .addGap(29, 29, 29))
        );

        panelTextInst2.setBackground(new java.awt.Color(176, 138, 93));

        lblScoring.setFont(new java.awt.Font("Sylfaen", 0, 17)); // NOI18N
        lblScoring.setText("Scoring:");

        lblScoring1.setFont(new java.awt.Font("Sylfaen", 0, 17)); // NOI18N
        lblScoring1.setText("3 pegs: 10 points");

        lblScoring2.setFont(new java.awt.Font("Sylfaen", 0, 17)); // NOI18N
        lblScoring2.setText("2 pegs: 25 points");

        lblScoring3.setFont(new java.awt.Font("Sylfaen", 0, 17)); // NOI18N
        lblScoring3.setText("1 peg: 50 points");

        lblScoring4.setFont(new java.awt.Font("Sylfaen", 0, 17)); // NOI18N
        lblScoring4.setText("If last peg is left in");

        lblScoring5.setFont(new java.awt.Font("Sylfaen", 0, 17)); // NOI18N
        lblScoring5.setText("same hole that was");

        lblScoring6.setFont(new java.awt.Font("Sylfaen", 0, 17)); // NOI18N
        lblScoring6.setText("initally left empty");

        lblScoring7.setFont(new java.awt.Font("Sylfaen", 0, 17)); // NOI18N
        lblScoring7.setText(":100 points");

        lblScoring8.setFont(new java.awt.Font("Sylfaen", 0, 17)); // NOI18N
        lblScoring8.setText("Leave 8 pegs on");

        lblScoring9.setFont(new java.awt.Font("Sylfaen", 0, 17)); // NOI18N
        lblScoring9.setText("the board with no");

        lblScoring10.setFont(new java.awt.Font("Sylfaen", 0, 17)); // NOI18N
        lblScoring10.setText("possible jumps");

        lblInst30.setFont(new java.awt.Font("Sylfaen", 0, 17)); // NOI18N
        lblInst30.setText(":200 points");

        javax.swing.GroupLayout panelTextInst2Layout = new javax.swing.GroupLayout(panelTextInst2);
        panelTextInst2.setLayout(panelTextInst2Layout);
        panelTextInst2Layout.setHorizontalGroup(
            panelTextInst2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTextInst2Layout.createSequentialGroup()
                .addComponent(lblScoring, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(panelTextInst2Layout.createSequentialGroup()
                .addGroup(panelTextInst2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblScoring2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblScoring1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblScoring3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblScoring4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblScoring7)
                    .addComponent(lblScoring8)
                    .addComponent(lblScoring9)
                    .addComponent(lblScoring10)
                    .addComponent(lblInst30)
                    .addComponent(lblScoring6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblScoring5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panelTextInst2Layout.setVerticalGroup(
            panelTextInst2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTextInst2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblScoring)
                .addGap(0, 0, 0)
                .addComponent(lblScoring1)
                .addGap(1, 1, 1)
                .addComponent(lblScoring2)
                .addGap(0, 0, 0)
                .addComponent(lblScoring3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblScoring4)
                .addGap(0, 0, 0)
                .addComponent(lblScoring5)
                .addGap(0, 0, 0)
                .addComponent(lblScoring6)
                .addGap(0, 0, 0)
                .addComponent(lblScoring7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblScoring8)
                .addGap(0, 0, 0)
                .addComponent(lblScoring9)
                .addGap(0, 0, 0)
                .addComponent(lblScoring10)
                .addGap(0, 0, 0)
                .addComponent(lblInst30))
        );

        javax.swing.GroupLayout panelBoardLayout = new javax.swing.GroupLayout(panelBoard);
        panelBoard.setLayout(panelBoardLayout);
        panelBoardLayout.setHorizontalGroup(
            panelBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBoardLayout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addGroup(panelBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelBoardLayout.createSequentialGroup()
                        .addGroup(panelBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelBoardLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 113, Short.MAX_VALUE)
                                .addComponent(btn4, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn5, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn6, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelBoardLayout.createSequentialGroup()
                                .addGap(166, 166, 166)
                                .addGroup(panelBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelBoardLayout.createSequentialGroup()
                                        .addComponent(btn2, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btn3, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(panelBoardLayout.createSequentialGroup()
                                        .addGap(58, 58, 58)
                                        .addComponent(btn1, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(panelTextInst2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelBoardLayout.createSequentialGroup()
                        .addComponent(btn11, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn12, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn13, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn14, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn15, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelBoardLayout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addComponent(btn7, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn8, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn9, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn10, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))))
            .addGroup(panelBoardLayout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addComponent(lblCatchPhrase))
            .addGroup(panelBoardLayout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(panelTextInst, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelBoardLayout.setVerticalGroup(
            panelBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBoardLayout.createSequentialGroup()
                .addGroup(panelBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBoardLayout.createSequentialGroup()
                        .addGroup(panelBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelBoardLayout.createSequentialGroup()
                                .addComponent(panelTextInst, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBoardLayout.createSequentialGroup()
                                .addContainerGap(50, Short.MAX_VALUE)
                                .addComponent(btn1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btn2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btn3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(panelBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn4, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn5, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn6, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(panelTextInst2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn7, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn8, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn9, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn10, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelBoardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn11, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn12, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn13, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn14, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn15, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblCatchPhrase)
                .addGap(9, 9, 9))
        );

        btnPlay.setFont(new java.awt.Font("Lucida Calligraphy", 0, 24)); // NOI18N
        btnPlay.setText("Play");
        btnPlay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPlayActionPerformed(evt);
            }
        });

        btnLeaderboard.setBackground(new java.awt.Color(255, 255, 255));
        btnLeaderboard.setFont(new java.awt.Font("Lucida Calligraphy", 0, 24)); // NOI18N
        btnLeaderboard.setIcon(new javax.swing.ImageIcon(getClass().getResource("/leaderboard.png"))); // NOI18N
        btnLeaderboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLeaderboardActionPerformed(evt);
            }
        });

        lblCopyright.setFont(new java.awt.Font("Yu Gothic Light", 0, 16)); // NOI18N
        lblCopyright.setText("WangBoardGames Enc.");

        javax.swing.GroupLayout panelBackgroundLayout = new javax.swing.GroupLayout(panelBackground);
        panelBackground.setLayout(panelBackgroundLayout);
        panelBackgroundLayout.setHorizontalGroup(
            panelBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBackgroundLayout.createSequentialGroup()
                .addGap(144, 144, 144)
                .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 528, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnLeaderboard, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBackgroundLayout.createSequentialGroup()
                .addGap(0, 72, Short.MAX_VALUE)
                .addComponent(panelBoard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(83, 83, 83))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBackgroundLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnPlay, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(63, 63, 63)
                .addComponent(lblCopyright, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(68, 68, 68))
        );
        panelBackgroundLayout.setVerticalGroup(
            panelBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBackgroundLayout.createSequentialGroup()
                .addGroup(panelBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTitle)
                    .addComponent(btnLeaderboard, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addComponent(panelBoard, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBackgroundLayout.createSequentialGroup()
                        .addComponent(btnPlay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBackgroundLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lblCopyright))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelBackground, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelBackground, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnPlayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPlayActionPerformed
        //Check if game is just starting
        if (!gameStart)
        {
            //Get username using JOptionPane
            username = JOptionPane.showInputDialog("Please enter your name:");
            
            //If username is blank or the user press x on the username option pane
            if ("".equals(username) || username == null)
            {
                //Set invalid username boolean to true
                invalidUsername = true;
            }
            
            //Set gameStart to true
            gameStart = true;
        }
        //Set buttons enabled boolean to true
        buttonEnabled = true;
        
        //Reset board for new game
        resetGame();
        
        //Disable the play button
        btnPlay.setEnabled(false);
    }//GEN-LAST:event_btnPlayActionPerformed

    private void btn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn1ActionPerformed
        //Call buttonPress method with button index as input
        btnPress(0, 0, 0);
    }//GEN-LAST:event_btn1ActionPerformed

    private void btn2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn2ActionPerformed
        //Call buttonPress method with button index as input
        btnPress(1, 0, 1);
    }//GEN-LAST:event_btn2ActionPerformed

    private void btn3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn3ActionPerformed
        //Call buttonPress method with button index as input
        btnPress(1, 1, 2);
    }//GEN-LAST:event_btn3ActionPerformed

    private void btn4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn4ActionPerformed
        //Call buttonPress method with button index as input
        btnPress(2, 0, 3);
    }//GEN-LAST:event_btn4ActionPerformed

    private void btn5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn5ActionPerformed
        //Call buttonPress method with button index as input
        btnPress(2, 1, 4);
    }//GEN-LAST:event_btn5ActionPerformed

    private void btn6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn6ActionPerformed
        //Call buttonPress method with button index as input
        btnPress(2, 2, 5);
    }//GEN-LAST:event_btn6ActionPerformed

    private void btn7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn7ActionPerformed
        //Call buttonPress method with button index as input
        btnPress(3, 0, 6);
    }//GEN-LAST:event_btn7ActionPerformed

    private void btn8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn8ActionPerformed
        //Call buttonPress method with button index as input
        btnPress(3, 1, 7);
    }//GEN-LAST:event_btn8ActionPerformed

    private void btn9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn9ActionPerformed
        //Call buttonPress method with button index as input
        btnPress(3, 2, 8);
    }//GEN-LAST:event_btn9ActionPerformed

    private void btn10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn10ActionPerformed
        //Call buttonPress method with button index as input
        btnPress(3, 3, 9);
    }//GEN-LAST:event_btn10ActionPerformed

    private void btn11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn11ActionPerformed
        //Call buttonPress method with button index as input
        btnPress(4, 0, 10);
    }//GEN-LAST:event_btn11ActionPerformed

    private void btn12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn12ActionPerformed
        //Call buttonPress method with button index as input
        btnPress(4, 1, 11);
    }//GEN-LAST:event_btn12ActionPerformed

    private void btn13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn13ActionPerformed
        //Call buttonPress method with button index as input
        btnPress(4, 2, 12);
    }//GEN-LAST:event_btn13ActionPerformed

    private void btn14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn14ActionPerformed
        //Call buttonPress method with button index as input
        btnPress(4, 3, 13);
    }//GEN-LAST:event_btn14ActionPerformed

    private void btn15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn15ActionPerformed
        //Call buttonPress method with button index as input
        btnPress(4, 4, 14);
    }//GEN-LAST:event_btn15ActionPerformed

    private void btnLeaderboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLeaderboardActionPerformed
        openLeaderboard();
    }//GEN-LAST:event_btnLeaderboardActionPerformed

    /*
    openLeaderboard
    
    This procedure creates a leaderboard object and opens the frame
    
    Parameters: none
    
    Returns: none
    */
    public void openLeaderboard()
    {
        Leaderboard lBoard = new Leaderboard();  //Create leaderboard object
        lBoard.setVisible(true);  //Make frame visible
        lBoard.setAlwaysOnTop(true);  //Place leaderboard over main gameOver form
        lBoard.setDefaultCloseOperation(DISPOSE_ON_CLOSE);  //Change exit button to dispose to avoid closing program
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Board.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Board.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Board.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Board.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Board().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn1;
    private javax.swing.JButton btn10;
    private javax.swing.JButton btn11;
    private javax.swing.JButton btn12;
    private javax.swing.JButton btn13;
    private javax.swing.JButton btn14;
    private javax.swing.JButton btn15;
    private javax.swing.JButton btn2;
    private javax.swing.JButton btn3;
    private javax.swing.JButton btn4;
    private javax.swing.JButton btn5;
    private javax.swing.JButton btn6;
    private javax.swing.JButton btn7;
    private javax.swing.JButton btn8;
    private javax.swing.JButton btn9;
    private javax.swing.JButton btnLeaderboard;
    private javax.swing.JButton btnPlay;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblCatchPhrase;
    private javax.swing.JLabel lblCopyright;
    private javax.swing.JLabel lblInst1;
    private javax.swing.JLabel lblInst10;
    private javax.swing.JLabel lblInst11;
    private javax.swing.JLabel lblInst12;
    private javax.swing.JLabel lblInst13;
    private javax.swing.JLabel lblInst14;
    private javax.swing.JLabel lblInst15;
    private javax.swing.JLabel lblInst16;
    private javax.swing.JLabel lblInst17;
    private javax.swing.JLabel lblInst18;
    private javax.swing.JLabel lblInst19;
    private javax.swing.JLabel lblInst2;
    private javax.swing.JLabel lblInst20;
    private javax.swing.JLabel lblInst21;
    private javax.swing.JLabel lblInst22;
    private javax.swing.JLabel lblInst23;
    private javax.swing.JLabel lblInst24;
    private javax.swing.JLabel lblInst25;
    private javax.swing.JLabel lblInst26;
    private javax.swing.JLabel lblInst27;
    private javax.swing.JLabel lblInst28;
    private javax.swing.JLabel lblInst3;
    private javax.swing.JLabel lblInst30;
    private javax.swing.JLabel lblInst4;
    private javax.swing.JLabel lblInst5;
    private javax.swing.JLabel lblInst6;
    private javax.swing.JLabel lblInst7;
    private javax.swing.JLabel lblInst8;
    private javax.swing.JLabel lblInst9;
    private javax.swing.JLabel lblPinsLeft;
    private javax.swing.JLabel lblPinsLeft1;
    private javax.swing.JLabel lblPinsLeft2;
    private javax.swing.JLabel lblScoring;
    private javax.swing.JLabel lblScoring1;
    private javax.swing.JLabel lblScoring10;
    private javax.swing.JLabel lblScoring2;
    private javax.swing.JLabel lblScoring3;
    private javax.swing.JLabel lblScoring4;
    private javax.swing.JLabel lblScoring5;
    private javax.swing.JLabel lblScoring6;
    private javax.swing.JLabel lblScoring7;
    private javax.swing.JLabel lblScoring8;
    private javax.swing.JLabel lblScoring9;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JPanel panelBackground;
    private javax.swing.JPanel panelBoard;
    private javax.swing.JPanel panelTextInst;
    private javax.swing.JPanel panelTextInst1;
    private javax.swing.JPanel panelTextInst2;
    private javax.swing.JPanel panelTextInst3;
    private javax.swing.JPanel panelTextInst4;
    private javax.swing.JPanel panelTextInst5;
    // End of variables declaration//GEN-END:variables
}
