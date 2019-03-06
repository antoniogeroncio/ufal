package br.ufal.ic.ppgi.tf.conectoremprestimolimitereliability;

import br.ufal.aracomp.cosmos.emprestimo.spec.dt.UsuarioDT;
import br.ufal.aracomp.cosmos.emprestimo.spec.req.ILimiteReq;
import br.ufal.aracomp.cosmos.limite.spec.dt.ClienteDT;
import br.ufal.aracomp.cosmos.limite.spec.prov.ILimiteOps;
import br.ufal.aracomp.cosmos.limite2.spec.dt.ClienteDT2;
import br.ufal.aracomp.cosmos.limite2.spec.prov.ILimiteOps2;
import br.ufal.ic.ppgi.tf.conectoremprestimolimitereliability.exception.ReliabilityException;

public class ConectorEmprestimoLimiteReliability implements ILimiteReq{
	
	private ILimiteOps limiteOps;
	private ILimiteOps2 limiteOps2;
	
	public ConectorEmprestimoLimiteReliability(ILimiteOps limiteOps, ILimiteOps2 limiteOps2) {
		this.limiteOps = limiteOps;
		this.limiteOps2 = limiteOps2;
	}
	
	@Override
	public double estimarLimite(UsuarioDT usuario) {
		Double rendimentos = Double.parseDouble(usuario.rendimentos);
		Double limiteSerasa = calcularLimiteSerasa(rendimentos);
		Double limiteSPC = calcularLimiteSPC(rendimentos);
		Double diferencaPercentualEntreLimites = calcularDiferencaPercentualEntreLimites(limiteSerasa, limiteSPC);
		casoDiferencaPercentualSejaSuperiorAoPermitidoLancarExcecaoFalhaConfiabilidadeCalculoLimite(diferencaPercentualEntreLimites);
		return estimarMediaLimitesEstimados(limiteSerasa, limiteSPC);
	}

	private double estimarMediaLimitesEstimados(Double limiteSerasa, Double limiteSPC) {
		return (limiteSerasa + limiteSPC) / 2;
	}

	private void casoDiferencaPercentualSejaSuperiorAoPermitidoLancarExcecaoFalhaConfiabilidadeCalculoLimite(
			Double diferenca) {
		if(diferenca>5.0) {
			throw new ReliabilityException();
		}
	}

	private Double calcularDiferencaPercentualEntreLimites(Double limiteSerasa, Double limiteSPC) {
		Double diferencaPercentualEntreLimites = (limiteSerasa - limiteSPC) / limiteSPC * 100;
		if(diferencaPercentualEntreLimites < 0) {
			diferencaPercentualEntreLimites = (diferencaPercentualEntreLimites) * -1;
		}
		return diferencaPercentualEntreLimites;
	}

	private Double calcularLimiteSPC(Double rendimentos) {
		ClienteDT2 cliente2 = new ClienteDT2();
		cliente2.salario = rendimentos;
		Double limiteSPC = this.limiteOps2.calcularLimite(cliente2);
		return limiteSPC;
	}

	private Double calcularLimiteSerasa(Double rendimentos) {
		ClienteDT cliente = new ClienteDT();
		cliente.salario = rendimentos;
		Double limiteSerasa = this.limiteOps.calcularLimite(cliente);
		return limiteSerasa;
	}

}
