package lka.wine.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Strings;

import lka.wine.dao.Brand;
import lka.wine.dao.Varietal;
import lka.wine.dao.Vineyard;
import lka.wine.dao.Wine;

public class WinesTable extends AbstractData<Wine> {

	private final static String tableName = "Wines";
	private final static List<String> columnNames = Arrays.asList(
			"WineID", "VineyardID", "BrandID", "VarietalID", "RegionID",
			"VintageYear", "WineDescription", "ListPrice", "InventoryNotes");
	
	// The following maps will be initialize prior to 
	// processing the select rows.
	private Map<Integer, Brand> brandsMap;
	private Map<Integer, lka.wine.dao.Region> regionsMap;
	private Map<Integer, Varietal> varietalsMap;
	private Map<Integer, Vineyard> vineyardsMap;
	
	@Override
	protected void preGetObjects() throws Exception {
		// Refresh the maps that are used to lookup related
		// objects based on the ids.
		List<Brand> brands = new BrandsTable().select();
		brandsMap = new HashMap<Integer, Brand>();
		for(Brand brand: brands) {
			brandsMap.put(brand.getBrandId(), brand);
		}
		
		List<lka.wine.dao.Region> regions = new RegionsTable().select();
		regionsMap = new HashMap<Integer, lka.wine.dao.Region>();
		for(lka.wine.dao.Region region: regions) {
			regionsMap.put(region.getRegionId(), region);
		}

		List<Varietal> varietals = new VarietalsTable().select();
		varietalsMap = new HashMap<Integer, Varietal>();
		for(Varietal varietal: varietals) {
			varietalsMap.put(varietal.getVarietalId(), varietal);
		}

		List<Vineyard> vineyards = new VineyardsTable().select();
		vineyardsMap = new HashMap<Integer, Vineyard>();
		for(Vineyard vineyard: vineyards) {
			vineyardsMap.put(vineyard.getVineyardId(), vineyard);
		}
	}

	@Override
	public Wine getObject(ResultSet rs) throws SQLException {
		Wine wine = new Wine();	
		wine.setWineId(rs.getInt("WineID"));

		wine.setVineyardId(rs.getInt("VineyardID"));
		wine.setBrandId(rs.getInt("BrandID"));
		wine.setVarietalId(rs.getInt("VarietalID"));
		wine.setRegionId(rs.getInt("RegionID"));
		
		//wine.setVineyard(vineyardsMap.get(rs.getInt("VineyardID")));
		//wine.setBrand(brandsMap.get(rs.getInt("BrandID")));
		//wine.setVarietal(varietalsMap.get(rs.getInt("VarietalID")));
		//wine.setRegion(regionsMap.get(rs.getInt("RegionID")));
		wine.setVintageYear(rs.getInt("VintageYear"));
		wine.setWineDescription(Strings.nullToEmpty(rs.getString("WineDescription")));
		wine.setListPrice(rs.getBigDecimal("ListPrice"));
		wine.setInventoryNotes(Strings.nullToEmpty(rs.getString("InventoryNotes")));

		return wine;
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
	public int setParameters(PreparedStatement pstmt, Wine obj)
			throws SQLException {
		int index = 1;
		//pstmt.setInt(index++, obj.getVineyard().getVineyardId());
		//pstmt.setInt(index++, obj.getBrand().getBrandId());
		//pstmt.setInt(index++, obj.getVarietal().getVarietalId());
		//pstmt.setInt(index++, obj.getRegion().getRegionId());
		pstmt.setInt(index++, obj.getVineyardId());
		pstmt.setInt(index++, obj.getBrandId());
		pstmt.setInt(index++, obj.getVarietalId());
		pstmt.setInt(index++, obj.getRegionId());
		pstmt.setInt(index++, obj.getVintageYear());
		pstmt.setString(index++, obj.getWineDescription());
		pstmt.setBigDecimal(index++, obj.getListPrice());
		pstmt.setString(index++, obj.getInventoryNotes());
		return index;		
	}

}

