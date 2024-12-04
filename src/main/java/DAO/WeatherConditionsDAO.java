package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import Model.ClimateCondition.*;

public class WeatherConditionsDAO {
	
	public static List<ClimateCondition> getAllWeatherConditions() throws SQLException {
	    
		List<ClimateCondition> weatherConditions = new ArrayList<>();
		
		String queryclimatecond = "SELECT * FROM weatherconditions "
	        						+ "ORDER BY id ASC ";

	        try(Connection connection = DBConnection.getConnection();
	        	Statement statement = connection.createStatement();
	        	ResultSet rs = statement.executeQuery(queryclimatecond)){
	        	 
	        	
	            while (rs.next()) {
	                int id = rs.getInt("id");
	                String description = rs.getString("description");
	                String measureUnit = rs.getString("measureunit");
	                double lowerMark   = rs.getDouble("lowermark");
	                double upperMark   = rs.getDouble("uppermark");
	                double swimmingWear= rs.getDouble("wearswimming");
	                double cyclingWear = rs.getDouble("wearcycling");
	                double runningWear = rs.getDouble("wearrunning");
	                
	                UnitMeasure unitMeasure = new UnitMeasure(measureUnit);

	                ClimateCondition condition = new ClimateCondition(id, description, unitMeasure, lowerMark, upperMark,swimmingWear,cyclingWear,runningWear);
	                weatherConditions.add(condition);
	            }
	        }catch (SQLException e) {
	            e.printStackTrace(); 
	       }
	       
	        return weatherConditions;
	}
	
	public static void insertWeatherCondition(UnitMeasure unitMeasure, double upperTier, double lowerTier, double swimmingWeathering, double cyclingWeathering, double pedestrianismWeathering, String description) throws SQLException{
		
		String sql = "INSERT INTO weatherconditions (description, measureunit, lowermark, uppermark, wearswimming, wearcycling, wearrunning)"
					+ "VALUES(?, ?, ?, ?, ?, ? ,?)";
		try(Connection connection = DBConnection.getConnection();
	        PreparedStatement ps = connection.prepareStatement(sql)){	
			
			ps.setString(1, description);
			ps.setString(2, unitMeasure.getDescription());
			ps.setDouble(3, lowerTier);
			ps.setDouble(4, upperTier);
			ps.setDouble(5, swimmingWeathering);
			ps.setDouble(6, cyclingWeathering);
			ps.setDouble(7, pedestrianismWeathering);
			ps.executeUpdate();
		}
	}

	public static void deleteWeatherCondition(int id) throws SQLException {
		
		String sql = "DELETE FROM weatherconditions WHERE id = ?";
		
		try(Connection conn = DBConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql)){
			
			ps.setInt(1, id);
			ps.executeUpdate();
			
		}
	}

	public static void updateCondition(int id, String description, String measurementUnit, double lowerTier,double upperTier,
									   double swimmingWeathering, double cyclingWeathering, double pedestrianismWeathering) throws SQLException {
		
		String sql = "UPDATE weatherconditions "
				   + "SET description = ?, measureunit = ?, lowermark = ?, uppermark = ?, wearswimming = ?, wearcycling = ?, wearrunning = ? "
				   + "WHERE id = ? ";
		try(Connection conn = DBConnection.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)){
				
			ps.setString(1, description);
			ps.setString(2, measurementUnit);
			ps.setDouble(3, lowerTier);
			ps.setDouble(4, upperTier);
			ps.setDouble(5, swimmingWeathering);
			ps.setDouble(6, cyclingWeathering);
			ps.setDouble(7, pedestrianismWeathering);
			ps.setInt(8, id);
			ps.executeUpdate();
				
		}
	}

}

