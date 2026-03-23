package bai5.view;

import bai5.model.Patient;
import bai5.service.PatientService;

import java.util.List;
import java.util.Scanner;

public class HospitalView {

    private PatientService service = new PatientService();
    private Scanner sc = new Scanner(System.in);

    public void start() {
        while (true) {
            System.out.println("\n1. Danh sách");
            System.out.println("2. Thêm");
            System.out.println("3. Cập nhật");
            System.out.println("4. Xuất viện");
            System.out.println("5. Thoát");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> showAll();
                case 2 -> add();
                case 3 -> update();
                case 4 -> discharge();
                case 5 -> { return; }
            }
        }
    }

    void showAll() {
        List<Patient> list = service.getAll();
        for (Patient p : list) {
            System.out.println(p.getId() + " - " + p.getName());
        }
    }

    void add() {
        System.out.print("Tên: ");
        String name = sc.nextLine();

        System.out.print("Tuổi: ");
        int age = sc.nextInt(); sc.nextLine();

        System.out.print("Khoa: ");
        String dept = sc.nextLine();

        System.out.print("Bệnh: ");
        String disease = sc.nextLine();

        System.out.print("Ngày: ");
        int days = sc.nextInt();

        service.add(new Patient(name, age, dept, disease, days));
    }

    void update() {
        System.out.print("ID: ");
        int id = sc.nextInt(); sc.nextLine();

        System.out.print("Bệnh mới: ");
        String disease = sc.nextLine();

        boolean ok = service.updateDisease(id, disease);
        System.out.println(ok ? "OK" : "Fail");
    }

    void discharge() {
        System.out.print("ID: ");
        int id = sc.nextInt();

        double fee = service.discharge(id);

        if (fee == -1) System.out.println("Không tìm thấy");
        else System.out.println("Viện phí: " + fee);
    }
}
