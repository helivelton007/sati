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
import br.com.ambientinformatica.sati.entidade.Marca;
import br.com.ambientinformatica.sati.persistencia.MarcaDao;

@Controller("MarcaControl")
@Scope("conversation")
public class MarcaControl {
	private Marca marca = new Marca();

	@Autowired
	private MarcaDao marcaDao;
	private String filtroGlobal = "";
	private List<Marca> marcas = new ArrayList<Marca>();

	@PostConstruct
	public void init() {
		
		listar(null);
		
	}

	public void confirmar(ActionEvent evt) {
		
		try {
			
			if (evt == null) {
				FacesContext.getCurrentInstance().addMessage(
						"Sati Tecnologia em Informática",
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"É preciso digitar o nome!", null));
			} else {
				marcaDao.alterar(marca);
				listar(evt);
				
				FacesContext.getCurrentInstance().getExternalContext()
						.redirect("marca.jsf");
			}
			
		} catch (Exception e) {
			
			UtilFaces.addMensagemFaces(e);
			
		}
		
	}

	public void listar(ActionEvent evt) {
		
		try {
			
			marcas.clear();
			Integer id = new Integer(filtroGlobal);
			Marca a = marcaDao.consultar(id);
			
			if (a != null) {
				marcas.add(a);
			} else {
				buscarPorNome();
			}
			
		} catch (NumberFormatException e) {
			
			buscarPorNome();
			
		} catch (Exception e) {
			
			UtilFaces.addMensagemFaces(e);
			
		}
	}

	public void preparaAlterar(ActionEvent evt) {
		
		try {
			
			marca = (Marca) evt.getComponent().getAttributes().get("marca");
			marca = marcaDao.consultar(marca.getId());
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("marcaDetalhes.jsf");
			
		} catch (Exception e) {
			
			UtilFaces.addMensagemFaces(e);
			
		}
	}
	
	public void voltarParaPaginaAnteritor(){
		
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("marca.jsf");
			
		} catch (IOException e) {
			
			UtilFaces.addMensagemFaces(e);
			
		}
	}

	public void novaOrdemServico(ActionEvent evt) {
		
		this.marca = new Marca();
		
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("marcaDetalhes.jsf");
		} catch (IOException e) {
			UtilFaces.addMensagemFaces(e);
		}
		
	}

	public void excluir(ActionEvent evt) throws PersistenciaException {
		
		try {
			
			marca = (Marca) evt.getComponent().getAttributes().get("marca");
			marca = marcaDao.consultar(marca.getId());
			marcaDao.excluirPorId(marca.getId());
			listar(evt);
			
			FacesContext.getCurrentInstance().addMessage(
					"Reis Tecnologia em Informática",
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Operação realizada com sucesso", null));
			
		} catch (Exception e) {
			
			UtilFaces.addMensagemFaces(e);
			
		}
	}

	public void voltarHome(ActionEvent evt) {
		
		try {
			
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("index.html");
			
		} catch (IOException e) {
			
			UtilFaces.addMensagemFaces(e);
			
		}
	}

	public void buscarPorNome() {
		
		marcas = marcaDao.listarPorNome(filtroGlobal);
		
		if (marcas.isEmpty()) {
			marcas = marcaDao.listarPorNome(filtroGlobal);
		}
		
	}

	public Marca getMarca() {
		
		return marca;
		
	}

	public void setMarca(Marca marca) {
		
		this.marca = marca;
		
	}

	public List<Marca> getMarcas() {
		
		return marcas;
	
	}

	public String getFiltroGlobal() {
		
		return filtroGlobal;
		
	}

	public void setFiltroGlobal(String filtroGlobal) {
		
		this.filtroGlobal = filtroGlobal;
		
	}

}
