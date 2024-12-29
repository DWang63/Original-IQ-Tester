/*
 June 22nd, 2023
 Created by Derek Wang
 This program outputs a frame that shows the leaderboard of the game
 */

//Imports for XML
import java.io.*;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.xml.sax.SAXException;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;
import org.xml.sax.*;


/**
 *
 * @author dw933
 */
public class Leaderboard extends javax.swing.JFrame {
    //Declare arraylists to hold player name, high score, and total score
    ArrayList <String> names = new ArrayList<String>();
    ArrayList <String> highScores = new ArrayList<String>();
    ArrayList <String> totalScores = new ArrayList<String>();
    
    
    
    public Leaderboard() {
        initComponents();
        
        //Get player information from XML
        getXML();
        
        //Output leaderboard values
        outputLBoard(1);
    }
    
    /*
    getXML
    
    This procedure reads the XML file and stores the information in 3 arrayLists
    
    Parameters: none
    
    Returns: none 
    */
    public void getXML()
    {
        /* This next section is creating the original XML file with the information.
        It's commented out now because I don't want to keep creating a new XML file, but rather edit it
        Create try statmenet while creating xml*/
        try{
            /*
            OutputStream fout = new FileOutputStream("leaderboard.xml");
            OutputStream bout = new BufferedOutputStream(fout);
            OutputStreamWriter out = new OutputStreamWriter(bout, "8859_1");
            
            //the following two lines define the XML coding.
            out.write("<?xml version=\"1.0\" ");
            out.write("encoding=\"ISO-8859-1\"?>\r\n");
            
            //Create info class
            out.write("<info>\r\n");
            
            //Create information about player Derek
            out.write("<player>\r\n");  //Opens player element
            out.write("<name>Derek</name>\r\n");  //Add player name
            out.write("<highScore>25</highScore>\r\n");  //Add high score
            out.write("<totalScore>25</totalScore>\r\n");  //Add total score
            out.write("</player>\r\n");  //Closes player element
            
            //Create information about player Liam
            out.write("<player>\r\n");  //Opens player element
            out.write("<name>Liam</name>\r\n");
            out.write("<highScore>10</highScore>\r\n");
            out.write("<totalScore>20</totalScore>\r\n");
            out.write("</player>\r\n");  //Closes player element
            
            //Create information about player Ricki
            out.write("<player>\r\n");  //Opens player element
            out.write("<name>Ricki</name>\r\n");  //Add player name
            out.write("<highScore>50</highScore>\r\n");  //Add high score
            out.write("<totalScore>75</totalScore>\r\n");  //Add total score
            out.write("</player>\r\n");  //Closes player element
            
            //Create information about player Ezra
            out.write("<player>\r\n");  //Opens player element
            out.write("<name>Ezra</name>\r\n");  //Add player name
            out.write("<highScore>50</highScore>\r\n");  //Add high score
            out.write("<totalScore>95</totalScore>\r\n");  //Add total score
            out.write("</player>\r\n");  //Closes player element
            
            //Create information about player Simon
            out.write("<player>\r\n");  //Opens player element
            out.write("<name>Simon</name>\r\n");  //Add player name
            out.write("<highScore>100</highScore>\r\n");  //Add high score
            out.write("<totalScore>100</totalScore>\r\n");  //Add total score
            out.write("</player>\r\n");  //Closes player element
            
            //Create information about player Zeke
            out.write("<player>\r\n");  //Opens player element
            out.write("<name>Zeke</name>\r\n");  //Add player name
            out.write("<highScore>25</highScore>\r\n");  //Add high score
            out.write("<totalScore>25</totalScore>\r\n");  //Add total score
            out.write("</player>\r\n");  //Closes player element
            
            //Create information about player Jim
            out.write("<player>\r\n");  //Opens player element
            out.write("<name>Jim</name>\r\n");  //Add player name
            out.write("<highScore>0</highScore>\r\n");  //Add high score
            out.write("<totalScore>0</totalScore>\r\n");  //Add total score
            out.write("</player>\r\n");  //Closes player element
            
            //Create information about player Kyle
            out.write("<player>\r\n");  //Opens player element
            out.write("<name>Kyle</name>\r\n");  //Add player name
            out.write("<highScore>0</highScore>\r\n");  //Add high score
            out.write("<totalScore>0</totalScore>\r\n");  //Add total score
            out.write("</player>\r\n");  //Closes player element
            
            //Create information about player Jon
            out.write("<player>\r\n");  //Opens player element
            out.write("<name>Jon</name>\r\n");  //Add player name
            out.write("<highScore>0</highScore>\r\n");  //Add high score
            out.write("<totalScore>0</totalScore>\r\n");  //Add total score
            out.write("</player>\r\n");  //Closes player element
            
            //Create information about player Victor
            out.write("<player>\r\n");  //Opens player element
            out.write("<name>Victor</name>\r\n");  //Add player name
            out.write("<highScore>0</highScore>\r\n");  //Add high score
            out.write("<totalScore>0</totalScore>\r\n");  //Add total score
            out.write("</player>\r\n");  //Closes player element
            
            //Close info class
            out.write("</info>");
            
            //Flush and close buffer
            out.flush();
            out.close();
            */
            
            //Declare strings for name, high score, and total score. They will need to be put into the array list as strings
            String name;
            String highScore;
            String totalScore;
            
            //Create string for file name
            String fileName = "leaderboard.xml";
            //Declare and initialize docFactory object to create doc
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            //Declare and initalize docBuilder
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            //Create a document called file. Assign information xml to this file
            Document file = docBuilder.parse(fileName);
            
            //Make an node list of players in XML file
            NodeList players = file.getElementsByTagName("player");
            
            //Run for loop to iterate through each player element in XML
            for (int i = 0; i < players.getLength(); i++)
            {
                //Declare nodes for names, high score, total score
                Node nameNode = file.getElementsByTagName("name").item(i);  //Create name node
                name = nameNode.getTextContent();  //Get the text stored in name
                names.add(name);  //Place text into name arrayList
                
                //Repeat same process for high score and total score
                Node highScoreNode = file.getElementsByTagName("highScore").item(i);
                highScore = highScoreNode.getTextContent();
                highScores.add(highScore);
                
                Node totalScoreNode = file.getElementsByTagName("totalScore").item(i);
                totalScore = totalScoreNode.getTextContent();
                totalScores.add(totalScore);
            }
        }
        
        //Catch statements for numerous errors
        catch(ParserConfigurationException | IOException | SAXException pce){
            pce.printStackTrace();
        }
        
    }
    
