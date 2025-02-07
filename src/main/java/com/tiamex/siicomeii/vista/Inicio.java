package com.tiamex.siicomeii.vista;

import com.tiamex.siicomeii.vista.utils.*;
import com.jarektoro.responsivelayout.ResponsiveLayout;
import com.jarektoro.responsivelayout.ResponsiveRow;
import com.tiamex.siicomeii.Main;
import com.tiamex.siicomeii.SiiComeiiUI;
import com.tiamex.siicomeii.controlador.ControladorAgremiado;
import com.tiamex.siicomeii.controlador.ControladorAsistenciaWebinar;
import com.tiamex.siicomeii.controlador.ControladorProximoEvento;
import com.tiamex.siicomeii.controlador.ControladorProximoWebinar;
import com.tiamex.siicomeii.controlador.ControladorWebinarRealizado;
import com.tiamex.siicomeii.persistencia.entidad.ProximoEvento;
import com.tiamex.siicomeii.persistencia.entidad.ProximoWebinar;
import com.tiamex.siicomeii.persistencia.entidad.WebinarRealizado;
import com.tiamex.siicomeii.vista.administracion.ProximoEvento.ProximoEventoDlg;
import com.tiamex.siicomeii.vista.administracion.WebinarRealizado.WebinarRealizadoModalWin;
import com.tiamex.siicomeii.vista.administracion.proximowebinar.ProximoWebinarDlg;
import com.vaadin.data.HasValue;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.event.selection.SelectionEvent;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.shared.ui.grid.ColumnResizeMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HasComponents;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author cerimice *
 */
/**
 * @company tiamex *
 */
/**
 * @param <T> la clase que se va administrar *
 */
public class Inicio<T> extends Panel {

    protected SiiComeiiUI ui;
    protected VerticalLayout contentLayout;
    protected ResponsiveLayout content;
    protected ResponsiveRow row1;
    protected GridLayout gridLHoy, gridLProx, gridOldWeb;

    protected Grid<T> grid;

    public VerticalLayout main;

    public VerticalLayout getMain() {
        return main;
    }

    public Inicio() throws Exception {
        initDlg();

    }

    private void initDlg() throws Exception {
        ui = Element.getUI();
        content = new ResponsiveLayout();
        content.setSpacing();
        content.setResponsive(true);

        contentLayout = new VerticalLayout();
        contentLayout.setSpacing(true);
        contentLayout.setSizeUndefined();
        contentLayout.setWidthFull();
        contentLayout.setResponsive(true);

        VerticalLayout vLayout = new VerticalLayout(); // agregar los eventos en vertical (hoy/proximos(mañana,etc))
        vLayout.setSizeFull();
        vLayout.setResponsive(true);
        vLayout.setSpacing(true);
        vLayout.setMargin(true);

        HorizontalLayout hLayoutHoy = new HorizontalLayout();
        hLayoutHoy.addStyleName("outlined");
        hLayoutHoy.setWidthFull();
        HorizontalLayout hLayoutProx = new HorizontalLayout();
        hLayoutProx.addStyleName("outlined");
        hLayoutProx.setSizeFull();

        gridOldWeb = new GridLayout();
        gridOldWeb.setColumns(5);
        gridOldWeb.setSizeFull();
        gridOldWeb.setSpacing(true);
        gridLHoy = new GridLayout();
        gridLHoy.setColumns(5);
        gridLHoy.setSizeFull();
        gridLHoy.setSpacing(true);
        gridLProx = new GridLayout();
        gridLProx.setColumns(5);
        gridLProx.setSizeFull();
        gridLProx.setSpacing(true);

        List<ProximoEvento> proxEventos = ControladorProximoEvento.getInstance().getAllSorted("titulo");
        List<ProximoWebinar> proxWebinars = ControladorProximoWebinar.getInstance().getAllSorted("titulo");
        List<Object> hoyEvents = new ArrayList<>();
        List<Object> proxEvents = new ArrayList<>();
        List<Object> oldEvents = new ArrayList<>();
        LocalDateTime fechaActual = LocalDateTime.now(ZoneId.systemDefault()).withHour(0).withMinute(0).withSecond(0).withNano(0);

        proxEventos.forEach(ev -> {
            LocalDateTime evDate = ev.getFecha().withHour(0).withMinute(0).withSecond(0);
            if (evDate.isEqual(fechaActual)) {
                hoyEvents.add(ev);
            } else {
                if (evDate.isAfter(fechaActual)) {
                    proxEvents.add(ev);
                }
            }
        });

        proxWebinars.forEach(web -> {
            LocalDateTime webDate = web.getFecha().withHour(0).withMinute(0).withSecond(0);
            if (webDate.isEqual(fechaActual)) {
                hoyEvents.add(web);
            } else {
                if (webDate.isAfter(fechaActual)) {
                    proxEvents.add(web);
                } else {
                    WebinarRealizado wr = (WebinarRealizado) ControladorWebinarRealizado.getInstance().
                            getByNameWebinarRealizado(web.getTitulo());
                    if (wr == null) {
                        oldEvents.add(web);
                    }
                }

            }
        });

        gridLHoy.setCaptionAsHtml(true);
        gridLProx.setCaptionAsHtml(true);
        gridOldWeb.setCaptionAsHtml(true);
        if (hoyEvents.size() > 0) {
            gridLHoy.setCaption("<span style=\"color:#007bff;font-weight:bold;font-size:22px;\"> Hoy (" + hoyEvents.size() + ")</span>");
        } else {
            gridLHoy.setCaption("<span style=\"color:#007bff;font-weight:bold;font-size:22px;\"> Hoy (Ninguno) </span>");
        }

        if (proxEvents.size() > 0) {
            gridLProx.setCaption("<span style=\"color:#007bff;font-weight:bold;font-size:22px;\"> Próximos (" + proxEvents.size() + ")</span>");
        } else {
            gridLProx.setCaption("<span style=\"color:#007bff;font-weight:bold;font-size:22px;\"> Próximos (Ninguno)</span>");
        }

        if (oldEvents.size() > 0) {
            gridOldWeb.setCaption("<span style=\"color:#dc3545;font-weight:bold;font-size:22px;\"> Webinars antiguos no realizados (" + oldEvents.size() + ")</span>");
            createBoxes(oldEvents, gridOldWeb);
            gridOldWeb.setSpacing(true);
            contentLayout.addComponent(gridOldWeb);
        }

        createBoxes(hoyEvents, gridLHoy);
        createBoxes(proxEvents, gridLProx);

        gridLHoy.setSpacing(true);
        gridLProx.setSpacing(true);

        contentLayout.addComponent(gridLHoy);
        contentLayout.addComponent(gridLProx);

        this.setSizeFull();
        this.setCaption("Inicio: Eventos pasados y próximos"); //filtrar mediante un native button por proximos webinars y proximos eventos
        this.setCaptionAsHtml(true);
        this.setContent(contentLayout);

    }

