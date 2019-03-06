package br.ufal.ic.ppgi.tf.conectoremprestimolimite;

import org.junit.Before;
import org.junit.Test;

public class ConectorEmprestimoLimiteAcceptanceTest {
	
	private ConectorEmprestimoLimite conn;
	private br.ufal.aracomp.cosmos.emprestimo.spec.prov.IManager emprestimo;
	private br.ufal.aracomp.cosmos.limite.spec.prov.IManager limite;
	private br.ufal.aracomp.cosmos.limite.spec.prov.ILimiteOps limiteOps;
	private br.ufal.aracomp.cosmos.emprestimo.spec.prov.IEmprestimoOps emprestimoOps;
	private br.ufal.aracomp.cosmos.emprestimo.spec.dt.UsuarioDT usuario;
	
	@Before
	public void inicializacao() {
		usuario = new br.ufal.aracomp.cosmos.emprestimo.spec.dt.UsuarioDT();
		usuario.rendimentos = "1002.5";
		emprestimo = br.ufal.aracomp.cosmos.emprestimo.impl.ComponentFactory.createInstance();
	}
	
	@Test
	public void dadoQueSejaSetadoInterfaceRequeridaValidaEntaoDeveriaExecutarOperacao() {
		limite = br.ufal.aracomp.cosmos.limite.impl.ComponentFactory.createInstance();
		limiteOps = (br.ufal.aracomp.cosmos.limite.spec.prov.ILimiteOps) limite.getProvidedInterface("ILimiteOps");
		conn = new ConectorEmprestimoLimite(limiteOps);
		emprestimo.setRequiredInterface("ILimiteReq", conn);
		emprestimoOps = (br.ufal.aracomp.cosmos.emprestimo.spec.prov.IEmprestimoOps) emprestimo.getProvidedInterface("IEmprestimoOps");
		
		emprestimoOps.liberarEmprestimoAutomatico(usuario);
	}

}