    /*
    quickSort
    
    This function sorts a list of integers by periodically finding where each item's final sorted position is,
    and then redoing the process without the found items
    
    Parameters: int[], int, int, int[], String[]
    
    Returns: none    
    */
    public void quickSort(int[] listMain, int low, int high, int[] otherList, String[] nameList)
    {
        //Declare variable j which will hold the index of the partitioned value
        int j;
        
        //Check that high is above low
        if (high > low)
        {
            //Partition an individual value
            j = partition(listMain, low, high, otherList, nameList);
              
            //Run the quicksort algorithm again on the list of numbers above below j and above j
            quickSort(listMain, low, j - 1, otherList, nameList);
            quickSort(listMain, j + 1, high, otherList, nameList);                    
        }
    }
    
    /*
    partition
    
    This function partitions a single number in bubblesort, 
    essentially finding its final sorted position without solving the numbers around it
    
    Parameters: int[], int, int, int[], string[]
    
    Returns: int
    
    */
    public int partition(int[] list, int low, int high, int[] otherList, String[] nameList)
    {
        //Declare ints for partition
        int i = low,  //Placeholder value for pivot's final position
        pivot,  //Value that we compare each item to
         temp;  //Temporary int for swapping values
        String tempStr;  //Temporary string name
        
        //Choose the last item as the pivot
        pivot = list[high];
        
        
        //Run for loop to check each item if it's smaller than the pivot
        for (int j = low; j < high; j++)
        {
            //Check if current value is smaller than pivot
            if (list[j] >= pivot)
            {
                /*Swap values of i and j, to place j below the pivot
                Swap values for all 3 arrays*/
                temp = list[i];
                list[i] = list[j];
                list[j] = temp;
                //Other list
                temp = otherList[i];
                otherList[i] = otherList[j];
                otherList[j] = temp;
                //Name list
                tempStr = nameList[i];
                nameList[i] = nameList[j];
                nameList[j] = tempStr;
                
                //Increment i
                i++;
            }
        }
        
        //Swap point at i with the pivot
        //Repeat for all 3 arrays
        temp = list[high];
        list[high] = list[i];
        list[i] = temp;
        //Other list
        temp = otherList[high];
        otherList[high] = otherList[i];
        otherList[i] = temp;
        //NameList
        tempStr = nameList[high];
        nameList[high] = nameList[i];
        nameList[i] = tempStr;
        

        //Return i which is index of pivot
        return (i);
    }
    
