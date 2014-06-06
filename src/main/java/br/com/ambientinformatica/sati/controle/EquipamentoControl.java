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
import br.com.ambientinformatica.sati.entidade.Equipamento;
import br.com.ambientinformatica.sati.persistencia.EquipamentoDao;

@Controller("EquipamentoControl")
@Scope("conversation")
public class EquipamentoControl {
	private Equipamento equipamento = new Equipamento();

	@Autowired
	private EquipamentoDao equipamentoDao;
	private String filtroGlobal = "";
	private List<Equipamento> equipamentos = new ArrayList<Equipamento>();

	@PostConstruct
	public void init() {
		listar(null);
	}

	public void confirmar(ActionEvent evt) {
		try {
			equipamentoDao.alterar(equipamento);
			listar(evt);
			equipamento = new Equipamento();
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("equipamento.jsf");

		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	public void listar(ActionEvent evt) {
		try {
			equipamentos.clear();
			Integer id = new Integer(filtroGlobal);
			Equipamento a = equipamentoDao.consultar(id);
			if (a != null) {
				equipamentos.add(a);
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
			equipamento = (Equipamento) evt.getComponent().getAttributes()
					.get("equipamento");
			equipamento = equipamentoDao.consultar(equipamento.getId());
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("equipamentoDetalhes.jsf");
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	public void preparaIncluir(ActionEvent evt) {
		this.equipamento = new Equipamento();
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("equipamentoDetalhes.jsf");
		} catch (IOException e) {
			UtilFaces.addMensagemFaces(e);
		}
	}

	public void excluir(ActionEvent evt) throws PersistenciaException {
		try {
			equipamento = (Equipamento) evt.getComponent().getAttributes()
					.get("equipamento");
			equipamento = equipamentoDao.consultar(equipamento.getId());
			equipamentoDao.excluirPorId(equipamento.getId());
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

	public void filtrarPorNome() {
		equipamentos = equipamentoDao.listarPorNome(filtroGlobal);
		if (equipamentos.isEmpty()) {
			equipamentos = equipamentoDao.listarPorNome(filtroGlobal);
		}
	}

	public Equipamento getEquipamento() {
		return equipamento;
	}

	public void setEquipamento(Equipamento equipamento) {
		this.equipamento = equipamento;
	}

	public List<Equipamento> getEquipamentos() {
		return equipamentos;

	}

	public String getFiltroGlobal() {
		return filtroGlobal;
	}

	public void setFiltroGlobal(String filtroGlobal) {
		this.filtroGlobal = filtroGlobal;
	}

}
