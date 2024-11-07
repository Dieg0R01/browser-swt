package browser;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.LocationEvent;
import org.eclipse.swt.browser.LocationListener;
import org.eclipse.swt.browser.ProgressEvent;
import org.eclipse.swt.browser.ProgressListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

public class MainWindow {
	public static void main(String[] args) {
		// Shell con Display
		Shell shell = new Shell(new Display());
		shell.setText("Práctica7");
		shell.setSize(1366, 768);
		Display displayShell = shell.getDisplay();
		
		// Barra de herramientas
		ToolBar toolBar = new ToolBar(shell, 0);
		// Items de la barra de herramientas
		ToolItem atrasB = new ToolItem(toolBar, SWT.PUSH);
		ToolItem adelanteB = new ToolItem(toolBar, SWT.PUSH);
		ToolItem pararB = new ToolItem(toolBar, SWT.PUSH);
		ToolItem irB = new ToolItem(toolBar, SWT.PUSH);
		atrasB.setText("Atrás");
		adelanteB.setText("Adelante");
		pararB.setText("Parar");
		irB.setText("Ir");
		
		// Cuadro de texto
        Text textBox = new Text(shell, SWT.SINGLE | SWT.BORDER);
        
        GridData gridData = new GridData();
        gridData.horizontalAlignment = SWT.FILL;
        gridData.grabExcessHorizontalSpace = true;
        textBox.setLayoutData(gridData);
        
        // Navegador
        Browser navegador = new Browser(shell, SWT.NONE);
        
        GridData gridDataNavegador = new GridData();
        gridDataNavegador.horizontalAlignment = SWT.FILL;
        gridDataNavegador.grabExcessHorizontalSpace = true;
        gridDataNavegador.verticalAlignment = SWT.FILL;
        gridDataNavegador.grabExcessVerticalSpace = true;
        gridDataNavegador.horizontalSpan = 2;
        navegador.setLayoutData(gridDataNavegador);
        
        navegador.setUrl("http://www.unirioja.es");
        
        navegador.addLocationListener(new LocationListener() {
            @Override
            public void changing(LocationEvent event) {
                
            }

            @Override
            public void changed(LocationEvent event) {
                textBox.setText(event.location);
            }
        });

        textBox.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.keyCode == SWT.CR) {
                    String url = textBox.getText();
                    if (url != null && !url.isEmpty()) {
                        navegador.setUrl(url);
                    }
                }
            }
        });
        
        atrasB.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                if (navegador.isBackEnabled()) {
                    navegador.back();
                }
            }
        });

        adelanteB.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                if (navegador.isForwardEnabled()) {
                    navegador.forward();
                }
            }
        });

        pararB.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                navegador.stop();
            }
        });

        irB.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                String url = textBox.getText();
                if (url != null && !url.isEmpty()) {
                    navegador.setUrl(url);
                }
            }
        });
        
        // Barra de estado
        Label statusLabel = new Label(shell, SWT.NONE);
        statusLabel.setText("Listo");
        statusLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
        
        // Barra de progreso
        ProgressBar progressBar = new ProgressBar(shell, SWT.HORIZONTAL);
        progressBar.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
        progressBar.setVisible(false);

        // Agregar barra de menús
        Menu menuBar = new Menu(shell, SWT.BAR);
        shell.setMenuBar(menuBar);
        
        MenuItem fileMenu = new MenuItem(menuBar, SWT.CASCADE);
        fileMenu.setText("Archivo");

        Menu fileSubMenu = new Menu(shell, SWT.DROP_DOWN);
        fileMenu.setMenu(fileSubMenu);

        // Opción de cargar archivo HTML
        MenuItem openItem = new MenuItem(fileSubMenu, SWT.PUSH);
        openItem.setText("Abrirº");
        openItem.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                FileDialog dialog = new FileDialog(shell, SWT.OPEN);
                dialog.setFilterExtensions(new String[] { "*.html", "*.htm" });
                String filePath = dialog.open();
                if (filePath != null) {
                    navegador.setUrl("file:///" + filePath);
                }
            }
        });

        // Opción de salir
        MenuItem exitItem = new MenuItem(fileSubMenu, SWT.PUSH);
        exitItem.setText("Salir");
        exitItem.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                shell.close();
            }
        });

        // Progreso de carga de la página
        navegador.addProgressListener(new ProgressListener() {
            @Override
            public void changed(ProgressEvent event) {
                if (event.total == 0) {
                    progressBar.setVisible(false);
                } else {
                    progressBar.setVisible(true);
                    progressBar.setSelection(event.current);
                    progressBar.setMaximum(event.total);
                }
            }

            @Override
            public void completed(ProgressEvent event) {
                progressBar.setVisible(false);
                statusLabel.setText("Completado");
            }
        });
		
		// Iniciación
        shell.setLayout(new GridLayout(2, false));
        shell.open();
		
        // Bucle gestor
		while(!shell.isDisposed()) {
			if(!displayShell.readAndDispatch()) {
				displayShell.sleep();
			}
		}
		displayShell.dispose();
	}
}
