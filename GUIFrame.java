
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;


public class GUIFrame extends JFrame
{
    private JButton startButton;
    private JPanel container;
    private int windowX=600, windowY=900;
    private JSlider slotValue;
    private int TotalMemory = 16;
    private JPanel controlPanel;
    private pageFile[] myMemory;
    private JPanel memoryPanel;

    public GUIFrame()
    {

      myMemory = new pageFile[TotalMemory];

      setTitle("Threading");
  		setSize(windowX,windowY);
  		//setResizable(false);
  		getContentPane().setBackground(Color.darkGray);
  		setDefaultCloseOperation(EXIT_ON_CLOSE);
  		setLayout(new FlowLayout());

      Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		  this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);

      startButton = new JButton();
      startButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
          addToMemory();
      } });
  		startButton.setText("Add to Memory");
  		startButton.setBackground(Color.white);
  		startButton.setPreferredSize(new Dimension(150,40));

      container = new JPanel();
      container.setBackground(Color.darkGray);
		  container.setPreferredSize(new Dimension(550,750));
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


      initMemory();

      /////////////////
      add(container);
      controlPanel.add(slotValue);
      controlPanel.add(startButton);
      add(controlPanel);
      setVisible(true);
      /////////////////


      myMemory = new pageFile[TotalMemory];


    }


    public void initMemory()
    {
      for (int x=0; x<TotalMemory; x++)
      {
        myMemory[x] = new pageFile();
        container.add(myMemory[x].getBar());
      }
    }


    public void addToMemory()
    {

    }
}
