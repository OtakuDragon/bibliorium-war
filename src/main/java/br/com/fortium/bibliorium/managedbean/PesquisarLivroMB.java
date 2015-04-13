package br.com.fortium.bibliorium.managedbean;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.fortium.bibliorium.builder.EmprestimoBuilder;
import br.com.fortium.bibliorium.enumeration.DialogType;
import br.com.fortium.bibliorium.exception.ValidationException;
import br.com.fortium.bibliorium.persistence.entity.Categoria;
import br.com.fortium.bibliorium.persistence.entity.Emprestimo;
import br.com.fortium.bibliorium.persistence.entity.Livro;
import br.com.fortium.bibliorium.print.ComprovanteEmprestimoPrintable;
import br.com.fortium.bibliorium.print.Printable;
import br.com.fortium.bibliorium.print.PrintableBuilder;
import br.com.fortium.bibliorium.print.PrintableDataHolder;
import br.com.fortium.bibliorium.service.CategoriaService;
import br.com.fortium.bibliorium.service.EmprestimoService;
import br.com.fortium.bibliorium.service.LivroService;

@ManagedBean
@ViewScoped
public class PesquisarLivroMB extends AbstractManagedBean<PesquisarLivroMB> {

	private static final long serialVersionUID = -5949416609941531609L;

	private List<Livro>  livros;
	private Livro livroDetalhe;
	private Livro filtro;
	
	private List<Categoria> categorias;

	@EJB
	private CategoriaService categoriaService;
	@EJB
	private LivroService livroService;
	@EJB
	private EmprestimoService emprestimoService;
	
	@Override
	protected void init() {
		livros = livroService.list();
		filtro = new Livro();
		setCategorias(categoriaService.buscarCategorias());
	}
	
	public void pesquisar(){
		livros = livroService.buscarPorFiltro(filtro);
		filtro = new Livro();
	}
	
	public void reservar(){
		if(livroDetalhe.getCopiasDisponiveis().size() > 0){
			try{
				Emprestimo reserva = EmprestimoBuilder.novaReserva(getUsuarioAutenticado(), livroDetalhe.getCopiasDisponiveis().remove(0));
				
				emprestimoService.efetuarEmprestimo(reserva);
				printComprovante(reserva);
				getDialogUtil().showDialog(DialogType.SUCCESS, "Reserva efetuada com sucesso.");
				
			}catch(ValidationException e){
				getDialogUtil().showDialog(DialogType.ERROR, e.getMessage());
			}
		}else{
			getDialogUtil().showDialog(DialogType.ERROR, "Nenhuma copia disponivel.");
		}
	}
	
	private void printComprovante(Emprestimo emprestimo){
		Printable comprovante = PrintableBuilder.buildComprovanteEmprestimo(emprestimo, PrintableBuilder.TipoComprovante.EMPRESTIMO);
		PrintableDataHolder dataHolder = new PrintableDataHolder(ComprovanteEmprestimoPrintable.NAME, comprovante);
		setPrintable(dataHolder);
	}

	public Livro getFiltro() {
		return filtro;
	}

	public void setFiltro(Livro filtro) {
		this.filtro = filtro;
	}

	public List<Livro> getLivros() {
		return livros;
	}

	public void setLivros(List<Livro> livros) {
		this.livros = livros;
	}

	public Livro getLivroDetalhe() {
		return livroDetalhe;
	}

	public void setLivroDetalhe(Livro livroDetalhe) {
		this.livroDetalhe = livroDetalhe;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}
	
}
