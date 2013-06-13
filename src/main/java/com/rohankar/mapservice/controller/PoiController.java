package com.rohankar.mapservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.rohankar.mapservice.bean.Poi;
import com.rohankar.mapservice.service.PoiService;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

/**
 * @author Sagar Rohankar
 */
@Controller
@RequestMapping("/pois")
public class PoiController {

    private final static Logger LOG = LoggerFactory.getLogger(PoiController.class);

    @Autowired
    private PoiService poiService;

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<Void> create(@RequestBody final Poi poi) {
        LOG.debug("Received create poi request {}", poi);
        final String id = poiService.create(poi);
        return poiCreatedResponse(id);
    }

    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Poi read(@PathVariable final String id) {
        LOG.debug("Received read poi request for poi id '{}'", id);
        final Poi poi = poiService.getPoiById(id);
        return poi;
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable final String id) {
        LOG.debug("Received delete poi request for poi id '{}'", id);
        poiService.deletePoiById(id);
    }

    //
    // private methods
    //
    private ResponseEntity<Void> poiCreatedResponse(final String id) {
        final HttpHeaders headers = new HttpHeaders();
        final String location = fromCurrentRequest().path("/{id}").buildAndExpand(id).toString();
        headers.set("Location", location);
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }
}
