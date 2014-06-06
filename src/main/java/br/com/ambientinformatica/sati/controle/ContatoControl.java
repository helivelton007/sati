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
import br.com.ambientinformatica.sati.entidade.Contato;
import br.com.ambientinformatica.sati.persistencia.ContatoDao;

@Controller("ContatoControl")
@Scope("conversation")
public class ContatoControl {

	private Contato contato = new Contato();

	@Autowired
	private ContatoDao contatoDao;
	private String filtroGlobal = "";
	private List<Contato> contatos = new ArrayList<Contato>();

	@PostConstruct
	public void init() {
		listar(null);
	}

	public void confirmar(ActionEvent evt) {
		try {

			contatoDao.alterar(contato);
			listar(evt);
			contato = new Contato();
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("contato.jsf");

		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	public void listar(ActionEvent evt) {
		try {
			contatos.clear();
			Integer id = new Integer(filtroGlobal);
			Contato a = contatoDao.consultar(id);
			if (a != null) {
				contatos.add(a);
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
			contato = (Contato) evt.getComponent().getAttributes()
					.get("contato");
			contato = contatoDao.consultar(contato.getId());
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("contatoDetalhes.jsf");
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	public void preparaIncluir(ActionEvent evt) {
		this.contato = new Contato();
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("contatoDetalhes.jsf");
		} catch (IOException e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	public void excluir(ActionEvent evt) throws PersistenciaException {
		try {
			contato = (Contato) evt.getComponent().getAttributes()
					.get("contato");
			contato = contatoDao.consultar(contato.getId());
			contatoDao.excluirPorId(contato.getId());
			listar(evt);
			FacesContext.getCurrentInstance().addMessage(
					"Reis Tecnologia em Informática",
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Operação realizada com sucesso", null));
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	// Metodo retorna para pagina index.html(Pagina Principal)
	public void voltarHome(ActionEvent evt) {
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("index.html");
		} catch (IOException e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	// Metodo faz a pesquisa do contato pelo nome
	public void filtrarPorNome() {
		contatos = contatoDao.listarPorNome(filtroGlobal);
		if (contatos.isEmpty()) {
			contatos = contatoDao.listarPorNome(filtroGlobal);
		}
	}

	public Contato getContato() {
		return contato;
	}

	public void setContato(Contato contato) {
		this.contato = contato;
	}

	public List<Contato> getContatos() {
		return contatos;
	}

	public String getFiltroGlobal() {
		return filtroGlobal;
	}

	public void setFiltroGlobal(String filtroGlobal) {
		this.filtroGlobal = filtroGlobal;
	}

}
