package com.example.graphics;

import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.*;
import java.util.*;

@SuppressWarnings("deprecation")
public class Window {
    public static Window getInstance() { return instance; }

    public void open(Observer callback) {
    
        updateCallback = callback;

        canvasSize = new Dimension(640, 360);
        windowSize = new Dimension(1024, 576);

        canvas = new BufferedImage((int)canvasSize.getWidth(), (int)canvasSize.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        graphics = canvas.createGraphics();

        JPanel canvasWrapper = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                transform.setToIdentity();
                ((Graphics2D)g).setTransform(transform);

                g.clearRect(0, 0, (int)windowSize.getWidth(), (int)windowSize.getHeight());
                g.setColor(Color.black);
                g.fillRect(0, 0, (int)windowSize.getWidth(), (int)windowSize.getHeight());

                setTransform();
                ((Graphics2D)g).setTransform(transform);

                ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);

                updateCallback.update(null, null);

                g.drawImage(canvas, 0, 0, Color.white, null);
            }
        };
        
        canvasWrapper.setDoubleBuffered(true);
        // graphics = canvas.getGraphics();

        handle = new JFrame() {
            @Override
            public void paint(Graphics g) {
                canvasWrapper.paint(g);
                repaint();
            }
        };

        handle.setTitle("lonewolf");
        handle.add(canvasWrapper);

        handle.addComponentListener(new ComponentListener() {

            @Override
            public void componentHidden(ComponentEvent e) {
            }

            @Override
            public void componentMoved(ComponentEvent e) {
            }

            @Override
            public void componentResized(ComponentEvent e) {
                windowSize = handle.getSize();
                canvasWrapper.setSize(windowSize);

                ratio = (float)(windowSize.getWidth() / canvasSize.getWidth()); 
                if (ratio * canvasSize.getHeight() > windowSize.getHeight()) {
                    ratio = (float)(windowSize.getHeight() / canvasSize.getHeight());
                }

                canvasPosition.x = (int)(windowSize.getWidth() - canvasSize.getWidth() * ratio) / 2; 
                canvasPosition.y =   (int)(windowSize.getHeight() - canvasSize.getHeight() * ratio) / 2;
            }

            @Override
            public void componentShown(ComponentEvent e) {
            }
            
        });

        handle.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }
            
            @Override
            public void mouseEntered(MouseEvent e) {
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
            }
            
            @Override
            public void mousePressed(MouseEvent e) {
                int btnNum = e.getButton();
                if (btnNum == 1)
                    leftMouseDown = true;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                int btnNum = e.getButton();
                if (btnNum == 1)
                    leftMouseDown = false;
            }
        });

        
        handle.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                mouseMoved(e);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                mousePosition.x = (int)(e.getX() / ratio - canvasPosition.x);
                mousePosition.y = (int)(e.getY() / ratio - canvasPosition.y);
            }
        });

        handle.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        handle.setUndecorated(true);
        handle.setSize(Toolkit.getDefaultToolkit().getScreenSize());

        handle.setLayout(null);

        handle.setVisible(true);
    }

    // Clears the screen
    public void clear() {
        drawRect(0, 0, 
            (int)canvasSize.getWidth(), 
            (int)canvasSize.getHeight(),
            Color.white);
    }

    // Draws a rectangle at x,y with a specified size and color
    public void drawRect(int x, int y, int width, int height, Color color) {
        graphics.setColor(color);
        graphics.fillRect(x, y, width, height);
    }

    // Writes a desired string at position x,y with the selected Color
    public void write(int x, int y, String string, Color color) {
        graphics.setColor(color);
        graphics.setFont(new Font("SansSerif", Font.PLAIN, 10));
        graphics.drawString(string, x, y);

    }

    public void drawImage(int x, int y, int width, int height, Image image) {
        drawImage(x, y, width, height, 0, 0, image.getWidth(null), image.getHeight(null), image);
    }

    
    public void drawImage(int x, int y, int width, int height, int srcX, int srcY, int srcEndX, int srcEndY, Image image) {
        graphics.drawImage(
            image, 
            x, 
            y,
            x + width,
            y + height,
            srcX,
            srcY,
            srcEndX,
            srcEndY, 
            null);
    }

    private void setTransform() {
        transform.setToIdentity();
        transform.translate(canvasPosition.x, canvasPosition.y);
        transform.scale(ratio, ratio);
    }

    public Point getMousePosition() { return mousePosition; }

    public boolean isLeftMouseDown() { return leftMouseDown; }

    private JFrame handle;
    private BufferedImage canvas;
    // JPanel canvas;
    private Graphics2D graphics;
    private Dimension windowSize;
    private Dimension canvasSize;
    private Point canvasPosition = new Point();
    private float ratio = 1.0f;
    private Observer updateCallback;
    private AffineTransform transform = new AffineTransform();
    private Point mousePosition = new Point();
    private boolean leftMouseDown = false;
    private static Window instance = new Window();
}
