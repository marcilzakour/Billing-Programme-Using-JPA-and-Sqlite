/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entities.BillType;
import entities.Bill;
import entities.Customer;
import entities.DailyRecords;
import entities.Item;
import entities.Product;
import entities.Storage;
import entities.controllers.BillJpaController;
import entities.controllers.CustomerJpaController;
import entities.controllers.DailyRecordsJpaController;
import entities.controllers.ItemJpaController;
import entities.controllers.ProductJpaController;
import entities.controllers.StorageJpaController;
import entities.controllers.exceptions.NonexistentEntityException;
import java.awt.Component;
import java.awt.Container;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Marcil
 */
/*the main frame contains 3 main panels
  1) daily record panel: contains a table with current day records (payments)
  2) bills panel: allow adding bills for a customer or a company
  3) storage panel: contains the amount of each product available in the storage
*/
public class MainGUI extends javax.swing.JFrame {

    private BillJpaController billController;
    private ProductJpaController productController;
    private ItemJpaController itemController;
    private CustomerJpaController customerController;
    private StorageJpaController storageController;
    private DailyRecordsJpaController dailyRecordsController;
    private Storage storage;
    private DailyRecords todayRecords;
    private BillType currentBillType;
    private List<AutoComplete> productAutoCompletes;
    private List<AutoComplete> customerAutoCompletes;
    private List<AutoComplete> companiesAutoComplete;
    private JPanel activePanel;

