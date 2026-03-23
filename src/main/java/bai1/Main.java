package bai1;

import database.MyDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Nhập mã bác sĩ: ");
        String code = sc.nextLine();

        System.out.print("Nhập mật khẩu: ");
        String pass = sc.nextLine();

        String sql = "SELECT * FROM Doctors WHERE code = ? AND pass = ?";

        try (
                Connection connection = MyDatabase.getConnection();
                PreparedStatement pstmt = connection.prepareStatement(sql)
        ) {
            pstmt.setString(1, code);
            pstmt.setString(2, pass);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                System.out.println("Đăng nhập thành công!");
            } else {
                System.out.println("Sai tài khoản hoặc mật khẩu!");
            }

        } catch (SQLException e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }
}