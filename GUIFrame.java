
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
    private int windowX=1350, windowY=800;
    private JSlider slotValue;
    private JPanel controlPanel;
    private pageFrame[] myMemory;
    private pageFrame[] myTLB;
    private JPanel memoryPanel;
    private int id=0;
    private int freeSlots;
    private JLabel memoryText;
    private JPanel primaryMemory;
    private JPanel secondaryStorage;
    private int TotalMemory = 16;
    private int totalStorage = 85;
    private int totalTLB = 4;
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
    private JPanel tlbPanel;
    private int freeTLBslots =4;
    private int[] memoryReads;
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
      memoryReads = new int[TotalMemory];
      initMemoryReads();

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
      myTLB = new pageFrame[totalTLB];

      primaryMemory = new JPanel();
      primaryMemory.setLayout(new GridLayout(0,1,2,2));
      primaryMemory.setPreferredSize(new Dimension(350,570));
      primaryMemory.setBorder(BorderFactory.createLineBorder(Color.black));
      primaryMemory.setBackground(Color.gray);

      secondaryStorage = new JPanel();
      secondaryStorage.setLayout(new FlowLayout());
      secondaryStorage.setPreferredSize(new Dimension(760,570));
      secondaryStorage.setBorder(BorderFactory.createLineBorder(Color.black));
      secondaryStorage.setBackground(Color.gray);

      tlbPanel = new JPanel();
      tlbPanel.setLayout(new FlowLayout());
      tlbPanel.setPreferredSize(new Dimension(150,150));
      tlbPanel.setBorder(BorderFactory.createLineBorder(Color.black));
      tlbPanel.setBackground(Color.gray);
      initTLB();


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
		  container.setPreferredSize(new Dimension(1300,600));
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



      readPanel.setPreferredSize(new Dimension(650,75));
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
      memoryPanel.add(tlbPanel);
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
        int readOrWrite = generator.nextInt(9); // 0=read 1=write

        if(readOrWrite<8)//read
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

    public void initTLB()
    {
      JLabel l1 = new JLabel("TLB");
      tlbPanel.add(l1);
      int kb = 0;
      for (int x=0; x<totalTLB; x++)
      {
        myTLB[x] = new pageFrame(kb);
        JProgressBar b1 = myTLB[x].getBar();
        b1.setPreferredSize(new Dimension(125,25));
        tlbPanel.add(b1);
        kb++;

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
          //set Reads
          memoryReads[pageIndex]++;
          //add to tlb
          readTLB(myMemory[pageIndex]);
        }
        else if(whereFound==1)
        {
          totalPageFualts++;
          totalPageFound++;
          pagesInStorage++;
          output += "PAGE FAULT\n";
          output+= "Page found in secondary storage.\nAddress: " + myStorage[pageIndex].memoryAddress + "\n";
          //add to tlb

          int newAddress = swapStorageToRam(pageIndex);
          output += "Moved page to memory. New address in memory: " + myMemory[newAddress].memoryAddress+"\n";
          readTLB(myMemory[newAddress]);
          //move pages
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


    private int[] TLBReads = {0,0,0,0};

    public void addToTLB(pageFrame pg)
    {

      //free up a slot
      if(freeTLBslots<=0)
      {
        int smallestIndex=0;
        for(int i=0; i<TLBReads.length; i++)
        {
          if(TLBReads[i]<TLBReads[smallestIndex])
            smallestIndex=i;
        }

        myTLB[smallestIndex].setInactive();

        freeTLBslots++;
        TLBReads[smallestIndex] = 0;
        System.out.println("Record "+smallestIndex+" evicted from the TLB.");
      }

      int firstFreeIndex=0;
      for(int i=0; i<myTLB.length; i++)
      {
        if(!myTLB[i].isOccupied)
        {
          firstFreeIndex=i;
          i=myTLB.length+1;
        }
      }
      myTLB[firstFreeIndex].memoryAddress = pg.memoryAddress;
      myTLB[firstFreeIndex].setActive(pg.programID, pg.myColor, pg.pageNumber);
      freeTLBslots--;
      TLBReads[firstFreeIndex]=1;
      System.out.println("Record added to TLB: Program " + pg.programID +", Page " + pg.pageNumber +".");


    }

    public void readTLB(pageFrame pg)
    {
      System.out.println("\n=====================TLB=======================");
      boolean found =false;
      for(int i=0; i<myTLB.length; i++)
      {
        if(myTLB[i].pageNumber==pg.pageNumber && myTLB[i].programID==pg.programID)
        {
          found = true;
          TLBReads[i]++;
          myTLB[i].memoryAddress = pg.memoryAddress;
          myTLB[i].updateText();
        }
      }

      if(!found)
      {
        addToTLB(pg);
      }
      System.out.println("TLB Number of Reads:\nRecord 0-"+TLBReads[0] + "\nRecord 1-"+TLBReads[1]+ "\nRecord 2-"+TLBReads[2]+ "\nRecord 3-"+TLBReads[3]);
      System.out.println("===============================================\n");
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
            memoryReads[x]=1;
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
            memoryReads[x] = 1;
            return insertedWhere;
          }
        }

        return 0;
    }

    public void freeMemory(int x)
    {
      freeLeastRecentlyUsedMemory(x);
    }

    public void initMemoryReads()
    {
      for(int x=0; x<memoryReads.length; x++)
        memoryReads[x] = 0; //0 = no page present
    }

    public void freeLeastRecentlyUsedMemory(int t)
    {

      for(int i=0; i<t; i++)
      {
        int leastIndex=0;
        //find first valid index with page
        for(int x=0; x<memoryReads.length; x++)
        {
          if(memoryReads[x]>0)
            leastIndex=x;
        }
        //find the index of the least recently used
        for(int x=0; x<memoryReads.length; x++)
        {
          System.out.println(memoryReads[x]);
          if(memoryReads[x]<memoryReads[leastIndex] && memoryReads[x]>0)
            {
              leastIndex=x;
              x=memoryReads.length;
            }
        }

        System.out.println("Removed page at address: " + myMemory[leastIndex].memoryAddress +" from program ID: "+myMemory[leastIndex].programID+"; Page: " +myMemory[leastIndex].pageNumber);
        addToStorage(myMemory[leastIndex].programID, myMemory[leastIndex].myColor, myMemory[leastIndex].pageNumber);
        myMemory[leastIndex].setInactive();
        memoryReads[leastIndex]=0;
        freeSlots++;
      }
    }

    // Random rand = new Random();
    // public void freeRandomMemory(int x)
    // {
    //   for (int i =0; i<x; i++ )
    //   {
    //       boolean removedMem=false;
    //       while(!removedMem)
    //       {
    //
    //           int r = rand.nextInt(TotalMemory);
    //           if(myMemory[r].isOccupied)
    //           {
    //             System.out.println("Removed page at address: " + myMemory[r].memoryAddress +" from program ID: "+myMemory[r].programID+"; Page: " +myMemory[r].pageNumber);
    //             addToStorage(myMemory[r].programID, myMemory[r].myColor, myMemory[r].pageNumber);
    //             myMemory[r].setInactive();
    //             freeSlots++;
    //             removedMem=true;
    //           }
    //       }
    //   }
    // }



    public void addToStorage(int id, Color mycolor, int pagenum)
    {

      if(freeStorageSlots<1)
      {
        int removedID =0;
        int removedPage=0;
        boolean removeStor=false;
        while(!removeStor)
        {
          Random rand = new Random();
          int r = rand.nextInt(totalStorage);
          if(myStorage[r].isOccupied)
          {
            System.out.println("Removed page from storage at address: " + myStorage[r].memoryAddress +" from Program ID: "+myStorage[r].programID+".");
            removedID =  myStorage[r].programID;
            removedPage=myStorage[r].pageNumber;
            myStorage[r].setInactive();
            freeStorageSlots++;
            removeStor=true;

          }
        }
        for(int x=0; x<myTLB.length; x++)
        {
          if(myTLB[x].programID == removedID && myTLB[x].pageNumber==removedPage)
            {
              myTLB[x].memoryAddress=-999;
              TLBReads[x]=-1;
              myTLB[x].updateText();
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

      for(int x=0; x<myTLB.length; x++)
      {
        if(myTLB[x].programID == id && myTLB[x].pageNumber==pagenum)
          {
            myTLB[x].memoryAddress=myStorage[next].memoryAddress;
            myTLB[x].updateText();
          }
      }
    }


}
