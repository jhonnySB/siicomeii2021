package com.tiamex.siicomeii.vista;

import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.tiamex.siicomeii.SiiComeiiUI;
import com.tiamex.siicomeii.vista.utils.Element;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

/** @author cerimice **/

public class MainPanel extends Panel{
    
    private SiiComeiiUI ui;
    
    private VerticalLayout contenidoPrincipal;
    public void setContenidoPrincipal(Panel contenido){
        contenidoPrincipal.removeAllComponents();
        contenidoPrincipal.addComponent(contenido);
    }

    public MainPanel(){
        init();
    }
    
    private void init(){
        ui = Element.getUI();
        
        ResponsiveLayout header = new ResponsiveLayout();
            Element.cfgLayoutComponent(header);
        ResponsiveRow headerRow1 = header.addRow().withAlignment(Alignment.TOP_CENTER);
            headerRow1.addColumn().withDisplayRules(12,12,12,12).withComponent(crearMenuPrincipal());
        
        contenidoPrincipal = new VerticalLayout();
            Element.cfgLayoutComponent(contenidoPrincipal,true,false);
        
        ResponsiveLayout footer = new ResponsiveLayout();
            Element.cfgLayoutComponent(footer);
        
        VerticalLayout contenido = new VerticalLayout();
            Element.cfgLayoutComponent(contenido,true,true);
            contenido.addComponent(header);
            contenido.addComponent(contenidoPrincipal);
            contenido.addComponent(footer);
        
        setSizeFull();
        setContent(contenido);
    }
    
    private MenuBar crearMenuPrincipal(){
        MenuBar menuPrincipal = new MenuBar();
            menuPrincipal.setWidth("100%");
        
        MenuBar.MenuItem administracion = menuPrincipal.addItem("Administración",VaadinIcons.TOOLBOX,null);
            MenuBar.MenuItem catalogos = administracion.addItem("Catalogos",VaadinIcons.CALC_BOOK,null);
            MenuBar.MenuItem gradoEstudio = catalogos.addItem("Grado de estudios",VaadinIcons.USERS,comando -> {setContenidoPrincipal(ui.getFabricaVista().getGradoEstudioDlg());});
            MenuBar.MenuItem usuarioGrupo = catalogos.addItem("Usuario Grupo",VaadinIcons.USERS,comando -> {setContenidoPrincipal(ui.getFabricaVista().getUsuarioGrupoDlg());});
            MenuBar.MenuItem usuario = catalogos.addItem("Usuarios",VaadinIcons.USER,comando -> {setContenidoPrincipal(ui.getFabricaVista().getUsuarioDlg());});
        //MenuBar.MenuItem proximoEvento = menuPrincipal.addItem("Proximos Eventos",VaadinIcons.CALENDAR_USER,null);
        MenuBar.MenuItem proximosWebinars = menuPrincipal.addItem("Proximos Webinars",VaadinIcons.CALENDAR_USER,comando -> {setContenidoPrincipal(ui.getFabricaVista().getProximoWebinarDlg());});
        MenuBar.MenuItem tutoriales = menuPrincipal.addItem("Tutoriales",VaadinIcons.DESKTOP,comando -> {setContenidoPrincipal(ui.getFabricaVista().getTutorialDlg());});
        
        //MenuBar.MenuItem usuario = menuPrincipal.addItem(ui.getUsuario().getNombre(),VaadinIcons.USER,null);
            //MenuBar.MenuItem cerrarSesion = usuario.addItem("Cerrar sesión",VaadinIcons.CLOSE,comando -> {ui.getSession().close(); ui.getPage().reload();});
        
        return menuPrincipal;
    }
}