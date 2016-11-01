package main;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.SwingUtilities;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author 10505053950
 */
public class ClickTerrain implements MouseListener {
    ColorChanger changeColor;
    Terrain mainTerrain;
    
    public ClickTerrain(ColorChanger alt, Terrain mainTerrain) {
        this.changeColor = alt;
        this.mainTerrain = mainTerrain;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        PointerInfo a = MouseInfo.getPointerInfo();
        Point point = new Point(a.getLocation());
        SwingUtilities.convertPointFromScreen(point, e.getComponent());
        int x = (int) point.getX();
        int y = (int) point.getY();
        changeColor.changeColor(x, y);
        mainTerrain.update();
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    
}
