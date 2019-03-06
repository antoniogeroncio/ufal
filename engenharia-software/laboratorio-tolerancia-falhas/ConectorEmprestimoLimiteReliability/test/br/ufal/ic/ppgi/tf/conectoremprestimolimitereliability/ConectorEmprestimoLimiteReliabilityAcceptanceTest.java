package br.ufal.ic.ppgi.tf.conectoremprestimolimitereliability;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import br.ufal.ic.ppgi.tf.conectoremprestimolimitereliability.exception.ReliabilityException;

@RunWith(MockitoJUnitRunner.class)
public class ConectorEmprestimoLimiteReliabilityAcceptanceTest {
	
	private ConectorEmprestimoLimiteReliability conn;
	
	@Mock
	private br.ufal.aracomp.cosmos.limite.spec.prov.ILimiteOps limiteOps;
	
	@Mock
	private br.ufal.aracomp.cosmos.limite2.spec.prov.ILimiteOps2 limiteOps2;
	
	private br.ufal.aracomp.cosmos.emprestimo.spec.dt.UsuarioDT usuario;
	
	@Before
	public void inicializacao() {
		usuario = new br.ufal.aracomp.cosmos.emprestimo.spec.dt.UsuarioDT();
		usuario.rendimentos = "1001.00";
		
		conn = new ConectorEmprestimoLimiteReliability(limiteOps, limiteOps2);
	}
	
	@Test(expected=ReliabilityException.class)
	public void dadoQueSejaObtidoLimiteDivergenteForaDosParametrosAceitaveisEntaoDeveriaRetornarExcecaoConfiabilidade() {
		when(limiteOps.calcularLimite(any())).thenReturn(1000.00);
		when(limiteOps2.calcularLimite(any())).thenReturn(100.00);
		conn.estimarLimite(usuario);
	}
	
	@Test
	public void dadoQueSejaObtidoLimitesDentroDosParametrosAceitaveisEntaoDeveriaRetornarMediaDosLimitesRetornados() {
		when(limiteOps.calcularLimite(any())).thenReturn(95.00);
		when(limiteOps2.calcularLimite(any())).thenReturn(100.00);
		Double limiteRetornado = conn.estimarLimite(usuario);
		Double limiteEsperado = 97.50;
		assertEquals(limiteEsperado, limiteRetornado);
	}
	
	

}
