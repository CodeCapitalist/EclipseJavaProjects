package randomGenerators;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumnModel;

import net.miginfocom.swing.MigLayout;

public class TableTester{
	private final int HEIGHT = 4;
	private final int WIDTH = 4;
	private final String[] HEADER = {"FIRST","SECOND","THIRD","FOURTH"};
	private String[][] Data;
	
	private JFrame frame;
	private JTable theTable;
	
  ////////////////////////////////////////////////////////////////////
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TableTester window = new TableTester();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

  //////////////////////////////////////////////////////////////
	public TableTester() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		
		Data = InitTableBaseData(HEIGHT, WIDTH);
		AbstractTableModel tableModel = new MyTableModel();
		tableModel.addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {
				System.out.println(e);
			}
		});
		theTable = new JTable(tableModel);
		
		InvokeTableDimensions(theTable);
		InvokeCellSelectionOptions(theTable);
		
		frame.getContentPane().add(new JScrollPane(theTable), "push, grow");
	}
		
	
	private String[][] InitTableBaseData(int height, int width) {
		String[][] theData = new String[height][width];
		for(int i = 0; i < HEIGHT; i++) {
			for(int j = 0; j < WIDTH; j++) {
				theData[i][j] = "";
			}
		}
		return theData;
	}
	
	
	@SuppressWarnings("serial")
	private class MyTableModel extends AbstractTableModel{
		
		@Override
		public String getColumnName(int column) {
			return HEADER[column];
		}

		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return true;
		}

		@Override
		public int getColumnCount() {
			// TODO Auto-generated method stub
			return WIDTH;
		}

		@Override
		public int getRowCount() {
			// TODO Auto-generated method stub
			return HEIGHT;
		}

		@Override
		public Object getValueAt(int arg0, int arg1) {
			// TODO Auto-generated method stub
			return Data[arg0][arg1];
		}
		
		@Override
		public void setValueAt(Object value, int row, int column) {
		    Data[row][column] = (String) value;	   
		    fireTableCellUpdated(row, column);		  
		}
		
	}
	

	private void InvokeTableDimensions(JTable theTable) {
		
		theTable.setFillsViewportHeight(true);              // ===== sets height of the JTable to the height of the container (in this case, JScrollPane)
		
		theTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // ==== prevents the table from auto-resizing
		TableColumnModel cm = theTable.getColumnModel();    // ==== class object the table uses to store its columns
		
		for(int i = 0; i < cm.getColumnCount(); i++) {
			cm.getColumn(i).setPreferredWidth(150);         // ==== used to set column width (otherwise, they stretch to fill their max size)
		}
	}
	

	private void InvokeCellSelectionOptions(JTable theTable) {
		int multipleInterval = javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION;
		int singleInterval = javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION;
		int singleSelection = javax.swing.ListSelectionModel.SINGLE_SELECTION;
		
		theTable.setRowSelectionAllowed(false);    
		theTable.setColumnSelectionAllowed(false);
		theTable.setCellSelectionEnabled(true);   
		
		theTable.setSelectionMode(singleSelection);
	}
	

}


