package bai4;

import database.MyDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    /*
    * Giải thích
    * sự lãng phí tài nguyên của Database Server:
    * Mỗi câu SQL là một chuỗi khác nhau
    * DB phải:
    * Kiểm tra cú pháp
    * Xác định bảng, cột
    * Tạo Execution Plan 1.000 lần
    * SQL giữ nguyên
    * Chỉ thay đổi dữ liệu (?)
     * */

    static class TestResult {
        private String data;

        public TestResult(String data) {
            this.data = data;
        }

        public String getData() {
            return data;
        }
    }

    public static void main(String[] args) {

        List<TestResult> list = new ArrayList<>();
        list.add(new TestResult("Hb: 13.5"));
        list.add(new TestResult("WBC: 7000"));
        list.add(new TestResult("Platelet: 250000"));

        try (
                Connection conn = MyDatabase.getConnection();
                PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Results(data) VALUES(?)")
        ) {

            for (TestResult tr : list) {
                pstmt.setString(1, tr.getData());
                pstmt.executeUpdate();
            }

            System.out.println("Insert thành công!");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
