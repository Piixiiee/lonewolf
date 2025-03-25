package com.example.graphics;

import java.awt.*;
import java.awt.RenderingHints.Key;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.*;
import java.util.*;

@SuppressWarnings("deprecation")
public class Window {
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
                // graphics.setTransform(transform);

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

                canvasPosition.setSize(
                    (windowSize.getWidth() - canvasSize.getWidth() * ratio) / 2, 
                    (windowSize.getHeight() - canvasSize.getHeight() * ratio) / 2);
            }

            @Override
            public void componentShown(ComponentEvent e) {
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
        graphics.drawImage(
            image, 
            x, 
            y,
            x + width,
            y + height,
            0,
            0,
            image.getWidth(null),
            image.getHeight(null), 
            null);
    }

    private void setTransform() {
        transform.setToIdentity();
        transform.translate(canvasPosition.getWidth(), canvasPosition.getHeight());
        transform.scale(ratio, ratio);
    }

    JFrame handle;
    BufferedImage canvas;
    // JPanel canvas;
    Graphics2D graphics;
    Dimension windowSize;
    Dimension canvasSize;
    Dimension canvasPosition = new Dimension();
    float ratio = 1.0f;
    Observer updateCallback;
    AffineTransform transform = new AffineTransform();
}
