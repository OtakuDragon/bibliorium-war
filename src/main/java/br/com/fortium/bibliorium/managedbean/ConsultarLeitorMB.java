package br.com.fortium.bibliorium.managedbean;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import br.com.fortium.bibliorium.enumeration.DialogType;
import br.com.fortium.bibliorium.persistence.entity.Usuario;
import br.com.fortium.bibliorium.service.UsuarioService;

@Named
@RequestScoped
public class ConsultarLeitorMB extends AbstractManagedBean<ConsultarLeitorMB> {

	private static final long serialVersionUID = 8656997963954115958L;
	
	private Usuario leitor;
	private String  cpf;
	
	@EJB
	private UsuarioService usuarioService;
	
	@Override
	protected void init() {

	}
	
	public void pesquisar(){
		if(cpf == null || !cpf.matches("^\\d{11}")){
			getDialogUtil().showDialog(DialogType.ERROR, "CPF inválido, 11 números.");
		}else{
			leitor = usuarioService.buscar(cpf);
		}
	}

	public Usuario getLeitor() {
		return leitor;
	}

	public void setLeitor(Usuario leitor) {
		this.leitor = leitor;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

}
