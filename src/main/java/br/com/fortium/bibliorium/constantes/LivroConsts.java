package br.com.fortium.bibliorium.constantes;

import java.io.File;

public interface LivroConsts {

	public static final String FOTOS_LIVROS_FOLDER  = "/fotosLivros/";
	public static final String DEFAULT_PICTURE_NAME = "bookicon-default.png";
	public static final File   DEFAULT_PICTURE = new File(FOTOS_LIVROS_FOLDER + DEFAULT_PICTURE_NAME);
	
}