    /**
     * Creates new form MainGUI
     */
    public MainGUI() {
	//open log file
        try {
            initComponents();

            try {
                Helper.out = new PrintStream(new FileOutputStream(new File("log.txt"), true));
                Helper.out.println("*** new Run (" + new Date() + ") ****");
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(null, "الملف log.txt مفقود");
                System.exit(0);
            }
           
            this.productAutoCompletes = new ArrayList<>();
//            this.customerAutoCompletes = new ArrayList<>();
//            this.companiesAutoComplete = new ArrayList<>();

            productAutoCompletes.add(productAutoComplete);

//            customerAutoCompletes.add()
            init();

            recordReadingPanel.setVisible(false);
            dateStorageFilterPanel.setVisible(false);
            payedStorageFilterPanel.setVisible(false);
            productCreationPanel.setVisible(false);
            fillAutoCompleteWithProducts(productAutoComplete);
            productsTable.getModel().addTableModelListener((TableModelEvent e) -> productsTableModelChanged(e));
            setEnableComponents(companyStorageFilterPanel, nameStorageFilterCheckBox, nameStorageFilterCheckBox.isSelected());

            billsTable.getModel().addTableModelListener((TableModelEvent e) -> billsTableModelChanged(e));
        } catch (Exception ex) {
            Helper.error(ex);
            Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void billsTableModelChanged(TableModelEvent e) {
        if(e.getType() == TableModelEvent.UPDATE && e.getFirstRow() == e.getLastRow() && (e.getColumn() == 2||e.getColumn() == 3)){
            DefaultTableModel table = (DefaultTableModel)billsTable.getModel();
            int row = e.getFirstRow();
            int col = e.getColumn();
            Long id = (Long)table.getValueAt(row,0);
            Bill bill = billController.findBill(id);
            int oldValue;
            if(col == 2)oldValue = bill.getFinalTotal();
            else if(col == 3)oldValue = bill.getPayed();
            else oldValue = 0;
            if(!Helper.isValidCost(table.getValueAt(row, col).toString())){
                table.setValueAt(oldValue, row, col);
                return;
            }
            
            int newValue = (int)table.getValueAt(row, col);
           
           
            if(col == 2){
                bill.setFinalTotal(newValue);
            }else if(col == 3){
                bill.setPayed(newValue);
            }
            try {
                billController.edit(bill);
            } catch (Exception ex) {
                Helper.error(ex);
            }
        }
    }

    private void fillAutoCompleteWithProducts(final AutoComplete auto) {
        auto.clearData();
        productController.findProductEntities().stream().forEach((p) -> {
            auto.addEntry(p.getId(), p.getPriority());
        });
    }

    public final void init() throws Exception {

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("APPU");
        billController = new BillJpaController(factory);
        productController = new ProductJpaController(factory);
        itemController = new ItemJpaController(factory);
        customerController = new CustomerJpaController(factory);
        dailyRecordsController = new DailyRecordsJpaController(factory);
        storageController = new StorageJpaController(factory);

        storage = storageController.findStorage(1l);

        if (storage == null) {
            Helper.message("تشغيل التطبيق للمرة الاولى");
            storage = new Storage();
            storageController.create(storage);
        }
        initDailyTable(true);

        currentBillType = BillType.customerBill;

    }

    /**
     *
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        payedStorageFilterPanel = new javax.swing.JPanel();
        jComboBox2 = new javax.swing.JComboBox();
        payedStorageFilterCheckBox = new javax.swing.JCheckBox();
        jLabel9 = new javax.swing.JLabel();
        dateStorageFilterPanel = new javax.swing.JPanel();
        jSpinner4 = new javax.swing.JSpinner();
        jSpinner5 = new javax.swing.JSpinner();
        dateStorageFilterCheckBox = new javax.swing.JCheckBox();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        customerBillsPanel = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jButton9 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox();
        jCheckBox4 = new javax.swing.JCheckBox();
        jLabel8 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jSpinner3 = new javax.swing.JSpinner();
        jSpinner2 = new javax.swing.JSpinner();
        jCheckBox2 = new javax.swing.JCheckBox();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        autoComplete1 = new gui.AutoComplete();
        jCheckBox1 = new javax.swing.JCheckBox();
        jLabel5 = new javax.swing.JLabel();
        jButton13 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        twoPanels = new javax.swing.JPanel();
        recordReadingPanel = new javax.swing.JPanel();
        costTextField = new javax.swing.JTextField();
        totalCostTextField = new javax.swing.JTextField();
        productAutoComplete = new gui.AutoComplete();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        quantitySpinner = new javax.swing.JSpinner();
        jScrollPane1 = new javax.swing.JScrollPane();
        dailyTable = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        billsTable = new javax.swing.JTable();
        jButton10 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        companyStorageFilterPanel = new javax.swing.JPanel();
        nameFilterAutoComplete = new gui.AutoComplete();
        nameStorageFilterCheckBox = new javax.swing.JCheckBox();
        bilSearchNameLabel = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        billTypeComboBox = new javax.swing.JComboBox();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        productsTable = new javax.swing.JTable();
        productCreationPanel = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        defaultProductCostTextField3 = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        productQuantitySpinner3 = new javax.swing.JSpinner();
        jButton14 = new javax.swing.JButton();
        productNameAutoComplete3 = new gui.AutoComplete();
        addToggleButton = new javax.swing.JToggleButton();
        jButton11 = new javax.swing.JButton();

        payedStorageFilterPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "المدفوع فقط", "غير المدفوع فقط" }));

        payedStorageFilterCheckBox.setText("تفعيل");

        jLabel9.setText("فلاتر اضافية");

        javax.swing.GroupLayout payedStorageFilterPanelLayout = new javax.swing.GroupLayout(payedStorageFilterPanel);
        payedStorageFilterPanel.setLayout(payedStorageFilterPanelLayout);
        payedStorageFilterPanelLayout.setHorizontalGroup(
            payedStorageFilterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, payedStorageFilterPanelLayout.createSequentialGroup()
                .addComponent(payedStorageFilterCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53)
                .addComponent(jLabel9))
        );
        payedStorageFilterPanelLayout.setVerticalGroup(
            payedStorageFilterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(payedStorageFilterPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(payedStorageFilterPanelLayout.createSequentialGroup()
                .addGroup(payedStorageFilterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(payedStorageFilterCheckBox))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        dateStorageFilterPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jSpinner4.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(), null, null, java.util.Calendar.MONTH));

        jSpinner5.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(), null, null, java.util.Calendar.MONTH));

        dateStorageFilterCheckBox.setText("تفعيل");
        dateStorageFilterCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dateStorageFilterCheckBoxActionPerformed(evt);
            }
        });

        jLabel10.setText("من تاريخ");

        jLabel11.setText("الى تاريخ");

        javax.swing.GroupLayout dateStorageFilterPanelLayout = new javax.swing.GroupLayout(dateStorageFilterPanel);
        dateStorageFilterPanel.setLayout(dateStorageFilterPanelLayout);
        dateStorageFilterPanelLayout.setHorizontalGroup(
            dateStorageFilterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dateStorageFilterPanelLayout.createSequentialGroup()
                .addComponent(dateStorageFilterCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 99, Short.MAX_VALUE)
                .addComponent(jSpinner4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSpinner5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel10)
                .addGap(2, 2, 2))
        );
        dateStorageFilterPanelLayout.setVerticalGroup(
            dateStorageFilterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dateStorageFilterPanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(dateStorageFilterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jSpinner5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSpinner4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dateStorageFilterCheckBox)))
            .addGroup(dateStorageFilterPanelLayout.createSequentialGroup()
                .addGroup(dateStorageFilterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        customerBillsPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "رقم الفاتورة", "اسم الزبون", "الاجمالي", "المدفوع"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(jTable4);

        jButton9.setText("حذف فاتورة");

        jButton8.setText("تعديل فاتورة");

        jButton6.setText("بحث");

        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "المدفوع فقط", "غير المدفوع فقط" }));

        jCheckBox4.setText("تفعيل");

        jLabel8.setText("فلاتر اضافية");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addComponent(jCheckBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53)
                .addComponent(jLabel8))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jCheckBox4))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jSpinner3.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(), null, null, java.util.Calendar.MONTH));

        jSpinner2.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(), null, null, java.util.Calendar.MONTH));

        jCheckBox2.setText("تفعيل");

        jLabel6.setText("من تاريخ");

        jLabel7.setText("الى تاريخ");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addComponent(jCheckBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 99, Short.MAX_VALUE)
                .addComponent(jSpinner3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSpinner2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addGap(2, 2, 2))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jSpinner2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSpinner3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBox2)))
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jCheckBox1.setSelected(true);
        jCheckBox1.setText("تفعيل");

        jLabel5.setText("اسم الزبون");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addComponent(jCheckBox1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(autoComplete1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(autoComplete1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jCheckBox1))
                    .addComponent(jLabel5))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton13.setText("انشاء فاتورة");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jButton13)
                        .addGap(18, 18, 18)
                        .addComponent(jButton9)
                        .addGap(18, 18, 18)
                        .addComponent(jButton8)
                        .addGap(18, 18, 18)
                        .addComponent(jButton6))
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jScrollPane4)
                        .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton6)
                    .addComponent(jButton8)
                    .addComponent(jButton9)
                    .addComponent(jButton13))
                .addGap(19, 19, 19)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout customerBillsPanelLayout = new javax.swing.GroupLayout(customerBillsPanel);
        customerBillsPanel.setLayout(customerBillsPanelLayout);
        customerBillsPanelLayout.setHorizontalGroup(
            customerBillsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(customerBillsPanelLayout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        customerBillsPanelLayout.setVerticalGroup(
            customerBillsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(customerBillsPanelLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 37, Short.MAX_VALUE))
        );

        jButton1.setText("اضافة فاتورة");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTabbedPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane1MouseClicked(evt);
            }
        });

        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel1MouseEntered(evt);
            }
        });

        jButton2.setText("حذف سجل");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton4.setText("اضافة سجل");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        recordReadingPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        costTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                costTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                costTextFieldFocusLost(evt);
            }
        });

        totalCostTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                totalCostTextFieldFocusGained(evt);
            }
        });
        totalCostTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                totalCostTextFieldActionPerformed(evt);
            }
        });

        productAutoComplete.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                productAutoCompleteFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                productAutoCompleteFocusLost(evt);
            }
        });
        productAutoComplete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                productAutoCompleteActionPerformed(evt);
            }
        });

        jLabel1.setText("الدواء");

        jLabel2.setText("الكمية");

        jLabel3.setText("السعر الفردي");

        jLabel4.setText("السعر الاجمالي");

        jButton5.setText("اغلاق");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton7.setText("اضافة");
        jButton7.setNextFocusableComponent(productAutoComplete);
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        quantitySpinner.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));
        quantitySpinner.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                quantitySpinnerFocusLost(evt);
            }
        });

        javax.swing.GroupLayout recordReadingPanelLayout = new javax.swing.GroupLayout(recordReadingPanel);
        recordReadingPanel.setLayout(recordReadingPanelLayout);
        recordReadingPanelLayout.setHorizontalGroup(
            recordReadingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(recordReadingPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(recordReadingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(productAutoComplete, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(recordReadingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(recordReadingPanelLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(63, 63, 63)
                        .addComponent(jLabel3)
                        .addGap(67, 67, 67)
                        .addComponent(jButton7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton5))
                    .addGroup(recordReadingPanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(quantitySpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(costTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(recordReadingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(totalCostTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        recordReadingPanelLayout.setVerticalGroup(
            recordReadingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(recordReadingPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(recordReadingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(recordReadingPanelLayout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(recordReadingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(totalCostTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(costTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(recordReadingPanelLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(26, 26, 26))
                    .addGroup(recordReadingPanelLayout.createSequentialGroup()
                        .addGroup(recordReadingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(recordReadingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(productAutoComplete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(quantitySpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addGroup(recordReadingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton5)
                    .addComponent(jButton7))
                .addContainerGap())
        );

        dailyTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "الدواء", "الكمية", "السعر الافرادي", "السعر الاجمالي", "رقم السجل"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        dailyTable.setToolTipText("");
        jScrollPane1.setViewportView(dailyTable);

        javax.swing.GroupLayout twoPanelsLayout = new javax.swing.GroupLayout(twoPanels);
        twoPanels.setLayout(twoPanelsLayout);
        twoPanelsLayout.setHorizontalGroup(
            twoPanelsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(twoPanelsLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(twoPanelsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(recordReadingPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        twoPanelsLayout.setVerticalGroup(
            twoPanelsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, twoPanelsLayout.createSequentialGroup()
                .addComponent(recordReadingPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap(72, Short.MAX_VALUE)
                        .addComponent(twoPanels, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton2)
                        .addGap(18, 18, 18)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(twoPanels, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 55, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("الحساب اليومي", jPanel1);

        jPanel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel2MouseEntered(evt);
            }
        });

        jPanel9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 205, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 21, Short.MAX_VALUE)
        );

        billsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "رقم الفاتورة", "الاسم", "الاجمالي", "المدفوع"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Long.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        billsTable.setToolTipText("");
        jScrollPane5.setViewportView(billsTable);

        jButton10.setText("حذف فاتورة");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton12.setText("بحث");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        companyStorageFilterPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        nameStorageFilterCheckBox.setText("تفعيل");
        nameStorageFilterCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameStorageFilterCheckBoxActionPerformed(evt);
            }
        });

        bilSearchNameLabel.setText("الاسم");

        javax.swing.GroupLayout companyStorageFilterPanelLayout = new javax.swing.GroupLayout(companyStorageFilterPanel);
        companyStorageFilterPanel.setLayout(companyStorageFilterPanelLayout);
        companyStorageFilterPanelLayout.setHorizontalGroup(
            companyStorageFilterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, companyStorageFilterPanelLayout.createSequentialGroup()
                .addComponent(nameStorageFilterCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(nameFilterAutoComplete, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(bilSearchNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        companyStorageFilterPanelLayout.setVerticalGroup(
            companyStorageFilterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(companyStorageFilterPanelLayout.createSequentialGroup()
                .addGroup(companyStorageFilterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(companyStorageFilterPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(nameFilterAutoComplete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(nameStorageFilterCheckBox))
                    .addComponent(bilSearchNameLabel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton3.setText("انشاء فاتورة");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane5)
                    .addComponent(companyStorageFilterPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton3)
                .addGap(73, 73, 73)
                .addComponent(jButton10)
                .addGap(68, 68, 68)
                .addComponent(jButton12))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(companyStorageFilterPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton12)
                    .addComponent(jButton10)
                    .addComponent(jButton3))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel13.setText("نوع الفاتورة");

        billTypeComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "زبون", "شركة" }));
        billTypeComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                billTypeComboBoxItemStateChanged(evt);
            }
        });
        billTypeComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                billTypeComboBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 10, Short.MAX_VALUE))
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(78, 78, 78)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(billTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel13)
                .addGap(24, 24, 24))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel13)
                        .addComponent(billTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 37, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 86, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.addTab("فواتير", jPanel2);

        jPanel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel4MouseEntered(evt);
            }
        });

        productsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "الاسم", "الكمية", "السعر الافتراضي"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Integer.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        productsTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                productsTableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(productsTable);

        jLabel12.setText("الاسم");

        jLabel14.setText("الكمية");

        defaultProductCostTextField3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                defaultProductCostTextField3FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                defaultProductCostTextField3FocusLost(evt);
            }
        });

        jLabel15.setText("السعر الافتراضي");

        jButton14.setText("اضافة");
        jButton14.setNextFocusableComponent(productNameAutoComplete3);
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        productNameAutoComplete3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                productNameAutoComplete3FocusGained(evt);
            }
        });

        javax.swing.GroupLayout productCreationPanelLayout = new javax.swing.GroupLayout(productCreationPanel);
        productCreationPanel.setLayout(productCreationPanelLayout);
        productCreationPanelLayout.setHorizontalGroup(
            productCreationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(productCreationPanelLayout.createSequentialGroup()
                .addGroup(productCreationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(productCreationPanelLayout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jLabel12))
                    .addGroup(productCreationPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(productNameAutoComplete3, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(23, 23, 23)
                .addGroup(productCreationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(productQuantitySpinner3)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(productCreationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(defaultProductCostTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addComponent(jButton14)
                .addContainerGap())
        );
        productCreationPanelLayout.setVerticalGroup(
            productCreationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(productCreationPanelLayout.createSequentialGroup()
                .addGroup(productCreationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, productCreationPanelLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(productCreationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(productQuantitySpinner3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(productNameAutoComplete3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(productCreationPanelLayout.createSequentialGroup()
                        .addGroup(productCreationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(jLabel14)
                            .addComponent(jLabel12))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(productCreationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(defaultProductCostTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton14))))
                .addGap(22, 22, 22))
        );

        addToggleButton.setText("اضافة دواء");
        addToggleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addToggleButtonActionPerformed(evt);
            }
        });

        jButton11.setText("حذف");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(103, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(56, 56, 56))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(productCreationPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(addToggleButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(34, 34, 34))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(addToggleButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton11))
                    .addComponent(productCreationPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45))
        );

        jTabbedPane1.addTab("الادوية", jPanel4);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        BillReader reader = new BillReader(this, true, BillType.customerBill);
        Long id = reader.getBillId();
        JOptionPane.showMessageDialog(this, id + "");
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        DefaultTableModel table = (DefaultTableModel) dailyTable.getModel();
        int rows[] = dailyTable.getSelectedRows();
        if (rows.length == 0) {
            JOptionPane.showMessageDialog(null, "لا يوجد اسطر محددة للحذف رجاء حدد اسطر من الجدول");
            return;
        }
        for (int i = 0; i < rows.length; ++i) {
            rows[i]++;
        }
        if (JOptionPane.showConfirmDialog(null, "هل تريد حذف السطر/الاسطر" + Arrays.toString(rows)) == JOptionPane.YES_OPTION) {
            while (true) {
                int row = dailyTable.getSelectedRow();
                if (row == -1) {
                    break;
                }
                Item itemToDelete = getItemFromDailyTable(row);
                removeRecord(itemToDelete);
                table.removeRow(row);
            }
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void productAutoCompleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_productAutoCompleteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_productAutoCompleteActionPerformed

    private void totalCostTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_totalCostTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_totalCostTextFieldActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed

        recordReadingPanel.setVisible(true);

    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        recordReadingPanel.setVisible(false);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        if (validateCurrentRecord()) {
            String productName = productAutoComplete.getText();
            int cost = Integer.parseInt(costTextField.getText());
            int quantity = (int) quantitySpinner.getValue();
            int total = Integer.parseInt(totalCostTextField.getText());
            if (productController.findProduct(productName) == null) {
                if (Helper.confirmCreateProduct(productName)) {
                    try {
                        productController.create(new Product(productName, cost, 0));
                        refreshProductAutoCompletes(productController.findProduct(productName));
                    } catch (Exception ex) {
                        Helper.error(ex);
                    }
                } else {
                    productAutoComplete.setText("");
                    return;
                }
            }

            increaseProductPriority(productName, quantity);
            costTextField.setText(productController.findProduct(productName).getCurrentPrice() + "");

            Item item = new Item(productName, cost, quantity, total);

            addRecord(item);
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void quantitySpinnerFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_quantitySpinnerFocusLost
        updateTotal();
    }//GEN-LAST:event_quantitySpinnerFocusLost

    private void costTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_costTextFieldFocusLost
        updateTotal();
    }//GEN-LAST:event_costTextFieldFocusLost

    private void productAutoCompleteFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_productAutoCompleteFocusLost
        String productName = productAutoComplete.getText();
        if (Helper.isValidName(productName)) {
            if (productController.findProduct(productName) != null) {
                costTextField.setText(productController.findProduct(productName).getCurrentPrice() + "");
            } else {
                costTextField.setText("0");
            }
        }
    }//GEN-LAST:event_productAutoCompleteFocusLost

    private void costTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_costTextFieldFocusGained
        costTextField.select(0, costTextField.getText().length());
    }//GEN-LAST:event_costTextFieldFocusGained

    private void totalCostTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_totalCostTextFieldFocusGained
        totalCostTextField.select(0, totalCostTextField.getText().length());
    }//GEN-LAST:event_totalCostTextFieldFocusGained

    private void productAutoCompleteFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_productAutoCompleteFocusGained
        productAutoComplete.select(0, productAutoComplete.getText().length());
        fillAutoCompleteWithProducts(productAutoComplete);
    }//GEN-LAST:event_productAutoCompleteFocusGained

    private void dateStorageFilterCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dateStorageFilterCheckBoxActionPerformed

    }//GEN-LAST:event_dateStorageFilterCheckBoxActionPerformed

    private void nameStorageFilterCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nameStorageFilterCheckBoxActionPerformed

        setEnableComponents(companyStorageFilterPanel, nameStorageFilterCheckBox, nameStorageFilterCheckBox.isSelected());
    }//GEN-LAST:event_nameStorageFilterCheckBoxActionPerformed


    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        ((DefaultTableModel) billsTable.getModel()).setRowCount(0);
        fillTableWithBills(billsTable, currentBillType, nameStorageFilterCheckBox.isSelected() ? nameFilterAutoComplete.getText() : null);
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        BillReader reader = new BillReader(this, true, currentBillType);
        Long id = reader.getBillId();
        if (id == -1l) {
            return;
        }

        if (Helper.confirmAddBillItemsToStorage(billController.findBill(id).getOwner().getId(), currentBillType)) {
            sendBillToStorage(id);
        }
        ((DefaultTableModel) billsTable.getModel()).setRowCount(0);
        fillTableWithBills(billsTable, currentBillType, nameStorageFilterCheckBox.isSelected() ? nameFilterAutoComplete.getText() : null);

    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        deleteSelectedRowsFromBillsTable(billsTable);

    }//GEN-LAST:event_jButton10ActionPerformed

    private void billTypeComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_billTypeComboBoxActionPerformed

    }//GEN-LAST:event_billTypeComboBoxActionPerformed

    private void billTypeComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_billTypeComboBoxItemStateChanged
        if (billTypeComboBox.getSelectedItem().equals("زبون")) {
            currentBillType = BillType.customerBill;

        } else if (billTypeComboBox.getSelectedItem().equals("شركة")) {
            currentBillType = BillType.companyBill;
        } else {
            currentBillType = BillType.NONE;
        }

        ((DefaultTableModel) billsTable.getModel()).setRowCount(0);
        fillTableWithBills(billsTable, currentBillType, nameStorageFilterCheckBox.isSelected() ? nameFilterAutoComplete.getText() : null);
    }//GEN-LAST:event_billTypeComboBoxItemStateChanged

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        if (validateProductData()) {
            String productName = productNameAutoComplete3.getText();
            int cost = Integer.valueOf(defaultProductCostTextField3.getText());
            int quantity = (int) productQuantitySpinner3.getValue();
            Product product = new Product(productName, cost, quantity);
            if (productController.findProduct(productName) != null) {
                Helper.error("المنتج موجود مسبقا");
                return;
            } else {
                try {
                    productController.create(product);
                    refreshProductAutoCompletes(product);
                    addProductToTable(product);
                } catch (Exception ex) {
                    Helper.error(ex);
                }
            }
        }
    }//GEN-LAST:event_jButton14ActionPerformed

    private void defaultProductCostTextField3FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_defaultProductCostTextField3FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_defaultProductCostTextField3FocusLost

    private void defaultProductCostTextField3FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_defaultProductCostTextField3FocusGained
        ((JTextField) evt.getSource()).selectAll();
    }//GEN-LAST:event_defaultProductCostTextField3FocusGained

    private void productNameAutoComplete3FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_productNameAutoComplete3FocusGained
        ((JTextField) evt.getSource()).selectAll();
    }//GEN-LAST:event_productNameAutoComplete3FocusGained

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        DefaultTableModel table = (DefaultTableModel) (productsTable.getModel());
        int rows[] = productsTable.getSelectedRows();
        if (rows.length == 0) {
            JOptionPane.showMessageDialog(null, "لا يوجد اسطر محددة للحذف رجاء حدد اسطر من الجدول");
            return;
        }
        for (int i = 0; i < rows.length; ++i) {
            rows[i]++;
        }
        if (JOptionPane.showConfirmDialog(null, "هل تريد حذف السطر/الاسطر" + Arrays.toString(rows)) == JOptionPane.YES_OPTION) {
            while (true) {
                int row = productsTable.getSelectedRow();
                if (row == -1) {
                    break;
                }
                try {
                    productController.destroy(table.getValueAt(row, 0).toString());
                } catch (NonexistentEntityException ex) {
                    Helper.error(ex);
                }
                table.removeRow(row);
            }
        }
    }//GEN-LAST:event_jButton11ActionPerformed

    private void addToggleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addToggleButtonActionPerformed
        boolean visible = productCreationPanel.isVisible();
        productCreationPanel.setVisible(!visible);
        addToggleButton.setText(visible ? "اضافة دواء" : "اغلاق");
    }//GEN-LAST:event_addToggleButtonActionPerformed

    private void productsTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_productsTableMouseClicked
//        int rows = productsTable.getSelectedRowCount();
//        int cols =  productsTable.getSelectedColumnCount();
//        if(rows != 1 && cols != 1)return;
//        int col = productsTable.getSelectedColumn();
//        int row = productsTable.getSelectedRow();
//        DefaultTableModel table = (DefaultTableModel)productsTable.getModel();
//        
//        table.setValueAt(55555, row, col);
    }//GEN-LAST:event_productsTableMouseClicked

    private void jTabbedPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane1MouseClicked

    }//GEN-LAST:event_jTabbedPane1MouseClicked


    private void jPanel4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel4MouseEntered
        if (activePanel == jPanel4) {
            return;
        }
        activePanel = jPanel4;
        ((DefaultTableModel) productsTable.getModel()).setRowCount(0);
        fillProductsTable();
    }//GEN-LAST:event_jPanel4MouseEntered

    private void jPanel2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MouseEntered
        if (activePanel == jPanel2) {
            return;
        }
        activePanel = jPanel2;
        if (nameStorageFilterCheckBox.isSelected()) {
            return;
        }
        ((DefaultTableModel) billsTable.getModel()).setRowCount(0);
        fillTableWithBills(billsTable, currentBillType, nameStorageFilterCheckBox.isSelected() ? nameFilterAutoComplete.getText() : null);

    }//GEN-LAST:event_jPanel2MouseEntered

    private void jPanel1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseEntered
        if (activePanel == jPanel1) {
            return;
        }
        activePanel = jPanel1;

        //initDailyTable(false);
    }//GEN-LAST:event_jPanel1MouseEntered

    private void jPanel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel1MouseClicked
    private void deleteSelectedRowsFromBillsTable(JTable table) {
        int[] rows = table.getSelectedRows();
        if (rows.length == 0) {
            Helper.message("لا يوجد اسطر محددة للحذف رجاء حدد اسطر من الجدول");
            return;
        }
        for (int i = 0; i < rows.length; ++i) {
            rows[i]++;
        }
        DefaultTableModel model = ((DefaultTableModel) table.getModel());
        if (JOptionPane.showConfirmDialog(null, "هل تريد حذف السطر/الاسطر" + Arrays.toString(rows)) == JOptionPane.YES_OPTION) {

            while (true) {
                int row = table.getSelectedRow();
                if (row == -1) {
                    break;
                }
                long billID = (long) model.getValueAt(row, 0);
                try {
                    billController.destroy(billID);
                    model.removeRow(row);
                } catch (NonexistentEntityException ex) {
                    Helper.error(ex);
                }
            }

        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainGUI.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainGUI.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainGUI.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainGUI.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton addToggleButton;
    private gui.AutoComplete autoComplete1;
    private javax.swing.JLabel bilSearchNameLabel;
    private javax.swing.JComboBox billTypeComboBox;
    private javax.swing.JTable billsTable;
    private javax.swing.JPanel companyStorageFilterPanel;
    private javax.swing.JTextField costTextField;
    private javax.swing.JPanel customerBillsPanel;
    private javax.swing.JTable dailyTable;
    private javax.swing.JCheckBox dateStorageFilterCheckBox;
    private javax.swing.JPanel dateStorageFilterPanel;
    private javax.swing.JTextField defaultProductCostTextField3;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JSpinner jSpinner2;
    private javax.swing.JSpinner jSpinner3;
    private javax.swing.JSpinner jSpinner4;
    private javax.swing.JSpinner jSpinner5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable4;
    private gui.AutoComplete nameFilterAutoComplete;
    private javax.swing.JCheckBox nameStorageFilterCheckBox;
    private javax.swing.JCheckBox payedStorageFilterCheckBox;
    private javax.swing.JPanel payedStorageFilterPanel;
    private gui.AutoComplete productAutoComplete;
    private javax.swing.JPanel productCreationPanel;
    private gui.AutoComplete productNameAutoComplete3;
    private javax.swing.JSpinner productQuantitySpinner3;
    private javax.swing.JTable productsTable;
    private javax.swing.JSpinner quantitySpinner;
    private javax.swing.JPanel recordReadingPanel;
    private javax.swing.JTextField totalCostTextField;
    private javax.swing.JPanel twoPanels;
    // End of variables declaration//GEN-END:variables

    private boolean validateCurrentRecord() {
        StringBuilder errorMessage = new StringBuilder();
        boolean isValid = true;
        if (!Helper.isValidName(productAutoComplete.getText())) {
            errorMessage.append("خطأ في اسم المنتج").append("\n");
            isValid = false;
        }
        if (!Helper.isValidCost(costTextField.getText())) {
            errorMessage.append("خطأ في سعر المنتج").append("\n");
            isValid = false;
        }
        if (!Helper.isValidQuantity(quantitySpinner.getValue().toString())) {
            errorMessage.append("خطأ في كمية المنتج").append("\n");
            isValid = false;
        }
        if (!Helper.isValidCost(totalCostTextField.getText())) {
            errorMessage.append("خطأ في سعر المنتج الاجمالي").append("\n");
            isValid = false;
        }
        if (!isValid) {
            Helper.error(errorMessage.toString());
        }
        return isValid;
    }

    private void addRecord(Item item) {
        try {
            Product product = productController.findProduct(item.getProductName());
            product.increaseQuantity(-item.getQuantity());
            productController.edit(product);
            itemController.create(item);
            todayRecords.getRecords().add(item);
            dailyRecordsController.edit(todayRecords);

            storage.modifyCacheBy(item.getFinalTotal());
            storageController.edit(storage);
        } catch (Exception ex) {
            Helper.error(ex);
        }
        addItemToDailyTable(item);
    }

    private void removeRecord(Item item) {

        try {
            todayRecords.getRecords().remove(item);
            dailyRecordsController.edit(todayRecords);
        } catch (Exception ex) {
            Helper.error(ex);
        }
    }

    private Item getItemFromDailyTable(int row) {
        DefaultTableModel table = (DefaultTableModel) dailyTable.getModel();
        if (row < 0 || row >= table.getRowCount()) {
            return null;
        }
        String productName = table.getValueAt(row, 0).toString();
        int quantity = (int) table.getValueAt(row, 1);
        int cost = (int) table.getValueAt(row, 2);
        int total = (int) table.getValueAt(row, 3);
        long id = (long) table.getValueAt(row, 4);
        return new Item(id, productName, cost, quantity, total);
    }

    private void addItemToDailyTable(Item item) {
        ((DefaultTableModel) dailyTable.getModel()).addRow(new Object[]{item.getProductName(), item.getQuantity(), item.getCost(), item.getFinalTotal(), item.getId()});
    }

    private void initDailyTable(boolean create) {
        ((DefaultTableModel) dailyTable.getModel()).setRowCount(0);
        todayRecords = dailyRecordsController.findRecordsByDay((Date) new Date());
        if (todayRecords == null) {
            if (!create) {
                return;
            }
            todayRecords = new DailyRecords();
            Helper.message("مرحبا, يوم جديد");
            dailyRecordsController.create(todayRecords);
        }
        List<Item> records = todayRecords.getRecords();
//        Helper.message(records);

        records.stream().forEach((item) -> {
            addItemToDailyTable(item);
        });
    }

    private void updateTotal() {

        if (Helper.isValidCost(costTextField.getText()) && Helper.isValidQuantity(quantitySpinner.getValue().toString())) {
            int cost = Integer.parseInt(costTextField.getText());
            int quantity = (int) quantitySpinner.getValue();
            totalCostTextField.setText(cost * quantity + "");
        }
    }

    private void setEnableComponents(Container panel, Component exclude, boolean value) {
        for (Component component : panel.getComponents()) {
            if (component == exclude) {
                continue;
            }
            component.setEnabled(value);
        }
    }

    private void fillTableWithBills(JTable table, BillType billType, String nameFilter) {
        List<Bill> bills;
        if (nameFilter != null) {

            if (!Helper.isValidName(nameFilter)) {
                Helper.error("خطأ في الاسم");
                return;
            }
            Customer customer = customerController.findCustomer(nameFilter);
            if (customer == null) {
                Helper.error("الاسم غير موجودة");
                return;
            }
            bills = customer.getBills();

        } else {
            bills = billController.findBills(billType);
        }
//        for(Bill b:bills)addBillTOTable(b, table)
        Comparator<Bill> cmp = ( a,  b) -> -Integer.compare(a.getFinalTotal()-a.getPayed(), b.getFinalTotal()-b.getPayed());
        bills.stream().sorted(cmp).forEach((b) -> {
            if (b.getBillType() == billType) {
                addBillTOTable(b, table);
            }
        });
    }

    private void addBillTOTable(Bill b, JTable table) {
        ((DefaultTableModel) table.getModel()).addRow(new Object[]{b.getId(), b.getOwner().getId(), b.getFinalTotal(), b.getPayed()});
    }

    private void sendBillToStorage(Long id) {
        if(id == -1l)return;
        List<Item> items = billController.findBill(id).getItems();
        //System.err.println(id+" "+items.size() + items);
        int mul;
        if(currentBillType == BillType.companyBill)mul = 1;
        else if(currentBillType == BillType.customerBill)mul = -1;
        else mul = 0;
        for (Item item:items){
            Product product = productController.findProduct(item.getProductName());
            if(product == null){
                product = new Product(item.getProductName(),item.getCost(),0);
                try {
                    productController.create(product);
                } catch (Exception ex) {
                    Helper.error(ex);
                }
                
            }
            product.increasePriority(item.getQuantity());
            product.increaseQuantity(mul*item.getQuantity());
//            Helper.error(mul+" "+item.getQuantity());
            try {
                productController.edit(product);
            } catch (Exception ex) {
                Helper.error(ex);
            }
        }
    }

    private boolean validateProductData() {
        return Helper.isValidName(productNameAutoComplete3.getText(), true)
                && Helper.isValidCost(defaultProductCostTextField3.getText(), true)
                && Helper.isValidQuantity(productQuantitySpinner3.getValue().toString(), true);

//        String productName = productNameAutoComplete3.getText();
//        int quantity = (int)productQuantitySpinner3.getValue();
//        String costString = defaultProductCostTextField3.getText();
//        StringBuilder builder = new StringBuilder();
//        if(Helper.isValidName(productName)){
//            
//        }
    }

    private void productsTableModelChanged(TableModelEvent e) {
        if (e.getType() == TableModelEvent.UPDATE) {
            if (e.getFirstRow() == e.getLastRow() && e.getColumn() >= 1 && e.getColumn() <= 2) {
                int row = e.getFirstRow();
                int col = e.getColumn();
                //Helper.message(row+" "+col);
                DefaultTableModel table = (DefaultTableModel) productsTable.getModel();
                String productName = table.getValueAt(row, 0).toString();
                Product product = productController.findProduct(productName);
                if (product == null) {
                    Helper.exeption(new NullPointerException("Product is Null"));
                    return;
                }
                Object value = table.getValueAt(row, col);
                int oldValue = col == 1 ? product.getStoredQuantity() : product.getCurrentPrice();
                if ((col == 1 && !Helper.isValidQuantity(value.toString())) || (col == 2 && !Helper.isValidCost(value.toString()))) {
                    Helper.error("القيمة غير صالحة");
                    table.setValueAt(oldValue, row, col);
                    return;
                }
                int newValue = Integer.parseInt(table.getValueAt(row, col).toString());
                if (col == 1) {
                    product.setStoredQuantity(newValue);
                } else if (col == 2) {
                    product.setCurrentPrice(newValue);
                }
                try {
                    productController.edit(product);
                } catch (Exception ex) {
                    Helper.error(ex);
                }
            }
        }
    }

    private void addProductToTable(Product product) {
        ((DefaultTableModel) productsTable.getModel()).addRow(new Object[]{product.getId(), product.getStoredQuantity(), product.getCurrentPrice()});
    }

    private void fillProductsTable() {
        productController.findProductEntities().stream().sorted((a,b)->-Integer.compare(a.getPriority(), b.getPriority())).forEach((product) -> {
            ((DefaultTableModel) productsTable.getModel()).addRow(new Object[]{product.getId(), product.getStoredQuantity(), product.getCurrentPrice()});
        });
    }

    private void increaseProductPriority(String productName, int by) {
        Product p = productController.findProduct(productName);
        if (p == null) {
            Helper.logException(new NullPointerException("trying to modify priority of nonexisted product " + productName));
            return;
        }
        p.increasePriority(by);
        try {
            productController.edit(p);
        } catch (Exception ex) {
            Helper.error(ex);
        }
    }

    public void refreshProductAutoCompletes(Product product) {
        productAutoCompletes.stream().forEach((x) -> {
            x.addEntry(product.getId(), product.getPriority());
        });
    }

}
