package bai3;

import database.MyDatabase;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

public class Main {
    /*
    * Giải thích
    * JDBC cần biết kiểu dữ liệu OUT vì
    * Stored Procedure có:
    * IN → truyền vào
    * OUT → trả ra
    * registerOutParameter() chính là bước khai báo trước
    * Kiểu DECIMAL trong SQL → Java dùng gì Types.DECIMAL
    * */

    public static void main(String[] args) {
        try (
                Connection conn = MyDatabase.getConnection();
                CallableStatement cstmt = conn.prepareCall("{call GET_SURGERY_FEE(?, ?)}")
        ) {
            cstmt.setInt(1, 505);

            cstmt.registerOutParameter(2, Types.DECIMAL);

            cstmt.execute();

            double cost = cstmt.getDouble(2);

            System.out.println("Chi phí phẫu thuật: " + cost);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
