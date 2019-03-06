package br.ufal.ic.ppgi.tf.conectoremprestimolimitereliability;

import org.junit.Before;
import org.junit.Test;

import br.ufal.ic.ppgi.tf.conectoremprestimolimitereliability.exception.ReliabilityException;

public class ConectorEmprestimoLimiteReliabilityIntegrationTest {
	
	private ConectorEmprestimoLimiteReliability conn;
	private br.ufal.aracomp.cosmos.emprestimo.spec.prov.IManager emprestimo;
	private br.ufal.aracomp.cosmos.limite.spec.prov.IManager limite;
	private br.ufal.aracomp.cosmos.limite.spec.prov.ILimiteOps limiteOps;
	private br.ufal.aracomp.cosmos.limite2.spec.prov.IManager limite2;
	private br.ufal.aracomp.cosmos.limite2.spec.prov.ILimiteOps2 limiteOps2;
	private br.ufal.aracomp.cosmos.emprestimo.spec.prov.IEmprestimoOps emprestimoOps;
	private br.ufal.aracomp.cosmos.emprestimo.spec.dt.UsuarioDT usuario;
	
	@Before
	public void inicializacao() {
		limite = br.ufal.aracomp.cosmos.limite.impl.ComponentFactory.createInstance();
		limiteOps = (br.ufal.aracomp.cosmos.limite.spec.prov.ILimiteOps) limite.getProvidedInterface("ILimiteOps");
		
		limite2 = br.ufal.aracomp.cosmos.limite2.impl.ComponentFactory.createInstance();
		limiteOps2 = (br.ufal.aracomp.cosmos.limite2.spec.prov.ILimiteOps2) limite2.getProvidedInterface("ILimiteOps2");
		
		conn = new ConectorEmprestimoLimiteReliability(limiteOps, limiteOps2);
		
		emprestimo = br.ufal.aracomp.cosmos.emprestimo.impl.ComponentFactory.createInstance();
		emprestimo.setRequiredInterface("ILimiteReq", conn);
		emprestimoOps = (br.ufal.aracomp.cosmos.emprestimo.spec.prov.IEmprestimoOps) emprestimo.getProvidedInterface("IEmprestimoOps");
	}
	
	@Test(expected=ReliabilityException.class)
	public void dadoQueSejaInformadoRendaMilIhHumEhAhDiferencaPercentualDosLimitesCalculadosSejaSuperiorAoPermitidoEntaoDeveriaLancarExcecao() {
		usuario = new br.ufal.aracomp.cosmos.emprestimo.spec.dt.UsuarioDT();
		usuario.rendimentos = "1001.00";	
		emprestimoOps.liberarEmprestimoAutomatico(usuario);
	}
	
	@Test
	public void dadoQueSejaSetadoInterfaceRequeridaValidaEntaoDeveriaExecutarOperacao() {
		usuario = new br.ufal.aracomp.cosmos.emprestimo.spec.dt.UsuarioDT();
		usuario.rendimentos = "1002.5";
		emprestimoOps.liberarEmprestimoAutomatico(usuario);
	}
	
	

}
