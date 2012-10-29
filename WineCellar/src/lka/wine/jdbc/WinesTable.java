package lka.wine.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.ec2.model.Region;

import lka.wine.dao.Brand;
import lka.wine.dao.Varietal;
import lka.wine.dao.Vineyard;
import lka.wine.dao.Wine;

public class WinesTable extends AbstractTable<Wine> {

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
		Wine Wine = new Wine();	
		Wine.setWineId(rs.getInt("WineID"));
		//Wine.setWineName(rs.getString("WineName"));
		return Wine;
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

}

