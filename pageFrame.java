import java.awt.*;
import javax.swing.*;

public class pageFrame
{
  private JProgressBar bar;
  public int programID;
  public boolean isOccupied = false;
  public Color myColor;
  public int memoryAddress;
  public int pageNumber;

  public pageFrame(int mem)
  {
    bar = new JProgressBar();
		bar.setMinimum(0);
		bar.setMaximum(10);
		bar.setPreferredSize(new Dimension (80,25));
    bar.setForeground(Color.white);
    bar.setValue(0);
    bar.setStringPainted(true);
    memoryAddress = mem;
    bar.setString(memoryAddress+"");
  }

  public JProgressBar getBar()
  {
    return bar;
  }

  public void setActive(int id, Color myBarColor, int pageNum)
  {
    pageNumber = pageNum;
    programID=id;
    isOccupied=true;
    myColor = myBarColor;
    bar.setString(memoryAddress+": Pr"+programID+"-"+pageNumber);
    bar.setForeground(myBarColor);
    bar.setValue(10);
  }

  public void setInactive()
  {
    programID=-1;
    isOccupied = false;
    //bar.setStringPainted(false);
    bar.setString(memoryAddress+"");
    bar.setForeground(Color.white);
    bar.setValue(0);
  }

}
