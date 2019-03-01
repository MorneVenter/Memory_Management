
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;


public class GUIFrame extends JFrame
{
    private JButton startButton;
    private JPanel container;
    private int windowX=1000, windowY=500;
    private JSlider slotValue;
    private int TotalMemory = 64;
    private JPanel controlPanel;

    public GUIFrame()
    {
      setTitle("Threading");
  		setSize(windowX,windowY);
  		setResizable(false);
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
  		startButton.setPreferredSize(new Dimension(250,40));

      container = new JPanel();
      container.setBackground(Color.darkGray);
		  container.setPreferredSize(new Dimension(650,500));

      slotValue = new JSlider();
      slotValue = new JSlider(JSlider.HORIZONTAL,1, TotalMemory, 15);
      slotValue.setMajorTickSpacing(16);
      slotValue.setMinorTickSpacing(4);
      slotValue.setPaintTicks(true);

      controlPanel = new JPanel();

      /////////////////
      add(container);
      controlPanel.add(slotValue);
      controlPanel.add(startButton);
      add(controlPanel);
      setVisible(true);
      /////////////////
    }

    public void addToMemory()
    {

    }
}
