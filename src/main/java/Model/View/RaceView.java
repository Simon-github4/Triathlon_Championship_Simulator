package Model.View;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

import Controller.Championship;
import Model.ClimateCondition.ClimateCondition;
import Model.Discipline.Provisioning;
import Model.Race.AthleteRaceInformation;
import Model.Race.Race;
import Persistence.Persistence;

import javax.swing.JTabbedPane;
import javax.swing.JToggleButton;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeListener;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.event.ChangeEvent;

public class RaceView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblClimateCondition;
	private JLabel lblRaceTime;
	private JLabel lblTitulo;
	private final ButtonGroup buttonGroupPause = new ButtonGroup();
	private JRadioButton rdbtnPause;
	private JRadioButton rdbtnResume;
	private JButton btnNextRace;
	private JButton btnSerialize;
	private JLabel lblMessage;
	
	public RaceView(String titleRace) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	 	setTitle("Race");
		setBounds(100, 100, 1410, 1000);
		setExtendedState(MAXIMIZED_BOTH);
		setResizable(false);
		
		contentPane = new JPanel();
		contentPane.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1.setBounds(41, 28, 328, 106);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		lblRaceTime = new JLabel("Race Time:");
		lblRaceTime.setBounds(6, 55, 202, 41);
		panel_1.add(lblRaceTime);
		lblRaceTime.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblRaceTime.setHorizontalAlignment(SwingConstants.LEFT);
		ViewUtils.setIconToLabel(lblRaceTime, "/resources/img/gestion-del-tiempo.png", 32, 32);
		
		lblClimateCondition = new JLabel("Climate condition:");
		lblClimateCondition.setBounds(6, 15, 322, 39);
		panel_1.add(lblClimateCondition);
		lblClimateCondition.setHorizontalAlignment(SwingConstants.LEFT);
		lblClimateCondition.setFont(new Font("Tahoma", Font.PLAIN, 14));
		ViewUtils.setIconToLabel(lblClimateCondition, "/resources/img/nublado.png", 32, 32);
		
		JLabel lblNewLabel_2 = new JLabel("SWIMMING");
		ViewUtils.setIconToLabel(lblNewLabel_2, "/resources/img/nadando.png", 32, 32);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_2.setBounds(431, 111, 118, 29);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_2_1 = new JLabel("CYCLISM");
		ViewUtils.setIconToLabel(lblNewLabel_2_1, "/resources/img/ciclismo.png", 32, 32);
		lblNewLabel_2_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_2_1.setBounds(782, 111, 118, 29);
		contentPane.add(lblNewLabel_2_1);
		
		JLabel lblNewLabel_2_2 = new JLabel("PEDESTRISM");
		ViewUtils.setIconToLabel(lblNewLabel_2_2, "/resources/img/capacitacion.png", 32, 32);
		lblNewLabel_2_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_2_2.setBounds(1100, 106, 138, 39);
		contentPane.add(lblNewLabel_2_2);
		
		lblTitulo = new JLabel("RACE: " + titleRace);
		lblTitulo.setFont(new Font("Montserrat Black", Font.ITALIC, 44));
		lblTitulo.setHorizontalAlignment(SwingConstants.LEFT);
		lblTitulo.setBounds(490, 6, 846, 78);
		contentPane.add(lblTitulo);	
		
		JButton btnExitRace = new JButton("Exit");
		btnExitRace.setBounds(41, 601, 190, 55);
		btnExitRace.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				dispose();
				Championship.getInstance().listenExitRace();
			}
		});
		contentPane.add(btnExitRace);
		
		JButton btnStartRace = new JButton("Start");
		btnStartRace.setBounds(41, 276, 190, 55);
		btnStartRace.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					Championship.getInstance().listenStartRace();
					buttonGroupPause.setSelected(rdbtnResume.getModel(), true);
					btnStartRace.setEnabled(false);
			}
		});
		contentPane.add(btnStartRace);
		
		JButton btnNewButton = new JButton("Championship Statistics");
		btnNewButton.setBounds(41, 341, 190, 55);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Championship.getInstance().listenShowCurrentRanking();
			}
			
		});
		contentPane.add(btnNewButton);
		
		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(64, 64, 64), Color.BLACK, new Color(192, 192, 192), null));
		panel.setBounds(41, 160, 190, 85);
		contentPane.add(panel);
		panel.setLayout(null);
		
		rdbtnPause = new JRadioButton("Pause Race");
		rdbtnPause.setBounds(6, 15, 178, 21);
		panel.add(rdbtnPause);
		rdbtnPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Championship.getInstance().listenInterruptRace(true);
				
			}
		});
		buttonGroupPause.add(rdbtnPause);
		
		rdbtnResume = new JRadioButton("Resume Race");
		rdbtnResume.setBounds(6, 52, 178, 21);
		panel.add(rdbtnResume);
		rdbtnResume.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Championship.getInstance().listenInterruptRace(false);

			}
		});
		buttonGroupPause.add(rdbtnResume);
		
		btnNextRace = new JButton("Next Race");
		btnNextRace.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Championship.getInstance().listenStartNewRace();
			}
		});
		btnNextRace.setBounds(41, 406, 190, 55);
		btnNextRace.setEnabled(false);
		contentPane.add(btnNextRace);
		
		btnSerialize = new JButton("Exit and Save");
		btnSerialize.setEnabled(false);
		btnSerialize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Persistence.championshipPersistence(Championship.getInstance());
				dispose();
			}
		});
		
		btnSerialize.setBounds(41, 471, 190, 55);
		contentPane.add(btnSerialize);
		
		JButton btnNewButton_1 = new JButton("Update Weather conditions");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UpdateWeatherView crudView = new UpdateWeatherView();
				crudView.setVisible(true);
				crudView.setLocationRelativeTo(null);
			}
		});
		btnNewButton_1.setBounds(41, 536, 190, 55);
		contentPane.add(btnNewButton_1);
		
		lblMessage = new JLabel("");
		lblMessage.setFont(new Font("SansSerif", Font.PLAIN, 16));
		lblMessage.setForeground(Color.RED);
		lblMessage.setBounds(287, 712, 852, 44);
		contentPane.add(lblMessage);
		
		
	}
	 
	//Methods    

	public void refreshInfo(float time, ClimateCondition currentWeather) {
		this.lblRaceTime.setText("Race Time: " + Float.valueOf(time).shortValue() + " seconds");
		this.lblClimateCondition.setText("Climate condition:" + currentWeather.getDescription());
	}	
	
	public void finishRace() {
		JOptionPane.showMessageDialog(null, "Race has Finished!");
		Championship.getInstance().listenShowCurrentRanking();
		this.getBtnNextRace().setEnabled(true);
        this.btnSerialize.setEnabled(true);
	}
	
	public void problemPause() {
		pause();
		JOptionPane.showMessageDialog(null, "There was a small problem updating the positions, press 'OK' to continue the race.");
	}
	
	public void pause() {
	 	rdbtnPause.doClick();
	}
	
	public void finishChampionship(String amateur,  String competition) {
		JOptionPane.showInternalMessageDialog(null, "The Competition category Winner of the Championship is: " + competition +
													"\nThe Amateur category Winner of the Championship is: " + amateur, 
													"Championship End", 2, (new ImageIcon("img\\trofeo.png")));
	}
	//Getters and Setters
	
	public JPanel getContentPane() {
		return contentPane;
	}
	 	 
	public JLabel getLblClimateCondition() {
		return lblClimateCondition;
	}

	public JLabel getLblMessage() {
		return lblMessage;
	}
	
	public JButton getBtnNextRace() {
		return btnNextRace;
	}

	public void setLblClimateCondition(JLabel lblClimateCondition) {
		this.lblClimateCondition = lblClimateCondition;
	}

	public JLabel getLblRaceTime() {
		return lblRaceTime;
	}

	public void setLblRaceTime(JLabel lblRaceTime) {
		this.lblRaceTime = lblRaceTime;
	}

	public JLabel getLblTitulo() {
		return lblTitulo;
	}

	public void setLblTitulo(JLabel lblTitulo) {
		this.lblTitulo = lblTitulo;
	}
}
