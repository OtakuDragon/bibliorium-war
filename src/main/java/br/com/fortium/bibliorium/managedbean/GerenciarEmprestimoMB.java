package br.com.fortium.bibliorium.managedbean;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.fortium.bibliorium.persistence.entity.Copia;
import br.com.fortium.bibliorium.service.CopiaService;

@ManagedBean
@ViewScoped
public class GerenciarEmprestimoMB extends AbstractManagedBean<GerenciarEmprestimoMB> {

	private Copia copia;
	private String codCopia;
	
	@EJB
	private CopiaService copiaService;
	
	@Override
	protected void init() {
		
	}
	
	public void buscarCopia(){
		Long idCopia = null;
		
		try{
			idCopia = Long.parseLong(codCopia);
			copia = copiaService.buscar(idCopia);
		}catch(NumberFormatException e){
			copia = null;
		}
		
	}
	
	public Copia getCopia() {
		return copia;
	}

	public void setCopia(Copia copia) {
		this.copia = copia;
	}

	public String getCodCopia() {
		return codCopia;
	}

	public void setCodCopia(String codCopia) {
		this.codCopia = codCopia;
	}



}