    private void createBoxes(List<Object> events, GridLayout grid) throws IOException {
        Label lblTitle;
        Label lblFecha;
        CustomLayout box;
        try {
            for (Object obj : events) {
                box = new CustomLayout(new FileInputStream(new File(Main.getBaseDir() + "/inicio.html")));
                box.setCaptionAsHtml(true); box.addStyleNames("customGridBox");
                box.setSizeFull();
                lblTitle = new Label();
                lblFecha = new Label();
                lblTitle.setWidthFull();
                lblFecha.setWidthFull();
                Button btnGo = new Button();
                btnGo.addStyleNames(ValoTheme.BUTTON_FRIENDLY,ValoTheme.BUTTON_SMALL);
                btnGo.setCaption("Ver");
                btnGo.setResponsive(true);
                if (obj instanceof ProximoEvento) {
                    ProximoEvento proxEv = (ProximoEvento) obj;
                    String value = proxEv.getTitulo();
                    box.setIcon(VaadinIcons.CALENDAR_CLOCK);
                    box.setCaption("<b>Proximo Evento</b>");
                    box.setDescription("<span>Descripción: " + proxEv.getDescripcion() + "</span>", ContentMode.HTML);
                    lblTitle.setValue(proxEv.getTitulo());
                    lblFecha.setValue(proxEv.getFechaFrm());
                    btnGo.addClickListener((ClickListener) (Button.ClickEvent listener) -> {
                        FabricaVista fb = ui.getFabricaVista();
                        try {
                            ProximoEventoDlg proxEvDlg = fb.getProximoEventoDlg();
                            fb.getMainPanel().setContenidoPrincipal(proxEvDlg);
                            proxEvDlg.filtroTitulo.setValue(value);
                        } catch (IOException ex) {
                            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (Exception ex) {
                            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                } else if (obj instanceof ProximoWebinar) {
                    ProximoWebinar proxWeb = (ProximoWebinar) obj;
                    String value = proxWeb.getTitulo();
                    box.setIcon(VaadinIcons.CALENDAR_USER);
                    box.setCaption("<b>Proximo Webinar</b>");
                    box.setDescription("<span>Ponente: " + proxWeb.getPonente() + "</span><br>"
                            + "<span>Institución: " + proxWeb.getInstitucion() + "</span>", ContentMode.HTML);
                    lblTitle.setValue(proxWeb.getTitulo());
                    lblFecha.setValue(proxWeb.getFechaFrm());
                    btnGo.addClickListener((ClickListener) (Button.ClickEvent listener) -> {
                        FabricaVista fb = ui.getFabricaVista();
                        try {
                            ProximoWebinarDlg proxWebDlg = fb.getProximoWebinarDlg();
                            fb.getMainPanel().setContenidoPrincipal(proxWebDlg);
                            proxWebDlg.cpySearchFld.setValue(value);
                        } catch (IOException ex) {
                            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (Exception ex) {
                            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                    
                }
                box.addComponent(lblTitle, "titulo");
                box.addComponent(lblFecha, "fecha");
                box.addComponent(btnGo, "btnGo");
                grid.addComponent(box);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
