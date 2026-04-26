import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class OrderTrackingSystem extends JFrame implements ActionListener {

    JTextField txtOrderId, txtCustomerId, txtProductId, txtQuantity, txtStatus;
    JTextField txtCustomerName, txtPhone, txtCity;
    JTextField txtProductName, txtPrice;

    JButton btnAddCustomer, btnAddProduct;
    JButton btnAddOrder, btnViewOrders, btnUpdateStatus, btnDeleteOrder;

    JTable table;
    DefaultTableModel model;

    public OrderTrackingSystem() {

        setTitle("E-Commerce Order Tracking System");
        setSize(1100, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        Font font = new Font("Arial", Font.BOLD, 14);

        // Order Panel
        JPanel orderPanel = new JPanel(new GridLayout(4, 4, 10, 10));
        orderPanel.setBorder(BorderFactory.createTitledBorder("Order Details"));
        orderPanel.setBackground(new Color(220, 235, 250));

        orderPanel.add(new JLabel("Order ID"));
        txtOrderId = new JTextField();
        orderPanel.add(txtOrderId);

        orderPanel.add(new JLabel("Customer ID"));
        txtCustomerId = new JTextField();
        orderPanel.add(txtCustomerId);

        orderPanel.add(new JLabel("Product ID"));
        txtProductId = new JTextField();
        orderPanel.add(txtProductId);

        orderPanel.add(new JLabel("Quantity"));
        txtQuantity = new JTextField();
        orderPanel.add(txtQuantity);

        orderPanel.add(new JLabel("Status"));
        txtStatus = new JTextField();
        orderPanel.add(txtStatus);

        btnAddOrder = new JButton("Add Order");
        btnViewOrders = new JButton("View Orders");
        btnUpdateStatus = new JButton("Update Status");
        btnDeleteOrder = new JButton("Delete Order");

        btnAddOrder.addActionListener(this);
        btnViewOrders.addActionListener(this);
        btnUpdateStatus.addActionListener(this);
        btnDeleteOrder.addActionListener(this);

        btnAddOrder.setFont(font);
        btnViewOrders.setFont(font);
        btnUpdateStatus.setFont(font);
        btnDeleteOrder.setFont(font);

        orderPanel.add(btnAddOrder);
        orderPanel.add(btnViewOrders);
        orderPanel.add(btnUpdateStatus);
        orderPanel.add(btnDeleteOrder);

        // Customer and Product Panel
        JPanel customerProductPanel = new JPanel(new GridLayout(4, 4, 10, 10));
        customerProductPanel.setBorder(BorderFactory.createTitledBorder("Customer / Product Details"));
        customerProductPanel.setBackground(new Color(240, 240, 240));

        customerProductPanel.add(new JLabel("Customer Name"));
        txtCustomerName = new JTextField();
        customerProductPanel.add(txtCustomerName);

        customerProductPanel.add(new JLabel("Phone"));
        txtPhone = new JTextField();
        customerProductPanel.add(txtPhone);

        customerProductPanel.add(new JLabel("City"));
        txtCity = new JTextField();
        customerProductPanel.add(txtCity);

        customerProductPanel.add(new JLabel("Product Name"));
        txtProductName = new JTextField();
        customerProductPanel.add(txtProductName);

        customerProductPanel.add(new JLabel("Price"));
        txtPrice = new JTextField();
        customerProductPanel.add(txtPrice);

        btnAddCustomer = new JButton("Add Customer");
        btnAddProduct = new JButton("Add Product");

        btnAddCustomer.addActionListener(this);
        btnAddProduct.addActionListener(this);

        btnAddCustomer.setFont(font);
        btnAddProduct.setFont(font);

        customerProductPanel.add(btnAddCustomer);
        customerProductPanel.add(btnAddProduct);

        JPanel topPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        topPanel.add(orderPanel);
        topPanel.add(customerProductPanel);

        add(topPanel, BorderLayout.NORTH);

        // Table
        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{
                "Order ID", "Customer Name", "Product Name", "Quantity", "Date", "Status"
        });

        table = new JTable(model);
        table.setRowHeight(25);
        table.getTableHeader().setFont(font);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Order Records"));

        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btnAddCustomer) {
            addCustomer();
        }

        if (e.getSource() == btnAddProduct) {
            addProduct();
        }

        if (e.getSource() == btnAddOrder) {
            addOrder();
        }

        if (e.getSource() == btnViewOrders) {
            viewOrders();
        }

        if (e.getSource() == btnUpdateStatus) {
            updateOrder();
        }

        if (e.getSource() == btnDeleteOrder) {
            deleteOrder();
        }
    }

    private void addCustomer() {
        try {
            Connection con = DBConnection.getConnection();

            String query = "INSERT INTO customers(customer_name, phone, city) VALUES (?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(query);

            pst.setString(1, txtCustomerName.getText());
            pst.setString(2, txtPhone.getText());
            pst.setString(3, txtCity.getText());

            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, "Customer Added Successfully");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void addProduct() {
        try {
            Connection con = DBConnection.getConnection();

            String query = "INSERT INTO products(product_name, price) VALUES (?, ?)";
            PreparedStatement pst = con.prepareStatement(query);

            pst.setString(1, txtProductName.getText());
            pst.setDouble(2, Double.parseDouble(txtPrice.getText()));

            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, "Product Added Successfully");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void addOrder() {
        try {
            Connection con = DBConnection.getConnection();

            String query = "INSERT INTO orders(customer_id, product_id, quantity, order_date, status) VALUES (?, ?, ?, CURDATE(), ?)";
            PreparedStatement pst = con.prepareStatement(query);

            pst.setInt(1, Integer.parseInt(txtCustomerId.getText()));
            pst.setInt(2, Integer.parseInt(txtProductId.getText()));
            pst.setInt(3, Integer.parseInt(txtQuantity.getText()));
            pst.setString(4, txtStatus.getText());

            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, "Order Added Successfully");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void viewOrders() {
        try {
            model.setRowCount(0);

            Connection con = DBConnection.getConnection();

            String query = "SELECT o.order_id, c.customer_name, p.product_name, o.quantity, o.order_date, o.status " +
                    "FROM orders o " +
                    "JOIN customers c ON o.customer_id = c.customer_id " +
                    "JOIN products p ON o.product_id = p.product_id";

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("order_id"),
                        rs.getString("customer_name"),
                        rs.getString("product_name"),
                        rs.getInt("quantity"),
                        rs.getDate("order_date"),
                        rs.getString("status")
                });
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void updateOrder() {
        try {
            Connection con = DBConnection.getConnection();

            String query = "UPDATE orders SET status = ? WHERE order_id = ?";
            PreparedStatement pst = con.prepareStatement(query);

            pst.setString(1, txtStatus.getText());
            pst.setInt(2, Integer.parseInt(txtOrderId.getText()));

            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, "Order Status Updated");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void deleteOrder() {
        try {
            Connection con = DBConnection.getConnection();

            String query = "DELETE FROM orders WHERE order_id = ?";
            PreparedStatement pst = con.prepareStatement(query);

            pst.setInt(1, Integer.parseInt(txtOrderId.getText()));

            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, "Order Deleted");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new OrderTrackingSystem();
    }
}
