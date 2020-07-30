package com.tiamex.siicomeii.vista.administracion.usuario;

import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.tiamex.siicomeii.controlador.ControladorUsuario;
import com.tiamex.siicomeii.persistencia.entidad.Usuario;
import com.tiamex.siicomeii.utils.Utils;
import com.tiamex.siicomeii.vista.utils.Element;
import com.tiamex.siicomeii.vista.utils.TemplateDlg;
import com.tiamex.siicomeii.vista.utils.TemplateModalWin;
import com.vaadin.shared.Position;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import java.util.logging.Logger;

/** @author cerimice **/
public class UsuarioDlg extends TemplateDlg<Usuario>{
    
    public UsuarioDlg(){
        init();
    }
    
    private void init(){
        grid.addColumn(Usuario::getNombre).setCaption("Nombre");
        grid.addColumn(Usuario::getCorreo).setCaption("Correo");
        grid.addColumn(Usuario::getObjUsuarioGrupo).setCaption("Grupo");
        
        buttonSearchEvent();
    }
    
    @Override
    protected void buttonSearchEvent(){
        try{
            grid.setItems(ControladorUsuario.getInstance().getAll());
        }catch (Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
        }
    }

    @Override
    protected void buttonAddEvent(){
        TemplateModalWin ventana = new TemplateModalWin(){
            @Override
            protected void loadData(long id) {
                
            }

            @Override
            protected void buttonDeleteEvent() {
                
            }

            @Override
            protected void buttonAcceptEvent(){
                Element.makeNotification("Datos guardados",Notification.Type.HUMANIZED_MESSAGE,Position.TOP_CENTER).show(ui.getPage());
                close();
            }

            @Override
            protected void buttonCancelEvent(){
                close();
            }
        };
        
        ui.addWindow(ventana);
    }

    @Override
    protected void gridEvent() {
    }
    
    @Override
    protected void eventEditButtonGrid(Usuario obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}