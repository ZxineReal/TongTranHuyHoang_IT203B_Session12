package bai5.dao;

import bai5.model.Patient;
import database.MyDatabase;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO {

    public List<Patient> getAll() {
        List<Patient> list = new ArrayList<>();
        String sql = "SELECT * FROM Patients";

        try (
                Connection conn = MyDatabase.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()
        ) {
            while (rs.next()) {
                Patient p = new Patient();
                p.setId(rs.getInt("id"));
                p.setName(rs.getString("name"));
                p.setId(rs.getInt("id"));

                list.add(p);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    public void insert(Patient p) {
        String sql = "INSERT INTO Patients(name, age, department, disease, days_admitted) VALUES(?, ?, ?, ?, ?)";

        try (
                Connection conn = MyDatabase.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, p.getName());
            pstmt.setInt(2, p.getAge());
            pstmt.setString(3, p.getDepartment());
            pstmt.setString(4, p.getDisease());
            pstmt.setInt(5, p.getDaysAdmitted());

            pstmt.executeUpdate();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean updateDisease(int id, String disease) {
        String sql = "UPDATE Patients SET disease = ? WHERE id = ?";

        try (
                Connection conn = MyDatabase.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, disease);
            pstmt.setInt(2, id);

            return pstmt.executeUpdate() > 0;

        } catch (Exception e) {
            return false;
        }
    }

    public int getDays(int id) {
        String sql = "SELECT days_admitted FROM Patients WHERE id = ?";

        try (
                Connection conn = MyDatabase.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("days_admitted");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }

    public void delete(int id) {
        String sql = "DELETE FROM Patients WHERE id = ?";

        try (
                Connection conn = MyDatabase.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public double calculateFee(int days) {
        String sql = "{call CALCULATE_DISCHARGE_FEE(?, ?)}";

        try (
                Connection conn = MyDatabase.getConnection();
                CallableStatement cstmt = conn.prepareCall(sql)
        ) {
            cstmt.setInt(1, days);
            cstmt.registerOutParameter(2, Types.DECIMAL);

            cstmt.execute();

            return cstmt.getDouble(2);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }
}
