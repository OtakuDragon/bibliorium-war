package br.com.fortium.bibliorium.enumeration;

import static br.com.fortium.bibliorium.constantes.DialogConsts.*;

public enum DialogType{
	SUCCESS(SUCCESS_ICON, DEFAULT_SUCCESS_HEADER, DEFAULT_SUCCESS_MESSAGE), WARNING(WARNING_ICON, DEFAULT_WARNING_HEADER, DEFAULT_WARNING_MESSAGE), ERROR(ERROR_ICON, DEFAULT_ERROR_HEADER, DEFAULT_ERROR_MESSAGE);
	
	private String defaultIcon, defaultHeader, defaultMessage;
	
	private DialogType(String defaultIcon, String defaultHeader, String defaultMessage){
		this.defaultIcon    = defaultIcon;
		this.defaultHeader  = defaultHeader;
		this.defaultMessage = defaultMessage;
	}

	public String getDefaultIcon() {
		return defaultIcon;
	}

	public String getDefaultHeader() {
		return defaultHeader;
	}

	public String getDefaultMessage() {
		return defaultMessage;
	}
}