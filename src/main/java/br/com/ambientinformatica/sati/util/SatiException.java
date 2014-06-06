package br.com.ambientinformatica.sati.util;

import java.util.List;

import br.com.ambientinformatica.util.AmbientException;

public class SatiException extends AmbientException{

   private static final long serialVersionUID = 1L;

   public SatiException() {
      super();
   }

   public SatiException(List<String> listaMensagens) {
      super(listaMensagens);
   }

   public SatiException(String message, Throwable cause) {
      super(message, cause);
   }

   public SatiException(String message) {
      super(message);
   }

   public SatiException(Throwable cause) {
      super(cause);
   }
}
