import javax.swing.border.Border;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;

public class RoundBtn implements Border {
  private int r;

  RoundBtn(int r){
    this.r = r;
  }
  @Override
  public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
    g.drawRoundRect(x, y, 0, 0, r, r);
  }

  @Override
  public Insets getBorderInsets(Component c) {
    return new Insets(0, 0, 0, 0);
  }

  @Override
  public boolean isBorderOpaque() {
    return false;
  }
}
