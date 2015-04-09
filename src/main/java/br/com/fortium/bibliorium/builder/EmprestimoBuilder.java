package br.com.fortium.bibliorium.builder;

import java.util.Date;

import br.com.fortium.bibliorium.persistence.entity.Copia;
import br.com.fortium.bibliorium.persistence.entity.Emprestimo;
import br.com.fortium.bibliorium.persistence.entity.Usuario;
import br.com.fortium.bibliorium.persistence.enumeration.TipoEmprestimo;
import br.com.fortium.bibliorium.util.DataUtil;

public class EmprestimoBuilder {

	public static Emprestimo novoEmprestimo(Usuario leitor, Copia copia){
		Emprestimo retorno = new Emprestimo();
		
		retorno.setUsuario(leitor);
		retorno.setCopia(copia);
		retorno.setDataEmprestimo(new Date());
		retorno.setDataDevolucao (DataUtil.calcularDataDevolucao(leitor.getTipo()));
		retorno.setDataFechamento(null);
		retorno.setDataRenovacao(null);
		retorno.setTipo(TipoEmprestimo.EMPRESTIMO);
		retorno.setValorMulta(null);
		
		return retorno;
	}

	public static Emprestimo novaReserva(Usuario leitor, Copia copia) {
		Emprestimo retorno = new Emprestimo();
		
		retorno.setUsuario(leitor);
		retorno.setCopia(copia);
		retorno.setDataEmprestimo(new Date());
		retorno.setDataDevolucao(DataUtil.calcularDataFimReserva());
		retorno.setDataFechamento(null);
		retorno.setDataRenovacao(null);
		retorno.setTipo(TipoEmprestimo.RESERVA);
		retorno.setValorMulta(null);
		
		return retorno;
	}
}
