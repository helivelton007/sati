package br.com.ambientinformatica.sati.controle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.ambientinformatica.ambientjsf.util.UtilFaces;
import br.com.ambientinformatica.jpa.exception.PersistenciaException;
import br.com.ambientinformatica.sati.entidade.Servico;
import br.com.ambientinformatica.sati.persistencia.ServicoDao;
import br.com.ambientinformatica.sati.util.SatiException;

@Controller("ServicoControl")
@Scope("conversation")
public class ServicoControl {

	private Servico servico = new Servico();
	
	
	@Autowired
	private ServicoDao servicoDao;
	private String filtroGlobal = "";
	private List<Servico> servicos = new ArrayList<Servico>();
	

   @PostConstruct
   public void init(){
      listar(null);
   }
   
	public void confirmar(ActionEvent evt){
		try {
		 servicoDao.alterar(servico);
         listar(evt);
         servico = new Servico();
         FacesContext.getCurrentInstance().getExternalContext()
			.redirect("servico.jsf");
		} catch (Exception e) {
		   UtilFaces.addMensagemFaces(e);
		}
	}
		public void listar(ActionEvent evt) {
			try {
				servicos.clear();
				Integer id = new Integer(filtroGlobal);
				Servico a = servicoDao.consultar(id);
				if (a != null) {
					servicos.add(a);
				} else {
					filtrarPorNome();
				}
			} catch (NumberFormatException e) {
				filtrarPorNome();
			} catch (Exception e) {
				UtilFaces.addMensagemFaces(e);
			}
	}
	public void preparaIncluir(ActionEvent evt) {
		this.servico = new Servico();
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("servicoDetalhes.jsf");
		} catch (IOException e) {
			UtilFaces.addMensagemFaces(e);
		}
	}
	public void preparaAlterar(ActionEvent evt) {
		try {
			servico = (Servico) evt.getComponent().getAttributes().get("servico");
			servico = servicoDao.consultar(servico.getId());
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("servicoDetalhes.jsf");
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}
	public void excluir(ActionEvent evt) throws PersistenciaException {
		try {
			servico = (Servico) evt.getComponent().getAttributes().get("servico");
			servico = servicoDao.consultar(servico.getId());
			servicoDao.excluirPorId(servico.getId());
			listar(evt);
			FacesContext.getCurrentInstance().addMessage(
					"",
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Operação realizada com sucesso", null));
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}
	public void voltarHome(ActionEvent evt){
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("index.html");
		} catch (IOException e) {
			UtilFaces.addMensagemFaces(e);
		}
	}
	
	public void filtrarPorNome() {
		try {
			servicos = servicoDao.listarPorDescricao(filtroGlobal);
		} catch (SatiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PersistenciaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (servicos.isEmpty()) {
			try {
				servicos = servicoDao.listarPorDescricao(filtroGlobal);
			} catch (SatiException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (PersistenciaException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public Servico getCliente() {
		return servico;
	}

	public void setServico(Servico servico) {
		this.servico = servico;
	}
	
	public List<Servico> getServicos() {
		return servicos;
	}

	public String getFiltroGlobal() {
		return filtroGlobal;
	}

	public void setFiltroGlobal(String filtroGlobal) {
		this.filtroGlobal = filtroGlobal;
	}

}
