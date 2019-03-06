package br.ufal.ic.ppgi.tf.conectoremprestimolimiteavailability;

import br.ufal.aracomp.cosmos.emprestimo.spec.dt.UsuarioDT;
import br.ufal.aracomp.cosmos.emprestimo.spec.req.ILimiteReq;
import br.ufal.aracomp.cosmos.limite.spec.dt.ClienteDT;
import br.ufal.aracomp.cosmos.limite.spec.prov.ILimiteOps;
import br.ufal.aracomp.cosmos.limite2.spec.dt.ClienteDT2;
import br.ufal.aracomp.cosmos.limite2.spec.prov.ILimiteOps2;
import br.ufal.aracomp.cosmos.limite3.spec.dt.ClienteDT3;
import br.ufal.aracomp.cosmos.limite3.spec.prov.ILimiteOps3;
import br.ufal.ic.ppgi.tf.conectoremprestimolimiteavailability.exception.ReliabilityException;

public class ConectorEmprestimoLimiteAvailability implements ILimiteReq{
	
	private ILimiteOps limiteOps;
	private ILimiteOps2 limiteOps2;
	private ILimiteOps3 limiteOps3;
	
	public ConectorEmprestimoLimiteAvailability(ILimiteOps limiteOps, 
			ILimiteOps2 limiteOps2, ILimiteOps3 limiteOps3) {
		this.limiteOps = limiteOps;
		this.limiteOps2 = limiteOps2;
		this.limiteOps3 = limiteOps3;
	}
	
	@Override
	public double estimarLimite(UsuarioDT usuario) {
		Double mediaLimitesEstimados = null;
		Double rendimentos = Double.parseDouble(usuario.rendimentos);
		Double limiteSerasa = calcularLimiteSerasa(rendimentos);
		Double limiteSPC = calcularLimiteSPC(rendimentos);
		Double limiteCSPC = calcularLimiteCSPC(rendimentos);
		if(calcularDiferencaPercentualEntreLimites(limiteSerasa, limiteSPC)<=5) {
			mediaLimitesEstimados = estimarMediaLimitesEstimados(limiteSerasa, limiteSPC);
		}else if(calcularDiferencaPercentualEntreLimites(limiteSerasa, limiteCSPC)<=5) {
			mediaLimitesEstimados = estimarMediaLimitesEstimados(limiteSerasa, limiteCSPC);
		}else if(calcularDiferencaPercentualEntreLimites(limiteSPC, limiteCSPC)<=5) {
			mediaLimitesEstimados = estimarMediaLimitesEstimados(limiteSPC, limiteCSPC);
		}else {
			throw new ReliabilityException();
		}
		return mediaLimitesEstimados;
	}

	

	private double estimarMediaLimitesEstimados(Double limiteSerasa, Double limiteSPC) {
		return (limiteSerasa + limiteSPC) / 2;
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
	
	private Double calcularLimiteCSPC(Double rendimentos) {
		ClienteDT3 cliente = new ClienteDT3();
		cliente.salario = rendimentos;
		Double limiteCSPC = this.limiteOps3.calcularLimite(cliente);
		return limiteCSPC;
	}

}
