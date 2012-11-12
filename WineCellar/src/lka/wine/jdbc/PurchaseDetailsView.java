package lka.wine.jdbc;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import com.google.common.base.Strings;

import lka.wine.dao.PurchaseDetail;

public class PurchaseDetailsView extends AbstractData<PurchaseDetail> {

	private final static String tableName = "vwPurchaseDetails";
	private final static List<String> columnNames = Arrays.asList(
			"PurchaseID", "WineID", "PurchaseDate", "Price", "PricePer", "QtyPurchased",
			"PriceNotes", "BottlesOnHand", "InvLocation", "LocationName", "LocationCity",
			"LocationState", "LocationType");

	@Override
	public PurchaseDetail getObject(ResultSet rs) throws SQLException {
		PurchaseDetail purchaseDetail = new PurchaseDetail();	
		purchaseDetail.setPurchaseId(rs.getInt("PurchaseID"));
		purchaseDetail.setWineId(rs.getInt("WineID"));
		purchaseDetail.setPurchaseDate(rs.getDate("PurchaseDate"));
		purchaseDetail.setPrice(rs.getBigDecimal("Price"));
		purchaseDetail.setPricePer(rs.getString("PricePer"));
		purchaseDetail.setQtyPurchased(rs.getInt("QtyPurchased"));
		purchaseDetail.setPriceNotes(Strings.nullToEmpty(rs.getString("PriceNotes")));
		purchaseDetail.setQtyOnHand(rs.getInt("BottlesOnHand"));
		purchaseDetail.setInvLocation(Strings.nullToEmpty(rs.getString("InvLocation")));
		purchaseDetail.setLocationName(Strings.nullToEmpty(rs.getString("LocationName")));
		purchaseDetail.setLocationCity(Strings.nullToEmpty(rs.getString("LocationCity")));
		purchaseDetail.setLocationState(Strings.nullToEmpty(rs.getString("LocationState")));
		purchaseDetail.setLocationType(Strings.nullToEmpty(rs.getString("LocationType")));
		return purchaseDetail;
	}
	
	public List<PurchaseDetail> select(int wineId) throws Exception {
		String sql = getSelectSql() + " WHERE WineID = ?";
		Connection cn = null;
		PreparedStatement pstmt = null;

		try {
			cn = DriverManager.getConnection();
			pstmt = cn.prepareStatement(sql);
			pstmt.setInt(1, wineId);
			pstmt.execute();

			return getObjects(pstmt.getResultSet());
		} finally {
			JdbcCloser.close(cn, pstmt);
		}
	}

	@Override
	public String getTableName() {
		return tableName;
	}

	@Override
	public String getIdColumnName() {
		return columnNames.get(0);
	}

	@Override
	public List<String> getColumnNames() {
		return columnNames;
	}

	@Override
	public int setParameters(PreparedStatement pstmt, PurchaseDetail obj)
			throws SQLException {
		int index = 1;
		pstmt.setInt(index++, obj.getWineId());		
		pstmt.setDate(index++, new java.sql.Date(obj.getPurchaseDate().getTime()));		
		pstmt.setBigDecimal(index++, obj.getPrice());		
		pstmt.setString(index++, obj.getPricePer());		
		pstmt.setInt(index++, obj.getQtyPurchased());		
		pstmt.setString(index++, obj.getPriceNotes());		
		pstmt.setInt(index++, obj.getQtyOnHand());		
		pstmt.setString(index++, obj.getInvLocation());		
		pstmt.setString(index++, obj.getLocationName());		
		pstmt.setString(index++, obj.getLocationCity());		
		pstmt.setString(index++, obj.getLocationState());		
		pstmt.setString(index++, obj.getLocationType());
		return index;
	}

}