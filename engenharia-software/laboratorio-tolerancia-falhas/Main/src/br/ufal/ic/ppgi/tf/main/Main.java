package br.ufal.ic.ppgi.tf.main;

import br.ufal.aracomp.cosmos.emprestimo.impl.ComponentFactory;
import br.ufal.aracomp.cosmos.emprestimo.spec.dt.UsuarioDT;
import br.ufal.aracomp.cosmos.emprestimo.spec.prov.IEmprestimoOps;
import br.ufal.aracomp.cosmos.emprestimo.spec.prov.IManager;
import br.ufal.aracomp.cosmos.limite.spec.prov.ILimiteOps;
import br.ufal.ic.ppgi.tf.conectorSimples.ConectorSimples;

public class Main {
	public static void main(String[] args) {
		IManager emprestimo;
		br.ufal.aracomp.cosmos.limite.spec.prov.IManager limite;
		
		emprestimo = ComponentFactory.createInstance();
		limite = br.ufal.aracomp.cosmos.limite.impl.ComponentFactory.createInstance();
		
		ILimiteOps limiteOps = (ILimiteOps)limite.getProvidedInterface("ILimiteOps");
		
		ConectorSimples conn = new ConectorSimples(limiteOps);
		emprestimo.setRequiredInterface("ILimiteReq", conn);
		//EXECUCAO
		IEmprestimoOps emprestimoOps = (IEmprestimoOps)emprestimo.getProvidedInterface("IEmprestimoOps");
		
		UsuarioDT usuario = new UsuarioDT();
		usuario.rendimentos="1002.5";
		System.out.println("VALOR DO EMPRESTIMO: " + emprestimoOps.liberarEmprestimoAutomatico(usuario));
		


	}
}