    /*
    outputLBoard
    
    This procedure sorts the arrays by high score or total score depending on an inputted 'key',
    and then outputs the top 10 players
    
    Parameters: int
    
    Returns: none 
    */
    public void outputLBoard(int key)
    {
        //Declare arrays to transfer the arrayList values to
        String[] nameArray = new String[names.size()];
        int[] highScoreArray = new int[highScores.size()];
        int[] totalScoreArray = new int[totalScores.size()];
        
        //Transfer values in a for loop
        for (int i = 0; i < names.size(); i++)
        {
            //Transfer specific i values to other array
            nameArray[i] = names.get(i);
            highScoreArray[i] = Integer.parseInt(highScores.get(i));
            totalScoreArray[i] = Integer.parseInt(totalScores.get(i));
        }
        
        //Check which key was inputted(0 means sort by high score, 1 means sort by total score)
        if (key == 0)
        {
            //Use quicksort to sort the arrays using high score as the reference to sort by
            quickSort(highScoreArray, 0, highScoreArray.length - 1, totalScoreArray, nameArray);
        }
        
        //If key is 1 then sort boy total score
        else if (key == 1)
        {
            //Use quicksort to sort the arrays using total score as the reference to sort by
            quickSort(totalScoreArray, 0, highScoreArray.length - 1, highScoreArray, nameArray);
        }
        
        //Update leaderboard label
        lblStats.setText("<html>#1 " + nameArray[0] + " | High Score: " + highScoreArray[0] + " | Total Score: " + totalScoreArray[0] + "<br/>"
                + "#2 " + nameArray[1] + " | High Score: " + highScoreArray[1] + " | Total Score: " + totalScoreArray[1] + "<br/>"
                + "#3 " + nameArray[2] + " | High Score: " + highScoreArray[2] + " | Total Score: " + totalScoreArray[2] + "<br/>"     
                + "#4 " + nameArray[3] + " | High Score: " + highScoreArray[3] + " | Total Score: " + totalScoreArray[3] + "<br/>"       
                + "#5 " + nameArray[4] + " | High Score: " + highScoreArray[4] + " | Total Score: " + totalScoreArray[4] + "<br/>"       
                + "#6 " + nameArray[5] + " | High Score: " + highScoreArray[5] + " | Total Score: " + totalScoreArray[5] + "<br/>"       
                + "#7 " + nameArray[6] + " | High Score: " + highScoreArray[6] + " | Total Score: " + totalScoreArray[6] + "<br/>"       
                + "#8 " + nameArray[7] + " | High Score: " + highScoreArray[7] + " | Total Score: " + totalScoreArray[7] + "<br/>"       
                + "#9 " + nameArray[8] + " | High Score: " + highScoreArray[8] + " | Total Score: " + totalScoreArray[8] + "<br/>"       
                + "#10 " + nameArray[9] + " | High Score: " + highScoreArray[9] + " | Total Score: " + totalScoreArray[9] + "<br/>"                               
                + "<html>");
        
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
        lblStats = new javax.swing.JLabel();
        btnTS = new javax.swing.JButton();
        btnHS = new javax.swing.JButton();
        btnDone = new javax.swing.JButton();
        lblLeaderboard = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panelBackground.setBackground(new java.awt.Color(255, 255, 255));

        lblStats.setFont(new java.awt.Font("Yu Gothic Medium", 0, 16)); // NOI18N

        btnTS.setFont(new java.awt.Font("Yu Gothic Medium", 0, 16)); // NOI18N
        btnTS.setText("Sort by Total Score");
        btnTS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTSActionPerformed(evt);
            }
        });

        btnHS.setFont(new java.awt.Font("Yu Gothic Medium", 0, 16)); // NOI18N
        btnHS.setText("Sort by Highest Score");
        btnHS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHSActionPerformed(evt);
            }
        });

        btnDone.setFont(new java.awt.Font("Yu Gothic Medium", 0, 16)); // NOI18N
        btnDone.setText("Done");
        btnDone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDoneActionPerformed(evt);
            }
        });

        lblLeaderboard.setFont(new java.awt.Font("Yu Gothic UI", 1, 28)); // NOI18N
        lblLeaderboard.setText("Leaderboard");

        javax.swing.GroupLayout panelBackgroundLayout = new javax.swing.GroupLayout(panelBackground);
        panelBackground.setLayout(panelBackgroundLayout);
        panelBackgroundLayout.setHorizontalGroup(
            panelBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBackgroundLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBackgroundLayout.createSequentialGroup()
                        .addGap(116, 116, 116)
                        .addComponent(btnDone, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBackgroundLayout.createSequentialGroup()
                        .addComponent(btnTS, javax.swing.GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(btnHS, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(panelBackgroundLayout.createSequentialGroup()
                .addGap(136, 136, 136)
                .addComponent(lblLeaderboard)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBackgroundLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblStats, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39))
        );
        panelBackgroundLayout.setVerticalGroup(
            panelBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBackgroundLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblLeaderboard)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblStats, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panelBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTS)
                    .addComponent(btnHS))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnDone)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelBackground, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelBackground, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnDoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDoneActionPerformed
        //Dispose of frame
        dispose();
    }//GEN-LAST:event_btnDoneActionPerformed

    private void btnTSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTSActionPerformed
        //Use outputLBoard with key 0 meaning sort by high score
        outputLBoard(1);
    }//GEN-LAST:event_btnTSActionPerformed

    private void btnHSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHSActionPerformed
        //Use outputLBoard with key 0 meaning sort by high score
        outputLBoard(0);
    }//GEN-LAST:event_btnHSActionPerformed

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
            java.util.logging.Logger.getLogger(Leaderboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Leaderboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Leaderboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Leaderboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Leaderboard().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDone;
    private javax.swing.JButton btnHS;
    private javax.swing.JButton btnTS;
    private javax.swing.JLabel lblLeaderboard;
    private javax.swing.JLabel lblStats;
    private javax.swing.JPanel panelBackground;
    // End of variables declaration//GEN-END:variables
}
