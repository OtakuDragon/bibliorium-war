package br.com.fortium.bibliorium.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import br.com.fortium.bibliorium.print.Printable;
import br.com.fortium.bibliorium.util.exception.PrintableException;

public class OutputPrintableUtil {

	public void download(HttpServletResponse response, String printableName, Printable... printables) throws PrintableException{
		StringBuilder fileContent = new StringBuilder();
		String printableFileName = formatPrintableFileName(printableName);
		for (Printable printable : printables) {
			fileContent.append(generateFileContent(printable.getPrintableInfo()));
			fileContent.append(Printable.DIVISION);
			fileContent.append(Printable.LINE_BREAK);
		}
		downloadAsText(printableFileName, fileContent.toString(), response);
	}
	
	private String formatPrintableFileName(String printableName){
		StringBuilder retorno = new StringBuilder();
		
		retorno.append(printableName);
		retorno.append(Printable.SEPARATOR);
		retorno.append(DataUtil.getDataS(new Date(), "dd_MM_yyyy_HH-mm-ss"));
		retorno.append(Printable.FILE_EXTENSION);
		
		return retorno.toString();
	}
	
	private void downloadAsText(String printableFileName, String fileContent, HttpServletResponse response) throws PrintableException{
		response.reset();
		response.setHeader("Content-Type", "text/plain");
		 response.setHeader("Content-Disposition", "attachment;filename="+printableFileName);
		 ByteArrayInputStream bis = new ByteArrayInputStream(fileContent.getBytes());
		try{
			OutputStream responseOutputStream = response.getOutputStream();
			
			byte[] bytesBuffer = new byte[256];
			int bytesRead;
			while ((bytesRead = bis.read(bytesBuffer)) > 0) {
				responseOutputStream.write(bytesBuffer, 0, bytesRead);
			}
	
			responseOutputStream.flush();
		}catch(IOException e){
			throw new PrintableException("Falha processar download.", e);
		}
	}
	
	private String generateFileContent(Map<String,String> printableInfo){
		StringBuilder fileContent = new StringBuilder();
		
		for (String key : printableInfo.keySet()) {
			if(printableInfo.get(key) != null){
				fileContent.append(key);
				fileContent.append(Printable.RELATION);
				fileContent.append(printableInfo.get(key));
				fileContent.append(Printable.LINE_BREAK);
			}
		}
		
		return fileContent.toString();
	}
}
