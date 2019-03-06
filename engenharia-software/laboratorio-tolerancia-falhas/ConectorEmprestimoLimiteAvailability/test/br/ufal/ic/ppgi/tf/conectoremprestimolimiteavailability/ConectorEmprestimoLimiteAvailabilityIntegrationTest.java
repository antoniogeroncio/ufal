package br.ufal.ic.ppgi.tf.conectoremprestimolimiteavailability;

import org.junit.Before;
import org.junit.Test;

import br.ufal.ic.ppgi.tf.conectoremprestimolimiteavailability.ConectorEmprestimoLimiteAvailability;
import br.ufal.ic.ppgi.tf.conectoremprestimolimiteavailability.exception.ReliabilityException;

public class ConectorEmprestimoLimiteAvailabilityIntegrationTest {
	
	private ConectorEmprestimoLimiteAvailability conn;
	private br.ufal.aracomp.cosmos.emprestimo.spec.prov.IManager emprestimo;
	private br.ufal.aracomp.cosmos.limite.spec.prov.IManager limite;
	private br.ufal.aracomp.cosmos.limite.spec.prov.ILimiteOps limiteOps;
	private br.ufal.aracomp.cosmos.limite2.spec.prov.IManager limite2;
	private br.ufal.aracomp.cosmos.limite2.spec.prov.ILimiteOps2 limiteOps2;
	private br.ufal.aracomp.cosmos.limite3.spec.prov.IManager limite3;
	private br.ufal.aracomp.cosmos.limite3.spec.prov.ILimiteOps3 limiteOps3;
	private br.ufal.aracomp.cosmos.emprestimo.spec.prov.IEmprestimoOps emprestimoOps;
	private br.ufal.aracomp.cosmos.emprestimo.spec.dt.UsuarioDT usuario;
	
	@Before
	public void inicializacao() {
		limite = br.ufal.aracomp.cosmos.limite.impl.ComponentFactory.createInstance();
		limiteOps = (br.ufal.aracomp.cosmos.limite.spec.prov.ILimiteOps) limite.getProvidedInterface("ILimiteOps");
		
		limite2 = br.ufal.aracomp.cosmos.limite2.impl.ComponentFactory.createInstance();
		limiteOps2 = (br.ufal.aracomp.cosmos.limite2.spec.prov.ILimiteOps2) limite2.getProvidedInterface("ILimiteOps2");
		
		limite3 = br.ufal.aracomp.cosmos.limite3.impl.ComponentFactory.createInstance();
		limiteOps3 = (br.ufal.aracomp.cosmos.limite3.spec.prov.ILimiteOps3) limite3.getProvidedInterface("ILimiteOps3");
		
		
		conn = new ConectorEmprestimoLimiteAvailability(limiteOps, limiteOps2, limiteOps3);
		
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
