package br.com.ambientinformatica.sati.controle;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.ambientinformatica.ambientjsf.util.UtilFaces;
import br.com.ambientinformatica.jpa.exception.PersistenciaException;
import br.com.ambientinformatica.sati.entidade.Modelo;
import br.com.ambientinformatica.sati.persistencia.ModeloDao;

@Controller("ModeloControl")
@Scope("conversation")
public class ModeloControl {
	private Modelo modelo = new Modelo();

	@Autowired
	private ModeloDao modeloDao;
	private String filtroGlobal = "";
	private List<Modelo> modelos = new ArrayList<Modelo>();

	@PostConstruct
	public void init() {
		listar(null);
	}

	public void confirmar(ActionEvent evt) {
		try {
			modeloDao.alterar(modelo);
			listar(evt);
			modelo = new Modelo();
			FacesContext.getCurrentInstance().getExternalContext()
			.redirect("modelo.jsf");
			
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	public void listar(ActionEvent evt) {
		try {
			modelos.clear();
			Integer id = new Integer(filtroGlobal);
			Modelo a = modeloDao.consultar(id);
			if (a != null) {
				modelos.add(a);
			} else {
				filtrarPorNome();
			}
		} catch (NumberFormatException e) {
			filtrarPorNome();
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}
	public void preparaAlterar(ActionEvent evt) {
		try {
			modelo = (Modelo) evt.getComponent().getAttributes().get("modelo");
			modelo = modeloDao.consultar(modelo.getId());
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("modeloDetalhes.jsf");
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}
	public void preparaIncluir(ActionEvent evt) {
		this.modelo = new Modelo();
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("modeloDetalhes.jsf");
		} catch (IOException e) {
			UtilFaces.addMensagemFaces(e);
		}
	}
	public void excluir(ActionEvent evt) throws PersistenciaException {
		try {
			modelo = (Modelo) evt.getComponent().getAttributes().get("modelo");
			modelo = modeloDao.consultar(modelo.getId());
			modeloDao.excluirPorId(modelo.getId());
			listar(evt);
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
		modelos = modeloDao.listarPorNome(filtroGlobal);
		if (modelos.isEmpty()) {
			modelos = modeloDao.listarPorNome(filtroGlobal);
		}
	}

	public Modelo getModelo() {
		return modelo;
	}

	public void setModelo(Modelo modelo) {
		this.modelo = modelo;
	}

	public List<Modelo> getModelos() {
		return modelos;

	}

	public String getFiltroGlobal() {
		return filtroGlobal;
	}

	public void setFiltroGlobal(String filtroGlobal) {
		this.filtroGlobal = filtroGlobal;
	}
	

}
