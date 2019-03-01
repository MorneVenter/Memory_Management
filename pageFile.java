import java.awt.*;
import javax.swing.*;

public class pageFile
{
  private JProgressBar bar;
  private int programID;

  public pageFile()
  {
    bar = new JProgressBar();
		bar.setMinimum(0);
		bar.setMaximum(10);
		bar.setPreferredSize(new Dimension (40,10));
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
    bar.setForeground(myBarColor);
    bar.setValue(10);
  }

  public void setInactive()
  {
    programID=-1;
    bar.setForeground(Color.white);
    bar.setValue(0);
  }

}
