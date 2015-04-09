package br.com.fortium.bibliorium.print;

public class PrintableDataHolder {

	private String printableName;
	private Printable[] printables;
	
	public PrintableDataHolder(String printableName, Printable... printables) {
		setPrintableName(printableName);
		setPrintables(printables);
	}
	
	public String getPrintableName() {
		return printableName;
	}
	public void setPrintableName(String printableName) {
		this.printableName = printableName;
	}
	public Printable[] getPrintables() {
		return printables;
	}
	public void setPrintables(Printable[] printables) {
		this.printables = printables;
	}
}
