package Model.View;


import java.awt.Color;
import java.awt.Font;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import com.jgoodies.forms.factories.DefaultComponentFactory;

import DAO.WeatherConditionsDAO;
import Model.ClimateCondition.ClimateCondition;
import Model.ClimateCondition.UnitMeasure;

import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UpdateWeatherView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField descriptionField;
    private JTextField measurementUnitField;
    private JTextField upperTierField;
    private JTextField lowerTierField;
    private JTextField swimmingImpactField;
    private JTextField cyclingImpactField;
    private JTextField pedestrianismImpactField;
    private JTable table;
    private DefaultTableModel tableModel;
    
	public UpdateWeatherView() {
		setBounds(100, 100, 1144, 604);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setLayout(null);
		
		initComponents();
		
		tableModel = new DefaultTableModel(){
	        @Override
	        public boolean isCellEditable(int row, int column) {
	            return false; // Make cells NON editable
	        }
	    };
		tableModel.addColumn("Description");
		tableModel.addColumn("Measurement Unit");
		tableModel.addColumn("Lower Mark");
		tableModel.addColumn("Upper Mark");
		tableModel.addColumn("Swimming Impact");
		tableModel.addColumn("Cycling Impact");
		tableModel.addColumn("Pedestrianism Impact");
		tableModel.addColumn("Id");

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(324, 85, 796, 458);
		contentPane.add(scrollPane);
		
		table = new JTable(tableModel);
		table.setFont(new Font("Tahoma", Font.PLAIN, 13));
	    table.setFillsViewportHeight(true);
	    table.setBorder(new LineBorder(new Color(0, 0, 0)));
	    table.getColumnModel().getColumn(0).setPreferredWidth(140);
	    table.getColumnModel().getColumn(7).setPreferredWidth(60);
	    scrollPane.setViewportView(table);
	    
	    table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                    // Show row selected data in every field
                        String description = (String) tableModel.getValueAt(selectedRow, 0);
                        String unitMeasure = (String) tableModel.getValueAt(selectedRow, 1);
                        String lowerMark = String.valueOf(tableModel.getValueAt(selectedRow, 2));
                        String upperMark = String.valueOf(tableModel.getValueAt(selectedRow, 3));
                        String swimmingImpact = String.valueOf(tableModel.getValueAt(selectedRow, 4));
                        String cyclingImpact = String.valueOf(tableModel.getValueAt(selectedRow, 5));
                        String pedestrianismImpact = String.valueOf(tableModel.getValueAt(selectedRow, 6));
                        
                        descriptionField.setText(description);
                        measurementUnitField.setText(unitMeasure);
                        lowerTierField.setText(lowerMark);
                		upperTierField.setText(upperMark);
                		swimmingImpactField.setText(swimmingImpact);
                		cyclingImpactField.setText(cyclingImpact);
                		pedestrianismImpactField.setText(pedestrianismImpact);
                    }
                }
            }
        });	    
	    loadTable();

	    JButton btnNewButton = new JButton("Confirm");
	    btnNewButton.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		if(validateFields()) {	
		    		if(table.getSelectedRow() == -1) 
		    			insertWeatherCondition();
		    		else 
		    			updateWeatherCondition();

	    		}
	    	}
	    });
		btnNewButton.setBounds(23, 445, 140, 55);
		contentPane.add(btnNewButton);
		
		JButton btnEliminar = new JButton("Delete");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
    			
				deleteWeatherCondition();
			}
		});
		btnEliminar.setBounds(173, 445, 141, 55);
		contentPane.add(btnEliminar);
		
		JButton btnNewButton_2 = new JButton("Cancel");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearFields();
			}
		});
		btnNewButton_2.setBounds(23, 510, 291, 33);
		contentPane.add(btnNewButton_2);
		

	    
	}

	private void deleteWeatherCondition() {
		
		int row = table.getSelectedRow();
		if (row != -1) {
			int id = (int)tableModel.getValueAt(row, 7);
			if(JOptionPane.showConfirmDialog(null,"do you want to delete the condition in row:"+ String.valueOf(row+1),"", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
				try{
					WeatherConditionsDAO.deleteWeatherCondition(id);
					showMessage("", "Weather condition Deleted.", JOptionPane.INFORMATION_MESSAGE);
					clearFields();
			        loadTable();
				
				} catch (SQLException e) {
					showMessage("Error", "Error trying to delete: " + e.getMessage(), JOptionPane.ERROR_MESSAGE);
				}
		}
	}

	private void insertWeatherCondition() {
		String description = descriptionField.getText();
        String measurementUnit = measurementUnitField.getText();
        double lowerTier = Double.valueOf(lowerTierField.getText());
        double upperTier = Double.valueOf(upperTierField.getText());
        double swimmingWeathering = Double.valueOf(swimmingImpactField.getText());
        double cyclingWeathering = Double.valueOf(cyclingImpactField.getText());
        double pedestrianismWeathering = Double.valueOf(pedestrianismImpactField.getText());
        
        try {
            WeatherConditionsDAO.insertWeatherCondition(new UnitMeasure(measurementUnit), upperTier, lowerTier, swimmingWeathering,
                    								 	 cyclingWeathering, pedestrianismWeathering, description);
            showMessage("", "Weather condition inserted.", JOptionPane.INFORMATION_MESSAGE);
            clearFields();
            loadTable();
        } catch (SQLException ex) {
            showMessage("Caution", "Error while inserting weather condition: " + ex.getMessage(), JOptionPane.ERROR_MESSAGE);
        }		
	}

	private void updateWeatherCondition() {
			
		int id = (int)tableModel.getValueAt(table.getSelectedRow(), 7);
		String description = descriptionField.getText();
	    String measurementUnit = measurementUnitField.getText();
	    double lowerTier = Double.parseDouble(lowerTierField.getText());
	    double upperTier = Double.valueOf(upperTierField.getText());
	    double swimmingWeathering = Double.valueOf(swimmingImpactField.getText());
	    double cyclingWeathering = Double.valueOf(cyclingImpactField.getText());
	    double pedestrianismWeathering = Double.valueOf(pedestrianismImpactField.getText());
		
	    try {
	    	WeatherConditionsDAO.updateCondition(id, description, measurementUnit, lowerTier, upperTier, swimmingWeathering, cyclingWeathering, pedestrianismWeathering);
            showMessage("", "Weather condition updated.", JOptionPane.INFORMATION_MESSAGE);
            clearFields();
	        loadTable();
	    } catch(SQLException e) {
            showMessage("Caution", "Error while updating weather condition: " + e.getMessage(), JOptionPane.ERROR_MESSAGE);
	    }
	}
	
	private void showMessage(String title, String message, int messageType) {
        JOptionPane.showMessageDialog(null, message, title, messageType);
    }
	
	private boolean validateFields() {
        if (descriptionField.getText().isBlank()) {
            showMessage("Caution", "The description is obligatory.", JOptionPane.ERROR_MESSAGE);
        	descriptionField.requestFocus();
            return false;
        }

        if (measurementUnitField.getText().isBlank()) {
            showMessage("Caution", "Measurement unit is obligatory.", JOptionPane.ERROR_MESSAGE);
            measurementUnitField.requestFocus();
            return false;
        }

        try {
            double lowerTier = Double.valueOf(lowerTierField.getText());
            double upperTier = Double.valueOf(upperTierField.getText());

            if (lowerTier >= upperTier) {
                showMessage("Caution", "The lower tier has to be lower than the upper tier.", JOptionPane.ERROR_MESSAGE);
                lowerTierField.requestFocus();
                return false;
            }

            double swimmingWeathering = Double.valueOf(swimmingImpactField.getText());
            if (swimmingWeathering < -30 || swimmingWeathering > 30) {
                showMessage("Caution", "The weather impact in the swimming stage of the race has to be between -30 and 30.", JOptionPane.ERROR_MESSAGE);
                swimmingImpactField.requestFocus();
                return false;
            }

            double cyclingWeathering = Double.valueOf(cyclingImpactField.getText());
            if (cyclingWeathering < -30 || cyclingWeathering > 30) {
                showMessage("Caution", "The weather impact in the cycling stage of the race has to be between -30 and 30.", JOptionPane.ERROR_MESSAGE);
                cyclingImpactField.requestFocus();
                return false;
            }

            double pedestrianismWeathering = Double.valueOf(pedestrianismImpactField.getText());
            if (pedestrianismWeathering < -30 || pedestrianismWeathering > 30) {
                showMessage("Caution", "The weather impact in the pedestrianism stage of the race has to be between -30 and 30.", JOptionPane.ERROR_MESSAGE);
                pedestrianismImpactField.requestFocus();
                return false;
            }

        } catch (NumberFormatException e) {
            showMessage("Caution", "Please ensure all numeric fields contain valid numbers.", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }
	
	private void clearFields() {
		descriptionField.setText("");
		measurementUnitField.setText("");
		upperTierField.setText("");
		lowerTierField.setText("");
		swimmingImpactField.setText("");
		cyclingImpactField.setText("");
		pedestrianismImpactField.setText("");
        table.clearSelection();
	}
	
	private void loadTable() {		
		try{
			tableModel.setRowCount(0);
			List<ClimateCondition> conditions = WeatherConditionsDAO.getAllWeatherConditions();
			for(ClimateCondition c : conditions) {
				Object[] row = {c.getDescription(), c.getUnitmeasure().getDescription(), c.getLowerMarck(), c.getUpperMark(), c.getSwimmingWear(), c.getCyclingWear(), c.getRunningWear(), c.getWearId()};
				tableModel.addRow(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void initComponents() { // Initialize labels and textFields
		
		JLabel lblNewJgoodiesTitle = DefaultComponentFactory.getInstance().createTitle("Weather Conditions");
		lblNewJgoodiesTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewJgoodiesTitle.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblNewJgoodiesTitle.setBounds(10, 10, 1066, 47);
		contentPane.add(lblNewJgoodiesTitle);
		
		JLabel label = new JLabel("Description:");
		label.setFont(new Font("Tahoma", Font.PLAIN, 13));
		label.setBounds(23, 85, 152, 33);
		getContentPane().add(label);
		descriptionField = new JTextField();
		descriptionField.setBounds(185, 88, 129, 26);
		getContentPane().add(descriptionField);

		JLabel label_1 = new JLabel("Measurement Unit:");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		label_1.setBounds(23, 139, 152, 33);
		getContentPane().add(label_1);
		measurementUnitField = new JTextField();
		measurementUnitField.setBounds(185, 142, 129, 26);
		getContentPane().add(measurementUnitField);

		JLabel label_2 = new JLabel("Lower Tier (Min Value):");
		label_2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		label_2.setBounds(23, 189, 152, 33);
		getContentPane().add(label_2);
		lowerTierField = new JTextField();
		lowerTierField.setBounds(185, 192, 129, 26);
		getContentPane().add(lowerTierField);

		JLabel label_3 = new JLabel("Upper Tier (Max Value):");
		label_3.setFont(new Font("Tahoma", Font.PLAIN, 13));
		label_3.setBounds(23, 243, 152, 26);
		getContentPane().add(label_3);
		upperTierField = new JTextField();
		upperTierField.setBounds(185, 243, 129, 26);
		getContentPane().add(upperTierField);

		JLabel label_4 = new JLabel("Swimming Impact:");
		label_4.setFont(new Font("Tahoma", Font.PLAIN, 13));
		label_4.setBounds(23, 288, 152, 33);
		getContentPane().add(label_4);
		swimmingImpactField = new JTextField();
		swimmingImpactField.setBounds(185, 291, 129, 26);
		getContentPane().add(swimmingImpactField);

		JLabel label_5 = new JLabel("Cycling Impact:");
		label_5.setFont(new Font("Tahoma", Font.PLAIN, 13));
		label_5.setBounds(23, 345, 152, 26);
		getContentPane().add(label_5);
		cyclingImpactField = new JTextField();
		cyclingImpactField.setBounds(185, 347, 129, 26);
		getContentPane().add(cyclingImpactField);

		JLabel label_6 = new JLabel("Pedestrianism Impact:");
		label_6.setFont(new Font("Tahoma", Font.PLAIN, 13));
		label_6.setBounds(23, 393, 152, 26);
		getContentPane().add(label_6);
		pedestrianismImpactField = new JTextField();
		pedestrianismImpactField.setBounds(185, 395, 129, 25);
		getContentPane().add(pedestrianismImpactField);
	}
}
