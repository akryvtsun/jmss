package com.jmss.application;

import java.io.IOException;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class LauncherViewModel {

	private String acronym = "name";
	private String version = "0.0";
	private String fullName = "full_name";

	private StringProperty title = new SimpleStringProperty();
	private StringProperty name = new SimpleStringProperty();

	public LauncherViewModel() {
		try {
			JarFile jar = new JarFile("jmss.jar");
			Manifest manifest = jar.getManifest();
			Attributes attributes = manifest.getMainAttributes();
			acronym = attributes.getValue("Acronym");
			version = attributes.getValue("Version");
			fullName = attributes.getValue("Title");
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		title.setValue(String.format("%s v%s", acronym, version));
		name.setValue(String.format("%s\n(c) 2016", fullName));
	}

	public StringProperty titleProperty() {
		return title;
	}

	public StringProperty nameProperty() {
		return name;
	}
}
