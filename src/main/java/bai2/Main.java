package bai2;

import database.MyDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    /*
    * Giải thích:
    * setDouble(), setInt() giải quyết được vì
    * Không còn phụ thuộc vào Locale
    * PreparedStatement không convert thành chuỗi SQL
    * */

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Nhập ID bệnh nhân: ");
        int patientId = sc.nextInt();

        System.out.print("Nhập nhiệt độ (double): ");
        double temp = sc.nextDouble();

        System.out.print("Nhập nhịp tim (int): ");
        int heartRate = sc.nextInt();

        try (
                Connection connection = MyDatabase.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Vitals SET temperature = ?, heart_rate = ? WHERE p_id = ?");
        ) {
            preparedStatement.setDouble(1, temp);
            preparedStatement.setInt(2, heartRate);
            preparedStatement.setInt(3, patientId);

            int rows = preparedStatement.executeUpdate();

            if (rows > 0) {
                System.out.println("Cập nhật thành công!");
            } else {
                System.out.println("Không tìm thấy bệnh nhân!");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
