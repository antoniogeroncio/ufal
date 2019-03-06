package br.ufal.ic.ppgi.tf.conectoremprestimolimite;

import br.ufal.aracomp.cosmos.emprestimo.spec.dt.UsuarioDT;
import br.ufal.aracomp.cosmos.emprestimo.spec.req.ILimiteReq;
import br.ufal.aracomp.cosmos.limite.spec.dt.ClienteDT;
import br.ufal.aracomp.cosmos.limite.spec.prov.ILimiteOps;

public class ConectorEmprestimoLimite implements ILimiteReq{
	private ILimiteOps limite;
	
	public ConectorEmprestimoLimite(ILimiteOps limite) {
		this.limite = limite;
	}
	
	@Override
	public double estimarLimite(UsuarioDT usuario) {
		ClienteDT cliente = new ClienteDT();
		cliente.salario = Double.parseDouble(usuario.rendimentos);
		return this.limite.calcularLimite(cliente);
	}

}
