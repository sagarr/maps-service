package com.rohankar.mapservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rohankar.mapservice.bean.Poi;
import com.rohankar.mapservice.dao.PoiDao;

/**
 * @author Sagar Rohankar
 */
@Service
public class PoiService {

    @Autowired
    private PoiDao poiDao;

    public String create(final Poi poi) {
        return poiDao.store(poi);
    }

    public Poi getPoiById(final String id) {
        return poiDao.find(id);
    }

    public void deletePoiById(final String id) {
        poiDao.delete(id);
    }
}
