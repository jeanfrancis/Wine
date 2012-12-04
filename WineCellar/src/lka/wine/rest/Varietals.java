package lka.wine.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import lka.wine.dao.*;
import lka.wine.jdbc.*;

@Path("/WineCellar/varietals")
public class Varietals extends AbstractRest{

	@Override
	@GET
	@Produces("application/json")
	public String getAll() {
		try {
			List<Varietal> varietals = new VarietalsTable().select();
			return gson.toJson(varietals);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Currently unsupported
	 */
	@Override
	@GET
	@Path("{id}")
	@Produces("application/json")
	public String get(@PathParam("id") int id) {
		try {
			Object varietals = new VarietalsTable().select(id);
			return gson.toJson(varietals);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 *Update: as called for by backbone.js 
	 *Currently unsupported
	 */
	@Override
	@PUT
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("text/plain")
	public void put(String data) {
		Varietal varietal = gson.fromJson(data, Varietal.class);
		try {
			new VarietalsTable().update(varietal);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 *Create: as called for by backbone.js 
	 *Currently unsupported
	 */
	@Override
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("text/plain")
	public String post(String data) {		
		try {
			Varietal varietal = gson.fromJson(data, Varietal.class);
			id = new VarietalsTable().insert(varietal);
			varietal.setVarietalId(id);
			return gson.toJson(varietal);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "error: " + id;
	}

	@Override
	@DELETE
	@Path("{id}")
	public void delete(@PathParam("id") int id) {
		try {
			new VarietalsTable().delete(id);
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
	}

}
