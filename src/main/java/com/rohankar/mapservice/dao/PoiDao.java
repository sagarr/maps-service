package com.rohankar.mapservice.dao;

import com.rohankar.mapservice.bean.Poi;

/**
 * @author Sagar Rohankar
 */
public interface PoiDao {

    String store(Poi poi);

    Poi find(String id);

    void delete(String id);

}
