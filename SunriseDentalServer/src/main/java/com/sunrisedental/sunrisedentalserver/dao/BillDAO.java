package com.sunrisedental.sunrisedentalserver.dao;

import com.sunrisedental.sunrisedentalserver.Bill;
import com.sunrisedental.sunrisedentalserver.config.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BillDAO {

    // CREATE BILL
    public boolean createBill(Bill bill) {

        String sql = "INSERT INTO bill "
                + "(appointment_id, consultation_fee, treatment_fee, "
                + "total_amount, bill_date) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(
                             sql,
                             java.sql.Statement.RETURN_GENERATED_KEYS
                     )) {

            statement.setInt(1, bill.getAppointmentId());
            statement.setDouble(2, bill.getConsultationFee());
            statement.setDouble(3, bill.getTreatmentFee());
            statement.setDouble(4, bill.getTotalAmount());
            statement.setDate(
                    5,
                    java.sql.Date.valueOf(bill.getBillDate())
            );

            int rows = statement.executeUpdate();

            if (rows > 0) {

                try (ResultSet rs = statement.getGeneratedKeys()) {

                    if (rs.next()) {
                        bill.setBillId(rs.getInt(1));
                    }
                }

                return true;
            }

            return false;

        } catch (Exception e) {

            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    // GET BILL BY APPOINTMENT ID
    public Bill getBillByAppointmentId(int appointmentId) {

        String sql = "SELECT * FROM bill "
                + "WHERE appointment_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, appointmentId);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {

                Bill bill = new Bill();

                bill.setBillId(
                        rs.getInt("bill_id")
                );

                bill.setAppointmentId(
                        rs.getInt("appointment_id")
                );

                bill.setConsultationFee(
                        rs.getDouble("consultation_fee")
                );

                bill.setTreatmentFee(
                        rs.getDouble("treatment_fee")
                );

                bill.setTotalAmount(
                        rs.getDouble("total_amount")
                );

                bill.setBillDate(
                        rs.getString("bill_date")
                );

                return bill;
            }

        } catch (Exception e) {

            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return null;
    }


    // GET ALL BILLS
    public List<Bill> getAllBills() {

        String sql = "SELECT * FROM bill "
                + "ORDER BY bill_date DESC, bill_id DESC";

        List<Bill> bills = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {

                Bill bill = new Bill();

                bill.setBillId(
                        rs.getInt("bill_id")
                );

                bill.setAppointmentId(
                        rs.getInt("appointment_id")
                );

                bill.setConsultationFee(
                        rs.getDouble("consultation_fee")
                );

                bill.setTreatmentFee(
                        rs.getDouble("treatment_fee")
                );

                bill.setTotalAmount(
                        rs.getDouble("total_amount")
                );

                bill.setBillDate(
                        rs.getString("bill_date")
                );

                bills.add(bill);
            }

        } catch (Exception e) {

            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return bills;
    }
}