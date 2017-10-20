package randomGenerators;

import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial") 
class MyTableModel extends AbstractTableModel{
	String[][] Data;
	String[] Header;
	int Height;
	int Width;
	
	public MyTableModel(String[][] data, String[] header, int height, int width) {
		super();
		Data = data;
		Header = header;
		Height= height;
		Width = width;
	}
	
	@Override
	public String getColumnName(int column) {
		return Header[column];
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return Width;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return Height;
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
