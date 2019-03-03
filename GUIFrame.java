
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


    public GUIFrame()
    {

      memoryText = new JLabel("Total Memory: "+TotalMemory+"KB");
      memoryText.setForeground(Color.white);

      freeSlots = TotalMemory;

      myMemory = new pageFile[TotalMemory];

      setTitle("Threading");
  		setSize(windowX,windowY);
  		setResizable(false);
  		getContentPane().setBackground(Color.darkGray);
  		setDefaultCloseOperation(EXIT_ON_CLOSE);
  		setLayout(new FlowLayout());

      memoryPanel = new JPanel();
      memoryPanel.setLayout(new GridLayout(0,1,2,2));

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

      /////////////////
      add(memoryText);
      add(container);
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
        memoryPanel.add(myMemory[x].getBar());
      }
    }


    public void addToMemory()
    {
        int blocks = slotValue.getValue();
        if(freeSlots<blocks)
          {
            JOptionPane.showMessageDialog(null,"Memory Full.");
            return;
          }

        int p=0;
        for (int x=0; x<myMemory.length; x++)
        {
            if(!myMemory[x].isOccupied)
            {
                boolean validSpot = true;
                for(int k=x; k<blocks; k++)
                {
                  if(myMemory[k].isOccupied)
                    validSpot = false;
                }

                if(validSpot)
                {
                  storeAt(blocks, x);
                  return;
                }
            }
        }

    }

    public void storeAt(int blocks, int start)
    {
      Random rand = new Random();
      int r = rand.nextInt(255);
      int g = rand.nextInt(255);
      int b = rand.nextInt(255);
      Color randomColor = new Color(r, g, b);

      freeSlots-=blocks;
      int myId= id;
      id++;

      for (int i = start; i<(start+blocks); i++)
      {
          myMemory[i].setActive(myId, randomColor);
      }
    }
}
