package swing;

import com.mysql.cj.jdbc.result.ResultSetMetaData;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import main.Config;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;

public class Table_Histori extends JTable {

    private void load_table() {
        try {
            int q;
            String sql = "Select * from transaksi";
            Connection conn = (Connection) Config.configDB();
            PreparedStatement pst;
            pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery(sql);
            ResultSetMetaData rss = (ResultSetMetaData) rs.getMetaData();
            q = rss.getColumnCount();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);
            while(rs.next()){
                Vector v2 = new Vector();
                for (int a = 1; a <= q - 1 ; a++) {
                    v2.add(rs.getString("Tanggal"));
                    v2.add(rs.getString("Detail"));
                    v2.add(rs.getString("Jumlah"));
                    v2.add(rs.getString("Aksi"));
                }
        
                
                rs.close();
                pst.close();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Data Gagal Ditampilan");
                e.printStackTrace();
        }
    }

    public Table_Histori() {
        load_table();
        setShowHorizontalLines(true);
        setRowHeight(40);
        setGridColor(new Color(230, 230, 230));
        
        
        setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int i1) {
                if (i1 != 4) {
                    Component com = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, i1);
                    setBackground(Color.WHITE);
                    setBorder(noFocusBorder);
                    if (isSelected) {
                        com.setForeground(new Color(207, 68, 199));
                    } else {
                        com.setForeground(new Color(10, 10, 10));
                    }
                    return com;
                }
                return new JLabel("testing");
            }
        });

    }
    
    JTable table = new JTable();

}
