package repository.customer.impl;

import model.Customer;
import model.CustomerType;
import repository.BaseRepository;
import repository.customer.ICustomerRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CustomerRepository implements ICustomerRepository {
    private final String SELECT_ALL_CUSTOMER = "select c.*,ct.customer_type_name from customer as c left join customer_type as ct on ct.customer_type_id = c.customer_type_id ;";
    private final String SELECT_CUSTOMER_BY_ID = "select * from customer where customer_id = ?;";
    private final String DELETE_CUSTOMER_BY_ID = "delete from customer where customer_id = ?;";
    private final String UPDATE_CUSTOMER_BY_ID = "update customer set \n" +
            "customer_name = ?,\n" +
            "date_of_birth =?,\n" +
            "gender = ?, \n" +
            "id_card = ?,\n" +
            "phone_number = ?,\n" +
            "address = ?,\n" +
            "email=?,\n" +
            "customer_type_id =? \n" +
            "where customer_id = ?;";
    private final String INSERT_INTO_CUSTOMER = "insert into `customer` values\n" +
            "\t( ?, ?, ? , ?, ?, ?, ?,?);";

    @Override
    public List<Customer> selectAllCustomer() {
        List<Customer> customerList = new ArrayList<>();
        Connection connection = BaseRepository.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(SELECT_ALL_CUSTOMER);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("customer_id");
                String name = rs.getString("customer_name");
                String dateOfBirth = rs.getString("date_of_birth");
                String gender = rs.getString("gender");
                String idCard = rs.getString("id_card");
                String phoneNumber = rs.getString("phone_number");
                String address = rs.getString("address");
                String email = rs.getString("email");
                int customerTypeId = rs.getInt("customer_type_id");
                String customerTypeName = rs.getString("customer_type_name");
                CustomerType customerType = new CustomerType(customerTypeId, customerTypeName);
                Customer customer = new Customer(id, name, dateOfBirth, gender, idCard, phoneNumber, address, email, customerType);
                customerList.add(customer);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return customerList;
    }

    @Override
    public Customer selectCustomerById(int id) {
        Customer customer = null;
        Connection connection = BaseRepository.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(SELECT_CUSTOMER_BY_ID);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id_new = rs.getInt("customer_id");
                String name = rs.getString("customer_name");
                String dateOfBirth = rs.getString("date_of_birth");
                String gender = rs.getString("gender");
                String idCCard = rs.getString("id_card");
                String phoneNumber = rs.getString("phone_number");
                String address = rs.getString("address");
                String email = rs.getString("email");
                int customerTypeId = rs.getInt("customer_type_id");
                String customerTypeName = rs.getString("customer_type_name");
                CustomerType customerType = new CustomerType(customerTypeId, customerTypeName);
                customer = new Customer(id_new, name, dateOfBirth, gender, idCCard, phoneNumber, address, email, customerType);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return customer;
    }

    @Override
    public boolean deleteCustomer(int id) {
        Connection connection = BaseRepository.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(DELETE_CUSTOMER_BY_ID);
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean editCustomer(Customer customer) {
        Connection connection = BaseRepository.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(UPDATE_CUSTOMER_BY_ID);
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getDateOfBirth());
            ps.setString(3, customer.getGender());
            ps.setString(4, customer.getIdCard());
            ps.setString(5, customer.getPhoneNumber());
            ps.setString(6, customer.getAddress());
            ps.setString(7, customer.getEmail());
            ps.setInt(8, customer.getCustomerType().getId());
            ps.setInt(9, customer.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean insertCustomer(Customer customer) {
        Connection connection = BaseRepository.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(INSERT_INTO_CUSTOMER);
            ps.setString(1,customer.getName());
            ps.setString(2,customer.getDateOfBirth());
            ps.setString(3,customer.getGender());
            ps.setString(4,customer.getIdCard());
            ps.setString(5,customer.getPhoneNumber());
            ps.setString(6,customer.getAddress());
            ps.setString(7,customer.getEmail());
            ps.setString(8,customer.getCustomerType().getName() );
            return ps.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
}
