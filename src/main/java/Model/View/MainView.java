package Model.View;

import javax.swing.JFrame;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Window.Type;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.BevelBorder;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import java.awt.Color;
import javax.swing.border.TitledBorder;

import Controller.Championship;
import Model.Race.Race;
import Model.View.BackgroundMainView;
import Persistence.Persistence;

import javax.swing.border.EtchedBorder;

public class MainView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Championship controller;
	 
	public MainView() {
		setBounds(100, 100, 900, 600);
		setIconImage(ViewUtils.getIcon("/resources/img/Triatlon background.png", 100, 100).getImage());
		setTitle("Triathlon Championship");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		contentPane = new BackgroundMainView(getWidth(), getHeight());
		contentPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		setContentPane(contentPane);
		
		JLabel lblNewLabel = new JLabel("TRIATHLON CHAMPIONSHIP");
		lblNewLabel.setBounds(29, 71, 832, 64);
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setVerticalAlignment(SwingConstants.BOTTOM);
		lblNewLabel.setFont(new Font("Montserrat Black", Font.BOLD, 50));	
		contentPane.add(lblNewLabel);
				
		JButton btnClose = new JButton("Exit");
		btnClose.setBounds(338, 419, 167, 86);
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(ABORT);
				dispose();
			}
		});
		btnClose.setFont(new Font("Montserrat Medium", Font.PLAIN, 20));
		contentPane.add(btnClose);
		
		JLabel iconRightDown = new JLabel("");
		iconRightDown.setBounds(810, 475, 76, 78);
		ViewUtils.setIconToLabel(iconRightDown, "/resources/img/triatlon.png", 64, 64);
		contentPane.setLayout(null);
		contentPane.add(iconRightDown);
		
		JButton btnNewButton = new JButton("Start new championship");
		btnNewButton.setBounds(448, 322, 350, 83);
		btnNewButton.setFont(new Font("Montserrat Medium", Font.PLAIN, 20));
		btnNewButton.setHorizontalAlignment(SwingConstants.LEFT);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller = new Championship();
				controller.listenStartNewChampionship();
			}
		});
		contentPane.add(btnNewButton);
		
		JButton btnContinuechampionship = new JButton("Continue saved championship");
		btnContinuechampionship.setHorizontalAlignment(SwingConstants.LEFT);
		btnContinuechampionship.setBounds(29, 322, 350, 83);
		contentPane.add(btnContinuechampionship);
		btnContinuechampionship.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					controller = Persistence.ChampionshipDeserialization();
					
					if(controller == null)
						JOptionPane.showMessageDialog(null, "Any information saved");				

				}catch(Exception ex) {
					JOptionPane.showMessageDialog(null, "A problem with saved Championship has occured");				
				}
				controller.listenStartNewChampionship();
			}
		});
		btnContinuechampionship.setFont(new Font("Montserrat Medium", Font.PLAIN, 20));
	
	
	}

}
