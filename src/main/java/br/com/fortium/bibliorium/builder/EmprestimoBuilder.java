package br.com.fortium.bibliorium.builder;

import java.util.Date;

import br.com.fortium.bibliorium.persistence.entity.Copia;
import br.com.fortium.bibliorium.persistence.entity.Emprestimo;
import br.com.fortium.bibliorium.persistence.entity.Usuario;
import br.com.fortium.bibliorium.persistence.enumeration.TipoEmprestimo;
import br.com.fortium.bibliorium.util.DataUtil;

public class EmprestimoBuilder {

	public static Emprestimo novoEmprestimo(Usuario usuario, Copia copia){
		Emprestimo retorno = new Emprestimo();

		retorno.setUsuario(usuario);
		retorno.setCopia(copia);
		retorno.setDataEmprestimo(new Date());
		retorno.setDataPrevista(DataUtil.calcularDataDevolucao(usuario.getTipo()));
		retorno.setTipo(TipoEmprestimo.EMPRESTIMO);

		return retorno;
	}

	public static Emprestimo novaReserva(Usuario usuario, Copia copia) {
		Emprestimo retorno = new Emprestimo();
		
		retorno.setUsuario(usuario);
		retorno.setCopia(copia);
		retorno.setDataEmprestimo(new Date());
		retorno.setDataPrevista(DataUtil.calcularDataFimReserva());
		retorno.setTipo(TipoEmprestimo.RESERVA);
		
		return retorno;
	}
}
