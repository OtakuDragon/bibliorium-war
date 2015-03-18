package br.com.fortium.bibliorium.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

import br.com.fortium.bibliorium.print.Printable;
import br.com.fortium.bibliorium.util.exception.PrintableException;

public class OutputPrintableUtil {

	public void output(Printable... printables) throws PrintableException{
		String outputDirectory = Printable.OUTPUT_FOLDER;
		File printableFile = null;
		String fileContent = null;
		Integer counter = 1;
		for (Printable printable : printables) {
			String printableFileName = formatPrintableFileName(outputDirectory, printable, counter++);
			printableFile = new File(printableFileName);
			fileContent = generateFileContent(printable.getPrintableInfo());
			writeToFile(printableFile, fileContent);
		}
	}
	
	private String formatPrintableFileName(String outputDirectory, Printable printable, Integer counter){
		StringBuilder retorno = new StringBuilder();
		
		retorno.append(outputDirectory);
		retorno.append(printable.getName());
		retorno.append(Printable.SEPARATOR);
		retorno.append(counter);
		retorno.append(Printable.SEPARATOR);
		retorno.append(DataUtil.getDataS(new Date(), "dd_MM_yyyy_HH-mm-ss"));
		retorno.append(Printable.SEPARATOR);
		retorno.append(Printable.FILE_EXTENSION);
		
		return retorno.toString();
	}
	
	private void writeToFile(File printableFile, String fileContent) throws PrintableException{
		FileOutputStream fos = null;
		try{
			fos = new FileOutputStream(printableFile);
			fos.write(fileContent.getBytes());
		}catch(IOException e){
			throw new PrintableException("Falha ao escrever arquivos.", e);
		}finally{
			try {
				fos.close();
			} catch (IOException e) {
				throw new PrintableException("Falha ao escrever arquivos.", e);
			}
		}
	}
	
	private String generateFileContent(Map<String,String> printableInfo){
		StringBuilder fileContent = new StringBuilder();
		
		for (String key : printableInfo.keySet()) {
			fileContent.append(key);
			fileContent.append(Printable.RELATION);
			fileContent.append(printableInfo.get(key));
			fileContent.append(Printable.LINE_BREAK);
		}
		
		return fileContent.toString();
	}
}
