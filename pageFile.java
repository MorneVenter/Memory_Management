import java.awt.*;
import javax.swing.*;

public class pageFile
{
  private JProgressBar bar;
  private int programID;
  public boolean isOccupied = false;

  public pageFile()
  {
    bar = new JProgressBar();
		bar.setMinimum(0);
		bar.setMaximum(10);
		bar.setPreferredSize(new Dimension (80,25));
    bar.setForeground(Color.white);
    bar.setValue(0);

  }

  public JProgressBar getBar()
  {
    return bar;
  }

  public void setActive(int id, Color myBarColor)
  {
    programID=id;
    isOccupied=true;
    bar.setStringPainted(true);
    bar.setString("ID: "+programID);
    bar.setForeground(myBarColor);
    bar.setValue(10);
  }

  public void setInactive()
  {
    programID=-1;
    isOccupied = false;
    bar.setStringPainted(false);
    bar.setString("");
    bar.setForeground(Color.white);
    bar.setValue(0);
  }

}
