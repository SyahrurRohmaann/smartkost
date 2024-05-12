package swing;

import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLayer;
import javax.swing.SwingUtilities;
import javax.swing.plaf.LayerUI;
import shadow.ShadowRenderer;

public class Tambah_Pengeluaran extends LayerUI<Component> {

    private Shape shape;
    private boolean MousePressed;
    private boolean MouseHovered;
    private Image image;
    private BufferedImage imageShadow;

    public Tambah_Pengeluaran() {
        image = new ImageIcon(getClass().getResource("/icon/plus.png")).getImage();
    }

    @Override
    public void installUI(JComponent c) {
        super.installUI(c);
        if (c instanceof JLayer) {
            ((JLayer) c).setLayerEventMask(AWTEvent.MOUSE_EVENT_MASK | AWTEvent.MOUSE_MOTION_EVENT_MASK);
        }
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        super.paint(g, c);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int size = 40;
        int x = c.getWidth() - size - 20;
        int y = c.getHeight() - size - 20;
        shape = new Ellipse2D.Double(x, y, size, size);
        if (MousePressed) {
            g2.setColor(Color.YELLOW);
        } else if (MouseHovered) {
            g2.setColor(Color.RED);
        } else {
            g2.setColor(Color.BLUE);
        }
        if (imageShadow==null){
            createImageShadow(size);
        }
        g2.drawImage(imageShadow, x, y, null);
        g2.fill(shape);
        int iconSize = 24;
        int iconX = (size - iconSize) / 2;
        int iconY = (size - iconSize) / 2;
        g2.drawImage(image, x + iconX, y + iconY, null);
        g2.dispose();
    }

    @Override
    protected void processMouseEvent(MouseEvent e, JLayer<? extends Component> l) {
        if (MouseHovered) {
            e.consume();
        }
        if (SwingUtilities.isLeftMouseButton(e)) {
            if (e.getID() == MouseEvent.MOUSE_PRESSED) {
                if (MouseHovered) {
                    MousePressed = true;
                    l.repaint(shape.getBounds());
                }
            } else if (e.getID() == MouseEvent.MOUSE_RELEASED) {
                MousePressed = false;
                l.repaint(shape.getBounds());
            }
        }
    }

    @Override
    protected void processMouseMotionEvent(MouseEvent me, JLayer<? extends Component> l) {
        Point point = SwingUtilities.convertPoint(me.getComponent(), me.getPoint(), l.getView());
        boolean hover = shape.contains(point);
        if (MouseHovered != hover) {
            MouseHovered = hover;
            l.repaint(shape.getBounds());
        }
        if (MousePressed) {
            me.consume();
        }
    }

    private void createImageShadow(int size) {
        int shadowSize = 6;
        int width = size + shadowSize * 2;
        int height = size + shadowSize * 2;
        imageShadow = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = imageShadow.createGraphics();
        g2.drawImage(new ShadowRenderer(shadowSize, 0.3f, Color.BLACK).createShadow(createShape(size)),0 ,0 ,null);
        g2.dispose();
    }

    private BufferedImage createShape(int size) {
        BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        g2.fill(new Ellipse2D.Double(0, 0, size, size));
        g2.dispose();
        return img;
    }
}
