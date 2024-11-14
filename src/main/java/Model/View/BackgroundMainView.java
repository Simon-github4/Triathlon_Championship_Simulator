package Model.View;

import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class BackgroundMainView extends JPanel{

	private static final long serialVersionUID = 1L;
	private ImageIcon img;
	
	public BackgroundMainView(int width, int height) {
            img = ViewUtils.getIcon("/resources/img/Triathlon Wallpaper.png", width, height);
    }
        
	
	@Override
	public void paint(Graphics g) {
		g.drawImage(img.getImage(), 0, 0, getWidth(), getHeight(), null);
		setOpaque(false);
		super.paintChildren(g);
	}
}