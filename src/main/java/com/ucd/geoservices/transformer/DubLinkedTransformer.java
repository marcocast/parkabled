package com.ucd.geoservices.transformer;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;
import java.util.Optional;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aol.cyclops.streams.Pair;
import com.google.common.collect.Lists;
import com.ucd.geoservices.geo.GeocoderService;
import com.ucd.geoservices.model.Coordinates;
import com.ucd.geoservices.model.Location;

@Component
public class DubLinkedTransformer {

	@Autowired
	private GeocoderService geocoderService;

	public List<Location> transformFromCSVData(String csvData) {
		List<Location> result = Lists.newArrayList();
		Reader in = new StringReader(csvData);
		CSVParser parser = null;
		try {
			parser = new CSVParser(in, CSVFormat.EXCEL);
			parser.getRecords()
					.parallelStream()
					.map(csvElement -> new Location(null, null, "Ireland,Dublin " + csvElement.get(3) + " " + csvElement.get(0) + " "
							+ csvElement.get(1), csvElement.get(2))).forEach(location -> {

						Optional<Pair<Double, Double>> longLat = geocoderService.getLongLat(location.getName().replaceAll(" ", "+"));

						longLat.ifPresent(pair -> {
							result.add(location.withCoordinates(new Coordinates(pair.getV1(), pair.getV2())));
						});

						Throttle();
					});

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (parser != null) {
					parser.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return result;

	}

	private void Throttle() {
		try {
			Thread.sleep(300);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
