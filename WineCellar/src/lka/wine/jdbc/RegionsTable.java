package lka.wine.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import com.google.common.base.Strings;

import lka.wine.dao.Region;

public class RegionsTable extends AbstractData<Region> {

	private final static String tableName = "Regions";
	private final static List<String> columnNames = Arrays.asList("RegionID", "Region", "SubRegion");

	@Override
	public Region getObject(ResultSet rs) throws SQLException {
		Region region = new Region();	
		region.setRegionId(rs.getInt("RegionID"));
		region.setRegion(Strings.nullToEmpty(rs.getString("Region")));
		region.setSubRegion(Strings.nullToEmpty(rs.getString("SubRegion")));
		return region;
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
	public int setParameters(PreparedStatement pstmt, Region obj)
			throws SQLException {
		int index = 1;
		pstmt.setString(index++, obj.getRegion());
		pstmt.setString(index++, obj.getSubRegion());
		return index;
	}

}