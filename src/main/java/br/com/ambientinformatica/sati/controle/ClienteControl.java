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
import br.com.ambientinformatica.corporativo.entidade.Cep;
import br.com.ambientinformatica.jpa.exception.PersistenciaException;
import br.com.ambientinformatica.sati.entidade.Cliente;
import br.com.ambientinformatica.sati.persistencia.CepDao;
import br.com.ambientinformatica.sati.persistencia.ClienteDao;
import br.com.ambientinformatica.sati.util.SatiException;

@Controller("ClienteControl")
@Scope("conversation")
public class ClienteControl {

	private Cliente cliente = new Cliente();
	
	
	@Autowired
	private ClienteDao clienteDao;
	private String filtroGlobal = "";
	private List<Cliente> clientes = new ArrayList<Cliente>();
	private String cepString;

   @PostConstruct
   public void init(){
      listar(null);
   }
   
	public void confirmar(ActionEvent evt){
		try {
			clienteDao.alterar(cliente);
         listar(evt);
         cliente = new Cliente();
         FacesContext.getCurrentInstance().getExternalContext()
			.redirect("cliente.jsf");
		} catch (Exception e) {
		   UtilFaces.addMensagemFaces(e);
		}
	}
		public void listar(ActionEvent evt) {
			try {
				clientes.clear();
				Integer id = new Integer(filtroGlobal);
				Cliente a = clienteDao.consultar(id);
				if (a != null) {
					clientes.add(a);
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
		this.cliente = new Cliente();
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("clienteDetalhes.jsf");
		} catch (IOException e) {
			UtilFaces.addMensagemFaces(e);
		}
	}
	public void preparaAlterar(ActionEvent evt) {
		try {
			cliente = (Cliente) evt.getComponent().getAttributes().get("cliente");
			cliente = clienteDao.consultar(cliente.getId());
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("clienteDetalhes.jsf");
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}
	public void excluir(ActionEvent evt) throws PersistenciaException {
		try {
			cliente = (Cliente) evt.getComponent().getAttributes().get("cliente");
			cliente = clienteDao.consultar(cliente.getId());
			clienteDao.excluirPorId(cliente.getId());
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
			clientes = clienteDao.listarPorNome(filtroGlobal);
		} catch (SatiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PersistenciaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (clientes.isEmpty()) {
			try {
				clientes = clienteDao.listarPorNome(filtroGlobal);
			} catch (SatiException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (PersistenciaException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void consultarCep(ActionEvent evento) throws Exception {
		CepDao cepDao = new CepDao();
		Cep cep = new Cep();
		try {
			cep = cepDao.consultar(cepString);
			cliente.setCep(cep);
		}catch(NullPointerException npe){
			cep = new Cep();
		} catch (IOException e) {
			UtilFaces.addMensagemFaces(e);
		}
	}
	
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public List<Cliente> getClientes() {
		return clientes;
	}

	public String getFiltroGlobal() {
		return filtroGlobal;
	}

	public void setFiltroGlobal(String filtroGlobal) {
		this.filtroGlobal = filtroGlobal;
	}

	public String getCepString() {
		return cepString;
	}

	public void setCepString(String cepString) {
		this.cepString = cepString;
	}
	

}
