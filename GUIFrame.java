
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class GUIFrame extends JFrame
{
    private JButton startButton;
    private JPanel container;
    private int windowX=1250, windowY=800;
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
    private int totalStorage = 85;
    private pageFrame[] myStorage;
    private JLabel storageText;
    private int freeStorageSlots;
    private JComboBox programList;
    private JComboBox pageList;
    private List<Integer> listOfPages;
    private List<Integer> listOfPrograms;
    private int selectedProgram=0;
    private JButton readButton;
    private JButton simButton;
    //stats
    private int totalReads=0;
    private int totalPageFualts=0;
    private int totalMissingPage=0;
    private int totalPageFound=0;
    private int pagesInMemory=0;
    private int pagesInStorage=0;
    private long startTime;

    public GUIFrame()
    {
      startTime = System.nanoTime();
      listOfPages = new ArrayList<Integer>();
      listOfPrograms = new ArrayList<Integer>();

      memoryText = new JLabel("Total Memory: "+TotalMemory*4+"KB");
      storageText = new JLabel("Total Storage: "+totalStorage*4+"KB");
      JLabel rangeText = new JLabel("Memory 0-" + (TotalMemory*4 -1) + ";      Storage " + TotalMemory*4 + "-" + ((totalStorage+TotalMemory)*4));

      freeSlots = TotalMemory;
      freeStorageSlots=totalStorage;

      myMemory = new pageFrame[TotalMemory];
      myStorage = new pageFrame[totalStorage];

      primaryMemory = new JPanel();
      primaryMemory.setLayout(new GridLayout(0,1,2,2));
      primaryMemory.setPreferredSize(new Dimension(350,570));
      primaryMemory.setBorder(BorderFactory.createLineBorder(Color.black));
      primaryMemory.setBackground(Color.gray);

      secondaryStorage = new JPanel();
      //secondaryStorage.setLayout(new GridLayout(12,11));
      secondaryStorage.setLayout(new FlowLayout());
      secondaryStorage.setPreferredSize(new Dimension(760,570));
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
		  container.setPreferredSize(new Dimension(1200,600));
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

      readButton = new JButton("Read");
      readButton.setBackground(Color.white);
      readButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
          readPage(true);
      } });

      JButton reportButton = new JButton("Report");
      reportButton.setBackground(Color.white);
      reportButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
          report();
      } });
      reportButton.setPreferredSize(new Dimension(150,75));

      simButton = new JButton("Simulate");
      simButton.setBackground(Color.white);
      simButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e)
        {
            simulate();

        } });
      simButton.setPreferredSize(new Dimension(150,75));



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
      primaryMemory.add(rangeText);
      add(container);
      memoryPanel.add(primaryMemory);
      memoryPanel.add(secondaryStorage);
      container.add(memoryPanel);
      controlPanel.add(slotValue);
      controlPanel.add(startButton);
      add(controlPanel);
      add(readPanel);
      add(reportButton);
      add(simButton);
      setVisible(true);
      /////////////////


    }


    public void report()
    {

      long timeNow = ((System.nanoTime()-startTime)/1000000000);
      String output = "===============REPORT===============\n"+
      "Total number of reads: " + totalReads +
      "\nTotal number of page faults: " + totalPageFualts +
      "\nTotal number of missing pages: " + totalMissingPage +
      "\nTotal number of pages found: " + totalPageFound +
      "\nTotal pages found in memory: " + pagesInMemory +
      "\nTotal pages found in secondary storage: " + pagesInStorage +
      "\nElapsed time: " + timeNow + " seconds" +
      "\n====================================";

      JOptionPane.showMessageDialog(null, output);
    }


    public void simulate()
    {
      startButton.setEnabled(false);
      readButton.setEnabled(false);
      programList.setEnabled(false);
      pageList.setEnabled(false);
      simButton.setEnabled(false);

      Random generator=new Random();
      int numPages = generator.nextInt(16)+1;
      slotValue.setValue(numPages);
      addToMemory();
      int x=0;


      while(x<50)
      {
        int readOrWrite = generator.nextInt(6); // 0=read 1=write

        if(readOrWrite<5)//read
        {
          programList.setSelectedIndex(generator.nextInt(programList.getItemCount()));
          pageList.setSelectedIndex(generator.nextInt(pageList.getItemCount()));
          readPage(false);
        }
        else //write new program
        {
          numPages = generator.nextInt(16)+1;
          slotValue.setValue(numPages);
          addToMemory();
        }

        x++;
      }
      startButton.setEnabled(true);
      readButton.setEnabled(true);
      programList.setEnabled(true);
      pageList.setEnabled(true);
      simButton.setEnabled(true);
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
        JProgressBar b1 = myStorage[x].getBar();
        b1.setPreferredSize(new Dimension(125,28));
        secondaryStorage.add(b1);
        kb+=4;

      }
    }

    public void readPage(boolean popup)
    {
      totalReads++;
      int readProgram =programList.getSelectedIndex();
      int readPage =pageList.getSelectedIndex() + 1;
      boolean found = false;
      int pageIndex=0;
      int whereFound =0; // 0=memory 1=secondary storage

      for (int i = 0; i<myMemory.length; i++)
      {
        if(myMemory[i].programID == readProgram && myMemory[i].pageNumber == readPage)
        {
          found = true;
          pageIndex =i;
          whereFound=0;
        }
      }

      if(!found)
      {
        for (int i = 0; i<myStorage.length; i++)
        {
          if(myStorage[i].programID == readProgram && myStorage[i].pageNumber == readPage)
          {
            found = true;
            pageIndex =i;
            whereFound=1;
          }
        }
      }
      String output ="";
      output+="\n=================READ RESULTS=================\n";

      output += "Program: " + readProgram + ", Page: " + readPage+"\n";

      if(found)
      {
        if(whereFound==0)
        {
          output += "Page found in primary storage.\nAddress: " + myMemory[pageIndex].memoryAddress+"\n";
          totalPageFound++;
          pagesInMemory++;
        }
        else if(whereFound==1)
        {
          totalPageFualts++;
          totalPageFound++;
          pagesInStorage++;
          output += "PAGE FAULT\n";
          output+= "Page found in secondary storage.\nAddress: " + myStorage[pageIndex].memoryAddress + "\n";

          //move pages
          int newAddress = swapStorageToRam(pageIndex);
          output += "Moved page to memory. New address in memory: " + myMemory[newAddress].memoryAddress+"\n";

        }

      }
      else
      {
        totalMissingPage++;
        output += "FATAL ERROR\nPAGE NOT FOUND\nPANIC\n";
      }

      output += "==============================================\n\n";

      System.out.println(output);
      if(popup)
        JOptionPane.showMessageDialog(null, output);
    }


    public int swapStorageToRam(int storageIndex)
    {
      Color cl = myStorage[storageIndex].myColor;
      int page = myStorage[storageIndex].pageNumber;
      int progra = myStorage[storageIndex].programID;
      System.out.println("Removed program "+progra+", page "+page+" from storage to be put in memory at address: "+myStorage[storageIndex].memoryAddress);
      myStorage[storageIndex].setInactive();
      freeStorageSlots++;
      int newAddress = addToMemory(cl,progra,page);
      return newAddress;
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
        int r = rand.nextInt(180);
        int g = rand.nextInt(180);
        int b = rand.nextInt(180);
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

    public int addToMemory(Color cl, int id, int pageid)
    {
        int blocks = 1;

        if(freeSlots<blocks)
          {
            System.out.println("Memory Full. Swapping page in memory to storage. Freeing 1 page.");
            freeMemory(1);
          }

        int insertedWhere=0;

        for (int x=0; x<myMemory.length; x++)
        {
          if(!myMemory[x].isOccupied)
          {
            myMemory[x].setActive(id, cl, pageid);
            insertedWhere=x;
            freeSlots--;
            return insertedWhere;
          }
        }

        return 0;
    }

    public void freeMemory(int x)
    {
      freeRandomMemory(x);
    }


    Random rand = new Random();
    public void freeRandomMemory(int x)
    {
      for (int i =0; i<x; i++ )
      {
          boolean removedMem=false;
          while(!removedMem)
          {

              int r = rand.nextInt(TotalMemory);
              if(myMemory[r].isOccupied)
              {
                System.out.println("Removed page at address: " + myMemory[r].memoryAddress +" from program ID: "+myMemory[r].programID+"; Page: " +myMemory[r].pageNumber);
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
            System.out.println("Removed page from storage at address: " + myStorage[r].memoryAddress +" from Program ID: "+myStorage[r].programID+".");
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
