package com.tiamex.siicomeii.vista.administracion.Pais;

import com.tiamex.siicomeii.controlador.ControladorPais;
import com.tiamex.siicomeii.persistencia.entidad.Pais;
import com.tiamex.siicomeii.utils.Utils;
import com.tiamex.siicomeii.vista.utils.TemplateDlg;
import java.util.logging.Logger;

/** @author fred **/
public class PaisDlg extends TemplateDlg<Pais>{
    
    public PaisDlg(){
        init();
    }
    
    private void init(){
        grid.addColumn(Pais::getId).setCaption("Id");
        grid.addColumn(Pais::getNombre).setCaption("Pais xd");
        
        buttonSearchEvent();
    }
    
    @Override
    protected void buttonSearchEvent(){
        try{
            grid.setItems(ControladorPais.getInstance().getByName(searchField.getValue()));
        }catch (Exception ex){
            Logger.getLogger(this.getClass().getName()).log(Utils.nivelLoggin(), ex.getMessage());
        }
    }
    
    @Override
    protected void buttonAddEvent(){
        ui.addWindow(new PaisModalWin());
    }

    @Override
    protected void gridEvent() {
    }
    
    @Override
    protected void eventEditButtonGrid(Pais obj){
        ui.addWindow(new PaisModalWin(obj.getId()));
    }
}