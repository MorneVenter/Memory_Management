
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;

public class GUIFrame extends JFrame
{
    private JButton startButton;
    private JPanel container;
    private int windowX=1200, windowY=775;
    private JSlider slotValue;
    private JPanel controlPanel;
    private pageFrame[] myMemory;
    private JPanel memoryPanel;
    private int id=0;
    private int freeSlots;
    private JLabel memoryText;
    private JPanel primaryMemory;
    private JPanel secondaryStorage;
    private int TotalMemory = 16;
    private int totalStorage = 64;
    private pageFrame[] myStorage;
    private JLabel storageText;
    private int freeStorageSlots;
    private JComboBox programList;
    private JComboBox pageList;
    private List<Integer> listOfPages;
    private List<Integer> listOfPrograms;
    private int selectedProgram=0;

    public GUIFrame()
    {

      listOfPages = new ArrayList<Integer>();
      listOfPrograms = new ArrayList<Integer>();

      memoryText = new JLabel("Total Memory: "+TotalMemory*4+"KB");
      storageText = new JLabel("Total Storage: "+totalStorage*4+"KB");

      freeSlots = TotalMemory;
      freeStorageSlots=totalStorage;

      myMemory = new pageFrame[TotalMemory];
      myStorage = new pageFrame[totalStorage];

      primaryMemory = new JPanel();
      primaryMemory.setLayout(new GridLayout(0,1,2,2));
      primaryMemory.setPreferredSize(new Dimension(350,500));
      primaryMemory.setBorder(BorderFactory.createLineBorder(Color.black));
      primaryMemory.setBackground(Color.gray);

      secondaryStorage = new JPanel();
      secondaryStorage.setLayout(new GridLayout(8,8));
      secondaryStorage.setPreferredSize(new Dimension(750,250));
      secondaryStorage.setBorder(BorderFactory.createLineBorder(Color.black));
      secondaryStorage.setBackground(Color.gray);

      setTitle("Memory Management");
  		setSize(windowX,windowY);
  		setResizable(false);
  		getContentPane().setBackground(Color.darkGray);
  		setDefaultCloseOperation(EXIT_ON_CLOSE);
  		setLayout(new FlowLayout());

      memoryPanel = new JPanel();
      memoryPanel.setBackground(Color.darkGray);

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
		  container.setPreferredSize(new Dimension(1180,550));
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

      myMemory = new pageFrame[TotalMemory];
      initMemory();
      initStorage();

      JLabel pagesize =  new JLabel("Page Size: 4KB");

      JPanel readPanel = new JPanel();
      programList = new JComboBox(); //add change event\
      programList.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
          fillPageList();
      } });
      pageList = new JComboBox();

      JButton readButton = new JButton("Read");
      readButton.setBackground(Color.white);
      readButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
          readPage();
      } });


      readPanel.setPreferredSize(new Dimension(550,75));
      readPanel.setBorder(BorderFactory.createLineBorder(Color.black));
      readPanel.setLayout(new GridLayout(0,3,2,2));
      readPanel.setBackground(Color.gray);

      readPanel.add(programList);
      readPanel.add(pageList);
      readPanel.add(readButton);

      /////////////////
      primaryMemory.add(memoryText);
      primaryMemory.add(pagesize);
      primaryMemory.add(storageText);
      add(container);
      memoryPanel.add(primaryMemory);
      memoryPanel.add(secondaryStorage);
      container.add(memoryPanel);
      controlPanel.add(slotValue);
      controlPanel.add(startButton);
      add(controlPanel);
      add(readPanel);
      setVisible(true);
      /////////////////


    }


    public void initMemory()
    {
      int kb = 0;
      for (int x=0; x<TotalMemory; x++)
      {
        myMemory[x] = new pageFrame(kb);
        primaryMemory.add(myMemory[x].getBar());
        kb+=4;

      }
    }

    public void initStorage()
    {
      int kb = TotalMemory*4;
      for (int x=0; x<totalStorage; x++)
      {
        myStorage[x] = new pageFrame(kb);
        secondaryStorage.add(myStorage[x].getBar());
        kb+=4;

      }
    }

    public void readPage()
    {

    }


    public void fillPageList()
    {
        pageList.removeAllItems();
        selectedProgram = programList.getSelectedIndex();
        int k =1;
        for (int x=0; x<listOfPages.size(); x++)
        {
            if(selectedProgram==listOfPages.get(x))
            {
              pageList.addItem("Page " +k);
              k++;
            }
        }
    }

    public void fillProgramList()
    {
        programList.removeAllItems();

        for (int x=0; x<listOfPrograms.size(); x++)
        {
            programList.addItem("Program " + listOfPrograms.get(x));
        }
    }

    public void addToMemory()
    {
        int blocks = slotValue.getValue();
        if(blocks<=0)
        return;

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

        listOfPrograms.add(myId);
        for (int x = 0; x<blocks; x++)
        {
            listOfPages.add(myId);
        }

        fillProgramList();

        int p =blocks;
        int i =  1;
        for (int x=0; x<myMemory.length; x++)
        {
          if(!myMemory[x].isOccupied && p>0)
          {
            myMemory[x].setActive(myId, randomColor, i);
            i++;
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
                System.out.println("Removed page at index: " + r +" from proccess ID: "+myMemory[r].programID+".");
                addToStorage(myMemory[r].programID, myMemory[r].myColor, myMemory[r].pageNumber);
                myMemory[r].setInactive();
                freeSlots++;
                removedMem=true;
              }
          }
      }
    }

    public void addToStorage(int id, Color mycolor, int pagenum)
    {

      if(freeStorageSlots<1)
      {
        boolean removeStor=false;
        while(!removeStor)
        {
          Random rand = new Random();
          int r = rand.nextInt(totalStorage);
          if(myStorage[r].isOccupied)
          {
            System.out.println("Removed page from storage at index: " + r +" from proccess ID: "+myStorage[r].programID+".");
            myStorage[r].setInactive();
            freeStorageSlots++;
            removeStor=true;
          }
        }
      }



      boolean found = false;
      int i=0;
      int next=0;
      while(!found)
      {
        if(!myStorage[i].isOccupied)
        {
          next=i;
          found = true;
        }
        i++;
      }

      myStorage[next].setActive(id, mycolor, pagenum);
      freeStorageSlots--;
    }


}
