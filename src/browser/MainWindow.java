package browser;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
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
