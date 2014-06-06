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
import br.com.ambientinformatica.sati.entidade.Tecnico;
import br.com.ambientinformatica.sati.persistencia.TecnicoDao;

@Controller("TecnicoControl")
@Scope("conversation")
public class TecnicoControl {

	private Tecnico tecnico = new Tecnico();
	
	@Autowired
	private TecnicoDao tecnicoDao;
	private String filtroGlobal = "";
	private List<Tecnico> tecnicos = new ArrayList<Tecnico>();
	

   @PostConstruct
   public void init(){
      listar(null);
   }
   
	public void confirmar(ActionEvent evt){
		try {
			tecnicoDao.alterar(tecnico);
         listar(evt);
         tecnico = new Tecnico();
         FacesContext.getCurrentInstance().getExternalContext()
			.redirect("tecnico.jsf");
		} catch (Exception e) {
		   UtilFaces.addMensagemFaces(e);
		}
	}
		public void listar(ActionEvent evt) {
			try {
				tecnicos.clear();
				Integer id = new Integer(filtroGlobal);
				Tecnico a = tecnicoDao.consultar(id);
				if (a != null) {
					tecnicos.add(a);
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
		this.tecnico = new Tecnico();
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("tecnicoDetalhes.jsf");
		} catch (IOException e) {
			UtilFaces.addMensagemFaces(e);
		}
	}
	public void preparaAlterar(ActionEvent evt) {
		try {
			tecnico = (Tecnico) evt.getComponent().getAttributes().get("tecnico");
			tecnico = tecnicoDao.consultar(tecnico.getId());
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("tecnicoDetalhes.jsf");
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}
	public void excluir(ActionEvent evt) throws PersistenciaException {
		try {
			tecnico = (Tecnico) evt.getComponent().getAttributes().get("tecnico");
			tecnico = tecnicoDao.consultar(tecnico.getId());
			tecnicoDao.excluirPorId(tecnico.getId());
			listar(evt);
			FacesContext.getCurrentInstance().addMessage(
					"Reis Tecnologia em Informática",
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
		tecnicos = tecnicoDao.listarPorNome(filtroGlobal);
		if (tecnicos.isEmpty()) {
			tecnicos = tecnicoDao.listarPorNome(filtroGlobal);
		}
	}
	
	public Tecnico getTecnico() {
		return tecnico;
	}

	public void setTecnico(Tecnico tecnico) {
		this.tecnico = tecnico;
	}
	
	public List<Tecnico> getTecnicos() {
		return tecnicos;
	}

	public String getFiltroGlobal() {
		return filtroGlobal;
	}

	public void setFiltroGlobal(String filtroGlobal) {
		this.filtroGlobal = filtroGlobal;
	}
	
	

}
