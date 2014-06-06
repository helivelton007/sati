package br.com.ambientinformatica.sati.util;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.ambientinformatica.jpa.exception.PersistenciaException;
import br.com.ambientinformatica.jpa.util.FabricaAbstrata;
import br.com.ambientinformatica.sati.entidade.Cliente;
import br.com.ambientinformatica.sati.persistencia.ClienteDao;

@FacesConverter("clienteConverter")
public class ClienteConverter implements Converter {  

   private ClienteDao clienteDao = (ClienteDao)FabricaAbstrata.criarObjeto("clienteDao");
   
   @Override
   public String getAsString(FacesContext facesContext, UIComponent component, Object value) {  
       if (value == null || value.equals("")) {  
           return "";  
       } else {  
           return String.valueOf(((Cliente)value).getId());  
       }  
   }


   @Override
   public Object getAsObject(FacesContext context, UIComponent component, String value) {
      if (value != null && !value.trim().equals("")) {  
         Cliente cliente = new Cliente();
         try {  
         	long id = Long.parseLong(value);  

            try {
               cliente = clienteDao.consultar(id);
            } catch (PersistenciaException e) {
               e.printStackTrace();
            }
         } catch(NumberFormatException exception) {  
//            throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Cliente escolhido não é válido"));
            return null;
         }  
         return cliente;  
      }else{
         return null;
      }
   }
}  

