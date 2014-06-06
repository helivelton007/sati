package br.com.ambientinformatica.sati.util;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.ambientinformatica.jpa.exception.PersistenciaException;
import br.com.ambientinformatica.jpa.util.FabricaAbstrata;
import br.com.ambientinformatica.sati.entidade.Servico;
import br.com.ambientinformatica.sati.persistencia.ServicoDao;

@FacesConverter("servicoConverter")
public class ServicoConverter implements Converter {  

   private ServicoDao servicoDao = (ServicoDao)FabricaAbstrata.criarObjeto("servicoDao");
   
   @Override
   public String getAsString(FacesContext facesContext, UIComponent component, Object value) {  
       if (value == null || value.equals("")) {  
           return "";  
       } else {  
           return String.valueOf(((Servico) value).getId());  
       }  
   }


   @Override
   public Object getAsObject(FacesContext context, UIComponent component, String value) {
      if (value != null && !value.trim().equals("")) {  
         Servico servico = new Servico();
         try {  
            int id = Integer.parseInt(value);  

            try {
               servico = servicoDao.consultar(id);
            } catch (PersistenciaException e) {
               e.printStackTrace();
            }
         } catch(NumberFormatException exception) {  

            return null;
         }  
         return servico;  
      }else{
         return null;
      }
   }
}  

