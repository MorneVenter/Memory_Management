
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Random;

public class GUIFrame extends JFrame
{
    private JButton startButton;
    private JPanel container;
    private int windowX=600, windowY=700;
    private JSlider slotValue;
    private int TotalMemory = 16;
    private JPanel controlPanel;
    private pageFile[] myMemory;
    private JPanel memoryPanel;
    private int id=0;
    private int freeSlots;
    private JLabel memoryText;
    private JPanel primaryMemory;

    public GUIFrame()
    {

      memoryText = new JLabel("Total Memory: "+TotalMemory*4+"KB");

      freeSlots = TotalMemory;

      myMemory = new pageFile[TotalMemory];

      primaryMemory = new JPanel();
      primaryMemory.setLayout(new GridLayout(0,1,2,2));
      primaryMemory.setPreferredSize(new Dimension(150,500));

      setTitle("Threading");
  		setSize(windowX,windowY);
  		setResizable(false);
  		getContentPane().setBackground(Color.darkGray);
  		setDefaultCloseOperation(EXIT_ON_CLOSE);
  		setLayout(new FlowLayout());

      memoryPanel = new JPanel();


      Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		  this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);

      startButton = new JButton();
      startButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
          addToMemory();
      } });
  		startButton.setText("Add Program");
  		startButton.setBackground(Color.white);
  		startButton.setPreferredSize(new Dimension(150,40));

      container = new JPanel();
      container.setBackground(Color.darkGray);
		  container.setPreferredSize(new Dimension(550,550));
      container.setBorder(BorderFactory.createLineBorder(Color.black));


      slotValue = new JSlider();
      slotValue = new JSlider(JSlider.HORIZONTAL,0, TotalMemory, TotalMemory/4);
      slotValue.setMajorTickSpacing(TotalMemory/4);
      slotValue.setMinorTickSpacing(1);
      slotValue.setPaintTicks(true);
      slotValue.setPaintLabels(true);
      slotValue.setPreferredSize(new Dimension(325,60));
      slotValue.setBackground(Color.gray);
      slotValue.setForeground(Color.white);


      controlPanel = new JPanel();
      controlPanel.setBackground(Color.gray);
      controlPanel.setPreferredSize(new Dimension(550,75));
      controlPanel.setBorder(BorderFactory.createLineBorder(Color.black));
      controlPanel.setLayout(new GridLayout(0,2,2,2));

      myMemory = new pageFile[TotalMemory];
      initMemory();

      JLabel pagesize =  new JLabel("Page Size: 4KB");

      /////////////////
      primaryMemory.add(memoryText);
      primaryMemory.add(pagesize);
      add(container);
      memoryPanel.add(primaryMemory);
      container.add(memoryPanel);
      controlPanel.add(slotValue);
      controlPanel.add(startButton);
      add(controlPanel);
      setVisible(true);
      /////////////////


    }


    public void initMemory()
    {
      for (int x=0; x<TotalMemory; x++)
      {
        myMemory[x] = new pageFile();
        primaryMemory.add(myMemory[x].getBar());
      }
    }


    public void addToMemory()
    {
        int blocks = slotValue.getValue();
        if(freeSlots<blocks)
          {
            System.out.println("Memory Full. Freeing "+(blocks-freeSlots)+" pages.");
            freeMemory(blocks - freeSlots);
          }

        Random rand = new Random();
        int r = rand.nextInt(255);
        int g = rand.nextInt(255);
        int b = rand.nextInt(255);
        Color randomColor = new Color(r, g, b);

        int myId= id;
        id++;

        int p =blocks;

        for (int x=0; x<myMemory.length; x++)
        {
          if(!myMemory[x].isOccupied && p>0)
          {
            myMemory[x].setActive(myId, randomColor);
            p--;
            freeSlots--;
          }
        }

    }

    public void freeMemory(int x)
    {
      freeRandomMemory(x);
    }

    public void freeRandomMemory(int x)
    {
      for (int i =0; i<x; i++ )
      {
          boolean removedMem=false;
          while(!removedMem)
          {
              Random rand = new Random();
              int r = rand.nextInt(TotalMemory);
              if(myMemory[r].isOccupied)
              {
                myMemory[r].setInactive();
                freeSlots++;
                removedMem=true;
              }
          }
      }
    }


}
