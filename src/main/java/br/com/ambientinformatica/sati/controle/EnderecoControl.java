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
import br.com.ambientinformatica.sati.entidade.Endereco;
import br.com.ambientinformatica.sati.persistencia.EnderecoDao;
import br.com.ambientinformatica.sati.util.SatiException;

@Controller("EnderecoControl")
@Scope("conversation")
public class EnderecoControl {

	private Endereco endereco = new Endereco();
	
	
	@Autowired
	private EnderecoDao enderecoDao;
	private String filtroGlobal = "";
	private List<Endereco> enderecos = new ArrayList<Endereco>();

   @PostConstruct
   public void init(){
      listar(null);
   }
   
	public void confirmar(ActionEvent evt){
		try {
			enderecoDao.alterar(endereco);
         listar(evt);
         endereco = new Endereco();
         FacesContext.getCurrentInstance().getExternalContext()
			.redirect("endereco.jsf");
		} catch (Exception e) {
		   UtilFaces.addMensagemFaces(e);
		}
	}
		public void listar(ActionEvent evt) {
			try {
				enderecos.clear();
				Integer id = new Integer(filtroGlobal);
				Endereco a = enderecoDao.consultar(id);
				if (a != null) {
					enderecos.add(a);
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
		this.endereco = new Endereco();
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("enderecoDetalhes.jsf");
		} catch (IOException e) {
			UtilFaces.addMensagemFaces(e);
		}
	}
	public void preparaAlterar(ActionEvent evt) {
		try {
			endereco = (Endereco) evt.getComponent().getAttributes().get("endereco");
			endereco = enderecoDao.consultar(endereco.getId());
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("enderecoDetalhes.jsf");
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}
	public void excluir(ActionEvent evt) throws PersistenciaException {
		try {
			endereco = (Endereco) evt.getComponent().getAttributes().get("endereco");
			endereco = enderecoDao.consultar(endereco.getId());
			enderecoDao.excluirPorId(endereco.getId());
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
			enderecos = enderecoDao.listarPorCep(filtroGlobal);
		} catch (SatiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PersistenciaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (enderecos.isEmpty()) {
			try {
				enderecos = enderecoDao.listarPorCep(filtroGlobal);
			} catch (SatiException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (PersistenciaException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	
	public List<Endereco> getEnderecos() {
		return enderecos;
	}

	public String getFiltroGlobal() {
		return filtroGlobal;
	}

	public void setFiltroGlobal(String filtroGlobal) {
		this.filtroGlobal = filtroGlobal;
	}


}
