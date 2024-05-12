
package swing;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JComboBox;


public class Bulan_Detail extends JComboBox{
    
    public Bulan_Detail(){
            }
    
    @Override
    protected void paintChildren(Graphics grafik) {
        Graphics2D g2 = (Graphics2D) grafik;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint g = new GradientPaint(getWidth()- 20, 0, Color.decode("#FF7396"), 0, getHeight(), Color.decode("#CF44C7"));
        g2.setPaint(g);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
        g2.fillRect(getWidth() - 20, 0, getWidth(), getHeight());
        super.paintChildren(grafik);
    }
}