package bai5;
import database.MyDatabase;

import java.sql.*;
import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        while (true) {
            System.out.println("\n===== QUẢN LÝ BỆNH NHÂN =====");
            System.out.println("1. Danh sách bệnh nhân");
            System.out.println("2. Tiếp nhận bệnh nhân");
            System.out.println("3. Cập nhật bệnh án");
            System.out.println("4. Xuất viện & Tính phí");
            System.out.println("5. Thoát");
            System.out.print("Chọn: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> listPatients();
                case 2 -> addPatient();
                case 3 -> updateDisease();
                case 4 -> dischargePatient();
                case 5 -> {
                    System.out.println("Thoát...");
                    return;
                }
                default -> System.out.println("Chọn sai!");
            }
        }
    }

    static void listPatients() {
        String sql = "SELECT id, name, age, department FROM Patients";

        try (
                Connection conn = MyDatabase.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()
        ) {
            System.out.println("\n--- DANH SÁCH ---");
            while (rs.next()) {
                System.out.println(
                        rs.getInt("id") + " | " +
                                rs.getString("name") + " | " +
                                rs.getInt("age") + " | " +
                                rs.getString("department")
                );
            }

        } catch (SQLException e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }

    static void addPatient() {
        String sql = "INSERT INTO Patients(name, age, department, disease, days_admitted) VALUES(?, ?, ?, ?, ?)";

        try (
                Connection conn = MyDatabase.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            System.out.print("Tên: ");
            String name = sc.nextLine();

            System.out.print("Tuổi: ");
            int age = sc.nextInt();
            sc.nextLine();

            System.out.print("Khoa: ");
            String dept = sc.nextLine();

            System.out.print("Bệnh: ");
            String disease = sc.nextLine();

            System.out.print("Số ngày nhập viện: ");
            int days = sc.nextInt();

            pstmt.setString(1, name);
            pstmt.setInt(2, age);
            pstmt.setString(3, dept);
            pstmt.setString(4, disease);
            pstmt.setInt(5, days);

            pstmt.executeUpdate();

            System.out.println("Thêm thành công!");

        } catch (SQLException e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }

    static void updateDisease() {
        String sql = "UPDATE Patients SET disease = ? WHERE id = ?";

        try (
                Connection conn = MyDatabase.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            System.out.print("Nhập ID: ");
            int id = sc.nextInt();
            sc.nextLine();

            System.out.print("Bệnh mới: ");
            String disease = sc.nextLine();

            pstmt.setString(1, disease);
            pstmt.setInt(2, id);

            int rows = pstmt.executeUpdate();

            if (rows > 0)
                System.out.println("Cập nhật thành công!");
            else
                System.out.println("Không tìm thấy bệnh nhân!");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    static void dischargePatient() {

        System.out.print("Nhập ID bệnh nhân: ");
        int id = sc.nextInt();

        String getDaysSql = "SELECT days_admitted FROM Patients WHERE id = ?";
        String callSP = "{call CALCULATE_DISCHARGE_FEE(?, ?)}";
        String deleteSql = "DELETE FROM Patients WHERE id = ?";

        try (
                Connection conn = MyDatabase.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(getDaysSql)
        ) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (!rs.next()) {
                System.out.println("Không tìm thấy bệnh nhân!");
                return;
            }

            int days = rs.getInt("days_admitted");

            try (CallableStatement cstmt = conn.prepareCall(callSP)) {

                cstmt.setInt(1, days);
                cstmt.registerOutParameter(2, Types.DECIMAL);

                cstmt.execute();

                double fee = cstmt.getDouble(2);

                System.out.println("Viện phí: " + fee);

                try (PreparedStatement del = conn.prepareStatement(deleteSql)) {
                    del.setInt(1, id);
                    del.executeUpdate();
                }

                System.out.println("Đã xuất viện!");

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}