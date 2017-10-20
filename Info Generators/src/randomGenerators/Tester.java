package randomGenerators;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import net.miginfocom.swing.MigLayout;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Tester {

	private JFrame frame;
	private JButton makeTableButton;
	private JLabel numFieldsLabel;
	private JTextField numFieldsText;
	private JTable theTable;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Tester window = new Tester();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Tester() {
		initialize();
	}

	/**
	 * Initialize the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new MigLayout("", "[||]", "[|grow]"));
		InitComponents();
		AddComponents();
		makeTableButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				InitTable(Integer.valueOf(numFieldsText.getText()));	
				makeTableButton.setEnabled(false);
				numFieldsText.setEnabled(false);
			}
		});
	}
	/**
	 * Initialize the components of the frame.
	 */
	private void InitComponents() {
		numFieldsLabel = new JLabel("# of Fields");
		numFieldsText = new JTextField();
		makeTableButton = new JButton("Make Table!");;
	}
	
	private void AddComponents() {
		frame.getContentPane().add(numFieldsLabel, "cell 0 0, pushx");
		frame.getContentPane().add(numFieldsText, "cell 1 0, pushx, grow");
		numFieldsText.setPreferredSize(numFieldsText.getPreferredSize());
		frame.getContentPane().add(makeTableButton, "cell 2 0, pushx");		

	}	
	
	private void InitTable(int numFields) {
		String[][] theData = new String[numFields][4];
		String[] header = {"Field Name", "Format", "Regex", "Max Variations"};
		for(int i = 0; i < numFields; i++) {
			for(int j = 0; j < 2; j++) {
				theData[i][j] = "";
			}
		}
		
		MyTableModel tModel = new MyTableModel(theData, header, numFields, 4); 

		theTable = new JTable(tModel);
		TableColumn optionColumn = theTable.getColumnModel().getColumn(1);
		optionColumn.setCellEditor(new DefaultCellEditor(BuildComboBox()));
		InvokeCellSelectionOptions(theTable);
		InvokeTableDimensions(theTable);
	
		
		frame.getContentPane().add(new JScrollPane(theTable), "cell 0 1,span,grow, push");
		frame.repaint();
		frame.revalidate();
	}
	
	
	private JComboBox BuildComboBox() {
		JComboBox<String> box = new JComboBox<String>();
		box.addItem("Name - First(M/F)");
		box.addItem("Name - First(F)");
		box.addItem("Name - First(M)");
		box.addItem("Name - Last");
		box.addItem("Custom");
		box.addItem("Copy Value of");	
		return box;
	}
	
	
	private void InvokeTableDimensions(JTable ttheTable) {
		
		ttheTable.setFillsViewportHeight(true);              // ===== sets height of the JTable to the height of the container (in this case, JScrollPane)
		
		ttheTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // ==== prevents the table from auto-resizing
		TableColumnModel cm = ttheTable.getColumnModel();    // ==== class object the table uses to store its columns
		
		for(int i = 0; i < cm.getColumnCount(); i++) {
			cm.getColumn(i).setPreferredWidth(80);         // ==== used to set column width (otherwise, they stretch to fill their max size)
		}
	}
	
	

	private void InvokeCellSelectionOptions(JTable ttheTable) {
		int multipleInterval = javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION;
		int singleInterval = javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION;
		int singleSelection = javax.swing.ListSelectionModel.SINGLE_SELECTION;
		
		ttheTable.setRowSelectionAllowed(false);    
		ttheTable.setColumnSelectionAllowed(false);
		ttheTable.setCellSelectionEnabled(true);   
		
		ttheTable.setSelectionMode(singleSelection);
	} 


	
}

